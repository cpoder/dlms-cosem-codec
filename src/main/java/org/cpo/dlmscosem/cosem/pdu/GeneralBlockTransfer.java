package org.cpo.dlmscosem.cosem.pdu;

import java.nio.ByteBuffer;

import org.cpo.dlmscosem.cosem.CosemPdu;

import com.google.common.io.BaseEncoding;

import lombok.Getter;

@Getter
public class GeneralBlockTransfer {
    private int serverBlockControl;
    private int blockControl;
    private int blockNumber;
    private int blockNumberAck;
    private byte[] blockData;

    public GeneralBlockTransfer(ByteBuffer buffer) {
        blockControl = Byte.toUnsignedInt(buffer.get());
        blockNumber = Short.toUnsignedInt(buffer.getShort());
        blockNumberAck = Short.toUnsignedInt(buffer.getShort());
        serverBlockControl = Byte.toUnsignedInt(buffer.get());
        int size = Byte.toUnsignedInt(buffer.get());
        blockData = new byte[size];
        buffer.get(blockData);
    }

    public GeneralBlockTransfer(int blockControl, int blockNumber, int blockNumberAck) {
        this.blockControl = blockControl;
        this.blockNumber = blockNumber;
        this.blockNumberAck = blockNumberAck;
    }

    public byte[] toBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(7 + (blockData != null ? blockData.length : 0));
        buffer.put(CosemPdu.GENERAL_BLOCK_TRANSFER.code);
        buffer.put((byte) blockControl);
        buffer.putShort((short) blockNumber);
        buffer.putShort((short) blockNumberAck);
        if (blockData != null) {
            buffer.put((byte) blockData.length);
            buffer.put(blockData);
        } else {
            buffer.put((byte) 0);
        }
        return buffer.array();
    }

    public String toString() {
        String result = "BLOCK CONTROL: " + blockControl;
        result += "\nBLOCK NUMBER: " + blockNumber;
        result += "\nBLOCK NUMBER ACK: " + blockNumberAck;
        if (blockData != null) {
            result += "\nBLOCK DATA: " + BaseEncoding.base16().encode(blockData);
        }

        return result;
    }
}
