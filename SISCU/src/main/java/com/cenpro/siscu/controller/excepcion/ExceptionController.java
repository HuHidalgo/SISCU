package com.cenpro.siscu.controller.excepcion;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cenpro.siscu.controller.excepcion.anotacion.Vista;
import com.cenpro.siscu.service.excepcion.BadRequestException;
import com.cenpro.siscu.service.excepcion.EjecucionManualException;
import com.cenpro.siscu.service.excepcion.EscenarioException;
import com.cenpro.siscu.service.excepcion.ListaVaciaException;
import com.cenpro.siscu.service.excepcion.OrdenEjecucionException;
import com.cenpro.siscu.service.excepcion.ValorEncontradoException;
import com.cenpro.siscu.service.excepcion.ValorNoEncontradoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;

@ControllerAdvice(annotations = Vista.class)
public class ExceptionController
{
    private @Autowired Logger logger;

    @ExceptionHandler(BadRequestException.class)
    public String catchBadRequestException(BadRequestException ex)
    {
        logger.error(ex.getMessage(), ex);
        return "redirect:/400";
    }

    @ExceptionHandler({ EscenarioException.class, OrdenEjecucionException.class,
            EjecucionManualException.class, ListaVaciaException.class,
            ValorNoEncontradoException.class, ValorEncontradoException.class })
    public String catchListaVaciaException(Exception ex, RedirectAttributes redirect)
    {
        logger.error(ex.getMessage(), ex);
        redirect.addFlashAttribute("mensaje", ex.getMessage());
        return "redirect:/409";
    }

    @ExceptionHandler(Exception.class)
    public String catchExcepcion(Exception ex, RedirectAttributes redirect)
    {
        logger.error(ex.getMessage(), ex);
        redirect.addFlashAttribute("mensaje", ConstantesExcepciones.ERROR_DESCONOCIDO);
        return "redirect:/500";
    }
}