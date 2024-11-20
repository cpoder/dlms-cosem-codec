package org.cpo.dlmscosem.pdu;

import org.cpo.dlmscosem.CosemPdu;
import org.cpo.dlmscosem.DataType;
import org.cpo.dlmscosem.Obis;

import com.google.common.io.ByteArrayDataOutput;

public class GetRequest {
    private ByteArrayDataOutput buffer;

    public GetRequest(ByteArrayDataOutput buffer) {
        this.buffer = buffer;
        this.buffer.writeByte(CosemPdu.GET_REQUEST.code);
    }

    public void getNormalRequest(int classId, Obis obis, int attributeId) {
        buffer.writeShort(0x01C1);
        buffer.writeShort(classId);
        buffer.write(obis.getBytes());
        buffer.writeByte(attributeId);
        buffer.writeByte(DataType.NULL_DATA.code); // no access selector
    }

    public void getRequest(int classId, Obis obis, int attributeId) {
        buffer.write(00);
        buffer.writeShort(classId);
        buffer.write(obis.getBytes());
        buffer.writeByte(attributeId);
        buffer.writeByte(DataType.NULL_DATA.code); // no access selector
    }
}
