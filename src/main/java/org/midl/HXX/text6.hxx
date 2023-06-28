/*below is the AST of the MIDL code,paste the code below to Typora can see the graphic tree
stateDiagram
specification --> definition
 definition --> module
  module -->m definition definition
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> u
    struct_type --> member_list
     member_list --> type_spec
     member_list --> declarators
     member_list --> type_spec
     member_list --> declarators
      type_spec --> base_type_spec
       base_type_spec -->  integer_type
        integer_type --> signed_int
         signed_int -->int8
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> A
        simple_declarator --> or_expr
          or_expr --> xor_expr 
           xor_expr --> and_expr 
            and_expr --> shift_expr 
             shift_expr --> add_expr 
              add_expr --> mult_expr 
               mult_expr --> unary_expr 
                unary_expr --> literal
                 literal --> 8
      type_spec --> base_type_spec
       base_type_spec -->  integer_type
        integer_type --> signed_int
         signed_int -->int16
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> B
        simple_declarator --> or_expr
          or_expr --> xor_expr 
           xor_expr --> and_expr 
            and_expr --> shift_expr 
             shift_expr --> add_expr 
              add_expr --> mult_expr 
               mult_expr --> unary_expr 
                unary_expr --> literal
                 literal --> 9
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> x
    struct_type --> member_list
     member_list --> type_spec
     member_list --> declarators
     member_list --> type_spec
     member_list --> declarators
      type_spec --> base_type_spec
       base_type_spec --> char
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> C
        simple_declarator --> or_expr
          or_expr --> xor_expr 
           xor_expr --> and_expr 
            and_expr --> shift_expr 
             shift_expr --> add_expr 
              add_expr --> mult_expr 
               mult_expr --> unary_expr 
                unary_expr --> literal
                 literal --> 'a'
      type_spec --> base_type_spec
       base_type_spec --> string
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> D
        simple_declarator --> or_expr
          or_expr --> xor_expr 
           xor_expr --> and_expr 
            and_expr --> shift_expr 
             shift_expr --> add_expr 
              add_expr --> mult_expr 
               mult_expr --> unary_expr 
                unary_expr --> literal
                 literal --> "a"
*/
#ifndef text6.hxx_h
#define text6.hxx_h

#ifndef rti_me_cpp_hxx
#include "rti_me_cpp.hxx"
#endif

#ifdef NDDS_USER_DLL_EXPORT
#if (defined(RTI_WIN32) || defined(RTI_WINCE))
/* If the code is building on Windows, start exporting symbols. */
#undef NDDSUSERDllExport
#define NDDSUSERDllExport __declspec(dllexport)
#endif
#else
#undef NDDSUSERDllExport
#define NDDSUSERDllExport
#endif

struct m_uSeq;
class m_uTypeSupport;
class m_uDataWriter;
class m_uDataReader;


class m_u
{
  public:
    typedef struct m_uSeq Seq;
    typedef m_uTypeSupport TypeSupport;
    typedef m_uDataWriter DataWriter;
    typedef m_uDataReader DataReader;

    CDR_Int8 = 8
    CDR_Int16 = 9

}

extern const char *m_uTYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(m_uSeq, m_u);

REDA_DEFINE_SEQUENCE_IN_C(m_uSeq, m_u);

NDDSUSERDllExport extern RTI_BOOL
m_u_initialize(m_u* sample)
{
        CDR_Primitive_init_CDR_Int8(&sample-> A);
        CDR_Primitive_init_CDR_Int16(&sample-> B);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
m_u_finalize(m_u* sample)
{
    UNUSED_ARG(sample);
    return RTI_TRUE;
}


#ifdef NDDS_USER_DLL_EXPORT
#if (defined(RTI_WIN32) || defined(RTI_WINCE))
/* If the code is building on Windows, stop exporting symbols. */
#undef NDDSUSERDllExport
#define NDDSUSERDllExport
#endif
#endif

#endif /* struct_nest */


struct m_xSeq;
class m_xTypeSupport;
class m_xDataWriter;
class m_xDataReader;


class m_x
{
  public:
    typedef struct m_xSeq Seq;
    typedef m_xTypeSupport TypeSupport;
    typedef m_xDataWriter DataWriter;
    typedef m_xDataReader DataReader;

    CDR_Char = 'a'
    CDR_String = "a"

}

extern const char *m_xTYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(m_xSeq, m_x);

REDA_DEFINE_SEQUENCE_IN_C(m_xSeq, m_x);

NDDSUSERDllExport extern RTI_BOOL
m_x_initialize(m_x* sample)
{
        CDR_Primitive_init_CDR_Char(&sample-> C);
        CDR_Primitive_init_CDR_String(&sample-> D);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
m_x_finalize(m_x* sample)
{
    UNUSED_ARG(sample);
    return RTI_TRUE;
}


#ifdef NDDS_USER_DLL_EXPORT
#if (defined(RTI_WIN32) || defined(RTI_WINCE))
/* If the code is building on Windows, stop exporting symbols. */
#undef NDDSUSERDllExport
#define NDDSUSERDllExport
#endif
#endif

#endif /* struct_nest */

