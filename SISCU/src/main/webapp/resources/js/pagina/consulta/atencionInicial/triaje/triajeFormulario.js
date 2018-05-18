$(document).ready(function() {

	$formResultadoExamenMedico.validate({
		focusCleanup : true,
		rules : {
			peso : {
				required : true,
				number : true,
				range : [ 30, 200 ]
			},
			talla : {
				required : true,
				number : true,
				range : [ 40, 300 ]
			},
			pulso : {
				required : true,
				digits : true,
				range : [ 50, 200 ]
			},
			presionSistolica : {
				required : true,
				digits : true,
				range : [ 60, 170 ]
			},
			presionDiastolica : {
				required : true,
				digits : true,
				range : [ 40, 140 ]
			}
		},
		messages : {
			peso : {
				required : "Ingrese el Peso.",
				number : "El Peso debe contener solo n&uacute;meros.",
				range : "El Peso debe estar entre <b>30 y 200 kg</b>."
			},
			talla : {
				required : "Ingrese la Talla.",
				number : "La Talla debe contener solo n&uacute;meros.",
				range : "La Talla debe estar entre <b>40 y 300 cm</b>."
			},
			pulso : {
				required : "Ingrese el Pulso.",
				digits : "El Pulso debe contener solo d&iacute;gitos.",
				range : "El Pulso debe estar entre <b>50 y 200</b>."
			},
			presionSistolica : {
				required : "Ingrese la Presi&oacute;n Sist&oacute;lica",
				digits : "La Presi&oacute;n Sist&oacute;lica debe contener solo d&iacute;gitos.",
				range : "La Presi&oacute;n Sist&oacute;lica debe estar entre <b>60 y 170</b>."
			},
			presionDiastolica : {
				required : "Ingrese la Presi&oacute;n Diast&oacute;lica",
				digits : "La Presi&oacute;n Diast&oacute;lica debe contener solo d&iacute;gitos.",
				range : "La Presi&oacute;n Diast&oacute;lica debe estar entre <b>40 y 140</b>."
			}
		}
	});
});