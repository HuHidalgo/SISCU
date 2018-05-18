package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IPersonaService;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.NumeroDocumento;

public class NumeroDocumentoValidator implements ConstraintValidator<NumeroDocumento, Object>
{
    private boolean existe;
    private String campoNumeroDocumento;
    private String campoIdTipoDocumento;

    private @Autowired IPersonaService personaService;

    @Override
    public void initialize(NumeroDocumento anotacion)
    {
        this.campoNumeroDocumento = anotacion.campoNumeroDocumento();
        this.campoIdTipoDocumento = anotacion.campoIdTipoDocumento();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String numeroDocumento = BeanUtils.getProperty(dto, this.campoNumeroDocumento);
            String idTipoDocumento = BeanUtils.getProperty(dto, this.campoIdTipoDocumento);
            
            boolean existePersona = personaService.existePersona(idTipoDocumento, numeroDocumento);
            if (existe && !existePersona)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Persona.numeroDocumento}", this.campoNumeroDocumento, context);
                return false;
            }
            if (!existe && existePersona)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.Persona.numeroDocumento}", this.campoNumeroDocumento, context);
                return false;
            }
        } catch (Exception e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoNumeroDocumento, context);
            return false;
        }
        return true;
    }
}