package serviceTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cenpro.siscu.configuracion.PersistenceConfiguration;
import com.cenpro.siscu.configuracion.ServiceConfiguration;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProcesoTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
