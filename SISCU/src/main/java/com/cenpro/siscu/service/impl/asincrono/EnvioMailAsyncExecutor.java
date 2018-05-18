package com.cenpro.siscu.service.impl.asincrono;

import java.util.concurrent.Future;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.cenpro.siscu.aspecto.anotacion.ControlLogEnvioCorreoDependencia;
import com.cenpro.siscu.model.procesoautomatico.Correo;
import com.cenpro.siscu.service.IEnvioMailAsyncExecutor;
import com.cenpro.siscu.service.IEnvioMailService;

@Service
public class EnvioMailAsyncExecutor implements IEnvioMailAsyncExecutor
{
    private @Autowired IEnvioMailService envioMailService;

    @Async
    @ControlLogEnvioCorreoDependencia
    public Future<Void> enviarCorreo(Correo correo) throws MessagingException
    {
        envioMailService.enviarCorreo(correo);
        try
        {
            Thread.sleep(30000);
        } catch (Exception e)
        {

        }
        return new AsyncResult<Void>(null);
    }
}