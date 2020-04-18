package message;

import exception.NoSuchKeyException;

/**
 * @author wujingyi
 */
public abstract class AbstractProtobufMessage implements ProtobufMessage {

    @Override
    public int getInt(String key) {
        Object value = getValue(key);
        if (value == null) {
            throw new NoSuchKeyException(key);
        }
        return (int) value;
    }

    @Override
    public long getLong(String key) {
        Object value = getValue(key);
        if (value == null) {
            throw new NoSuchKeyException(key);
        }
        return (long) value;
    }

    @Override
    public double getDouble(String key) {
        Object value = getValue(key);
        if (value == null) {
            throw new NoSuchKeyException(key);
        }
        return (double) value;
    }

    @Override
    public String getString(String key) {
        Object value = getValue(key);
        if (value == null) {
            throw new NoSuchKeyException(key);
        }
        return (String) value;
    }

    @Override
    public boolean getBool(String key) {
        Object value = getValue(key);
        if (value == null) {
            throw new NoSuchKeyException(key);
        }
        return (boolean) value;
    }

    @Override
    public void putInt(String key, int value) {
        putValue(key, value);
    }

    @Override
    public void putLong(String key, long value) {
        putValue(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        putValue(key, value);
    }

    @Override
    public void putString(String key, String value) {
        putValue(key, value);
    }

    @Override
    public void putBool(String key, boolean value) {
        putValue(key, value);
    }

}
