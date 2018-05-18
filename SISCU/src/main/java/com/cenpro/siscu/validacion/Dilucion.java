package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.DilucionValidator;

@Documented
@Constraint(validatedBy = DilucionValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Dilucion.List.class)
public @interface Dilucion
{
    String message() default "{NoExiste.ExamenMedicoLaboratorio.dilucion}";

    String campoRPR() default "idRPR";

    String campoDilucion() default "dilucion";

    int maxDilucion() default 3;

    int minDilucion() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        Dilucion[] value();
    }
}
