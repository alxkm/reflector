package org.common.reflector.data;

public class CustomTestInvokeClass {
    private String key;
    private String value;

    public CustomTestInvokeClass() {
    }

    public CustomTestInvokeClass(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String name) {
        this.value = name;
    }
}
