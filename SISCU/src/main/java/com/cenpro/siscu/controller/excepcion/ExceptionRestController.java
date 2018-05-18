package com.cenpro.siscu.controller.excepcion;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sun.mail.util.MailConnectException;

import com.cenpro.siscu.model.parametro.MensajeValidacion;
import com.cenpro.siscu.service.excepcion.BadRequestException;
import com.cenpro.siscu.service.excepcion.CargaArchivoException;
import com.cenpro.siscu.service.excepcion.EjecucionManualException;
import com.cenpro.siscu.service.excepcion.EscenarioException;
import com.cenpro.siscu.service.excepcion.ListaVaciaException;
import com.cenpro.siscu.service.excepcion.MantenimientoException;
import com.cenpro.siscu.service.excepcion.OrdenEjecucionException;
import com.cenpro.siscu.service.excepcion.ValorEncontradoException;
import com.cenpro.siscu.service.excepcion.ValorNoEncontradoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.StringsUtils;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionRestController
{
    private @Autowired Logger logger;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public List<MensajeValidacion> capturarConstraintViolationException(
            ConstraintViolationException ex)
    {
        logger.error(ex.getMessage(), ex);
        return ex.getConstraintViolations().stream()
                .map(constraint -> new MensajeValidacion(StringsUtils.obtenerCadenaDespuesDePunto(
                        constraint.getPropertyPath().toString()), constraint.getMessage()))
                .distinct().collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public List<MensajeValidacion> capturarBadRequestException(BadRequestException ex)
    {
        logger.error(ex.getMessage(), ex);
        return ex.getMensajesValidacion();
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = { EscenarioException.class, OrdenEjecucionException.class,
            EjecucionManualException.class, ListaVaciaException.class,
            ValorNoEncontradoException.class, ValorEncontradoException.class,
            MantenimientoException.class, CargaArchivoException.class, NullPointerException.class,
            IllegalArgumentException.class })
    public String capturarEscenarioException(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String capturarMissingServletRequestParameterException(
            MissingServletRequestParameterException ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_MISSING_PARAMETER_EXCEPTION_REST;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = { TypeMismatchException.class, InvalidFormatException.class,
            HttpMessageNotReadableException.class })
    public String capturarTypeMismatchException(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_TYPE_MISMATCH_EXCEPTION_REST;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = { UnknownHostException.class, MailConnectException.class })
    public String capturarUnknownHostException(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_CONEXION_SERVIDOR_CORREOS;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String capturarOtrasExcepcion(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_DESCONOCIDO;
    }
}