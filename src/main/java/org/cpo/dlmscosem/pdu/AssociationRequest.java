package org.cpo.dlmscosem.pdu;

import java.util.ArrayList;
import java.util.List;

import org.cpo.dlmscosem.CosemPdu;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataOutput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationRequest {
    public enum ConformanceBit {
        GENERAL_PROTECTION(0x000002),
        GENERAL_BLOCK_TRANSFER(0x000004),
        DELTA_VALUE_ENCODING(0x000008),
        ATTR0_SET(0x000020),
        PRIORITY_MANAGEMENT(0x000040),
        ATTR0_GET(0x000080),
        BLOCK_TRANSFER_WITH_GET(0x000100),
        BLOCK_TRANSFER_WITH_SET(0x000200),
        BLOCK_TRANSFER_WITH_ACTION(0x000400),
        MULTIPLE_REFERENCES(0x000800),
        DATA_NOTIFICATION(0x001000),
        ACCESS(0x002000),
        GET(0x004000),
        SET(0x008000),
        SELECTIVE_ACCESS(0x010000),
        EVENT_NOTIFICATION(0x020000),
        ACTION(0x040000);

        int value;

        ConformanceBit(int value) {
            this.value = value;
        }

        public static String getCapabilities(int bits) {
            List<String> list = new ArrayList<>();
            for (var cb : values()) {
                if ((cb.value & bits) > 0) {
                    list.add(cb.name());
                }
            }
            return String.join(", ", list);
        }
    }

    public enum ApplicationContext {
        LN_NO_CIPHERING("2.16.756.5.8.1.1"),
        LN_LLS("2.16.756.5.8.1.2"),
        LN_HLS("2.16.756.5.8.1.3"),
        SN_NO_CIPHERING("2.16.756.5.8.2.1"),
        SN_LLS("2.16.756.5.8.2.2"),
        SN_HLS("2.16.756.5.8.2.3");

        String oid;

        ApplicationContext(String oid) {
            this.oid = oid;
        }

        public String getOid() {
            return oid;
        }
    }

    public byte[] encodeApplicationContext(String oid) {
        // Convert OID string to integers
        String[] oidParts = oid.split("\\.");
        int[] oidInts = new int[oidParts.length];
        for (int i = 0; i < oidParts.length; i++) {
            oidInts[i] = Integer.parseInt(oidParts[i]);
        }

        // Encode OID using ITU-T X.690 rules
        List<Byte> encodedOID = new ArrayList<>();
        encodedOID.add((byte) (40 * oidInts[0] + oidInts[1])); // Encode first two parts (e.g., 2.16)
        for (int i = 2; i < oidInts.length; i++) {
            int value = oidInts[i];
            List<Byte> subidentifier = new ArrayList<>();
            do {
                subidentifier.add(0, (byte) (value & 0x7F | 0x80));
                value >>= 7;
            } while (value > 0);
            Byte lastByte = subidentifier.get(subidentifier.size() - 1); // Clear the MSB of the last byte
            subidentifier.set(subidentifier.size() - 1, (byte) (lastByte & 0x7f));
            encodedOID.addAll(subidentifier);
        }

        // Convert to byte array
        byte[] oidBytes = new byte[encodedOID.size() + 2];
        oidBytes[0] = 6; // Universal tag for OID
        oidBytes[1] = (byte) encodedOID.size(); // Size of the OID
        for (int i = 0; i < encodedOID.size(); i++) {
            oidBytes[i + 2] = encodedOID.get(i);
        }

        return oidBytes;
    }

    public AssociationRequest(ByteArrayDataOutput buffer, ApplicationContext applicationContext) {
        this(buffer, applicationContext.getOid());
    }

    public AssociationRequest(ByteArrayDataOutput buffer, String applicationContext) {
        buffer.write(CosemPdu.ASSOCIATION_REQUEST.code);
        buffer.write(0x1d);
        // Application Context Name
        byte[] appContext = encodeApplicationContext(applicationContext);
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
