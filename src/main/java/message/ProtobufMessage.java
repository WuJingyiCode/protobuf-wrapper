package message;

/**
 * @author wujingyi
 */
public interface ProtobufMessage extends Message {
    static ProtobufMessage create() {
        return new HashProtobufMessage();
    }

    static ProtobufMessage create(int initSize) {
        return new HashProtobufMessage(initSize);
    }

    static ProtobufMessage create(byte[] arr, String msgName) {
        return null;
    }
}
