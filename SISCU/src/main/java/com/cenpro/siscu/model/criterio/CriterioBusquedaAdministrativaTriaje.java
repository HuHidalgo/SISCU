package com.cenpro.siscu.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaAdministrativaTriaje
{
    private Integer numeroRegistro;
    private String anio;
    private String codigoAlumno;
    private String tipoAlumno;
    private String idEstadoExamenMedico;
    private Integer idCampania;
    private Integer codigoFacultad;
    private Integer codigoEscuela;

    private Integer idRangoPeso;
    private Integer idRangoTalla;
    private Integer idRangoPulso;
    private Integer idPresionSistolica;
    private Integer idPresionDiastolica;

    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
}