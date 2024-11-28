package org.cpo.dlmscosem.cosem.pdu;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DLTaggedObject;
import org.cpo.dlmscosem.enums.ConformanceBit;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class InitiateResponse {
    private int protocolVersion;
    private List<ConformanceBit> conformanceBits;
    private int pduSize;
    private int vaaName;

    public InitiateResponse(ByteBuffer buffer) {
        buffer.get(); // negotiated qos, always 0
        protocolVersion = buffer.get();
        // buffer.getShort(); // Conformance tag = 5f1f, application 31
        // buffer.get(); // Size = 4
        byte[] bytes = new byte[7];
        buffer.get(bytes);
        try {
            DLTaggedObject conformance = (DLTaggedObject) ASN1Primitive.fromByteArray(bytes);
            // Extract the raw bytes (interpreting as BIT STRING content)
            ASN1OctetString octets = (ASN1OctetString) conformance.getBaseObject();
            byte[] rawBitString = octets.getOctets();
            conformanceBits = ConformanceBit.getCapabilities(
                    Integer.toUnsignedLong(ByteBuffer.wrap(rawBitString).getInt()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pduSize = buffer.getShort();
        vaaName = buffer.getShort();
    }
}
