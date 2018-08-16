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
        return this.afiliacionService.buscarPorNroDocumento(criterioBusquedaEstamento);
    }
    
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping
    public ResponseEntity<?> registrarAfiliacion(@RequestBody Afiliacion afiliacion)
    {
    	CriterioBusquedaEstamento criterioBusqueda = new CriterioBusquedaEstamento();
    	List<Afiliacion> idAfiliacion = afiliacionService.registrarAfiliacion(afiliacion);
    	criterioBusqueda.setIdEstamento(afiliacion.getIdEstamento());
    	criterioBusqueda.setNroDocumento(idAfiliacion.get(0).getNumeroDocumento());
    	criterioBusqueda.setTipoDocumento(idAfiliacion.get(0).getIdTipoDocumento());
        return ResponseEntity.ok(this.afiliacionService.buscarPorNroDocumento(criterioBusqueda));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping
    public ResponseEntity<?> actualizarAfiliacion(@RequestBody Afiliacion afiliacion)
    {
    	CriterioBusquedaEstamento criterioBusqueda = new CriterioBusquedaEstamento();
    	afiliacionService.actualizarAfiliacion(afiliacion);
    	criterioBusqueda.setIdEstamento(afiliacion.getIdEstamento());
    	criterioBusqueda.setNroDocumento(afiliacion.getNumeroDocumento());
    	criterioBusqueda.setTipoDocumento(afiliacion.getIdTipoDocumento());
        return ResponseEntity.ok(this.afiliacionService.buscarPorNroDocumento(criterioBusqueda));
    }
    
}