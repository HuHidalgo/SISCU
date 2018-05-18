package com.cenpro.siscu.model.seguridad;

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
public class Auditoria
{
    private Integer idAuditoria;
    private Integer idTipoAuditoria;
    private String descripcionTipoAuditoria;
    private String codigoAuditoria;    
    private String idAccion;
    private String descripcionAccion;
    private String direccionIp;
    private boolean exito;
    private String comentario;
    private String nombreUsuario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fecha;
    private String hora;
}