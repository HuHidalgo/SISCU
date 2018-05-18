package com.cenpro.siscu.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICargaService
{
    public void cargarAlumnoIngresante(MultipartFile archivoAlumnos, String tipoAlumno);
    
    public void cargarAlumnoRegular(MultipartFile archivoAlumnos, String tipoAlumno);
}