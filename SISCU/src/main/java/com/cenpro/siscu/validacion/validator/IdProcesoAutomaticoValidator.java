package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IProcesoAutomaticoService;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.IdProcesoAutomatico;

public class IdProcesoAutomaticoValidator
        implements ConstraintValidator<IdProcesoAutomatico, String>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    @Override
    public void initialize(IdProcesoAutomatico anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String idProcesoAutomatico, ConstraintValidatorContext context)
    {
        if (idProcesoAutomatico == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.ProcesoAutomatico.idProcesoAutomatico}", context);
            return false;
        }
        if (idProcesoAutomatico.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.ProcesoAutomatico.idProcesoAutomatico}", context);
            return false;
        }
        if (idProcesoAutomatico.length() < min || idProcesoAutomatico.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.ProcesoAutomatico.idProcesoAutomatico}", context);
            return false;
        }
        boolean existeProcesoAutomatico = procesoAutomaticoService
                .existeProcesoAutomatico(idProcesoAutomatico);
        return (existe) ? existeProcesoAutomatico : !existeProcesoAutomatico;
    }
}