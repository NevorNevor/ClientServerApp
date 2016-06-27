package servertestapp.logic;

import org.junit.Test;
import static org.junit.Assert.*;

public class FactorizationTest {
    
    public FactorizationTest() {
    }

    @Test
    public void testFactorMaxValue() {
        Factorization instance = new Factorization(Integer.MAX_VALUE);
        String expResult = String.valueOf(Integer.MAX_VALUE);
        String result = instance.factor();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFactorMaxZero() {
        Factorization instance = new Factorization(0);
        String expResult = String.valueOf(0);
        String result = instance.factor();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFactorLessZero() {
        Factorization instance = new Factorization(-1);
        String expResult = String.valueOf(-1);
        String result = instance.factor();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testFactorCorrect() {
        Factorization instance = new Factorization(4);
        String expResult = "2 * 2";
        String result = instance.factor();
        assertEquals(expResult, result);
        instance = new Factorization(200);
        expResult = "2 * 2 * 2 * 5 * 5";
        result = instance.factor();
        assertEquals(expResult, result);
    }
}
