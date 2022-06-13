package netty.nio;

import jnr.ffi.annotations.In;

import java.nio.IntBuffer;

public class BaseBuffer {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
