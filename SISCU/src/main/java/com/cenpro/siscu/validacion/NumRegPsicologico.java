package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.NumRegPsicologicoValidator;

@Documented
@Constraint(validatedBy = NumRegPsicologicoValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NumRegPsicologico.List.class)
public @interface NumRegPsicologico
{
    String message() default "{NoExiste.ExamenPsicologico.numeroRegistro}";

    boolean existe();

    int min() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        NumRegPsicologico[] value();
    }
}
