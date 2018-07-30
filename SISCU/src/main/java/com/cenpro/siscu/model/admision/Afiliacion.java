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
	private Integer idAfiliacion;
	private Integer idEstamento;
	private String descEstamento;
	private Integer numeroRegistro;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String idTipoDocumento;
    private String numeroDocumento;
    private String idSexo;
    private String sexo;
    private Integer idEstadoCivil;
    private String descEstadoCivil;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaNacimiento;
    private Integer edad;
    private String idDistritoNac;
    private String distritoNac;
    private String idDepartamentoNac;
    private String departamentoNac;
    private Integer idGradoInstruccion;
    private Integer gradoInstruccion;
    private String religion;
    private String ocupacionActual;
    private String direccionActual;
    private String distritoProcedencia;
    private String telefonoFijo;
    private String celular;
    private String correo;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "EST")
    private Date fechaAfiliacion;
      
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date fechaRegistroHC;
    
    //Datos tipo de paciente
    private String codigoAlumno;
    private Integer codigoEscuela;
    private String descripcionEscuela;
    private Integer codigoFacultad;
    private String descripcionFacultad;
    
    //No docente
    private String areaTrabajo;
    
    //Datos de persona en caso de emergencia
    private String nombreEmerg;
    private String direccionEmerg;
    private String telefonoEmerg;
}