package com.cenpro.siscu.model.movimiento;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cenpro.siscu.utilitario.MultiTablaUtil;
import com.cenpro.siscu.validacion.CodigoAlumno;
import com.cenpro.siscu.validacion.IdCampania;
import com.cenpro.siscu.validacion.MultitabDet;
import com.cenpro.siscu.validacion.NumRegRadiologico;
import com.cenpro.siscu.validacion.grupo.IGeneracionNumReg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoAlumno(existe = true, groups = IGeneracionNumReg.class)
public @NumRegRadiologico class ExamenMedicoRadiologico
{
    private Integer numeroRegistro;
    private String anio;

    @MultitabDet(campoIdItem = "idResultadoRadiologico", idTabla = MultiTablaUtil.TABLA_RESULTADO_RADIOLOGICO, existe = true, min = 1, max = 1)
    private String idResultadoRadiologico;

    @Length(max = 250, message = "{Length.ExamenMedicoRadiologico.observacion}")
    private String observacion;
    
    @IdCampania(existe = true, groups = IGeneracionNumReg.class)
    private Integer idCampania;
    private String codigoAlumno;
    private String tipoAlumno;
    private String idEstadoExamenMedico;

    // DATOS ADICIONALES
    private String descripcionTipoAlumno;
    private String descripcionResultadoRadiologico;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private int edad;
    private String idSexo;
    private String descripcionSexo;
    private String descripcionEscuela;
    private String descripcionCampania;
    private String descripcionEstadoExamenMedico;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaGeneracionNumeroRegistro;
}