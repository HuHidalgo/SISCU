$(document).ready(function() {

	var $local = {
		$tablaCargaInicial : $("#tablaCargaInicial"),
		tablaCargaInicial : "",
		$cargarInicial : $("#cargarInicial"),
		$estamentos : $("#estamentos"),
		$estamentos2 : $("#estamentos2"),
		$uploadfile : $("#uploadfile"),
		$divCarga : $("#divCarga"),
		$divConsulta : $("#divConsulta"),
		$consultar : $("#consultar"),
		$cargar : $("#cargar"),
		$buscarAfiliado : $("#buscarAfiliado"),
		$tiposDocumento : $("#tiposDocumento")
	};

	$formCargaInicial = $("#formCargaInicial");
	$formConsultaInicial = $("#formConsultaInicial");
	$funcionUtil.crearSelect2($local.$estamentos, "Seleccione un Estamento");
	$funcionUtil.crearSelect2($local.$estamentos2, "Seleccione un Estamento");
	$funcionUtil.crearSelect2($local.$tiposDocumento, "Seleccione el tipo de documento");
	
	$formCargaInicial.validate({
		focusCleanup : true,
		rules : {
			idEstamento : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 1 ]
			},
			uploadfile : {
				required : true
			}
		},
		messages : {
			idEstamento : {
				required : "Seleccione un Estamento.",
				notOnlySpace : "El Estamento no puede contener solo espacios en blanco.",
				rangelength : "El Estamento debe contener 1 car&aacute;cter."
			},
			uploadfile : {
				required : "Seleccione un Archivo"
			}
		}
	});

	$local.$cargarInicial.on("click", function(e) {
		e.preventDefault();
		if (!$formCargaInicial.valid()) {
			return;
		}
		var form = $("#formCargaInicial")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos.val();
		console.log(idEstamento);
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/inicial/uploadfile/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.tablaCargaInicial.clear().draw();
				$local.$cargarInicial.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formCargaInicial);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formCargaInicial);
				}
			},
			success : function(response) {
				/*
				if (alumnos.length == 0) {
					$funcionUtil.notificarException("No se cargaron los datos", "fa-exclamation-circle", "Información", "info");
					return;
				}*/
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
				//$local.tablaCargaInicial.rows.add(alumnos).draw();
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarInicial.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$local.$buscarAfiliado.on("click", function() {
		if (!$formCargaInicial.valid()) {
			return;
		}
		var consultarDatos = $formCargaInicial.serializeJSON();
		console.log(consultarDatos);
		/*
		 if ($funcionUtil.camposVacios($formCriterioConsultaAdmision)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Información", "info");
			return;
		}*/
				 
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "admision/afiliacion/consulta?accion=buscarPorEstamento",
			data : consultarDatos,
			beforeSend : function() {
				$local.tablaCargaInicial.clear().draw();
				$local.$buscarAfiliado.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(afiliacion) {
				if (afiliacion.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaCargaInicial.rows.add(afiliacion).draw();
			},
			complete : function() {
				$local.$buscarAfiliado.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaCargaInicial.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaCargaInicial.clear().draw();
			break;
		}
	});

	$local.tablaCargaInicial = $local.$tablaCargaInicial.DataTable({
		"language" : {
			"emptyTable" : "No hay datos cargados"
		},
		"initComplete" : function() {
			$local.$tablaCargaInicial.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaCargaInicial);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 ],
			"className" : "all filtrable",
		}],
		"columns" : [ {
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
			"data" : "direccion",
			"title" : 'Dirección'
		}, {
			"data" : "telefonoFijo",
			"title" : 'Tel. Fijo'
		}, {
			"data" : "telefonoMovil",
			"title" : 'Tel. Móvil'
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.descripcionSexo);
			},
			"title" : "Sexo"
		}, {
			"data" : "correo",
			"title" : 'Correo'
		}]
	});

	$local.$tablaCargaInicial.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaCargaInicial.column($(this).parent().index() + ':visible').search(this.value).draw();
	});
	
	$local.$tablaCargaInicial.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaCargaInicial.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});
	
	$local.$consultar.on("click", function() {
		$local.$divCarga.addClass("hidden");
		$local.$divConsulta.removeClass("hidden");
	});
	
	$local.$cargar.on("click", function() {
		$local.$divCarga.removeClass("hidden");
		$local.$divConsulta.addClass("hidden");			
	});
});