package com.cenpro.siscu.model.logEnvio;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogEnvioCorreoDependencia
{
    private Integer secuencia;
    private Integer codigoDependencia;
    private Date fechaEnvio;
    private Integer idCampania;
    private boolean exito;
    private String mensaje;
    private String horaInicio;
    private String horaFin;
    private Integer idSecuenciaProcesoAutomatico;
    private String idProcesoAutomatico;
    private String idDestinoEnvio;
    private String correoEnvio;
}