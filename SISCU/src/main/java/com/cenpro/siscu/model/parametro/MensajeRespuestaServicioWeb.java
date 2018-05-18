package com.cenpro.siscu.model.parametro;

import lombok.Data;

@Data
public class MensajeRespuestaServicioWeb
{
    private int codigoRespuesta;
    private int cantidadRegistros;
    private String descripcion;
}