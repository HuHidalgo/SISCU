package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IAlumnoService;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.CodigoAlumno;

public class CodigoAlumnoValidator implements ConstraintValidator<CodigoAlumno, Object>
{
    private boolean existe;
    private String campoTipoAlumno;
    private String campoCodigoAlumno;

    private @Autowired IAlumnoService alumnoService;

    @Override
    public void initialize(CodigoAlumno anotacion)
    {
        this.campoTipoAlumno = anotacion.campoTipoAlumno();
        this.campoCodigoAlumno = anotacion.campoCodigoAlumno();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String tipoAlumno = BeanUtils.getProperty(dto, this.campoTipoAlumno);
            String codigoAlumno = BeanUtils.getProperty(dto, this.campoCodigoAlumno);

            boolean existeAlumno = alumnoService.existeAlumno(codigoAlumno, tipoAlumno);
            if (existe && !existeAlumno)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Alumno.codigoAlumno}", this.campoCodigoAlumno, context);
                return false;
            }
            if (!existe && existeAlumno)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.Alumno.codigoAlumno}", this.campoCodigoAlumno, context);
                return false;
            }
        } catch (Exception e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodigoAlumno, context);
            return false;
        }
        return true;
    }
}