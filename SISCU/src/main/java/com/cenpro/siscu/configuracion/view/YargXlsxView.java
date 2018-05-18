package com.cenpro.siscu.configuracion.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;

import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.Conversor;
import com.cenpro.siscu.utilitario.MimeTypeUtil;
import com.cenpro.siscu.utilitario.StringsUtils;

public class YargXlsxView extends AbstractYargXlsxView
{
    @SuppressWarnings("unchecked")
    @Override
    protected void buildExcelDocument(Map<String, Object> model, ReportBuilder reportBuilder,
            Map<String, Object> parametros, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        String templateExcel = (String) model.get(ConstantesGenerales.PARAM_TEMPLATE);
        Map<String, Object> reporteParametros = (Map<String, Object>) model
                .get(ConstantesGenerales.PARAM_REPORTE_PARAMETERS);
        Conversor.toMapJSON(reporteParametros, parametros);
        String rutaTemplateExcel = getClass().getClassLoader()
                .getResource(StringsUtils.concatenarCadena(ConstantesGenerales.RUTA_REPORTE_XLSX,
                        templateExcel, MimeTypeUtil.EXTENSION_XLSX))
                .getPath();
        ReportTemplateBuilder reportTemplateBuilder = new ReportTemplateBuilder()
                .documentPath(rutaTemplateExcel)
                .documentName(
                        StringsUtils.concatenarCadena(templateExcel, MimeTypeUtil.EXTENSION_XLSX))
                .outputType(ReportOutputType.xlsx).readFileFromPath();
        reportBuilder.template(reportTemplateBuilder.build());
        model.entrySet().stream()
                .filter(map -> map.getKey().startsWith(ConstantesGenerales.PREFIX_REPORT_BAND)
                        && map.getValue() instanceof ReportBand)
                .forEach(map -> reportBuilder.band((ReportBand) map.getValue()));
    }
}