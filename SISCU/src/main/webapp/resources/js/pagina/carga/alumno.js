$(document).ready(function() {

	var $local = {
		$cargarAlumno : $("#cargarAlumno"),
		$tiposAlumno : $("#tiposAlumno")
	};

	$formCargaAlumno = $("#formCargaAlumno");
	$funcionUtil.crearSelect2($local.$tiposAlumno, "Seleccione un Tipo de Alumno");
	
	$formCargaAlumno.validate({
		focusCleanup : true,
		rules : {
			tipoAlumno : {
				required : true,
				notOnlySpace : true,
				rangelength : [ 1, 1 ]
			}
		},
		messages : {
			tipoAlumno : {
				required : "Seleccione un Tipo de Alumno.",
				notOnlySpace : "El Tipo de Alumno no puede contener solo espacios en blanco.",
				rangelength : "El Tipo de Alumno debe contener 1 car&aacute;cter."
			}
		}
	});

	$local.$cargarAlumno.on("click", function(e) {
		e.preventDefault();
		if (!$formCargaAlumno.valid()) {
			return;
		}
		var form = $("#formCargaAlumno")[0];
		var data = new FormData(form);
		var idTipoAlumno = $local.$tiposAlumno.val();
		$.ajax({
			type : "POST",
			enctype : 'multipart/form-data',
			url : $variableUtil.root + "carga/alumno/uploadfile/" + idTipoAlumno + "?accion=cargar",
			data : data,
			processData : false,
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				$local.$cargarAlumno.attr("disabled", true).find("i").removeClass("fa-floppy-o").addClass("fa-spinner fa-pulse fa-fw");
				xhr.setRequestHeader("X-CSRF-TOKEN", $variableUtil.csrf);
			},
			success : function(controlLotes) {

			},
			error : function(response) {
			},
			complete : function(response) {
				$local.$cargarAlumno.attr("disabled", false).find("i").addClass("fa-floppy-o").removeClass("fa-spinner fa-pulse fa-fw");
			}
		});
	});
});