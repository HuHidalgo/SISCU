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
public class CriterioBusquedaAdministrativaExamenMedicoPsicologico
{
    private Integer numeroRegistro;
    private String codigoAlumno;
    private String tipoAlumno;
    private Integer idCampania;
    private Integer codigoFacultad;
    private Integer codigoEscuela;
    private String idResultadoPsicologico;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
}