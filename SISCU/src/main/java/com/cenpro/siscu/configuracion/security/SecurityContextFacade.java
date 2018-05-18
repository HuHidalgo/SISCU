package com.cenpro.siscu.configuracion.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class SecurityContextFacade
{
    public static String obtenerNombreUsuario()
    {
        String nombreUsuario = null;
        CustomUser user = getAuthenticatedUser();
        if (user != null)
        {
            nombreUsuario = user.getUsername();
        }
        return nombreUsuario;
    }

    public static CustomUser getAuthenticatedUser()
    {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();
        CustomUser user = null;
        if (aut != null)
        {
            user = (CustomUser) aut.getPrincipal();
        }
        return user;
    }

    public static String obtenerIpCliente()
    {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        String ipAddress = "0.0.0.0";
        if (details instanceof WebAuthenticationDetails)
        {
            ipAddress = ((WebAuthenticationDetails) details).getRemoteAddress();
        }
        return ipAddress;
    }
}