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
public class CriterioBusquedaReporteAtencionDiaria
{
    private String verbo;
    private String idTipoExamenMedico;
    private String tipoReporte;
    private Integer idCampania;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    
    private String descripcionExamenMedico;
    private String descripcionCampania;
    private String descripcionFechaGeneracionNumeroRegistro;
}