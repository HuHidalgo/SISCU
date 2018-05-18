package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.CodigoAlumnoValidator;

@Documented
@Constraint(validatedBy = CodigoAlumnoValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CodigoAlumno.List.class)
public @interface CodigoAlumno
{
    String message() default "{NoExiste.Alumno.codigoAlumno}";

    boolean existe();
    
    String campoCodigoAlumno() default "codigoAlumno";
    
    String campoTipoAlumno() default "tipoAlumno";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        CodigoAlumno[] value();
    }
}