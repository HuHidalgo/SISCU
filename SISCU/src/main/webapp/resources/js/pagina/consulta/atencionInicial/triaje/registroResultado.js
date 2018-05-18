function calcularResultadoImc(imc) {
	switch (true) {
	case (imc < 18.5): {
		$resultadoImc.text("Inferior al normal");
		$resultadoImc.addClass("input-group-addon-danger");
		$resultadoImc.removeClass("input-group-addon-success");
		$resultadoImc.removeClass("input-group-addon-warning");
		break;
	}
	case (imc >= 18.5 && imc <= 24.9): {
		$resultadoImc.text("Normal");
		$resultadoImc.removeClass("input-group-addon-danger");
		$resultadoImc.removeClass("input-group-addon-warning");
		$resultadoImc.addClass("input-group-addon-success");
		break;
	}

	case (imc >= 25 && imc <= 29.9): {
		$resultadoImc.text("Superior al normal");
		$resultadoImc.removeClass("input-group-addon-danger");
		$resultadoImc.removeClass("input-group-addon-success");
		$resultadoImc.addClass("input-group-addon-warning");
		break;
	}
	case (imc >= 30): {
		$resultadoImc.text("Obesidad");
		$resultadoImc.addClass("input-group-addon-danger");
		$resultadoImc.removeClass("input-group-addon-success");
		$resultadoImc.removeClass("input-group-addon-warning");
		break;
	}
	}
}

function calcularPulso(pulso) {
	if (pulso >= 60 && pulso <= 100) {
		$pulso.text("Normal");
		$pulso.addClass("input-group-addon-success");
		$pulso.removeClass("input-group-addon-danger");
	} else {
		$pulso.text("Observado");
		$pulso.addClass("input-group-addon-danger");
		$pulso.removeClass("input-group-addon-success");
	}

}

function calcularPresionSistolica(presionS) {
	if (presionS < 120) {
		$presionS.text("Hipotension");
		$presionS.addClass("input-group-addon-danger");
		$presionS.removeClass("input-group-addon-success");
	} else {
		if (presionS >= 120 && presionS <= 139) {
			$presionS.text("Normal");
			$presionS.addClass("input-group-addon-success");
			$presionS.removeClass("input-group-addon-danger");
		} else {
			if (presionS >= 140) {
				$presionS.text("Hipertensión");
				$presionS.addClass("input-group-addon-danger");
				$presionS.removeClass("input-group-addon-success");
			}
		}
	}
}

function calcularPresionDiastolica(presionD) {
	if (presionD < 80) {
		$presionD.text("Hipotension");
		$presionD.addClass("input-group-addon-danger");
		$presionD.removeClass("input-group-addon-success");
	} else {
		if (presionD >= 80 && presionD <= 89) {
			$presionD.text("Normal");
			$presionD.addClass("input-group-addon-success");
			$presionD.removeClass("input-group-addon-danger");
		} else {
			if (presionD >= 90) {
				$presionD.text("Hipertensión");
				$presionD.addClass("input-group-addon-danger");
				$presionD.removeClass("input-group-addon-success");
			}
		}
	}
}

$(document).ready(function() {

	
	// calculo de IMC
	$imc = $("#input-imc");
	$resultadoImc = $("#resultadoImc");
	$pulso = $("#pulso");
	$presionS = $("#presionS");
	$presionD = $("#presionD");
	$("#input-talla, #input-peso").keyup(function() {
		var talla = parseFloat($("#input-talla").val());
		var peso = parseFloat($("#input-peso").val());
		if (talla && peso) {
			var imc = (peso / Math.pow((talla / 100), 2)).toFixed(2);
			// console.log(imc);
			$imc.val(parseFloat(imc).toFixed(2) + "   kg/m²");
			calcularResultadoImc(imc);
		} else {
			$imc.val("No calculado");
			$resultadoImc.text("");
			$resultadoImc.removeClass("input-group-addon-danger");
			$resultadoImc.removeClass("input-group-addon-success");
			$resultadoImc.removeClass("input-group-addon-warning");
		}
	});
	// Mostrar pulso
	$("#input-pulso").keyup(function() {
		var pulso = parseFloat($("#input-pulso").val());
		if (pulso) {
			calcularPulso(pulso);
		} else {
			$pulso.text("");
			$pulso.removeClass("input-group-addon-danger");
			$pulso.removeClass("input-group-addon-success");
		}
	});
	// Mostrar presion sistolica
	$("#input-presionS").keyup(function() {
		var presionS = parseFloat($("#input-presionS").val());
		if (presionS) {
			calcularPresionSistolica(presionS);
		} else {
			$presionS.text("");
			$presionS.removeClass("input-group-addon-danger");
			$presionS.removeClass("input-group-addon-success");
		}
	});
	// Mostrar presion diastolica
	$("#input-presionD").keyup(function() {
		var presionD = parseFloat($("#input-presionD").val());
		if (presionD) {
			calcularPresionDiastolica(presionD);
		} else {
			$presionD.text("");
			$presionD.removeClass("input-group-addon-danger");
			$presionD.removeClass("input-group-addon-success");
		}
	});
	
});
