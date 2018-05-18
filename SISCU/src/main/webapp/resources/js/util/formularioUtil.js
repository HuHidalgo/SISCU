$(document).ready(function() {
	$.validator.addMethod('selectlength', function(value, element, param) {
		if (value == null) {
			return true;
		} else {
			if ($.isArray(param)) {
				var min = param[0];
				var max = param[1];
				return value.length >= min && value.length <= max;
			} else {
				var tamanio = param;
				return value.length == tamanio
			}
		}
	}, $.validator.format('Please select at least {0} things.'));

	jQuery.validator.addMethod("notOnlySpace", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return value.replace(/\s/g, '').length > 0;
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("notOnlySpaceMoreOneBlank", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return !(value.length > 1 && value.replace(/\s/g, '').length == 0);
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("notOnlySpaceOrEmpty", function(value, element, string) {
		if (value == null) {
			return true;
		}
		return value.length == 0 ? true : value.replace(/\s/g, '').length > 0;
	}, $.validator.format("Please enter '{0}'"));

	jQuery.validator.addMethod("lettersonly", function(value, element) {
		return this.optional(element) || /^[a-z\u00f1\u00d1\u00E0-\u00FC\' ']+$/i.test(value);
	}, "Ingrese solo letras");

	jQuery.validator.addMethod("soloalfanumericos", function(value, element) {
		return this.optional(element) || /^[a-z0-9]+$/i.test(value);
	}, "Ingrese solo alfanumericos");

	jQuery.validator.addMethod("solodigitos", function(value, element) {
		return this.optional(element) || /^[0-9]+$/i.test(value);
	}, "Ingrese solo digitos");

	jQuery.validator.addMethod("hora", function(value, string) {
		return false || /^(?:2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$/i.test(value);
	}, "Ingrese solo horas");

	jQuery.validator.addMethod("numberorempty", function(value, element) {
		return this.optional(element) || /^[0-9\'']+$/i.test(value);
	}, "Ingrese solo numeros o vacio");

	$.validator.addMethod("equals", function(value, element, string) {
		return $.inArray(value, string) !== -1;
	}, $.validator.format("Please enter '{0}'"));

	$.fn.clearValidation = function() {
		var v = $(this).validate();
		$('[name]', this).each(function() {
			v.successList.push(this);
			v.showErrors();
		});
		v.resetForm();
		v.reset();
	};

	$.validator.setDefaults({
		focusCleanup : true,
		highlight : function(element) {
			$(element).parents(".group, .form-group").first().addClass('has-error');
		},
		unhighlight : function(element) {
			$(element).parents(".group, .form-group").first().removeClass('has-error');
		},
		errorElement : "span",
		errorClass : "help-block",
		errorPlacement : function(error, element) {
			if (element.parent(".input-group").length) {
				error.insertAfter(element.parent());
			} else if (element.parent().find("span.select2").length > 0) {
				error.appendTo(element.parent());
			} else {
				error.appendTo(element.parents("div")[0]);
			}
		}
	});
});