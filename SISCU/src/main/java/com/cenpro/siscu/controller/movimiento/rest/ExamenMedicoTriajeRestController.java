package com.cenpro.siscu.controller.movimiento.rest;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.movimiento.ExamenMedicoTriaje;
import com.cenpro.siscu.service.IExamenMedicoTriajeService;
import com.cenpro.siscu.service.excepcion.BadRequestException;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.grupo.accion.IRegistro;

@RequestMapping("/examenmedico/triaje")
public @RestController class ExamenMedicoTriajeRestController
{
    private @Autowired IExamenMedicoTriajeService examenMedicoTriajeService;

    @PostMapping
    public String registrarExamenMedicoTriaje(
            @Validated({ IRegistro.class,
                    Default.class }) @RequestBody ExamenMedicoTriaje examenMedicoTriaje,
            Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        this.examenMedicoTriajeService.registrarExamenMedicoTriaje(examenMedicoTriaje);
        return ConstantesGenerales.REGISTRO_EXITOSO;
    }
}