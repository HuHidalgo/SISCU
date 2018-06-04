package com.cenpro.siscu.controller.admision.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;
import com.cenpro.siscu.service.IAfiliacionService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;

@RequestMapping("/admision/afiliacion")
public @RestController class AfiliacionRestController
{
    private @Autowired IAfiliacionService afiliacionService;

    @GetMapping(value = "/consulta", params = "accion=buscarPorEstamento")
    public List<Afiliacion> buscarPorEstamento(CriterioBusquedaEstamento criterioBusquedaEstamento){
    	System.out.println(criterioBusquedaEstamento);
    	//System.out.println(afiliacionService.buscarPorNroDocumento(criterioBusquedaEstamento));
        return afiliacionService.buscarPorNroDocumento(criterioBusquedaEstamento);
    }

    @PostMapping
    public ResponseEntity<?> registrarAfiliacion(@RequestBody Afiliacion afiliacion)
    {
    	afiliacionService.registrarAfiliacion(afiliacion);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @PutMapping
    public ResponseEntity<?> actualizarAfiliacion(@RequestBody Afiliacion afiliacion)
    {
    	afiliacionService.actualizarAfiliacion(afiliacion);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }
    
}