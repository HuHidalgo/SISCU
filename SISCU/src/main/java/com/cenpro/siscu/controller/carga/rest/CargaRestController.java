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

import com.cenpro.siscu.aspecto.anotacion.Audit;
import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.carga.Carga;
import com.cenpro.siscu.model.carga.ErrorCarga;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;
import com.cenpro.siscu.service.IAfiliacionService;
import com.cenpro.siscu.service.ICargaActualizarCodigoService;
import com.cenpro.siscu.service.ICargaAtendidosService;
import com.cenpro.siscu.service.ICargaInicialService;
import com.cenpro.siscu.service.ICargaPeriodicaService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;

@RequestMapping("/carga")
public @RestController class CargaRestController
{
    private @Autowired ICargaActualizarCodigoService cargaActualizarCodigoService;
    private @Autowired ICargaInicialService cargaInicialService;
    private @Autowired ICargaPeriodicaService cargaPeriodicaService;
    private @Autowired ICargaAtendidosService cargaAtendidosService;
    
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping
    public ResponseEntity<?> registrarAfiliado(@RequestBody Afiliacion afiliacion)
    {
    	int idAfiliado = cargaInicialService.registrarAtendidos(afiliacion);
        return ResponseEntity.ok(cargaInicialService.buscarPorId(idAfiliado));
    }

    @PostMapping(value = "/inicial/uploadfile/{estamento}", params = "accion=cargar")
    public Carga cargasIniciales(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("1"))
            return cargaInicialService.cargarAlumnos(file, estamento);
         else
        	 if (estamento.equals("2"))
                 return cargaInicialService.cargarDocentes(file, estamento);
             else
            	 if (estamento.equals("3"))
                     return cargaInicialService.cargarNoDocentes(file, estamento);
                 else
                	 return cargaInicialService.cargarParticulares(file, estamento);    	 	
        
    }	
    
    @PostMapping(value = "/periodica/uploadfile/{estamento}", params = "accion=cargar")
    public Carga cargasPeriodicas(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
        if (estamento.equals("1"))
        	return cargaPeriodicaService.cargarAlumnos(file, estamento);
         else
        	 if (estamento.equals("2"))
        		 return cargaPeriodicaService.cargarDocentes(file, estamento);
              else
            	 return cargaPeriodicaService.cargarNoDocentes(file, estamento);               	 	
    }	
    
    @PostMapping(value = "/actualizacion/      /{estamento}", params = "accion=cargar")
    public Carga cargasActualizaciones(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
    	
        return cargaActualizarCodigoService.cargarAlumnos(file, estamento);              	 	
    }	
    
    @PostMapping(value = "/registrarAtendidos/uploadfile/{estamento}", params = "accion=cargar")
    public Carga cargasAfiliados(@RequestParam("uploadfile") MultipartFile file, @PathVariable String estamento){
    	if (estamento.equals("1"))
    		return cargaAtendidosService.cargarAlumnosAfiliados(file, estamento);
         else
        	 if (estamento.equals("2"))
        		 return cargaAtendidosService.cargarDocentesAfiliados(file, estamento);
              else
            	  return cargaAtendidosService.cargarNoDocentesAfiliados(file, estamento);
  
    }
}