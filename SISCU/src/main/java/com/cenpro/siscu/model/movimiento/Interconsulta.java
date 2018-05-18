package com.cenpro.siscu.model.movimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.cenpro.siscu.validacion.IdEspecialidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interconsulta
{
    private int secuencia;

    @IdEspecialidad(existe = true)
    private Integer idEspecialidad;

    @NotNull(message = "{NotNull.Interconsulta.motivo}")
    @NotBlank(message = "{NotBlank.Interconsulta.motivo}")
    @Length(min = 3, max = 400, message = "{Length.Interconsulta.motivo}")
    private String motivo;
    
    /* Dato de Medicina General */
    private Integer numeroRegistro;
    private String anio;
}