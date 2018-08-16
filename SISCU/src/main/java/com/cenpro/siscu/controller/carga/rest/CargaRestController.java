package com.cenpro.siscu.controller.carga.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.carga.Cliente;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;
import com.cenpro.siscu.service.IAfiliacionService;
import com.cenpro.siscu.service.ICargaService;

import com.cenpro.siscu.utilitario.ConstantesGenerales;

@RequestMapping("/carga")
public @RestController class CargaRestController
{
    private @Autowired ICargaService cargaService;
    private @Autowired IAfiliacionService afiliacionService;

    @PostMapping(value = "/inicial/uploadfile/{estamento}", params = "accion=cargar")
    public ResponseEntity<?> cargasIniciales(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("1"))
            cargaService.cargarAlumnos(file, estamento);
         else
        	 if (estamento.equals("2"))
                 cargaService.cargarDocentes(file, estamento);
              else
            	  if (estamento.equals("3"))
            		  cargaService.cargarNoDocentes(file, estamento);
                  else
                	  cargaService.cargarParticulares(file, estamento);                	 	
        return ResponseEntity.ok(ConstantesGenerales.CARGA_EXITOSA);
    }	
    
    @PostMapping(value = "/periodica/uploadfile/{estamento}", params = "accion=cargar")
    public void cargasPeriodicas(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("1"))
            cargaService.cargarAlumnos(file, estamento);
         else
        	 if (estamento.equals("2"))
                 cargaService.cargarDocentes(file, estamento);
              else
            	  if (estamento.equals("3"))
            		  cargaService.cargarNoDocentes(file, estamento);
                  else
                	  cargaService.cargarParticulares(file, estamento);                	 	
    }	
    
    @PostMapping(value = "/actualizacion/uploadfile/{estamento}", params = "accion=cargar")
    public void cargasActualizaciones(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("1"))
            cargaService.cargarAlumnos(file, estamento);
         else
        	 if (estamento.equals("2"))
                 cargaService.cargarDocentes(file, estamento);
              else
            	  if (estamento.equals("3"))
            		  cargaService.cargarNoDocentes(file, estamento);
                  else
                	  cargaService.cargarParticulares(file, estamento);                	 	
    }	
    
    @GetMapping(value = "/consulta", params = "accion=buscarPorEstamento")
    public List<Afiliacion> consultarCliente(CriterioBusquedaEstamento criterioBusquedaEstamento){
    	return cargaService.consultarPorNroDocumento(criterioBusquedaEstamento);
    }
}