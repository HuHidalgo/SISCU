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
public class CriterioBusquedaAdministrativaExamenMedicoLaboratorio
{
    private Integer numeroRegistro;
    private String anio;
    private String codigoAlumno;
    private String tipoAlumno;
    private String idEstadoExamenMedico;
    private Integer idCampania;
    private Integer codigoFacultad;
    private Integer codigoEscuela;

    private String idRPR;
    private String dilucion;
    private String idHemograma;
    private Double hemoglobina;
    private String idGrupoSanguineo;
    private String idFactorRh;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
}