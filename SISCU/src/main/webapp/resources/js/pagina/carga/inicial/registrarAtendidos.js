$(document).ready(function() {

	var $local = {
		$tablaCargaConsulta : $("#tablaCargaConsulta"),
		tablaCargaConsulta : "",
		$filaSeleccionada : "",
		
		$modalAfiliado : $("#modalAfiliado"),
		$estamentos2 : $("#estamentos2"),
		$estamentos3 : $("#estamentos3"),
		$sexos : $("#sexos"),
		$estadosCiviles : $("#estadosCiviles"),
		$distritosNac : $("#distritosNac"),
		$departamentosNac : $("#departamentosNac"),
		$grados : $("#grados"),
		$facultades : $("#facultades"),
		$escuelas : $("#escuelas"),
		
		$buscarAfiliado : $("#buscarAfiliado"),
		$tiposDocumento : $("#tiposDocumento"),
		$tiposDocumento2 : $("#tiposDocumento2"), 

		$registrarAfiliado : $("#registrarAfiliado"),
		$fechaNacimiento : $("#fechaNacimiento"),
		$edad : $("#edad"),
		
		tipoDocumento : "",
		nroDocumento : ""
	};

	$formConsultaInicial = $("#formConsultaInicial");
	$formAfiliado = $("#formAfiliado");
	
	$funcionUtil.crearDatePickerSimple($local.$fechaNacimiento, "DD/MM/YYYY");
	
	$funcionUtil.crearSelect2($local.$estamentos2, "Seleccione un Estamento");
	$funcionUtil.crearSelect2($local.$estamentos3, "Seleccione un Estamento");
	$funcionUtil.crearSelect2($local.$tiposDocumento, "Seleccione el tipo de documento");
	$funcionUtil.crearSelect2($local.$tiposDocumento2, "Seleccione el tipo de documento");
	$funcionUtil.crearSelect2($local.$sexos, "Seleccione el Sexo");
	$funcionUtil.crearSelect2($local.$estadosCiviles, "Seleccione el Estado Civil");
	$funcionUtil.crearSelect2($local.$grados, "Seleccione un Grado de Instrucción");
	$funcionUtil.crearSelect2($local.$facultades, "Seleccione una Facultad");
	$funcionUtil.crearSelect2($local.$escuelas, "Seleccione una Escuela");
	$funcionUtil.crearSelect2($local.$departamentosNac, "Seleccione un Departamento");
	$funcionUtil.crearSelect2($local.$distritosNac, "Seleccione un Distrito");
	
	//$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaCargaConsulta.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaCargaConsulta.clear().draw();
			break;
		}
	});	
	
	$formConsultaInicial.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscarAfiliado.trigger("click");
			return false;
		}
	});
	
	$local.tablaCargaConsulta = $local.$tablaCargaConsulta.DataTable({
		"language" : {
			"emptyTable" : "No hay datos cargados"
		},
		"initComplete" : function() {
			$local.$tablaCargaConsulta.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaCargaConsulta);
		},
		"ordering" : false,
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				if (row.fechaAfiliacion == null)
					return $variableUtil.botonAfiliar;
				else
					return "<label class='label label-success label-size-12'>AFILIADO</label>";
			}
		}],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
		}, {
			"data" : 'codigoAlumno',
			"title" : "Código de Alumno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idTipoDocumento, row.numeroDocumento);
			},
			"title" : "Documento"
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
				return row.fechaNacimiento;
			},
			"title" : "Fecha de Nacimiento"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoFacultad, row.descripcionFacultad);
			},
			"title" : "Facultad"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.codigoEscuela, row.descripcionEscuela);
			},
			"title" : "Escuela"
		}, {
			"data" : "direccionActual",
			"title" : 'Dirección'
		}, {
			"data" : "telefonoFijo",
			"title" : 'Tel. Fijo'
		}, {
			"data" : "celular",
			"title" : 'Tel. Móvil'
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.sexo);
			},
			"title" : "Sexo"
		}]
	});
	
	$local.$buscarAfiliado.on("click", function() {
		if (!$formConsultaInicial.valid()) {
			return;
		}
		var consultarDatos = $formConsultaInicial.serializeJSON();
				 
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "carga/registrarAtendidos?accion=buscarPorEstamento",
			data : consultarDatos,
			beforeSend : function() {
				$local.tablaCargaConsulta.clear().draw();
				$local.$buscarAfiliado.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(afiliacion) {
				if (afiliacion.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				//console.log(afiliacion[0]);
				$local.tablaCargaConsulta.rows.add(afiliacion).draw();
			},
			complete : function() {
				$local.$buscarAfiliado.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$modalAfiliado.PopupWindow({
		title : "Afiliacion",
		autoOpen : false,
		modal : false,
		height : 720,
		width : 900
	});

	$local.$modalAfiliado.on("open.popupwindow", function() {
		$formAfiliado.find("input:not([disabled]):first").focus();
	});

	$local.$modalAfiliado.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
	});
	
	$local.$tablaCargaConsulta.children("tbody").on("click", ".afiliar", function() {
		console.log("dfd");
		$local.$filaSeleccionada = $(this).parents("tr");
		var paciente = $local.tablaCargaConsulta.row($local.$filaSeleccionada).data();
		var fechaNacimiento = $funcionUtil.convertirDeFormatoAFormato(paciente.fechaNacimiento, "YYYY-MM-DD", "DD/MM/YYYY");
		$local.$fechaNacimiento.data("daterangepicker").setStartDate(fechaNacimiento);
		$local.$fechaNacimiento.data("daterangepicker").setEndDate(fechaNacimiento);
		$local.tipoDocumento = paciente.idTipoDocumento;
		$local.nroDocumento = paciente.numeroDocumento;
		$funcionUtil.llenarFormulario(paciente, $formAfiliado);
		$local.$facultades.trigger("change", [ paciente.codigoEscuela ]);
		$local.$departamentosNac.trigger("change", [ paciente.idDistritoNac ]);
		$local.$estamentos3.trigger("change");
		//$local.$actualizarAfiliacion.removeClass("hidden");
		//$local.$registrarAfiliacion.addClass("hidden");
		$local.$modalAfiliado.PopupWindow("open");
	});

	$local.$tablaCargaConsulta.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaCargaConsulta.column($(this).parent().index() + ':visible').search(this.value).draw();
	});
	
	$local.$tablaCargaConsulta.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaCargaConsulta.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});
});