$(document).ready(function() {

	/* Remover boton para ser reemplazado por boton al final del formulario */
	$("#botonesForm").remove();

	var $local = {
		$tablaAtencionInicial : $("#tablaAtencionInicial"),
		tablaAtencionInicial : "",

		/* Formulario de Búsqueda */
		$codigoAlumno : $("#codigoAlumno"),
		$facultades : $("#facultades"),
		$campanias : $("#campanias"),
		$buscar : $("#buscar"),

		/* Seleccion */
		$filaSeleccionada : "",
		codigoAlumnoSeleccionado : "",
		tipoAlumnoSeleccionado : "",
		idCampaniaSeleccionado : 0,

		/* Antecedente Personal */
		$idsCie10AntecedentePersonal : $("#idsCie10AntecedentePersonal"),
		$tablaAntecedentesPersonales : $("#tablaAntecedentesPersonales"),
		tablaAntecedentesPersonales : "",
		$aniadirAntecedentePersonal : $("#aniadirAntecedentePersonal"),
		$filaSeleccionadaAntecedentePersonal : "",

		/* Antecedente Familiar */
		$idsCie10AntecedenteFamiliar : $("#idsCie10AntecedenteFamiliar"),
		$tablaAntecedentesFamiliares : $("#tablaAntecedentesFamiliares"),
		tablaAntecedentesFamiliares : "",
		$aniadirAntecedenteFamiliar : $("#aniadirAntecedenteFamiliar"),
		$parentescos : $("#parentescos"),
		$filaSeleccionadaAntecedenteFamiliar : "",

		/* Alergia */
		$idsCie10Alergia : $("#idsCie10Alergia"),
		$tablaAlergias : $("#tablaAlergias"),
		tablaAlergias : "",
		$aniadirAlergia : $("#aniadirAlergia"),
		$filaSeleccionadaAlergia : "",

		/* Interconsulta */
		$especialidades : $("#especialidades"),
		$tablaInterconsultas : $("#tablaInterconsultas"),
		tablaInterconsultas : "",
		$aniadirInterconsulta : $("#aniadirInterconsulta"),
		$filaSeleccionadaInterconsulta : "",

		/* Formulario Resultado de Medicina General */
		$inputCodigo : $("#codigo"),
		$inputApellidosNombres : $("#apellidosNombres"),
		$modalResultado : $("#modalResultado"),
		$registrarResultado : $("#registrarResultado"),
		$fieldsetEmbarazo : $("#fieldset-embarazo")
	};

	/* Variable Global */
	$formResultadoExamenMedico = $("#formResultadoExamenMedico");
	$formCriterioBusquedaConsulta = $("#formCriterioBusquedaConsulta");

	$formAntecedentePersonal = $("#formAntecedentePersonal");
	$formAntecedenteFamiliar = $("#formAntecedenteFamiliar");
	$formAlergia = $("#formAlergia");
	$formInterconsulta = $("#formInterconsulta");

	$funcionUtil.crearSelect2($local.$facultades);
	$funcionUtil.crearSelect2($local.$campanias);
	$funcionUtil.crearSelect2($local.$parentescos, "Seleccione un Parentesco");
	$funcionUtil.crearSelect2($local.$especialidades, "Seleccione una Especialidad");

	$formCriterioBusquedaConsulta.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});

	$local.$modalResultado.PopupWindow({
		title : "Resultado de Examen Médico de Medicina General",
		autoOpen : false,
		modal : false,
		height : $variableUtil.altoModalResultadoOdontologico,
		width : $variableUtil.anchoModalResultadoOdontologico
	});

	$local.$modalResultado.on("open.popupwindow", function() {
		$("a[href='#antecedentesPersonales']").tab("show");
		$local.tablaAntecedentesPersonales.clear().draw();
		$local.tablaAntecedentesFamiliares.clear().draw();
		$local.tablaAlergias.clear().draw();
		$funcionUtil.prepararFormularioRegistro($formAntecedentePersonal);
		$funcionUtil.prepararFormularioRegistro($formAntecedenteFamiliar);
		$funcionUtil.prepararFormularioRegistro($formAlergia);
		$formResultadoExamenMedico.find("input:not([disabled]):first").focus();
	});

	$local.$modalResultado.on("close.popupwindow", function() {
		$local.$codigoAlumno.select();
		$local.$filaSeleccionada = "";
		$local.codigoAlumnoSeleccionado = "";
		$local.tipoAlumnoSeleccionado = "";
		$local.idCampaniaSeleccionado = 0;
		$local.$fieldsetEmbarazo.addClass("hidden");
	});

	$local.tablaAtencionInicial = $local.$tablaAtencionInicial.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados."
		},
		"initComplete" : function() {
			$local.$tablaAtencionInicial.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaAtencionInicial);
		},
		"order" : [ [ 0, "desc" ] ],
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
					elemento = $variableUtil.labelAtendido;
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
			url : $variableUtil.root + "consulta/atencion/medicinaGeneral?accion=buscar",
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
		$funcionUtil.prepararFormularioRegistro($formResultadoExamenMedico);
		$local.$filaSeleccionada = $(this).parents("tr");
		var alumno = $local.tablaAtencionInicial.row($local.$filaSeleccionada).data();
		var apellidosNombres = alumno.nombres + ", " + alumno.apellidoPaterno + " " + alumno.apellidoMaterno;
		$local.codigoAlumnoSeleccionado = alumno.codigoAlumno;
		$local.tipoAlumnoSeleccionado = alumno.tipoAlumno;
		$local.$inputApellidosNombres.val(apellidosNombres);
		$local.$inputCodigo.val(alumno.codigoAlumno);
		$funcionUtil.aniadirTitleParaTooltip($local.$inputApellidosNombres, apellidosNombres);
		if (alumno.idSexo == "F") {
			$local.$fieldsetEmbarazo.removeClass("hidden");
		}
		$local.$modalResultado.PopupWindow("open");
		$local.$modalResultado.PopupWindow("maximize");
	});

	/* Mantenimiento de Antecedente Personal */

	$local.$idsCie10AntecedentePersonal.select2({
		ajax : {
			url : $variableUtil.root + "mantenimiento/cie10?accion=buscarPorDescripcion",
			dataType : "json",
			delay : 250,
			data : function(params) {
				return {
					descripcion : params.term
				}
			},
			processResults : function(data) {
				var resultado = [];
				$.each(data, function(i, cie10) {
					resultado.push({
						id : cie10.idCie10,
						text : cie10.descripcion
					});
				});
				return {
					results : resultado
				};
			}
		},
		templateResult : $funcionUtil.templateResultCie10,
		templateSelection : $funcionUtil.templateSelectionCie10,
		minimumInputLength : 3,
		language : {
			noResults : function() {
				return "No se encontró resultados";
			},
			inputTooShort : function() {
				return "Ingrese 3 o más carácteres";
			}
		},
		"width" : "100%",
		"theme" : "bootstrap",
		"dropdownAutoWidth" : true,
		"dropdownParent" : $local.$idsCie10AntecedentePersonal.parent()
	});

	$local.tablaAntecedentesPersonales = $local.$tablaAntecedentesPersonales.DataTable({
		"language" : {
			"emptyTable" : "No hay antecedentes personales registrados."
		},
		"initComplete" : function() {
			$local.$tablaAntecedentesPersonales.wrap("<div class='table-responsive'></div>");
		},
		"pageLength" : 3,
		"lengthChange" : false,
		"searching" : false,
		order : [ [ 1, "asc" ] ],
		"columnDefs" : [ {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción',
			"width" : "5%",
		}, {
			"className" : "all",
			"data" : "idCie10",
			"title" : "CIE 10",
			"width" : "15%",
		}, {
			"className" : "all break-word",
			"data" : "descripcion",
			"title" : "Descripción"
		} ]
	});

	$local.$aniadirAntecedentePersonal.on("click", function() {
		if (!$formAntecedentePersonal.valid()) {
			return;
		}
		var antecedentePersonal = $formAntecedentePersonal.serializeJSON();
		antecedentePersonal.idTipoAntecedente = "A";
		var row = $local.tablaAntecedentesPersonales.row.add(antecedentePersonal).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$funcionUtil.prepararFormularioRegistro($formAntecedentePersonal);
	});

	$local.$tablaAntecedentesPersonales.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionadaAntecedentePersonal = $(this).parents("tr");
		var antecedentePersonal = $local.tablaAntecedentesPersonales.row($local.$filaSeleccionadaAntecedentePersonal).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar el Antecedente Personal <b>'" + antecedentePersonal.idCie10 + " - " + antecedentePersonal.descripcion + "'<b/>?",
			theme : "bootstrap",
			buttons : {
				Aceptar : {
					action : function() {
						$local.tablaAntecedentesPersonales.row($local.$filaSeleccionadaAntecedentePersonal).remove().draw(false);
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

	/* Mantenimiento de Antecedente Familiar */

	$local.$idsCie10AntecedenteFamiliar.select2({
		ajax : {
			url : $variableUtil.root + "mantenimiento/cie10?accion=buscarPorDescripcion",
			dataType : "json",
			delay : 250,
			data : function(params) {
				return {
					descripcion : params.term
				}
			},
			processResults : function(data) {
				var resultado = [];
				$.each(data, function(i, cie10) {
					resultado.push({
						id : cie10.idCie10,
						text : cie10.descripcion
					});
				});
				return {
					results : resultado
				};
			}
		},
		templateResult : $funcionUtil.templateResultCie10,
		templateSelection : $funcionUtil.templateSelectionCie10,
		minimumInputLength : 3,
		language : {
			noResults : function() {
				return "No se encontró resultados";
			},
			inputTooShort : function() {
				return "Ingrese 3 o más carácteres";
			}
		},
		"width" : "100%",
		"theme" : "bootstrap",
		"dropdownAutoWidth" : true,
		"dropdownParent" : $local.$idsCie10AntecedenteFamiliar.parent()
	});

	$local.tablaAntecedentesFamiliares = $local.$tablaAntecedentesFamiliares.DataTable({
		"language" : {
			"emptyTable" : "No hay antecedentes familiares registrados."
		},
		"initComplete" : function() {
			$local.$tablaAntecedentesFamiliares.wrap("<div class='table-responsive'></div>");
		},
		"pageLength" : 3,
		"lengthChange" : false,
		"searching" : false,
		order : [ [ 1, "asc" ] ],
		"columnDefs" : [ {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción',
			"width" : "5%",
		}, {
			"className" : "all",
			"data" : "idCie10",
			"title" : "CIE 10",
			"width" : "15%",
		}, {
			"className" : "all",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idTipoAntecedente, row.descripcionTipoAntecedente);
			},
			"title" : "Parentesco",
			"width" : "15%",
		}, {
			"className" : "all break-word",
			"data" : "descripcion",
			"title" : "Descripción"
		} ]
	});

	$local.$aniadirAntecedenteFamiliar.on("click", function() {
		if (!$formAntecedenteFamiliar.valid()) {
			return;
		}
		var antecedenteFamiliar = $formAntecedenteFamiliar.serializeJSON();
		antecedenteFamiliar.descripcionTipoAntecedente = $local.$parentescos.find("option:selected").attr("data-descripcion-parentesco");
		var row = $local.tablaAntecedentesFamiliares.row.add(antecedenteFamiliar).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$funcionUtil.prepararFormularioRegistro($formAntecedenteFamiliar);
	});

	$local.$tablaAntecedentesFamiliares.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionadaAntecedenteFamiliar = $(this).parents("tr");
		var antecedenteFamiliar = $local.tablaAntecedentesFamiliares.row($local.$filaSeleccionadaAntecedenteFamiliar).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar el Antecedente Familiar <b>'" + antecedenteFamiliar.idCie10 + " - " + antecedenteFamiliar.descripcion + "'<b/>?",
			theme : "bootstrap",
			buttons : {
				Aceptar : {
					action : function() {
						$local.tablaAntecedentesFamiliares.row($local.$filaSeleccionadaAntecedenteFamiliar).remove().draw(false);
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

	/* Alergia */

	$local.$idsCie10Alergia.select2({
		ajax : {
			url : $variableUtil.root + "mantenimiento/cie10?accion=buscarPorDescripcion",
			dataType : "json",
			delay : 250,
			data : function(params) {
				return {
					descripcion : params.term
				}
			},
			processResults : function(data) {
				var resultado = [];
				$.each(data, function(i, cie10) {
					resultado.push({
						id : cie10.idCie10,
						text : cie10.descripcion
					});
				});
				return {
					results : resultado
				};
			}
		},
		templateResult : $funcionUtil.templateResultCie10,
		templateSelection : $funcionUtil.templateSelectionCie10,
		minimumInputLength : 3,
		language : {
			noResults : function() {
				return "No se encontró resultados";
			},
			inputTooShort : function() {
				return "Ingrese 3 o más carácteres";
			}
		},
		"width" : "100%",
		"theme" : "bootstrap",
		"dropdownAutoWidth" : true,
		"dropdownParent" : $local.$idsCie10Alergia.parent()
	});

	$local.tablaAlergias = $local.$tablaAlergias.DataTable({
		"language" : {
			"emptyTable" : "No hay Alergias registrados."
		},
		"initComplete" : function() {
			$local.$tablaAlergias.wrap("<div class='table-responsive'></div>");
		},
		"pageLength" : 3,
		"lengthChange" : false,
		"searching" : false,
		order : [ [ 1, "asc" ] ],
		"columnDefs" : [ {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción',
			"width" : "5%",
		}, {
			"className" : "all",
			"data" : "idCie10",
			"title" : "CIE 10",
			"width" : "15%",
		}, {
			"className" : "all break-word",
			"data" : "descripcion",
			"title" : "Descripción"
		} ]
	});

	$local.$aniadirAlergia.on("click", function() {
		if (!$formAlergia.valid()) {
			return;
		}
		var antecedenteAlergia = $formAlergia.serializeJSON();
		antecedenteAlergia.idTipoAntecedente = "B";
		var row = $local.tablaAlergias.row.add(antecedenteAlergia).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$funcionUtil.prepararFormularioRegistro($formAlergia);
	});

	$local.$tablaAlergias.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionadaAlergia = $(this).parents("tr");
		var antecedenteAlergia = $local.tablaAlergias.row($local.$filaSeleccionadaAlergia).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar el Antecedente de Alergia <b>'" + antecedenteAlergia.idCie10 + " - " + antecedenteAlergia.descripcion + "'<b/>?",
			theme : "bootstrap",
			buttons : {
				Aceptar : {
					action : function() {
						$local.tablaAlergias.row($local.$filaSeleccionadaAlergia).remove().draw(false);
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

	/* Interconsultas */
	$local.tablaInterconsultas = $local.$tablaInterconsultas.DataTable({
		"language" : {
			"emptyTable" : "No hay Interconsultas registradas."
		},
		"initComplete" : function() {
			$local.$tablaInterconsultas.wrap("<div class='table-responsive'></div>");
		},
		"pageLength" : 3,
		"lengthChange" : false,
		"searching" : false,
		order : [ [ 1, "asc" ] ],
		"columnDefs" : [ {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción',
			"width" : "5%",
		}, {
			"className" : "all",
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idEspecialidad, row.descripcionEspecialidad);
			},
			"title" : "Especialidad",
			"width" : "25%",
		}, {
			"className" : "all break-word",
			"data" : "motivo",
			"title" : "Motivo"
		} ]
	});

	$local.$aniadirInterconsulta.on("click", function() {
		if (!$formInterconsulta.valid()) {
			return;
		}
		var interconsulta = $formInterconsulta.serializeJSON();
		interconsulta.descripcionEspecialidad = $local.$especialidades.find("option:selected").attr("data-descripcion-especialidad");
		var row = $local.tablaInterconsultas.row.add(interconsulta).draw();
		row.show().draw(false);
		$(row.node()).animateHighlight();
		$funcionUtil.prepararFormularioRegistro($formInterconsulta);
	});

	$local.$tablaInterconsultas.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionadaInterconsulta = $(this).parents("tr");
		var interconsulta = $local.tablaInterconsultas.row($local.$filaSeleccionadaInterconsulta).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar la Interconsulta <b>'" + interconsulta.descripcionEspecialidad + " - " + interconsulta.motivo + "'<b/>?",
			theme : "bootstrap",
			buttons : {
				Aceptar : {
					action : function() {
						$local.tablaInterconsultas.row($local.$filaSeleccionadaInterconsulta).remove().draw(false);
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

	/* Registro */
	$local.$registrarResultado.on("click", function() {
		var examenMedicoMedicinaGeneral = $formResultadoExamenMedico.serializeJSON();
		var antecedentes = [];
		var antecedentesPersonales = $local.tablaAntecedentesPersonales.rows({
			selected : true
		}).data().toArray();
		var antecedentesFamiliares = $local.tablaAntecedentesFamiliares.rows({
			selected : true
		}).data().toArray();
		var alergias = $local.tablaAlergias.rows({
			selected : true
		}).data().toArray();
		var interconsultas = $local.tablaInterconsultas.rows({
			selected : true
		}).data().toArray();

		$.merge(antecedentes, antecedentesPersonales);
		$.merge(antecedentes, antecedentesFamiliares);
		$.merge(antecedentes, alergias);

		examenMedicoMedicinaGeneral.antecedentes = antecedentes;
		examenMedicoMedicinaGeneral.interconsultas = interconsultas;
		examenMedicoMedicinaGeneral.idCampania = $local.idCampaniaSeleccionado;
		examenMedicoMedicinaGeneral.codigoAlumno = $local.codigoAlumnoSeleccionado;
		examenMedicoMedicinaGeneral.tipoAlumno = $local.tipoAlumnoSeleccionado;

		$.ajax({
			type : "POST",
			url : $variableUtil.root + "examenmedico/medicinaGeneral",
			data : JSON.stringify(examenMedicoMedicinaGeneral),
			beforeSend : function(xhr) {
				$local.$registrarResultado.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
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
				$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
				var examenMedicoMedicinaGeneral = $local.tablaAtencionInicial.row($local.$filaSeleccionada).data();
				examenMedicoMedicinaGeneral.numeroRegistro = 1;
				var row = $local.tablaAtencionInicial.row($local.$filaSeleccionada).data(examenMedicoMedicinaGeneral).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalResultado.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarResultado.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

});