$(document).ready(function() {

	var $local = {
		$cargarPeriodica : $("#cargarPeriodica"),
		$estamentos : $("#estamentos"),
		$uploadfile : $("#uploadfile")
	};

	$formCargaPeriodica = $("#formCargaPeriodica");
	$funcionUtil.crearSelect2($local.$estamentos, "Seleccione un Estamento");
	
	$formCargaPeriodica.validate({
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

	$local.$cargarPeriodica.on("click", function(e) {
		console.log(".l.");
		e.preventDefault();
		if (!$formCargaPeriodica.valid()) {
			return;
		}
		var form = $("#formCargaPeriodica")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos.val();
		console.log(idEstamento);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/periodica/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.$cargarPeriodica.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(controlLotes) {

			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarPeriodica.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});