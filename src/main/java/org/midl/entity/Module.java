package org.midl.entity;

import org.midl.MyVisitor;

import java.util.ArrayList;

public class Module {
    String name;
    boolean isRight;
    ArrayList<Struct> structs=new ArrayList<>();
    public Module(String name) {
        this.name = name;
    }
    public void addStruct(String name){
        Struct struct = new Struct(name);
        structs.add(struct);
    }
}
