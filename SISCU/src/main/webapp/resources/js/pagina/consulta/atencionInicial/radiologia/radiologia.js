$(document).ready(function() {

	var $local = {
		$tablaAtencionInicial : $("#tablaAtencionInicial"),
		tablaAtencionInicial : "",
		$criterioBusquedaConsulta : $("#criterioBusquedaConsulta"),
		$filaSeleccionada : "",
		$facultades : $("#facultades"),
		$campanias : $("#campanias"),
		codigoAlumnoSeleccionado : "",
		tipoAlumnoSeleccionado : "",
		idCampaniaSeleccionado : 0,
		$buscar : $("#buscar"),
		$codigoAlumno : $("#codigoAlumno")
	};

	/* Variable Global */
	$formCriterioBusquedaConsulta = $("#formCriterioBusquedaConsulta");

	$funcionUtil.crearSelect2($local.$facultades);
	$funcionUtil.crearSelect2($local.$campanias);

	$local.tablaAtencionInicial = $local.$tablaAtencionInicial.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados."
		},
		"initComplete" : function() {
			$local.$tablaAtencionInicial.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaAtencionInicial);
		},
		order : [[0, "desc"]],
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3, 4, 5, 6, 7, 8 ],
			"className" : "all filtrable",
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				var elemento = "";
				if (row.numeroRegistro == null || row.numeroRegistro == undefined || row.numeroRegistro < 1 || row.numeroRegistro == "null") {
					elemento = $variableUtil.botonRegistrar;
				} else {
					elemento = "<label class='label label-primary label-size-12'>" + row.numeroRegistro + "</label>";
				}
				return elemento;
			}
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
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

	$local.$tablaAtencionInicial.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaAtencionInicial.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaAtencionInicial.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaAtencionInicial.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		if (!$formCriterioBusquedaConsulta.valid()) {
			return;
		}
		var criterioBusqueda = $formCriterioBusquedaConsulta.serializeJSON();
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/atencion/radiologia?accion=buscar",
			data : criterioBusqueda,
			beforeSend : function() {
				$local.idCampaniaSeleccionado = 0;
				$local.tablaAtencionInicial.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(atenciones) {
				if (atenciones.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.idCampaniaSeleccionado = criterioBusqueda.idCampania;
				$local.tablaAtencionInicial.rows.add(atenciones).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				$local.$codigoAlumno.select();
			}
		});
	});

	$local.$tablaAtencionInicial.children("tbody").on("click", ".registrar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var alumno = $local.tablaAtencionInicial.row($local.$filaSeleccionada).data();
		alumno.idCampania = $local.idCampaniaSeleccionado;
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			theme: "bootstrap",
			content : "¿Desea generar el Número de Registro para el Alumno <b>'" + alumno.codigoAlumno + " - " + alumno.apellidoPaterno + " " + alumno.apellidoMaterno + ", " + alumno.nombres + "</b>?",
			buttons : {
				Aceptar : {
					action : function() {
						var confirmar = $.confirm({
							icon : 'fa fa-spinner fa-pulse fa-fw',
							title : "Generando ...",
							theme: "bootstrap",
							content : function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type : "POST",
									url : $variableUtil.root + "examenmedico/radiologico?accion=generar",
									data : JSON.stringify(alumno),
									autoclose : true,
									beforeSend : function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(numeroRegistro) {
									$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
									var alumno = $local.tablaAtencionInicial.row($local.$filaSeleccionada).data();
									alumno.numeroRegistro = numeroRegistro;
									var row = $local.tablaAtencionInicial.row($local.$filaSeleccionada).data(alumno).draw();
									row.show().draw(false);
									$(row.node()).animateHighlight();
								}).fail(function(xhr) {
									switch (xhr.status) {
									case 400:
										$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
										break;
									case 409:
										$funcionUtil.notificarException(xhr.responseText, "fa-warning", "Aviso", "warning");
										break;
									}
								}).always(function(xhr){
									confirmar.close();
									$local.$codigoAlumno.select();
								});
							},
							buttons : {
								close : {
									text : 'Aceptar'
								}
							}
						});
					},
					keys : [ 'enter' ],
					btnClass : "btn-primary"
				},
				Cancelar : {
					keys : [ 'esc' ]
				},
			}
		});
	});
	
	$formCriterioBusquedaConsulta.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});
});