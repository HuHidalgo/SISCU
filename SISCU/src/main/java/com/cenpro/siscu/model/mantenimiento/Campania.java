package com.cenpro.siscu.model.mantenimiento;

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
public class Campania
{
    private Integer idCampania;
    private String descripcion;
    private String descripcionCorta;
    private boolean activo;
    private String idTipoCertificado;
    private String descripcionTipoCertificado;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaEjecucion;
}