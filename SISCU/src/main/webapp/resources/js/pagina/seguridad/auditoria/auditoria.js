$(document).ready(function() {

	var $local = {
		$buscar : $("#buscar"),
		$rangoFechaBusqueda : $("#rangoFechaBusqueda"),
		$selectUsuario : $("#selectUsuario"),
		$selectTipoAuditoria : $("#selectTipoAuditoria"),
		$accionesAuditoria : $("#accionesAuditoria"),
		$tablaAuditoria : $("#tablaAuditoria"),
		tablaAuditoria : "",
		$seleccionUsuario : $("#seleccionUsuario"),
		$seleccionTipoAuditoria : $("#seleccionTipoAuditoria"),
		filtrosSeleccionables : {},
		arregloSiNo : [ "1", "0" ]
	};

	$('#comboUsuario').hide();
	$('#comboTipoAuditoria').hide();
	$formCriterioBusquedaAuditoria = $("#formCriterioBusquedaAuditoria");

	$funcionUtil.crearDateRangePickerSimple($local.$rangoFechaBusqueda);
	$funcionUtil.crearSelect2($local.$seleccionUsuario);
	$funcionUtil.crearSelect2($local.$seleccionTipoAuditoria);
	$funcionUtil.crearSelect2($local.$accionesAuditoria);

	$local.tablaAuditoria = $local.$tablaAuditoria.DataTable({
		"initComplete" : function() {
			$local.$tablaAuditoria.wrap("<div class='table-responsive'></div>");
			$local.filtrosSeleccionables["6"] = $variableUtil.arregloSiNo;
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaAuditoria, $local.filtrosSeleccionables);
		},
		"language" : {
			"emptyTable" : "No se han encontrado Auditorías para su búsqueda"
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 7 ],
			"className" : "all filtrable"
		} ],
		"ordering": false,
		"columns" : [ {
			"data" : 'fecha',
			"title" : 'Fecha'
		}, {
			"data" : 'hora',
			"title" : 'Hora'
		}, {
			"data" : "nombreUsuario",
			"title" : 'Usuario'
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idTipoAuditoria, row.descripcionTipoAuditoria);
			},
			"title" : 'Tipo Auditoría'
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idAccion, row.descripcionAccion);
			},
			"title" : 'Acción'
		}, {
			"data" : 'direccionIp',
			"title" : 'Dirección IP'
		}, {
			"className" : "all seleccionable data-no-definida",
			"data" : function(row) {
				return $funcionUtil.insertarEtiquetaSiNo(row.exito);
			},
			"title" : 'Éxito de la operación'
		}, {
			"data" : 'comentario',
			"title" : 'Comentario'
		} ]
	});

	$local.$buscar.on("click", function() {
		var criterioBusquedaAuditoria = $formCriterioBusquedaAuditoria.serializeJSON();
		var rangoFechaTxn = $funcionUtil.obtenerFechasDateRangePicker($local.$rangoFechaBusqueda);
		criterioBusquedaAuditoria.fechaInicio = rangoFechaTxn.fechaInicio;
		criterioBusquedaAuditoria.fechaFin = rangoFechaTxn.fechaFin;
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "seguridad/auditoria?accion=buscar",
			data : criterioBusquedaAuditoria,
			beforeSend : function(xhr) {
				$local.tablaAuditoria.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(auditorias) {
				if (auditorias.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaAuditoria.rows.add(auditorias).draw();
			},
			error : function() {
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaAuditoria.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaAuditoria.column($(this).parent().index() + ':visible').search(this.value).draw();
	});
	
	$local.$tablaAuditoria.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaAuditoria.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});
});
