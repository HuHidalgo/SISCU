package com.cenpro.siscu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.carga.Carga;
import com.cenpro.siscu.model.carga.ErrorCarga;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;

public interface ICargaPeriodicaService
{
	//CARGA 
	public Carga cargarAlumnos(MultipartFile archivoAlumnos, String estamento);
    
    public Carga cargarDocentes(MultipartFile archivoDocentes, String estamento);
    
    public Carga cargarNoDocentes(MultipartFile archivoNoDocentes, String estamento);
    
    //REGISTRO A LA BD	
    public void registrarAlumnos(List<Afiliacion> alumnos);

    public void registrarNoDocentes(List<Afiliacion> noDocentes);

    public void registrarDocentes(List<Afiliacion> docentes);
    
}