package org.cpo.dlmscosem;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

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

    public void decode(ByteBuffer buffer, DlmsData data) {
        buffer.getShort(); // version
        int serverId = buffer.getShort();
        log.info("Server Id: {}", serverId);
        int clientId = buffer.getShort();
        log.info("Client Id: {}", clientId);
        int length = buffer.getShort();
        log.info("DLMS message size: {}", length);
        decodeCosemPdu(buffer.slice(), data);
    }

    public void decode(InputStream is, DlmsData data) {
        ByteBuffer header;
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
            decodeCosemPdu(buffer, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void decodeCosemPdu(ByteBuffer buffer, DlmsData data) {
        log.info("Receiving DLMS frame {}", BaseEncoding.base16().encode(buffer.array()));
        while (buffer.hasRemaining()) {
            byte code = buffer.get();
            log.info("COSEM PDU is: {}", CosemPdu.valueOf(code));
            CosemPdu.valueOf(code).decode(buffer, data);
        }
    }
}
