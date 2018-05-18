$(document).ready(function() {

	var $local = {
		$tablaRegistroResultado : $("#tablaRegistroResultado"),
		tablaRegistroResultado : "",
		$filaSeleccionada : "",
		$resultadosRadiologico : $("#resultadosRadiologico"),
		$registrarResultado : $("#registrarResultado"),
		$modalResultado : $("#modalResultado"),
		$observacion : $("#observacion"),
		/* Datos */
		$inputCodigo : $("#codigo"),
		$inputApellidosNombres : $("#apellidosNombres"),
		/* Seleccion */
		numeroRegistroSeleccionado : 0,
		anioSeleccionado : ""
	};

	/* Variable Global */
	$formResultadoExamenMedico = $("#formResultadoExamenMedico");

	$funcionUtil.crearSelect2($local.$resultadosRadiologico, "Seleccione un Resultado");

	$local.$modalResultado.PopupWindow({
		title : "Resultado de Examen Médico Radiológico",
		autoOpen : false,
		modal : false,
		height : $variableUtil.altoModalResultadoRadiologico,
		width : $variableUtil.anchoModalResultadoRadiologico
	});

	$local.$modalResultado.on("open.popupwindow", function() {
		$formResultadoExamenMedico.find("input:not([disabled]):first").focus();
		$local.$resultadosRadiologico.trigger("change");
		$local.$resultadosRadiologico.val("N").trigger("change.select2");
	});

	$local.$modalResultado.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.numeroRegistroSeleccionado = 0;
		$local.anioSeleccionado = "";
	});

	$local.tablaRegistroResultado = $local.$tablaRegistroResultado.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "examenmedico/radiologico?accion=buscarTomaRadiologica",
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
			"targets" : [ 2, 3, 4, 5, 6, 7, 8, 9 ],
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
			"data" : "codigoAlumno",
			"title" : "Código de Alumno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoAlumno, row.descripcionTipoAlumno);
			},
			"title" : "Tipo de Alumno"
		}, {
			"data" : "edad",
			"title" : "Edad"
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
		var examenMedicoRadiologico = $local.tablaRegistroResultado.row($local.$filaSeleccionada).data();
		var apellidosNombres = examenMedicoRadiologico.nombres + ", " + examenMedicoRadiologico.apellidoPaterno + " " + examenMedicoRadiologico.apellidoMaterno;
		$local.numeroRegistroSeleccionado = examenMedicoRadiologico.numeroRegistro;
		$local.anioSeleccionado = examenMedicoRadiologico.anio;
		$local.$inputCodigo.val(examenMedicoRadiologico.codigoAlumno);
		$local.$inputApellidosNombres.val(apellidosNombres);
		$funcionUtil.aniadirTitleParaTooltip($local.$inputApellidosNombres, apellidosNombres);
		$local.$modalResultado.PopupWindow("open");
	});

	$local.$resultadosRadiologico.on("change", function() {
		var resultado = $(this).val();
		$local.$observacion.prop("disabled", resultado == null ? true : resultado == "N" || resultado == "C");
		$local.$observacion.val((resultado == "N" || resultado == "C") ? "" : $local.$observacion.val());
	});

	$local.$registrarResultado.on("click", function() {
		if (!$formResultadoExamenMedico.valid()) {
			return;
		}
		var examenMedicoRadiologico = $formResultadoExamenMedico.serializeJSON();
		examenMedicoRadiologico.numeroRegistro = $local.numeroRegistroSeleccionado;
		examenMedicoRadiologico.anio = $local.anioSeleccionado;
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "examenmedico/radiologico?accion=registrarExamenMedicoRegularRadiologico",
			data : JSON.stringify(examenMedicoRadiologico),
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