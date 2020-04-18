package message;

/**
 * @author wujingyi
 */
public interface Message {
    /**
     * create a message.HashMessage
     * @return message.HashMessage
     */
    static Message create() {
        return new HashMessage();
    }

    static Message create(int initSize) {
        return new HashMessage(initSize);
    }

    int getInt(String key);

    long getLong(String key);

    double getDouble(String key);

    String getString(String key);

    boolean getBool(String key);

    Object getValue(String key);

    void putInt(String key, int value);

    void putLong(String key, long value);

    void putDouble(String key, double value);

    void putString(String key, String value);

    void putBool(String key, boolean value);

    void putValue(String key, Object value);
}
