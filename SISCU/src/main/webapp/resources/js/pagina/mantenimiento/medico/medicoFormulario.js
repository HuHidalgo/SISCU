$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idPersona : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 3, 30 ]
			},
			colegioMedico : {
				required : true,
				digits : true,
				range : [ 1, 999999 ]
			},
			registroNacionalEspecialidad : {
				required : true,
				digits : true,
				range : [ 1, 999999 ]
			}
		},
		messages : {
			idPersona : {
				required : "Seleccione una Persona.",
				notOnlySpace : "La Persona no puede contener solo espacios en blanco.",
				selectlength : "La Persona debe contener entre 3 y 30 car&aacute;cteres."
			},
			colegioMedico : {
				required : "Ingrese el N&uacute;mero de Colegio M&eacute;dico.",
				digits : "El N&uacute;umero de Colegio M&eacute;dico solo debe contener d&iacute;gitos.",
				range : "El N&uacute;umero de Colegio M&eacute;dico debe estar entre 1 y 999999."
			},
			registroNacionalEspecialidad : {
				required : "Ingrese el Registro Nacional de Especialidad.",
				digits : "El Registro Nacional de Especialidad solo debe contener d&iacute;gitos.",
				range : "El Registro Nacional de Especialidad debe estar entre 1 y 999999."
			}
		}
	});

});