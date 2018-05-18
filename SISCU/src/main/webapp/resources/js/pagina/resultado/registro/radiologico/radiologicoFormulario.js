$(document).ready(function() {

	$formResultadoExamenMedico.validate({
		focusCleanup : true,
		rules : {
			idResultadoRadiologico : {
				required : true,
				lettersonly : true,
				selectlength : [ 1, 1 ]
			},
			observacion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 250 ]
			}
		},
		messages : {
			idResultadoRadiologico : {
				required : "Seleccione un Resultado.",
				lettersonly : "El Resultado debe contener solo car&aacute;cteres.",
				selectlength : "El Resultado debe contener 1 car&aacute;cter."
			},
			observacion : {
				required : "Ingrese una Observaci&oacute;n.",
				notOnlySpace : "La Observaci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La Observaci&oacute;n debe contener entre 3 y 250 car&aacute;cteres."
			}
		}
	});
});