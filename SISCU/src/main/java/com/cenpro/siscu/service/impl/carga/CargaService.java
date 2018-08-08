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

import com.cenpro.siscu.mapper.ICargaMapper;
import com.cenpro.siscu.mapper.base.IMantenibleMapper;
import com.cenpro.siscu.model.carga.Cliente;
import com.cenpro.siscu.model.carga.Cliente.ClienteBuilder;
import com.cenpro.siscu.service.ICargaService;
import com.cenpro.siscu.service.excepcion.CargaArchivoException;
import com.cenpro.siscu.utilitario.ConstantesExcepciones;
import com.cenpro.siscu.utilitario.ConstantesFormatosExcelIngresante;
import com.cenpro.siscu.utilitario.ConstantesFormatosExcelRegular;

import com.cenpro.siscu.service.impl.MantenibleService;

@Service
public class CargaService extends MantenibleService<Cliente> implements ICargaService
{
	@SuppressWarnings("unused")
    private ICargaMapper cargaMapper;
    private static final String CARGAR = "CARGAR";
    
    public CargaService(@Qualifier("ICargaMapper") IMantenibleMapper<Cliente> mapper)
    {
        super(mapper);
        this.cargaMapper = (ICargaMapper) mapper;
    }

    public void cargarAlumnos(MultipartFile archivoAlumnos, String estamento)
    {
        List<Cliente> alumnos = new ArrayList<>();

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

            ClienteBuilder alumno = Cliente.builder();

            // CODIGO ALUMNO
            Cell codigoAlumno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ALUMNO);
            //System.out.println("codigo ALUMNO: " + codigoAlumno);
            if (codigoAlumno == null){
                finExcel = true;
                continue;
            }
            codigoAlumno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.codigoAlumno(codigoAlumno.getStringCellValue().trim());

            // TIPO ALUMNO
            alumno.tipoAlumno("R");

            // APELLIDO PATERNO
            Cell apellidoPaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_PATERNO);
            //System.out.println("apellidoPaterno: " + apellidoPaterno);
            apellidoPaterno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.apellidoPaterno(Optional.ofNullable(apellidoPaterno.getStringCellValue()).orElse("").trim());

            // APELLIDO MATERNO
            Cell apellidoMaterno = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_APELLIDO_MATERNO);
            //System.out.println("apellidoMaterno: " + apellidoMaterno);
            apellidoMaterno.setCellType(Cell.CELL_TYPE_STRING);
            alumno.apellidoMaterno(apellidoMaterno.getStringCellValue().trim());

            // NOMBRES
            Cell nombres = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NOMBRES);
            //System.out.println("nombres: " + nombres);
            nombres.setCellType(Cell.CELL_TYPE_STRING);
            alumno.nombres(nombres.getStringCellValue().trim());

            // SEXO
            Cell sexo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_SEXO);
            //System.out.println("sexo: " + sexo);
            sexo.setCellType(Cell.CELL_TYPE_STRING);
            String idSexo = sexo.getStringCellValue().trim();
            alumno.idSexo(idSexo.length() == 1 ? idSexo : "N");

            // FECHA NACIMIENTO
            Cell fechaNacimiento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_FECHA_NACIMIENTO);
            //System.out.println("fechaNacimiento: " + fechaNacimiento);
            try{
                alumno.fechaNacimiento(fechaNacimiento.getDateCellValue());
            } catch (Exception e){
                alumno.fechaNacimiento(null);
            }

            // TIPO DOCUMENTO
            Cell tipoDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TIPO_DOCUMENTO);
            //System.out.println("tipoDocumento: " + tipoDocumento);
            tipoDocumento.setCellType(Cell.CELL_TYPE_STRING);
            alumno.idTipoDocumento(tipoDocumento.getStringCellValue().trim());

            // NUMERO DOCUMENTO
            Cell numeroDocumento = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_NUMERO_DOCUMENTO);
            //System.out.println("numeroDocumento: " + numeroDocumento);
            numeroDocumento.setCellType(Cell.CELL_TYPE_STRING);
            alumno.numeroDocumento(numeroDocumento.getStringCellValue().trim());

            // CORREO PERSONAL
            Cell correoPersonal = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CORREO_PERSONAL);
            //System.out.println("correoPersonal: " + correoPersonal);
            correoPersonal.setCellType(Cell.CELL_TYPE_STRING);
            alumno.correo(correoPersonal.getStringCellValue().trim());

            // DIRECCION
            Cell direccion = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_DIRECCION);
            //System.out.println("direccion: " + direccion);
            direccion.setCellType(Cell.CELL_TYPE_STRING);
            alumno.direccion(direccion.getStringCellValue().trim());

            // TELEFONO FIJO
            Cell telefonoFijo = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_FIJO);
            //System.out.println("telefonoFijo: " + telefonoFijo);
            telefonoFijo.setCellType(Cell.CELL_TYPE_STRING);
            alumno.telefonoFijo(telefonoFijo.getStringCellValue().trim());

            // TELEFONO MOVIL
            Cell telefonoMovil = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_TELEFONO_MOVIL);
            //System.out.println("telefonoMovil: " + telefonoMovil);
            telefonoMovil.setCellType(Cell.CELL_TYPE_STRING);
            alumno.telefonoMovil(telefonoMovil.getStringCellValue().trim());

            // CODIGO ESCUELA
            Cell codigoEscuela = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_ESCUELA);
            //System.out.println("codigoEscuela: " + codigoEscuela);
            codigoEscuela.setCellType(Cell.CELL_TYPE_STRING);
            try{
                alumno.codigoEscuela(Integer.parseInt(codigoEscuela.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                alumno.codigoEscuela(0);
            }

            // CODIGO FACULTAD
            Cell codigoFacultad = row.getCell(ConstantesFormatosExcelRegular.COLUMNA_CODIGO_FACULTAD);
            //System.out.println("codigoFacultad: " + codigoFacultad);
            codigoFacultad.setCellType(Cell.CELL_TYPE_STRING);
            try{
                alumno.codigoFacultad(Integer.parseInt(codigoFacultad.getStringCellValue().trim()));
            } catch (Exception e){
                e.printStackTrace();
                alumno.codigoFacultad(0);
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
    public void registrarAlumnos(List<Cliente> pacientes)
    {
    	pacientes.stream().forEach(paciente -> this.registrar(paciente, CARGAR));
    }
}