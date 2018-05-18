package com.cenpro.siscu.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Escuela
{
    private Integer codigoEscuela;
    private String descripcion;
    private Integer codigoFacultad;
    private String descripcionFacultad;
}