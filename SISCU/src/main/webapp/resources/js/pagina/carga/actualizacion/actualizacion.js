$(document).ready(function() {

	var $local = {
		$cargarActualizacion : $("#cargarActualizacion"),
		$estamentos : $("#estamentos"),
		$uploadfile : $("#uploadfile")
	};

	$formCargaActualizacion = $("#formCargaActualizacion");
	$funcionUtil.crearSelect2($local.$estamentos, "Seleccione un Estamento");
	
	$formCargaActualizacion.validate({
		focusCleanup : true,
		rules : {
			estamentos : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 1 ]
			},
			uploadfile : {
				required : true
			}
		},
		messages : {
			estamentos : {
				required : "Seleccione un Estamento.",
				notOnlySpace : "El Estamento no puede contener solo espacios en blanco.",
				rangelength : "El Estamento debe contener 1 car&aacute;cter."
			},
			uploadfile : {
				required : "Seleccione un Archivo"
			}
		}
	});

	$local.$cargarActualizacion.on("click", function(e) {
		console.log(".l.");
		e.preventDefault();
		if (!$formCargaActualizacion.valid()) {
			return;
		}
		var form = $("#formCargaActualizacion")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos.val();
		console.log(idEstamento);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/actualizacion/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.$cargarActualizacion.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(controlLotes) {

			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarActualizacion.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});