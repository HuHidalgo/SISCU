$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idUsuario : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 20 ]
			},
			idPersona : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 3, 30 ]
			},
			idPerfil : {
				required : true,
				digits : true,
				range : [ 1, 100 ]
			},
			contrasenia : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 20 ]
			}
		},
		messages : {
			idUsuario : {
				required : "Ingrese un Nombre de Usuario.",
				notOnlySpace : "El Nombre de Usuario no puede contener solo espacios en blanco.",
				rangelength : "El Nombre de Usuario debe contener entre 3 y 20 car&aacute;cteres."
			},
			idPersona : {
				required : "Seleccione una Persona.",
				notOnlySpace : "La Persona no puede contener solo espacios en blanco.",
				selectlength : "La Persona debe contener entre 3 y 30 car&aacute;cteres."
			},
			idPerfil : {
				required : "Seleccione un Perfil.",
				digits : "El Perfil debe contener solo d&iacute;gitos.",
				range : "El Perfil debe estar entre 1 y 100."
			},
			contrasenia : {
				required : "Ingrese una Contraseña.",
				notOnlySpace : "La Contraseña no puede contener solo espacios en blanco.",
				rangelength : "La Contraseña debe contener entre 3 y 20 car&aacute;cteres."
			}
		},
	});

});