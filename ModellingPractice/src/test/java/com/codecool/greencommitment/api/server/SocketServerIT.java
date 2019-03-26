package com.codecool.greencommitment.api.server;

import com.codecool.greencommitment.api.client.Client;
import com.codecool.greencommitment.api.client.SocketClient;
import com.codecool.greencommitment.api.common.Measurement;
import com.codecool.greencommitment.api.common.MeasurementType;
import com.codecool.greencommitment.api.server.exception.ServerNotRunningException;
import com.codecool.greencommitment.api.server.listener.NoopServerListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SocketServerIT {

    private static final String HOST = "localhost";
    private static final int PORT = 9999;
    private static final String FIRST_CLIENT_ID = "first";
    private static final String SECOND_CLIENT_ID = "second";

    private Server server;

    @AfterEach
    void afterEach() throws Exception {
        if (server.isRunning()) {
            server.stop();
        }

        sleep();
    }

    @Test
    void stopFailWhenServerNotRunning() {
        // given
        server = new SocketServer(PORT, new NoopServerListener());

        // and
        assertFalse(server.isRunning());

        // when
        assertThrows(ServerNotRunningException.class, server::stop);

        // and
        assertFalse(server.isRunning());
    }

    @Test
    void startOkWhenServerNotRunning() throws Exception {
        // given
        server = new SocketServer(PORT, new NoopServerListener());

        // and
        assertFalse(server.isRunning());

        // when
        server.start();

        // and
        sleep();

        // then
        assertTrue(server.isRunning());
    }

    @Test
    void okWhenMultipleClientSends() throws Exception {
        // given
        server = new SocketServer(PORT, new NoopServerListener());

        // and
        server.start();

        // and
        Client client1 = createClient1();
        Client client2 = createClient2();

        // when
        client1.send();
        client2.send();
        client1.send();
        client2.send();
        client2.send();

        // and
        sleep();

        // then
        Map<String, List<Measurement>> data = server.getData();
        assertNotNull(data);
        assertEquals(2, data.size());

        // and
        List<Measurement> firstClientMeasurements = data.get(FIRST_CLIENT_ID);
        assertEquals(2, firstClientMeasurements.size());

        // and
        List<Measurement> secondClientMeasurements = data.get(SECOND_CLIENT_ID);
        assertEquals(3, secondClientMeasurements.size());
    }

    private SocketClient createClient1() {
        return new SocketClient(
            FIRST_CLIENT_ID,
            "localhost",
            PORT,
            MeasurementType.TEMPERATURE,
            20,
            25);
    }

    private SocketClient createClient2() {
        return new SocketClient(
            SECOND_CLIENT_ID,
            HOST,
            PORT,
            MeasurementType.DISTANCE,
            50,
            100);

    }

    private void sleep() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100L);
    }
}
