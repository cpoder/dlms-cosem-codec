package org.cpo.dlmscosem.pdu;

import org.cpo.dlmscosem.CosemPdu;
import org.cpo.dlmscosem.DataType;
import org.cpo.dlmscosem.Obis;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class GetRequest {

    public ByteArrayDataOutput getNormalRequest(int classId, Obis obis, int attributeId, byte block) {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeByte(CosemPdu.GET_REQUEST.code);
        buffer.writeShort(0x01C1);
        buffer.writeShort(classId);
        buffer.write(obis.getBytes());
        buffer.writeByte(attributeId);
        buffer.writeByte(block); // no access selector
        return buffer;
    }

    public ByteArrayDataOutput getNormalRequest(int classId, Obis obis, int attributeId) {
        return getNormalRequest(classId, obis, attributeId, (byte) 0);
    }

    public ByteArrayDataOutput getRequest(int classId, Obis obis, int attributeId) {
        ByteArrayDataOutput buffer = ByteStreams.newDataOutput();
        buffer.writeByte(CosemPdu.GET_REQUEST.code);
        buffer.write(00);
        buffer.writeShort(classId);
        buffer.write(obis.getBytes());
        buffer.writeByte(attributeId);
        buffer.writeByte(DataType.NULL_DATA.code); // no access selector
        return buffer;
    }
}
