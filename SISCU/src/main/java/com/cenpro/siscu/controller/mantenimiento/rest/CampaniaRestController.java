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

import com.cenpro.siscu.aspecto.anotacion.Audit;
import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
import com.cenpro.siscu.aspecto.enumeracion.Dato;
import com.cenpro.siscu.aspecto.enumeracion.Tipo;
import com.cenpro.siscu.model.mantenimiento.Campania;
import com.cenpro.siscu.service.ICampaniaService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;

@Audit(tipo = Tipo.Campania, datos = Dato.Campania)
@RequestMapping("/mantenimiento/campania")
public @RestController class CampaniaRestController
{
    private @Autowired ICampaniaService campaniaService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(params = "accion=buscarTodos")
    public List<Campania> buscarTodos()
    {
        return campaniaService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping
    public ResponseEntity<?> registrarCampania(@RequestBody Campania campania)
    {
        int idCampania = campaniaService.registrarCampania(campania);
        return ResponseEntity.ok(campaniaService.buscarPorId(idCampania));
    }
    
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping
    public ResponseEntity<?> actualizarCampania(@RequestBody Campania campania)
    {
        campaniaService.actualizarCampania(campania);
        return ResponseEntity.ok(campaniaService.buscarPorId(campania.getIdCampania()));
    }
    
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @DeleteMapping
    public ResponseEntity<?> eliminarCampania(@RequestBody Campania campania)
    {
        campaniaService.eliminarCampania(campania);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }
}