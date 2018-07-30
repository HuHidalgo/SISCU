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

import com.cenpro.siscu.aspecto.anotacion.Audit;
import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
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
        return afiliacionService.buscarPorNroDocumento(criterioBusquedaEstamento);
    }
    
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping
    public ResponseEntity<?> registrarAfiliacion(@RequestBody Afiliacion afiliacion)
    {
    	List<Afiliacion> idAfiliacion = afiliacionService.registrarAfiliacion(afiliacion);
        return ResponseEntity.ok(afiliacionService.buscarPorId(idAfiliacion.get(0).getNumeroDocumento(), idAfiliacion.get(0).getIdTipoDocumento()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping
    public ResponseEntity<?> actualizarAfiliacion(@RequestBody Afiliacion afiliacion)
    {
    	afiliacionService.actualizarAfiliacion(afiliacion);
        return ResponseEntity.ok(afiliacionService.buscarPorId(afiliacion.getNumeroDocumento(), afiliacion.getIdTipoDocumento()));
    }
    
}