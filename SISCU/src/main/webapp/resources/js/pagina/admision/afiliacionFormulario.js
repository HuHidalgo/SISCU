$(document).ready(function() {

	$formCriterioConsultaAdmision.validate({
		focusCleanup : true,
		rules : {
			idEstamento : {
				required : true
			},
			tipoDocumento : {
				required : true
			},
			nroDocumento : {
				required : true
			}
		},
		messages : {
			idEstamento : {
				required : "Seleccione un Estamento."
			},
			tipoDocumento : {
				required : "Ingrese el Tipo de Documento."
			},
			nroDocumento : {
				required : "Ingrese Número de Documento."
			}
		}
	});
	
	$formAfiliacion.validate({
		focusCleanup : true,
		rules : {
			idTipoDocumento : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			numeroDocumento : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 5, 12 ]
			},
			apellidoPaterno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			apellidoMaterno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			nombres : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			codigoAlumno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 6, 8 ]
			},
			idEstadoCivil : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			direccionActual : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 70 ]
			},
			codigoFacultad : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			codigoEscuela : {
				required : true,
				digits : true,
				range : [ 0, 99 ]
			},
			correo : {
				notOnlySpaceOrEmpty : true,
				email: true,
				maxlength : 50
			},
			distritoProcedencia : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			telefonoFijo : {
				notOnlySpaceOrEmpty : true,
				digits : true,
				maxlength : 13
			},
			celular : {
				notOnlySpaceOrEmpty : true,
				digits : true,
				maxlength : 13
			},
			ocupacionActual : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			}
		},
		messages : {
			idTipoDocumento : {
				required : "Seleccione el Tipo de Documento.",
				notOnlySpace : "El Tipo de Documento no puede contener solo espacios en blanco.",
				selectlength : "El Tipo de Documento debe contener entre 1 y 4 car&aacute;cteres"
			},
			numeroDocumento : {
				required : "Ingrese el N&uacute;mero de Documento.",
				notOnlySpace : "El N&uacute;umero de Documento no puede contener solo espacios en blanco.",
				rangelength : "El N&uacute;umero de Documento debe contener entre 3 y 12 car&aacute;cteres."
			},
			apellidoPaterno : {
				required : "Ingrese el Apellido Paterno.",
				notOnlySpace : "El Apellido Paterno no puede contener solo espacios en blanco.",
				rangelength : "El Apellido Paterno debe contener entre 3 y 100 car&aacute;cteres."
			},
			apellidoMaterno : {
				required : "Ingrese el Apellido Materno.",
				notOnlySpace : "El Apellido Materno no puede contener solo espacios en blanco.",
				rangelength : "El Apellido Materno debe contener entre 3 y 100 car&aacute;cteres."
			},
			nombres : {
				required : "Ingrese el(os) Nombre(s).",
				notOnlySpace : "El(os) Nombre(s) no puede contener solo espacios en blanco.",
				rangelength : "El(os) Nombre(s) debe contener entre 3 y 100 car&aacute;cteres.",
			},
			codigoAlumno : {
				required : "Ingrese el C&oacute;digo de Alumno.",
				notOnlySpace : "El C&oacute;digo de Alumno no debe contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo de Alumno debe contener entre 6 y 8 car&aacute;cteres."
			},
			idEstadoCivil : {
				required : "Seleccione el Estado Civil.",
				notOnlySpace : "El Tipo de Documento no puede contener solo espacios en blanco.",
				selectlength : "El Tipo de Documento debe contener entre 1 y 4 car&aacute;cteres"
			},
			direccionActual : {
				required : "Ingrese la Direcci&oacute;n.",
				notOnlySpace : "La Direcci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "El Direcci&oacute;n debe contener entre 3 y 70 car&aacute;cteres."
			},
			codigoFacultad : {
				required : "Seleccione una Facultad.",
				digits : "La Facultad debe contener solo d&iacute;gitos.",
				range : "La Facultad debe estar entre 1 y 99."
			},
			codigoEscuela : {
				required : "Seleccione una Escuela.",
				digits : "La Escuela debe contener solo d&iacute;gitos.",
				range : "La Escuela debe estar entre 0 y 99."
			},
			correo : {
				required : "Ingrese el Correo.",
				notOnlySpaceOrEmpty : "El Correo no debe contener solo espacios en blanco.",
				email: "El Correo debe tener el formato <b>example@compania.com</b>.",
				maxlength : "El no debe contener m&aacute;s de 50 car&aacute;cteres."
			},
			distritoProcedencia : {
				required : "Ingrese el Distrito de procedencia.",
				notOnlySpace : "El Distrito de procedencia no puede contener solo espacios en blanco.",
				rangelength : "El Distrito de procedencia debe contener entre 3 y 100 car&aacute;cteres.",
			},
			telefonoFijo : {
				notOnlySpace : "El Tel&eacute;fono Fijo no debe contener solo espacios en blanco.",
				digits : "El Tel&eacute;fono Fijo debe contener solo d&iacute;gitos.",
				maxlength : "El Tel&eacute;fono Fijo no debe contener m&aacute;s de 13 d&iacute;gitos."
			},
			celular : {
				notOnlySpace : "El Tel&eacute;fono M&oacute;vil no debe contener solo espacios en blanco.",
				digits : "El Tel&eacute;fono M&oacute;vil debe contener solo d&iacute;gitos.",
				maxlength : "El Tel&eacute;fono M&oacute;vil no debe contener m&aacute;s de 13 d&iacute;gitos."
			},
			ocupacionActual : {
				required : "Ingrese la ocupación actual.",
				notOnlySpace : "La Ingrese la ocupación actual no puede contener solo espacios en blanco.",
				rangelength : "La Ingrese la ocupación actual debe contener entre 3 y 100 car&aacute;cteres.",
			}
		}
	});
});