$(document).ready(function() {

	var $local = {
		$tablaRegistroResultado : $("#tablaRegistroResultado"),
		tablaRegistroResultado : "",
		$filaSeleccionada : "",
		$registrarResultado : $("#registrarResultado"),
		$modalResultado : $("#modalResultado"),

		// Combo
		$indicadoresRPR : $("#indicadoresRPR"),
		$diluciones : $("#diluciones"),
		$indicadoresHemograma : $("#indicadoresHemograma"),
		$gruposSanguineo : $("#gruposSanguineo"),
		$factoresRh : $("#factoresRh"),

		$divDilucion : $("#divDilucion"),
		$hemoglobina : $("#hemoglobina"),
		$estadoHemoglobina : $("#estadoHemoglobina"),
		$divObservacion : $("#divObservacion"),
		$observacion : $("#observacion"),

		// Registro
		numeroRegistroSeleccionado : 0,
		anioSeleccionado : "",
		idSexoSeleccionado : "",
		tipoAlumnoSeleccionado : "",

		// Formulario
		$divFormulario : $(".div-formulario"),
		
		/* Datos */
		$inputCodigo : $("#codigo"),
		$inputApellidosNombres : $("#apellidosNombres")
	};

	/* Variable Global */
	$formResultadoExamenMedico = $("#formResultadoExamenMedico");
	
	$funcionUtil.crearSelect2($local.$indicadoresRPR, "Seleccione un RPR");
	$funcionUtil.crearSelect2($local.$diluciones, "Seleccione una Dilución");
	$funcionUtil.crearSelect2($local.$indicadoresHemograma, "Seleccione un Hemograma");
	$funcionUtil.crearSelect2($local.$gruposSanguineo, "Seleccione un Gpo. Sanguíneo");
	$funcionUtil.crearSelect2($local.$factoresRh, "Seleccione un Factor de RH");

	$local.$tablaRegistroResultado.on('xhr.dt', function(e, settings, json, xhr) {
		if (xhr.status == 500) {
			$local.tablaRegistroResultado.clear().draw();
		}
	});
	
	$local.$modalResultado.PopupWindow({
		title : "Resultado de Examen Médico de Laboratorio",
		autoOpen : false,
		modal : false,
		height : $variableUtil.altoModalResultadoLaboratorio,
		width : $variableUtil.anchoModalResultadoLaboratorio
	});

	$local.$modalResultado.on("open.popupwindow", function() {
		$formResultadoExamenMedico.find("input:not([disabled]):first").focus();
		$local.$hemoglobina.trigger("keyup");
		$local.$indicadoresRPR.trigger("change");
		$local.$indicadoresHemograma.trigger("change");
		$local.$indicadoresRPR.val("N").trigger("change.select2");
		$local.$indicadoresHemograma.val("N").trigger("change.select2");
		$local.$gruposSanguineo.val("O").trigger("change.select2");
		$local.$factoresRh.val("P").trigger("change.select2");
	});

	$local.$modalResultado.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.numeroRegistroSeleccionado = 0;
		$local.anioSeleccionado = "";
		$local.idSexoSeleccionado = "";
		$local.tipoAlumnoSeleccionado = "";
	});

	$local.tablaRegistroResultado = $local.$tablaRegistroResultado.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "examenmedico/laboratorio?accion=buscarNumeroRegistroGenerado",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No resultados por registrar."
		},
		"initComplete" : function() {
			$local.$tablaRegistroResultado.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaRegistroResultado);
		},
		"columnDefs" : [ {
			"targets" : [ 2, 3, 4, 5, 6, 7, 8, 9, 10 ],
			"className" : "all filtrable",
		}, {
			"targets" : 1,
			"className" : "all dt-center filtrable",
			"render" : function(data, type, row, meta) {
				return "<label class='label label-primary label-size-12'>" + row.numeroRegistro + "</label>";
			}
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonRegistrar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : "Acción"
		}, {
			"data" : "numeroRegistro",
			"title" : "Num. Reg."
		}, {
			"data" : "fechaGeneracionNumeroRegistro",
			"title" : "Fecha Num. Reg."
		}, {
			"data" : "codigoAlumno",
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
			"data" : "edad",
			"title" : "Edad"
		}, {
			"data" : "descripcionFacultad",
			"title" : "Facultad"
		}, {
			"data" : "descripcionEscuela",
			"title" : "Escuela"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		} ]
	});

	$local.$tablaRegistroResultado.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaRegistroResultado.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$formResultadoExamenMedico.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$registrarResultado.trigger("click");
			return false;
		}
	});

	$local.$tablaRegistroResultado.children("tbody").on("click", ".registrar", function() {
		$funcionUtil.prepararFormularioRegistro($formResultadoExamenMedico);
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoLaboratorio = $local.tablaRegistroResultado.row($local.$filaSeleccionada).data();
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
		$local.$modalResultado.PopupWindow("open");
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
				$local.tablaRegistroResultado.row($local.$filaSeleccionada).remove().draw(false);
				$local.$modalResultado.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarResultado.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

});