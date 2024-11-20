package org.cpo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.cpo.dlmscosem.DlmsData;
import org.cpo.dlmscosem.Obis;
import org.cpo.dlmscosem.Wrapper;
import org.cpo.dlmscosem.pdu.AssociationRequest;
import org.cpo.dlmscosem.pdu.AssociationRequest.ApplicationContext;
import org.cpo.dlmscosem.pdu.GetRequest;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {

    @Test
    public void testGetObjectList() {
        Wrapper wrapper = new Wrapper();
        ByteBuffer buffer = ByteBuffer.wrap(BaseEncoding.base16().decode(
                "0001000100100228C40141000109020412000F110109060000280000FF0202010902030F0116010002030F0216010002030F0316010002030F0416010002030F0516010002030F0616010002030F0716000002030F0816010002030F09160000010402020F01160002020F02160002020F03160002020F04160002041200011100090600002A0000FF0202010202030F0116010002030F0216010001000204120001110009060000600100FF0202010202030F0116010002030F02160100010002041200011100090600002B0102FF0202010202030F0116010002030F02160100010002041200011100090600002B0103FF0202010202030F0116010002030F02160100010002041200011100090600002B0104FF0202010202030F0116010002030F02160100010002041200011100090600002B0105FF0202010202030F0116010002030F0216010001000204120008110009060000010000FF0202010902030F0116010002030F0216010002030F0316010002030F0416010002030F0516010002030F0616010002030F0716010002030F0816010002030F09160100010602020F01160002020F02160002020F03160002020F04160002020F05160102020F061601020412000F110109060000280001FF0202010902030F0116010002030F0216010002030F0316010002030F0416010002030F0516010002030F0616010002030F0716000002030F0816010002030F09160000010402020F01160002020F02160002020F03160002020F041600"));
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