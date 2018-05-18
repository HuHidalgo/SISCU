package com.cenpro.siscu.controller.admision;

import static com.cenpro.siscu.utilitario.ConstantesGenerales.PAGINA_CONSULTA_ADMISION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cenpro.siscu.aspecto.anotacion.Audit;
import com.cenpro.siscu.aspecto.enumeracion.Accion;
import com.cenpro.siscu.aspecto.enumeracion.Comentario;
import com.cenpro.siscu.aspecto.enumeracion.Tipo;
import com.cenpro.siscu.controller.excepcion.anotacion.Vista;
import com.cenpro.siscu.service.IFacultadService;
import com.cenpro.siscu.service.IMultiTabDetService;
import com.cenpro.siscu.utilitario.MultiTablaUtil;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/admision")
public @Controller class AdmisionController
{
	private @Autowired IFacultadService facultadService;
	private @Autowired IMultiTabDetService multiTabDetService;
	
    @GetMapping("/afiliacion")
    public String irPaginaConsultaAdmision(ModelMap model)
    {
        ///model.addAttribute(P_CONSULTA_ADMISION, admision);
    	model.addAttribute("estamentos", multiTabDetService.buscarPorIdTabla(MultiTablaUtil.TABLA_ESTAMENTOS));
        return PAGINA_CONSULTA_ADMISION;
    }
}