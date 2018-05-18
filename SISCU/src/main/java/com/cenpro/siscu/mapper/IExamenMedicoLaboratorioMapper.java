package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.movimiento.ExamenMedicoLaboratorio;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IExamenMedicoLaboratorioMapper extends IMantenibleMapper<ExamenMedicoLaboratorio>
{
    @Select(value = { "{call MANT_EXAMEN_MEDICO_LABORATORIO ( " 
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroRegistro, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.anio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idRPR, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.dilucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idGrupoSanguineo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idFactorRh, jdbcType = CHAR, mode = IN},"
            + "#{objeto.idHemograma, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.observacion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.hemoglobina, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.idCampania, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idEstadoExamenMedico, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ExamenMedicoLaboratorio> mantener(Parametro parametro);
}