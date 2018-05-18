package com.cenpro.siscu.configuracion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.AuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;

import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
import com.cenpro.siscu.aspecto.enumeracion.Tipo;
import com.cenpro.siscu.service.excepcion.LoginException;
import com.cenpro.siscu.service.impl.seguridad.AuditoriaService;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    @Qualifier("customUserDetailsService")
    private @Autowired UserDetailsService userDetailsService;
    private @Autowired PasswordEncoder passwordEnconder;
    private @Autowired AuditoriaService auditoriaService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String idUsuario = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        CustomUser usuario = null;
        String direccionIp = ((WebAuthenticationDetails) authentication.getDetails())
                .getRemoteAddress();
        try
        {
            usuario = (CustomUser) userDetailsService.loadUserByUsername(idUsuario);
        } catch (CannotCreateTransactionException cannotCreateTransactionException)
        {
            throw new LoginException(ConstantesExcepciones.ERROR_CONEXION_BASE_DATOS);
        }
        if (usuario == null)
        {
            auditoriaService.registrarAuditoria(Tipo.Login, Comentario.UsuarioNoEncontrado,
                    Accion.Acceso, false, idUsuario, direccionIp);
            throw new LoginException(
                    String.format(ConstantesExcepciones.USUARIO_NO_ENCONTRADO, idUsuario));
        }
        if (!usuario.isEnabled())
        {
            auditoriaService.registrarAuditoria(Tipo.Login, Comentario.NoActivo, Accion.Acceso,
                    false, idUsuario, direccionIp);
            throw new LoginException(
                    String.format(ConstantesExcepciones.USUARIO_NO_ACTIVO, idUsuario));
        }
        if (!passwordEnconder.matches(password, usuario.getPassword()))
        {
            auditoriaService.registrarAuditoria(Tipo.Login, Comentario.CredencialIncorrecta,
                    Accion.Acceso, false, idUsuario, direccionIp);
            throw new LoginException(ConstantesExcepciones.CONTRASENIA_INCORRECTA);
        }
        auditoriaService.registrarAuditoria(Tipo.Login, Comentario.CredencialCorrecta,
                Accion.Acceso, true, idUsuario, direccionIp);
        return new UsernamePasswordAuthenticationToken(usuario, password, usuario.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}