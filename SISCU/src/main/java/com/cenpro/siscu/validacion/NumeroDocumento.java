package com.cenpro.siscu.validacion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cenpro.siscu.validacion.validator.NumeroDocumentoValidator;

@Documented
@Constraint(validatedBy = NumeroDocumentoValidator.class)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NumeroDocumento.List.class)
public @interface NumeroDocumento
{
    String message() default "{NoExiste.Persona.numeroDocumento}";

    boolean existe();
    
    String campoNumeroDocumento() default "numeroDocumento";
    
    String campoIdTipoDocumento() default "idTipoDocumento";
    
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List
    {
        NumeroDocumento[] value();
    }
}