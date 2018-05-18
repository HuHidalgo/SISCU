package com.cenpro.siscu.controller.mantenimiento.rest;

import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.mantenimiento.Especialidad;
import com.cenpro.siscu.service.IEspecialidadService;
import com.cenpro.siscu.service.excepcion.BadRequestException;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.ValidatorUtil;
import com.cenpro.siscu.validacion.grupo.accion.IActualizacion;

@RequestMapping("/mantenimiento/especialidad")
public @RestController class EspecialidadRestController
{
    private @Autowired IEspecialidadService especialidadService;

    @GetMapping(params = "accion=buscarTodos")
    public List<Especialidad> buscarTodos()
    {
        return this.especialidadService.buscarTodos();
    }

    @PostMapping
    public int registrarEspecialidad(
            @Validated(Default.class) @RequestBody Especialidad especialidad, Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        return this.especialidadService.registrarEspecialidad(especialidad);
    }

    @PutMapping
    public String actualizarEspecialidad(@Validated({ IActualizacion.class,
            Default.class }) @RequestBody Especialidad especialidad, Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        this.especialidadService.actualizarEspecialidad(especialidad);
        return ConstantesGenerales.ACTUALIZACION_EXITOSA;
    }

    @DeleteMapping
    public String eliminarEspecialidad(
            @Validated(IActualizacion.class) @RequestBody Especialidad especialidad, Errors error)
    {
        if (error.hasErrors())
        {
            throw new BadRequestException(ValidatorUtil.obtenerMensajeValidacionError(error));
        }
        this.especialidadService.eliminarEspecialidad(especialidad);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }
}