package com.cenpro.siscu.service.impl.carga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.mapper.ICargaInicialMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.admision.Afiliacion.AfiliacionBuilder;
import com.cenpro.siscu.model.criterio.CriterioBusquedaEstamento;
import com.cenpro.siscu.service.ICargaInicialService;
import com.cenpro.siscu.service.excepcion.CargaArchivoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.ConstantesFormatosExcelIngresante;
import com.cenpro.siscu.utilitario.ConstantesFormatosExcelRegular;
import com.cenpro.siscu.utilitario.Verbo;
import com.cenpro.siscu.service.impl.MantenibleService;

@Service
public class CargaInicialService extends MantenibleService<Afiliacion> implements ICargaInicialService
{
	@SuppressWarnings("unused")
    private ICargaInicialMapper cargaInicialMapper;
    private static final String CARGAR = "CARGAR";
    
    public CargaInicialService(@Qualifier("ICargaInicialMapper") IMantenibleMapper<Afiliacion> mapper)
    {
        super(mapper);
        this.cargaInicialMapper = (ICargaInicialMapper) mapper;
    }

    public void cargarAlumnos(MultipartFile archivoAlumnos, String estamento)
    {
        List<Afiliacion> alumnos = new ArrayList<>();

        boolean finExcel = false;
        XSSFWorkbook workbook = null;
        XSSFSheet worksheet = null;
        int fila = 1;

        try{
            workbook = new XSSFWorkbook(archivoAlumnos.getInputStream());
        } catch (IOException e){
            System.out.println("Error: " + e);
            throw new CargaArchivoException(ConstantesExcepciones.ERROR_LECTURA_ARCHIVO);
        }
        worksheet = workbook.getSheetAt(0);
        worksheet.getRow(3);
        fila = ConstantesFormatosExcelRegular.CANTIDAD_FILA_INICIO;

        while (fila <= worksheet.getLastRowNum() && !finExcel)
        {
            XSSFRow row = worksheet.getRow(fila);

            AfiliacionBuilder alumno = Afiliacion.builder();
            
            // TIPO DE PACIENTE (ESTAMENTO)
            alumno.idEstamento(Integer.parseInt(estamento));

            // CODIGO ALUMNO
            Cell codigoAlumno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ALUMNO);
            System.out.println("codigo ALUMNO: " + codigoAlumno);
            if (codigoAlumno == null){
                finExcel = true;
                continue;
            }
            codigoAlumno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.codigoAlumno(codigoAlumno.getStringCellValue().trim());

            // TIPO ALUMNO
            //alumno.tipoAlumno("R");

            // APELLIDO PATERNO
            Cell apellidoPaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_PATERNO);
            System.out.println("apellidoPaterno: " + apellidoPaterno);
            apellidoPaterno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.apellidoPaterno(Optional.ofNullable(apellidoPaterno.getStringCellValue()).orElse("").trim());

            // APELLIDO MATERNO
            Cell apellidoMaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_MATERNO);
            System.out.println("apellidoMaterno: " + apellidoMaterno);
            apellidoMaterno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.apellidoMaterno(apellidoMaterno.getStringCellValue().trim());

            // NOMBRES
            Cell nombres = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRES);
            System.out.println("nombres: " + nombres);
            nombres.setCellType(Cell.CELL_TYPE_STRING);
            alumno.nombres(nombres.getStringCellValue().trim());

            // SEXO
            Cell sexo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_SEXO);
            System.out.println("sexo: " + sexo);
            sexo.setCellType(Cell.CELL_TYPE_STRING);
            String idSexo = sexo.getStringCellValue().trim();
            alumno.idSexo(idSexo.length() == 1 ? idSexo : "N");

            // FECHA NACIMIENTO
            Cell fechaNacimiento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_FECHA_NACIMIENTO);
            System.out.println("fechaNacimiento: " + fechaNacimiento);
            try{
                alumno.fechaNacimiento(fechaNacimiento.getDateCellValue());
            } catch (Exception e){
                alumno.fechaNacimiento(null);
            }

            // TIPO DOCUMENTO
            Cell tipoDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TIPO_DOCUMENTO);
            System.out.println("tipoDocumento: " + tipoDocumento);
            tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
            alumno.idTipoDocumento(tipoDocumento.getStringCellValue().trim());

            // NUMERO DOCUMENTO
            Cell numeroDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NUMERO_DOCUMENTO);
            System.out.println("numeroDocumento: " + numeroDocumento);
            numeroDocumento.setCellType(Cell.CELL_TYPE_STRING);
            alumno.numeroDocumento(numeroDocumento.getStringCellValue().trim());

            // CORREO PERSONAL
            Cell correoPersonal = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CORREO_PERSONAL);
            System.out.println("correoPersonal: " + correoPersonal);
            correoPersonal.setCellType(Cell.CELL_TYPE_STRING);
            alumno.correo(correoPersonal.getStringCellValue().trim());

            // DIRECCION
            Cell direccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION);
            System.out.println("direccion: " + direccion);
            direccion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.direccionActual(direccion.getStringCellValue().trim());

            // TELEFONO FIJO
            Cell telefonoFijo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_FIJO);
            System.out.println("telefonoFijo: " + telefonoFijo);
            telefonoFijo.setCellType(Cell.CELL_TYPE_STRING);
            alumno.telefonoFijo(telefonoFijo.getStringCellValue().trim());

            // TELEFONO MOVIL
            Cell telefonoMovil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_MOVIL);
            System.out.println("telefonoMovil: " + telefonoMovil);
            telefonoMovil.setCellType(Cell.CELL_TYPE_STRING);
            alumno.celular(telefonoMovil.getStringCellValue().trim());

            // CODIGO ESCUELA
            Cell codigoEscuela = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ESCUELA);
            System.out.println("codigoEscuela: " + codigoEscuela);
            codigoEscuela.setCellType(Cell.CELL_TYPE_STRING);
            try{
                alumno.codigoEscuela(Integer.parseInt(codigoEscuela.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                alumno.codigoEscuela(0);
            }

            // CODIGO FACULTAD
            Cell codigoFacultad = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_FACULTAD);
            System.out.println("codigoFacultad: " + codigoFacultad);
            codigoFacultad.setCellType(Cell.CELL_TYPE_STRING);
            try{
                alumno.codigoFacultad(Integer.parseInt(codigoFacultad.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                alumno.codigoFacultad(0);
            }
            
            // ESTADO CIVIL
            Cell estadoCivil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_ESTADO_CIVIL);
            System.out.println("estadoCivil: " + estadoCivil);
            estadoCivil.setCellType(Cell.CELL_TYPE_STRING);
            alumno.descEstadoCivil(estadoCivil.getStringCellValue().trim());
            
            // DEPARTAMENTO DE NACIMIENTO
            Cell departamentoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DEPARTAMENTO_NAC);
            System.out.println("departamentoNac: " + departamentoNac);
            departamentoNac.setCellType(Cell.CELL_TYPE_STRING);
            alumno.departamentoNac(departamentoNac.getStringCellValue().trim());
            
            // DISTRITO DE NACIMIENTO
            Cell distritoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_NAC);
            System.out.println("codigoFacultad: " + departamentoNac);
            distritoNac.setCellType(Cell.CELL_TYPE_STRING);
            alumno.distritoNac(distritoNac.getStringCellValue().trim());
            
            // GRADO INSTRUCCION
            Cell gradoIntruccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_GRADO_INSTRUCCION);
            System.out.println("gradoIntruccion: " + gradoIntruccion);
            gradoIntruccion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.gradoInstruccion(gradoIntruccion.getStringCellValue().trim());
            
            // RELIGION
            Cell religion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_RELIGION);
            System.out.println("religion: " + religion);
            religion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.religion(religion.getStringCellValue().trim());
            
            // OCUPACION ACTUAL
            Cell ocupacionActual = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_OCUPACION_ACTUAL);
            System.out.println("ocupacionActual: " + ocupacionActual);
            ocupacionActual.setCellType(Cell.CELL_TYPE_STRING);
            alumno.ocupacionActual(ocupacionActual.getStringCellValue().trim());
            
            // DISTRITO DE PROCEDENCIA
            Cell distritoProc = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_PROCE);
            System.out.println("distritoProc: " + distritoProc);
            distritoProc.setCellType(Cell.CELL_TYPE_STRING);
            alumno.distritoProcedencia(distritoProc.getStringCellValue().trim());
            
            // NOMBRE EMERGENCIA
            Cell nombreEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRE_EMERGENCIA);
            System.out.println("nombreEmergencia: " + nombreEmergencia);
            if (nombreEmergencia == null) {
            	alumno.nombreEmerg("");
            }
            else {
	            nombreEmergencia.setCellType(Cell.CELL_TYPE_STRING);
	            alumno.nombreEmerg(nombreEmergencia.getStringCellValue().trim());
            }
            System.out.println("nombreEmergencia: " + nombreEmergencia);
            
            // TELEFONO EMERGENCIA
            Cell telefonoEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_EMERGENCIA);
            if (telefonoEmergencia == null) {
            	alumno.telefonoEmerg("");
            }
            else {
	            telefonoEmergencia.setCellType(Cell.CELL_TYPE_STRING);
	            alumno.telefonoEmerg(telefonoEmergencia.getStringCellValue().trim());
            }
            
            // DIRECCION EMERGENCIA
            Cell direccionEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION_EMERGENCIA);
            if (direccionEmergencia == null) {
            	alumno.direccionEmerg("");
            }
            else {
	            direccionEmergencia.setCellType(Cell.CELL_TYPE_STRING);
	            alumno.direccionEmerg(direccionEmergencia.getStringCellValue().trim());
            }
            alumnos.add(alumno.build());
            fila++;
        }
        try{
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("FIN LECTURA EXCEL");
        System.out.println("ALUMNOS: ");
        registrarAlumnos(alumnos);
    }

	@Override
	public void cargarDocentes(MultipartFile archivoDocentes, String estamento) {
		// TODO Auto-generated method stub
	}

	@Override
	public void cargarNoDocentes(MultipartFile archivoNoDocentes, String estamento) {
		// TODO Auto-generated method stub
	}

	@Override
	public void cargarParticulares(MultipartFile archivoParticulares, String estamento) {
		// TODO Auto-generated method stub
	}

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAlumnos(List<Afiliacion> pacientes)
    {
    	pacientes.stream().forEach(paciente -> this.registrar(paciente, CARGAR));
    }

	@Override
	public List<Afiliacion> consultarPorNroDocumento(CriterioBusquedaEstamento criterioBusquedaEstamento) {
		Afiliacion paciente = Afiliacion.builder().idEstamento(criterioBusquedaEstamento.getIdEstamento()).
				idTipoDocumento(criterioBusquedaEstamento.getTipoDocumento()).
				numeroDocumento(criterioBusquedaEstamento.getNroDocumento()).build();
		return this.buscar(paciente, Verbo.GET_PACIENTE);
	}
}