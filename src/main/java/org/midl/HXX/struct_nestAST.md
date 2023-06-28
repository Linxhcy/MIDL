```mermaid
stateDiagram
specification --> definition
 definition --> module
  module -->space definition definition
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> A
    struct_type --> member_list
     member_list --> type_spec
     member_list --> declarators
      type_spec --> base_type_spec
       base_type_spec -->  integer_type
        integer_type --> signed_int
         signed_int -->short
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> i1
        simple_declarator --> or_expr
          or_expr --> xor_expr 
           xor_expr --> and_expr 
            and_expr --> shift_expr 
             shift_expr --> add_expr 
              add_expr --> mult_expr 
               mult_expr --> unary_expr 
                unary_expr --> literal
                 literal --> 10
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> B
    struct_type --> member_list
     member_list --> type_spec
     member_list --> declarators
     member_list --> type_spec
     member_list --> declarators
      type_spec --> base_type_spec
       base_type_spec -->  integer_type
        integer_type --> signed_int
         signed_int -->long
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> i2
        simple_declarator --> or_expr
          or_expr --> xor_expr 
           xor_expr --> and_expr 
            and_expr --> shift_expr 
             shift_expr --> add_expr 
              add_expr --> mult_expr 
               mult_expr --> unary_expr 
                unary_expr --> literal
                 literal --> 100
      type_spec --> scoped_name
       scoped_name --> A
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> i3

```