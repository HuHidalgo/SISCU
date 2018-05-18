package com.cenpro.siscu.utilitario;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidatorContext;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.cenpro.siscu.model.parametro.MensajeValidacion;

public class ValidatorUtil
{
    private ValidatorUtil()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
    
    public static void addCustomMessageWithTemplate(String template,
            ConstraintValidatorContext context)
    {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }

    public static void addCustomMessageWithTemplateWithProperty(String template, String property,
            ConstraintValidatorContext context)
    {
        if (property.isEmpty())
        {
            addCustomMessageWithTemplate(template, context);
        } else
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(template).addPropertyNode(property)
                    .addConstraintViolation();
        }
    }

    public static String obtenerCadenaErrores(Errors error)
    {
        return error.getAllErrors().stream().map(ObjectError::getDefaultMessage).distinct()
                .collect(Collectors.joining("\n"));
    }

    public static List<MensajeValidacion> obtenerMensajeValidacionError(Errors error)
    {
        return error.getFieldErrors().stream()
                .map(fieldError -> new MensajeValidacion(
                        StringsUtils.obtenerCadenaDespuesDePunto(fieldError.getField()),
                        fieldError.getDefaultMessage()))
                .distinct().collect(Collectors.toList());
    }
}