package com.cenpro.siscu.model.consulta.administrativa;

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
public class ConsultaPsicologicoAdministrativo
{
    private Integer numeroRegistro;
    private String codigoAlumno;
    private String tipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String descripcionTipoAlumno;
    private String idEstadoExamenMedico;
    private String descripcionEstadoExamenMedico;
    private Integer idCampania;
    private String descripcionCampania;
    private String codigoFacultad;
    private String codigoEscuela;
    private String descripcionEscuela;
    private String idSexo;
    private String descripcionSexo;
    private String idResultadoPsicologico;
    private String descResultadoPsicologico;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaRegistro;
}
