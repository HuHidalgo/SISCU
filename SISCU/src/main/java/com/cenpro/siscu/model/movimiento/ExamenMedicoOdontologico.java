package com.cenpro.siscu.model.movimiento;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cenpro.siscu.utilitario.MultiTablaUtil;
import com.cenpro.siscu.validacion.CodigoAlumno;
import com.cenpro.siscu.validacion.IdCampania;
import com.cenpro.siscu.validacion.MultitabDet;
import com.cenpro.siscu.validacion.grupo.accion.IRegistro;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoAlumno(existe = true, groups = IRegistro.class)
public class ExamenMedicoOdontologico
{
    private Integer numeroRegistro;
    private String anio;

    /* Ingresantes */
    @NotNull(message = "{NotNull.ExamenMedicoOdontologico.indiceMasticacion}")
    @Range(min = 0, max = 100, message = "{Range.ExamenMedicoOdontologico.indiceMasticacion}")
    private Integer indiceMasticacion;

    @MultitabDet(campoIdItem = "idMaloclusion", idTabla = MultiTablaUtil.TABLA_MALOCLUSION, existe = true, min = 1, max = 5)
    private String idMaloclusion;

    @MultitabDet(campoIdItem = "idHigiene", idTabla = MultiTablaUtil.TABLA_HIGIENE, existe = true, min = 1, max = 5)
    private String idHigiene;

    @MultitabDet(campoIdItem = "idProtesis", idTabla = MultiTablaUtil.TABLA_PROTESIS, existe = true, min = 1, max = 5)
    private String idProtesis;

    @NotNull(message = "{NotNull.ExamenMedicoOdontologico.piezasFaltantes}")
    @Range(min = 0, max = 32, message = "{Range.ExamenMedicoOdontologico.piezasFaltantes}")
    private Integer piezasFaltantes;

    @NotNull(message = "{NotNull.ExamenMedicoOdontologico.piezasObturadas}")
    @Range(min = 0, max = 32, message = "{Range.ExamenMedicoOdontologico.piezasObturadas}")
    private Integer piezasObturadas;

    @NotNull(message = "{NotNull.ExamenMedicoOdontologico.piezasPorObturar}")
    @Range(min = 0, max = 32, message = "{Range.ExamenMedicoOdontologico.piezasPorObturar}")
    private Integer piezasPorObturar;

    @NotNull(message = "{NotNull.ExamenMedicoOdontologico.piezasPorExtraer}")
    @Range(min = 0, max = 32, message = "{Range.ExamenMedicoOdontologico.piezasPorExtraer}")
    private Integer piezasPorExtraer;

    @Length(max = 250, message = "{Length.ExamenMedicoOdontologico.observacion}")
    private String observacion;

    @IdCampania(existe = true, groups = IRegistro.class)
    private Integer idCampania;
    private String codigoAlumno;
    private String tipoAlumno;
    
    private String idEstadoExamenMedico;

    /* Datos Adicionales */
    private String descripcionTipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private int edad;
    private String idSexo;
    private String descripcionSexo;
    private String descripcionFacultad;
    private String descripcionEscuela;
    private String descripcionCampania;

    private String descripcionEstadoExamenMedico;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaGeneracionNumeroRegistro;
}