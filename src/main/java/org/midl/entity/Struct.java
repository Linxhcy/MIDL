package org.midl.entity;

import org.midl.MyVisitor;

import java.util.ArrayList;

public class Struct {
    String name;
    boolean isRight;
    ArrayList<MyVisitor.Declaration> declarations=new ArrayList<>();
    public Struct(String name) {
        this.name = name;
    }
    public void addDeclaration(String name){
        MyVisitor.Declaration declaration = new MyVisitor.Declaration(name);
        declarations.add(declaration);
    }
}
