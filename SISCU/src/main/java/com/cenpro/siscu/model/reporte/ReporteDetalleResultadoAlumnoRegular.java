package com.cenpro.siscu.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReporteDetalleResultadoAlumnoRegular
{
    private String codigoAlumno;
    private String tipoAlumno;
    private String descripcionTipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String idSexo;
    private String descripcionSexo;
    private int edad;
    private Integer codigoFacultad;
    private String descripcionFacultad;
    private Integer codigoEscuela;
    private String descripcionEscuela;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaAtencionExamenLaboratorio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaAtencionExamenRadiologico;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaAtencionExamenPsicologico;
    private Double hemoglobina;
    private String descripcionResultadoHemoglobina;
    private String idRPR;
    private String descripcionRPR;
    private String dilucion;
    private String idHemograma;
    private String descripcionHemograma;
    private String idResultadoRadiologico;
    private String descripcionResultadoRadiologico;
    private String idResultadoPsicologico;
    private String descripcionResultadoPsicologico;
}