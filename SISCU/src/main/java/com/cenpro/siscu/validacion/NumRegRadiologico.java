package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.NumRegRadiologicoValidator;

@Documented
@Constraint(validatedBy = NumRegRadiologicoValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NumRegRadiologico.List.class)
public @interface NumRegRadiologico
{
    String message() default "{NoExiste.ExamenMedicoRadiologico.numeroRegistro}";
    
    String campoAnio() default "anio";
    
    String campoNumeroRegistro() default "numeroRegistro";

    int tamanioAnio() default 4;
    
    int minNumeroRegistro() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        NumRegRadiologico[] value();
    }
}
