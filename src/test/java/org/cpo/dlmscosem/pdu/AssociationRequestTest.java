package org.cpo.dlmscosem.pdu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.cpo.dlmscosem.cosem.pdu.AssociationRequest;
import org.cpo.dlmscosem.enums.ApplicationContext;
import org.cpo.dlmscosem.enums.ConformanceBit;
import org.junit.jupiter.api.Test;

import com.google.common.io.BaseEncoding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationRequestTest {

    @Test
    void testAssocationRequest() {
        var dlmsFrame = new AssociationRequest().getRequest(ApplicationContext.LN_NO_CIPHERING,
                List.of(ConformanceBit.GET, ConformanceBit.SET, ConformanceBit.GENERAL_BLOCK_TRANSFER,
                        ConformanceBit.GENERAL_PROTECTION, ConformanceBit.ACTION));
        assertEquals("601DA109060760857405080101BE10040E01000000065F1F0400600019FFFF",
                BaseEncoding.base16().encode(dlmsFrame.toByteArray()));
    }

}
