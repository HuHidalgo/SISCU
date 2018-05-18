package com.cenpro.siscu.controller.reporte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cenpro.siscu.controller.excepcion.anotacion.Vista;
import com.cenpro.siscu.service.ICampaniaService;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.MultiTablaUtil;

@Vista
@RequestMapping("/reporte")
public @Controller class ReporteAtencionController
{
    private @Autowired ICampaniaService campaniaService;
    private @Autowired IMultiTabDetService multiTabDetService;

    @GetMapping("/atencion/diaria")
    public String irPaginaReporteAtencionDiaria(ModelMap model)
    {
        model.addAttribute("campanias", campaniaService.buscarTodos());
        model.addAttribute("tiposExamenMedico",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_TIPO_EXAMEN_MEDICO));
        return "seguras/reporte/reporteAtencionDiaria";
    }
}