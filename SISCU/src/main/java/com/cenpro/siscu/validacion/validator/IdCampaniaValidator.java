package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.ICampaniaService;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.IdCampania;

public class IdCampaniaValidator implements ConstraintValidator<IdCampania, Integer>
{
    private int min;
    private int max;
    private boolean existe;
    
    private @Autowired ICampaniaService campaniaService;

    @Override
    public void initialize(IdCampania anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer idCampania, ConstraintValidatorContext context)
    {
        if (idCampania == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Campania.idCampania}", context);
            return false;
        }
        if (idCampania < min || idCampania > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Campania.idCampania}", context);
            return false;
        }
        boolean existeCampania = campaniaService.existeCampania(idCampania);
        return (existe ? existeCampania : !existeCampania);
    }
}