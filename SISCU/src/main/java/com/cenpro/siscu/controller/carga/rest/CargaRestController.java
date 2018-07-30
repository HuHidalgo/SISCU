package com.cenpro.siscu.controller.carga.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.carga.Cliente;
import com.cenpro.siscu.service.ICargaService;

@RequestMapping("/carga")
public @RestController class CargaRestController
{
    private @Autowired ICargaService cargaService;

    @PostMapping(value = "/inicial/uploadfile/{estamento}", params = "accion=cargar")
    public List<Cliente> cargasIniciales(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("1"))
            return cargaService.cargarAlumnos(file, estamento);
         else
        	 if (estamento.equals("2"))
                 return cargaService.cargarDocentes(file, estamento);
              else
            	  if (estamento.equals("3"))
            		  return cargaService.cargarNoDocentes(file, estamento);
                  else
                	  return cargaService.cargarParticulares(file, estamento);                	 	
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
}