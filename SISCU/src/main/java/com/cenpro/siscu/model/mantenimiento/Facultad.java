package com.cenpro.siscu.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facultad
{
    private Integer codigoFacultad;
    private String descripcion;
    private String correoAsistenta;
    private String correoFacultad;
}