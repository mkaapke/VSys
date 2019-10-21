package Test;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RunWith(JUnitParamsRunner.class)
public abstract class BidiNetcatTest {

    public static Object[] inputs() {
        return new Object[] {
                new Object[]{"Hallo\n"},
                new Object[]{"Test Test\n"}
        };
    }

    protected abstract Thread createClient(ByteArrayInputStream inputStream);

    protected abstract Thread createServer(PrintStream outputStream);

    @Test
    @Parameters
    public void test(String input) throws InterruptedException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(byteOutput);

        Thread serverThread = createServer(outputStream);
        Thread clientThread = createClient(inputStream);

        serverThread.start();
        Thread.sleep(1000);

        clientThread.start();
        clientThread.join();
        serverThread.join();

    }
}
