package org.cpo.dlmscosem.pdu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.cpo.dlmscosem.enums.ApplicationContext;
import org.junit.jupiter.api.Test;

import com.google.common.io.BaseEncoding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationRequestTest {

    @Test
    void testAssocationRequest() {
        var dlmsFrame = new AssociationRequest().getRequest(ApplicationContext.LN_NO_CIPHERING);
        assertEquals("601DA109060760857405080101BE10040E01000000065F1F040060FEDFFFFF",
                BaseEncoding.base16().encode(dlmsFrame.toByteArray()));
    }

}
