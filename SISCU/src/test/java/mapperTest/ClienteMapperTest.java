package mapperTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cenpro.siscu.configuracion.PersistenceConfiguration;
import com.cenpro.siscu.model.criterio.CriterioBusquedaReporteAtencionDiaria;

@ContextConfiguration(classes = { PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ClienteMapperTest
{
   

    @Test
    public void prueba()
    {
        LocalDate inicio = LocalDate.of(2018, 01, 01);
        LocalDate fin = LocalDate.of(2018, 03, 03);
        Date fechaInicio = Date.from(inicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CriterioBusquedaReporteAtencionDiaria c = CriterioBusquedaReporteAtencionDiaria.builder()
                .idTipoExamenMedico("L").fechaInicio(fechaInicio).fechaFin(fechaFin).idCampania(1)
                .build();

//        List<ReporteAtencionDiaria> reportez = reporteMapper.buscarAtencioDiarioDetalle(c);
//        System.out.println(reportez);
        
    }
}