package org.cpo.dlmscosem.pdu;

import org.cpo.dlmscosem.CosemPdu;
import org.cpo.dlmscosem.enums.ApplicationContext;
import org.cpo.dlmscosem.enums.ConformanceBit;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataOutput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationRequest {
    public AssociationRequest(ByteArrayDataOutput buffer, String applicationContext) {
        this(buffer, ApplicationContext.from(applicationContext));
    }

    public AssociationRequest(ByteArrayDataOutput buffer, ApplicationContext applicationContext) {
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
        buffer.writeInt(0x0060FEDF);
        log.info(ConformanceBit.getCapabilities(0x0060FEDF));
        // Max PDU size
        buffer.write(0xff);
        buffer.write(0xff);
    }
}
