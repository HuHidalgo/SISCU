$(document).ready(function() {

	var $local = {
		tablaTomaRadiologica : "",
		$tablaTomaRadiologica : $("#tablaTomaRadiologica"),
		$filaSeleccionada : "",
		numeroRegistroSeleccionado : 0,
		anioSeleccionado : ""
	};

	$local.tablaTomaRadiologica = $local.$tablaTomaRadiologica.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "examenmedico/radiologico?accion=buscarNumeroRegistroGenerado",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Tomas Radiológicas pendientes."
		},
		"ordering" : false,
		"initComplete" : function() {
			$local.$tablaTomaRadiologica.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaTomaRadiologica);
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
			"title" : "Num. Registro"
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
			"data" : "codigoAlumno",
			"title" : "Código de Alumno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoAlumno, row.descripcionTipoAlumno);
			},
			"title" : "Tipo de Alumno"
		}, {
			"data" : "fechaGeneracionNumeroRegistro",
			"title" : "Fecha de Generación"
		}, {
			"data" : "edad",
			"title" : "Edad"
		}, {
			"data" : "descripcionEscuela",
			"title" : "Escuela"
		} ]
	});

	$local.$tablaTomaRadiologica.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaTomaRadiologica.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaTomaRadiologica.children("tbody").on("click", ".registrar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoRadiologico = $local.tablaTomaRadiologica.row($local.$filaSeleccionada).data();
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "examenmedico/radiologico?accion=actualizarTomaRadiologica",
			data : JSON.stringify(examenMedicoRadiologico),
			autoclose : true,
			beforeSend : function(xhr) {
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			}
		}).done(function(response) {
			$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
			$local.tablaTomaRadiologica.row($local.$filaSeleccionada).remove().draw(false);
		}).fail(function(xhr) {
			switch (xhr.status) {
			case 400:
				$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
				break;
			case 409:
				$funcionUtil.notificarException(xhr.responseText, "fa-warning", "Aviso", "warning");
				break;
			}
		}).always(function(xhr) {
			confirmar.close();
			$local.$filaSeleccionada = "";
		});
	});

});