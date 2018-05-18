package com.cenpro.siscu.validacion.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IExamenMedicoLaboratorioService;
import com.cenpro.siscu.utilitario.Regex;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.NumRegLaboratorio;

public class NumRegLaboratorioValidator implements ConstraintValidator<NumRegLaboratorio, Object>
{
    private int tamanioAnio;
    private String campoAnio;
    private int minNumeroRegistro;
    private String campoNumeroRegistro;

    private @Autowired IExamenMedicoLaboratorioService examenMedicoLaboratorioService;

    @Override
    public void initialize(NumRegLaboratorio anotacion)
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
                        "{NotNull.ExamenMedicoLaboratorio.numeroRegistro}",
                        this.campoNumeroRegistro, context);
                return false;
            }
            if (numeroRegistro < this.minNumeroRegistro)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Min.ExamenMedicoLaboratorio.numeroRegistro}", this.campoNumeroRegistro,
                        context);
                return false;
            }
            if (anio == null || anio.equals("null"))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.ExamenMedicoLaboratorio.anio}", this.campoAnio, context);
                return false;
            }
            if (anio.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.ExamenMedicoLaboratorio.anio}", this.campoAnio, context);
                return false;
            }
            if (anio.length() != this.tamanioAnio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Length.ExamenMedicoLaboratorio.anio}", this.campoAnio, context);
                return false;
            }
            if (!anio.matches(Regex.SOLO_DIGITOS))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.ExamenMedicoLaboratorio.anio}", this.campoAnio, context);
                return false;
            }
            boolean existe = examenMedicoLaboratorioService
                    .existeExamenMedicoLaboratorio(numeroRegistro, anio);
            if (!existe)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.ExamenMedicoLaboratorio.numeroRegistro}", this.campoAnio,
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