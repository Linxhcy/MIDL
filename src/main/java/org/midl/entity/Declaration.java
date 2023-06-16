package org.midl.entity;

public class Declaration {
    private int isRight = 1;//1代表正确,2代表命名重复,3代表类型未定义,4代表值与类型不匹配
    private String name;
    private String type;
    private Object value;
    public Declaration(String name) {
        this.name = name;
    }

    public int isRight() {
        return isRight;
    }

    public void setRight(int right) {
        isRight = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
