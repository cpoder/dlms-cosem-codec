package org.cpo.dlmscosem.pdu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.cpo.dlmscosem.Wrapper;
import org.junit.jupiter.api.Test;

import com.google.common.io.BaseEncoding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationResponseTest {
    @Test
    void testAssociationResponse() {
        Wrapper wrapper = new Wrapper();
        ByteBuffer associationResponse = ByteBuffer.wrap(BaseEncoding.base16().decode(
                "00010001001000346132A109060760857405080101A203020100A305A103020100890760857405080200BE10040E0800065F1F0400601A9D01000007"));
        var data = wrapper.decode(associationResponse);
        assertEquals(AssociationResponse.class, data.getValue().getClass());
    }
}
