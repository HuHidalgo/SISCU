package com.cenpro.siscu.configuracion.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.template.IContext;

public class XdocDocxView extends AbstractXdocDocxView
{
    @Override
    protected void buildDocxDocument(Map<String, Object> model, Map<String, Object> parametros,
            IXDocReport ixDocReport, IContext contextReport, HttpServletRequest request,
            HttpServletResponse response)
    {
        throw new UnsupportedOperationException();
    }
}