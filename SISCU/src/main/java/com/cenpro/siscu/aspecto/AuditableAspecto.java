package com.cenpro.siscu.aspecto;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cenpro.siscu.aspecto.anotacion.Audit;
import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
import com.cenpro.siscu.aspecto.enumeracion.Dato;
import com.cenpro.siscu.aspecto.enumeracion.Tipo;
import com.cenpro.siscu.configuracion.security.SecurityContextFacade;
import com.cenpro.siscu.model.seguridad.Auditoria;
import com.cenpro.siscu.service.IAuditoriaService;
import com.cenpro.siscu.utilitario.CustomSpringExpressionLanguageParserUtil;

@Aspect
@Component
public class AuditableAspecto
{
    private @Autowired Logger logger;
    private @Autowired IAuditoriaService auditoriaService;

    @Around("@annotation(audit)")
    public Object registrarAuditoria(ProceedingJoinPoint proceedingJoinPoint, Audit audit)
            throws Throwable
    {
        boolean exito = true;
        Tipo tipo = null;
        String datos = "";
        Comentario comentario = null;
        String comentarioParaAuditoria = "";
        boolean tieneClaseAnotacion = false;
        Accion accion = null;
        String direccionIp = SecurityContextFacade.obtenerIpCliente();
        String nombreUsuario = SecurityContextFacade.obtenerNombreUsuario();

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Class<?> clazz = signature.getDeclaringType();
        tieneClaseAnotacion = this.tieneAnotacion(clazz);

        tipo = this.obtenerTipo(audit.tipo(), clazz, tieneClaseAnotacion);
        comentario = this.obtenerComentario(audit.comentario(), clazz, tieneClaseAnotacion);
        accion = this.obtenerAccion(audit.accion(), clazz, tieneClaseAnotacion);

        if (accion == Accion.Registro || accion == Accion.Actualizacion
                || accion == Accion.Eliminacion)
        {
            datos = this.obtenerDatos(audit.datos(), clazz, tieneClaseAnotacion);
            String datosParaAuditar = CustomSpringExpressionLanguageParserUtil.getDynamicValue(
                    signature.getParameterNames(), proceedingJoinPoint.getArgs(), datos);
            comentarioParaAuditoria = String.format(comentario.toString(), tipo.toString(),
                    datosParaAuditar);
        } else
        {
            comentarioParaAuditoria = String.format(comentario.toString(), tipo.toString());
        }
        Auditoria auditoria = Auditoria.builder().codigoAuditoria(tipo.name().toUpperCase())
                .idAccion(accion.toString()).comentario(comentarioParaAuditoria)
                .direccionIp(direccionIp).nombreUsuario(nombreUsuario).build();
        try
        {
            return proceedingJoinPoint.proceed();
        } catch (Exception ex)
        {
            exito = false;
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally
        {
            auditoria.setExito(exito);
            auditoriaService.registrarAuditoria(auditoria);
        }
    }

    public boolean tieneAnotacion(Class<?> clazz)
    {
        return clazz.isAnnotationPresent(Audit.class);
    }

    public Tipo obtenerTipo(Tipo tipo, Class<?> clazz, boolean tieneClaseAnotacion)
    {
        if (tipo == Tipo.Ninguno && tieneClaseAnotacion)
        {
            tipo = clazz.getAnnotation(Audit.class).tipo();
        }
        return tipo;
    }

    public String obtenerDatos(Dato dato, Class<?> clazz, boolean tieneClaseAnotacion)
    {
        if (dato == Dato.Ninguno && tieneClaseAnotacion)
        {
            dato = clazz.getAnnotation(Audit.class).datos();

        }
        return dato.toString();
    }

    public Comentario obtenerComentario(Comentario comentario, Class<?> clazz,
            boolean tieneClaseAnotacion)
    {
        if (comentario == Comentario.Ninguno && tieneClaseAnotacion)
        {
            comentario = clazz.getAnnotation(Audit.class).comentario();
        }
        return comentario;
    }

    public Accion obtenerAccion(Accion accion, Class<?> clazz, boolean tieneClaseAnotacion)
    {
        if (accion == Accion.Ninguna && tieneClaseAnotacion)
        {
            accion = clazz.getAnnotation(Audit.class).accion();
        }
        return accion;
    }
}