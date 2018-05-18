$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoFacultad : {
				required : true,
				notOnlySpace : true,
				digits : true,
				range : [ 1, 99 ]
			},
			codigoEscuela : {
				required : true,
				notOnlySpace : true,
				digits : true,
				range : [ 1, 99 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 250 ]
			}
		},
		messages : {
			codigoFacultad : {
				required : "Ingrese el C&oacute;digo de Facultad.",
				notOnlySpace : "El C&oacute;digo de Facultad no debe contener solo espacios en blanco.",
				digits : "El C&oacute;digo de Facultad debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Facultad debe estar entre 1 y 99."
			},
			codigoEscuela : {
				required : "Ingrese el C&oacute;digo de Escuela.",
				notOnlySpace : "El C&oacute;digo de Escuela no debe contener solo espacios en blanco.",
				digits : "El C&oacute;digo de Escuela debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Escuela debe estar entre 1 y 99."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 250 car&aacute;cteres."
			}
		}
	});
});