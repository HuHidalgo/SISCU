package com.cenpro.siscu.model.carga;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carga
{
    private List<ErrorCarga> errorCarga;
    private Integer totalRegistros;
}