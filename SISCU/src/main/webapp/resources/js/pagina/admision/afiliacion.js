$(document).ready(function() {

	var $local = {
		// Para Consulta
		$tablaConsultaAdmision : $("#tablaConsultaAdmision"),
		tablaConsultaAdmision : "",
		$filaSeleccionada : "",
		$estamentos : $("#estamentos"),
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
		$codigoAlumno : $("#codigoAlumno"),
		numeroRegistroSeleccionado : "",
		$divCodigoAlumno : $("#divCodigoAlumno"),
		$registrarResultado : $("#registrarResultado"),
		$editarObservacion : $("#editarObservacion"),
		
		/* Datos */
		$inputCodigo : $("#codigo"),
		$inputApellidosNombres : $("#apellidosNombres")
	};

	// Para Consulta
	$formCriterioConsultaAdmision = $("#formCriterioConsultaAdmision");

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);

	$funcionUtil.crearSelect2($local.$estamentos);
	$funcionUtil.crearSelect2($local.$campanias);
	$funcionUtil.crearSelect2($local.$tiposAlumno);
	$funcionUtil.crearSelect2($local.$escuelas);

	var esAdmin = $local.$administrador.length > 0;

	$local.tablaConsultaAdmision = $local.$tablaConsultaAdmision.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdmision.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdmision);
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

	$local.$tablaConsultaAdmision.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaAdmision.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaAdmision.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaAdmision.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formCriterioConsultaAdmision.serializeJSON();
		if ($funcionUtil.camposVacios($formCriterioConsultaAdmision)) {
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
				$local.tablaConsultaAdmision.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoRadiologico) {
				if (examenesMedicoRadiologico.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdmision.rows.add(examenesMedicoRadiologico).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$formCriterioConsultaAdmision.find("input").keypress(function(event) {
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

	$local.$tablaConsultaAdmision.children("tbody").on("click", ".actualizar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoPsicologico = $local.tablaConsultaAdmision.row($local.$filaSeleccionada).data();
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

	$local.$estamentos.on("change", function() {
		var opcion = $(this).val();
		console.log(opcion);
		switch (opcion) {
		case "1":
			$local.$divCodigoAlumno.addClass("hidden");
			$local.$codigoAlumno.val("");
			break;
		case "2":
			$local.$divCodigoAlumno.removeClass("hidden");
			break;
		}
	});

	$local.$editarObservacion.on("click", function() {
		var disabled = $local.$observacion.prop("disabled");
		$local.$observacion.prop("disabled", !disabled);
	});


});