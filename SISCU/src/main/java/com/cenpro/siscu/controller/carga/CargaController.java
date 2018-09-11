package com.cenpro.siscu.controller.carga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.cenpro.siscu.controller.excepcion.anotacion.Vista;
import com.cenpro.siscu.service.IFacultadService;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.MultiTablaUtil;

@Vista
public @Controller class CargaController
{
	private @Autowired IFacultadService facultadService;
    private @Autowired IMultiTabDetService multiTabDetService;

    @GetMapping("/carga/inicial")
    public String irPaginaCargaInicial(ModelMap model)
    {
    	model.addAttribute("estamentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
        return "seguras/carga/inicial/cargarDatos";
    }
    
    @GetMapping("/carga/registrarAtendidos")
    public String irPaginaRegistrarAtendidos(ModelMap model)
    {
    	model.addAttribute("estamentos2", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
    	model.addAttribute("estamentos3", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
    	model.addAttribute("tiposDocumento", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
    	model.addAttribute("tiposDocumento2", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_DOCUMENTO));
    	model.addAttribute("sexos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_SEXO));
    	model.addAttribute("estadosCiviles", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTADO_CIVIL));
    	model.addAttribute("grados", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_GRADO_INSTRUCCION));
    	model.addAttribute("departamentosNac", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_DEPARTAMENTOS));
    	model.addAttribute("facultades", facultadService.buscarTodos());
        return "seguras/carga/inicial/registrarAtendidos";
    }
    
    @GetMapping("/carga/periodica")
    public String irPaginaCargaPeriodica(ModelMap model)
    {
        //model.addAttribute("tiposAlumno", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_ALUMNO));
    	model.addAttribute("estamentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
        return "seguras/carga/periodica";
    }
    
    @GetMapping("/carga/actualizacion")
    public String irPaginaCargaActualizacion(ModelMap model)
    {
        //model.addAttribute("tiposAlumno", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_ALUMNO));
    	model.addAttribute("estamentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
        return "seguras/carga/actualizacion";
    }
}