package com.cenpro.siscu.controller.movimiento.rest;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.movimiento.ExamenMedicoMedicinaGeneral;
import com.cenpro.siscu.service.IExamenMedicoMedicinaGeneralService;
import com.cenpro.siscu.service.excepcion.BadRequestException;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.grupo.accion.IRegistro;

@RequestMapping("/examenmedico/medicinaGeneral")
public @RestController class ExamenMedicoMedicinaGeneralRestController
{
    private @Autowired IExamenMedicoMedicinaGeneralService examenMedicoMedicinaGeneralService;

    @PostMapping
    public String registrarExamenMedicoMedicinaGeneral(@Validated({ IRegistro.class,
            Default.class }) @RequestBody ExamenMedicoMedicinaGeneral examenMedicoMedicinaGeneral,
            Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        this.examenMedicoMedicinaGeneralService
                .registrarExamenMedicoMedicinaGeneral(examenMedicoMedicinaGeneral);
        return ConstantesGenerales.REGISTRO_EXITOSO;
    }
}