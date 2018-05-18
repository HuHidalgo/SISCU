package com.cenpro.siscu.model.criterio;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaReporteDetalleResultadoAlumno
{
    private String codigoAlumno;
    private Integer codigoFacultad;
    private Integer codigoEscuela;
    
    @NotNull(message = "{NotNull.CriterioBusquedaDetalleResultadoAlumno.idCampania}")
    private Integer idCampania;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
}