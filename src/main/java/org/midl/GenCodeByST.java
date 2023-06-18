package org.midl;

import org.midl.entity.Declaration;
import org.midl.entity.Module;
import org.midl.entity.Struct;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenCodeByST {
    private String filename;
    //以下是代码生成
    private String OUTFILE;
    private String INFILE;
    private final STGroup group;
    private final StringBuilder CppOutput=new StringBuilder();
    private HashMap<String, ArrayList<Module>> modules;
    GenCodeByST(String INROOT, String OUTROOT, String filename){
        this.filename=filename.replace("idl","hxx");
        this.INFILE=INROOT+this.filename;
        this.OUTFILE=OUTROOT+"\\"+this.filename;
        group = new STGroupFile(INROOT+"\\MIDL.stg");
    }
    public void write(String filename){
        try(FileOutputStream fos = new FileOutputStream(OUTFILE)){
            fos.write(this.CppOutput.toString().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void genDef(StringBuilder stringBuilder) {
//        System.out.println(this.StructName);
        org.stringtemplate.v4.ST st = group.getInstanceOf("genDef");
        String Typename="MIDL";
        String includes="rti_me_cpp";
        st.add("HeaderContext","/*" +
                "below is the AST of the MIDL code,paste the code below to Typora can see the graphic tree\n" +
                "stateDiagram\n"+
                stringBuilder.toString()+
                "*/");
        st.add("typename", filename);
        st.add("includes", filename);
        this.CppOutput.append(st.render());
    }
    public void genMain() {
        org.stringtemplate.v4.ST st = group.getInstanceOf("genMain");
        module_struct_declaration msd;
        for(Map.Entry<String, ArrayList<Module>> e1 :this.modules.entrySet()){
            Module m = e1.getValue().get(0);
            for(Map.Entry<String, ArrayList<Struct>> e2:m.getStructs().entrySet())
            {
                msd= new module_struct_declaration(m.getName() + "_" + e2.getValue().get(0).getName());
                Struct s=e2.getValue().get(0);
                StringBuilder type,name,value;
                for (Map.Entry<String, ArrayList<Declaration>> e3:s.getDeclarations().entrySet()){
                    type= new StringBuilder();
                    name= new StringBuilder();
                    value= new StringBuilder();
                    if(m.getStructs().get(e3.getValue().get(0).getType())!=null){
                        type.append("space_").append(e3.getValue().get(0).getType());
                    }else {
                        type.append("CDR_").append(e3.getValue().get(0).getType().toUpperCase().charAt(0))
                                .append(e3.getValue().get(0).getType().substring(1));
                    }
                    name.append(" ").append(e3.getValue().get(0).getName());
                    if (e3.getValue().get(0).getValue()!=null){
                        value.append(" = ").append(e3.getValue().get(0).getValue());
                    }
                    msd.addDec(type.toString(),name.toString(),value.toString());
                }
                st.add("msd",msd);
            }
        }
        this.CppOutput.append(st.render());
    }
    private static class module_struct_declaration{
        public String module_struct;
        public ArrayList<Declaration> declarations=new ArrayList<>();
        public module_struct_declaration(String m_s){
            this.module_struct=m_s;
        }
        public void addDec(String t,String n,String v){
            Declaration D=new Declaration(n);
            D.setType(t);
            D.setValue(v);
            this.declarations.add(D);
        }

        public String getModule_struct() {
            return module_struct;
        }

        public ArrayList<Declaration> getDeclarations() {
            return declarations;
        }
    }
    public void setModules(HashMap<String, ArrayList<Module>> modules) {
        this.modules = modules;
    }

    public String getOUTFILE() {
        return OUTFILE;
    }

    public void setOUTFILE(String OUTFILE) {
        this.OUTFILE = OUTFILE;
    }

    public String getINFILE() {
        return INFILE;
    }

    public void setINFILE(String INFILE) {
        this.INFILE = INFILE;
    }
}
