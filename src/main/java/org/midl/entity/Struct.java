package org.midl.entity;

import org.midl.MyVisitor;

import java.util.ArrayList;
import java.util.HashMap;

public class Struct {
    private String name;
    private boolean isRight = true;
    private HashMap<String,ArrayList<Declaration>> declarations=new HashMap<>();
    public Struct(String name) {
        this.name = name;
    }
    public void addDeclaration(Declaration declaration){
        if(this.declarations.get(declaration.getName())==null){
            //declaration未被定义
            ArrayList<Declaration> DeclarationList =new ArrayList<>();
            DeclarationList.add(declaration);
            this.declarations.put(declaration.getName(),DeclarationList);
        } else {
            //declaration已被定义
            ArrayList<Declaration> DeclarationList = this.declarations.get(declaration.getName());
            declaration.setRight(2);
            DeclarationList.add(declaration);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public HashMap<String, ArrayList<Declaration>> getDeclarations() {
        return declarations;
    }

    public void setDeclarations(HashMap<String, ArrayList<Declaration>> declarations) {
        this.declarations = declarations;
    }
}
