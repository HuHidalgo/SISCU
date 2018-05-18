package com.cenpro.siscu.controller.procesomanual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cenpro.siscu.service.ICampaniaService;
import com.cenpro.siscu.service.IFacultadService;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.MultiTablaUtil;

@RequestMapping("/mantenimiento")
public @Controller class MantenimientoProcesoAutomaticoController
{
    private @Autowired ICampaniaService campaniaService;
    private @Autowired IFacultadService facultadService;
    private @Autowired IMultiTabDetService multiTabDetService;

    @GetMapping("/{mantenimiento:procesoAutomatico}")
    public String irPaginaMantenimientoProcesoAutomatico(@PathVariable String mantenimiento,
            ModelMap model)
    {
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("campanias", campaniaService.buscarTodos());
        model.addAttribute("destinosEnvio",
                multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_DESTINO_ENVIO));
        model.addAttribute("facultades", facultadService.buscarTodos());
        return "seguras/mantenimiento/mantenimiento";
    }
}