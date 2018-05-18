package com.cenpro.siscu.model.movimiento;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cenpro.siscu.validacion.CodigoAlumno;
import com.cenpro.siscu.validacion.IdCampania;
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
public class ExamenMedicoTriaje
{
    private Integer numeroRegistro;
    private String anio;

    /* Datos de triaje */
    @NotNull(message = "{NotNull.ExamenMedicoTriaje.peso}")
    @Digits(integer = 5, fraction = 2, message = "{Digits.ExamenMedicoTriaje.peso}")
    @Range(min = 30, max = 200, message = "{Range.ExamenMedicoTriaje.peso}")
    private Double peso;
    
    @NotNull(message = "{NotNull.ExamenMedicoTriaje.talla}")
    @Digits(integer = 5, fraction = 2, message = "{Digits.ExamenMedicoTriaje.talla}")
    @Range(min = 40, max = 300, message = "{Range.ExamenMedicoTriaje.peso}")
    private Double talla;
    
    @NotNull(message = "{NotNull.ExamenMedicoTriaje.pulso}")
    @Range(min = 50, max = 200, message = "{Range.ExamenMedicoTriaje.pulso}")
    private Integer pulso;
    
    @NotNull(message = "{NotNull.ExamenMedicoTriaje.presionSistolica}")
    @Range(min = 60, max = 200, message = "{Range.ExamenMedicoTriaje.pulso}")
    private Integer presionSistolica;
    
    @NotNull(message = "{NotNull.ExamenMedicoTriaje.presionDiastolica}")
    @Range(min = 40, max = 200, message = "{Range.ExamenMedicoTriaje.pulso}")
    private Integer presionDiastolica;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fum;

    @IdCampania(existe = true, groups = IRegistro.class)
    private Integer idCampania;
    private String codigoAlumno;
    private String tipoAlumno;
    private String idEstadoExamenMedico;
    
    /* Datos propios de adicionales */
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