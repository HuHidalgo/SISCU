package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteAtencionDiaria;
import com.cenpro.siscu.model.reporte.ReporteAtencionDiaria;

public interface IReporteAtencionDiariaMapper
{
    @Select(value = { "{call REPORT_ATENCION_DIARIA ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{fechaInicio, jdbcType = DATE, mode = IN},"
            + "#{fechaFin, jdbcType = DATE, mode = IN},"
            + "#{idCampania, jdbcType = INTEGER, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ReporteAtencionDiaria> buscarAtencionesDiarias(
            CriterioBusquedaReporteAtencionDiaria criterioBusquedaReporteAtencionDiaria);
}