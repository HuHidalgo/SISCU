$(document).ready(function() {

	var $local = {
		/* Filtros */
		$facultades : $("#facultades"),
		$campanias : $("#campanias"),
		$codigoAlumno : $("#codigoAlumno"),
		$buscar : $("#buscar"),
		/* Seleccion */
		$filaSeleccionada : "",
		/* label de tipo de certificado */
		$labelTipoCertificado : $("#label-tipo-certificado"),
		idTipoCertificadoSeleccionado : "",
		/* Examen de Laboratorio */
		$indicadoresRPR : $("#indicadoresRPR"),
		$diluciones : $("#diluciones"),
		$indicadoresHemograma : $("#indicadoresHemograma"),
		$gruposSanguineo : $("#gruposSanguineo"),
		$factoresRh : $("#factoresRh"),
		/* Examen de Radiologia */
		$resultadosRadiologico : $("#resultadosRadiologico"),
		$observacion : $("#observacion"),
		/* Examen de Odontología */
		$selectMaloclusion : $("#select-maloclusion"),
		$selectHigiene : $("#select-higiene"),
		$selectProtesis : $("#select-protesis")
	}

	$funcionUtil.crearSelect2($local.$facultades);
	$funcionUtil.crearSelect2($local.$campanias);
	
	/* Examen de Laboratorio */
	$funcionUtil.crearSelect2($local.$indicadoresRPR, "Seleccione un RPR");
	$funcionUtil.crearSelect2($local.$diluciones, "Seleccione una Dilución");
	$funcionUtil.crearSelect2($local.$indicadoresHemograma, "Seleccione un Hemograma");
	$funcionUtil.crearSelect2($local.$gruposSanguineo, "Seleccione un Gpo. Sanguíneo");
	$funcionUtil.crearSelect2($local.$factoresRh, "Seleccione un Factor de RH");
	
	/* Examen de Radiología */
	$funcionUtil.crearSelect2($local.$resultadosRadiologico, "Seleccione un Resultado");
	
	/* Examen de Odontologia */
	$funcionUtil.crearSelect2($local.$selectMaloclusion, "Seleccione una Maloclusión");
	$funcionUtil.crearSelect2($local.$selectHigiene, "Seleccione un Estado de Higiene");
	$funcionUtil.crearSelect2($local.$selectProtesis, "Seleccione un Estado de Prótesis");

	$local.$campanias.on("change", function() {
		var $opcionSeleccionada = $local.$campanias.find("option:selected");
		var idTipoCertificado = $opcionSeleccionada.attr("data-id-tipo-certificado");
		var descripcionTipoCertificado = $opcionSeleccionada.attr("data-descripcion-tipo-certificado");
		$local.$labelTipoCertificado.text($funcionUtil.unirCodigoDescripcion(idTipoCertificado, descripcionTipoCertificado));
		$local.idTipoCertificadoSeleccionado = idTipoCertificado;
	});

	$local.$campanias.trigger("change");

});