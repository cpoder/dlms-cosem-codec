package org.cpo.dlmscosem.cosem.pdu;

import java.util.List;

import org.cpo.dlmscosem.cosem.CosemPdu;
import org.cpo.dlmscosem.enums.ApplicationContext;
import org.cpo.dlmscosem.enums.ConformanceBit;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationRequest {
    public ByteArrayDataOutput getRequest(String applicationContext, List<ConformanceBit> conformanceBits) {
        return getRequest(ApplicationContext.from(applicationContext), conformanceBits);
    }

    public ByteArrayDataOutput getRequest(ApplicationContext applicationContext, List<ConformanceBit> conformanceBits) {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.write(CosemPdu.ASSOCIATION_REQUEST.code);
        buffer.write(0x1d);
        // Application Context Name
        byte[] appContext = applicationContext.encode();
        buffer.write((byte) 0xA1); // Application context tag
        log.info("Application context is: {}", BaseEncoding.base16().encode(appContext));
        buffer.write((byte) appContext.length); // Length of the context
        buffer.write(appContext);

        buffer.write(0xbe); // User information field tag
        buffer.write(0x10); // length
        buffer.write(0x04); // initiate request tag
        buffer.write(0x0e); // length
        // Reserved bytes
        buffer.write(0x01);
        buffer.write(0);
        buffer.write(0);
        buffer.write(0);
        // DLMS version (6)
        buffer.write(6);
        // Conformance tag
        buffer.write(0x5f);
        buffer.write(0x1f);
        // Length
        buffer.write(4);
        // Proposed conformance bits (supported features)
        int conformance = ConformanceBit.getConformanceBits(conformanceBits);
        buffer.writeInt(conformance);
        log.info(ConformanceBit.getCapabilities(conformance).toString());
        // Max PDU size
        buffer.write(0xff);
        buffer.write(0xff);

        return buffer;
    }
}
