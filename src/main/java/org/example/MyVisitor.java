package org.example;

import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MyVisitor extends MIDLBaseVisitor<Object>{

    private StringBuilder output = new StringBuilder();

    public void Output() {
          //打印输出到SyntaxOut.txt
//        File f = new File(System.getProperty("user.dir")+"\\src"+"\\main\\java\\org\\example\\SyntaxOut.txt");
//        try(FileOutputStream fos = new FileOutputStream(f)){
//            fos.write(output.toString().getBytes());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(f);
        System.out.println(output.toString());
    }

    @Override
    public Object visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override public Object visitSpecification(MIDLParser.SpecificationContext ctx){
        this.output.append("specification ->");
        int i=0;
        while(ctx.definition(i)!=null){
            this.output.append(" definition");
            i++;
        }
        this.output.append("\n");
        return super.visitSpecification(ctx);
    }

    @Override
    public Object visitDefinition(MIDLParser.DefinitionContext ctx) {
        this.output.append(" definition ->");
        int i=0,j=0;
        if (ctx.type_decl()!=null){
            this.output.append(" type_decl");
        }
        if (ctx.module()!=null){
            this.output.append(" module");
        }
        this.output.append("\n");
        return super.visitDefinition(ctx);
    }

    @Override
    public Object visitModule(MIDLParser.ModuleContext ctx) {
        this.output.append("  module ->");
        if (ctx.ID()!=null){
            this.output.append(ctx.ID());
        }
        int i=0;
        while (ctx.definition(i)!=null){
            this.output.append(" definition");
            visitDefinition(ctx.definition(i));
            i++;
        }
        this.output.append("\n");
        return super.visitModule(ctx);
    }

    @Override
    public Object visitType_decl(MIDLParser.Type_declContext ctx) {
        this.output.append("  type_decl ->");
        if (ctx.struct_type()!=null){
            this.output.append(" struct_type");
        }
        if (ctx.ID()!=null){
            this.output.append(" "+ctx.ID());
        }
        this.output.append("\n");
        return super.visitType_decl(ctx);
    }

    @Override
    public Object visitStruct_type(MIDLParser.Struct_typeContext ctx) {
        this.output.append("    struct_type ->");
        this.output.append(" ").append(ctx.ID());
        this.output.append(" member_list");
        this.output.append("\n");
        return super.visitStruct_type(ctx);
    }

    @Override
    public Object visitMember_list(MIDLParser.Member_listContext ctx) {
        this.output.append("     member_list_type ->");
        int i=0;
//        while (ctx.type_spec(i)!=null&&ctx.declarators()!=null){
//            this.output.append(" type_spec"+i+" declarators"+i);
//            i++;
//        }
        this.output.append(" type_spec declarators");
        this.output.append("\n");
        return super.visitMember_list(ctx);
    }

    @Override
    public Object visitType_spec(MIDLParser.Type_specContext ctx) {
        this.output.append("      type_spec ->");
        if (ctx.scoped_name()!=null){
            this.output.append(" scoped_name");
        }else if(ctx.base_type_spec()!=null){
            this.output.append(" base_type_spec");
        }else {
            this.output.append(" struct_type");
        }
        this.output.append("\n");
        return super.visitType_spec(ctx);
    }

    @Override
    public Object visitScoped_name(MIDLParser.Scoped_nameContext ctx) {
        this.output.append("       scoped_name ->");
        int i=0;
        while(ctx.ID(i)!=null){
            this.output.append(" ");
            i++;
        }
        this.output.append("\n");
        return super.visitScoped_name(ctx);
    }

    @Override
    public Object visitBase_type_spec(MIDLParser.Base_type_specContext ctx) {
        this.output.append("       base_type_spec -> ");
        if (ctx.floating_pt_type()!=null){
            this.output.append(" floating_pt_type");
        }else if(ctx.integer_type()!=null){
            this.output.append(" integer_type");
        }else{
            this.output.append(ctx.getChild(0));
        }
        this.output.append("\n");
        return super.visitBase_type_spec(ctx);
    }

    @Override
    public Object visitFloating_pt_type(MIDLParser.Floating_pt_typeContext ctx) {
        this.output.append("        floating_pt_type ->");
        this.output.append(ctx);
        this.output.append("\n");
        return super.visitFloating_pt_type(ctx);
    }

    @Override
    public Object visitInteger_type(MIDLParser.Integer_typeContext ctx) {
        this.output.append("        integer_type ->");
        if(ctx.signed_int()!=null){
            this.output.append(" signed_int");
        }else if(ctx.unsigned_int()!=null){
            this.output.append(" unsigned_int");
        }
        this.output.append("\n");
        return super.visitInteger_type(ctx);
    }

    @Override
    public Object visitSigned_int(MIDLParser.Signed_intContext ctx) {
        this.output.append("         signed_int ->");
        this.output.append(ctx.getChild(0));
        this.output.append("\n");
        return super.visitSigned_int(ctx);
    }

    @Override
    public Object visitUnsigned_int(MIDLParser.Unsigned_intContext ctx) {
        this.output.append("         unsigned_int ->");
        this.output.append(ctx);
        this.output.append("\n");
        return super.visitUnsigned_int(ctx);
    }

    @Override
    public Object visitDeclarators(MIDLParser.DeclaratorsContext ctx) {
        this.output.append("      declarators ->");
        int i=0;
        while(ctx.declarator(i)!=null){
            this.output.append(" declarator");
            i++;
        }
        this.output.append("\n");
        return super.visitDeclarators(ctx);
    }

    @Override
    public Object visitDeclarator(MIDLParser.DeclaratorContext ctx) {
        this.output.append("       declarator ->");
        if (ctx.simple_declarator()!=null){
            this.output.append(" simple_declarator");
        }else if (ctx.array_declarator()!=null){
            this.output.append(" array_declarator");
        }
        this.output.append("\n");
        return super.visitDeclarator(ctx);
    }

    @Override
    public Object visitSimple_declarator(MIDLParser.Simple_declaratorContext ctx) {
        this.output.append("        simple_declarator ->");
        this.output.append(" "+ctx.ID());
        if (ctx.or_expr()!=null){
            this.output.append(" or_expr");
        }
        this.output.append("\n");
        return super.visitSimple_declarator(ctx);
    }

    @Override
    public Object visitArray_declarator(MIDLParser.Array_declaratorContext ctx) {
        this.output.append("        array_declarator ->");
        this.output.append(" "+ctx.ID()+" "+ctx.or_expr());
        if (ctx.exp_list()!=null){
            this.output.append(" exp_list");
        }
        this.output.append("\n");
        return super.visitArray_declarator(ctx);
    }

    @Override
    public Object visitExp_list(MIDLParser.Exp_listContext ctx) {
        this.output.append("         exp_list -> or_expr ");
        int i=0;
        while(ctx.or_expr(i)!=null){
            this.output.append(", or_expr");
            i++;
        }
        this.output.append("\n");
        return super.visitExp_list(ctx);
    }

    @Override
    public Object visitOr_expr(MIDLParser.Or_exprContext ctx) {
        this.output.append("          or_expr -> ");
        int i=0;
        while(ctx.xor_expr(i)!=null){
            this.output.append("or_expr ");
            if (ctx.xor_expr(i+1)!=null){
                this.output.append("| ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitOr_expr(ctx);
    }

    @Override
    public Object visitXor_expr(MIDLParser.Xor_exprContext ctx) {
        this.output.append("           xor_expr -> ");
        int i=0;
        while(ctx.and_expr(i)!=null){
            this.output.append("and_expr ");
            if (ctx.and_expr(i+1)!=null){
                this.output.append("^ ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitXor_expr(ctx);
    }

    @Override
    public Object visitAnd_expr(MIDLParser.And_exprContext ctx) {
        this.output.append("            and_expr -> ");
        int i=0;
        while(ctx.shift_expr(i)!=null){
            this.output.append("shift_expr ");
            if (ctx.shift_expr(i+1)!=null){
                this.output.append("& ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitAnd_expr(ctx);
    }

    @Override
    public Object visitShift_expr(MIDLParser.Shift_exprContext ctx) {
        this.output.append("             shift_expr -> ");
        int i=0;
        while(ctx.add_expr(i)!=null){
            this.output.append("add_expr ");
            if (ctx.add_expr(i+1)!=null){
                this.output.append(ctx.getChild(1).toString()+" ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitShift_expr(ctx);
    }

    @Override
    public Object visitAdd_expr(MIDLParser.Add_exprContext ctx) {
        this.output.append("              add_expr -> ");
        int i=0;
        while(ctx.mult_expr(i)!=null){
            this.output.append("mult_expr ");
            if (ctx.mult_expr(i+1)!=null){
                this.output.append(ctx.getChild(1).toString()+" ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitAdd_expr(ctx);
    }

    @Override
    public Object visitMult_expr(MIDLParser.Mult_exprContext ctx) {
        this.output.append("               mult_expr -> ");
        int i=0;
        while(ctx.unary_expr(i)!=null){
            this.output.append("unary_expr ");
            if (ctx.unary_expr(i+1)!=null){
                this.output.append(ctx.getChild(1).toString()+" ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitMult_expr(ctx);
    }

    @Override
    public Object visitUnary_expr(MIDLParser.Unary_exprContext ctx) {
        this.output.append("                unary_expr ->");
        if (ctx.getChild(1)!=null){
            this.output.append(" "+ctx.getChild(0));
        }
        this.output.append(" literal");
        this.output.append("\n");
        this.output.append("                 literal -> "+ctx.children.get(0));
        this.output.append("\n");
        return super.visitUnary_expr(ctx);
    }

    @Override
    public Object visitLiteral(MIDLParser.LiteralContext ctx) {
        this.output.append("                 literal ->");
        this.output.append(ctx.getChild(0));
        this.output.append("\n");
        return super.visitLiteral(ctx);
    }

}