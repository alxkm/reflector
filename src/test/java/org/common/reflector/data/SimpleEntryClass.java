package org.common.reflector.data;

public class SimpleEntryClass {
    private String key;
    private String value;

    public SimpleEntryClass() {
    }

    public SimpleEntryClass(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private SimpleEntryClass(String key, String value, Object obj) {
        this.key = key;
        this.value = value;
    }

    public SimpleEntryClass(String value) {
        this.value = value;
    }
}
