package clienttestapp.client;

import com.mycompany.clientapp.client.Proccess;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProccessTest {

    Socket socket;
    InputStream in;
    OutputStream out;
    Throwable error;
    OutputStream err;

    @Before
    public void setUp() {
        socket = mock(Socket.class);
        InputStream in = new ByteArrayInputStream("test_message".getBytes(StandardCharsets.UTF_8));
        out = new ByteArrayOutputStream();
        try {
            when(socket.getOutputStream()).thenReturn(out);
            when(socket.getInputStream()).thenReturn(in);
        } catch (IOException ex) {}
    }

    @After
    public void tearDown() throws IOException {
        error = null;
    }

    @Test(expected = NullPointerException.class)
    public void testRunWithNullSocket() {
        Proccess instance = new Proccess(null);
    }

    @Test
    public void testReturnNumberCorrect() {
        Integer number = (int) (Math.random() * Integer.MAX_VALUE);
        setConsoleMessage(number.toString());
        Proccess instance = new Proccess(socket);
        instance.setUncaughtExceptionHandler(new ProcessUncaughtExceptionHandler());
        try {
            instance.start();
            Thread.sleep(1000);
        } catch (Exception e) {}
        assertTrue(error instanceof NullPointerException);
        assertEquals(number.toString() + "\r\n", getOutputString());
    }

    @Test
    public void testExitMessage() {
        setConsoleMessage("exit");
        Proccess instance = new Proccess(socket);
        instance.setUncaughtExceptionHandler(new ProcessUncaughtExceptionHandler());
        try {
            instance.start();
            Thread.sleep(1000);
        } catch (Exception e) {}
        assertNull(error);
        assertTrue(instance.getState() == Thread.State.TERMINATED);
        assertEquals("", getOutputString());
    }

    @Test
    public void testIncorrectMessage() {
        setConsoleMessage("gdfgdgdg");
        setErrStream();
        Proccess instance = new Proccess(socket);
        instance.setUncaughtExceptionHandler(new ProcessUncaughtExceptionHandler());
        try {
            instance.start();
            Thread.sleep(1000);
        } catch (Exception e) {}
        assertTrue(error instanceof NullPointerException);
        assertEquals("Your number is not correct"  + "\r\n", getErrString());
    }

    /**
     * Mock user's console message
     *
     * @param message
     */
    private void setConsoleMessage(String message) {
        InputStream console = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
        System.setIn(console);
    }

    /**
     * Mock System.err stream
     */
    private void setErrStream() {
        err = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(err);
        System.setErr(ps);
    }

    /**
     * Mock server in stream
     *
     * @return server in string
     */
    private String getOutputString() {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        return result;
    }

    /**
     * Get mocked System.err as string
     *
     * @return err string
     */
    private String getErrString() {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) err;
        String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        return result;
    }

    private class ProcessUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            error = e;
        }

    }
}
