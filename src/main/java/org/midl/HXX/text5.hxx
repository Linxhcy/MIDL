/*below is the AST of the MIDL code,paste the code below to Typora can see the graphic tree
stateDiagram
specification --> definition
 definition --> module
  module -->m definition
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> s
    struct_type --> member_list
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
*/
#ifndef text5.hxx_h
#define text5.hxx_h

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

struct m_sSeq;
class m_sTypeSupport;
class m_sDataWriter;
class m_sDataReader;


class m_s
{
  public:
    typedef struct m_sSeq Seq;
    typedef m_sTypeSupport TypeSupport;
    typedef m_sDataWriter DataWriter;
    typedef m_sDataReader DataReader;

    CDR_Int8 = 8

}

extern const char *m_sTYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(m_sSeq, m_s);

REDA_DEFINE_SEQUENCE_IN_C(m_sSeq, m_s);

NDDSUSERDllExport extern RTI_BOOL
m_s_initialize(m_s* sample)
{
        CDR_Primitive_init_CDR_Int8(&sample-> A);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
m_s_finalize(m_s* sample)
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

