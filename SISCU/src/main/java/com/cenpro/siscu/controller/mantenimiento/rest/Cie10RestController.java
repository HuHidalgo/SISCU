package com.cenpro.siscu.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.mantenimiento.Cie10;
import com.cenpro.siscu.service.ICie10Service;

@RequestMapping("/mantenimiento/cie10")
public @RestController class Cie10RestController
{
    private @Autowired ICie10Service cie10Service;

    @GetMapping(params = "accion=buscarPorDescripcion")
    public List<Cie10> buscarPorDescripcion(@RequestParam String descripcion)
    {
        return this.cie10Service.buscarPorDescripcion(descripcion);
    }
}