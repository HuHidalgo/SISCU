$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			codigoFacultad : {
				required : true,
				notOnlySpace : true,
				range : [ 1, 99 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 250 ]
			},
			correoAsistenta : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			correoFacultad : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			}
		},
		messages : {
			codigoFacultad : {
				required : "Ingrese el C&oacute;digo de Facultad.",
				notOnlySpace : "El C&oacute;digo de Facultad no debe contener solo espacios en blanco.",
				range : "El C&oacute;digo de Facultad debe estar entre 1 y 99."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 250 car&aacute;cteres."
			},
			correoAsistenta : {
				required : "Ingrese el Correo de la Asistenta.",
				notOnlySpace : "El Correo de la Asistenta no puede contener solo espacios en blanco.",
				rangelength : "El Correo de la Asistenta debe contener entre 3 y 50 car&aacute;cteres."
			},
			correoFacultad : {
				required : "Ingrese el Correo del Decano.",
				notOnlySpace : "El Correo del Decano no puede contener solo espacios en blanco.",
				rangelength : "El Correo del Decano debe contener entre 3 y 50 car&aacute;cteres."
			}
		}
	});
});