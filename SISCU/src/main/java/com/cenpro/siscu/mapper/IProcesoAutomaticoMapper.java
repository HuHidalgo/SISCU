package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.parametro.Parametro;
import com.cenpro.siscu.model.procesoautomatico.ProcesoAutomatico;

public interface IProcesoAutomaticoMapper extends IMantenibleMapper<ProcesoAutomatico>
{
	@Select(value = { "{call MANT_PROCESO_AUTOMATICO ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idProcesoAutomatico, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.ordenEjecucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.horaProgramada, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.horaReprogramada, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nombrePlantilla, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.activo, jdbcType = BIT, mode = IN},"
            + "#{objeto.idCampaniaEnvio, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoFacultadesEnvio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idDestinoEnvio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.diario, jdbcType = BIT, mode = IN},"
            + "#{objeto.secuencia, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.fechaInicio, jdbcType = DATE, mode = IN},"
            + "#{objeto.fechaFin, jdbcType = DATE, mode = IN},"
            + "#{objeto.textoEncabezado, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.textoCuerpo, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ProcesoAutomatico> mantener(Parametro parametro);
}