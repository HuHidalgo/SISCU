package com.cenpro.siscu.controller.carga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.cenpro.siscu.controller.excepcion.anotacion.Vista;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.MultiTablaUtil;

@Vista
public @Controller class CargaController
{
    private @Autowired IMultiTabDetService multiTabDetService;

    @GetMapping("/carga/inicial")
    public String irPaginaCargaInicial(ModelMap model)
    {
        //model.addAttribute("tiposAlumno", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_ALUMNO));
    	model.addAttribute("estamentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
        return "seguras/carga/inicial";
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