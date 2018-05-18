$(document).ready(function() {

	$formMantenimiento.validate({
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
			tipoAlumno : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
			},
			codigoAlumno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 6, 8 ]
			},
			direccion : {
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
			correoInstitucional : {
				required : function(){
					return $tiposAlumno.val() == "R";
				},
				notOnlySpaceOrEmpty : true,
				email: true,
				maxlength : 50
			},
			correoPersonal : {
				required : function(){
					return $tiposAlumno.val() == "I";
				},
				notOnlySpaceOrEmpty : true,
				email: true,
				maxlength : 50
			},
			telefonoFijo : {
				notOnlySpaceOrEmpty : true,
				digits : true,
				maxlength : 13
			},
			telefonoMovil : {
				notOnlySpaceOrEmpty : true,
				digits : true,
				maxlength : 13
			},
			idDiscapacidad : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 4 ]
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
			tipoAlumno : {
				required : "Seleccione el Tipo de Alumno.",
				notOnlySpace : "El Tipo de Alumno no debe contener solo espacios en blanco.",
				selectlength : "El Tipo de Alumno debe contener entre 1 y 4 car&aacute;cteres"
			},
			codigoAlumno : {
				required : "Ingrese el C&oacute;digo de Alumno.",
				notOnlySpace : "El C&oacute;digo de Alumno no debe contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo de Alumno debe contener entre 6 y 8 car&aacute;cteres."
			},
			direccion : {
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
			correoInstitucional : {
				required : "Ingrese el Correo Institucional.",
				notOnlySpaceOrEmpty : "El Correo Institucional no debe contener solo espacios en blanco.",
				email: "El Correo Institucional debe tener el formato <b>example@compania.com</b>.",
				maxlength : "El Correo Institucional no debe contener m&aacute;s de 50 car&aacute;cteres."
			},
			correoPersonal : {
				required : "Ingrese el Correo Personal.",
				notOnlySpaceOrEmpty : "El Correo Personal no debe contener solo espacios en blanco.",
				email: "El Correo Personal debe tener el formato <b>example@compania.com</b>.",
				maxlength : "El Correo Personal no debe contener m&aacute;s de 50 car&aacute;cteres."
			},
			telefonoFijo : {
				notOnlySpace : "El Tel&eacute;fono Fijo no debe contener solo espacios en blanco.",
				digits : "El Tel&eacute;fono Fijo debe contener solo d&iacute;gitos.",
				maxlength : "El Tel&eacute;fono Fijo no debe contener m&aacute;s de 13 d&iacute;gitos."
			},
			telefonoMovil : {
				notOnlySpace : "El Tel&eacute;fono M&oacute;vil no debe contener solo espacios en blanco.",
				digits : "El Tel&eacute;fono M&oacute;vil debe contener solo d&iacute;gitos.",
				maxlength : "El Tel&eacute;fono M&oacute;vil no debe contener m&aacute;s de 13 d&iacute;gitos."
			},
			idDiscapacidad : {
				required : "Seleccione una Discapacidad.",
				notOnlySpace : "La Discapacidad no debe contener solo espacios en blanco.",
				selectlength : "La Discapacidad debe contener entre 1 y 4 car&aacute;cteres."
			}
		}
	});
});