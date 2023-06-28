/*below is the AST of the MIDL code,paste the code below to Typora can see the graphic tree
stateDiagram
specification --> definition definition
 definition --> module
  module -->space1 definition
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> B
    struct_type --> member_list
     member_list --> type_spec
     member_list --> declarators
      type_spec --> base_type_spec
       base_type_spec -->  integer_type
        integer_type --> signed_int
         signed_int -->short
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> a
 definition --> module
  module -->space2 definition
 definition --> type_decl
  type_decl --> struct_type
    struct_type --> A
    struct_type --> member_list
     member_list --> type_spec
     member_list --> declarators
      type_spec --> scoped_name
       scoped_name --> space1
      declarators --> declarator
       declarator --> simple_declarator
        simple_declarator --> b
*/
#ifndef text3.hxx_h
#define text3.hxx_h

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

struct space2_ASeq;
class space2_ATypeSupport;
class space2_ADataWriter;
class space2_ADataReader;


class space2_A
{
  public:
    typedef struct space2_ASeq Seq;
    typedef space2_ATypeSupport TypeSupport;
    typedef space2_ADataWriter DataWriter;
    typedef space2_ADataReader DataReader;

    CDR_Space1::B

}

extern const char *space2_ATYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(space2_ASeq, space2_A);

REDA_DEFINE_SEQUENCE_IN_C(space2_ASeq, space2_A);

NDDSUSERDllExport extern RTI_BOOL
space2_A_initialize(space2_A* sample)
{
        CDR_Primitive_init_CDR_Space1::B(&sample-> b);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
space2_A_finalize(space2_A* sample)
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


struct space1_BSeq;
class space1_BTypeSupport;
class space1_BDataWriter;
class space1_BDataReader;


class space1_B
{
  public:
    typedef struct space1_BSeq Seq;
    typedef space1_BTypeSupport TypeSupport;
    typedef space1_BDataWriter DataWriter;
    typedef space1_BDataReader DataReader;

    CDR_Short

}

extern const char *space1_BTYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(space1_BSeq, space1_B);

REDA_DEFINE_SEQUENCE_IN_C(space1_BSeq, space1_B);

NDDSUSERDllExport extern RTI_BOOL
space1_B_initialize(space1_B* sample)
{
        CDR_Primitive_init_CDR_Short(&sample-> a);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
space1_B_finalize(space1_B* sample)
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

