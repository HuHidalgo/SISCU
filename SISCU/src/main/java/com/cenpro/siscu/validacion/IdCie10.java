package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.IdCie10Validator;

@Documented
@Constraint(validatedBy = IdCie10Validator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdCie10.List.class)
public @interface IdCie10
{
    String message() default "{NoExiste.Cie10.idCie10}";

    boolean existe();

    int min() default 1;

    int max() default 8;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdCie10[] value();
    }
}
