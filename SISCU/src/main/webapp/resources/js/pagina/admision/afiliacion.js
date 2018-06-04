$(document).ready(function() {

	var $local = {
		
		$tablaConsultaAdmision : $("#tablaConsultaAdmision"),
		tablaConsultaAdmision : "",
		$filaSeleccionada : "",
		$estamentos : $("#estamentos"),
		$estamentos2 : $("#estamentos2"),
		$sexos : $("#sexos"),
		$estados : $("#estados"),
		$distritosNac : $("#distritosNac"),
		$departamentosNac : $("#departamentosNac"),
		$tiposDocumento : $("#tiposDocumento"),
		$tiposDocumento2 : $("#tiposDocumento2"),
		$grados : $("#grados"),
		$buscar : $("#buscar"),
		$afiliar : $("#afiliar"),
		$facultades : $("#facultades"),
		$escuelas : $("#escuelas"),
		$actualizarAfiliacion : $("#actualizarAfiliacion"),

		// Para Actualizacion de Resultado
		$modalAfiliacion : $("#modalAfiliacion"),
		$codigoAlumno : $("#codigoAlumno"),
		$codAlumno : $("#codAlumno"),
		$areaTra : $("#areaTra"),
		numeroRegistroSeleccionado : "",
		$divCodigoAlumno : $("#divCodigoAlumno"),
		$divNroDocumento : $("#divNroDocumento"),
		$divCodAlumno : $("#divCodAlumno"),
		$divFacultad : $("#divFacultad"),
		$divEscuela : $("#divEscuela"),
		$divAreaTrabajo : $("#divAreaTrabajo"),
		$registrarAfiliacion : $("#registrarAfiliacion"),
		$actualizarAfiliacion : $("#actualizarAfiliacion"),
		$fechaNacimiento : $("#fechaNacimiento"),
	};

	// Para Consulta
	$formCriterioConsultaAdmision = $("#formCriterioConsultaAdmision");

	$funcionUtil.crearDatePickerSimple($local.$fechaNacimiento, "DD/MM/YYYY");

	$funcionUtil.crearSelect2($local.$estamentos, "Seleccione un Estamento");
	$funcionUtil.crearSelect2($local.$estamentos2, "Seleccione un Estamento");
	$funcionUtil.crearSelect2($local.$sexos, "Seleccione el Sexo");
	$funcionUtil.crearSelect2($local.$estados, "Seleccione el Estado Civil");
	$funcionUtil.crearSelect2($local.$tiposDocumento, "Seleccione el tipo de documento");
	$funcionUtil.crearSelect2($local.$tiposDocumento2, "Seleccione el tipo de documento");
	$funcionUtil.crearSelect2($local.$grados, "Seleccione un Grado de Instrucción");
	$funcionUtil.crearSelect2($local.$facultades, "Seleccione una Facultad");
	$funcionUtil.crearSelect2($local.$escuelas, "Seleccione una Escuela");
	$funcionUtil.crearSelect2($local.$departamentosNac, "Seleccione un Departamento");
	$funcionUtil.crearSelect2($local.$distritosNac, "Seleccione un Distrito");

	//var esAdmin = $local.$administrador.length > 0;

	$local.tablaConsultaAdmision = $local.$tablaConsultaAdmision.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdmision.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdmision);
		},
		"ordering" : false,
		"columnDefs" : [ {
			"targets" : [ 1, 2, 3, 4, 5 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
		}, {
			"data" : "descEstamento",
			"title" : "Tipo Paciente"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoDocumento, row.nroDocumento);
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
				return $funcionUtil.unirCodigoDescripcion(row.idSexo, row.sexo);
			},
			"title" : "Sexo"
		}]
	});

	$local.$tablaConsultaAdmision.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaConsultaAdmision.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaConsultaAdmision.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaConsultaAdmision.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$buscar.on("click", function() {
		var consultarDatos = $formCriterioConsultaAdmision.serializeJSON();
		console.log(consultarDatos);
		/*
		 if ($funcionUtil.camposVacios($formCriterioConsultaAdmision)) {
			$funcionUtil.notificarException($variableUtil.camposVacios, "fa-exclamation-circle", "Información", "info");
			return;
		}
		 * */
		 
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "admision/afiliacion/consulta?accion=buscarPorEstamento",
			data : consultarDatos,
			beforeSend : function() {
				$local.tablaConsultaAdmision.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(afiliacion) {
				console.log(afiliacion[0]);
				if (afiliacion.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				$local.tablaConsultaAdmision.rows.add(afiliacion).draw();
			},
			complete : function() {
				$local.$buscar.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$formCriterioConsultaAdmision.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});

	// Para Actualizacion de Resultado
	
	$formAfiliacion = $("#formAfiliacion");

	$local.$modalAfiliacion.PopupWindow({
		title : "Admisión - Afiliacion",
		autoOpen : false,
		modal : false,
		height : 720,
		width : 900
	});
	
	$local.$afiliar.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formAfiliacion);
		$local.$actualizarAfiliacion.addClass("hidden");
		$local.$registrarAfiliacion.removeClass("hidden");
		$local.$modalAfiliacion.PopupWindow("open");
	});

	$local.$modalAfiliacion.on("open.popupwindow", function() {
		$formAfiliacion.find("input:not([disabled]):first").focus();
	});

	$local.$modalAfiliacion.on("close.popupwindow", function() {
		$local.$filaSeleccionada = "";
		$local.numeroRegistroSeleccionado = 0;
		$local.anioSeleccionado = "";
	});
	
	$local.$facultades.on("change", function(event, opcionSeleccionada) {
		var codigoFacultad = $(this).val();
		if (codigoFacultad == null || codigoFacultad == undefined) {
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
					$funcionUtil.limpiarMensajesDeError($formAfiliacion);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formAfiliacion);
				}
			},
			success : function(escuelas) {
				$.each(escuelas, function(i, escuela) {
					$local.$escuelas.append($("<option />").val(this.codigoEscuela).text(this.codigoEscuela + " - " + this.descripcion));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$escuelas.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			complete : function() {
				$local.$escuelas.parent().find(".cargando").remove();
			}
		});
	});
	
	$local.$departamentosNac.on("change", function(event, opcionSeleccionada) {
		var codDepartamento = $(this).val();
		if (codDepartamento == null || codDepartamento == undefined) {
			$local.$distritosNac.find("option:not(:eq(0))").remove();
			return;
		}
		console.log(codDepartamento);
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "mantenimiento/multiTabDet/multiTabCab2/" + codDepartamento,
			beforeSend : function(xhr) {
				$local.$distritosNac.find("option:not(:eq(0))").remove();
				$local.$distritosNac.parent().append("<span class='help-block cargando'><i class='fa fa-spinner fa-pulse fa-fw'></i> Cargando Distritos</span>")
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formAfiliacion);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formAfiliacion);
				}
			},
			success : function(distritos) {
				$.each(distritos, function(i, distrito) {
					$local.$distritosNac.append($("<option />").val(this.idItem).text(this.descripcionItem));
				});
				if (opcionSeleccionada != null && opcionSeleccionada != undefined) {
					$local.$distritosNac.val(opcionSeleccionada).trigger("change.select2");
				}
			},
			complete : function() {
				$local.$distritosNac.parent().find(".cargando").remove();
			}
		});
	});

	$local.$tablaConsultaAdmision.children("tbody").on("click", ".actualizar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var examenMedicoPsicologico = $local.tablaConsultaAdmision.row($local.$filaSeleccionada).data();
		var apellidosNombres = examenMedicoPsicologico.nombres + ", " + examenMedicoPsicologico.apellidoPaterno + " " + examenMedicoPsicologico.apellidoMaterno;
		$local.numeroRegistroSeleccionado = examenMedicoPsicologico.numeroRegistro;
		$local.$inputCodigo.val(examenMedicoPsicologico.codigoAlumno);
		$local.$inputApellidosNombres.val(apellidosNombres);
		$funcionUtil.aniadirTitleParaTooltip($local.$inputApellidosNombres, apellidosNombres);
		var criterioBusqueda = {
			numeroRegistro : $local.numeroRegistroSeleccionado,
		};
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "consulta/psicologico?accion=buscarResultadoRegularPorNumeroRegistro",
			data : criterioBusqueda,
			beforeSend : function() {
				$(this).attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(examenesMedicoPsicologico) {
				if (examenesMedicoPsicologico.length == 0) {
					$funcionUtil.notificarException($variableUtil.busquedaSinResultados, "fa-exclamation-circle", "Información", "info");
					return;
				}
				examenMedicoPsicologico = examenesMedicoPsicologico[0];
				$funcionUtil.prepararFormularioActualizacion($formResultadoExamenMedico);
				$funcionUtil.llenarFormulario(examenMedicoPsicologico, $formResultadoExamenMedico);
				var r = $local.$resultadoPsicologico.filter(":checked").val();
				if (r == "O") {
					$local.$divObservacion.removeClass("hidden");
				} else {
					$local.$divObservacion.addClass("hidden");
				}
				$local.$modalResultado.PopupWindow("open");
			},
			complete : function() {
				$(this).attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$registrarAfiliacion.on("click", function() {
		console.log("fdfdfdfdfd");
		if (!$formAfiliacion.valid()) {
			return;
		}
		var afiliacion = $formAfiliacion.serializeJSON();
		console.log(afiliacion);
		afiliacion.fechaNacimiento = $local.$fechaNacimiento.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "admision/afiliacion",
			data : JSON.stringify(afiliacion),
			beforeSend : function(xhr) {
				$local.$registrarAfiliacion.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formAfiliacion);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formAfiliacion);
				}
			},
			success : function(response) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var afiliacion = $local.tablaConsultaAdmision.row($local.$filaSeleccionada).data();
				var row = $local.tablaConsultaAdmision.row($local.$filaSeleccionada).data(afiliacion).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalAfiliacion.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarAfiliacion.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$formAfiliacion.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$buscar.trigger("click");
			return false;
		}
	});
/*
	$local.$estamentos.on("change", function() {
		var opcion = $(this).val();
		switch (opcion) {
		case "1":
			$local.$divCodigoAlumno.removeClass("hidden");
			$local.$divNroDocumento.addClass("hidden");
			$local.$codigoAlumno.val("");
			break;
		case "2":
			$local.$divCodigoAlumno.addClass("hidden");
			$local.$divNroDocumento.removeClass("hidden");
			break;
		case "3":
			$local.$divCodigoAlumno.addClass("hidden");
			$local.$divNroDocumento.removeClass("hidden");
			break;
		case "4":
			$local.$divCodigoAlumno.addClass("hidden");
			$local.$divNroDocumento.removeClass("hidden");
			break;
		case "5":
			$local.$divCodigoAlumno.addClass("hidden");
			$local.$divNroDocumento.removeClass("hidden");			
		}
	});
	*/
	$local.$estamentos2.on("change", function() {
		var opcion = $(this).val();
		console.log(opcion);
		switch (opcion) {
		case "1":
			$local.$divCodAlumno.removeClass("hidden");
			$local.$divFacultad.removeClass("hidden");
			$local.$divEscuela.removeClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			$local.$codAlumno.val("");
			break;
		case "2":
			$local.$divCodAlumno.addClass("hidden");
			$local.$divFacultad.removeClass("hidden");
			$local.$divEscuela.removeClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			break;
		case "3":
			$local.$divCodAlumno.addClass("hidden");
			$local.$divFacultad.removeClass("hidden");
			$local.$divEscuela.addClass("hidden");
			$local.$divAreaTrabajo.removeClass("hidden");
			$local.$areaTra.val("");
			break;
		case "4":
			$local.$divCodAlumno.addClass("hidden");
			$local.$divFacultad.addClass("hidden");
			$local.$divEscuela.addClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			break;
		case "5":
			$local.$divCodAlumno.addClass("hidden");
			$local.$divFacultad.addClass("hidden");
			$local.$divEscuela.addClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			break;
		}
	});

});