package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.MultitabDet;

public class MultitabDetValidator implements ConstraintValidator<MultitabDet, Object>
{
    private int min;
    private int max;
    private boolean existe;
    private int idTabla;
    private String campoIdItem;

    private @Autowired IMultiTabDetService multiTabDetService;

    @Override
    public void initialize(MultitabDet anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
        this.idTabla = anotacion.idTabla();
        this.campoIdItem = anotacion.campoIdItem();
    }

    @Override
    public boolean isValid(Object oIdItem, ConstraintValidatorContext context)
    {
        String idItem = String.valueOf(oIdItem);
        if (idItem == null || idItem.equals("null"))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.MultitabDet." + campoIdItem + "}",
                    context);
            return false;
        }
        if (idItem.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.MultitabDet." + campoIdItem + "}",
                    context);
            return false;
        }
        if (idItem.length() < min || idItem.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.MultitabDet." + campoIdItem + "}",
                    context);
            return false;
        }
        boolean existeItem = !multiTabDetService.buscarPorIdTablaIdItem(idTabla, idItem).isEmpty();
        if (existe && !existeItem)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NoExiste.MultitabDet." + campoIdItem + "}",
                    context);
            return false;
        }
        if (!existe && existeItem)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Existe.MultitabDet." + campoIdItem + "}",
                    context);
            return false;
        }
        return true;
    }

}