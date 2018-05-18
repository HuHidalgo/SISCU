package com.cenpro.siscu.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.cenpro.siscu.service.IUsuarioService;
import com.cenpro.siscu.utilitario.Regex;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.CodigoUsuario;

public class CodigoUsuarioValidator implements ConstraintValidator<CodigoUsuario, String>
{
    private int min;
    private int max;
    private boolean existe;
    private @Autowired IUsuarioService usuarioService;

    @Override
    public void initialize(CodigoUsuario anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String idUsusario, ConstraintValidatorContext context)
    {
        if (idUsusario == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Usuario.idUsusario}", context);
            return false;
        }
        if (idUsusario.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Usuario.idUsusario}", context);
            return false;
        }
        if (!idUsusario.matches(Regex.ALFANUMERICO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Usuario.idUsusario}", context);
            return false;
        }
        if (idUsusario.length() < min || idUsusario.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Usuario.idUsusario}", context);
            return false;
        }
        boolean existeUsuario = usuarioService.existeUsuario(idUsusario);
        return (existe) ? existeUsuario : !existeUsuario;
    }
}