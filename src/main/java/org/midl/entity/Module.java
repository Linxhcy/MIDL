package org.midl.entity;

import org.midl.MyVisitor;

import java.util.ArrayList;
import java.util.HashMap;

public class Module {
    private String name;
    private boolean isRight = true;
    private HashMap<String,ArrayList<Struct>> structs=new HashMap<>();
    public Module(String name) {
        this.name = name;
    }
    public void addStruct(Struct struct){
        if(this.structs.get(struct.getName())==null){
            //struct未被定义
            ArrayList<Struct> StructList =new ArrayList<>();
            StructList.add(struct);
            this.structs.put(struct.getName(),StructList);
        } else {
            //struct已被定义
            ArrayList<Struct> StructList = this.structs.get(struct.getName());
            struct.setRight(false);
            StructList.add(struct);
            this.structs.replace(struct.getName(),StructList);
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

    public HashMap<String, ArrayList<Struct>> getStructs() {
        return structs;
    }

    public void setStructs(HashMap<String, ArrayList<Struct>> structs) {
        this.structs = structs;
    }
}
