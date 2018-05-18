package com.cenpro.siscu.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteAtencionDiaria
{
    private Integer numeroRegistro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaGeneracionNumeroRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date soloFechaGeneracionNumeroRegistro;
    
    private String codigoAlumno;
    private String tipoAlumno;
    private String descripcionTipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private Integer codigoFacultad;
    private String descripcionFacultad;
    private String descripcionEscuela;
    private Integer idCampania;
    private int cantidad;
}