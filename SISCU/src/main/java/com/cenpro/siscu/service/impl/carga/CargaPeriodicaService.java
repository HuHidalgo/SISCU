package com.cenpro.siscu.service.impl.carga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cenpro.siscu.mapper.ICargaPeriodicaMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.admision.Afiliacion.AfiliacionBuilder;
import com.cenpro.siscu.model.carga.Carga;
import com.cenpro.siscu.model.carga.ErrorCarga;
import com.cenpro.siscu.model.carga.ErrorCarga.ErrorCargaBuilder;
import com.cenpro.siscu.service.ICargaPeriodicaService;
import com.cenpro.siscu.service.excepcion.CargaArchivoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.ConstantesFormatosExcelRegular;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.Verbo;
import com.cenpro.siscu.service.impl.MantenibleService;

@Service
public class CargaPeriodicaService extends MantenibleService<Afiliacion> implements ICargaPeriodicaService
{
	//@SuppressWarnings("unused")
    private ICargaPeriodicaMapper cargaPeriodicaMapper;
    private static final String CARGAR = "CARGAR";
    
    public CargaPeriodicaService(@Qualifier("ICargaPeriodicaMapper") IMantenibleMapper<Afiliacion> mapper)
    {
        super(mapper);
        this.cargaPeriodicaMapper = (ICargaPeriodicaMapper) mapper;
    }

	//@SuppressWarnings("unlikely-arg-type")
	public Carga cargarAlumnos(MultipartFile archivoAlumnos, String estamento)
    {
        List<Afiliacion> alumnos = new ArrayList<>();
        List<ErrorCarga> errores = new ArrayList<>();
        Carga carga = new Carga();
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
        int fila2 = 0;
        
        while (fila <= worksheet.getLastRowNum() && !finExcel)
        {
            XSSFRow row = worksheet.getRow(fila);

            AfiliacionBuilder alumno = Afiliacion.builder();
            ErrorCargaBuilder error = ErrorCarga.builder();
            error.nombreColumna("");
            
            // TIPO DE PACIENTE (ESTAMENTO)
            alumno.idEstamento(Integer.parseInt(estamento));

            // CODIGO ALUMNO
            Cell codigoAlumno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ALUMNO);
            if (codigoAlumno == null || codigoAlumno.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Código de Alumno' ");
                //finExcel = true;
                //continue;
            }
            codigoAlumno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.codigoAlumno(codigoAlumno.getStringCellValue().trim());

            // TIPO ALUMNO
            //alumno.tipoAlumno("R");

            // APELLIDO PATERNO
            Cell apellidoPaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_PATERNO);
            if (apellidoPaterno == null || apellidoPaterno.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Apellido Paterno' ");
                //finExcel = true;
                //continue;
            }
            else {
            	apellidoPaterno.setCellType(Cell.CELL_TYPE_STRING);
                alumno.apellidoPaterno(Optional.ofNullable(apellidoPaterno.getStringCellValue()).orElse("").trim());
            }                      

            // APELLIDO MATERNO
            Cell apellidoMaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_MATERNO);
            if (apellidoMaterno == null || apellidoMaterno.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Apellido Materno' ");
                //finExcel = true;
                //continue;
            }
            else {
            	apellidoMaterno.setCellType(Cell.CELL_TYPE_STRING);
                alumno.apellidoMaterno(apellidoMaterno.getStringCellValue().trim());
            }
            
            // NOMBRES
            Cell nombres = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRES);
            if (nombres == null || nombres.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Nombres' ");
                //finExcel = true;
                //continue;
            }
            else {
            	nombres.setCellType(Cell.CELL_TYPE_STRING);
                alumno.nombres(nombres.getStringCellValue().trim());
            }

            // SEXO
            Cell sexo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_SEXO);
            if (sexo == null || sexo.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Sexo' ");
                //finExcel = true;
                //continue;
            }
            else {
            	sexo.setCellType(Cell.CELL_TYPE_STRING);
                String idSexo = sexo.getStringCellValue().trim();
                alumno.idSexo(idSexo.length() == 1 ? idSexo : "N");
            }
            
            // FECHA NACIMIENTO
            Cell fechaNacimiento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_FECHA_NACIMIENTO);
            if (fechaNacimiento == null || fechaNacimiento.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Fecha de Nacimiento' ");
                //finExcel = true;
                //continue;
            }
            else {
            	try{
                    alumno.fechaNacimiento(fechaNacimiento.getDateCellValue());
                } catch (Exception e){
                    alumno.fechaNacimiento(null);
                }
            }
            
            // TIPO DOCUMENTO
            Cell tipoDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TIPO_DOCUMENTO);
            if (tipoDocumento == null || tipoDocumento.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Tipo de Documento' ");
                //finExcel = true;
                //continue;
            }
            else {
            	tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
                alumno.idTipoDocumento(tipoDocumento.getStringCellValue().trim());
            }            

            // NUMERO DOCUMENTO
            Cell numeroDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NUMERO_DOCUMENTO);
            if (numeroDocumento == null || numeroDocumento.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Numero de Documento' ");
                //finExcel = true;
                //continue;
            }
            else {
                numeroDocumento.setCellType(Cell.CELL_TYPE_STRING);
                alumno.numeroDocumento(numeroDocumento.getStringCellValue().trim());
            }

            // CORREO PERSONAL
            Cell correoPersonal = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CORREO_PERSONAL);
            correoPersonal.setCellType(Cell.CELL_TYPE_STRING);
            alumno.correo(correoPersonal.getStringCellValue().trim());

            // DIRECCION
            Cell direccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION);
            direccion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.direccionActual(direccion.getStringCellValue().trim());

            // TELEFONO FIJO
            Cell telefonoFijo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_FIJO);
            telefonoFijo.setCellType(Cell.CELL_TYPE_STRING);
            alumno.telefonoFijo(telefonoFijo.getStringCellValue().trim());

            // TELEFONO MOVIL
            Cell telefonoMovil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_MOVIL);
            telefonoMovil.setCellType(Cell.CELL_TYPE_STRING);
            alumno.celular(telefonoMovil.getStringCellValue().trim());

            // CODIGO ESCUELA
            Cell codigoEscuela = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ESCUELA);
            codigoEscuela.setCellType(Cell.CELL_TYPE_STRING);
            try{
                alumno.codigoEscuela(Integer.parseInt(codigoEscuela.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                alumno.codigoEscuela(0);
            }

            // CODIGO FACULTAD
            Cell codigoFacultad = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_FACULTAD);
            codigoFacultad.setCellType(Cell.CELL_TYPE_STRING);
            try{
                alumno.codigoFacultad(Integer.parseInt(codigoFacultad.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                alumno.codigoFacultad(0);
            }
            
            // ESTADO CIVIL
            Cell estadoCivil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_ESTADO_CIVIL);
            estadoCivil.setCellType(Cell.CELL_TYPE_STRING);
            alumno.descEstadoCivil(estadoCivil.getStringCellValue().trim());
            
            // DEPARTAMENTO DE NACIMIENTO
            Cell departamentoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DEPARTAMENTO_NAC);
            departamentoNac.setCellType(Cell.CELL_TYPE_STRING);
            alumno.departamentoNac(departamentoNac.getStringCellValue().trim());
            
            // DISTRITO DE NACIMIENTO
            Cell distritoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_NAC);
            distritoNac.setCellType(Cell.CELL_TYPE_STRING);
            alumno.distritoNac(distritoNac.getStringCellValue().trim());
            
            // GRADO INSTRUCCION
            Cell gradoIntruccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_GRADO_INSTRUCCION);
            gradoIntruccion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.gradoInstruccion(gradoIntruccion.getStringCellValue().trim());
            
            // RELIGION
            Cell religion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_RELIGION);
            religion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.religion(religion.getStringCellValue().trim());
            
            // OCUPACION ACTUAL
            Cell ocupacionActual = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_OCUPACION_ACTUAL);
            ocupacionActual.setCellType(Cell.CELL_TYPE_STRING);
            alumno.ocupacionActual(ocupacionActual.getStringCellValue().trim());
            
            // DISTRITO DE PROCEDENCIA
            Cell distritoProc = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_PROCE);
            distritoProc.setCellType(Cell.CELL_TYPE_STRING);
            alumno.distritoProcedencia(distritoProc.getStringCellValue().trim());
            
            // NOMBRE EMERGENCIA
            Cell nombreEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRE_EMERGENCIA);
            if (nombreEmergencia == null) {
            	alumno.nombreEmerg("");
            }
            else {
	            nombreEmergencia.setCellType(Cell.CELL_TYPE_STRING);
	            alumno.nombreEmerg(nombreEmergencia.getStringCellValue().trim());
            }
            
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
            if (error.build().getNombreColumna() != "")
            	errores.add(error.build());
            fila++;
        }
        try{
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("FIN LECTURA EXCEL");
        registrarAlumnos(alumnos);
        carga.setErrorCarga(errores);
        carga.setTotalRegistros(alumnos.size());;
        return carga;
    }

	@Override
	public Carga cargarDocentes(MultipartFile archivoDocentes, String estamento) {
		List<Afiliacion> docentes = new ArrayList<>();
        List<ErrorCarga> errores = new ArrayList<>();
        Carga carga = new Carga();

        boolean finExcel = false;
        XSSFWorkbook workbook = null;
        XSSFSheet worksheet = null;
        int fila = 1;

        try{
            workbook = new XSSFWorkbook(archivoDocentes.getInputStream());
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

            AfiliacionBuilder docente = Afiliacion.builder();
            ErrorCargaBuilder error = ErrorCarga.builder();
            
            // TIPO DE PACIENTE (ESTAMENTO)
            docente.idEstamento(Integer.parseInt(estamento));

            // CODIGO DOCENTE
            Cell codigoDocente = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ALUMNO);
            //System.out.println("codigo ALUMNO: " + codigoAlumno);
            if (codigoDocente == null){
                finExcel = true;
                continue;
            }
            codigoDocente.setCellType(Cell.CELL_TYPE_STRING);
            docente.codigoAlumno(codigoDocente.getStringCellValue().trim());
            
            // TIPO ALUMNO
            //alumno.tipoAlumno("R");

            // APELLIDO PATERNO
            Cell apellidoPaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_PATERNO);
            //System.out.println("apellidoPaterno: " + apellidoPaterno);
            apellidoPaterno.setCellType(Cell.CELL_TYPE_STRING);
            docente.apellidoPaterno(Optional.ofNullable(apellidoPaterno.getStringCellValue()).orElse("").trim());

            // APELLIDO MATERNO
            Cell apellidoMaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_MATERNO);
            //System.out.println("apellidoMaterno: " + apellidoMaterno);
            apellidoMaterno.setCellType(Cell.CELL_TYPE_STRING);
            docente.apellidoMaterno(apellidoMaterno.getStringCellValue().trim());

            // NOMBRES
            Cell nombres = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRES);
            //System.out.println("nombres: " + nombres);
            nombres.setCellType(Cell.CELL_TYPE_STRING);
            docente.nombres(nombres.getStringCellValue().trim());

            // SEXO
            Cell sexo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_SEXO);
            //System.out.println("sexo: " + sexo);
            sexo.setCellType(Cell.CELL_TYPE_STRING);
            String idSexo = sexo.getStringCellValue().trim();
            docente.idSexo(idSexo.length() == 1 ? idSexo : "N");

            // FECHA NACIMIENTO
            Cell fechaNacimiento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_FECHA_NACIMIENTO);
            //System.out.println("fechaNacimiento: " + fechaNacimiento);
            try{
                docente.fechaNacimiento(fechaNacimiento.getDateCellValue());
            } catch (Exception e){
                docente.fechaNacimiento(null);
            }

            // TIPO DOCUMENTO
            Cell tipoDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TIPO_DOCUMENTO);
            //System.out.println("tipoDocumento: " + tipoDocumento);
            tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
            docente.idTipoDocumento(tipoDocumento.getStringCellValue().trim());

            // NUMERO DOCUMENTO
            Cell numeroDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NUMERO_DOCUMENTO);
            //System.out.println("numeroDocumento: " + numeroDocumento);
            numeroDocumento.setCellType(Cell.CELL_TYPE_STRING);
            docente.numeroDocumento(numeroDocumento.getStringCellValue().trim());

            // CORREO PERSONAL
            Cell correoPersonal = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CORREO_PERSONAL);
            //System.out.println("correoPersonal: " + correoPersonal);
            correoPersonal.setCellType(Cell.CELL_TYPE_STRING);
            docente.correo(correoPersonal.getStringCellValue().trim());

            // DIRECCION
            Cell direccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION);
            //System.out.println("direccion: " + direccion);
            direccion.setCellType(Cell.CELL_TYPE_STRING);
            docente.direccionActual(direccion.getStringCellValue().trim());

            // TELEFONO FIJO
            Cell telefonoFijo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_FIJO);
            //System.out.println("telefonoFijo: " + telefonoFijo);
            telefonoFijo.setCellType(Cell.CELL_TYPE_STRING);
            docente.telefonoFijo(telefonoFijo.getStringCellValue().trim());

            // TELEFONO MOVIL
            Cell telefonoMovil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_MOVIL);
            //System.out.println("telefonoMovil: " + telefonoMovil);
            telefonoMovil.setCellType(Cell.CELL_TYPE_STRING);
            docente.celular(telefonoMovil.getStringCellValue().trim());

            // CODIGO ESCUELA
            Cell codigoEscuela = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ESCUELA);
            //System.out.println("codigoEscuela: " + codigoEscuela);
            codigoEscuela.setCellType(Cell.CELL_TYPE_STRING);
            try{
                docente.codigoEscuela(Integer.parseInt(codigoEscuela.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                docente.codigoEscuela(0);
            }

            // CODIGO FACULTAD
            Cell codigoFacultad = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_FACULTAD);
            //System.out.println("codigoFacultad: " + codigoFacultad);
            codigoFacultad.setCellType(Cell.CELL_TYPE_STRING);
            try{
                docente.codigoFacultad(Integer.parseInt(codigoFacultad.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                docente.codigoFacultad(0);
            }
            
            // ESTADO CIVIL
            Cell estadoCivil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_ESTADO_CIVIL);
            estadoCivil.setCellType(Cell.CELL_TYPE_STRING);
            docente.descEstadoCivil(estadoCivil.getStringCellValue().trim());
            
            // DEPARTAMENTO DE NACIMIENTO
            Cell departamentoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DEPARTAMENTO_NAC);
            departamentoNac.setCellType(Cell.CELL_TYPE_STRING);
            docente.departamentoNac(departamentoNac.getStringCellValue().trim());
            
            // DISTRITO DE NACIMIENTO
            Cell distritoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_NAC);
            distritoNac.setCellType(Cell.CELL_TYPE_STRING);
            docente.distritoNac(distritoNac.getStringCellValue().trim());
            
            // GRADO INSTRUCCION
            Cell gradoIntruccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_GRADO_INSTRUCCION);
            gradoIntruccion.setCellType(Cell.CELL_TYPE_STRING);
            docente.gradoInstruccion(gradoIntruccion.getStringCellValue().trim());
            
            // RELIGION
            Cell religion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_RELIGION);
            religion.setCellType(Cell.CELL_TYPE_STRING);
            docente.religion(religion.getStringCellValue().trim());
            
            // OCUPACION ACTUAL
            Cell ocupacionActual = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_OCUPACION_ACTUAL);
            ocupacionActual.setCellType(Cell.CELL_TYPE_STRING);
            docente.ocupacionActual(ocupacionActual.getStringCellValue().trim());
            
            // DISTRITO DE PROCEDENCIA
            Cell distritoProc = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_PROCE);
            distritoProc.setCellType(Cell.CELL_TYPE_STRING);
            docente.distritoProcedencia(distritoProc.getStringCellValue().trim());
            
            // NOMBRE EMERGENCIA
            Cell nombreEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRE_EMERGENCIA);
            nombreEmergencia.setCellType(Cell.CELL_TYPE_STRING);
            docente.nombreEmerg(nombreEmergencia.getStringCellValue().trim());
            
            // TELEFONO EMERGENCIA
            Cell telefonoEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_EMERGENCIA);
            telefonoEmergencia.setCellType(Cell.CELL_TYPE_STRING);
            docente.telefonoEmerg(telefonoEmergencia.getStringCellValue().trim());
            
            // DIRECCION EMERGENCIA
            Cell direccionEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION_EMERGENCIA);
            direccionEmergencia.setCellType(Cell.CELL_TYPE_STRING);
            docente.direccionEmerg(direccionEmergencia.getStringCellValue().trim());

            docentes.add(docente.build());
            fila++;
        }
        try{
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("FIN LECTURA EXCEL");
        registrarDocentes(docentes);
        return carga;
	}

	@Override
	public Carga cargarNoDocentes(MultipartFile archivoNoDocentes, String estamento) {
		List<Afiliacion> noDocentes = new ArrayList<>();
        List<ErrorCarga> errores = new ArrayList<>();
        Carga carga = new Carga();

        boolean finExcel = false;
        XSSFWorkbook workbook = null;
        XSSFSheet worksheet = null;
        int fila = 1;

        try{
            workbook = new XSSFWorkbook(archivoNoDocentes.getInputStream());
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

            AfiliacionBuilder noDocente = Afiliacion.builder();
            ErrorCargaBuilder error = ErrorCarga.builder();
            
            // TIPO DE PACIENTE (ESTAMENTO)
            noDocente.idEstamento(Integer.parseInt(estamento));

            // CODIGO ALUMNO
            Cell codigoNoDocente = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ALUMNO);
            //System.out.println("codigo ALUMNO: " + codigoAlumno);
            if (codigoNoDocente == null){
                finExcel = true;
                continue;
            }
            codigoNoDocente.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.codigoAlumno(codigoNoDocente.getStringCellValue().trim());
            
            // TIPO ALUMNO
            //alumno.tipoAlumno("R");

            // APELLIDO PATERNO
            Cell apellidoPaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_PATERNO);
            //System.out.println("apellidoPaterno: " + apellidoPaterno);
            apellidoPaterno.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.apellidoPaterno(Optional.ofNullable(apellidoPaterno.getStringCellValue()).orElse("").trim());

            // APELLIDO MATERNO
            Cell apellidoMaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_MATERNO);
            //System.out.println("apellidoMaterno: " + apellidoMaterno);
            apellidoMaterno.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.apellidoMaterno(apellidoMaterno.getStringCellValue().trim());

            // NOMBRES
            Cell nombres = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRES);
            //System.out.println("nombres: " + nombres);
            nombres.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.nombres(nombres.getStringCellValue().trim());

            // SEXO
            Cell sexo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_SEXO);
            //System.out.println("sexo: " + sexo);
            sexo.setCellType(Cell.CELL_TYPE_STRING);
            String idSexo = sexo.getStringCellValue().trim();
            noDocente.idSexo(idSexo.length() == 1 ? idSexo : "N");

            // FECHA NACIMIENTO
            Cell fechaNacimiento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_FECHA_NACIMIENTO);
            //System.out.println("fechaNacimiento: " + fechaNacimiento);
            try{
            	noDocente.fechaNacimiento(fechaNacimiento.getDateCellValue());
            } catch (Exception e){
            	noDocente.fechaNacimiento(null);
            }

            // TIPO DOCUMENTO
            Cell tipoDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TIPO_DOCUMENTO);
            //System.out.println("tipoDocumento: " + tipoDocumento);
            tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.idTipoDocumento(tipoDocumento.getStringCellValue().trim());

            // NUMERO DOCUMENTO
            Cell numeroDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NUMERO_DOCUMENTO);
            //System.out.println("numeroDocumento: " + numeroDocumento);
            numeroDocumento.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.numeroDocumento(numeroDocumento.getStringCellValue().trim());

            // CORREO PERSONAL
            Cell correoPersonal = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CORREO_PERSONAL);
            //System.out.println("correoPersonal: " + correoPersonal);
            correoPersonal.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.correo(correoPersonal.getStringCellValue().trim());

            // DIRECCION
            Cell direccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION);
            //System.out.println("direccion: " + direccion);
            direccion.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.direccionActual(direccion.getStringCellValue().trim());

            // TELEFONO FIJO
            Cell telefonoFijo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_FIJO);
            //System.out.println("telefonoFijo: " + telefonoFijo);
            telefonoFijo.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.telefonoFijo(telefonoFijo.getStringCellValue().trim());

            // TELEFONO MOVIL
            Cell telefonoMovil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_MOVIL);
            //System.out.println("telefonoMovil: " + telefonoMovil);
            telefonoMovil.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.celular(telefonoMovil.getStringCellValue().trim());

            // CODIGO FACULTAD
            Cell codigoFacultad = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_FACULTAD);
            //System.out.println("codigoFacultad: " + codigoFacultad);
            codigoFacultad.setCellType(Cell.CELL_TYPE_STRING);
            try{
            	noDocente.codigoFacultad(Integer.parseInt(codigoFacultad.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                noDocente.codigoFacultad(0);
            }
            
            // DEPARTAMENTO DE TRABAJO
            Cell departamentoTrabajo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DEPARTAMENTO_TRABAJO);
            //System.out.println("Area de Trabajo : " + departamentoTrabajo);
            departamentoTrabajo.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.areaTrabajo(departamentoTrabajo.getStringCellValue().trim());
            
            // ESTADO CIVIL
            Cell estadoCivil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_ESTADO_CIVIL);
            estadoCivil.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.descEstadoCivil(estadoCivil.getStringCellValue().trim());
            
            // DEPARTAMENTO DE NACIMIENTO
            Cell departamentoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DEPARTAMENTO_NAC);
            departamentoNac.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.departamentoNac(departamentoNac.getStringCellValue().trim());
            
            // DISTRITO DE NACIMIENTO
            Cell distritoNac = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_NAC);
            distritoNac.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.distritoNac(distritoNac.getStringCellValue().trim());
            
            // GRADO INSTRUCCION
            Cell gradoIntruccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_GRADO_INSTRUCCION);
            gradoIntruccion.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.gradoInstruccion(gradoIntruccion.getStringCellValue().trim());
            
            // RELIGION
            Cell religion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_RELIGION);
            religion.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.religion(religion.getStringCellValue().trim());
            
            // OCUPACION ACTUAL
            Cell ocupacionActual = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_OCUPACION_ACTUAL);
            ocupacionActual.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.ocupacionActual(ocupacionActual.getStringCellValue().trim());
            
            // DISTRITO DE PROCEDENCIA
            Cell distritoProc = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DISTRITO_PROCE);
            distritoProc.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.distritoProcedencia(distritoProc.getStringCellValue().trim());
            
            // NOMBRE EMERGENCIA
            Cell nombreEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRE_EMERGENCIA);
            nombreEmergencia.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.nombreEmerg(nombreEmergencia.getStringCellValue().trim());
            
            // TELEFONO EMERGENCIA
            Cell telefonoEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_EMERGENCIA);
            telefonoEmergencia.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.telefonoEmerg(telefonoEmergencia.getStringCellValue().trim());
            
            // DIRECCION EMERGENCIA
            Cell direccionEmergencia = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION_EMERGENCIA);
            direccionEmergencia.setCellType(Cell.CELL_TYPE_STRING);
            noDocente.direccionEmerg(direccionEmergencia.getStringCellValue().trim());

            noDocentes.add(noDocente.build());
            fila++;
        }
        try{
            workbook.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("FIN LECTURA EXCEL");
        registrarNoDocentes(noDocentes);
        return carga;
	}

	
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAlumnos(List<Afiliacion> pacientes)
    {
    	pacientes.stream().forEach(paciente -> this.registrar(paciente, CARGAR));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarDocentes(List<Afiliacion> docentes) {
		docentes.stream().forEach(docente -> this.registrar(docente, CARGAR));
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void registrarNoDocentes(List<Afiliacion> noDocentes) {
		noDocentes.stream().forEach(noDocente -> this.registrar(noDocente, CARGAR));
	}

}