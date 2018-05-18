package com.cenpro.siscu.controller.movimiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cenpro.siscu.controller.excepcion.anotacion.Vista;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.MultiTablaUtil;

@Vista
@RequestMapping("/examenmedico")
public @Controller class ResultadoExamenMedicoController
{
    private @Autowired IMultiTabDetService multiTabDetService;

    @GetMapping(value = "/{examen:laboratorio}/resultado")
    public String irPaginaRegistroResultadoExamenMedicoLaboratorio(@PathVariable String examen,
            ModelMap model)
    {
        model.addAttribute("examen", examen);
        model.addAttribute("indicadoresRPR",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_RPR));
        model.addAttribute("indicadoresHemograma",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_HEMOGRAMA));
        model.addAttribute("diluciones",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_DILUCION));
        model.addAttribute("gruposSanguineo",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_GRUPO_SANGUINEO));
        model.addAttribute("factoresRh",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_FACTOR_RH));
        return "seguras/resultado/registroResultado";
    }

    @GetMapping(value = "/radiologico/tomaRadiologica")
    public String irPaginaTomaRadiologico(ModelMap model)
    {
        model.addAttribute("resultadosRadiologico",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_RESULTADO_RADIOLOGICO));
        return "seguras/tomaRadiologica";
    }

    @GetMapping(value = "/{examen:radiologico}/resultado")
    public String irPaginaRegistroResultadoExamenMedicoRadiologico(@PathVariable String examen,
            ModelMap model)
    {
        model.addAttribute("examen", examen);
        model.addAttribute("resultadosRadiologico",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_RESULTADO_RADIOLOGICO));
        return "seguras/resultado/registroResultado";
    }
}