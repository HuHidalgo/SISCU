package com.cenpro.siscu.model.consulta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaAtencionInicial
{
    private String codigoAlumno;
    private String tipoAlumno;
    private String descripcionTipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private int edad;
    private String idSexo;
    private String descripcionSexo;
    private String descripcionFacultad;
    private String descripcionEscuela;
    private Integer numeroRegistro;
}