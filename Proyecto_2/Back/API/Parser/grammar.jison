%{
    const ListError = require('./Error/ListError.js');
    const Error = require('./Error/Error.js');
%}

// ---> Definicion Lexica Del Parser
%lex

%options case-insensitive

%%
\s+                                     // IGNORAR ESPACIOS EN BLANCO
\t+                                     // IGNORAR TABULACIONES
\r+                                     // IGNORAR RETORNOS DE CARRO
\n+                                     // IGNORAR SALTOS DE LINEA
"//".*                                  // COMENTARIO DE LINEA SIMPLE
[/][*][^*]*[*]+([^/*][^*]*[*]+)*[/]     // COMENTARIO MULTILINEA

// ---> Palabras Reservadas
"int"           return 'INT';
"double"        return 'DOUBLE';
"boolean"       return 'BOOLEAN';
"char"          return 'CHAR';
"string"        return 'STRING';
"void"          return 'VOID';
"true"          return 'TRUE';
"false"         return 'FALSE';
"new"           return 'NEW';
"if"            return 'IF';
"else"          return 'ELSE';
"elif"          return 'ELIF';
"switch"        return 'SWTICH';
"case"          return 'CASE';
"while"         return 'WHILE';
"for"           return 'FOR';
"do"            return 'DO';
"until"         return 'UNTIL';
"break"         return 'BREAK';
"continue"      return 'CONTINUE';
"return"        return 'RETURN';
"print"         return 'PRINT';
"println"       return 'PRINTLN';
"toLower"       return 'TOLOWER';
"toUpper"       return 'TOUPPER';
"round"         return 'ROUND';
"length"        return 'LENGTH';
"typeof"        return 'TYPEOF';
"toString"      return 'TOSTRING';
"toCharArray"   return 'TOCHARARR';
"push"          return 'PUSH';
"pop"           return 'POP';
"run"           return 'RUN';
"default"       return 'DEFAULT';

// ---> Simbolos De La Gramatica
"++"        return 'INC';
"--"        return 'DEC';
"=="        return 'IG';
"!="        return 'DIF';
"<="        return 'MEN_IG';
">="        return 'MAY_IG';
"||"        return 'OR';
"&&"        return 'AND';
">"        return 'MAYOR';
"<"        return 'MENOR';
"+"        return 'SUM';
"-"        return 'RES';
"*"        return 'MULT';
"/"        return 'DIV';
"^"        return 'POT';
"%"        return 'MOD';
"!"        return 'NOT';
"("        return 'P_OP';
")"        return 'P_CL';
";"        return 'PCOMA';
"="        return 'IGUAL';
":"        return 'DPUNTOS';
"?"        return 'TERTKN';
","        return 'COMA';
"."        return 'PUNTO';
"["        return 'C_OP';
"]"        return 'C_CL';
"{"        return 'LL_OP';
"}"        return 'LL_CL';

// ---> Expresiones Regulares
\'([a-zA-Z0-9]|(\\\\)|(\\n)|(\\t)|(\\\')|(\\\"))\'      return 'CARACTER';
\"[^\"]*\"                                              return 'CADENA';
[0-9]+("."[0-9]+)?\b  	                                return 'DECIMAL';
[0-9]+\b				                                return 'ENTERO';
([a-zA-Z])[a-zA-Z0-9_]*	   	                            return 'IDENTIFICADOR';
<<EOF>>                                                 return 'EOF';

. {
    console.log('Error Lexico: ' + yytext + ', en la linea: ' + yylloc.first_line + ', en la columna: ' + yylloc.first_column);
}

/lex

// ---> Definicion De Precedencias Del Parser
%left "OR"
%left "AND"
%right "NOT"
%left "TERTKN"
%left P_CAST
%left "IG" "DIF" "MAYOR" "MENOR" "MAY_IG" "MEN_IG"
%left "SUM" "RES"
%left "MULT" "DIV" "MOD"
%left "POT"
%left UMENOS

// ---> Simbolo De Inicio
%start INICIO

// ---> Definicion Sintactica Del Parser
%%

INICIO
    : INSTRUCCIONES EOF
;

INSTRUCCIONES 
    : INSTRUCCION INSTRUCCIONES
    | INSTRUCCION
    | METODO
    | EJECTUAR
    | error { console.error('Hay Un Error Sintactico En La Linea: ' + this._$.first_line + ', En La Columna: ' + (this._$.first_column + 1) + ' Lexema: ' + yytext); }
;

INSTRUCCION
    : DECLARACION_VARIABLES PCOMA
    | DECLARACION_VECTORES
    | ASIGNACION_VARIABLES PCOMA
    | ASIGNACION_VECTORES
    | RETURN EXPRESION PCOMA
    | CONTINUE PCOMA
    | LLAMADAS PCOMA
    | WHILE_CICLO
    | SWITCH_CASE
    | BREAK PCOMA
    | FUNC_VECTOR
    | FOR_CICLO
    | DO_WHILE
    | DO_UNTIL
    | INC_DEC PCOMA
    | PRINTS
    | IFS
;

DECLARACION_VARIABLES
    : TIPO_DATO IDENTIFICADORES
    | TIPO_DATO IDENTIFICADORES IGUAL EXPRESION
;

DECLARACION_VECTORES
    : TIPO_DATO C_OP C_CL IDENTIFICADOR IGUAL NEW TIPO_DATO C_OP EXPRESION C_CL PCOMA
    | TIPO_DATO C_OP C_CL C_OP C_CL IDENTIFICADOR IGUAL NEW TIPO_DATO C_OP EXPRESION C_CL C_OP EXPRESION C_CL PCOMA
    | TIPO_DATO C_OP C_CL IDENTIFICADOR IGUAL LL_OP LISTA_VALORES LL_CL PCOMA
    | TIPO_DATO C_OP C_CL C_OP C_CL IDENTIFICADOR IGUAL LL_OP LL_OP LISTA_VALORES LL_CL COMA LL_OP LISTA_VALORES LL_CL LL_CL PCOMA
;

ASIGNACION_VARIABLES
    : IDENTIFICADORES IGUAL EXPRESION
;

ASIGNACION_VECTORES
    : IDENTIFICADOR C_OP EXPRESION C_CL IGUAL EXPRESION PCOMA
    | IDENTIFICADOR C_OP EXPRESION C_CL C_OP EXPRESION C_CL IGUAL EXPRESION PCOMA
;

IDENTIFICADORES
    : IDENTIFICADOR COMA IDENTIFICADORES
    | IDENTIFICADOR
;

TIPO_DATO
    : INT
    | DOUBLE
    | BOOLEAN
    | CHAR
    | STRING
    | VOID
;

LISTA_VALORES
    : EXPRESION COMA LISTA_VALORES
    | EXPRESION
;

EXPRESION // ----> HAY PROBLEMAS AGREGANDO CASTEO Y TERNARIA, PROBLEMA AMBIGUEDAD
    : IDENTIFICADOR C_OP EXPRESION C_CL
    | IDENTIFICADOR C_OP EXPRESION C_CL C_OP EXPRESION C_CL
    | P_OP EXPRESION P_CL
    | LLAMADAS
    | FUNC_NAT
    | ARITMETICA
    | RELACIONAL
    | TERNARIA
    | CASTEO
    | LOGICO
    | INC_DEC
    | VALOR
;

ARITMETICA
    : EXPRESION SUM EXPRESION
    | EXPRESION RES EXPRESION
    | EXPRESION MULT EXPRESION
    | EXPRESION DIV EXPRESION
    | EXPRESION POT EXPRESION
    | EXPRESION MOD EXPRESION
;

RELACIONAL
    : EXPRESION MAYOR EXPRESION
    | EXPRESION MENOR EXPRESION
    | EXPRESION MAY_IG EXPRESION
    | EXPRESION MEN_IG EXPRESION
    | EXPRESION IG EXPRESION
    | EXPRESION DIF EXPRESION
;

LOGICO
    : EXPRESION OR EXPRESION
    | EXPRESION AND EXPRESION
    | NOT EXPRESION
;

TERNARIA
    : EXPRESION TERTKN EXPRESION DPUNTOS EXPRESION
;

CASTEO
    : P_OP TIPO_DATO P_CL EXPRESION %prec P_CAST
;

INC_DEC
    : IDENTIFICADOR INC
    | IDENTIFICADOR DEC
;

VALOR
    : MENOS EXPRESION %prec UMENOS
    | ENTERO
    | DECIMAL
    | CADENA
    | CARACTER
    | TRUE
    | FALSE
    | IDENTIFICADOR
;

FUNC_NAT
    : TOLOWER P_OP EXPRESION P_CL
    | TOUPPER P_OP EXPRESION P_CL
    | ROUND P_OP EXPRESION P_CL
    | LENGTH P_OP EXPRESION P_CL
    | TYPEOF P_OP EXPRESION P_CL
    | TOSTRING P_OP EXPRESION P_CL
    | TOCHARARR P_OP EXPRESION P_CL
;

FUNC_VECTOR
    : IDENTIFICADOR PUNTO PUSH P_OP EXPRESION P_CL PCOMA
    | IDENTIFICADOR PUNTO POP P_OP P_CL PCOMA
;

PRINTS
    : PRINT P_OP EXPRESION P_CL PCOMA
    | PRINTLN P_OP EXPRESION P_CL PCOMA
;

LLAMADAS
    : IDENTIFICADOR P_OP LISTA_VALORES P_CL
    | IDENTIFICADOR P_OP P_CL
;

METODO
    : IDENTIFICADOR P_OP PARAMETROS P_CL DPUNTOS TIPO_DATO LL_OP INSTRUCCIONES LL_CL
    | IDENTIFICADOR P_OP P_CL DPUNTOS TIPO_DATO LL_OP INSTRUCCIONES LL_CL
    | IDENTIFICADOR P_OP PARAMETROS P_CL DPUNTOS TIPO_DATO LL_OP LL_CL
    | IDENTIFICADOR P_OP P_CL TIPO_DATO LL_OP LL_CL
;

PARAMETROS
    : TIPO_DATO IDENTIFICADOR COMA PARAMETROS
    | TIPO_DATO IDENTIFICADOR
;

EJECTUAR    // ---> RUN NO SE RECONOCE EN EL ANALISIS
    : RUN IDENTIFICADOR P_OP LISTA_VALORES P_CL PCOMA
    | RUN IDENTIFICADOR P_OP P_CL PCOMA
;

IFS
    : IFSENCILLO
    | IFSENCILLO ELSE LL_OP INSTRUCCIONES LL_CL
    | IFSENCILLO ELIFS
    | IFSENCILLO ELIFS ELSE LL_OP INSTRUCCIONES LL_CL
;

IFSENCILLO
    : IF P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL
;

ELIFS
    : ELIF P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL
    | ELIF P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL ELIFS
;

SWITCH_CASE
    : SWITCH P_OP EXPRESION P_CL LL_OP CASES_LIST LL_CL
;

CASES_LIST
    : CASE EXPRESION DPUNTOS INSTRUCCIONES CASES_LIST
    | DEFAULT DPUNTOS INSTRUCCIONES CASES_LIST
    | CASE EXPRESION DPUNTOS INSTRUCCIONES
    | DEFAULT DPUNTOS INSTRUCCIONES
;

WHILE_CICLO
    : WHILE P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL
;

FOR_CICLO
    : FOR P_OP DECLARACION_VARIABLES PCOMA RELACIONAL PCOMA ACTUALIZACION P_CL LL_OP INSTRUCCIONES LL_CL
    | FOR P_OP ASIGNACION_VARIABLES PCOMA RELACIONAL PCOMA ACTUALIZACION P_CL LL_OP INSTRUCCIONES LL_CL
;

ACTUALIZACION
    : INC_DEC
    | ASIGNACION_VARIABLES
;

DO_WHILE
    : DO LL_OP INSTRUCCIONES LL_CL WHILE P_OP EXPRESION P_CL PCOMA
;

DO_UNTIL
    : DO LL_OP INSTRUCCIONES LL_CL UNTIL P_OP EXPRESION P_CL PCOMA
;

