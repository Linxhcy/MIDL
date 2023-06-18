package org.midl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.midl.entity.Declaration;
import org.midl.entity.Module;
import org.midl.entity.Struct;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Launch {
    private static final String INROOT = System.getProperty("user.dir") + "\\src" + "\\main\\java\\org\\midl\\MIDL";//输入文件根目录
    private static final String OUTROOT = System.getProperty("user.dir") + "\\src" + "\\main\\java\\org\\midl\\HXX";//输入文件根目录
    private static final ArrayList<File> FileList = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        getFileList();
        processFile();
    }
    private static void processFile() {
        for(File f:FileList){
            StringBuilder FileContext = new StringBuilder();
            try(FileReader fis=new FileReader(f);
                BufferedReader br = new BufferedReader(fis)
            ){
                String s;
                while ((s=br.readLine())!=null){
                    FileContext.append(s).append("\n");
                }
                run(f.getName(),FileContext.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
//            try(FileInputStream fis=new FileInputStream(f);
//                BufferedInputStream bis = new BufferedInputStream(fis)
//            ){
//                byte[] bytes = new byte[5];
//                int len;// 记录每次读取的字节的个数
//                while ((len = bis.read(bytes)) != -1){
//                    String str = new String(bytes, 0, len);
//                    FileContext.append(str);
//                }
//                run(FileContext.toString());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
    }
    private static void getFileList() {
        File file = new File(INROOT);
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.getName().endsWith("idl")) {
                FileList.add(f);
            }
        }
    }
    public static void run(String filename,String Context) throws Exception {
        MyVisitor v = new MyVisitor();
        CharStream input = new ANTLRInputStream(Context);
        MIDLLexer lexer = new MIDLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIDLParser parser = new MIDLParser(tokens);
        v.visit(parser.specification());
//        v.printModules();//打印结构结构
        System.out.println("正在检查"+filename+"中是否有错误");
        v.genResult(Context);//判错并生成各种错误信息
        outputAST(v.getModules(),filename);
        outputAST(v.getAST().toString(),filename);
        if(v.isRight()){//代码正确才能生成
            System.out.println(filename+"中无错误,为其生成目标代码\n");
            GenCodeByST st = new GenCodeByST(INROOT,OUTROOT,filename);
            st.setModules(v.getModules());
            st.genDef(v.getAST());//生成头
            st.genMain();//生成主体
            st.write(filename);//创建写入同名hxx文件
        }
    }

    private static void outputAST(HashMap<String, ArrayList<Module>> modules, String filename) {
        File f = new File(OUTROOT+"\\"+filename.replace(".idl","Simple.md"));
        try(FileOutputStream fos =new FileOutputStream(f)){
            StringBuilder output=new StringBuilder();
            output.append("```mermaid\nstateDiagram\n");
            int i=0,j=0,k=0;
            for (Map.Entry<String, ArrayList<Module>> e1 : modules.entrySet()) {
//            System.out.println(e1.getKey());
                for (Module m : e1.getValue()) {
                    //module
                    output.append("specification --> (module)" + m.getName()+"\n");
                    for (Map.Entry<String, ArrayList<Struct>> e2 : m.getStructs().entrySet()) {
//                        //struct
////                    System.out.println("  "+e2.getKey());
                        for (Struct s : e2.getValue()) {
                            output.append("(module)"+m.getName()+"-->" + "(struct)"+s.getName()+"\n");
//                            //declaration
                            for (Map.Entry<String, ArrayList<Declaration>> e3 : s.getDeclarations().entrySet()) {
//                            System.out.println("    "+e3.getKey());
                                for (Declaration d : e3.getValue()) {
                                    output.append("(struct)"+s.getName()+"-->" + "(declaration)"+d.getName()+"\n");
                                    output.append("(declaration)"+d.getName()+"-->" + "(type)"+d.getType()+"\n");
                                    output.append("(declaration)"+d.getName()+"-->" + "(value)"+d.getValue()+"\n");
                                }
                            }
                        }
                    }
                    i++;
                }
            }
            output.append("\n```");
            fos.write(output.toString().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void outputAST(String s, String filename) {
        File f = new File(OUTROOT+"\\"+filename.replace(".idl","AST.md"));
        try(FileOutputStream fos =new FileOutputStream(f)){
            StringBuilder output=new StringBuilder();
            output.append("```mermaid\nstateDiagram\n");
            int i=0,j=0,k=0;
            output.append(s);
            output.append("\n```");
            fos.write(output.toString().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
