package com.cenpro.siscu.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cenpro.siscu.controller.excepcion.anotacion.Vista;

@Vista
public @Controller class HomeController
{
    @GetMapping(value = "/irPaginaInicio")
    public String irPageInicio(Principal principal, HttpSession session)
    {
        cargarInformacionUsuario(principal.getName(), session);
        return "redirect:/inicio";
    }

    public void cargarInformacionUsuario(String username, HttpSession session)
    {
        session.setAttribute("nombreUsuario", username);
    }

    @GetMapping("/inicio")
    public String irPaginaInicio(Model model, Principal principal)
    {
        model.addAttribute("usuario", principal.getName());
        return "seguras/inicio";
    }
}