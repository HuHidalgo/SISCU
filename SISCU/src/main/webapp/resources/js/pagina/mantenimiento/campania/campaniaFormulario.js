$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			descripcionCorta : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 20 ]
			},
			idTipoCertificado : {
				required : true,
				notOnlySpace : true,
				selectlength : 1
			}
		},
		messages : {
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				notOnlySpace : "La descripci&oacute;n no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre 3 y 50 car&aacute;cteres."
			},
			descripcionCorta : {
				required : "Ingrese una descripci&oacute;n corta.",
				notOnlySpace : "La descripci&oacute;n corta no puede contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n corta debe contener entre 3 y 20 car&aacute;cteres."
			},
			idTipoCertificado : {
				required : "Seleccione un Tipo de Certificado.",
				notOnlySpace : "El Tipo de Certificado no puede contener solo espacios en blanco.",
				selectlength : "El Tipo de Certificado debe contener 1 car&aacute;cter."
			}
		}
	});
});