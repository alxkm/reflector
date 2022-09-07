package org.common.reflector.data;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleEntryClass)) return false;
        SimpleEntryClass that = (SimpleEntryClass) o;
        return key.equals(that.key) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
