$(document).ready(function() {

	var $local = {		
		$cargarInicial : $("#cargarInicial"),
		$estamentos : $("#estamentos"),				
		$uploadfile : $("#uploadfile"),
		
	};

	$formCargaInicial = $("#formCargaInicial");
	
	$funcionUtil.crearSelect2($local.$estamentos, "Seleccione un Estamento");
	
	$formCargaInicial.validate({
		focusCleanup : true,
		rules : {
			idEstamento : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 1 ]
			},
			uploadfile : {
				required : true
			}
		},
		messages : {
			idEstamento : {
				required : "Seleccione un Estamento.",
				notOnlySpace : "El Estamento no puede contener solo espacios en blanco.",
				rangelength : "El Estamento debe contener 1 car&aacute;cter."
			},
			uploadfile : {
				required : "Seleccione un Archivo"
			}
		}
	});
	
	$local.$cargarInicial.on("click", function(e) {
		e.preventDefault();
		if (!$formCargaInicial.valid()) {
			return;
		}
		var form = $("#formCargaInicial")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos.val();
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/inicial/uploadfile/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.$cargarInicial.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formCargaInicial);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCargaInicial);
				}
			},
			success : function(response) {
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarInicial.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	
});