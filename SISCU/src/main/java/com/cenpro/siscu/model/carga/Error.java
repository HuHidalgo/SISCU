package com.cenpro.siscu.model.carga;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error
{
    private Integer fila;
    private String nombreColumna;
}