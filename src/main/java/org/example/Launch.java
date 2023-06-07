package org.example;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Launch {
    public static void run(String expr) throws Exception{

        MyVisitor v = new MyVisitor();

        CharStream input = new ANTLRInputStream(expr);
        MIDLLexer lexer = new MIDLLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MIDLParser parser = new MIDLParser(tokens);
        v.visit(parser.specification());
        v.Output();
    }

    public static void main(String[] args) throws Exception{
        String[] testStr={
                "struct A{short num;};"
        };
        for(String s : testStr){
            System.out.println("Input: " + s);
            run(s);
        }
    }
}
