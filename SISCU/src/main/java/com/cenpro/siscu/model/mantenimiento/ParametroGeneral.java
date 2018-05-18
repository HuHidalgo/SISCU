package com.cenpro.siscu.model.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametroGeneral
{
    private int tiempoReprogramacion;
    private String correoSUM;
    private String correoClinica;
    private String anioInicial;
}