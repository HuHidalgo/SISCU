$(document).ready(function() {

	var $local = {		
		$tablaErroresCarga : $("#tablaErroresCarga"),
		tablaErroresCarga : "",
		
		$cargarInicial : $("#cargarInicial"),
		$estamentos : $("#estamentos"),				
		$uploadfile : $("#uploadfile"),
		
	};

	$formCargaInicial = $("#formCargaInicial");
	
	$funcionUtil.crearSelect2($local.$estamentos, "Seleccione un Estamento");
	
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
	
	$local.$tablaErroresCarga.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaErroresCarga.clear().draw();
			break;
		}
	});	
	
	$local.tablaErroresCarga = $local.$tablaErroresCarga.DataTable({
		"language" : {
			"emptyTable" : "No hay datos cargados"
		},
		"initComplete" : function() {
			$local.$tablaErroresCarga.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaErroresCarga);
		},
		"ordering" : false,
		"columnDefs" : [ {
			"targets" : [ 1 ],
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
			"title" : 'Fila',
			"width" : "10%"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idTipoDocumento, row.numeroDocumento);
			},
			"title" : "Descripción"
		}]
	});
	
	$local.$cargarInicial.on("click", function(e) {
		e.preventDefault();
		if (!$formCargaInicial.valid()) {
			return;
		}
		var form = $("#formCargaInicial")[0];
		var data = new FormData(form);
		var idEstamento = $local.$estamentos.val();
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/inicial/uploadfile/" + idEstamento + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
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
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarInicial.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
	
});