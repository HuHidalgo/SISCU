package com.cenpro.siscu.model.criterio;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaEnvioCorreo
{
    private String idProcesoAutomatico;
    private String idDestinoEnvio;
    private Integer codigoFacultad;
    private String codigoFacultadesEnvio;
    private Integer idCampania;
    private String descripcionCampania;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcionFechaEnvio;
}