$(document).ready(function() {

	$formConsultaInicial.validate({
		focusCleanup : true,
		rules : {
			idEstamento : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 1 ]
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
				required : "Seleccione un Estamento.",
				notOnlySpace : "El Estamento no puede contener solo espacios en blanco.",
				rangelength : "El Estamento debe contener 1 car&aacute;cter."
			},
			tipoDocumento : {
				required : "Ingrese el Tipo de Documento."
			},
			nroDocumento : {
				required : "Ingrese Número de Documento."
			}
		}
	});
});