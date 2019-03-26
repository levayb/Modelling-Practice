package com.codecool.greencommitment.api.server;

import com.codecool.greencommitment.api.common.Measurement;
import com.codecool.greencommitment.api.common.MeasurementType;
import com.codecool.greencommitment.api.server.listener.CountingServerListener;
import com.codecool.greencommitment.api.server.listener.NoopServerListener;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClientHandlerTest {

    /**
     * Wraps a stream and decorates it so that after a certain number of bytes read it'll throw an {@link IOException}.
     */
    static final class ThrowAfterCountInputStream extends FilterInputStream {

        private final int throwAfterCount;

        private int counter;

        ThrowAfterCountInputStream(InputStream in, int throwAfterCount) {
            super(in);
            this.throwAfterCount = throwAfterCount;
        }

        @Override
        public int read() throws IOException {
            if (counter >= throwAfterCount) {
                throw new IOException();
            }
            counter++;
            return super.read();
        }
    }

    @Test
    void runOkWhenStreamContainsMeasurement() {
        // given
        String expectedId = "123";
        MeasurementType expectedType = MeasurementType.TEMPERATURE;
        long expectedTime = System.currentTimeMillis();
        int expectedValue = 28;
        Measurement expectedMeasurement = new Measurement(expectedId, expectedType, expectedTime, expectedValue);

        // and
        InputStream is = getInputStream(expectedMeasurement.toDocument());
        ConcurrentMap<String, CopyOnWriteArrayList<Measurement>> data = new ConcurrentHashMap<>();
        CountingServerListener listener = new CountingServerListener();

        // and
        ClientHandler handler = new ClientHandler(is, data, listener);

        // when
        handler.run();

        // then
        assertServerCounterListenerSuccess(listener);

        // and
        assertFalse(data.isEmpty());

        // and
        List<Measurement> measurements = data.get(expectedId);
        assertNotNull(measurements);
        assertFalse(measurements.isEmpty());
        assertEquals(1, measurements.size());

        // and
        Measurement actualMeasurement = measurements.get(0);
        assertEquals(expectedMeasurement, actualMeasurement);
    }

    @Test
    void runFailWhenErrorWhileStreaming() {
        // given
        Measurement expectedMeasurement = new Measurement("123", MeasurementType.TEMPERATURE, System.currentTimeMillis(), 33);

        // and
        InputStream is = new ThrowAfterCountInputStream(getInputStream(expectedMeasurement.toDocument()), 100);
        ConcurrentMap<String, CopyOnWriteArrayList<Measurement>> data = new ConcurrentHashMap<>();
        CountingServerListener listener = new CountingServerListener();

        // and
        ClientHandler handler = new ClientHandler(is, data, listener);

        // when
        handler.run();

        // then
        assertServerCounterListenerFail(listener);

        // and
        assertTrue(data.isEmpty());
    }

    @Test
    void runFailWhenStreamContainsNothing() {
        // given
        InputStream is = new ByteArrayInputStream(new byte[0]);
        ConcurrentHashMap<String, CopyOnWriteArrayList<Measurement>> data = new ConcurrentHashMap<>();
        CountingServerListener listener = new CountingServerListener();

        // and
        ClientHandler handler = new ClientHandler(is, data, listener);

        // when
        handler.run();

        // then
        assertServerCounterListenerFail(listener);

        // and
        assertTrue(data.isEmpty());
    }

    @Test
    void failWhenStreamContainsIncorrectObjects() {
        // given
        InputStream is = getInputStream("This isn't what the handler expects.");

        //and
        ClientHandler handler = new ClientHandler(is, new ConcurrentHashMap<>(), new NoopServerListener());

        // when/then
        assertThrows(ClassCastException.class, handler::run);
    }

    private void assertServerCounterListenerSuccess(CountingServerListener listener) {
        assertServerCounterListener(listener);
        assertEquals(1, listener.getClientReceiveSuccessCounter());
        assertEquals(0, listener.getClientReceiveErrorCounter());
    }

    private void assertServerCounterListenerFail(CountingServerListener listener) {
        assertServerCounterListener(listener);
        assertEquals(0, listener.getClientReceiveSuccessCounter());
        assertEquals(1, listener.getClientReceiveErrorCounter());
    }

    private void assertServerCounterListener(CountingServerListener listener) {
        assertEquals(0, listener.getClientAccessSuccessCounter());
        assertEquals(0, listener.getClientAcceptErrorCounter());
    }

    /**
     * Creates a mock stream that'll be consumed by the handler.
     *
     * @param obj the object that will be written to the stream
     * @return the stream with {@code obj} serialized into it
     */
    private InputStream getInputStream(Object obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
