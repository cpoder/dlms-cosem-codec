package org.cpo.dlmscosem.pdu;

import java.nio.ByteBuffer;

import com.google.common.io.BaseEncoding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AssociationResponse {
    public AssociationResponse(ByteBuffer buffer) {
        byte totalLength = buffer.get();
        log.info("Total association response length is {} bytes", totalLength);
        while (buffer.hasRemaining()) {
            byte tag = buffer.get();
            byte tagLength = buffer.get();
            byte[] tagData = new byte[tagLength];
            buffer.get(tagData);
            log.info("Tag {} has a length of {} bytes with content: {}", Integer.toHexString(tag & 0xff), tagLength,
                    BaseEncoding.base16().encode(tagData));
            if (tag == 0xA1) {

            }
        }

    }
}
