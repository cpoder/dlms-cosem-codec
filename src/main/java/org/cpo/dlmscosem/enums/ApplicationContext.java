package org.cpo.dlmscosem.enums;

import java.util.ArrayList;
import java.util.List;

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

    public static ApplicationContext from(String oid) {
        for (var v : values()) {
            if (v.oid.equals(oid)) {
                return v;
            }
        }
        return null;
    }

    public byte[] encode() {
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

    public static ApplicationContext decode(byte[] bytes) {
        if (bytes[0] == 6 && bytes[1] == bytes.length - 2) {
            String oid = (bytes[2] / 40) + "." + (bytes[2] % 40);

            int value = 0;
            for (int i = 3; i < bytes.length; i++) {
                int current = bytes[i] & 0xFF;
                value = (value << 7) | (current & 0x7F); // Combine 7 bits
                if ((current & 0x80) == 0) { // MSB is 0 -> end of component
                    oid += "." + value;
                    value = 0;
                }
            }

            return ApplicationContext.from(oid);
        } else {
            return null;
        }
    }
}