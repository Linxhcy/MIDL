package org.midl;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class Launch {
    private static final String INROOT = System.getProperty("user.dir") + "\\src" + "\\main\\java\\org\\midl\\MIDL";
    private static final String OUTROOT = System.getProperty("user.dir") + "\\src" + "\\main\\java\\org\\midl\\HXX";
    private static final ArrayList<File> FileList = new ArrayList<>();
    public static void main(String[] args) throws Exception{
        getFileList();
        processFile();
    }
    public static void run(String expr) throws Exception {
        MyVisitor v = new MyVisitor();
        CharStream input = new ANTLRInputStream(expr);
        MIDLLexer lexer = new MIDLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIDLParser parser = new MIDLParser(tokens);
        v.visit(parser.specification());
        v.Output(expr);
        v.ShowError();
        v.genCode();
    }
    private static void processFile() {
        StringBuilder FileContext = new StringBuilder();
        for(File f:FileList){
            try(FileReader fis=new FileReader(f);
                BufferedReader br = new BufferedReader(fis)
            ){
                String s;
                while ((s=br.readLine())!=null){
                    FileContext.append(s);
                }
                run(FileContext.toString());
                FileContext.append(br.readLine());
            }catch (Exception e){
                e.printStackTrace();
            }
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
}
