package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.criterio.CriterioBusquedaAuditoria;
import com.cenpro.siscu.model.parametro.Parametro;
import com.cenpro.siscu.model.seguridad.Auditoria;

public interface IAuditoriaMapper extends IMantenibleMapper<Auditoria>
{
    @Select(value = { "{call MANT_AUDITORIA ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idAccion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAuditoria, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.direccionIp, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.exito, jdbcType = BIT, mode = IN},"
            + "#{objeto.comentario, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nombreUsuario, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.fecha, jdbcType = DATE, mode = IN},"
            + "#{objeto.hora, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Auditoria> mantener(Parametro parametro);

    public List<Auditoria> buscarPistasAuditoriaPorCriterio(CriterioBusquedaAuditoria criterioBusqueda);
}