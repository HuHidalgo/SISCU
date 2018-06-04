package com.cenpro.siscu.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaEstamento
{
	private Integer idEstamento;
    //private String nombres;
    //private String apellidoPaterno;
    //private String codigoAlumno;
    private String nroDocumento;
    private String tipoDocumento;
}