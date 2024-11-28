package org.cpo.dlmscosem.enums;

import java.util.ArrayList;
import java.util.List;

public enum ConformanceBit {
    GENERAL_PROTECTION(1),
    GENERAL_BLOCK_TRANSFER(2),
    READ(3),
    WRITE(4),
    UNCONFIRMED_WRITE(5),
    ATTR0_SET(8),
    PRIORITY_MANAGEMENT(9),
    ATTR0_GET(10),
    BLOCK_TRANSFER_WITH_GET_OR_READ(11),
    BLOCK_TRANSFER_WITH_SET_OR_WRITE(12),
    BLOCK_TRANSFER_WITH_ACTION(13),
    MULTIPLE_REFERENCES(14),
    INFORMATION_REPORT(15),
    DATA_NOTIFICATION(16),
    ACCESS(17),
    PARAMETERIZED_ACCESS(18),
    GET(19),
    SET(20),
    SELECTIVE_ACCESS(21),
    EVENT_NOTIFICATION(22),
    ACTION(23);

    int value;

    ConformanceBit(int value) {
        this.value = value;
    }

    public static List<ConformanceBit> getCapabilities(long bits) {
        List<ConformanceBit> list = new ArrayList<>();
        for (var cb : values()) {
            // Beware! bits are in reversed order
            long bit = Integer.toUnsignedLong(1 << (23 - cb.value));
            if ((bit & bits) > 0) {
                list.add(cb);
            }
        }
        return list;
    }

    public static int getConformanceBits(List<ConformanceBit> conformanceBits) {
        int result = 0;

        for (ConformanceBit conformanceBit : conformanceBits) {
            result |= (1 << (23 - conformanceBit.value));
        }

        return result;
    }
}