package com.cenpro.siscu.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cenpro.siscu.model.mantenimiento.Escuela;
import com.cenpro.siscu.service.IEscuelaService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;

@RequestMapping("/mantenimiento/escuela")
public @RestController class EscuelaRestController
{
    private @Autowired IEscuelaService escuelaService;

    @GetMapping("/facultad/{codigoFacultad}")
    public List<Escuela> buscarPorCodigoFacultad(@PathVariable Integer codigoFacultad)
    {
        return escuelaService.buscarPorCodigoFacultad(codigoFacultad);
    }

    @GetMapping(params = "accion=buscarTodos")
    public List<Escuela> buscarTodos()
    {
        return escuelaService.buscarTodos();
    }

    @PostMapping
    public ResponseEntity<?> registrarEscuela(@RequestBody Escuela escuela)
    {
        escuelaService.registrarEscuela(escuela);
        return ResponseEntity.ok(escuelaService.buscarPorCodigoFacultadCodigoEscuela(
                escuela.getCodigoFacultad(), escuela.getCodigoEscuela()));
    }

    @PutMapping
    public ResponseEntity<?> actualizarEscuela(@RequestBody Escuela escuela)
    {
        escuelaService.actualizarEscuela(escuela);
        return ResponseEntity.ok(escuelaService.buscarPorCodigoFacultadCodigoEscuela(
                escuela.getCodigoFacultad(), escuela.getCodigoEscuela()));
    }

    @DeleteMapping
    public ResponseEntity<?> eliminarEscuela(@RequestBody Escuela escuela)
    {
        escuelaService.eliminarEscuela(escuela);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }
}