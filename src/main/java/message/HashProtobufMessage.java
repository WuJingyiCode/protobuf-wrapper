package message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wujingyi
 */
public class HashProtobufMessage extends AbstractProtobufMessage {

    private Map<String, Object> map;

    HashProtobufMessage() {
        this.map = new HashMap<>();
    }

    HashProtobufMessage(int initSize) {
        this.map = new HashMap<>(initSize);
    }

    @Override
    public Object getValue(String key) {
        return map.get(key);
    }

    @Override
    public void putValue(String key, Object value) {
        map.put(key, value);
    }
}
