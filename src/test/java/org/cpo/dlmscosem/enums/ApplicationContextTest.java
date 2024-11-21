package org.cpo.dlmscosem.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.google.common.io.BaseEncoding;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationContextTest {
    @Test
    void testEncodeThenDecodeOID() {
        String oid = "2.16.756.5.8.1.1";
        var applicationContext = ApplicationContext.from(oid);
        assertEquals(ApplicationContext.LN_NO_CIPHERING, applicationContext);
        var encoded = applicationContext.encode();
        log.info("Encoded application context: {}", BaseEncoding.base16().encode(encoded));
        var decoded = ApplicationContext.decode(encoded);
        assertEquals(ApplicationContext.LN_NO_CIPHERING, decoded);
    }
}
