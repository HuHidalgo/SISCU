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

import com.cenpro.siscu.mapper.ICargaActualizarCodigoMapper;
import com.cenpro.siscu.mapper.ICargaAtendidosMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.admision.Afiliacion;
import com.cenpro.siscu.model.admision.Afiliacion.AfiliacionBuilder;
import com.cenpro.siscu.model.carga.Carga;
import com.cenpro.siscu.model.carga.ErrorCarga;
import com.cenpro.siscu.model.carga.ErrorCarga.ErrorCargaBuilder;
import com.cenpro.siscu.service.ICargaActualizarCodigoService;
import com.cenpro.siscu.service.ICargaAtendidosService;
import com.cenpro.siscu.service.excepcion.CargaArchivoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.ConstantesFormatosExcelRegular;
import com.cenpro.siscu.utilitario.ConstantesGenerales;
import com.cenpro.siscu.utilitario.Verbo;
import com.cenpro.siscu.service.impl.MantenibleService;

@Service
public class CargaActualizarCodigoService extends MantenibleService<Afiliacion> implements ICargaActualizarCodigoService
{
	//@SuppressWarnings("unused")
    private ICargaActualizarCodigoMapper cargaActualizarCodigoMapper;
    private static final String CARGAR = "CARGAR";
    
    public CargaActualizarCodigoService(@Qualifier("ICargaActualizarCodigoMapper") IMantenibleMapper<Afiliacion> mapper)
    {
        super(mapper);
        this.cargaActualizarCodigoMapper = (ICargaActualizarCodigoMapper) mapper;
    }
    
    @Override
	public Carga cargarAlumnos(MultipartFile archivoAlumnos, String estamento) {
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
                                    
            // TIPO DOCUMENTO
            Cell tipoDocumento = row.getCell(0);
            if (tipoDocumento == null || tipoDocumento.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Tipo de Documento' ");
            	errores.add(error.build());
                finExcel = true;
                continue;
            }
            else {
            	tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
                alumno.idTipoDocumento(tipoDocumento.getStringCellValue().trim());
            }            

            // NUMERO DOCUMENTO
            Cell numeroDocumento = row.getCell(1);
            if (numeroDocumento == null || numeroDocumento.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Numero de Documento' ");
            	errores.add(error.build());
                finExcel = true;
                continue;
            }
            else {
                numeroDocumento.setCellType(Cell.CELL_TYPE_STRING);
                alumno.numeroDocumento(numeroDocumento.getStringCellValue().trim());
            }
            
            // NUMERO DOCUMENTO
            Cell codigoAlumno = row.getCell(2);
            if (codigoAlumno == null || codigoAlumno.toString() == ""){
            	fila2 = fila +1;
            	error.fila(fila2);
            	error.nombreColumna(error.build().getNombreColumna()+ " 'Código de Alumno' ");
            	errores.add(error.build());
                finExcel = true;
                continue;
            }
            else {
            	codigoAlumno.setCellType(Cell.CELL_TYPE_STRING);
                alumno.codigoAlumno(codigoAlumno.getStringCellValue().trim());
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
        registrarAlumnos(alumnos);
        carga.setErrorCarga(errores);
        carga.setTotalRegistros(alumnos.size());
        return carga;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAlumnos(List<Afiliacion> pacientes)
    {
    	pacientes.stream().forEach(paciente -> this.registrar(paciente, CARGAR));
    }

}