package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.ICie10Service;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.IdCie10;

public class IdCie10Validator implements ConstraintValidator<IdCie10, String>
{
    private int min;
    private int max;
    private boolean existe;
    private @Autowired ICie10Service cie10Service;

    @Override
    public void initialize(IdCie10 anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String idCie10, ConstraintValidatorContext context)
    {
        if (idCie10 == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Cie10.idCie10}", context);
            return false;
        }
        if (idCie10.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Cie10.idCie10}", context);
            return false;
        }
        if (idCie10.length() < min || idCie10.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Cie10.idCie10}", context);
            return false;
        }
        boolean existeCie10 = cie10Service.existeCie10(idCie10);
        return (existe ? existeCie10 : !existeCie10);
    }
}