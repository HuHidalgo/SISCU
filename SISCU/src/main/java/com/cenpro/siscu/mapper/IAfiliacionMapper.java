package com.cenpro.siscu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;
import com.cenpro.siscu.model.parametro.Parametro;

public interface IAfiliacionMapper extends IMantenibleMapper<Afiliacion>
{
    @Select(value = { "{call MANT_AFILIACION ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idAfiliacion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idEstamento, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.apellidoPaterno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.apellidoMaterno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nombres, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTipoDocumento, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroDocumento, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idSexo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEstadoCivil, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.fechaNacimiento, jdbcType = DATE, mode = IN},"
            + "#{objeto.idDistritoNac, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idDepartamentoNac, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idGradoInstruccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.religion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.ocupacionActual, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.direccionActual, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.distritoProcedencia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.telefonoFijo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.celular, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.correo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.fechaRegistroHC, jdbcType = DATE, mode = IN},"
            + "#{objeto.codigoAlumno, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoEscuela, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.codigoFacultad, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.areaTrabajo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nombreEmerg, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.direccionEmerg, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.telefonoEmerg, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Afiliacion> mantener(Parametro parametro);
    
    public List<Afiliacion> buscarPorNroDocumento(CriterioBusquedaEstamento criterioBusqueda);
    
    public List<Afiliacion> buscarPorNroDocumentoNoAfiliado(CriterioBusquedaEstamento criterioBusqueda);
}