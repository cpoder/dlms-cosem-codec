package org.cpo.dlmscosem.pdu;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
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
            var bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            log.info("Will process bytes: {}", BaseEncoding.base16().encode(bytes));
            var asn1 = ASN1Primitive.fromByteArray(bytes);
            log.info("{}: {}", asn1.getClass(), asn1);
            ASN1TaggedObject taggedObject = ASN1TaggedObject.getInstance(asn1);
            ASN1Sequence sequence = ASN1Sequence.getInstance(taggedObject.getBaseObject());
            log.info("{}", sequence.size());
            for (int i = 0; i < sequence.size(); i++) {
                var context = ASN1TaggedObject.getInstance(sequence.getObjectAt(i));
                int tag = context.getTagNo();
                int tagClass = context.getTagClass();
                var obj = context.getBaseObject();
                log.info("{} {} {}", tagClass, tag, obj.getClass());
                switch (tag) {
                    case 1:
                        var oid = ASN1ObjectIdentifier.getInstance(obj);
                        applicationContext = ApplicationContext.from(oid.getId());
                        log.info("Application context is: {}", applicationContext);
                        break;
                    case 2:
                        var result = ASN1Integer.getInstance(obj);
                        associationResult = result.getValue().intValue() == 0;
                        log.info("Association result is: {}", associationResult);
                        break;
                    case 3:
                        break;
                    case 9:
                        break;
                    case 30:
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
