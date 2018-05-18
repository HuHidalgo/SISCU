package securityTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cenpro.siscu.configuracion.PersistenceConfiguration;

@ContextConfiguration(classes = {PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordEncoderTest
{

    @Test
    public void encodePasswordTest()
    {
        String password = "CERME";
//        BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEnconder.encode(password);
        System.out.println("HOLA");
    }

    //@Test
    public void matchPasswordTest()
    {
        String hashedPassword = "$2a$10$yuRs/KLQhzbZsmN71EIhq.wuo1ZrhYfnklKOI9I1upAWB6kTSu4Ky";
        BCryptPasswordEncoder passwordEnconder = new BCryptPasswordEncoder();
        boolean passwordMatches = passwordEnconder.matches("SIMP", hashedPassword);
        System.out.println("Passwords iguales: " + passwordMatches);
    }

}
