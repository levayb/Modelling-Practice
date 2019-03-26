package com.codecool.greencommitment.api.client;

import com.codecool.greencommitment.api.common.DOMUtil;
import com.codecool.greencommitment.api.common.MeasurementType;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractClientTest {

    @Test
    void sendOkWhenStreamingMeasurement() throws Exception {
        // given
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // and
        String id = "123";
        MeasurementType measurementType = MeasurementType.TEMPERATURE;
        int lowerBound = 10;
        int upperBound = 13;

        // and
        Client client = new AbstractClient(id, measurementType, lowerBound, upperBound) {
            @Override
            protected OutputStream getOutputStream() {
                return baos;
            }
        };

        // when
        client.send();

        // then
        Element el = getElement(baos);
        assertEquals(id, el.getAttribute("id"));

        // and
        String time = DOMUtil.getSingleElementOf(el, "time").getTextContent();
        assertNotNull(time);
        assertNotEquals("", time);

        // and
        String type = DOMUtil.getSingleElementOf(el, "type").getTextContent();
        assertNotNull(type);
        assertEquals(measurementType.name(), type);

        // and
        String value = DOMUtil.getSingleElementOf(el, "value").getTextContent();
        assertNotNull(value);
        assertTrue(List.of("10", "11", "12", "13").contains(value));
    }

    private Element getElement(ByteArrayOutputStream baos) throws IOException, ClassNotFoundException {
        byte[] bytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Document document = (Document) ois.readObject();
        return document.getDocumentElement();
    }
}
