$(document).ready(function() {

	$formReporteAtencionDiaria.validate({
		focusCleanup : true,
		rules : {
			idTipoExamenMedico : {
				required : true,
				lettersonly : true,
				selectlength : [ 1, 1 ]
			},
			idCampania : {
				required : true,
				digits : true
			}
		},
		messages : {
			idTipoExamenMedico : {
				required : "Seleccione un Tipo de Examen Médico.",
				lettersonly : "El Tipo de Examen Médico debe contener solo car&aacute;cteres.",
				selectlength : "El Tipo de Examen Médico debe contener 1 car&aacute;cter."
			},
			idCampania : {
				required : "Seleccione una Campaña.",
				idCampania : "La Campaña debe contener solo d&iacute;gitos."
			}
		}
	});
});