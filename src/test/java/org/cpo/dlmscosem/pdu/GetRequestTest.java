package org.cpo.dlmscosem.pdu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.cpo.dlmscosem.DlmsData;
import org.cpo.dlmscosem.Obis;
import org.cpo.dlmscosem.Wrapper;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetRequestTest {
    @Test
    public void testGetRequestNormal() {
        Wrapper wrapper = new Wrapper();
        ByteArrayDataOutput dlmsFrame = ByteStreams.newDataOutput();
        new GetRequest(dlmsFrame).getNormalRequest(15, Obis.ASSOCIATION_OBJECT, 2);
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        wrapper.encode(buffer, 16, 1, dlmsFrame.toByteArray());
        log.info(BaseEncoding.base16().encode(buffer.toByteArray()));
        assertEquals("000100100001000DC001C1000F0000280000FF0200", BaseEncoding.base16().encode(buffer.toByteArray()));
    }

    @Test
    public void testGetResponseAttribute() {
        Wrapper wrapper = new Wrapper();
        ByteBuffer buffer = ByteBuffer.wrap(BaseEncoding.base16().decode("0001000100100009C401C100060000003E"));
        DlmsData data = new DlmsData();
        wrapper.decode(buffer, data);
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            log.info(mapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
