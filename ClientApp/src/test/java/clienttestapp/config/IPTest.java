package clienttestapp.config;

import com.mycompany.clientapp.config.IP;
import org.junit.Test;
import static org.junit.Assert.*;

public class IPTest {
    
    public IPTest() {
    }

    @Test
    public void testValidateCorrectIP() {
        String ip = "192.168.0.1";
        boolean expResult = true;
        boolean result = IP.validate(ip);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateInorrectIP() {
        String ip = "256.195.168.1";
        boolean expResult = false;
        boolean result = IP.validate(ip);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateNullIP() {
        String ip = null;
        boolean expResult = false;
        boolean result = IP.validate(ip);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testValidateEmptyIP() {
        String ip = "";
        boolean expResult = false;
        boolean result = IP.validate(ip);
        assertEquals(expResult, result);
    }
    
}
