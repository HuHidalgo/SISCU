package com.cenpro.siscu.model.movimiento;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cenpro.siscu.utilitario.MultiTablaUtil;
import com.cenpro.siscu.validacion.CodigoAlumno;
import com.cenpro.siscu.validacion.Dilucion;
import com.cenpro.siscu.validacion.IdCampania;
import com.cenpro.siscu.validacion.MultitabDet;
import com.cenpro.siscu.validacion.NumRegLaboratorio;
import com.cenpro.siscu.validacion.grupo.IGeneracionNumReg;
import com.cenpro.siscu.validacion.grupo.alumno.IIngresante;
import com.cenpro.siscu.validacion.grupo.alumno.IRegular;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoAlumno(existe = true, groups = IGeneracionNumReg.class)
@Dilucion(groups = { IIngresante.class, IRegular.class })
@NumRegLaboratorio(groups = { IIngresante.class, IRegular.class })
public class ExamenMedicoLaboratorio
{
    private Integer numeroRegistro;
    private String anio;

    /* Regulares e Ingresantes */
    @MultitabDet(campoIdItem = "idRPR", idTabla = MultiTablaUtil.TABLA_RPR, existe = true, min = 1, max = 1, groups = {
            IIngresante.class, IRegular.class })
    private String idRPR;
    private String dilucion;

    /* Regulares */
    @MultitabDet(campoIdItem = "idHemograma", idTabla = MultiTablaUtil.TABLA_HEMOGRAMA, existe = true, min = 1, max = 1, groups = IRegular.class)
    private String idHemograma;

    @NotNull(message = "{NotNull.ExamenMedicoLaboratorio.hemoglobina}", groups = IRegular.class)
    @Digits(integer = 4, fraction = 2, message = "{Digits.ExamenMedicoLaboratorio.hemoglobina}", groups = IRegular.class)
    @Range(min = 1, max = 30, message = "{Range.ExamenMedicoLaboratorio.hemoglobina}", groups = IRegular.class)
    private Double hemoglobina;

    @Length(max = 250, message = "{Length.ExamenMedicoLaboratorio.observacion}", groups = IRegular.class)
    private String observacion;

    /* Ingresantes */
    @MultitabDet(campoIdItem = "idGrupoSanguineo", idTabla = MultiTablaUtil.TABLA_GRUPO_SANGUINEO, existe = true, min = 1, max = 2, groups = IIngresante.class)
    private String idGrupoSanguineo;

    @MultitabDet(campoIdItem = "idFactorRh", idTabla = MultiTablaUtil.TABLA_FACTOR_RH, existe = true, min = 1, max = 1, groups = IIngresante.class)
    private String idFactorRh;

    @IdCampania(existe = true, groups = IGeneracionNumReg.class)
    private Integer idCampania;
    private String codigoAlumno;
    private String tipoAlumno;
    private String idEstadoExamenMedico;

    // DATOS ADICIONALES
    private String descripcionTipoAlumno;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private int edad;
    private String idSexo;
    private String descripcionSexo;
    private String descripcionFacultad;
    private String descripcionEscuela;
    private String descripcionRPR;
    private String descripcionHemograma;
    private String descripcionCampania;
    private String descripcionEstadoExamenMedico;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaGeneracionNumeroRegistro;
}