package com.cenpro.siscu.model.carga;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.cenpro.siscu.utilitario.MultiTablaUtil;
import com.cenpro.siscu.utilitario.Regex;
//import com.cenpro.siscu.validacion.CodigoAlumno;
import com.cenpro.siscu.validacion.MultitabDet;
import com.cenpro.siscu.validacion.grupo.ILlave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@CodigoAlumno(existe = true, groups = IActualizacion.class)
//@CodigoAlumno(existe = false, groups = IRegistro.class)
public class Cliente
{
    // DATO PER
    @MultitabDet(campoIdItem = "idTipoDocumento", idTabla = MultiTablaUtil.TABLA_TIPO_DOCUMENTO, min = 1, max = 5)
    private String idTipoDocumento;

    @NotNull(message = "{NotNull.Persona.numeroDocumento}")
    @NotBlank(message = "{NotBlank.Persona.numeroDocumento}")
    @Length(min = 5, max = 12, message = "{Length.Persona.numeroDocumento}")
    private String numeroDocumento;

    @NotNull(message = "{NotNull.Persona.nombres}")
    @NotBlank(message = "{NotBlank.Persona.nombres}")
    @Length(min = 3, max = 100, message = "{Length.Persona.nombres}")
    private String nombres;

    @NotNull(message = "{NotNull.Persona.apellidoPaterno}")
    @NotBlank(message = "{NotBlank.Persona.apellidoPaterno}")
    @Length(min = 3, max = 100, message = "{Length.Persona.apellidoPaterno}")
    private String apellidoPaterno;

    @NotNull(message = "{NotNull.Persona.apellidoMaterno}")
    @NotBlank(message = "{NotBlank.Persona.apellidoMaterno}")
    @Length(min = 3, max = 100, message = "{Length.Persona.apellidoMaterno}")
    private String apellidoMaterno;

    @MultitabDet(campoIdItem = "idSexo", idTabla = MultiTablaUtil.TABLA_SEXO, min = 1, max = 1)
    private String idSexo;

    @NotNull(message = "{NotNull.Alumno.direccion}")
    @NotBlank(message = "{NotBlank.Alumno.direccion}")
    @Length(min = 3, max = 200, message = "{Length.Alumno.direccion}")
    private String direccion;

    @Length(min = 3, max = 50, message = "{Length.Alumno.correoPersonal}")
    @Email(message = "{Email.Alumno.correoPersonal}")
    private String correo;

    @Length(min = 5, max = 20, message = "{Length.Alumno.telefonoFijo}")
    @Pattern(regexp = Regex.SOLO_DIGITOS, message = "{Pattern.Alumno.telefonoFijo}")
    private String telefonoFijo;

    @Length(min = 5, max = 20, message = "{Length.Alumno.telefonoMovil}")
    @Pattern(regexp = Regex.SOLO_DIGITOS, message = "{Pattern.Alumno.telefonoMovil}")
    private String telefonoMovil;

    @NotNull(message = "{NotNull.Alumno.codigoFacultad}")
    @Range(min = 1, max = 99, message = "{Range.Alumno.codigoFacultad}")
    private Integer codigoFacultad;

    @NotNull(message = "{NotNull.Alumno.codigoEscuela}")
    @Range(min = 1, max = 99, message = "{Range.Alumno.codigoEscuela}")
    private Integer codigoEscuela;

 // DATO ALUMNO
    @NotNull(message = "{NotNull.Alumno.codigoAlumno}", groups = ILlave.class)
    @NotBlank(message = "{NotBlank.Alumno.codigoAlumno}", groups = ILlave.class)
    @Length(min = 6, max = 8, message = "{Length.Alumno.codigoAlumno}", groups = ILlave.class)
    private String codigoAlumno;

    @MultitabDet(campoIdItem = "tipoAlumno", idTabla = MultiTablaUtil.TABLA_TIPO_ALUMNO, min = 1, max = 1, groups = ILlave.class)
    private String tipoAlumno;
    
    private int edad;
	private Integer idEstamento;
    private String descripcionFacultad;
    private String descripcionSexo;
    private String descripcionTipoDocumento;
    private String descripcionEscuela;
    private String descripcionTipoAlumno;

    @NotNull(message = "{NotNull.Alumno.fechaNacimiento}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaNacimiento;
}