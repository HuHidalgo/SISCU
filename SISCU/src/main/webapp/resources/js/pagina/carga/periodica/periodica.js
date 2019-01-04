$(document).ready(function() {

	var $local = {		
		$tablaErroresCarga : $("#tablaErroresCarga"),
		tablaErroresCarga : "",
		
		$cargarPeriodica : $("#cargarPeriodica"),
		$estamentos : $("#estamentos"),
		$uploadfile : $("#uploadfile"),
		
		$divErroresCarga: $("#divErroresCarga"),
		$divTotalRegistros: $("#divTotalRegistros")
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
	
	$local.$tablaErroresCarga.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaErroresCarga.clear().draw();
			break;
		}
	});		
	
	$local.tablaErroresCarga = $local.$tablaErroresCarga.DataTable({
		"language" : {
			"emptyTable" : "No hay datos cargados"
		},
		"lengthChange": false,
		"searching": false,
		"ordering" : false,
		"columnDefs" : [ {
			"targets" : [ 1 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 0,
			"className" : "all dt-center",
		}],
		"columns" : [ {
			"data" : function(row){
				return "<label class='label label-danger label-size-12'>" + row.fila + "</label>";
			},
			"title" : 'Fila',
			"width" : "10%"
		}, {
			"data" : "nombreColumna",
			"title" : "Descripción de Errores: Columnas con valores nulos"
		}]
	});

	$local.$cargarPeriodica.on("click", function(e) {
		e.preventDefault();
		if (!$formCargaPeriodica.valid()) {
			return;
		}
		var form = $("#formCargaPeriodica")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos.val();
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/periodica/uploadfile/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.$cargarPeriodica.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formCargaPeriodica);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCargaPeriodica);
				}
			},
			success : function(carga) {
				console.log(carga);
				$local.$divTotalRegistros.append($("<h3 align='center'>").text("Total de Registros Cargados : " + carga.totalRegistros));
				if (carga.errorCarga != 0) {
					$funcionUtil.notificarException("Hubo un error en la carga", "fa-exclamation-circle", "Información", "warning");
					$local.$divErroresCarga.removeClass("hidden");
					$local.tablaErroresCarga.rows.add(carga.errorCarga).draw();
					//$local.$divAreaTrabajo.addClass("hidden");
					return;
				}
				$funcionUtil.notificarException("Se cargó exitósamente", "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarPeriodica.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});