package com.cenpro.siscu.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.model.carga.Cliente;

public interface ICargaService
{
	public List<Cliente> cargarAlumnos(MultipartFile archivoAlumnos, String estamento);
    
    public List<Cliente> cargarDocentes(MultipartFile archivoDocentes, String estamento);
    
    public List<Cliente> cargarNoDocentes(MultipartFile archivoNoDocentes, String estamento);
    
    public List<Cliente> cargarParticulares(MultipartFile archivoParticulares, String estamento);
	
    public void registrarAlumnos(List<Cliente> alumnos);
}