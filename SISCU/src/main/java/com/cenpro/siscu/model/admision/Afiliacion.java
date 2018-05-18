package com.cenpro.siscu.model.admision;

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
public class Afiliacion
{
	//Datos afiliacion
	private Integer numeroRegistro;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String tipoDocumento;
    private String nroDocumento;
    private String idSexo;
    private String sexo;
    private String idEstadoCivil;
    private String estadoCivil;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaNacimiento;
    private Integer edad;
    private Integer idDistritoNac;
    private String distritoNac;
    private Integer idDepartamentoNac;
    private String departamentoNac;
    private Integer idGradoInstruccion;
    private String gradoInstruccion;
    private String religion;
    private String ocupacionActual;
    private String direccionActual;
    private String distritoProcedencia;
    private String telefonoFijo;
    private String celular;
    private String correo;
      
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaRegistroHC;
    
    //Datos tipo de paciente
    private String codigoAlumno;
    private String codigoEscuela;
    private String descripcionEscuela;
    private String codigoFacultad;
    private String descripcionFacultad;
    
    //No docente
    private String areaTrabajo;
    
    //Datos de persona en caso de emergencia
    private String nombreEmerg;
    private String direccionEmerg;
    private String telefonoEmerg;
}