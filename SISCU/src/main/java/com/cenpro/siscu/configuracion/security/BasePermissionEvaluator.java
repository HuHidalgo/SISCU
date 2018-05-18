package com.cenpro.siscu.configuracion.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class BasePermissionEvaluator implements PermissionEvaluator
{
    @Override
    @SuppressWarnings("unchecked")
    public boolean hasPermission(Authentication authentication, Object targetDomainObject,
            Object permission)
    {
        int indiceAutorizacion = 0;
        boolean autorizacionEncontrada = false;
        List<CustomGrantedAuthority> autorizaciones = (List<CustomGrantedAuthority>) (Object) authentication
                .getAuthorities();
        while (!autorizacionEncontrada && indiceAutorizacion < autorizaciones.size())
        {
            CustomGrantedAuthority autorizacion = autorizaciones.get(indiceAutorizacion);
            if (autorizacion.getAuthority().equals(targetDomainObject))
            {
                autorizacionEncontrada = true;
            }
            indiceAutorizacion++;
        }
        return autorizacionEncontrada;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId,
            String targetType, Object permission)
    {
        return true;
    }
}