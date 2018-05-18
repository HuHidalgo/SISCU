package com.cenpro.siscu.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.mantenimiento.Facultad;
import com.cenpro.siscu.service.IFacultadService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;

@RequestMapping("/mantenimiento/facultad")
public @RestController class FacultadRestController
{
    private @Autowired IFacultadService facultadService;

    @GetMapping(params = "accion=buscarTodos")
    public List<Facultad> buscarTodosT()
    {
        return facultadService.buscarTodosT();
    }

    @PostMapping
    public ResponseEntity<?> registrarFacultad(@RequestBody Facultad facultad)
    {
        facultadService.registrarFacultad(facultad);
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    @PutMapping
    public ResponseEntity<?> actualizarFacultad(@RequestBody Facultad facultad)
    {
        facultadService.actualizarFacultad(facultad);
        return ResponseEntity.ok(ConstantesGenerales.ACTUALIZACION_EXITOSA);
    }
    
    @DeleteMapping
    public ResponseEntity<?> eliminarFacultad(@RequestBody Facultad facultad)
    {
        facultadService.eliminarFacultad(facultad);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }
}