$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idTipoDocumento : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 5 ]
			},
			numeroDocumento : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 12 ]
			},
			apellidoPaterno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			apellidoMaterno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			nombres : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 100 ]
			},
			idSexo : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 1 ]
			}
		},
		messages : {
			idTipoDocumento : {
				required : "Seleccione el Tipo de Documento.",
				notOnlySpace : "El Tipo de Documento no debe contener solo espacios en blanco.",
				selectlength : "El Tipo de Documento debe contener entre 1 y 5 car&aacute;cteres"
			},
			numeroDocumento : {
				required : "Ingrese el N&uacute;mero de Documento.",
				notOnlySpace : "El N&uacute;umero de Documento no debe contener solo espacios en blanco.",
				rangelength : "El N&uacute;umero de Documento debe contener entre 3 y 12 car&aacute;cteres."
			},
			apellidoPaterno : {
				required : "Ingrese el Apellido Paterno.",
				notOnlySpace : "El Apellido Paterno no puede contener solo espacios en blanco.",
				rangelength : "El Apellido Paterno debe contener entre 3 y 100 car&aacute;cteres."
			},
			apellidoMaterno : {
				required : "Ingrese el Apellido Materno.",
				notOnlySpace : "El Apellido Materno no puede contener solo espacios en blanco.",
				rangelength : "El Apellido Materno debe contener entre 3 y 100 car&aacute;cteres."
			},
			nombres : {
				required : "Ingrese el(os) Nombre(s).",
				notOnlySpace : "El(os) Nombre(s) no puede contener solo espacios en blanco.",
				rangelength : "El(os) Nombre(s) debe contener entre 3 y 100 car&aacute;cteres."
			},
			idSexo : {
				required : "Seleccione un Sexo.",
				notOnlySpace : "El Sexo no puede contener solo espacios en blanco.",
				selectlength : "El Sexo debe 1 contener car&aacute;cter."
			}
		}
	});

});