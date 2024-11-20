package org.cpo.dlmscosem;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.cpo.dlmscosem.pdu.AssociationResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum CosemPdu {
    INITIATEREQUEST((byte) 0x01) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    READREQUEST((byte) 0x05) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    WRITEREQUEST((byte) 0x06) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    INITIATERESPONSE((byte) 0x08) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    READRESPONSE((byte) 0x0C) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    WRITERESPONSE((byte) 0x0D) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    CONFIRMEDSERVICEERROR((byte) 0x0E) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    UNCONFIRMEDWRITEREQUEST((byte) 0x16) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    INFORMATIONREPORTREQUEST((byte) 0x18) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_INITIATEREQUEST((byte) 0x21) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_READREQUEST((byte) 0x25) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_WRITEREQUEST((byte) 0x26) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_INITIATERESPONSE((byte) 0x28) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_READRESPONSE((byte) 0x2C) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_WRITERESPONSE((byte) 0x2D) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    ASSOCIATION_REQUEST((byte) 0x60) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'decode'");
        }

    },
    ASSOCIATION_RESPONSE((byte) 0x61) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            log.info("In ASSOCIATION_RESPONSE");
            data.setValue(new AssociationResponse(buffer));
        }

    },
    GET_REQUEST((byte) 0xC0) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    SET_REQUEST((byte) 0xC1) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    EVENT_NOTIFICATION_REQUEST((byte) 0xC2) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    ACTION_REQUEST((byte) 0xC3) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GET_RESPONSE((byte) 0xC4) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            log.info("In GET_RESPONSE");
            byte code = buffer.get();
            if (code == 0x01) { // GET_RESPONSE_NORMAL
                byte invokeIdAndPriority = buffer.get();
                log.info("invokeIdAndPriority: {}", invokeIdAndPriority);
                buffer.get();
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    DataType dataType = DataType.valueOf(b);
                    if (dataType != null) {
                        dataType.process(buffer, data);
                    } else {
                        log.error("Unknown data type {}", b);
                    }
                }
            }
        }
    },
    SET_RESPONSE((byte) 0xC5) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    ACTION_RESPONSE((byte) 0xC7) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_GET_REQUEST((byte) 0xC8) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_SET_REQUEST((byte) 0xC9) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_EVENT_NOTIFICATION_REQUEST((byte) 0xCA) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_ACTION_REQUEST((byte) 0xCB) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_GET_RESPONSE((byte) 0xCC) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_SET_RESPONSE((byte) 0xCD) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    GLO_ACTION_RESPONSE((byte) 0xCF) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_GET_REQUEST((byte) 0xD0) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_SET_REQUEST((byte) 0xD1) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_EVENT_NOTIFICATION_REQUEST((byte) 0xD2) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_ACTIONREQUEST((byte) 211) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_GET_RESPONSE((byte) 212) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_SET_RESPONSE((byte) 213) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    DED_ACTION_RESPONSE((byte) 215) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    },
    EXCEPTION_RESPONSE((byte) 216) {
        @Override
        public void decode(ByteBuffer buffer, DlmsData data) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'process'");
        }
    };

    public byte code;

    private CosemPdu(byte code) {
        this.code = code;
    }

    static final Map<Byte, CosemPdu> BY_VALUE = new HashMap<>();

    public abstract void decode(ByteBuffer buffer, DlmsData data);

    static {
        for (CosemPdu f : values()) {
            BY_VALUE.put(f.code, f);
        }
    }

    public static CosemPdu valueOf(byte code) {
        return BY_VALUE.get(code);
    }

}
