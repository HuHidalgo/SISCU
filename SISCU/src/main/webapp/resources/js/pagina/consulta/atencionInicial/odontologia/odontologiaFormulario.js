$(document).ready(function() {

	$formResultadoExamenMedico.validate({
		focusCleanup : true,
		rules : {
			piezasFaltantes : {
				required : true,
				digits : true,
				range : [ 0, 32 ]
			},
			piezasObturadas : {
				required : true,
				digits : true,
				range : [ 0, 32 ]
			},
			piezasAObturar : {
				required : true,
				digits : true,
				range : [ 0, 32 ]
			},
			piezasPorExtraer : {
				required : true,
				digits : true,
				range : [ 0, 32 ]
			},
			idMaloclusion : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 5 ]
			},
			idHigiene : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 5 ]
			},
			idProtesis : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 5 ]
			},
			indiceMasticacion : {
				required : true,
				digits : true,
				range : [ 0, 100 ]
			},
			observacion : {
				rangelength : [ 5, 250 ]
			}
		},
		messages : {
			piezasFaltantes : {
				required : "La cantidad de Piezas Faltantes no debe contener solo espacios en blanco.",
				digits : "La cantidad de Piezas Faltantes debe contener solo d&iacute;gitos.",
				range : "La cantidad de Piezas Faltantes debe estar entre <b>0 y 32</b>."
			},
			piezasObturadas : {
				required : "La cantidad de Piezas Obturadas no debe contener solo espacios en blanco.",
				digits : "La cantidad de Piezas Obturadas debe contener solo d&iacute;gitos.",
				range : "La cantidad de Piezas Obturadas deben estar entre <b>0 y 32</b>."
			},
			piezasAObturar : {
				required : "La cantidad de Piezas a Obturar no debe contener solo espacios en blanco.",
				digits : "La cantidad de Piezas a Obturar debe contener solo d&iacute;gitos.",
				range : "La cantidad de Piezas a Obturar debe estar entre <b>0 y 32</b>."
			},
			piezasPorExtraer : {
				required : "La cantidad de Piezas por Extraer no debe contener solo espacios en blanco.",
				digits : "La cantidad de Piezas por Extraer debe contener solo d&iacute;gitos.",
				range : "La cantidad de Piezas por Extraer debe estar entre <b>0 y 32</b>."
			},
			idMaloclusion : {
				required : "Seleccione una Maloclusi&oacute;n.",
				notOnlySpace : "La Maloclusi&oacute;n no debe contener solo espacios en blanco.",
				selectlength : "La Maloclusi&oacute;n debe contener <b>1 car&aacute;cter</b>."
			},
			idHigiene : {
				required : "Seleccione una Estado de Higiene.",
				notOnlySpace : "El Estado de Higiene no debe contener solo espacios en blanco.",
				selectlength : "El Estado de Higiene debe contener <b>1 car&aacute;cter</b>."
			},
			idProtesis : {
				required : "Seleccione una Estado de Prótesis.",
				notOnlySpace : "El Estado de Prótesis no debe contener solo espacios en blanco.",
				selectlength : "El Estado de Prótesis debe contener <b>1 car&aacute;cter</b>."
			},
			indiceMasticacion : {
				required : "Ingrese el Índice de Masticación.",
				digits : "El Índice de Masticación debe contener solo d&iacute;gitos.",
				range : "El Índice de Masticación debe estar entre <b>0 y 100</b>."
			},
			observacion : {
				rangelength : "La Observaci&oacute;n debe contener entre <b>5 y 250 car&aacute;cteres</b>."
			}
		}
	});
});