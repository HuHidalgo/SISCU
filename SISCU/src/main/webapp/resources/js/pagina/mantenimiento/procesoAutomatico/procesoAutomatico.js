$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		idProcesoAutomaticoSeleccionado : "",
		$destinosEnvio : $("#destinosEnvio"),
		$campanias : $("#campanias"),
		$facultades : $("#facultades"),
		filtrosSeleccionables : {},
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$horaProcesoAutomatico : $("#horaProcesoAutomatico")
	};

	$formMantenimiento = $("#formMantenimiento");

	$funcionUtil.crearSelect2($local.$campanias, "Seleccione una Campaña");
	$funcionUtil.crearSelect2($local.$destinosEnvio, "Seleccione un Destino");
	$funcionUtil.crearSelect2($local.$facultades);

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$local.$horaProcesoAutomatico.datetimepicker({
		format : 'HH:mm'
	});

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaMantenimiento.clear().draw();
		}
	});

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "mantenimiento/procesoAutomatico?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Procesos Automáticos registrados."
		},
		order : [ [ 1, "asc" ] ],
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
			"className" : "all filtrable",
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar + " " + $variableUtil.botonEjecutar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
		}, {
			"data" : "ordenEjecucion",
			"title" : "Orden Ejec."
		}, {
			"data" : "idProcesoAutomatico",
			"title" : "Código Proc. Autom."
		}, {
			"data" : "descripcion",
			"title" : "Descripción"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCampaniaEnvio, row.descripcionCampania);
			},
			"title" : "Campaña"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idDestinoEnvio, row.descripcionDestinoEnvio);
			},
			"title" : "Destino Envío"
		}, {
			"data" : "horaProgramada",
			"title" : "Hora Programada"
		}, {
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.diario);
			},
			"title" : "Diario"
		}, {
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.activo);
			},
			"title" : "Activo"
		}, {
			"data" : function(row) {
				if (row.fechaInicio == null || row.fechaInicio == undefined || row.fechaFin == null || row.fechaFin == undefined) {
					return "";
				} else {
					return $funcionUtil.convertirDeFormatoAFormato(row.fechaInicio, "YYYY-MM-DD", "DD/MM/YYYY") + " - " + $funcionUtil.convertirDeFormatoAFormato(row.fechaFin, "YYYY-MM-DD", "DD/MM/YYYY");
				}
			},
			"title" : "Fechas"
		}, {
			"data" : "nombrePlantilla",
			"title" : "Plantilla"
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Proceso Automático",
		autoOpen : false,
		modal : false,
		height : 527,
		width : 754,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
		$local.$modalMantenimiento.PopupWindow("maximize");
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.idProcesoAutomaticoSeleccionado = "";
	});

	$formMantenimiento.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$registrarMantenimiento.hasClass("hidden")) {
				$local.$registrarMantenimiento.trigger("click");
				return false;
			} else {
				if (!$local.$actualizarMantenimiento.hasClass("hidden")) {
					$local.$actualizarMantenimiento.trigger("click");
				}
				return false;
			}
		}
	});

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var procesoAutomatico = $formMantenimiento.serializeJSON();
		var rangoFecha = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		procesoAutomatico.fechaInicio = rangoFecha.fechaInicio;
		procesoAutomatico.fechaFin = rangoFecha.fechaFin;
		procesoAutomatico.codigoFacultadesEnvio = procesoAutomatico.codigoFacultades.join(",");
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "mantenimiento/procesoAutomatico",
			data : JSON.stringify(procesoAutomatico),
			beforeSend : function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(procesoAutomaticos) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var procesoAutomatico = procesoAutomaticos[0];
				var row = $local.tablaMantenimiento.row.add(procesoAutomatico).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var procesoAutomatico = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		console.log(procesoAutomatico);
		$local.idProcesoAutomaticoSeleccionado = procesoAutomatico.idProcesoAutomatico;
		$funcionUtil.llenarFormulario(procesoAutomatico, $formMantenimiento);
		if (procesoAutomatico.fechaInicio != null || procesoAutomatico.fechaInicio != undefined || procesoAutomatico.fechaFin != null || procesoAutomatico.fechaFin != undefined) {
			var fechaInicio = $funcionUtil.convertirDeFormatoAFormato(procesoAutomatico.fechaInicio, "YYYY-MM-DD", "DD/MM/YYYY");
			var fechaFin = $funcionUtil.convertirDeFormatoAFormato(procesoAutomatico.fechaFin, "YYYY-MM-DD", "DD/MM/YYYY");
			$local.$rangoFechaBusqueda.data("daterangepicker").setStartDate(fechaInicio);
			$local.$rangoFechaBusqueda.data("daterangepicker").setEndDate(fechaFin);
			$local.$rangoFechaBusqueda.val(fechaInicio + " - " + fechaFin);
		}
		if (procesoAutomatico.codigoFacultadesEnvio != undefined || procesoAutomatico.codigoFacultadesEnvio != null) {
			$local.$facultades.val(procesoAutomatico.codigoFacultadesEnvio.split(",")).trigger("change.select2");
		} else {
			$local.$facultades.val("").trigger("change.select2");
		}
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var procesoAutomatico = $formMantenimiento.serializeJSON();
		procesoAutomatico.idProcesoAutomatico = $local.idProcesoAutomaticoSeleccionado;
		var rangoFecha = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		procesoAutomatico.fechaInicio = rangoFecha.fechaInicio;
		procesoAutomatico.fechaFin = rangoFecha.fechaFin;
		procesoAutomatico.codigoFacultadesEnvio = procesoAutomatico.codigoFacultades.join(",");
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "mantenimiento/procesoAutomatico",
			data : JSON.stringify(procesoAutomatico),
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
			success : function(procesoAutomaticos) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(procesoAutomaticos[0]).draw();
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

	$local.$tablaMantenimiento.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var procesoAutomatico = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar el Proceso Automático <b>'" + procesoAutomatico.idProcesoAutomatico + " - " + procesoAutomatico.descripcion + "'<b/>?",
			theme : "bootstrap",
			buttons : {
				Aceptar : {
					action : function() {
						var confirmar = $.confirm({
							icon : 'fa fa-spinner fa-pulse fa-fw',
							title : "Eliminando...",
							content : function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type : "DELETE",
									url : $variableUtil.root + "mantenimiento/procesoAutomatico",
									data : JSON.stringify(procesoAutomatico),
									autoclose : true,
									beforeSend : function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
									case 400:
										$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
										break;
									case 409:
										var mensaje = $funcionUtil.obtenerMensajeError("La Persona <b>" + persona.idTipoDocumento + " - " + persona.numeroDocumento + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
										$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
										break;
									}
								});
							},
							buttons : {
								close : {
									text : 'Aceptar'
								}
							}
						});
					},
					keys : [ 'enter' ],
					btnClass : "btn-primary"
				},
				Cancelar : {
					keys : [ 'esc' ]
				},
			}
		});
	});

	// Ejecucion de proceso
	$local.$tablaMantenimiento.children("tbody").on("click", ".ejecutar", function() {
		var $botonEjcutar = $(this);
		$local.$filaSeleccionada = $botonEjcutar.parents("tr");
		var procesoAutomatico = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		var criterioBusquedaEnvioCorreo = {};
		criterioBusquedaEnvioCorreo.idProcesoAutomatico = procesoAutomatico.idProcesoAutomatico;
		criterioBusquedaEnvioCorreo.descripcionCampania = procesoAutomatico.descripcionCampania;
		criterioBusquedaEnvioCorreo.descripcionFechaEnvio = $funcionUtil.convertirDeFormatoAFormato(procesoAutomatico.fechaInicio, "YYYY-MM-DD", "DD/MM/YYYY") + " - " + $funcionUtil.convertirDeFormatoAFormato(procesoAutomatico.fechaFin, "YYYY-MM-DD", "DD/MM/YYYY");
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "procesoManual/ejecucion?accion=ejecutar",
			data : JSON.stringify(criterioBusquedaEnvioCorreo),
			beforeSend : function(xhr) {
				$botonEjcutar.attr("disabled", true).find("i").removeClass("fa-flash").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
				xhr.setRequestHeader('Content-Type', 'application/json');
			},
			statusCode : {},
			success : function() {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$botonEjcutar.attr("disabled", false).find("i").addClass("fa-flash").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});