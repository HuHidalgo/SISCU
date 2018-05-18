package com.cenpro.siscu.service.impl.yarg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;

import com.cenpro.siscu.service.IYargReportXlsxService;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.Conversor;
import com.cenpro.siscu.utilitario.MimeTypeUtil;
import com.cenpro.siscu.utilitario.StringsUtils;

@Service
public class YargReportXlsxService implements IYargReportXlsxService
{
    private static final String NOMBRE_REPORTE = "Reporte";

    @Override
    public byte[] renderMergedOutputModel(Map<String, Object> model) throws IOException
    {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        model.putIfAbsent(ConstantesGenerales.PARAM_NOMBRE_REPORTE, NOMBRE_REPORTE);
        Map<String, Object> parametros = new HashMap<>();
        ReportBuilder reportBuilder = createReportBuilder();
        buildExcelDocument(model, reportBuilder, parametros);
        Report report = reportBuilder.build();
        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        reporting.setLoaderFactory(
                new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));
        RunParams runParams = new RunParams(report).params(parametros);
        renderReport(reporting, runParams, output);
        return output.toByteArray();
    }

    protected ReportBuilder createReportBuilder()
    {
        return new ReportBuilder();
    }

    @SuppressWarnings("unchecked")
    protected void buildExcelDocument(Map<String, Object> model, ReportBuilder reportBuilder,
            Map<String, Object> parametros) throws IOException
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

    protected void renderReport(Reporting reporting, RunParams runParams,
            ByteArrayOutputStream output)
    {
        reporting.runReport(runParams, output);
    }
}