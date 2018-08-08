package com.cenpro.siscu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.carga.Cliente;

public interface ICargaService
{
	public void cargarAlumnos(MultipartFile archivoAlumnos, String estamento);
    
    public void cargarDocentes(MultipartFile archivoDocentes, String estamento);
    
    public void cargarNoDocentes(MultipartFile archivoNoDocentes, String estamento);
    
    public void cargarParticulares(MultipartFile archivoParticulares, String estamento);
	
    public void registrarAlumnos(List<Cliente> alumnos);
}