package kr.co.prgrms.clone.global.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class ApplicationUtils {

    private ApplicationUtils() {
    }

    public static UUID createUUID() {
        return UUID.randomUUID();
    }

    public static UUID toUUID(byte[] uuid) {
        var byteBuffer = ByteBuffer.wrap(uuid);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
