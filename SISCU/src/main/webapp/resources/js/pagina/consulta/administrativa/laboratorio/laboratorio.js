$(document).ready(function() {

	var $local = {
		// Para Consulta
		$tablaConsultaAdministrativaExamenMedico : $("#tablaConsultaAdministrativaExamenMedico"),
		$tablaConsultaAdministrativaExamenMedicoDetalle : $("#tablaConsultaAdministrativaExamenMedicoDetalle"),
		tablaConsultaAdministrativaExamenMedico : "",
		$filaSeleccionada : "",
		$facultades : $("#facultades"),
		$campanias : $("#campanias"),
		$anios : $("#anios"),
		$estadosExamenMedico : $("#estadosExamenMedico"),
		$tiposAlumno : $("#tiposAlumno"),
		$escuelas : $("#escuelas"),
		$buscar : $("#buscar"),
		$buscar1 : $("#buscar1"),
		$numeroRegistro : $("#numeroRegistro"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$administrador : $("#administrador"),

		// Para Actualizacion de Resultado
		$modalResultado : $("#modalResultado"),
		$indicadoresRPR : $("#indicadoresRPR"),
		$gruposSanguineo : $("#gruposSanguineo"),
		$factoresRh : $("#factoresRh"),
		$diluciones : $("#diluciones"),
		$indicadoresHemograma : $("#indicadoresHemograma"),
		$divDilucion : $("#divDilucion"),
		$hemoglobina : $("#hemoglobina"),
		$estadoHemoglobina : $("#estadoHemoglobina"),
		$divObservacion : $("#divObservacion"),
		$observacion : $("#observacion"),
		$registrarResultado : $("#registrarResultado"),
		
		// Registro
		numeroRegistroSeleccionado : 0,
		anioSeleccionado : "",
		tipoAlumnoSeleccionado : "",

		// Formulario
		$divFormulario : $(".div-formulario"),
		
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
	$funcionUtil.crearSelect2($local.$estadosExamenMedico);
	$funcionUtil.crearSelect2($local.$anios);
	$funcionUtil.crearSelect2($local.$tiposAlumno);
	$funcionUtil.crearSelect2($local.$campanias);
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
		order : [ [ 1, "desc" ] ],
		"columnDefs" : [ {
			"targets" : [ 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 1,
			"className" : "all filtrable dt-center",
			"render" : function(data, type, row, meta) {
				var texto = row.numeroRegistro != undefined && row.numeroRegistro != null && row.numeroRegistro > 0 ? row.numeroRegistro : "NO ATENDIDO";
				return "<label class='label label-primary label-size-12'>" + texto + "</label>";
			}
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				var elemento = "";
				if (esAdmin) {
					if (row.idEstadoExamenMedico == 'C') {
						elemento = $variableUtil.botonActualizar;
					}
				}
				return elemento;
			}
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
		}, {
			"data" : 'numeroRegistro',
			"title" : "Núm. Registro"
		}, {
			"data" : "anio",
			"title" : "Año"
		}, {
			"data" : "fechaGeneracionNumeroRegistro",
			"title" : "Fecha Num. Reg."
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
		if (codigoFacultad == null || codigoFacultad == -1) {
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
		console.log(criterioBusqueda);
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaTxn.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaTxn.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/laboratorio/administrativa?accion=buscarPorCriterioBusquedaAdministrativa",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaConsultaAdministrativaExamenMedico.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoLaboratorio) {
				if (examenesMedicoLaboratorio.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdministrativaExamenMedico.rows.add(examenesMedicoLaboratorio).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				$local.$numeroRegistro.select();
			}
		});
		
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/laboratorio/administrativa?accion=buscarPorCriterioBusquedaAdministrativa",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.clear().draw();
				$local.$buscar1.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoLaboratorio) {
				if (examenesMedicoLaboratorio.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.rows.add(examenesMedicoLaboratorio).draw();
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

	$funcionUtil.crearSelect2($local.$indicadoresRPR, "Seleccione un RPR");
	$funcionUtil.crearSelect2($local.$diluciones, "Seleccione una Dilución");
	$funcionUtil.crearSelect2($local.$indicadoresHemograma, "Seleccione un Hemograma");
	$funcionUtil.crearSelect2($local.$gruposSanguineo, "Seleccione un Gpo. Sanguíneo");
	$funcionUtil.crearSelect2($local.$factoresRh, "Seleccione un Factor de RH");

	$local.$modalResultado.PopupWindow({
		title : "Resultado de Examen Médico de Laboratorio",
		autoOpen : false,
		modal : false,
		height : $variableUtil.altoModalResultadoLaboratorio,
		width : $variableUtil.anchoModalResultadoLaboratorio
	});

	$local.$modalResultado.on("open.popupwindow", function() {
		$formResultadoExamenMedico.find("input:not([disabled]):first").focus();
	});

	$local.$modalResultado.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.numeroRegistroSeleccionado = 0;
		$local.anioSeleccionado = "";
		$local.idSexoSeleccionado = "";
		$local.tipoAlumnoSeleccionado = "";
		$local.$divFormulario.addClass("hidden");
	});

	$local.$tablaConsultaAdministrativaExamenMedico.children("tbody").on("click", ".actualizar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoLaboratorio = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data();
		var apellidosNombres = examenMedicoLaboratorio.nombres + ", " + examenMedicoLaboratorio.apellidoPaterno + " " + examenMedicoLaboratorio.apellidoMaterno;
		$local.numeroRegistroSeleccionado = examenMedicoLaboratorio.numeroRegistro;
		$local.anioSeleccionado = examenMedicoLaboratorio.anio;
		$local.idSexoSeleccionado = examenMedicoLaboratorio.idSexo;
		$local.tipoAlumnoSeleccionado = examenMedicoLaboratorio.tipoAlumno;
		$local.$inputCodigo.val(examenMedicoLaboratorio.codigoAlumno);
		$local.$inputApellidosNombres.val(apellidosNombres);
		$funcionUtil.aniadirTitleParaTooltip($local.$inputApellidosNombres, apellidosNombres);
		$local.$divFormulario.addClass("hidden");
		$("#" + $local.tipoAlumnoSeleccionado).removeClass("hidden");
		var criterioBusqueda = {
			numeroRegistro : $local.numeroRegistroSeleccionado,
			anio : $local.anioSeleccionado
		};
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/laboratorio?accion=buscarResultadoPorNumeroRegistroAnio&tipoAlumno=" + $local.tipoAlumnoSeleccionado,
			data : criterioBusqueda,
			beforeSend : function() {
				$(this).attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoLaboratorio) {
				if (examenesMedicoLaboratorio.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				examenMedicoLaboratorio = examenesMedicoLaboratorio[0];
				$funcionUtil.prepararFormularioActualizacion($formResultadoExamenMedico);
				$funcionUtil.llenarFormulario(examenMedicoLaboratorio, $formResultadoExamenMedico);
				$local.$indicadoresRPR.trigger("change");
				$local.$indicadoresHemograma.trigger("change");
				$local.$hemoglobina.trigger("keyup");
				$("#" + $local.tipoAlumnoSeleccionado).removeClass("hidden");
				$local.$modalResultado.PopupWindow("open");
			},
			complete : function() {
				$(this).attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});

	});

	$local.$hemoglobina.on("keyup", function(event) {
		if (event.which == 13) {
			event.preventDefault();
		}
		$funcionUtil.determinarEstadoHemoglobina($(this).val(), $local.idSexoSeleccionado, $local.$estadoHemoglobina);
	});

	$local.$indicadoresRPR.on("change", function() {
		$local.$diluciones.prop("disabled", ($(this).val() != "R"));
		$local.$diluciones.val(($(this).val() == "N") ? "" : $local.$diluciones.val()).trigger("change.select2");
	});

	$local.$indicadoresHemograma.on("change", function() {
		$local.$observacion.prop("disabled", ($(this).val() != "O"));
		$local.$observacion.val(($(this).val() == "N") ? "" : $local.$observacion.val());
	});

	$local.$registrarResultado.on("click", function() {
		if (!$formResultadoExamenMedico.valid()) {
			return;
		}
		var examenMedicoLaboratorio = $formResultadoExamenMedico.serializeJSON();
		examenMedicoLaboratorio.numeroRegistro = $local.numeroRegistroSeleccionado;
		examenMedicoLaboratorio.anio = $local.anioSeleccionado;
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "examenmedico/laboratorio?accion=registrar&tipoAlumno=" + $local.tipoAlumnoSeleccionado,
			data : JSON.stringify(examenMedicoLaboratorio),
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
				var examenMedicoLaboratorio = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data();
				var row = $local.tablaConsultaAdministrativaExamenMedico.row($local.$filaSeleccionada).data(examenMedicoLaboratorio).draw();
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//						CONSULTA DETALLADA DE LABORATORIO
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
			"targets" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 0,
			"className" : "all filtrable dt-center",
			"render" : function(data, type, row, meta) {
				var texto = row.numeroRegistro != undefined && row.numeroRegistro != null && row.numeroRegistro > 0 ? row.numeroRegistro : "NO ATENDIDO";
				return "<label class='label label-primary label-size-12'>" + texto + "</label>";
			}
		} ],
		"columns" : [ {
			"data" : 'numeroRegistro',
			"title" : "Núm. Registro"
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
				return $funcionUtil.unirCodigoDescripcion(row.idRPR, row.descripcionRPR);
			},
			"title" : "RPR"
		}, {
			"data" : function(row) {
				if (row.dilucion == null){
					return " - "
				}
				else{
					return "1 : "+row.dilucion;
				}
			},
			"title" : "Dilución"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idHemograma, row.descripcionHemograma);
			},
			"title" : "Hemograma"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idGrupoSanguineo, row.descGrupoSanguineo);
			},
			"title" : "Gpo. Sanguíneo"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idFactorRh, row.descFactorRh);
			},
			"title" : "Factor RH"
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
			url : $variableUtil.root + "consulta/laboratorio/administrativa?accion=buscarPorCriterioBusquedaAdministrativa",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.clear().draw();
				$local.$buscar1.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoLaboratorio) {
				if (examenesMedicoLaboratorio.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdministrativaExamenMedicoDetalle.rows.add(examenesMedicoLaboratorio).draw();
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