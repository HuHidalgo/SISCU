package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IExamenMedicoPsicologicoService;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.NumRegPsicologico;

public class NumRegPsicologicoValidator implements ConstraintValidator<NumRegPsicologico, Integer>
{
    private int min;
    private boolean existe;

    private @Autowired IExamenMedicoPsicologicoService examenMedicoPsicologicoService;

    @Override
    public void initialize(NumRegPsicologico anotacion)
    {
        this.min = anotacion.min();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer numeroRegistro, ConstraintValidatorContext context)
    {
        if (numeroRegistro == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ExamenMedicoLaboratorio.numeroRegistro}",
                    context);
            return false;
        }
        if (numeroRegistro < min)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Min.ExamenPsicologico.numeroRegistro}",
                    context);
            return false;
        }
        boolean existeExamenMedicoPsicologico = examenMedicoPsicologicoService
                .existeExamenMedicoPsicologico(numeroRegistro);
        return (existe) ? existeExamenMedicoPsicologico : !existeExamenMedicoPsicologico;
    }
}