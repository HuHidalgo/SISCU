package com.cenpro.siscu.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cie10
{
    private String idCie10;
    private String descripcion;
    private String grupo;
}