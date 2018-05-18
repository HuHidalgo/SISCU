package com.cenpro.siscu.configuracion.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.MimeTypeUtil;
import com.cenpro.siscu.utilitario.StringsUtils;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public abstract class AbstractXdocDocxView extends AbstractView
{
    private static final String NOMBRE_REPORTE = "Reporte";

    public AbstractXdocDocxView()
    {
        setContentType(MimeTypeUtil.DOCX);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        model.putIfAbsent(ConstantesGenerales.PARAM_NOMBRE_REPORTE, NOMBRE_REPORTE);
        String templateDocx = (String) model.get(ConstantesGenerales.PARAM_TEMPLATE);
        Map<String, Object> reporteParametros = (Map<String, Object>) model
                .get(ConstantesGenerales.PARAM_REPORTE_PARAMETERS);
        IXDocReport ixDocReport = XDocReportRegistry.getRegistry()
                .loadReport(
                        getClass().getClassLoader()
                                .getResourceAsStream(StringsUtils.concatenarCadena(
                                        ConstantesGenerales.RUTA_REPORTE_DOCX, templateDocx,
                                        MimeTypeUtil.EXTENSION_DOCX)),
                        TemplateEngineKind.Freemarker);
        IContext context = ixDocReport.createContext();
        reporteParametros.entrySet().stream()
                .forEach(map -> context.put(map.getKey(), map.getValue()));
        String nombreReporte = (String) model.get(ConstantesGenerales.PARAM_NOMBRE_REPORTE);
        response.setHeader("Content-Disposition", StringsUtils.concatenarCadena(
                "attachment;filename=", nombreReporte, MimeTypeUtil.EXTENSION_DOCX));
        response.setContentType(getContentType());
        renderReport(ixDocReport, context, response);
    }

    protected abstract void buildDocxDocument(Map<String, Object> model,
            Map<String, Object> parametros, IXDocReport ixDocReport, IContext contextReport,
            HttpServletRequest request, HttpServletResponse response);

    protected void renderReport(IXDocReport ixDocReport, IContext contextReport,
            HttpServletResponse response) throws XDocReportException, IOException
    {
        ixDocReport.process(contextReport, response.getOutputStream());
    }

    @Override
    protected boolean generatesDownloadContent()
    {
        return true;
    }
}