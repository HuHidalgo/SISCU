package com.cenpro.siscu.validacion.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IExamenMedicoMedicinaGeneralService;
import com.cenpro.siscu.utilitario.Regex;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.NumRegMedicinaGeneral;

public class NumRegMedicinaGeneralValidator
        implements ConstraintValidator<NumRegMedicinaGeneral, Object>
{
    private int tamanioAnio;
    private String campoAnio;
    private int minNumeroRegistro;
    private String campoNumeroRegistro;

    private @Autowired IExamenMedicoMedicinaGeneralService examenMedicoMedicinaGeneralService;

    @Override
    public void initialize(NumRegMedicinaGeneral anotacion)
    {
        this.campoAnio = anotacion.campoAnio();
        this.tamanioAnio = anotacion.tamanioAnio();
        this.minNumeroRegistro = anotacion.minNumeroRegistro();
        this.campoNumeroRegistro = anotacion.campoNumeroRegistro();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            Integer numeroRegistro = Integer.parseInt(Optional
                    .ofNullable(BeanUtils.getProperty(dto, this.campoNumeroRegistro)).orElse("-1"));
            String anio = BeanUtils.getProperty(dto, this.campoAnio);
            if (numeroRegistro == -1)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.ExamenMedicoMedicinaGeneral.numeroRegistro}",
                        this.campoNumeroRegistro, context);
                return false;
            }
            if (numeroRegistro < this.minNumeroRegistro)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Min.ExamenMedicoMedicinaGeneral.numeroRegistro}",
                        this.campoNumeroRegistro, context);
                return false;
            }
            if (anio == null || anio.equals("null"))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.ExamenMedicoMedicinaGeneral.anio}", this.campoAnio, context);
                return false;
            }
            if (anio.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.ExamenMedicoMedicinaGeneral.anio}", this.campoAnio, context);
                return false;
            }
            if (anio.length() != this.tamanioAnio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Length.ExamenMedicoMedicinaGeneral.anio}", this.campoAnio, context);
                return false;
            }
            if (!anio.matches(Regex.SOLO_DIGITOS))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.ExamenMedicoMedicinaGeneral.anio}", this.campoAnio, context);
                return false;
            }
            boolean existe = examenMedicoMedicinaGeneralService
                    .existeExamenMedicoMedicinaGeneral(numeroRegistro, anio);
            if (!existe)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.ExamenMedicoMedicinaGeneral.numeroRegistro}", this.campoAnio,
                        context);
                return false;
            }
            return true;
        } catch (Exception e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoAnio, context);
            return false;
        }
    }
}