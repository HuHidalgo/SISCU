$(document).ready(function() {

	$formAntecedentePersonal.validate({
		focusCleanup : true,
		rules : {
			idCie10 : {
				required : true,
				rangelength : [ 1, 8 ]
			}
		},
		messages : {
			idCie10 : {
				required : "Seleccione un C&oacute;digo de CIE 10.",
				rangelength : "El C&oacute;digo de CIE 10 debe contener entre <b>1 y 8 caracteres</b>."
			}
		}
	});

	$formAntecedenteFamiliar.validate({
		focusCleanup : true,
		rules : {
			idCie10 : {
				required : true,
				rangelength : [ 1, 8 ]
			},
			idTipoAntecedente : {
				required : true,
				rangelength : [ 1, 5 ]
			}
		},
		messages : {
			idCie10 : {
				required : "Seleccione un C&oacute;digo de CIE 10.",
				rangelength : "El C&oacute;digo de CIE 10 debe contener entre <b>1 y 8 caracteres</b>."
			},
			idTipoAntecedente : {
				required : "Seleccione un Parentesco.",
				rangelength : "El Parentesco debe contener entre <b>1 y 5 caracteres</b>."
			}
		}
	});

	$formAlergia.validate({
		focusCleanup : true,
		rules : {
			idCie10 : {
				required : true,
				rangelength : [ 1, 8 ]
			}
		},
		messages : {
			idCie10 : {
				required : "Seleccione un C&oacute;digo de CIE 10.",
				rangelength : "El C&oacute;digo de CIE 10 debe contener entre <b>1 y 8 caracteres</b>."
			}
		}
	});
	
	$formCriterioBusquedaConsulta.validate({
		focusCleanup : true,
		rules : {
			codigoAlumno : {
				notOnlySpaceOrEmpty : true,
				rangelength : [ 6, 8 ]
			},
			codigoFacultad : {
				number : true,
				range : [ -1, 99 ]
			},
			idCampania : {
				required : true,
				digits : true,
				range : [ 1, 999 ]
			}
		},
		messages : {
			codigoAlumno : {
				notOnlySpaceOrEmpty : "El C&oacute;digo de Alumno no debe contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo de Alumno debe contener entre 6 y 8 car&aacute;cteres."
			},
			codigoFacultad : {
				number : "La Facultad debe ser un n&uacute;mero.",
				range : "La Facultad debe estar entre -1 y 99."
			},
			idCampania : {
				required : "Seleccione una Campaña.",
				digits : "La Campaña debe contener solo d&icute;gitos.",
				range : "La Campaña debe estar entre 1 y 999."
			}
		}
	});

});