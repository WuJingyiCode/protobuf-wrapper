package message;

import exception.NoSuchKeyException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cakutau
 */
public class HashMessage implements Message {

    private Map<String, Object> map;

    /**
     * FIXME 使用<code>HashMap</code>是否会有线程安全的问题？
     */
    HashMessage() {
        this.map = new HashMap<>();
    }

    HashMessage(int initSize) {
        this.map = new HashMap<>(initSize);
    }

    @Override
    public int getInt(String key) {
        Object value = map.get(key);
        if (value == null) {
            throwNoSuchKeyException(key);
        }
        return (int) value;
    }

    @Override
    public long getLong(String key) {
        Object value = map.get(key);
        if (value == null) {
            throwNoSuchKeyException(key);
        }
        return (long) value;
    }

    @Override
    public double getDouble(String key) {
        Object value = map.get(key);
        if (value == null) {
            throwNoSuchKeyException(key);
        }
        return (double) value;
    }

    @Override
    public String getString(String key) {
        Object value = map.get(key);
        if (value == null) {
            throwNoSuchKeyException(key);
        }
        return (String) value;
    }

    @Override
    public boolean getBool(String key) {
        Object value = map.get(key);
        if (value == null) {
            throwNoSuchKeyException(key);
        }
        return (boolean) value;
    }

    @Override
    public Object getValue(String key) {
        return map.get(key);
    }

    @Override
    public void putInt(String key, int value) {
        map.put(key, value);
    }

    @Override
    public void putLong(String key, long value) {
        map.put(key, value);
    }

    @Override
    public void putDouble(String key, double value) {
        map.put(key, value);
    }

    @Override
    public void putString(String key, String value) {
        map.put(key, value);
    }

    @Override
    public void putBool(String key, boolean value) {
        map.put(key, value);
    }

    @Override
    public void putValue(String key, Object value) {
        map.put(key, value);
    }

    private void throwNoSuchKeyException(String message) throws NoSuchKeyException {
        throw new NoSuchKeyException(message);
    }
}
