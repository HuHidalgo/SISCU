package com.cenpro.siscu.model.reporte;

import lombok.Data;

@Data
public class ReporteExamenMedicoRegular
{
    private String codigoAlumno;
    private String tipoAlumno;
    private String descripcionTipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String descripcionEscuela;
    private Double hemoglobina;
    private String descripcionResultadoHemoglobina;
    private String idRPR;
    private String descripcionRPR;
    private String idHemograma;
    private String descripcionHemograma;
    private String idResultadoRadiologico;
    private String descripcionResultadoRadiologico;
    private String idResultadoPsicologico;
    private String descripcionResultadoPsicologico;
}