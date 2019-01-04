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
		//$actualizarAfiliacion : $("#actualizarAfiliacion"),

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
		$edad : $("#edad"),
		
		tipoDocumento : "",
		nroDocumento : "",
		idAfiliacion: ""
	};

	// Para Consulta
	$formCriterioConsultaAdmision = $("#formCriterioConsultaAdmision");	
	$formAfiliacion = $("#formAfiliacion");

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
	
	$local.$tablaConsultaAdmision.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaConsultaAdmision.clear().draw();
			break;
		}
	});

	$local.tablaConsultaAdmision = $local.$tablaConsultaAdmision.DataTable({
		"language" : {
			"emptyTable" : "No hay resultados para la búsqueda."
		},
		"initComplete" : function() {
			$local.$tablaConsultaAdmision.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaConsultaAdmision);
		},
		"ordering" : false,
		"lengthChange": false,
		"searching": false,
		"columnDefs" : [ {
			"targets" : [ 2, 3, 4, 5, 6 ],
			"className" : "all filtrable",
			"defaultContent" : "-"
		}, {
			"targets" : 0,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				if (row.fechaAfiliacion == null)
					return $variableUtil.botonAfiliar;
				else
					return $variableUtil.botonActualizar; //+ " " + $variableUtil.botonEliminar;
			}
		}, {
			"targets" : 1,
			"className" : "all dt-center",
			"render" : function(data, type, row, meta) {
				if (row.fechaAfiliacion == null)
					return "<label class='label label-warning label-size-12'>NO AFILIADO</label>";
				else
					return "<label class='label label-success label-size-12'>AFILIADO</label>";
			}
		} ],
		"columns" : [ {
			"data" : null,
			"title" : 'Acción'
		}, {
			"data" : null,
			"title" : "Estado"
		}, {
			"data" : "descEstamento",
			"title" : "Tipo Paciente"
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
		if (!$formCriterioConsultaAdmision.valid()) {
			return;
		}
		var consultarDatos = $formCriterioConsultaAdmision.serializeJSON();
		
		$.ajax({
			type : "GET",
			url : $variableUtil.root + "admision/afiliacion/consulta?accion=buscarPorEstamento",
			data : consultarDatos,
			beforeSend : function() {
				$local.tablaConsultaAdmision.clear().draw();
				$local.$buscar.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw");
			},
			success : function(afiliacion) {
				//console.log(afiliacion[0]);
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
	
	//para afiliacion
	$local.$tablaConsultaAdmision.children("tbody").on("click", ".afiliar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var cliente = $local.tablaConsultaAdmision.row($local.$filaSeleccionada).data();
		var fechaNacimiento = $funcionUtil.convertirDeFormatoAFormato(cliente.fechaNacimiento, "YYYY-MM-DD", "DD/MM/YYYY");
		$local.$fechaNacimiento.data("daterangepicker").setStartDate(fechaNacimiento);
		$local.$fechaNacimiento.data("daterangepicker").setEndDate(fechaNacimiento);
		$local.tipoDocumento = cliente.idTipoDocumento;
		$local.nroDocumento = cliente.numeroDocumento;
		$funcionUtil.llenarFormulario(cliente, $formAfiliacion);
		$local.$facultades.trigger("change", [ cliente.codigoEscuela ]);
		$local.$departamentosNac.trigger("change", [ cliente.idDistritoNac ]);
		$local.$estamentos2.trigger("change");
		//$local.$actualizarAfiliacion.removeClass("hidden");
		//$local.$registrarAfiliacion.addClass("hidden");
		$local.$modalAfiliacion.PopupWindow("open");
	});
	
	$local.$fechaNacimiento.on("change", function(event, opcionSeleccionada) {
		var fechaNac = $(this).val();
		var hoy = new Date();
		var cumpleanos = fechaNac.split("/");
	    edad = hoy.getFullYear() - cumpleanos[2];
	    var m = (hoy.getMonth()+1) - cumpleanos[1];
	    if (m < 0 || (m == 0 && hoy.getDate() < cumpleanos[0])) {
	    	edad--;
	    }
	    $local.$edad.val(edad);
	});
	   

	//registrar afiliacion
	$local.$registrarAfiliacion.on("click", function() {
		if (!$formAfiliacion.valid()) {
			return;
		}
		var afiliacion = $formAfiliacion.serializeJSON();
		afiliacion.fechaNacimiento = $local.$fechaNacimiento.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "admision/afiliacion",
			data : JSON.stringify(afiliacion),
			beforeSend : function(xhr) {
				$local.tablaConsultaAdmision.clear().draw();
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
			success : function(afiliaciones) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var afiliacion = afiliaciones[0];
				var row = $local.tablaConsultaAdmision.row.add(afiliacion).draw();
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
	
	$local.$tablaConsultaAdmision.children("tbody").on("click", ".actualizar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var cliente = $local.tablaConsultaAdmision.row($local.$filaSeleccionada).data();
		//console.log(cliente);
		var fechaNacimiento = $funcionUtil.convertirDeFormatoAFormato(cliente.fechaNacimiento, "YYYY-MM-DD", "DD/MM/YYYY");
		$local.$fechaNacimiento.data("daterangepicker").setStartDate(fechaNacimiento);
		$local.$fechaNacimiento.data("daterangepicker").setEndDate(fechaNacimiento);
		$local.$idAfiliacion = cliente.idAfiliacion;
		$local.$tipoDocumento = cliente.idTipoDocumento;
		$local.$nroDocumento = cliente.numeroDocumento;
		$funcionUtil.llenarFormulario(cliente, $formAfiliacion);
		$local.$facultades.trigger("change", [ cliente.codigoEscuela ]);
		$local.$departamentosNac.trigger("change", [ cliente.idDistritoNac ]);
		$local.$estamentos2.trigger("change");
		$local.$actualizarAfiliacion.removeClass("hidden");
		$local.$registrarAfiliacion.addClass("hidden");
		$local.$modalAfiliacion.PopupWindow("open");
	});
	
	$local.$actualizarAfiliacion.on("click", function() {
		if (!$formAfiliacion.valid()) {
			return;
		}
		var afiliacion = $formAfiliacion.serializeJSON();
		//console.log(afiliacion);
		afiliacion.idAfiliacion = $local.$idAfiliacion;
		afiliacion.fechaNacimiento = $local.$fechaNacimiento.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "admision/afiliacion",
			data : JSON.stringify(afiliacion),
			beforeSend : function(xhr) {
				$local.tablaConsultaAdmision.clear().draw();
				$local.$actualizarAfiliacion.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formAfiliacion);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formAfiliacion);
				}
			},
			success : function(afiliaciones) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var afiliacion = afiliaciones[0];
				var row = $local.tablaConsultaAdmision.row.add(afiliacion).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalAfiliacion.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarAfiliacion.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
/*
	$formAfiliacion.find("input").keypress(function(event) {
		if (event.which == 13) {
			$local.$registrarAfiliacion.trigger("click");
			$local.$actualizarAfiliacion.trigger("click");
			return false;
		}
	});
	*/
	$local.$estamentos2.on("change", function() {
		var opcion = $(this).val();
		switch (opcion) {
		case "1":
			$local.$divCodAlumno.removeClass("hidden");
			$local.$divFacultad.removeClass("hidden");
			$local.$divEscuela.removeClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			//$local.$codAlumno.val("");
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
			//$local.$areaTra.val("");
			break;
		case "4":
			$local.$divCodAlumno.addClass("hidden");
			$local.$divFacultad.addClass("hidden");
			$local.$divEscuela.addClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			break;
		default:
			$local.$divCodAlumno.addClass("hidden");
			$local.$divFacultad.addClass("hidden");
			$local.$divEscuela.addClass("hidden");
			$local.$divAreaTrabajo.addClass("hidden");
			break;
		}
	});

});