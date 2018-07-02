package com.cenpro.siscu.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICargaService
{
    public void cargarAlumnos(MultipartFile archivoAlumnos, String estamento);
    
    public void cargarDocentes(MultipartFile archivoDocentes, String estamento);
    
    public void cargarNoDocentes(MultipartFile archivoNoDocentes, String estamento);
    
    public void cargarParticulares(MultipartFile archivoParticulares, String estamento);
}