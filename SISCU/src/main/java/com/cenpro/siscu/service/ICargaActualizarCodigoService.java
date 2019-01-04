package com.cenpro.siscu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.carga.Carga;
import com.cenpro.siscu.model.carga.ErrorCarga;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;

public interface ICargaActualizarCodigoService
{    
    //CARGA
  	public Carga cargarAlumnos(MultipartFile archivoAlumnos, String estamento);
    
    //REGISTRO A LA BD	
    public void registrarAlumnos(List<Afiliacion> alumnos);
    
}