var $funcionUtil = {};
var $variableUtil = {};
var $tablaFuncion = {};

$(document).ready(function() {

	var path = location.pathname;
	$('.x-navigation li').find("a").filter(function() {
		return $(this).attr('href') === path;
	}).parent("li").addClass('active').parents("li.xn-openable").addClass("active");

	$variableUtil = {
		anchoModalResultadoLaboratorio : 789,
		altoModalResultadoLaboratorio : 553,
		anchoModalResultadoRadiologico : 700,
		altoModalResultadoRadiologico : 466,
		anchoModalResultadoTriaje : 785,
		altoModalResultadoTriaje : 569,
		anchoModalResultadoOdontologico : 717,
		altoModalResultadoOdontologico : 609,
		anchoModalResultadoPsicologico : 729,
		altoModalResultadoPsicologico : 442,
		csrf : $('meta[name=_csrf]').attr("content"),
		root : $("meta[name='root']").attr("content"),
		posIzquierdo : "izquierdo",
		posDerecho : "derecho",
		rutaIconoSimp : this.root + "resources/css/icono-simp/",
		botonEliminar : "<button class='btn btn-xs btn-danger eliminar' title='Eliminar' data-tooltip='tooltip'><i class='fa fa-trash'></i></button>",
		botonActualizar : "<button class='btn btn-xs btn-primary actualizar' title='Actualizar' data-tooltip='tooltip'><i class='fa fa-pencil-square'></i></button>",
		botonExportarDocx : "<button class='btn btn-xs btn-primary exportar-docx' title='Exportar .DOCX' data-tooltip='tooltip'><i class='fa fa-file-word-o'></i></button>",
		botonRegistrar : "<button class='btn btn-xs btn-success registrar' title='Registrar' data-tooltip='tooltip'><i class='fa fa-floppy-o'></i></button>",
		botonAniadirDetalle : "<button class='btn btn-xs btn-success aniadir-detalle' title='Añadir' data-tooltip='tooltip'><i class='fa fa-plus'></i></button>",
		botonVerDetalle : "<button class='btn btn-xs btn-primary ver-detalle' title='Ver Detalle' data-tooltip='tooltip'><i class='fa fa-eye'></i></button>",
		botonEjecutar : "<button class='btn btn-xs btn-success ejecutar' title='Ejecutar proceso' data-tooltip='tooltip'><i class='fa fa-flash'></i></button>",
		botonPermisos : "<button class='btn btn-xs btn-success permiso' title='Asignar permisos' data-tooltip='tooltip'><i class='fa fa-check-square-o'></i></button>",
		tableBotonExportarXlsx : "<button id='exportarXlsx' class='btn btn-success pull-right input-sm' type='button'><i class='fa fa-download'></i> XLSX</button>",
		$labelPrimary : $("<label class='label label-primary label-size-12'></label>"),
		$labelSuccess : $("<label class='label label-success label-size-12'></label>"),
		$labelDanger : $("<label class='label label-danger label-size-12'></label>"),
		labelAtendido : "<label class='label label-primary label-size-12'>ATENDIDO</label>",
		labelNoRegistrado : "<label class='label label-primary label-size-12' title='AÚN NO REGISTRADO' data-tooltip='tooltip'>N/R</label>",
		registroExitoso : "Se registró correctamente",
		asignacionExitosa : "Se asignaron los recursos exitosamente",
		busquedaSinResultados : "No se han encontrado resultados para su búsqueda. Pruebe diferentes opciones o filtros.",
		cambioContrasenia : "<strong>¡Cuidado!</strong> Cambie su contraseña, sino sera bloqueado",
		actualizacionExitosa : "Se actualizó correctamente",
		accionEliminado : "eliminado(a)",
		accionActualizado : "actualizado(a)",
		filtrable : "filtrable",
		noFiltrable : "noFiltrable",
		seleccionable : "seleccionable",
		idFilaFiltro : "filaFiltro",
		claseEncabezadoFiltros : "encabezadoFiltro",
		claseDataNoDefinida : "data-no-definida",
		claseDataVacia : "data-vacia",
		claseInsertableOpcionesHtml : "insertable-opciones-html",
		arregloSiNo : [ "0", "1" ],
		camposVacios : "Debe escoger algún Filtro para poder realizar la Búsqueda",
	};

	$funcionUtil = {
		aniadirTitleParaTooltip : function($element, titulo) {
			$element.attr({
				"title" : titulo,
				"data-original-title" : titulo
			});
		},
		esHemoglobinaObservado : function(hemoglobina, sexo) {
			return (!((hemoglobina >= 11 && sexo == "F") || (hemoglobina >= 12 && sexo == "M"))) ? true : false;
		},
		determinarEstadoHemoglobina : function(hemoglobina, sexo, $spanEstadoHemoglobina) {
			var hemoglobinaFloat = parseFloat(hemoglobina);
			if (!hemoglobinaFloat) {
				$spanEstadoHemoglobina.text('NINGUNO');
				$spanEstadoHemoglobina.removeClass("input-group-addon-success input-group-addon-danger");
				return;
			}
			var observado = this.esHemoglobinaObservado(hemoglobinaFloat, sexo);
			$spanEstadoHemoglobina.text((observado) ? 'OBSERVADO' : 'NORMAL');
			$spanEstadoHemoglobina.removeClass((observado) ? "input-group-addon-success" : "input-group-addon-danger");
			$spanEstadoHemoglobina.addClass((observado) ? "input-group-addon-danger" : "input-group-addon-success");
		},
		construirNotEqual : function(name, argumentos) {
			var clause = '';
			for (var i = 0; i < argumentos.length; i++) {
				clause = clause + '[' + name + '!=\'' + argumentos[i] + '\']';
			}
			return clause;
		},
		construirEqual : function(name, argumentos) {
			var clause = '';
			for (var i = 0; i < argumentos.length; i++) {
				clause = clause + '[' + name + '=\'' + argumentos[i] + '\'],';
			}
			if (clause != '') {
				return clause.slice(0, -1);
			}
			return clause;
		},
		obtenerMensajeError : function(mensajeInicial, mensajeError, accion) {
			var mensaje = mensajeInicial || "";
			switch (mensajeError.codigo_error) {
			case 547:
				return mensaje += " no puede ser " + accion + ", porque est&aacute; siendo usado(a) actualmente.";
			default:
				return "Ocurrió un problema desconocido.";
			}
		},
		notificarException : function(mensaje, icon, titulo, tipo) {
			$.notify({
				icon : "fa " + icon,
				title : " <strong>" + titulo + ": </strong>",
				message : "<p>" + mensaje + "</p>"
			}, {
				delay : 6000,
				type : tipo
			})
		},
		insertarEtiquetaSiNo : function(valor_booleano) {
			switch (true) {
			case (valor_booleano == "0" || valor_booleano == "NO" || valor_booleano == false):
				return $variableUtil.$labelDanger.text("NO").get(0).outerHTML;
			case (valor_booleano == "1" || valor_booleano == "SI" || valor_booleano == true):
				return $variableUtil.$labelSuccess.text("SI").get(0).outerHTML;
			default:
				return "-";
			}
		},
		insertarBoolNumerico : function(cuenta_compensacion) {
			switch (cuenta_compensacion) {
			case 0:
				return $variableUtil.$labelPrimary.text("NO").get(0).outerHTML;
			case 1:
				return $variableUtil.$labelPrimary.text("SI").get(0).outerHTML;
			default:
				return $variableUtil.$labelPrimary.text("No Especificado").get(0).outerHTML;
			}
		},
		limpiarMensajesDeError : function(formulario) {
			formulario.find(".form-group, .group").removeClass("has-error").find(".help-block").remove();
		},
		mostrarMensajeDeError : function(mensajesDeError, formulario) {
			var notificacionMensajeError = [];
			$.each(mensajesDeError, function(i, mensaje) {
				var idSpanError = mensaje.campoError + "-error";
				var input = formulario.find("input[name=" + mensaje.campoError + "], select[name=" + mensaje.campoError + "], textarea[name=" + mensaje.campoError + "]").first();
				if (input.length > 0) {
					if (input.parent(".input-group").length > 0 || input.parent(".radio-inline").length > 0) {
						input.attr({
							"aria-required" : true,
							"aria-describedby" : idSpanError,
							"aria-invalid" : true
						}).parent().parent().append("<span id='" + idSpanError + "' class='help-block'>" + mensaje.mensajeError + "</span>").parents(".group, .form-group").first().addClass("has-error");
					} else {
						input.attr({
							"aria-required" : true,
							"aria-describedby" : idSpanError,
							"aria-invalid" : true
						}).parent().append("<span id='" + idSpanError + "' class='help-block'>" + mensaje.mensajeError + "</span>").parents(".group, .form-group").first().addClass("has-error");
					}
				} else {
					notificacionMensajeError.push(mensaje.mensajeError);
				}
			});
			if (notificacionMensajeError.length > 0) {
				this.notificarException(notificacionMensajeError.join(".<br/>"), "fa-warning", "Aviso", "warning");
			}
		},
		llenarFormulario : function(dto, formulario) {
			$.each(dto, function(i, value) {
				var input = formulario.find("input[name=" + i + "], select[name=" + i + "], textarea[name=" + i + "]");
				if (input.is(":checkbox")) {
					input.prop("checked", (value == "1" || value == "true"));
				} else if (input.is(":radio")) {
					input.filter("[value=" + value + "]").prop('checked', true);
				} else {
					input.val(value);
				}
				if (input.hasClass("select2")) {
					input.val(value).trigger("change.select2");
				}
			});
		},
		limpiarCamposFormulario : function(formulario) {
			formulario.trigger("reset");
			formulario.find(".select2").val("").trigger("change.select2");
		},
		prepararFormularioRegistro : function(formulario) {
			formulario.find(".elemento-desactivable").prop("disabled", false);
			this.limpiarCamposFormulario(formulario);
			this.limpiarMensajesDeError(formulario);
			formulario.validate().resetForm();
		},
		prepararFormularioActualizacion : function(formulario) {
			formulario.find(".elemento-desactivable").prop("disabled", true);
			this.limpiarMensajesDeError(formulario);
			formulario.validate().resetForm();
		},
		obtenerMensajeErrorEnCadena : function(mensajesDeError) {
			var mensajeCadena = "";
			$.each(mensajesDeError, function(i, mensaje) {
				mensajeCadena += mensaje.mensajeError + "<br/>";
			});
			return mensajeCadena;
		},
		filaFiltro : function() {
			return "<tr id='" + $variableUtil.idFilaFiltro + "'></tr>"
		},
		encabezadoFiltro : function() {
			return "<th class='" + $variableUtil.claseEncabezadoFiltros + "'></th>"
		},
		unique : function(array) {
			return $.grep(array, function(el, index) {
				return index === $.inArray(el, array);
			});
		},
		unirCodigoDescripcion : function(codigo, descripcion) {
			if (codigo == null || codigo == undefined) {
				codigo = "";
			}
			if (descripcion == null || descripcion == undefined) {
				descripcion = "";
			}
			return codigo + " - " + descripcion;
		},
		crearSelect2 : function(select, textoPorDefecto) {
			var propiedad = {
				placeholder : textoPorDefecto,
				language : {
					noResults : function() {
						return "No se encontró resultados";
					}
				},
				"width" : "100%",
				"theme" : "bootstrap",
				"dropdownAutoWidth" : true,
				"dropdownParent" : select.parent()
			};
			if (textoPorDefecto != undefined && textoPorDefecto != null) {
				propiedad.placeholder = textoPorDefecto;
			}
			if (select.hasClass("encabezado")) {
				propiedad.containerCssClass = ":all:";
			}
			select.select2(propiedad);
		},
		templateResultCie10 : function(data) {
			return $funcionUtil.unirCodigoDescripcion(data.id, data.text);
		},
		templateSelectionCie10 : function(data) {
			if (data.id == "") {
				return "Seleccione un CIE 10";
			}
			return $funcionUtil.unirCodigoDescripcion(data.id, data.text);
		},
		crearDatePickerSimple : function(input, format) {
			format = format || "DD/MM/YYYY";
			input.daterangepicker({
				"singleDatePicker" : true,
				"showDropdowns" : true,
				"locale" : {
					direction : 'ltr',
					format : format,
					separator : ' - ',
					customRangeLabel : 'Personalizado',
					daysOfWeek : [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
					monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
					firstDay : 1
				},
				"maxDate" : moment()
			}, function(start, end, label) {
			});
		},
		crearDateRangePickerSimple : function(input) {
			input.daterangepicker({
				"alwaysShowCalendars" : true,
				"ranges" : {
					'Hoy' : [ moment(), moment() ],
					'Ayer' : [ moment().subtract(1, 'days'), moment().subtract(1, 'days') ],
					'Últimos 07 días' : [ moment().subtract(6, 'days'), moment() ],
					'Últimos 30 días' : [ moment().subtract(29, 'days'), moment() ],
					'Este mes' : [ moment().startOf('month'), moment().endOf('month') ],
					'Último mes' : [ moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month') ]
				},
				"startDate" : moment(),
				"endDate" : moment(),
				"opens" : "left",
				"locale" : {
					direction : 'ltr',
					format : 'DD/MM/YYYY',
					separator : ' - ',
					applyLabel : 'Aplicar',
					cancelLabel : 'Cancelar',
					fromLabel : 'Desde',
					toLabel : 'A',
					customRangeLabel : 'Personalizado',
					daysOfWeek : [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
					monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
					firstDay : 1
				},
				"maxDate" : moment()
			}, function(start, end, label) {
			});
		},
		animacionAjax : function($button) {
			var $this = this;
			$(document).ajaxStart(function() {
				$this.animacionBusqueda($button);
				console.log("Start");
			}).ajaxStop(function() {
				$this.animacionBusqueda($button);
				console.log("Stop");
			});
		},
		animacionBusqueda : function($boton, action, callback) {
			if (!$boton.hasClass("refreshing")) {
				$boton.addClass("refreshing");
				$boton.attr("disabled", true).find("i").removeClass("fa-search").addClass("fa-spinner fa-pulse fa-fw")
				if (action && action === "shown" && typeof callback === "function")
					callback();
			} else {
				$boton.removeClass("refreshing");
				$boton.attr("disabled", false).find("i").addClass("fa-search").removeClass("fa-spinner fa-pulse fa-fw");
				if (action && action === "hidden" && typeof callback === "function")
					callback();
			}
		},
		crearDateRangePickerConsulta : function(input) {
			input.daterangepicker({
				"alwaysShowCalendars" : true,
				"ranges" : {
					'Hoy' : [ moment(), moment() ],
					'Ayer' : [ moment().subtract(1, 'days'), moment().subtract(1, 'days') ],
					'Últimos 07 días' : [ moment().subtract(6, 'days'), moment() ],
					'Últimos 30 días' : [ moment().subtract(29, 'days'), moment() ],
					'Este mes' : [ moment().startOf('month'), moment().endOf('month') ],
					'Último mes' : [ moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month') ]
				},
				"autoUpdateInput" : false,
				"opens" : "left",
				"locale" : {
					direction : 'ltr',
					format : 'DD/MM/YYYY',
					separator : ' - ',
					applyLabel : 'Aplicar',
					cancelLabel : 'Limpiar',
					fromLabel : 'Desde',
					toLabel : 'A',
					customRangeLabel : 'Personalizado',
					daysOfWeek : [ 'Dom', 'Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab' ],
					monthNames : [ 'Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre' ],
					firstDay : 1
				},
				"maxDate" : moment()
			}, function(start, end, label) {
			});
			input.on('apply.daterangepicker', function(ev, picker) {
				$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
			});
			input.on('cancel.daterangepicker', function(ev, picker) {
				$(this).val('');
			});
		},
		obtenerFechasDateRangePicker : function(input) {
			var rangoFecha = {};
			if (input.val() == undefined || input.val() == "") {
				rangoFecha.fechaInicio = "";
				rangoFecha.fechaFin = "";
				return rangoFecha;
			}
			rangoFecha.fechaInicio = input.data("daterangepicker").startDate.format('YYYY-MM-DD');
			rangoFecha.fechaFin = input.data("daterangepicker").endDate.format('YYYY-MM-DD');
			return rangoFecha;
		},
		convertirDeFormatoAFormato : function(fecha, formatoOrigen, formatoFinal) {
			return moment(fecha, formatoOrigen).format(formatoFinal);
		},
		camposVacios : function(formulario) {
			var camposVacios = true;
			formulario.find(".filtro").each(function() {
				var campo = $(this);
				var valorCampo = "";
				if (campo.is(":radio") && !campo.is(":checked")) {
					valorCampo = "";
				} else {
					valorCampo = campo.val();
				}
				if (valorCampo != undefined && valorCampo != "" && valorCampo != "-1") {
					camposVacios = false;
					return camposVacios;
				}
			});
			return camposVacios;
		},
		descripcionLarga : function(txt) {
			$('[data-tooltip="tooltip"]').tooltip({
				placement : 'top',
				title : txt
			});
		}
	};

	$tablaFuncion = {
		aniadirBotonEnTabla : function(elemento, boton, lugar) {
			if (lugar == $variableUtil.posIzquierdo) {
				elemento.append(boton);
				elemento.find("label").addClass("margin-left-2");
			} else if (lugar == $variableUtil.posDerecho) {
				elemento.prepend(boton);
				elemento.find("button").addClass("margin-left-2");
			}
		},
		aniadirFiltroAlSeleccionable : function(select, filtro) {
			if (filtro == "0") {
				filtro = "NO";
			} else {
				if (filtro == "1") {
					filtro = "SI";
				}
			}
			select.append("<option value='" + filtro + "'>" + filtro + "</option>");
		},
		aniadirFiltroBusquedaCajaTexto : function(encabezadoTabla, encabezadoFiltros) {
			encabezadoFiltros.html('<input type="text" placeholder="' + encabezadoTabla.text() + '"class="input-sm form-control filtrable" style="width:100%;" />');
		},
		aniadirFiltroBusquedaSeleccionable : function(encabezadoTabla, encabezadoFiltros, filtrosSeleccionables, column) {
			var funcion = this;
			var $select = $("<select class='encabezado input-sm form-control' style='width:100%;'><option value='' selected='selected'>Todos</option></select>").appendTo(encabezadoFiltros);
			switch (true) {
			case encabezadoTabla.hasClass($variableUtil.claseDataNoDefinida):
				$.each(filtrosSeleccionables[encabezadoTabla.index()], function(i, filtro) {
					funcion.aniadirFiltroAlSeleccionable($select, filtro);
				});

				break;
			case encabezadoTabla.hasClass($variableUtil.claseDataVacia):
				break;
			case encabezadoTabla.hasClass($variableUtil.claseInsertableOpcionesHtml):
				$select.append(filtrosSeleccionables[encabezadoTabla.index()]);
				break;
			default:
				column.data().unique().sort().each(function(filtro, j) {
					funcion.aniadirFiltroAlSeleccionable($select, filtro);
				});
				break;
			}
			var claseAgregable = encabezadoTabla.attr("class").match(/agregable[\w-]*\b/);
			if (claseAgregable != null) {
				$select.addClass(claseAgregable[0]);
			}
			if (encabezadoTabla.hasClass("select2")) {
				$funcionUtil.crearSelect2($select);
			}
		},
		aniadirFiltroDeBusquedaEnEncabezado : function(datatable, tabla, filtrosSeleccionables) {
			var $thead = tabla.find("thead");
			var numeroEncabezados = $thead.find("tr").find("th").length;
			var funcion = this;
			filtrosSeleccionables = filtrosSeleccionables || {};
			$thead.append($funcionUtil.filaFiltro());
			$thead.find("#" + $variableUtil.idFilaFiltro).append($($funcionUtil.encabezadoFiltro()).multiply(numeroEncabezados));
			datatable.api().columns().every(function() {
				var column = this;
				var encabezadoTabla = $(column.header());
				var encabezadoFiltros = $thead.find("#" + $variableUtil.idFilaFiltro).find("th").eq(encabezadoTabla.index());
				switch (true) {
				case encabezadoTabla.hasClass($variableUtil.filtrable):
					funcion.aniadirFiltroBusquedaCajaTexto(encabezadoTabla, encabezadoFiltros);
					break;
				case encabezadoTabla.hasClass($variableUtil.seleccionable):
					funcion.aniadirFiltroBusquedaSeleccionable(encabezadoTabla, encabezadoFiltros, filtrosSeleccionables, column);
					break;
				}
			});
		},
		actualizarFiltroBusquedaSeleccionable : function(select, opcionAgregar) {
			if (select.children("option[value='" + opcionAgregar + "']").length === 0) {
				select.append("<option value='" + opcionAgregar + "' >" + opcionAgregar + "</option>");
			}
		},
		trasladarHaciaSelect : function(select, data, valor, texto) {
			select.empty();
			$.each(data, function(i, fila) {
				select.append("<option value='" + fila[valor] + "' >" + fila[texto] + "</option>");
			});
			$funcionUtil.crearSelect2(select, "sera o.o");
		},
		pintarMontosComisiones : function($tabla, selector) {
			$tabla.find(selector).filter(function() {
				var celda = $(this);
				var valor = parseFloat(celda.text());
				if (valor > 0) {
					celda.css("color", "blue");
				} else if (valor < 0) {
					celda.css("color", "red");
				} else {
					celda.css("color", "inherited");
				}
			});
		}
	};

	$(".select2").on("select2:close", function() {
		$(this).trigger('blur');
	});

	$("input[type=text]").on("click", function() {
		$(this).select();
	});

	$(".upperCase").bestupper();

	$('body').tooltip({
		selector : "[data-tooltip=tooltip]",
		container : "body"
	});

	$(".lettersOnlyAccent").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\u00f1\u00d1\u00E0-\u00FC\' ']/g, '')) {
			this.value = this.value.replace(/[^a-z\u00f1\u00d1\u00E0-\u00FC\' ']/g, '');
		}
	});

	$(".lettersOnly").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\' ']/g, '')) {
			this.value = this.value.replace(/[^a-z\' ']/g, '');
		}
	});

	$(".numbersOnly").on("keyup", function() {
		if (this.value != this.value.replace(/[^0-9]/g, '')) {
			this.value = this.value.replace(/[^0-9]/g, '');
		}
	});

	$(".notDash").on("keyup", function() {
		if (this.value != this.value.replace(/[\-]/g, '')) {
			this.value = this.value.replace(/[\-]/g, '');
		}
	});

	$(".numbersOnlyAndDot").on("keyup", function() {
		if (this.value != this.value.replace(/[^0-9\.]/g, '')) {
			this.value = this.value.replace(/[^0-9\.]/g, '');
		}
	});

	$(".numbersAndLettersOnlyAndDot").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\u00f1\u00d1\^0-9\.]/g, '')) {
			this.value = this.value.replace(/[^a-z\u00f1\u00d1\^0-9\.]/g, '');
		}
	});

	$(".numbersAndLettersOnly").on("keyup", function() {
		if (this.value != this.value.replace(/[^a-z\u00f1\u00d1\^0-9]/g, '')) {
			this.value = this.value.replace(/[^a-z\u00f1\u00d1\^0-9]/g, '');
		}
	});

	$.ajaxSetup({
		statusCode : {
			409 : function(response) {
				if (response.responseJSON == undefined) {
					$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
				}
			},
			500 : function(response) {
				$funcionUtil.notificarException(response.responseText, "fa-warning", "Aviso", "warning");
			}
		}
	});
});