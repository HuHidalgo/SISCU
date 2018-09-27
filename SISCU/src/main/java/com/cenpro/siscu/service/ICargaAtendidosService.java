package com.cenpro.siscu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.carga.Carga;
import com.cenpro.siscu.model.carga.ErrorCarga;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;

public interface ICargaAtendidosService
{    
    //CARGA DE AFILIADOS
  	public Carga cargarAlumnosAfiliados(MultipartFile archivoAlumnos, String estamento);
      
    public Carga cargarDocentesAfiliados(MultipartFile archivoDocentes, String estamento);
      
    public Carga cargarNoDocentesAfiliados(MultipartFile archivoNoDocentes, String estamento);
      
    public Carga cargarParticularesAfiliados(MultipartFile archivoParticulares, String estamento);
    
    //REGISTRO A LA BD	
    public void registrarAlumnos(List<Afiliacion> alumnos);
    
    public void registrarParticulares(List<Afiliacion> particulares);

    public void registrarNoDocentes(List<Afiliacion> noDocentes);

    public void registrarDocentes(List<Afiliacion> docentes);
}