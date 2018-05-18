$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),		
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$tiempoReprogramacion : $("#tiempoReprogramacion"),
		tiempoReprogramacion : 0,
		correoSUM : "",
		correoClinica : "",
		anioInicial : 0,
	};

	$formMantenimiento = $("#formMantenimiento");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaMantenimiento.clear().draw();
			console.log(xhr);
			break;
		case 200:			
			console.log(xhr);
			break;
		}
	});

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "mantenimiento/parametroGeneral?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Parámetros generales registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3],
			"className" : "all filtrable",
		}, {
			"targets" : 4,
			"className" : "all dt-center",
			"width" : "10%",
			"defaultContent" : $variableUtil.botonActualizar
		} ],

		"columns" : [ 
		{
			"data" : "tiempoReprogramacion",
			"title" : 'Tiempo de Reprogramación'
		}, {
			"data" : "correoSUM",
			"title" : 'Correo SUM'
		}, {
			"data" : "correoClinica",
			"title" : 'Correo CLinica'
		}, {
			"data" : "anioInicial",
			"title" : 'Año Inicial'
		}, {
			"data" : null,
			"title" : "Acción"
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Parámetros generales",
		autoOpen : false,
		modal : false,
		height : 440,
		width : 770,
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {

	});	

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var parametroGeneral = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();		
		$funcionUtil.llenarFormulario(parametroGeneral, $formMantenimiento);			
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$actualizarMantenimiento.removeClass("hidden");		
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var parametroGeneral = $formMantenimiento.serializeJSON();
		console.log(parametroGeneral);
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "mantenimiento/parametroGeneral",
			data : JSON.stringify(parametroGeneral),
			beforeSend : function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(parametrosGenerales) {				
				$funcionUtil.notificarException("Actualización Exitosa", "fa-check", "Aviso", "success");
				$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
				var row = $local.tablaMantenimiento.row.add(parametroGeneral).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
			},					
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
});