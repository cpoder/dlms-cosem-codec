package org.cpo.dlmscosem.pdu;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.bouncycastle.asn1.ASN1Primitive;
import org.cpo.dlmscosem.enums.ApplicationContext;

import com.google.common.io.BaseEncoding;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AssociationResponse {
    private ApplicationContext applicationContext;
    private boolean associationResult;

    public AssociationResponse(ByteBuffer buffer) {
        byte totalLength = buffer.get();
        log.info("Total association response length is {} bytes", totalLength);
        try {
            buffer.rewind();
            // buffer.getLong();
            var bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            log.info("Will process bytes: {}", BaseEncoding.base16().encode(bytes));
            var asn1 = ASN1Primitive.fromByteArray(bytes);
            log.info("{}: {}", asn1.getClass(), asn1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
