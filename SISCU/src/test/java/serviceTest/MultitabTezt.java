package serviceTest;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cenpro.siscu.configuracion.PersistenceConfiguration;
import com.cenpro.siscu.configuracion.ServiceConfiguration;
import com.cenpro.siscu.model.mantenimiento.MultiTabCab;
import com.cenpro.siscu.service.IMultiTabCabService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class MultitabTezt
{
    private @Autowired IMultiTabCabService zervice;
    
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test()
    {
        MultiTabCab m = MultiTabCab.builder().descripcion("HOLA").build();
        zervice.registrar(m);
        fail("Not yet implemented");
    }

}
