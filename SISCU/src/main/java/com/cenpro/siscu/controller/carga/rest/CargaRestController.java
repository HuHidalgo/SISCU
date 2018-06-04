package com.cenpro.siscu.controller.carga.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.service.ICargaService;

@RequestMapping("/carga/inicial")
public @RestController class CargaRestController
{
    private @Autowired ICargaService cargaService;

    @PostMapping(value = "/uploadfile/{estamento}", params = "accion=cargar")
    public void cargasIniciales(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("R"))
        {
            cargaService.cargarAlumnoRegular(file, estamento);
        } else
        {
            cargaService.cargarAlumnoIngresante(file, estamento);
        }
    }
}