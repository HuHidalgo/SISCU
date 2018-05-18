package com.cenpro.siscu.service.impl.procesomanual;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.cenpro.siscu.model.mantenimiento.ParametroCorreo;
import com.cenpro.siscu.model.procesoautomatico.Correo;
import com.cenpro.siscu.service.IEnvioMailService;
import com.cenpro.siscu.service.IParametroCorreoService;
import com.cenpro.siscu.utilitario.MimeTypeUtil;

@Service
@PropertySource("classpath:email.properties")
public class EnvioMailService implements IEnvioMailService
{
    private @Autowired Environment env;
    private @Autowired IParametroCorreoService parametroCorreoService;

    private Session sesionMail;
    private Transport transport;
    private Properties properties;
    private long tokenExpires = 1458168133864L;

    private ParametroCorreo parametroCorreo;

    @PostConstruct
    public void init() throws MessagingException
    {
        this.parametroCorreo = parametroCorreoService.buscarParametroCorreo();
        this.properties = new Properties();
        properties.put("mail.transport.protocol", env.getProperty("protocol"));
        properties.put("mail.smtp.host", env.getProperty("host"));
        properties.put("mail.smtp.port", Integer.valueOf(env.getProperty("port")));
        properties.put("mail.smtp.auth", Boolean.valueOf(env.getProperty("auth")));
        properties.put("mail.smtp.ssl.trust", env.getProperty("ssl.trust"));
        properties.put("mail.smtp.auth.mechanisms", env.getProperty("auth.mechanisms"));
        properties.put("mail.smtp.starttls.enable", Boolean.valueOf(env.getProperty("starttls")));
        properties.put("mail.debug", Boolean.valueOf(env.getProperty("debug")));
        this.sesionMail = Session.getInstance(this.properties);
    }

    public void enviarCorreo(List<Correo> correos) throws MessagingException
    {
        for (Correo correo : correos)
        {
            this.enviarCorreo(correo);
        }
        this.cerrarSesion();
    }

    public void enviarCorreo(Correo correo) throws MessagingException
    {
        // Contenido del Correo
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        MimeBodyPart mimeBodyText = new MimeBodyPart();
        InternetAddress emisor = new InternetAddress(correo.getCorreoEmisor());
        InternetAddress receptor = new InternetAddress(correo.getCorreoDestino());
        DataHandler dataHandler = new DataHandler(correo.getDocumento(), correo.getContentType());
        mimeBodyPart.setDataHandler(dataHandler);
        mimeBodyPart.setFileName(correo.getNombreConExtension());
        mimeBodyText.setContent(correo.getTextoCuerpo(), MimeTypeUtil.TEXT);

        // Construccion del Correo
        MimeMultipart mimeMultipart = new MimeMultipart(mimeBodyPart, mimeBodyText);
        MimeMessage mimeMessage = new MimeMessage(this.sesionMail);
        mimeMessage.setSubject(correo.getTextoEncabezado());
        mimeMessage.setSender(emisor);
        mimeMessage.setRecipient(Message.RecipientType.TO, receptor);
        
        // Correo con copia
        if (correo.getCorreoDestinoConCopia() != null
                && !correo.getCorreoDestinoConCopia().trim().isEmpty())
        {
            InternetAddress receptorConCopia = new InternetAddress(
                    correo.getCorreoDestinoConCopia());
            mimeMessage.setRecipient(Message.RecipientType.CC, receptorConCopia);
        }
        mimeMessage.setContent(mimeMultipart);

        // Envio de Correo
        this.enviarCorreo(mimeMessage);
    }

    private void enviarCorreo(MimeMessage mimeMessage) throws MessagingException
    {
        if (this.transport == null)
        {
            this.transport = this.sesionMail.getTransport();
        }
        if (!this.transport.isConnected() || System.currentTimeMillis() > this.tokenExpires)
        {
            InternetAddress emisor = (InternetAddress) mimeMessage.getSender();
            this.iniciarSesion(emisor.getAddress());
        }
        this.transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }

    public void iniciarSesion(String emisor) throws MessagingException
    {
        this.transport.connect(emisor, obtenerAccessToken());
    }

    public void cerrarSesion() throws MessagingException
    {
        if (this.transport != null && this.transport.isConnected())
        {
            this.transport.close();
        }
    }

    private String obtenerAccessToken()
    {
        if (System.currentTimeMillis() > this.tokenExpires)
        {
            try
            {
                String request = "client_id="
                        + URLEncoder.encode(parametroCorreo.getOAuthClienteId(), "UTF-8")
                        + "&client_secret="
                        + URLEncoder.encode(parametroCorreo.getOAuthSecretId(), "UTF-8")
                        + "&refresh_token="
                        + URLEncoder.encode(parametroCorreo.getRefreshToken(), "UTF-8")
                        + "&grant_type=refresh_token";
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
                        parametroCorreo.getTokenUrl()).openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");
                PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                printWriter.print(request);
                printWriter.flush();
                printWriter.close();
                httpURLConnection.connect();
                HashMap<String, Object> respuesta = new ObjectMapper().readValue(
                        httpURLConnection.getInputStream(),
                        new TypeReference<HashMap<String, Object>>()
                        {
                        });
                this.parametroCorreo.setAccessToken(String.valueOf(respuesta.get("access_token")));
                this.tokenExpires = System.currentTimeMillis()
                        + (((Number) respuesta.get("expires_in")).intValue() * 1000);
                return parametroCorreo.getAccessToken();
            } catch (UnsupportedEncodingException unEx)
            {

            } catch (IOException ioException)
            {

            }
        }
        return parametroCorreo.getAccessToken();
    }
}