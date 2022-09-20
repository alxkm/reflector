package org.common.reflector.data;


import org.common.reflector.data.annotation.CustomAnnotationForTest;

public class SimpleAnnotatedEntry {
    @CustomAnnotationForTest
    private String key;
    @CustomAnnotationForTest
    private String value;
    private String info;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private void doSomething() {

    }
}
