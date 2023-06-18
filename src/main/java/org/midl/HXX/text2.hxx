/*below is the AST of the MIDL code,paste the code below to Typora can see the graphic tree
stateDiagram
specification --> definition definition
 definition --> module
  module -->space1 definition definition
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
        simple_declarator --> num
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
        simple_declarator --> num
 definition --> module
  module -->space2 definition definition
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
        simple_declarator --> num
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
        simple_declarator --> num
*/
#ifndef text2.hxx_h
#define text2.hxx_h

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

    CDR_Short

}

extern const char *space2_ATYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(space2_ASeq, space2_A);

REDA_DEFINE_SEQUENCE_IN_C(space2_ASeq, space2_A);

NDDSUSERDllExport extern RTI_BOOL
space2_A_initialize(space2_A* sample)
{
        CDR_Primitive_init_CDR_Short(&sample-> num);

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


struct space2_BSeq;
class space2_BTypeSupport;
class space2_BDataWriter;
class space2_BDataReader;


class space2_B
{
  public:
    typedef struct space2_BSeq Seq;
    typedef space2_BTypeSupport TypeSupport;
    typedef space2_BDataWriter DataWriter;
    typedef space2_BDataReader DataReader;

    CDR_Short

}

extern const char *space2_BTYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(space2_BSeq, space2_B);

REDA_DEFINE_SEQUENCE_IN_C(space2_BSeq, space2_B);

NDDSUSERDllExport extern RTI_BOOL
space2_B_initialize(space2_B* sample)
{
        CDR_Primitive_init_CDR_Short(&sample-> num);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
space2_B_finalize(space2_B* sample)
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


struct space1_ASeq;
class space1_ATypeSupport;
class space1_ADataWriter;
class space1_ADataReader;


class space1_A
{
  public:
    typedef struct space1_ASeq Seq;
    typedef space1_ATypeSupport TypeSupport;
    typedef space1_ADataWriter DataWriter;
    typedef space1_ADataReader DataReader;

    CDR_Short

}

extern const char *space1_ATYPENAME;

REDA_DEFINE_SEQUENCE_STRUCT(space1_ASeq, space1_A);

REDA_DEFINE_SEQUENCE_IN_C(space1_ASeq, space1_A);

NDDSUSERDllExport extern RTI_BOOL
space1_A_initialize(space1_A* sample)
{
        CDR_Primitive_init_CDR_Short(&sample-> num);

    return RTI_TRUE;
}

NDDSUSERDllExport extern RTI_BOOL
space1_A_finalize(space1_A* sample)
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
        CDR_Primitive_init_CDR_Short(&sample-> num);

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

