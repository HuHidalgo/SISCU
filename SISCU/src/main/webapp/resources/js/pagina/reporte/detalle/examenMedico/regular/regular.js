$(document).ready(function() {

	var $local = {
		$campanias : $("#campanias"),
		$facultades : $("#facultades"),
		$escuelas : $("#escuelas"),
		$buscar : $("#buscar"),
		$tablaReporteDetalleResultadoExamenMedico : $("#tablaReporteDetalleResultadoExamenMedico"),
		tablaReporteDetalleResultadoExamenMedico : "",
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		idCampaniaSeleccionado : ""
	}

	$formCriterioBusquedaReporte = $("#formCriterioBusquedaReporte");

	$funcionUtil.crearDateRangePickerConsulta($local.$rangoFechaBusqueda);
	$funcionUtil.crearSelect2($local.$campanias);
	$funcionUtil.crearSelect2($local.$facultades);
	$funcionUtil.crearSelect2($local.$escuelas);

	$local.tablaReporteDetalleResultadoExamenMedico = $local.$tablaReporteDetalleResultadoExamenMedico.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaReporteDetalleResultadoExamenMedico.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporteDetalleResultadoExamenMedico);
		},
		order : [ [ 1, "desc" ] ],
		"columnDefs" : [ {
			"targets" : 0,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				if (row.fechaAtencionExamenLaboratorio != null && row.fechaAtencionExamenRadiologico != null && row.fechaAtencionExamenPsicologico != null) {
					return $variableUtil.botonExportarDocx;
				}
				return "";
			}
		}, {
			"targets" : [ 1, 2, 3, 4, 5, 14, 15 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 6,
			"className" : "all filtrable",
			"render" : function(data, type, row, meta) {
				if (row.fechaAtencionExamenLaboratorio == null || row.fechaAtencionExamenLaboratorio == undefined) {
					return "<label class='label label-info label-size-12'>NO ATENDIDO</label>";
				} else {
					return row.fechaAtencionExamenLaboratorio;
				}
			}
		}, {
			"targets" : 7,
			"className" : "all filtrable celdaRPR",
			"render" : function(data, type, row, meta) {
				if (row.idRPR == null || row.idRPR == undefined) {
					return $variableUtil.labelNoRegistrado;
				} else if (row.idRPR == "R") {
					return $funcionUtil.unirCodigoDescripcion(row.idRPR, row.descripcionRPR) + " " + row.dilucion;
				} else {
					return $funcionUtil.unirCodigoDescripcion(row.idRPR, row.descripcionRPR);
				}
			}
		}, {
			"targets" : 8,
			"className" : "all filtrable celdaHemoglobina dt-right",
			"render" : function(data, type, row, meta) {
				if (row.hemoglobina == null || row.hemoglobina == undefined) {
					return $variableUtil.labelNoRegistrado;
				} else {
					return parseFloat(row.hemoglobina).toFixed(2);
				}
			}
		}, {
			"targets" : 9,
			"className" : "all filtrable celdaHemograma",
			"render" : function(data, type, row, meta) {
				if (row.idHemograma == null || row.idHemograma == undefined) {
					return $variableUtil.labelNoRegistrado;
				} else {
					return $funcionUtil.unirCodigoDescripcion(row.idHemograma, row.descripcionHemograma);
				}
			}
		}, {
			"targets" : 10,
			"className" : "all filtrable",
			"render" : function(data, type, row, meta) {
				if (row.fechaAtencionExamenRadiologico == null || row.fechaAtencionExamenRadiologico == undefined) {
					return "<label class='label label-info label-size-12'>NO ATENDIDO</label>";
				} else {
					return row.fechaAtencionExamenRadiologico;
				}
			}
		}, {
			"targets" : 11,
			"className" : "all filtrable celdaResultadoRadiologico",
			"render" : function(data, type, row, meta) {
				if (row.idResultadoRadiologico == null || row.idResultadoRadiologico == undefined) {
					return $variableUtil.labelNoRegistrado;
				} else if (row.idResultadoRadiologico != "G") {
					return $funcionUtil.unirCodigoDescripcion(row.idResultadoRadiologico, row.descripcionResultadoRadiologico);
				} else {
					return "<label class='label label-info label-size-12'>" + $funcionUtil.unirCodigoDescripcion(row.idResultadoRadiologico, row.descripcionResultadoRadiologico) + "</label>";
				}
			}
		}, {
			"targets" : 12,
			"className" : "all filtrable",
			"render" : function(data, type, row, meta) {
				if (row.fechaAtencionExamenPsicologico == null || row.fechaAtencionExamenPsicologico == undefined) {
					return "<label class='label label-info label-size-12'>NO ATENDIDO</label>";
				} else {
					return row.fechaAtencionExamenPsicologico;
				}
			}
		}, {
			"targets" : 13,
			"className" : "all filtrable celdaResultadoPsicologico",
			"render" : function(data, type, row, meta) {
				if (row.idResultadoPsicologico == null || row.idResultadoPsicologico == undefined) {
					return $variableUtil.labelNoRegistrado;
				} else {
					return $funcionUtil.unirCodigoDescripcion(row.idResultadoPsicologico, row.descripcionResultadoPsicologico);
				}
			}
		} ],
		"columns" : [ {
			"data" : null,
			"title" : "Acción"
		}, {
			"data" : "codigoAlumno",
			"title" : "Código"
		}, {
			"data" : function(row) {
				return row.apellidoPaterno + " " + row.apellidoMaterno;
			},
			"title" : "Apellidos"
		}, {
			"data" : "nombres",
			"title" : "Nombres"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		}, {
			"data" : "edad",
			"title" : "Edad"
		}, {
			"data" : null,
			"title" : "F. Laboratorio"
		}, {
			"data" : null,
			"title" : "RPR"
		}, {
			"data" : null,
			"title" : "Hemoglobina"
		}, {
			"data" : null,
			"title" : "Hemograma"
		}, {
			"data" : null,
			"title" : "F. Radiológico"
		}, {
			"data" : null,
			"title" : "Res. Radiológico"
		}, {
			"data" : null,
			"title" : "F. Psicológico"
		}, {
			"data" : null,
			"title" : "Res. Psicológico"
		}, {
			"data" : "descripcionFacultad",
			"title" : "Facultad"
		}, {
			"data" : "descripcionEscuela",
			"title" : "Escuela"
		} ],
		"createdRow" : function(row, data, dataIndex) {
			var $fila = $(row);
			if (data.idRPR != null) {
				var $celda = $fila.find(".celdaRPR");
				if (data.idRPR == "R") {
					$celda.addClass("color-red");
				} else {
					$celda.addClass("color-blue");
				}
			}
			if (data.hemoglobina != null && data.descripcionResultadoHemoglobina != null) {
				var $celda = $fila.find(".celdaHemoglobina");
				if (data.descripcionResultadoHemoglobina == "OBSERVADO") {
					$celda.addClass("color-red");
				} else {
					$celda.addClass("color-blue");
				}
			}
			if (data.idHemograma != null) {
				var $celda = $fila.find(".celdaHemograma");
				if (data.idHemograma == "O") {
					$celda.addClass("color-red");
				} else {
					$celda.addClass("color-blue");
				}
			}
			if (data.idResultadoRadiologico != null) {
				var $celda = $fila.find(".celdaResultadoRadiologico");
				if (data.idResultadoRadiologico == "N" || data.idResultadoRadiologico == "C") {
					$celda.addClass("color-blue");
				} else if (data.idResultadoRadiologico != "G") {
					$celda.addClass("color-red");
				}
			}
			if (data.idResultadoPsicologico != null) {
				var $celda = $fila.find(".celdaResultadoPsicologico");
				if (data.idResultadoPsicologico == "N") {
					$celda.addClass("color-blue");
				} else {
					$celda.addClass("color-red");
				}
			}
		},
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
		var criterioBusqueda = $formCriterioBusquedaReporte.serializeJSON();
		if ($funcionUtil.camposVacios($formCriterioBusquedaReporte)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Información", "info");
			return;
		}
		$local.idCampaniaSeleccionado = criterioBusqueda.idCampania;
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFechaTxn.fechaInicio;
		criterioBusqueda.fechaFin = rangoFechaTxn.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/detalle/resultado/examenMedico/regular?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaReporteDetalleResultadoExamenMedico.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(resultadosExamenesMedico) {
				if (resultadosExamenesMedico.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaReporteDetalleResultadoExamenMedico.rows.add(resultadosExamenesMedico).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaReporteDetalleResultadoExamenMedico.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaReporteDetalleResultadoExamenMedico.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaReporteDetalleResultadoExamenMedico.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaReporteDetalleResultadoExamenMedico.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$formCriterioBusquedaReporte.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});

	$local.$tablaReporteDetalleResultadoExamenMedico.children("tbody").on("click", ".exportar-docx", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var reporte = $local.tablaReporteDetalleResultadoExamenMedico.row($local.$filaSeleccionada).data();
		var criterioBusqueda = {};
		criterioBusqueda.codigoAlumno = reporte.codigoAlumno;
		criterioBusqueda.idCampania = $local.idCampaniaSeleccionado;
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		console.log(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/examenMedico?accion=exportarRegular&" + paramCriterioBusqueda;
	});

});