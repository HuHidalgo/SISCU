package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.IdEspecialidadValidator;

@Documented
@Constraint(validatedBy = IdEspecialidadValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdEspecialidad.List.class)
public @interface IdEspecialidad
{
    String message() default "{NoExiste.Especialidad.idEspecialidad}";

    boolean existe();

    int min() default 1;

    int max() default 1000;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdEspecialidad[] value();
    }
}