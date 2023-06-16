package org.midl;

import org.midl.entity.Module;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ST {
    //以下是代码生成
    private static final String OUTFILE = System.getProperty("user.dir")+"\\src"+"\\main\\java\\org\\midl\\MIDL.cpp";
    private static final String INFILE = System.getProperty("user.dir")+"\\src"+"\\main\\java\\org\\midl\\MIDL.stg";
    private static final STGroup group = new STGroupFile(INFILE);
    private StringBuilder CppOutput=new StringBuilder();
    private HashMap<String, ArrayList<Module>> modules=new HashMap<>();
    public void genCode(){
        genDef();
        genMain();
        write();
    }

    public void write(){
        try(FileOutputStream fos = new FileOutputStream(OUTFILE)){
            fos.write(this.CppOutput.toString().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void genDef() {
//        System.out.println(this.StructName);
        org.stringtemplate.v4.ST st = group.getInstanceOf("genDef");
        String Typename="MIDL";
        String includes="rti_me_cpp";
        st.add("HeaderContext","/*\n" +
                "WARNING: THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.\n" +
                "\n" +
                "This file was generated from struct.idl using \"idltoc\".\n" +
                "The idltoc tool is part of the RTI Data Distribution Service distribution.\n" +
                "For more information, type 'idltoc -help' at a command shell\n" +
                "or consult the RTI Data Distribution Service manual.\n" +
                "*/");
        st.add("typename", Typename);
        st.add("includes", includes);
        this.CppOutput.append(st.render());
    }
    private void genMain() {
        //TODO 链接module与struct输出,时间多可以判错再输出
        for(Map.Entry<String, ArrayList<Module>> e :this.modules.entrySet()){

        }
        String Module_Struct;
    }

    public void setModules(HashMap<String, ArrayList<Module>> modules) {
        this.modules = modules;
    }
}
