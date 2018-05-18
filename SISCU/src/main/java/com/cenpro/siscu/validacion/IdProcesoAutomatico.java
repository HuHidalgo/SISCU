package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.IdProcesoAutomaticoValidator;

@Documented
@Constraint(validatedBy = IdProcesoAutomaticoValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IdProcesoAutomatico.List.class)
public @interface IdProcesoAutomatico
{
    String message() default "{NoExiste.ProcesoAutomatico.idProcesoAutomatico}";

    boolean existe();

    int min() default 1;

    int max() default 10;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        IdProcesoAutomatico[] value();
    }
}