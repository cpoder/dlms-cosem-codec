package org.cpo.dlmscosem;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.common.io.BaseEncoding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum DataType {
    NULL_DATA((byte) 0) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            log.info("NULL_DATA");
            data.setDataType(NULL_DATA);
            data.setValue("NULL");
        }
    },
    ARRAY((byte) 1) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            byte size = buffer.get();
            log.info("ARRAY of {} elements", size);
            data.setDataType(ARRAY);
            data.setValue(size);
            data.setData(new LinkedList<>());
            for (int i = 0; i < size; i++) {
                DlmsData child = new DlmsData();
                DataType.valueOf(buffer.get()).process(buffer, child);
                data.getData().add(child);
            }
        }
    },
    STRUCTURE((byte) 2) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            byte size = buffer.get();
            log.info("STRUCTURE of {} elements", size);
            data.setDataType(STRUCTURE);
            data.setValue(size);
            data.setData(new LinkedList<>());
            for (int i = 0; i < size; i++) {
                DlmsData child = new DlmsData();
                DataType.valueOf(buffer.get()).process(buffer, child);
                data.getData().add(child);
            }
        }
    },
    BOOL((byte) 3) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            byte value = buffer.get();
            Boolean bool = false;
            if (value == 0x00) {
                bool = false;
            } else if (value == 0x01) {
                bool = true;
            } else {
                throw new IllegalArgumentException(
                        "DLMS BOOL type can only be 0x00 or 0x01, " + value + " is an illegal value");
            }

            data.setDataType(BOOL);
            data.setValue(bool);
            log.info("BOOL: {}", bool);
        }
    },
    BIT_STRING((byte) 4) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            // The first byte indicates the number of valid bits
            int bitLength = Byte.toUnsignedInt(buffer.get());

            // Calculate the number of bytes required for the bit data
            int byteLength = (bitLength + 7) / 8; // Round up to the nearest byte

            // Extract the bit data
            byte[] bitData = new byte[byteLength];
            buffer.get(bitData);

            // Use BitSet to interpret the bits
            BitSet bitSet = BitSet.valueOf(bitData);

            // Build a string representation of the bits
            StringBuilder bitString = new StringBuilder();
            for (int i = 0; i < bitLength; i++) {
                bitString.append(bitSet.get(i) ? "1" : "0");
            }

            String value = bitString.toString();
            data.setDataType(BIT_STRING);
            data.setValue(value);
            log.info("BIT_STRING: {}", value);
        }
    },
    DOUBLE_LONG((byte) 5) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int value = buffer.getInt();
            data.setDataType(DOUBLE_LONG);
            data.setValue(value);
            log.info("DOUBLE_LONG: {}", value);
        }
    },
    DOUBLE_LONG_UNSIGNED((byte) 6) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            long value = Integer.toUnsignedLong(buffer.getInt());
            data.setDataType(DOUBLE_LONG_UNSIGNED);
            data.setValue(value);
            log.info("DOUBLE_LONG_UNSIGNED: {}", value);
        }
    },
    OCTET_STRING((byte) 9) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int size = buffer.get();
            byte[] value = new byte[size];
            buffer.get(value);
            data.setDataType(OCTET_STRING);
            data.setValue(new String(value));
            log.info("OCTET_STRING: {} -> {}", BaseEncoding.base16().encode(value), new String(value));
        }
    },
    VISIBLE_STRING((byte) 10) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            // Read the length of the string
            int length = Byte.toUnsignedInt(buffer.get());

            // Validate that the buffer has enough bytes for the string
            if (buffer.remaining() < length) {
                throw new IllegalArgumentException("ByteBuffer does not contain enough bytes for the Visible-String.");
            }

            // Extract the string bytes
            byte[] stringBytes = new byte[length];
            buffer.get(stringBytes);

            // Convert bytes to a string using ASCII encoding
            String value = new String(stringBytes, StandardCharsets.US_ASCII);
            data.setDataType(VISIBLE_STRING);
            data.setValue(value);
            log.info("VISIBLE_STRING: {}", value);
        }
    },
    UTF8_STRING((byte) 12) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            // Read the length of the string
            int length = Byte.toUnsignedInt(buffer.get());

            // Validate that the buffer has enough bytes for the string
            if (buffer.remaining() < length) {
                throw new IllegalArgumentException("ByteBuffer does not contain enough bytes for the UTF8-String.");
            }

            // Extract the string bytes
            byte[] stringBytes = new byte[length];
            buffer.get(stringBytes);

            // Convert bytes to a string using UTF8 encoding
            String value = new String(stringBytes, StandardCharsets.UTF_8);
            data.setDataType(VISIBLE_STRING);
            data.setValue(value);
            log.info("UTF8_STRING: {}", value);
        }
    },
    BCD((byte) 13) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            byte bcdByte = buffer.get();
            // Extract the high nibble (first digit) and low nibble (second digit)
            int highNibble = (bcdByte >> 4) & 0x0F;
            int lowNibble = bcdByte & 0x0F;
            data.setDataType(BCD);
            data.setValue(highNibble * 10 + lowNibble);
            log.info("BCD: {}{}", highNibble, lowNibble);
        }
    },
    INTEGER((byte) 15) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int value = buffer.get();
            data.setDataType(INTEGER);
            data.setValue(value);
            log.info("INTEGER: {}", value);
        }
    },
    LONG_INTEGER((byte) 16) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int value = buffer.getShort();
            data.setDataType(LONG_INTEGER);
            data.setValue(value);
            log.info("LONG_INTEGER: {}", value);
        }
    },
    UNSIGNED((byte) 17) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int value = Byte.toUnsignedInt(buffer.get());
            data.setDataType(UNSIGNED);
            data.setValue(value);
            log.info("UNSIGNED: {}", value);
        }
    },
    LONG_UNSIGNED((byte) 18) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int value = Short.toUnsignedInt(buffer.getShort());
            data.setDataType(LONG_UNSIGNED);
            data.setValue(value);
            log.info("LONG_UNSIGNED: {}", value);
        }
    },
    COMPACT_ARRAY((byte) 19) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int numElements = Byte.toUnsignedInt(buffer.get());
            byte[] numbers = new byte[numElements];
            buffer.get(numbers);
            data.setDataType(COMPACT_ARRAY);
            data.setValue(numbers);
            log.info("COMPACT_ARRAY: {}", numbers);
        }
    },
    LONG64((byte) 20) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            long value = buffer.getLong();
            data.setDataType(LONG64);
            data.setValue(value);
            log.info("LONG64: {}", value);
        }
    },
    LONG64_UNSIGNED((byte) 21) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            // Initialize a BigDecimal to hold the final value
            BigDecimal value = BigDecimal.ZERO;

            // Iterate over each byte in the 8-byte array and build the BigDecimal
            for (int i = 0; i < 8; i++) {
                byte bcdByte = buffer.get();
                // Shift the byte into the appropriate position in the 64-bit unsigned number
                value = value.add(
                        BigDecimal.valueOf(Byte.toUnsignedInt(bcdByte)).multiply(BigDecimal.valueOf(256L).pow(7 - i)));
            }
            data.setDataType(LONG64_UNSIGNED);
            data.setValue(value);
            log.info("LONG64_UNSIGNED: {}", value);
        }
    },
    ENUMERATE((byte) 22) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            byte enumValue = buffer.get();
            data.setDataType(ENUMERATE);
            data.setValue(enumValue);
            log.info("ENUMERATE: {}", enumValue);
        }
    },
    FLOAT32((byte) 23) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            float enumValue = buffer.getFloat();
            data.setDataType(FLOAT32);
            data.setValue(enumValue);
            log.info("FLOAT32: {}", enumValue);
        }
    },
    FLOAT64((byte) 24) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            double enumValue = buffer.getDouble();
            data.setDataType(FLOAT32);
            data.setValue(enumValue);
            log.info("FLOAT64: {}", enumValue);
        }
    },
    DATE_TIME((byte) 25) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int year = Short.toUnsignedInt(buffer.getShort()); // Bytes 0-1
            int month = Byte.toUnsignedInt(buffer.get()); // Byte 2
            int day = Byte.toUnsignedInt(buffer.get()); // Byte 3
            Byte.toUnsignedInt(buffer.get()); // dayOfWeek Byte 4 (ignored here)
            int hour = Byte.toUnsignedInt(buffer.get()); // Byte 5
            int minute = Byte.toUnsignedInt(buffer.get()); // Byte 6
            int second = Byte.toUnsignedInt(buffer.get()); // Byte 7
            int deviation = buffer.getShort(); // Bytes 10-11
            Byte.toUnsignedInt(buffer.get()); // clockStatus Byte 12 (ignored here)

            // Handle "not specified" fields
            year = (year == 0xFFFF) ? 1970 : year; // Default to Unix epoch year if unspecified
            month = (month == 0xFF) ? 1 : month; // Default to January if unspecified
            day = (day == 0xFF) ? 1 : day; // Default to the first of the month if unspecified
            hour = (hour == 0xFF) ? 0 : hour; // Default to midnight if unspecified
            minute = (minute == 0xFF) ? 0 : minute;
            second = (second == 0xFF) ? 0 : second;

            // Handle "not specified" deviation
            ZoneOffset offset = (deviation == (short) 0x8000) ? ZoneOffset.UTC
                    : ZoneOffset.ofTotalSeconds(deviation * 60);

            // Build OffsetDateTime
            var dateTime = OffsetDateTime.of(year, month, day, hour, minute, second, 0, offset);
            data.setDataType(DATE_TIME);
            data.setValue(dateTime);
            log.info("DATE_TIME: {}", dateTime);
        }
    },
    DATE((byte) 26) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int year = Short.toUnsignedInt(buffer.getShort()); // Bytes 0-1
            int month = Byte.toUnsignedInt(buffer.get()); // Byte 2
            int day = Byte.toUnsignedInt(buffer.get()); // Byte 3
            Byte.toUnsignedInt(buffer.get()); // dayOfWeek Byte 4 (ignored in this example)

            // Handle "not specified" fields
            year = (year == 0xFFFF) ? 1970 : year; // Default to Unix epoch year if unspecified
            month = (month == 0xFF) ? 1 : month; // Default to January if unspecified
            day = (day == 0xFF) ? 1 : day; // Default to the first of the month if unspecified

            // Build OffsetDateTime
            var dateTime = OffsetDateTime.of(year, month, day, 0, 0, 0, 0, ZoneOffset.UTC);
            data.setDataType(DATE);
            data.setValue(dateTime);
            log.info("DATE: {}", dateTime);
        }
    },
    TIME((byte) 27) {
        @Override
        public void process(ByteBuffer buffer, DlmsData data) {
            int hour = Byte.toUnsignedInt(buffer.get()); // Byte 0
            int minute = Byte.toUnsignedInt(buffer.get()); // Byte 1
            int second = Byte.toUnsignedInt(buffer.get()); // Byte 2
            int hundredths = Byte.toUnsignedInt(buffer.get()); // Byte 3 (ignored)

            // Handle "not specified" fields
            hour = (hour == 0xFF) ? 0 : hour; // Default to 00 if unspecified
            minute = (minute == 0xFF) ? 0 : minute; // Default to 00 if unspecified
            second = (second == 0xFF) ? 0 : second; // Default to 00 if unspecified

            // Build OffsetTime
            OffsetTime time = OffsetTime.of(hour, minute, second, hundredths * 10_000_000, ZoneOffset.UTC);
            data.setDataType(TIME);
            data.setValue(time);
            log.info("TIME: {}", time);
        }
    };

    public byte code;

    private DataType(byte code) {
        this.code = code;
    }

    static final Map<Byte, DataType> BY_VALUE = new HashMap<>();

    public abstract void process(ByteBuffer buffer, DlmsData data);

    static {
        for (DataType f : values()) {
            BY_VALUE.put(f.code, f);
        }
    }

    public static DataType valueOf(byte code) {
        return BY_VALUE.get(code);
    }
}
