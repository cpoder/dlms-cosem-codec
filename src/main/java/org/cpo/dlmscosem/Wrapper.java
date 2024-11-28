package org.cpo.dlmscosem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.cpo.dlmscosem.cosem.CosemPdu;

import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteArrayDataOutput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Wrapper {
    public void encode(ByteArrayDataOutput buffer, int clientId, int serverId, byte[] dlmsFrame) {
        log.info("Sending DLMS frame {}", BaseEncoding.base16().encode(dlmsFrame));
        buffer.writeShort(0x0001);
        buffer.writeShort(clientId);
        buffer.writeShort(serverId);
        buffer.writeShort(dlmsFrame.length);
        buffer.write(dlmsFrame);
        log.info("Sending frame {}", BaseEncoding.base16().encode(buffer.toByteArray()));
    }

    public DlmsData decode(ByteBuffer buffer) {
        buffer.getShort(); // version
        int serverId = buffer.getShort();
        log.info("Server Id: {}", serverId);
        int clientId = buffer.getShort();
        log.info("Client Id: {}", clientId);
        int length = buffer.getShort();
        log.info("DLMS message size: {}", length);
        return decodeCosemPdu(buffer.slice());
    }

    public DlmsData decode(InputStream is) {
        ByteBuffer header;
        DlmsData result = null;
        try {
            header = ByteBuffer.wrap(is.readNBytes(8));
            header.getShort(); // version
            int serverId = header.getShort();
            log.info("Server Id: {}", serverId);
            int clientId = header.getShort();
            log.info("Client Id: {}", clientId);
            int length = header.getShort();
            log.info("DLMS message size: {}", length);
            ByteBuffer buffer = ByteBuffer.wrap(is.readNBytes(length));
            result = decodeCosemPdu(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private DlmsData decodeCosemPdu(ByteBuffer buffer) {
        DlmsData result = null;
        log.info("Receiving DLMS frame {}", BaseEncoding.base16().encode(buffer.array()));
        while (buffer.hasRemaining()) {
            byte code = buffer.get();
            var cosemPdu = CosemPdu.valueOf(code);
            if (cosemPdu != null) {
                log.info("COSEM PDU is: {}", CosemPdu.valueOf(code));
                result = CosemPdu.valueOf(code).decode(buffer);
            } else {
                log.error("{} is not a know PDU code", Byte.toUnsignedInt(code));
            }
        }
        return result;
    }
}
