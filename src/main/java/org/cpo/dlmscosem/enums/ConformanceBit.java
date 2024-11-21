package org.cpo.dlmscosem.enums;

import java.util.ArrayList;
import java.util.List;

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