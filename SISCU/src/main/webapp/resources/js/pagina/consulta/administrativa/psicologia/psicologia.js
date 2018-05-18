$(document).ready(function() {

	var $local = {
		// Para Consulta
		$tablaConsultaAdministrativaExamenMedico : $("#tablaConsultaAdministrativaExamenMedico"),
		$tablaConsultaAdministrativaExamenMedicoDetalle : $("#tablaConsultaAdministrativaExamenMedicoDetalle"),
		tablaConsultaAdministrativaExamenMedico : "",
		$filaSeleccionada : "",
		$facultades : $("#facultades"),
		$campanias : $("#campanias"),
		$tiposAlumno : $("#tiposAlumno"),
		$escuelas : $("#escuelas"),
		$buscar : $("#buscar"),
		$buscar1 : $("#buscar1"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$administrador : $("#administrador"),

		// Para Actualizacion de Resultado
		$modalResultado : $("#modalResultado"),
		$resultadoPsicologico : $(".resultadoPsicologico"),
		$observacion : $("#observacion"),
		numeroRegistroSeleccionado : "",
		$divObservacion : $("#divObservacion"),
		$registrarResultado : $("#registrarResultado"),
		$editarObservacion : $("#editarObservacion"),
		
		/* Datos */
		$inputCodigo : $("#codigo"),
		$inputApellidosNombres : $("#apellidosNombres")
	};

	// Para Consulta
	$formCriterioBusquedaAdministrativaExamenMedico = $("#formCriterioBusquedaAdministrativaExamenMedico");
	$formCriterioBusquedaAdministrativaDetallada = $("#formCriterioBusquedaAdministrativaDetallada");

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$funcionUtil.crearSelect2($local.$facultades);
	$funcionUtil.crearSelect2($local.$campanias);
	$funcionUtil.crearSelect2($local.$tiposAlumno);
	$funcionUtil.crearSelect2($local.$escuelas);

	var esAdmin = $local.$administrador.length > 0;

	$local.tablaConsultaAdministrativaExamenMedico = $local.$tablaConsultaAdministrativaExamenMedico.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativaExamenMedico.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativaExamenMedico);
		},
		"ordering" : false,
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				var elemento = "";
				if (esAdmin) {
					if (row.idEstadoExamenMedico == 'C') {
						elemento = $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar;
					}
				}
				return elemento;
			}
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
		}, {
			"data" : "fechaRegistro",
			"title" : "Fecha Registro"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEstadoExamenMedico, row.descripcionEstadoExamenMedico);
			},
			"title" : "Estado Ex. Médico"
		}, {
			"data" : 'codigoAlumno',
			"title" : "Código de Alumno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoAlumno, row.descripcionTipoAlumno);
			},
			"title" : "Tipo de Alumno"
		}, {
			"data" : function(row) {
				return row.apellidoPaterno + " " + row.apellidoMaterno;
			},
			"title" : "Apellidos"
		}, {
			"data" : function(row) {
				return row.nombres;
			},
			"title" : "Nombres"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCampania, row.descripcionCampania);
			},
			"title" : "Campaña"
		}, {
			"data" : "descripcionEscuela",
			"title" : "Escuela"
		} ]
	});

	$local.$tablaConsultaAdministrativaExamenMedico.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaAdministrativaExamenMedico.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaAdministrativaExamenMedico.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaAdministrativaExamenMedico.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$facultades.on("change", function() {
		var codigoFacultad = $(this).val();
		if (codigoFacultad == null || codigoFacultad == undefined || codigoFacultad == -1) {
			$local.$escuelas.find("option:not(:eq(0))").remove();
			return;
		}
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "mantenimiento/escuela/facultad/" + codigoFacultad,
			beforeSend : function(xhr) {
				$local.$escuelas.find("option:not(:eq(0))").remove();
				$local.$escuelas.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Escuelas</span>")
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(escuelas) {
				$.each(escuelas, function(i, escuela) {
					$local.$escuelas.append($("<option />").val(this.codigoEscuela).text(this.codigoEscuela + " - " + this.descripcion));
				});
			},
			complete : function() {
				$local.$escuelas.parent().find(".cargando").remove();
			}
		});
	});

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaAdministrativaExamenMedico.serializeJSON();
		if ($funcionUtil.camposVacios($formCriterioBusquedaAdministrativaExamenMedico)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Información", "info");
			return;
		}
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaTxn.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaTxn.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/psicologico/administrativa?accion=buscarPorCriterioBusquedaAdministrativa",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaConsultaAdministrativaExamenMedico.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoRadiologico) {
				if (examenesMedicoRadiologico.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdministrativaExamenMedico.rows.add(examenesMedicoRadiologico).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
		
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/psicologico/administrativa?accion=buscarPorCriterioBusquedaAdministrativa",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.clear().draw();
				$local.$buscar1.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoPsicologia) {
				if (examenesMedicoPsicologia.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.rows.add(examenesMedicoPsicologia).draw();
			},
			complete : function() {
				$local.$buscar1.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				$local.$numeroRegistro.select();
			}
		});
	});

	$formCriterioBusquedaAdministrativaExamenMedico.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});

	// Para Actualizacion de Resultado
	$formResultadoExamenMedico = $("#formResultadoExamenMedico");

	$local.$modalResultado.PopupWindow({
		title : "Resultado de Examen Médico Psicológico",
		autoOpen : false,
		modal : false,
		height : $variableUtil.altoModalResultadoPsicologico,
		width : $variableUtil.anchoModalResultadoPsicologico
	});

	$local.$modalResultado.on("open.popupwindow", function() {
		$formResultadoExamenMedico.find("input:not([disabled]):first").focus();
	});

	$local.$modalResultado.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.numeroRegistroSeleccionado = 0;
		$local.anioSeleccionado = "";
	});

	$local.$tablaConsultaAdministrativaExamenMedico.children("tbody").on("click", ".actualizar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoPsicologico = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data();
		var apellidosNombres = examenMedicoPsicologico.nombres + ", " + examenMedicoPsicologico.apellidoPaterno + " " + examenMedicoPsicologico.apellidoMaterno;
		$local.numeroRegistroSeleccionado = examenMedicoPsicologico.numeroRegistro;
		$local.$inputCodigo.val(examenMedicoPsicologico.codigoAlumno);
		$local.$inputApellidosNombres.val(apellidosNombres);
		$funcionUtil.aniadirTitleParaTooltip($local.$inputApellidosNombres, apellidosNombres);
		var criterioBusqueda = {
			numeroRegistro : $local.numeroRegistroSeleccionado,
		};
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/psicologico?accion=buscarResultadoRegularPorNumeroRegistro",
			data : criterioBusqueda,
			beforeSend : function() {
				$(this).attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoPsicologico) {
				if (examenesMedicoPsicologico.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				examenMedicoPsicologico = examenesMedicoPsicologico[0];
				$funcionUtil.prepararFormularioActualizacion($formResultadoExamenMedico);
				$funcionUtil.llenarFormulario(examenMedicoPsicologico, $formResultadoExamenMedico);
				var r = $local.$resultadoPsicologico.filter(":checked").val();
				if (r == "O") {
					$local.$divObservacion.removeClass("hidden");
				} else {
					$local.$divObservacion.addClass("hidden");
				}
				$local.$modalResultado.PopupWindow("open");
			},
			complete : function() {
				$(this).attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$registrarResultado.on("click", function() {
		if (!$formResultadoExamenMedico.valid()) {
			return;
		}
		var examenMedicoPsicologico = $formResultadoExamenMedico.serializeJSON();
		examenMedicoPsicologico.numeroRegistro = $local.numeroRegistroSeleccionado;
		examenMedicoPsicologico.observacion = $local.$observacion.val();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "examenmedico/psicologia",
			data : JSON.stringify(examenMedicoPsicologico),
			beforeSend : function(xhr) {
				$local.$registrarResultado.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formResultadoExamenMedico);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formResultadoExamenMedico);
				}
			},
			success : function(response) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var examenMedicoPsicologico = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data();
				var row = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data(examenMedicoPsicologico).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalResultado.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarResultado.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$formResultadoExamenMedico.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});

	$local.$resultadoPsicologico.on("change", function() {
		var resultadoPsicologico = $(this).val();
		switch (resultadoPsicologico) {
		case "N":
			$local.$divObservacion.addClass("hidden");
			$local.$observacion.val("");
			break;
		case "O":
			$local.$divObservacion.removeClass("hidden");
			$local.$observacion.val("PRESENTA ALTOS INDICADORES PATOLOGICOS DE PERSONALIDAD");
			break;
		}
	});

	$local.$editarObservacion.on("click", function() {
		var disabled = $local.$observacion.prop("disabled");
		$local.$observacion.prop("disabled", !disabled);
	});

	// Para Eliminacion de Resultado
	$local.$tablaConsultaAdministrativaExamenMedico.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoPsicologico = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar el Examen Médico Psicológico del Alumno <b>'" + examenMedicoPsicologico.codigoAlumno + " " + examenMedicoPsicologico.tipoAlumno + " - " + examenMedicoPsicologico.descripcionTipoAlumno + "; " + examenMedicoPsicologico.apellidoPaterno + " " + examenMedicoPsicologico.apellidoMaterno + ", " + examenMedicoPsicologico.nombres + "'<b/>?",
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
									url : $variableUtil.root + "examenmedico/psicologia",
									data : JSON.stringify(examenMedicoPsicologico),
									autoclose : true,
									beforeSend : function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).remove().draw(false);
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
									case 400:
										$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
										break;
									case 409:
										var mensaje = $funcionUtil.obtenerMensajeError("El Alumno <b>" + alumno.codigoAlumno + " - " + alumno.apellidoPaterno + " " + alumno.apellidoMaterno + ", " + alumno.nombres + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
										$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
										break;
									}
								});
							},
							buttons : {
								close : {
									text : 'Aceptar',
									keys : [ 'enter' ]
								}
							}
						});
					},
				},
				Cancelar : {
					keys : [ 'esc' ],
					btnClass : "btn-primary",
				},
			}
		});
	});
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//						CONSULTA DETALLADA DE PSICOLOGIA
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	$local.tablaConsultaAdministrativaExamenMedicoDetalle = $local.$tablaConsultaAdministrativaExamenMedicoDetalle.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdministrativaExamenMedicoDetalle.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdministrativaExamenMedicoDetalle);
		},
		order : [ [ 1, "desc" ] ],
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}],
		"columns" : [ {
			"data" : "fechaRegistro",
			"title" : "Fecha Registro"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idCampania, row.descripcionCampania);
			},
			"title" : "Campaña"
		}, {
			"data" : 'codigoAlumno',
			"title" : "Código de Alumno"
		}, {
			"data" : function(row) {
				return row.apellidoPaterno + " " + row.apellidoMaterno;
			},
			"title" : "Apellidos"
		}, {
			"data" : function(row) {
				return row.nombres;
			},
			"title" : "Nombres"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idResultadoPsicologico, row.descResultadoPsicologico);
			},
			"title" : "Resultado"
		}]
	});
	
	$local.$tablaConsultaAdministrativaExamenMedicoDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaAdministrativaExamenMedicoDetalle.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaAdministrativaExamenMedicoDetalle.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaAdministrativaExamenMedicoDetalle.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});
		
	$local.$buscar1.on("click", function() {
		var criterioBusqueda = $formCriterioBusquedaAdministrativaDetallada.serializeJSON();
		if (!$formCriterioBusquedaAdministrativaDetallada.valid()) {
			return;
		}
		console.log(criterioBusqueda);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/psicologico/administrativa?accion=buscarPorCriterioBusquedaAdministrativa",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.clear().draw();
				$local.$buscar1.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoPsicologia) {
				if (examenesMedicoPsicologia.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.rows.add(examenesMedicoPsicologia).draw();
			},
			complete : function() {
				$local.$buscar1.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				$local.$numeroRegistro.select();
			}
		});
	});
	
	$formCriterioBusquedaAdministrativaDetallada.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar1.trigger("click");
			return false;
		}
	});

});