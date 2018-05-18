package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.MultiTablaUtil;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.Dilucion;

public class DilucionValidator implements ConstraintValidator<Dilucion, Object>
{
    private String campoRPR;
    private String campoDilucion;
    private int maxDilucion;
    private int minDilucion;

    private @Autowired IMultiTabDetService multiTabDetService;

    @Override
    public void initialize(Dilucion anotacion)
    {
        this.campoRPR = anotacion.campoRPR();
        this.campoDilucion = anotacion.campoDilucion();
        this.maxDilucion = anotacion.maxDilucion();
        this.minDilucion = anotacion.minDilucion();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String idRPR = BeanUtils.getProperty(dto, this.campoRPR);
            String dilucion = BeanUtils.getProperty(dto, this.campoDilucion);
            if (idRPR != null && idRPR.equals(ConstantesGenerales.RPR_REACTIVO))
            {
                if (dilucion == null || dilucion.equals("null"))
                {
                    ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                            "{NotNull.MultitabDet.dilucion}", this.campoDilucion, context);
                    return false;
                }
                if (dilucion.trim().isEmpty())
                {
                    ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                            "{NotBlank.MultitabDet.dilucion}", this.campoDilucion, context);
                    return false;
                }
                if (dilucion.length() < minDilucion || dilucion.length() > maxDilucion)
                {
                    ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                            "{Length.MultitabDet.dilucion}", this.campoDilucion, context);
                    return false;
                }
                boolean existe = !multiTabDetService
                        .buscarPorIdTablaIdItem(MultiTablaUtil.TABLA_DILUCION, dilucion).isEmpty();
                if (!existe)
                {
                    ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                            "{NoExiste.MultitabDet.dilucion}", this.campoDilucion, context);
                    return false;
                }
                return true;
            }
            return true;
        } catch (Exception e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoDilucion, context);
            e.printStackTrace();
            return false;
        }
    }
}