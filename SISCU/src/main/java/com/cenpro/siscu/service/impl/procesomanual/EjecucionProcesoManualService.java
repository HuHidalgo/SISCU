package com.cenpro.siscu.service.impl.procesomanual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.cenpro.siscu.mapper.IEjecucionProcesoManualMapper;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEnvioCorreo;
import com.cenpro.siscu.model.mantenimiento.Facultad;
import com.cenpro.siscu.model.procesoautomatico.Correo;
import com.cenpro.siscu.model.procesoautomatico.EnvioLote;
import com.cenpro.siscu.model.procesoautomatico.ProcesoAutomatico;
import com.cenpro.siscu.service.IEjecucionProcesoManualService;
import com.cenpro.siscu.service.IEnvioMailAsyncService;
import com.cenpro.siscu.service.IFacultadService;
import com.cenpro.siscu.service.IParametroGeneralService;
import com.cenpro.siscu.service.IProcesoAutomaticoService;
import com.cenpro.siscu.service.IYargReportXlsxService;
import com.cenpro.siscu.service.excepcion.ValorEncontradoException;
import com.cenpro.siscu.service.excepcion.ValorNoEncontradoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.MimeTypeUtil;
import com.cenpro.siscu.utilitario.ReporteUtilYarg;

@Service
public class EjecucionProcesoManualService implements IEjecucionProcesoManualService
{
    private @Autowired IEjecucionProcesoManualMapper ejecucionProcesoManualMapper;

    private @Autowired IFacultadService facultadService;
    private @Autowired IEnvioMailAsyncService envioMailAsyncService;
    private @Autowired IYargReportXlsxService yargReportXlsxService;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    public void ejecutarProcesoManual(CriterioBusquedaEnvioCorreo criterioBusquedaEnvioCorreo)
            throws IOException, MessagingException, InterruptedException, ExecutionException
    {
        List<Correo> correosPorEnviar = new ArrayList<>();
        List<ProcesoAutomatico> procesosAutomatico = procesoAutomaticoService
                .buscarPorIdProcesoAutomatico(criterioBusquedaEnvioCorreo.getIdProcesoAutomatico());
        if (procesosAutomatico.isEmpty())
        {
            throw new ValorNoEncontradoException(
                    ConstantesExcepciones.PROC_AUTOMATICO_NO_ENCONTRADO);
        }
        ProcesoAutomatico procesoAutomatico = procesosAutomatico.get(0);
        criterioBusquedaEnvioCorreo.setIdCampania(procesoAutomatico.getIdCampaniaEnvio());
        criterioBusquedaEnvioCorreo.setIdDestinoEnvio(procesoAutomatico.getIdDestinoEnvio());
        criterioBusquedaEnvioCorreo
                .setCodigoFacultadesEnvio(procesoAutomatico.getCodigoFacultadesEnvio());
        criterioBusquedaEnvioCorreo.setFechaInicio(procesoAutomatico.getFechaInicio());
        criterioBusquedaEnvioCorreo.setFechaFin(procesoAutomatico.getFechaFin());

        switch (procesoAutomatico.getIdDestinoEnvio())
        {
        case ConstantesGenerales.SUM:
            correosPorEnviar = prepararReporteSUM(procesoAutomatico, criterioBusquedaEnvioCorreo,
                    new ModelMap(), new HashMap<String, Object>());
            break;
        case ConstantesGenerales.FAC:
            correosPorEnviar = prepararReporteFacultad(procesoAutomatico,
                    criterioBusquedaEnvioCorreo, new ModelMap(), new HashMap<String, Object>());
            break;
        case ConstantesGenerales.ASREG:
            correosPorEnviar = prepararAsistentasRegulares(procesoAutomatico,
                    criterioBusquedaEnvioCorreo, new ModelMap(), new HashMap<String, Object>());
            break;
        default:
            throw new ValorEncontradoException(ConstantesExcepciones.DESTINO_ENVIO_DESCONOCIDO);
        }
        if (correosPorEnviar.isEmpty())
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.NINGUN_CORREO_POR_ENVIAR);
        }
        envioMailAsyncService.enviarCorreo(correosPorEnviar);
    }

    public List<Correo> prepararReporteSUM(ProcesoAutomatico procesoAutomatico,
            CriterioBusquedaEnvioCorreo criterioBusquedaEnvioCorreo, ModelMap model,
            Map<String, Object> params) throws IOException
    {
        List<Correo> correoSUM = new ArrayList<>();
        String correoEmisor = parametroGeneralService.buscarCorreoClinica();
        String nombreDocumento = String.format("Reporte Alumno atendidos del %tF A %tF",
                procesoAutomatico.getFechaInicio(), procesoAutomatico.getFechaFin());

        List<EnvioLote> lote = ejecucionProcesoManualMapper
                .buscarAlumnosParaEnvioCorreo(criterioBusquedaEnvioCorreo);

        if (!lote.isEmpty())
        {
            params.put("param1", lote);
            model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
            model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
            model.addAttribute("rb_datos", ReporteUtilYarg.buildReportBand("Datos", "Datos",
                    "parameter=param1 $", "json"));
            model.addAttribute(ConstantesGenerales.PARAM_TEMPLATE,
                    procesoAutomatico.getNombrePlantilla());
            model.addAttribute(ConstantesGenerales.PARAM_NOMBRE_REPORTE, nombreDocumento);
            model.addAttribute(ConstantesGenerales.PARAM_REPORTE_PARAMETERS, params);

            Correo correo = Correo.builder().contentType(MimeTypeUtil.XLSX)
                    .correoEmisor(correoEmisor)
                    .correoDestino(parametroGeneralService.buscarCorreoSUM())
                    .codigoDependencia(0)
                    .idCampania(criterioBusquedaEnvioCorreo.getIdCampania())
                    .idProcesoAutomatico(procesoAutomatico.getIdProcesoAutomatico())
                    .nombreDocumento(nombreDocumento)
                    .documento(yargReportXlsxService.renderMergedOutputModel(model))
                    .textoEncabezado(procesoAutomatico.getTextoEncabezado())
                    .textoCuerpo(procesoAutomatico.getTextoCuerpo()).build();
            correoSUM.add(correo);
        }
        return correoSUM;
    }

    public List<Correo> prepararReporteFacultad(ProcesoAutomatico procesoAutomatico,
            CriterioBusquedaEnvioCorreo criterioBusquedaEnvioCorreo, ModelMap model,
            Map<String, Object> params) throws IOException
    {
        String correoEmisor = parametroGeneralService.buscarCorreoClinica();
        String nombreDocumento = String.format("Reporte Alumno atendidos del %tF A %tF",
                procesoAutomatico.getFechaInicio(), procesoAutomatico.getFechaFin());

        List<Facultad> facultades = facultadService.buscarTodosT();
        List<Correo> correosFacultad = new ArrayList<>();

        for (Facultad facultad : facultades)
        {
            criterioBusquedaEnvioCorreo.setCodigoFacultad(facultad.getCodigoFacultad());
            List<EnvioLote> lotes = ejecucionProcesoManualMapper
                    .buscarAlumnosParaEnvioCorreo(criterioBusquedaEnvioCorreo);
            if (!lotes.isEmpty())
            {
                params.put("param1", lotes);
                params.put("param2", criterioBusquedaEnvioCorreo);
                model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
                model.addAttribute("rb_criteriosBusqueda", ReporteUtilYarg.buildReportBand(
                        "CriteriosBusqueda", "CriteriosBusqueda", "parameter=param2 $", "json"));
                model.addAttribute("rb_total", ReporteUtilYarg.buildReportBand("Total"));
                model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
                model.addAttribute("rb_datos", ReporteUtilYarg.buildReportBand("Datos", "Datos",
                        "parameter=param1 $", "json"));
                model.addAttribute(ConstantesGenerales.PARAM_TEMPLATE,
                        procesoAutomatico.getNombrePlantilla());
                model.addAttribute(ConstantesGenerales.PARAM_NOMBRE_REPORTE, nombreDocumento);
                model.addAttribute(ConstantesGenerales.PARAM_REPORTE_PARAMETERS, params);

                Correo correo = Correo.builder().correoEmisor(correoEmisor)
                        .correoDestino(facultad.getCorreoFacultad())
                        .correoDestinoConCopia(facultad.getCorreoAsistenta())
                        .codigoDependencia(facultad.getCodigoFacultad())
                        .idCampania(criterioBusquedaEnvioCorreo.getIdCampania())
                        .idProcesoAutomatico(procesoAutomatico.getIdProcesoAutomatico())
                        .nombreDocumento(nombreDocumento)
                        .documento(yargReportXlsxService.renderMergedOutputModel(model))
                        .contentType(MimeTypeUtil.XLSX)
                        .textoEncabezado(procesoAutomatico.getTextoEncabezado())
                        .textoCuerpo(procesoAutomatico.getTextoCuerpo()).build();
                correosFacultad.add(correo);
            }
        }
        return correosFacultad;
    }

    public List<Correo> prepararAsistentasRegulares(ProcesoAutomatico procesoAutomatico,
            CriterioBusquedaEnvioCorreo criterioBusquedaEnvioCorreo, ModelMap model,
            Map<String, Object> params) throws IOException
    {
        String correoEmisor = parametroGeneralService.buscarCorreoClinica();
        String nombreDocumento = String.format("Reporte Alumno atendidos del %tF A %tF",
                procesoAutomatico.getFechaInicio(), procesoAutomatico.getFechaFin());

        List<Facultad> facultades = facultadService.buscarTodosT();
        List<Correo> correosFacultad = new ArrayList<>();

        for (Facultad facultad : facultades)
        {
            criterioBusquedaEnvioCorreo.setCodigoFacultad(facultad.getCodigoFacultad());
            List<EnvioLote> lotes = ejecucionProcesoManualMapper
                    .buscarAlumnosParaEnvioCorreo(criterioBusquedaEnvioCorreo);
            if (!lotes.isEmpty())
            {
                params.put("param1", lotes);
                params.put("param2", criterioBusquedaEnvioCorreo);
                model.addAttribute("rb_titulo", ReporteUtilYarg.buildReportBand("Titulo"));
                model.addAttribute("rb_criteriosBusqueda", ReporteUtilYarg.buildReportBand(
                        "CriteriosBusqueda", "CriteriosBusqueda", "parameter=param2 $", "json"));
                model.addAttribute("rb_total", ReporteUtilYarg.buildReportBand("Total"));
                model.addAttribute("rb_encabezado", ReporteUtilYarg.buildReportBand("Encabezado"));
                model.addAttribute("rb_datos", ReporteUtilYarg.buildReportBand("Datos", "Datos",
                        "parameter=param1 $", "json"));
                model.addAttribute(ConstantesGenerales.PARAM_TEMPLATE,
                        procesoAutomatico.getNombrePlantilla());
                model.addAttribute(ConstantesGenerales.PARAM_NOMBRE_REPORTE, nombreDocumento);
                model.addAttribute(ConstantesGenerales.PARAM_REPORTE_PARAMETERS, params);

                Correo correo = Correo.builder().correoEmisor(correoEmisor)
                        .correoDestino(facultad.getCorreoAsistenta())
                        .correoDestinoConCopia(facultad.getCorreoFacultad())
                        .codigoDependencia(facultad.getCodigoFacultad())
                        .idCampania(criterioBusquedaEnvioCorreo.getIdCampania())
                        .idProcesoAutomatico(procesoAutomatico.getIdProcesoAutomatico())
                        .nombreDocumento(nombreDocumento)
                        .documento(yargReportXlsxService.renderMergedOutputModel(model))
                        .contentType(MimeTypeUtil.XLSX)
                        .textoEncabezado(procesoAutomatico.getTextoEncabezado())
                        .textoCuerpo(procesoAutomatico.getTextoCuerpo()).build();
                correosFacultad.add(correo);
            }
        }
        return correosFacultad;
    }
}