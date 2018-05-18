package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.mantenimiento.Alumno;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IAlumnoMapper extends IMantenibleMapper<Alumno>
{
    @Select(value = { "{call MANT_ALUMNOS ( " 
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTipoDocumento, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroDocumento, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nombres, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.apellidoPaterno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.apellidoMaterno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idSexo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.direccion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoUbigeo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.fechaNacimiento, jdbcType = DATE, mode = IN},"
            + "#{objeto.codigoFacultad, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.codigoEscuela, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.correoInstitucional, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.correoPersonal, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.telefonoFijo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.telefonoMovil, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idDiscapacidad, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Alumno> mantener(Parametro parametro);
}