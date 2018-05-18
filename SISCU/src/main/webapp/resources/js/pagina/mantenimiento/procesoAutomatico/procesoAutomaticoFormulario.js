$(document).ready(function() {

	$formMantenimiento.validate({
		focusCleanup : true,
		rules : {
			idProcesoAutomatico : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 10 ]
			},
			descripcion : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 250 ]
			},
			ordenEjecucion : {
				required : true,
				digits : true,
				range : [ 1, 99 ]
			},
			horaProgramada : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 5, 5 ]
			},
			idCampaniaEnvio : {
				required : true,
				digits : true,
				range : [ 1, 999 ]
			},
			idDestinoEnvio : {
				required : true,
				notOnlySpace : true,
				selectlength : [ 1, 6 ]
			},
			nombrePlantilla : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 50 ]
			},
			"codigoFacultades[]" : {
				required : true,
				minlength : 1
			},
			textoEncabezado : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 250 ]
			},
			textoCuerpo : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 3, 300 ]
			},
			rangoFechasEnvio : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 20, 25 ]
			}
		},
		messages : {
			idProcesoAutomatico : {
				required : "Ingrese el C&oacute;digo de Proceso Autom&aacute;tico.",
				notOnlySpace : "El C&oacute;digo de Proceso Autom&aacute;tico no debe contener solo espacios en blanco.",
				rangelength : "El C&oacute;digo de Proceso Autom&aacute;tico debe contener entre <b>1 y 10 car&aacute;cteres</b>."
			},
			descripcion : {
				required : "Ingrese una descripci&oacute;n.",
				required : "La descripci&oacute;n no debe contener solo espacios en blanco.",
				rangelength : "La descripci&oacute;n debe contener entre <b>1 y 50 car&aacute;cteres</b>."
			},
			ordenEjecucion : {
				required : "Ingrese el Orden de Ejecuci&oacute;n.",
				digits : "El Orden de Ejecuci&oacute;n solo debe contener d&iacute;gitos.",
				range : "El Orden de Ejecuci&oacute;n debe estar entre <b> 1 y 99</b>."
			},
			horaProgramada : {
				required : "Ingrese la Hora Programada.",
				notOnlySpace : "La Hora Programada no debe contener solo espacios en blanco.",
				rangelength : "La Hora Programada debe contener <b>5 car&aacute;cteres</b>."
			},
			idCampaniaEnvio : {
				required : "Selecciona la Campaña.",
				digits : "El C&oacute;digo de Campaña debe contener solo d&iacute;gitos.",
				range : "El C&oacute;digo de Campaña debe estar entre <b>1 y 999</b>."
			},
			idDestinoEnvio : {
				required : "Seleccione el Destino de Env&iacute;o.",
				notOnlySpace : "El Destino de Env&iacute;o no debe contener solo espacios en blanco.",
				selectlength : "El Destino de Env&iacute;o debe contener entre <b>1 y 6 car&aacute;cteres</b>."
			},
			nombrePlantilla : {
				required : "Ingrese el Nombre de Plantilla.",
				notOnlySpace : "El Nombre de Plantilla no debe contener solo espacios en blanco.",
				rangelength : "El Nombre de Plantilla debe contener entre <b>1 y 6 car&aacute;cteres</b>."
			},
			"codigoFacultades[]" : {
				required : "Seleccione al menos una facultad.",
				minlength : "Seleccione al menos una facultad."
			},
			textoEncabezado : {
				required : "Ingrese el Texto de Encabezado de Correo.",
				notOnlySpace : "El Texto de Encabezado de Correo no debe contener solo espacios en blanco.",
				rangelength : "El Texto de Encabezado de Correo debe contener entre <b>3 y 250 car&aacute;cteres</b>."
			},
			textoCuerpo : {
				required : "Ingrese el Texto de Cuerpo de Correo.",
				notOnlySpace : "El Texto de Cuerpo de Correo no debe contener solo espacios en blanco.",
				rangelength : "El Texto de Cuerpo de Correo debe contener entre <b>3 y 300 car&aacute;cteres</b>."
			},
			rangoFechasEnvio : {
				required : "Seleccione el Rango de Fechas de Env&iacute;os.",
				notOnlySpace : "El Rango de Fechas de Env&iacute;os no debe contener solo espacios en blanco.",
				rangelength : "El Rango de Fechas de Env&iacute;os debe contener <b>25 car&aacute;cteres</b>."
			}
		}
	});

});