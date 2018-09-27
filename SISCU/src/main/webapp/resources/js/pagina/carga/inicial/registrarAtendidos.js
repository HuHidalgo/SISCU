$(document).ready(function() {

	var $local = {
		$tablaErroresAfiliados : $("#tablaErroresAfiliados"),
		tablaErroresAfiliados : "",
		$filaSeleccionada : "",
		
		$estamentos2 : $("#estamentos2"),		
		$cargarAfiliados : $("#cargarAfiliados"),
		numeroRegistroSeleccionado : "",
		
		$divErroresCarga: $("#divErroresCarga"),
		$divTotalRegistros: $("#divTotalRegistros")
	};

	$formCargaAtendidos = $("#formCargaAtendidos");
	
	$funcionUtil.crearSelect2($local.$estamentos2, "Seleccione un Estamento");
		
	//$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaErroresAfiliados.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaErroresAfiliados.clear().draw();
			break;
		}
	});	
	
	$formCargaAtendidos.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$cargarAfiliados.trigger("click");
			return false;
		}
	});
	
	$local.tablaErroresAfiliados = $local.$tablaErroresAfiliados.DataTable({
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
	
	$local.$cargarAfiliados.on("click", function(e) {
		e.preventDefault();
		if (!$formCargaAtendidos.valid()) {
			return;
		}
		var form = $("#formCargaAtendidos")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos2.val();
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/registrarAtendidos/uploadfile/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.$cargarAfiliados.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formCargaAtendidos);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCargaAtendidos);
				}
			},
			success : function(carga) {
				console.log(carga);
				$local.$divTotalRegistros.append($("<h3 align='center'>").text("Total de Registros Cargados : " + carga.totalRegistros));
				if (carga.errorCarga != 0) {
					$funcionUtil.notificarException("Hubo un error en la carga", "fa-exclamation-circle", "Información", "warning");
					$local.$divErroresCarga.removeClass("hidden");
					$local.tablaErroresAfiliados.rows.add(carga.errorCarga).draw();
					//$local.$divAreaTrabajo.addClass("hidden");
					return;
				}
				$funcionUtil.notificarException("Se cargó exitósamente", "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarAfiliados.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});	
	
});