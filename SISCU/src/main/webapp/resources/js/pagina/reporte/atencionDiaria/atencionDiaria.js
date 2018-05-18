$(document).ready(function() {

	var $local = {
		tablaReporteAtencionesDiariaDetalle : "",
		$tablaReporteAtencionesDiariaDetalle : $("#tablaReporteAtencionesDiariaDetalle"),
		tablaReporteAtencionesDiariaGeneral : "",
		$tablaReporteAtencionesDiariaGeneral : $("#tablaReporteAtencionesDiariaGeneral"),
		$buscar : $("#buscar"),
		$exportar : $("#exportar"),
		$tiposExamenMedico : $("#tiposExamenMedico"),
		$campanias : $("#campanias"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda")
	}

	$formReporteAtencionDiaria = $("#formReporteAtencionDiaria");

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda);
	$funcionUtil.crearSelect2($local.$campanias);
	$funcionUtil.crearSelect2($local.$tiposExamenMedico);

	$local.tablaReporteAtencionesDiariaDetalle = $local.$tablaReporteAtencionesDiariaDetalle.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaReporteAtencionesDiariaDetalle.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporteAtencionesDiariaDetalle);
		},
		order : [ [ 0, "desc" ] ],
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3, 4, 5, 6 ],
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
			"data" : null,
			"title" : "Núm. Reg."
		}, {
			"data" : "fechaGeneracionNumeroRegistro",
			"title" : "Fecha Num. Reg."
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
			"data" : "descripcionEscuela",
			"title" : "Escuela"
		} ]
	});

	$local.$tablaReporteAtencionesDiariaDetalle.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaReporteAtencionesDiariaDetalle.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaReporteAtencionesDiariaDetalle.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaReporteAtencionesDiariaDetalle.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.tablaReporteAtencionesDiariaGeneral = $local.$tablaReporteAtencionesDiariaGeneral.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaReporteAtencionesDiariaGeneral.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaReporteAtencionesDiariaGeneral);
		},
		"order" : [ [ 2, "desc" ] ],
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		} ],
		"columns" : [ {
			"data" : "soloFechaGeneracionNumeroRegistro",
			"title" : "Fecha Num. Reg."
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoFacultad, row.descripcionFacultad);
			},
			"title" : "Facultad"
		}, {
			"data" : "cantidad",
			"title" : "Cantidad"
		} ],
		"footerCallback" : function(row, data, start, end, display) {
			var tieneData = $local.tablaReporteAtencionesDiariaGeneral == "" ? false : $local.tablaReporteAtencionesDiariaGeneral.data().any();
			var api = this.api(), data;
			if (tieneData) {
				cantidadTotal = api.column(2).data().reduce(function(a, b) {
					return parseInt(a) + parseInt(b);
				}, 0);
				$(api.column(2).footer()).html(cantidadTotal);
			} else {
				$(api.column(2).footer()).html("");
			}
		},
	});

	$local.$tablaReporteAtencionesDiariaGeneral.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaReporteAtencionesDiariaGeneral.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaReporteAtencionesDiariaGeneral.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaReporteAtencionesDiariaGeneral.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		var criterioBusqueda = $formReporteAtencionDiaria.serializeJSON();
		if ($funcionUtil.camposVacios($formReporteAtencionDiaria)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Información", "info");
			return;
		}
		if (!$formReporteAtencionDiaria.valid()) {
			return;
		}
		var rangoFecha = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFecha.fechaInicio;
		criterioBusqueda.fechaFin = rangoFecha.fechaFin;
		criterioBusqueda.tipoReporte = "DETALLE";
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/atencion/diaria?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaReporteAtencionesDiariaDetalle.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(atencionesDiarias) {
				if (atencionesDiarias.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaReporteAtencionesDiariaDetalle.rows.add(atencionesDiarias).draw();
			},
			complete : function() {
			}
		});

		criterioBusqueda.tipoReporte = "GENERAL";
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "reporte/atencion/diaria?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.tablaReporteAtencionesDiariaGeneral.clear().draw();
			},
			success : function(atencionesDiarias) {
				if (atencionesDiarias.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaReporteAtencionesDiariaGeneral.rows.add(atencionesDiarias).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$exportar.on("click", function() {
		var criterioBusqueda = $formReporteAtencionDiaria.serializeJSON();
		if ($funcionUtil.camposVacios($formReporteAtencionDiaria)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Información", "info");
			return;
		}
		if (!$formReporteAtencionDiaria.valid()) {
			return;
		}
		var rangoFecha = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusqueda.fechaInicio = rangoFecha.fechaInicio;
		criterioBusqueda.fechaFin = rangoFecha.fechaFin;
		var descripcionRangoFechas = $local.$rangoFechaBusqueda.val();
		criterioBusqueda.descripcionFechaGeneracionNumeroRegistro = descripcionRangoFechas == null || descripcionRangoFechas == undefined || descripcionRangoFechas == "" ? "TODOS" : descripcionRangoFechas;
		criterioBusqueda.descripcionExamenMedico = $local.$tiposExamenMedico.find("option:selected").text();
		criterioBusqueda.descripcionCampania = $local.$campanias.find("option:selected").text();
		var paramCriterioBusqueda = $.param(criterioBusqueda);
		window.location.href = $variableUtil.root + "reporte/atencion/diaria?accion=exportar&" + paramCriterioBusqueda;
	});

});