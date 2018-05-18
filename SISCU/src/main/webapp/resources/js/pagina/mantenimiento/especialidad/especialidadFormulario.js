$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 400 ]
			},
			descripcionCorta : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 25 ]
			}
		},
		messages : {
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 400 car&aacute;cteres."
			},
			descripcionCorta : {
				required : "Ingrese una descripci&oacute;n corta.",
				notOnlySpace : "La descripci&oacute;n corta no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n corta debe contener entre 3 y 25 car&aacute;cteres."
			}
		}
	});
	
});