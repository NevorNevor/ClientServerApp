package servertestapp.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import servertestapp.logic.Factorization;

public class ProccessTest {

    Socket socket;
    InputStream in;
    OutputStream out;

    @Before
    public void setUp() {
        socket = mock(Socket.class);
        in = mock(InputStream.class);
        out = new ByteArrayOutputStream();
        try {
            when(socket.getOutputStream()).thenReturn(out);
        } catch (IOException ex) {}
    }

    @Test(expected = NullPointerException.class)
    public void testRunWithNullSocket() {
        Proccess instance = new Proccess(null);
    }

    @Test
    public void testRunCorrect() {
        Integer number = (int) (Math.random() * Integer.MAX_VALUE);
        setSotketInMessage(number.toString());
        Proccess instance = new Proccess(socket);   
        try{
            instance.start();
            Thread.sleep(1000);
        }catch(InterruptedException ie){}
        assertEquals(factory(number) + "\r\n", getOutputString());
    }

    private void setSotketInMessage(String message) {
        InputStream stream = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
        try {
            when(socket.getInputStream()).thenReturn(stream);
        } catch (IOException e) {
            fail("Test init error");
        }
    }
    
    private String getOutputString(){
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out; 
        String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        return result;
    }
    
    private String factory(int number){
        Factorization factor = new Factorization(number);
        return factor.factor();
    }

}
