$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			tiempoReprogramacion : {
				required : true,
				notOnlySpace : true,
				digits : true,
				range : [ 1, 99 ]
			},
			correoClinica : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			correoSUM : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			anioInicial : {
				required : true,
				notOnlySpace : true,
				digits : true,
				range : [ 2016, 2030 ]
			}
						
		},
		messages : {
			tiempoReprogramacion : {
				required : "Ingrese el Tiempo de Programaci&oacute;n.",
				notOnlySpace : "El Tiempo de Programaci&oacute;n no debe contener solo espacios en blanco.",
				digits : "El Tiempo de Programaci&oacute;n debe contener solo d&iacute;gitos.",
				range : "El Tiempo de Programaci&oacute;n debe estar entre 1 y 99."
			},
			correoClinica : {
				required : "Ingrese el Correo de la CL&iacutenica.",
				notOnlySpace : "El Correo de la CL&iacutenica no puede contener solo espacios en blanco.",
				rangelength : "El Correo de la CL&iacutenica debe contener entre 3 y 100 car&aacute;cteres."
			},
			correoSUM : {
				required : "Ingrese su Correo del SUM .",
				notOnlySpace : "El Correo del Correo del SUM no puede contener solo espacios en blanco.",
				rangelength : "El Correo del SUM debe contener entre 3 y 100 car&aacute;cteres."
			},
			anioInicial : {
				required : "Ingrese el Año inicial.",
				notOnlySpace : "El Año inicial no debe contener solo espacios en blanco.",
				digits : "El Año inicial debe contener solo d&iacute;gitos.",
				range : "El Año inicial debe ser posterior al 2016."
			},
		}
	});

});