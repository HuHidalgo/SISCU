$(document).ready(function() {

	var $local = {
		$tablaMantenimiento : $("#tablaMantenimiento"),
		tablaMantenimiento : "",
		$modalMantenimiento : $("#modalMantenimiento"),
		$aniadirMantenimento : $("#aniadirMantenimiento"),
		$registrarMantenimiento : $("#registrarMantenimiento"),
		$filaSeleccionada : "",
		$actualizarMantenimiento : $("#actualizarMantenimiento"),
		$tiposDocumento : $("#tiposDocumento"),
		$sexos : $("#sexos"),
		$facultades : $("#facultades"),
		$escuelas : $("#escuelas"),
		$discapacidades : $("#discapacidades"),
		$fechaNacimiento : $("#fechaNacimiento"),
		codigoAlumnoSeleccionado : "",
		tipoAlumnoSeleccionado : "",
		filtrosSeleccionables : {},
	};

	$formMantenimiento = $("#formMantenimiento");
	$tiposAlumno = $("#tiposAlumno");

	$funcionUtil.crearDatePickerSimple($local.$fechaNacimiento, "DD/MM/YYYY");

	$funcionUtil.crearSelect2($local.$tiposDocumento, "Seleccione un Tipo de Documento");
	$funcionUtil.crearSelect2($local.$sexos, "Seleccione un Sexo");
	$funcionUtil.crearSelect2($tiposAlumno, "Seleccione un Tipo");
	$funcionUtil.crearSelect2($local.$facultades, "Seleccione una Facultad");
	$funcionUtil.crearSelect2($local.$escuelas, "Seleccione una Escuela");
	$funcionUtil.crearSelect2($local.$discapacidades, "Seleccione una Discapacidad");

	$.fn.dataTable.ext.errMode = 'none';

	$local.$tablaMantenimiento.on('xhr.dt', function(e, settings, json, xhr) {
		switch (xhr.status) {
		case 500:
			$local.tablaMantenimiento.clear().draw();
			break;
		}
	});

	$local.tablaMantenimiento = $local.$tablaMantenimiento.DataTable({
		"ajax" : {
			"url" : $variableUtil.root + "mantenimiento/alumno?accion=buscarTodos",
			"dataSrc" : ""
		},
		"language" : {
			"emptyTable" : "No hay Alumnos registrados"
		},
		"initComplete" : function() {
			$local.$tablaMantenimiento.wrap("<div class='table-responsive'></div>");
			$tablaFuncion.aniadirFiltroDeBusquedaEnEncabezado(this, $local.$tablaMantenimiento);
		},
		"columnDefs" : [ {
			"targets" : [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 ],
			"className" : "all filtrable",
		}, {
			"targets" : 16,
			"className" : "all dt-center",
			"defaultContent" : $variableUtil.botonActualizar + " " + $variableUtil.botonEliminar
		} ],
		"columns" : [ {
			"data" : 'codigoAlumno',
			"title" : "Código de Alumno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.tipoAlumno, row.descripcionTipoAlumno);
			},
			"title" : "Tipo de Alumno"
		}, {
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idTipoDocumento, row.descripcionTipoDocumento);
			},
			"title" : "Tipo de Doc."
		}, {
			"data" : 'numeroDocumento',
			"title" : "Num. de Doc."
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
				return $funcionUtil.convertirDeFormatoAFormato(row.fechaNacimiento, "YYYY-MM-DD", "DD/MM/YYYY");
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
			"data" : function(row) {
				return $funcionUtil.unirCodigoDescripcion(row.idDiscapacidad, row.descripcionDiscapacidad);
			},
			"title" : "Discapacidad"
		}, {
			"data" : "correoInstitucional",
			"title" : 'Correo Inst.'
		}, {
			"data" : "correoPersonal",
			"title" : 'Correo Pers.'
		}, {
			"data" : null,
			"title" : 'Acción'
		} ]
	});

	$local.$tablaMantenimiento.find("thead").on('keyup', 'input.filtrable', function() {
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(this.value).draw();
	});

	$local.$tablaMantenimiento.find("thead").on('change', 'select', function() {
		var val = $.fn.dataTable.util.escapeRegex($(this).val());
		$local.tablaMantenimiento.column($(this).parent().index() + ':visible').search(val ? '^' + val + '$' : '', true, false).draw();
	});

	$local.$modalMantenimiento.PopupWindow({
		title : "Mantenimiento de Alumno",
		autoOpen : false,
		modal : false,
		height : 440,
		width : 626,
	});

	$local.$aniadirMantenimento.on("click", function() {
		$funcionUtil.prepararFormularioRegistro($formMantenimiento);
		$local.$actualizarMantenimiento.addClass("hidden");
		$local.$registrarMantenimiento.removeClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$modalMantenimiento.on("open.popupwindow", function() {
		$formMantenimiento.find("input:not([disabled]):first").focus();
		$local.$modalMantenimiento.PopupWindow("maximize");
	});

	$local.$modalMantenimiento.on("close.popupwindow", function() {
		$local.codigoBINSeleccionado = 0;
	});

	$formMantenimiento.find("input").keypress(function(event) {
		if (event.which == 13) {
			if (!$local.$registrarMantenimiento.hasClass("hidden")) {
				$local.$registrarMantenimiento.trigger("click");
				return false;
			} else {
				if (!$local.$actualizarMantenimiento.hasClass("hidden")) {
					$local.$actualizarMantenimiento.trigger("click");
				}
				return false;
			}
		}
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
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
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

	$local.$registrarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var alumno = $formMantenimiento.serializeJSON();
		alumno.fechaNacimiento = $local.$fechaNacimiento.val() == "" || $local.$fechaNacimiento.val() == null ? null : $local.$fechaNacimiento.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "POST",
			url : $variableUtil.root + "mantenimiento/alumno",
			data : JSON.stringify(alumno),
			beforeSend : function(xhr) {
				$local.$registrarMantenimiento.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(alumno) {
				$funcionUtil.notificarException($variableUtil.registroExitoso, "fa-check", "Aviso", "success");
				var row = $local.tablaMantenimiento.row.add(alumno).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$registrarMantenimiento.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".actualizar", function() {
		$funcionUtil.prepararFormularioActualizacion($formMantenimiento);
		$local.$filaSeleccionada = $(this).parents("tr");
		var alumno = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		var fechaNacimiento = $funcionUtil.convertirDeFormatoAFormato(alumno.fechaNacimiento, "YYYY-MM-DD", "DD/MM/YYYY");
		$local.$fechaNacimiento.data("daterangepicker").setStartDate(fechaNacimiento);
		$local.$fechaNacimiento.data("daterangepicker").setEndDate(fechaNacimiento);
		$local.codigoAlumnoSeleccionado = alumno.codigoAlumno;
		$local.tipoAlumnoSeleccionado = alumno.tipoAlumno;
		$funcionUtil.llenarFormulario(alumno, $formMantenimiento);
		$local.$facultades.trigger("change", [ alumno.codigoEscuela ]);
		$local.$actualizarMantenimiento.removeClass("hidden");
		$local.$registrarMantenimiento.addClass("hidden");
		$local.$modalMantenimiento.PopupWindow("open");
	});

	$local.$actualizarMantenimiento.on("click", function() {
		if (!$formMantenimiento.valid()) {
			return;
		}
		var alumno = $formMantenimiento.serializeJSON();
		alumno.codigoAlumno = $local.codigoAlumnoSeleccionado;
		alumno.tipoAlumno = $local.tipoAlumnoSeleccionado;
		alumno.fechaNacimiento = $local.$fechaNacimiento.val() == "" || $local.$fechaNacimiento.val() == null ? null : $local.$fechaNacimiento.data("daterangepicker").startDate.format('YYYY-MM-DD');
		$.ajax({
			type : "PUT",
			url : $variableUtil.root + "mantenimiento/alumno",
			data : JSON.stringify(alumno),
			beforeSend : function(xhr) {
				$local.$actualizarMantenimiento.attr("disabled", true).find("i").removeClass("fa-pencil-square").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader('Content-Type', 'application/json');
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			statusCode : {
				400 : function(response) {
					$funcionUtil.limpiarMensajesDeError($formMantenimiento);
					$funcionUtil.mostrarMensajeDeError(response.responseJSON, $formMantenimiento);
				}
			},
			success : function(alumno) {
				$funcionUtil.notificarException($variableUtil.actualizacionExitosa, "fa-check", "Aviso", "success");
				var row = $local.tablaMantenimiento.row($local.$filaSeleccionada).data(alumno).draw();
				row.show().draw(false);
				$(row.node()).animateHighlight();
				$local.$modalMantenimiento.PopupWindow("close");
			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$actualizarMantenimiento.attr("disabled", false).find("i").addClass("fa-pencil-square").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});

	$local.$tablaMantenimiento.children("tbody").on("click", ".eliminar", function() {
		$local.$filaSeleccionada = $(this).parents("tr");
		var alumno = $local.tablaMantenimiento.row($local.$filaSeleccionada).data();
		$.confirm({
			icon : "fa fa-info-circle",
			title : "Aviso",
			content : "¿Desea eliminar el Alumno <b>'" + alumno.codigoAlumno + " - " + alumno.tipoAlumno + ", " + alumno.apellidoPaterno + ", " + alumno.nombres + "'<b/>?",
			theme : "bootstrap",
			buttons : {
				Aceptar : {
					action : function() {
						var confirmar = $.confirm({
							icon : 'fa fa-spinner fa-pulse fa-fw',
							title : "Eliminando...",
							content : function() {
								var self = this;
								self.buttons.close.hide();
								$.ajax({
									type : "DELETE",
									url : $variableUtil.root + "mantenimiento/alumno",
									data : JSON.stringify(alumno),
									autoclose : true,
									beforeSend : function(xhr) {
										xhr.setRequestHeader('Content-Type', 'application/json');
										xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
									}
								}).done(function(response) {
									$funcionUtil.notificarException(response, "fa-check", "Aviso", "success");
									$local.tablaMantenimiento.row($local.$filaSeleccionada).remove().draw(false);
									confirmar.close();
								}).fail(function(xhr) {
									confirmar.close();
									switch (xhr.status) {
									case 400:
										$funcionUtil.notificarException($funcionUtil.obtenerMensajeErrorEnCadena(xhr.responseJSON), "fa-warning", "Aviso", "warning");
										break;
									case 409:
										var mensaje = $funcionUtil.obtenerMensajeError("El Alumno <b>" + alumno.codigoAlumno + " - " + alumno.apellidoPaterno + " " + alumno.apellidoMaterno + ", " + alumno.nombres + "</b>", xhr.responseJSON, $variableUtil.accionEliminado);
										$funcionUtil.notificarException(mensaje, "fa-warning", "Aviso", "warning");
										break;
									}
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
});