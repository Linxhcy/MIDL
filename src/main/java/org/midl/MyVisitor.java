package org.midl;

import org.antlr.v4.runtime.tree.ParseTree;
import org.midl.entity.Declaration;
import org.midl.entity.Module;
import org.midl.entity.Struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyVisitor extends MIDLBaseVisitor<Object> {
    private boolean isRight=false;
    private final StringBuilder TypeErrorMessage = new StringBuilder();
    private final StringBuilder NameErrorMessage = new StringBuilder();
    private final StringBuilder output = new StringBuilder();
    //以下用于类型判断
//    private final ArrayList<String> ElementAndTypeAndValue = new ArrayList<>();//用”元素名 元素类型 值“来存储
//    private final ArrayList<String> ErrorType = new ArrayList<>();
//    private boolean HasModule=false;
//    private boolean HasStruct=false;
    //散列链表检查冲突
    private final HashMap<String, ArrayList<Module>> modules = new HashMap<>();
    private String TypeTemp;
    private Module ModuleTemp = null;
    private Struct StructTemp = null;
    private Declaration DeclarationTemp = null;
    private int DeclarationCounter = 0;
    private int StructCounter = 0;
    private int ArrayCounter = 0;
    private ArrayList<Object> ArrayTemp = null;
    private String NegativeType;
    public boolean isRight() {
        return isRight;
    }
    public HashMap<String, ArrayList<Module>> getModules() {
        return this.modules;
    }
//    private final String[] BaseTypeList = {"boolean","short","long",
//            "unsigned","int8","int16","int32","int64",
//            "uint8","uint16","uint32","uint64","char",
//            "string","float","double"};

    private void addModule(Module module) {//boolean表示是否冲突
        if (this.modules.get(module.getName()) == null) {
            //module未被定义
            ArrayList<Module> ModuleList = new ArrayList<>();
            ModuleList.add(module);
            this.modules.put(module.getName(), ModuleList);
        } else {
            //module已被定义,模拟散列链表特性
            ArrayList<Module> ModuleList = this.modules.get(module.getName());
            module.setRight(false);
            ModuleList.add(module);
        }
    }

    public void outputMessage(String expr) throws InterruptedException {
        //打印输出到SyntaxOut.txt
//        File f = new File(System.getProperty("user.dir")+"\\src"+"\\main\\java\\org\\example\\SyntaxOut.txt");
//        try(FileOutputStream fos = new FileOutputStream(f)){
//            fos.write(output.toString().getBytes());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(f);
//        System.out.println("input:\n" + expr);
        if(this.NameErrorMessage.toString().equals("")&&this.TypeErrorMessage.toString().equals("")){

        }
        System.out.println(output);
        System.out.println(this.TypeErrorMessage);
        CheckName();
        System.out.println(this.NameErrorMessage);
    }

    @Override
    public Object visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public Object visitSpecification(MIDLParser.SpecificationContext ctx) {
        this.output.append("specification ->");
        int i = 0;
        while (ctx.definition(i) != null) {
            this.output.append(" definition");
            i++;
        }
        this.output.append("\n");
        return super.visitSpecification(ctx);
    }

    @Override
    public Object visitDefinition(MIDLParser.DefinitionContext ctx) {
        this.output.append(" definition ->");
        int i = 0, j = 0;
        if (ctx.type_decl() != null) {
            this.output.append(" type_decl");
        }
        if (ctx.module() != null) {
            this.output.append(" module");
        }
        this.output.append("\n");
        return super.visitDefinition(ctx);
    }

    @Override
    public Object visitModule(MIDLParser.ModuleContext ctx) {
//        this.HasModule=true;
        this.output.append("  module ->");
        this.output.append(ctx.ID());
        //建立新temp
        this.ModuleTemp = new Module(ctx.ID().toString());
        for (int i = 0; ctx.definition(i) != null; i++) {
            this.StructCounter++;
            this.output.append(" definition");
        }
        this.output.append("\n");
        return super.visitModule(ctx);
    }

    @Override
    public Object visitType_decl(MIDLParser.Type_declContext ctx) {
        this.output.append("  type_decl ->");
        //不定义module，给与默认module
        if (this.ModuleTemp == null) {
            this.ModuleTemp = new Module("");
            this.StructCounter = 1;
        }
        if (ctx.struct_type() != null) {
            this.output.append(" struct_type");
        } else {
            //这里到叶子了
            this.StructCounter--;
            this.output.append(" ").append(ctx.ID());
//            checkStructName(ctx);
            this.StructTemp = new Struct(ctx.ID().toString());
            this.ModuleTemp.addStruct(this.StructTemp);
//            if (this.StructCounter==0){
//                addModule(this.ModuleTemp);
////                this.ModuleTemp=null;
//            }
        }
        this.output.append("\n");
        return super.visitType_decl(ctx);
    }

    @Override
    public Object visitStruct_type(MIDLParser.Struct_typeContext ctx) {
        this.StructCounter--;
        this.output.append("    struct_type ->");
        this.output.append(" ").append(ctx.ID());
        this.StructTemp = new Struct(ctx.ID().toString());
//        this.ModuleTemp.addStruct(this.StructTemp);
//        checkStructName(ctx);
        this.output.append(" member_list");
        this.output.append("\n");
        return super.visitStruct_type(ctx);
    }

    @Override
    public Object visitMember_list(MIDLParser.Member_listContext ctx) {
        this.output.append("     member_list ->");
        if (ctx.type_spec(0) == null) {
            this.ModuleTemp.addStruct(this.StructTemp);
            if (this.StructCounter == 0 && this.DeclarationCounter == 0) {
                addModule(this.ModuleTemp);
            }
        }
        for (int i = 0; ctx.type_spec(i) != null; i++) {
            this.output.append(" {type_spec declarators}");
            this.DeclarationCounter++;
        }
        this.output.append("\n");
        return super.visitMember_list(ctx);
    }

    @Override
    public Object visitType_spec(MIDLParser.Type_specContext ctx) {
        this.output.append("      type_spec ->");
        if (ctx.scoped_name() != null) {
            this.output.append(" scoped_name");
        } else if (ctx.base_type_spec() != null) {
            this.output.append(" base_type_spec");
        } else {
            this.output.append(" struct_type");
        }
        this.output.append("\n");
        return super.visitType_spec(ctx);
    }

    @Override
    public Object visitScoped_name(MIDLParser.Scoped_nameContext ctx) {
        boolean isDefined=false;//用于标记是否已定义
        this.output.append("       scoped_name ->");
        //能visit到这说明不是基本型了，要验证是否已定义
        //先检查本module是否已定义,ctx.ID(0)代表已定义的struct::id
        if (this.ModuleTemp.getStructs().get(ctx.ID(0).toString()) != null) {
            isDefined=true;
        }
        else {//若未被本module定义,查看是否是引用,ctx.ID(0)代表其他module
            //先检查是否有此module
            ArrayList<Module> am=this.modules.get(ctx.ID(0).toString());
            if(am==null){
                this.NameErrorMessage.append("未定义module::"+ctx.ID(0).toString());
            }else {
                if(am.size()>1) {//可能存在重复定义的module::id给予报错
                    this.NameErrorMessage.append(am.get(0).getName()+"存在重复定义,引用失效");
                }else {
                    //再检查该module是否定义了后序struct::id
                    isDefined=true;
                    for(int i=1;ctx.ID(i)!=null;i++){
                        if(am.get(0).getStructs().get(ctx.ID(i).toString())==null){
                            isDefined=false;
                            this.NameErrorMessage.append(am.get(0).getName()+"::"+ctx.ID(i)+" 未被定义");
                        }
                    }
                }
            }
        }
        if(isDefined){
            this.output.append(" ").append(ctx.ID(0));
            this.DeclarationTemp.setType(ctx.ID().toString());
        }
        this.output.append("\n");
        return super.visitScoped_name(ctx);
    }

    @Override
    public Object visitBase_type_spec(MIDLParser.Base_type_specContext ctx) {
        this.output.append("       base_type_spec -> ");
        if (ctx.floating_pt_type() != null) {
            this.output.append(" floating_pt_type");
        } else if (ctx.integer_type() != null) {
            this.output.append(" integer_type");
        } else {
            this.TypeTemp = ctx.getChild(0).toString();
            this.output.append(ctx.getChild(0));
        }
        this.output.append("\n");
        return super.visitBase_type_spec(ctx);
    }

    @Override
    public Object visitFloating_pt_type(MIDLParser.Floating_pt_typeContext ctx) {
        this.output.append("        floating_pt_type ->");
        this.output.append(ctx.getChild(0).toString());
        this.output.append("\n");
        this.TypeTemp = ctx.getChild(0).toString();
        return super.visitFloating_pt_type(ctx);
    }

    @Override
    public Object visitInteger_type(MIDLParser.Integer_typeContext ctx) {
        this.output.append("        integer_type ->");
        if (ctx.signed_int() != null) {
            this.output.append(" signed_int");
        } else if (ctx.unsigned_int() != null) {
            this.output.append(" unsigned_int");
        }
        this.output.append("\n");
        return super.visitInteger_type(ctx);
    }

    @Override
    public Object visitSigned_int(MIDLParser.Signed_intContext ctx) {
        this.output.append("         signed_int ->");
        this.output.append(ctx.getChild(0));
        this.TypeTemp = ctx.getChild(0).toString();
        this.output.append("\n");
        return super.visitSigned_int(ctx);
    }

    @Override
    public Object visitUnsigned_int(MIDLParser.Unsigned_intContext ctx) {
        this.output.append("         unsigned_int ->");
        this.output.append(ctx.getChild(0).toString());
        this.output.append("\n");
        this.TypeTemp = ctx.getChild(0).toString();
        return super.visitUnsigned_int(ctx);
    }

    @Override
    public Object visitDeclarators(MIDLParser.DeclaratorsContext ctx) {
        this.output.append("      declarators ->");
        int i = 0;
        while (ctx.declarator(i) != null) {
            this.output.append(" declarator");
            i++;
        }
        this.output.append("\n");
        return super.visitDeclarators(ctx);
    }

    @Override
    public Object visitDeclarator(MIDLParser.DeclaratorContext ctx) {
        this.output.append("       declarator ->");
        if (ctx.simple_declarator() != null) {
            this.output.append(" simple_declarator");
        } else {
            this.output.append(" array_declarator");
        }
        this.output.append("\n");
        return super.visitDeclarator(ctx);
    }

    @Override
    public Object visitSimple_declarator(MIDLParser.Simple_declaratorContext ctx) {
        this.output.append("        simple_declarator ->");
        this.DeclarationTemp = new Declaration(ctx.ID().toString());
        this.DeclarationTemp.setType(TypeTemp);
//        this.StructTemp.addDeclaration(this.DeclarationTemp);
//        checkScopedName(ctx);
        this.output.append(" ").append(ctx.ID());
        if (ctx.or_expr() != null) {
            this.output.append(" or_expr");
        }
        this.output.append("\n");

        if (ctx.or_expr() == null) {//这里到叶子了
            this.StructTemp.addDeclaration(this.DeclarationTemp);
            this.DeclarationCounter--;
            if (this.DeclarationCounter == 0) {
                this.ModuleTemp.addStruct(this.StructTemp);
            }
            if (this.DeclarationCounter == 0 && this.StructCounter == 0) {
                addModule(this.ModuleTemp);
            }
        }
        return super.visitSimple_declarator(ctx);


    }

    @Override
    public Object visitArray_declarator(MIDLParser.Array_declaratorContext ctx) {
        this.output.append("        array_declarator ->");
        this.output.append(" ").append(ctx.ID()).append(" or_expr");
//        checkScopedName(ctx);
        this.DeclarationTemp = new Declaration(ctx.ID().toString());
        this.DeclarationTemp.setType(TypeTemp);
        ArrayTemp = new ArrayList<>();
        if (ctx.exp_list() != null) {
            this.output.append(" exp_list");
        }
        this.output.append("\n");
        return super.visitArray_declarator(ctx);
    }

    @Override
    public Object visitExp_list(MIDLParser.Exp_listContext ctx) {
        this.output.append("         exp_list -> or_expr ");
        int i = 0;
        while (ctx.or_expr(i) != null) {
            this.output.append(", or_expr");
            i++;
        }
        this.output.append("\n");
        return super.visitExp_list(ctx);
    }

    @Override
    public Object visitOr_expr(MIDLParser.Or_exprContext ctx) {
        this.output.append("          or_expr -> ");
        int i = 0;
        while (ctx.xor_expr(i) != null) {
            this.output.append("or_expr ");
            if (ctx.xor_expr(i + 1) != null) {
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
        int i = 0;
        while (ctx.and_expr(i) != null) {
            this.output.append("and_expr ");
            if (ctx.and_expr(i + 1) != null) {
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
        int i = 0;
        while (ctx.shift_expr(i) != null) {
            this.output.append("shift_expr ");
            if (ctx.shift_expr(i + 1) != null) {
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
        int i = 0;
        while (ctx.add_expr(i) != null) {
            this.output.append("add_expr ");
            if (ctx.add_expr(i + 1) != null) {
                this.output.append(ctx.getChild(1).toString() + " ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitShift_expr(ctx);
    }

    @Override
    public Object visitAdd_expr(MIDLParser.Add_exprContext ctx) {
        this.output.append("              add_expr -> ");
        int i = 0;
        while (ctx.mult_expr(i) != null) {
            this.output.append("mult_expr ");
            if (ctx.mult_expr(i + 1) != null) {
                this.output.append(ctx.getChild(1).toString() + " ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitAdd_expr(ctx);
    }

    @Override
    public Object visitMult_expr(MIDLParser.Mult_exprContext ctx) {
        this.output.append("               mult_expr -> ");
        int i = 0;
        while (ctx.unary_expr(i) != null) {
            this.output.append("unary_expr ");
            if (ctx.unary_expr(i + 1) != null) {
                this.output.append(ctx.getChild(1).toString() + " ");
            }
            i++;
        }
        this.output.append("\n");
        return super.visitMult_expr(ctx);
    }

    @Override
    public Object visitUnary_expr(MIDLParser.Unary_exprContext ctx) {
        this.output.append("                unary_expr ->");
        if (ctx.getChild(0) != null && (ctx.getChild(0).toString().equals("-")
                || ctx.getChild(0).toString().equals("~"))) {
            this.NegativeType = ctx.getChild(0).toString();
        } else {
            this.NegativeType = "";
        }
        if (ctx.getChild(1) != null) {
            this.output.append(" " + ctx.getChild(0));
        }
        this.output.append(" literal");
        this.output.append("\n");
        return super.visitUnary_expr(ctx);
    }

    //为definition赋值并检查差错
    @Override
    public Object visitLiteral(MIDLParser.LiteralContext ctx) {
        this.output.append("                 literal -> ");
        this.output.append(ctx.getChild(0));
        this.output.append("\n");
        if (ctx.ID() != null || ctx.OTHER() != null) {
            TypeErrorMessage.append("无法识别" + ctx.ID().toString() + "\n");
        }
//        this.ElementAndTypeAndValue.add(this.ElementTemp+" "+this.TypeTemp+" "+ctx.children.get(0));
        if (this.ArrayTemp != null) {
            if (this.DeclarationTemp.getIsArray() == -1) { //这里是定义数组大小
                if (this.NegativeType.equals("")) {
                    this.DeclarationTemp.setIsArray(Integer.parseInt(ctx.INTEGER().toString()));
                    this.ArrayCounter = this.DeclarationTemp.getIsArray();
                } else {
                    this.TypeErrorMessage.append(this.DeclarationTemp.getName() + " 数组大小不可定义为负数");
                    System.out.println(TypeErrorMessage);
                    System.exit(0);
                }
            } else {
                if (ctx.INTEGER() != null) {
                    this.ArrayTemp.add(this.NegativeType + Integer.parseInt(ctx.INTEGER().toString()));
                } else if (ctx.FLOATING_PT() != null) {
                    this.ArrayTemp.add(this.NegativeType + Float.valueOf(ctx.FLOATING_PT().toString()));
                } else if (ctx.CHAR() != null) {
//                    if (ctx.CHAR().toString().length() != 1) {
//                        this.TypeErrorMessage.append("char定义出错");
//                    }
                    this.ArrayTemp.add(ctx.CHAR().toString());
                } else if (ctx.STRING() != null) {
                    this.ArrayTemp.add(ctx.STRING().toString());
                } else {
                    this.ArrayTemp.add(Boolean.valueOf(ctx.BOOLEAN().toString()));
                }
                this.ArrayCounter--;
                if (this.ArrayCounter == 0) {//表示数组初始化完毕
                    this.DeclarationTemp.setValue(this.ArrayTemp);
                    CheckType(this.DeclarationTemp);
                    this.StructTemp.addDeclaration(this.DeclarationTemp);
                    this.ArrayTemp = null;
                    this.DeclarationCounter--;
                }
            }
        } else {
            try {
                if (ctx.INTEGER() != null) {
                    DeclarationTemp.setValue(this.NegativeType + Integer.parseInt(ctx.INTEGER().toString()));
                } else if (ctx.FLOATING_PT() != null) {
                    DeclarationTemp.setValue(this.NegativeType + Float.valueOf(ctx.FLOATING_PT().toString()));
                } else if (ctx.CHAR() != null) {
//                    if (ctx.CHAR().toString().length() != 1) {
//                        throw new Exception();
//                    }
                    DeclarationTemp.setValue(ctx.CHAR().toString());
                } else if (ctx.STRING() != null) {
                    DeclarationTemp.setValue(ctx.STRING().toString());
                } else {
                    DeclarationTemp.setValue(Boolean.valueOf(ctx.BOOLEAN().toString()));
                }
                CheckType(this.DeclarationTemp);
//                System.out.println(this.DeclarationTemp.getValue().getClass());
            } catch (Exception e) {//这里出错是因为类型不匹配
//                e.printStackTrace();
                DeclarationTemp.setIsRight(4);
            } finally {
                this.DeclarationCounter--;
                this.StructTemp.addDeclaration(this.DeclarationTemp);
            }

        }
        if (this.DeclarationCounter == 0) {
            this.ModuleTemp.addStruct(this.StructTemp);
        }
        if (this.DeclarationCounter == 0 && this.StructCounter == 0) {
            addModule(this.ModuleTemp);
        }
        return super.visitLiteral(ctx);
    }

    public void printModules() {
        for (Map.Entry<String, ArrayList<Module>> e1 : modules.entrySet()) {
//            System.out.println(e1.getKey());
            for (Module m : e1.getValue()) {
                //module
                System.out.println(" " + m.getName());
                for (Map.Entry<String, ArrayList<Struct>> e2 : m.getStructs().entrySet()) {
                    //struct
//                    System.out.println("  "+e2.getKey());
                    for (Struct s : e2.getValue()) {
                        System.out.println("   " + s.getName());
                        //declaration
                        for (Map.Entry<String, ArrayList<Declaration>> e3 : s.getDeclarations().entrySet()) {
//                            System.out.println("    "+e3.getKey());
                            for (Declaration d : e3.getValue()) {
                                System.out.println("     " + d.getName());
                            }
                        }
                    }
                }
            }
        }
    }

    void CheckName() {
        for (Map.Entry<String, ArrayList<Module>> e1 : modules.entrySet()) {
//            System.out.println(e1.getKey());//String
            if (e1.getValue().size() > 1) {
                this.NameErrorMessage.append(e1.getKey() + " 命名重复\n");
            }
            for (Module m : e1.getValue()) {//Arraylist
                //module
//                System.out.println(" " + m.getName());//Arraylist里面的String
                for (Map.Entry<String, ArrayList<Struct>> e2 : m.getStructs().entrySet()) {
                    //struct
                    if (e2.getValue().size() > 1) {
                        this.NameErrorMessage.append(e1.getKey() + "::" + e2.getKey() + " 命名重复\n");
                    }
//                    System.out.println("  "+e2.getKey());
                    for (Struct s : e2.getValue()) {
//                        System.out.println("   " + s.getName());
                        //declaration
                        for (Map.Entry<String, ArrayList<Declaration>> e3 : s.getDeclarations().entrySet()) {
                            if (e3.getValue().size() > 1) {
                                this.NameErrorMessage.append(e1.getKey() + "::" + e2.getKey() + "::" + e3.getKey() + " 命名重复\n");
                            }
                        }
////                            System.out.println("    "+e3.getKey());
//                            for (Declaration d : e3.getValue()) {
//                                System.out.println("     " + d.getName());
//                            }
//                        }
                    }
                }
            }
        }
    }

//    public void ShowError() throws InterruptedException {
//        if(this.DefinitionNameError){
//            for (String s : this.ErrorDefinitionName.keySet()){
//                TimeUnit.MILLISECONDS.sleep(5);
//                System.out.println("在"+this.ErrorDefinitionName.get(s)+"中，命名\""+s+"\"发生冲突");
//            }
//            System.out.println("\n");
//        }
//        if(this.StructNameError){
//            for (String s : this.ErrorStructName.keySet()){
//                TimeUnit.MILLISECONDS.sleep(5);
//                System.out.println("在"+this.ErrorStructName.get(s)+"中，命名\""+s+"\"发生冲突");
//            }
//            System.out.println("\n");
//        }
//        if(this.NotDefiniteError){
//            for(String s:ErrorNotDefinite){
//                System.out.println(s+"未被定义");
//            }
//            System.out.println("\n");
//        }
//        //检查类型错误
//        checkType();
//        if (this.TypeError){
//            for(String s : this.ErrorType){
//                System.out.println(s);
//            }
//        }
//    }

    private void CheckType(Declaration declaration) {
        if (declaration.getIsRight() == 1) {
            String E = "", T = "";//代表元素名，类型，值
            Object V = new Object();
            try {
//            System.out.println((2<<15)-1);
                E = declaration.getName();
                T = declaration.getType();
                if (declaration.getIsArray() == -1) {//不是数组
                    V = declaration.getValue();
                    if (T.equals("short")) {
                        int I = Integer.parseInt(V.toString());
//                    System.out.println(I);
                        if (I < -32768 || I > 32767) {//溢出变复数
                            this.TypeErrorMessage.append(E + "存在溢出错误," + T + "不可赋值为" + I + "\n");
                            declaration.setIsRight(4);
                        }
                    }
                    if (T.startsWith("int")) {
                        //没报错就通过好了
                        int I = Integer.parseInt(V.toString());
                    }
                    if (T.equals("boolean")) {
                        //没报错就通过好了
                        Boolean I = Boolean.valueOf(V.toString());
                    }
//                    else if (T.equals("char")) {
//                        if (V.toString().length() > 1) {
//                            this.TypeErrorMessage.append(E + "存在类型错误," + T + "不可赋值为" + V+"\n");
//                        }
//                    }
                } else {//是数组
                    if (declaration.getValue() instanceof ArrayList<?>) {
                        V = declaration.getValue();
                        for (Object s : (ArrayList<?>) V) {
                            if (T.equals("short")) {
                                int I = Integer.parseInt(s.toString());
                                if (I < -32768 || I > 32767) {//溢出变复数
                                    this.TypeErrorMessage.append(E + "存在溢出错误," + T + "[]不可赋值为" + I + "\n");
                                    declaration.setIsRight(4);
                                }
                            }
                            if (T.startsWith("int")) {
                                int I = Integer.parseInt(V.toString());
                            }
                        }
                    } else {
                        this.TypeErrorMessage.append("数组定义处理出错,");
                    }

                }

            } catch (Exception e) {
//                if(!T.equals(V.getClass().toString())){
//                    this.ErrorType.add(E+"存在类型错误"+T+"不可赋值为"+V);
//                    this.TypeError=true;
//                }
                if (V instanceof ArrayList<?>) {
                    this.TypeErrorMessage.append(E + "存在类型错误," + T + "[]不可赋值为" + V + "\n");
                } else {
                    this.TypeErrorMessage.append(E + "存在类型错误," + T + "不可赋值为" + V + "\n");
                }
                declaration.setIsRight(4);
            }
        }
    }
}

//    private void checkScopedName(MIDLParser.Simple_declaratorContext ctx){
//        String StructName=ctx.parent.parent.parent.parent.getChild(1).toString();
//        HashMap<String,String> h=new HashMap<>();
//        if(this.IDsInStruct.get(ctx.ID().toString())==null){
//            //为新名保存其存在的空间
//            h.put(ctx.ID()+"0",StructName);
//            this.IDsInStruct.put(ctx.ID().toString(),h);
//        }else {
//            //如果已有命名，检查命名空间下的所有命名时候有重复，有报错，无新建
//            h=this.IDsInStruct.get(ctx.ID().toString());
//            for(String s : h.values()){
//                if (StructName.equals(s)) {
//                    this.DefinitionNameError = true;
//                    break;
//                }
//            }
//            //在h中，用IDn来区分不通变量名
//            h.put(ctx.ID().toString()+h.size(),StructName);
//            this.IDsInStruct.replace(ctx.ID().toString(),h);
//            ErrorDefinitionName.put(ctx.ID().toString(),StructName);
////            System.out.println(ctx.ID()+"属于"+StructName);
//        }
//    }
//
//    private void checkScopedName(MIDLParser.Array_declaratorContext ctx) {
//        String StructName=ctx.parent.parent.parent.parent.getChild(1).toString();
//        HashMap<String,String> h=new HashMap<>();
//        if(this.IDsInStruct.get(ctx.ID().toString())==null){
//            //为新名保存其存在的空间
//            h.put(ctx.ID()+"0",StructName);
//            this.IDsInStruct.put(ctx.ID().toString(),h);
//        }else {
//            //如果已有命名，检查命名空间下的所有命名时候有重复，有报错，无新建
//            h=this.IDsInStruct.get(ctx.ID().toString());
//            for(String s : h.values()){
//                if (StructName.equals(s)) {
//                    this.DefinitionNameError = true;
//                    break;
//                }
//            }
//            //在h中，用IDn来区分不通变量名
//            h.put(ctx.ID().toString()+h.size(),StructName);
//            this.IDsInStruct.replace(ctx.ID().toString(),h);
//            ErrorDefinitionName.put(ctx.ID().toString(),StructName);
//            System.out.println(ctx.ID()+"属于"+StructName);
//        }
////        System.out.println(ctx.ID()+"属于"+StructName);
//    }
//
//    private void checkStructName(MIDLParser.Type_declContext ctx) {
//        String ModuleName=ctx.parent.parent.getChild(1).toString();
//        HashMap<String,String> h=new HashMap<>();
//        if(this.StructName.get(ctx.ID().toString())==null){
//            //为新名保存其存在的空间
//            h.put(ctx.ID()+"0",ModuleName);
//            this.StructName.put(ctx.ID().toString(),h);
//        }else {
//            //如果已有命名，检查命名空间下的所有命名时候有重复，有报错，无新建
//            h=this.StructName.get(ctx.ID().toString());
//            for(String s : h.values()){
//                if (ModuleName.equals(s)) {
//                    this.StructNameError = true;
//                    break;
//                }
//            }
//            //在h中，用IDn来区分不通变量名
//            h.put(ctx.ID().toString()+h.size(),ModuleName);
//            this.StructName.replace(ctx.ID().toString(),h);
//            this.ErrorStructName.put(ctx.ID().toString(),ModuleName);
//        }
////        System.out.println(ctx.ID()+"属于"+ModuleName);
//    }
//    private void checkStructName(MIDLParser.Struct_typeContext ctx) {
//        String ModuleName=ctx.parent.parent.getChild(1).toString();
//        HashMap<String,String> h=new HashMap<>();
//        if(this.StructName.get(ctx.ID().toString())==null){
//            //为新名保存其存在的空间
//            h.put(ctx.ID()+"0",ModuleName);
//            this.StructName.put(ctx.ID().toString(),h);
//        }else {
//            //如果已有命名，检查命名空间下的所有命名时候有重复，有报错，无新建
//            h=this.StructName.get(ctx.ID().toString());
//            for(String s : h.values()){
//                if (StructName.equals(s)) {
//                    this.DefinitionNameError = true;
//                    break;
//                }
//            }
//            //在h中，用IDn来区分不通变量名
//            h.put(ctx.ID().toString()+h.size(),ModuleName);
//            this.StructName.replace(ctx.ID().toString(),h);
//            ErrorDefinitionName.put(ctx.ID().toString(),ModuleName);
//        }
//        System.out.println(ctx.ID()+"属于"+ModuleName);
//    }
//}
