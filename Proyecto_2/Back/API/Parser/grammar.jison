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
    | error INSTRUCCIONES { console.error('Hay Un Error Sintactico En La Linea: ' + this._$.first_line + ', En La Columna: ' + (this._$.first_column + 1)); }
;

INSTRUCCION
    : DECLARACION_VARIABLES
    | DECLARACION_VECTORES
    | ASIGNACION_VARIABLES
    | ASIGNACION_VECTORES
;

DECLARACION_VARIABLES
    : TIPO_DATO IDENTIFICADORES PCOMA
    | TIPO_DATO IDENTIFICADORES IGUAL EXPRESION PCOMA
;

DECLARACION_VECTORES
    : TIPO_DATO C_OP C_CL IDENTIFICADOR IGUAL NEW TIPO_DATO C_OP EXPRESION C_CL PCOMA
    | TIPO_DATO C_OP C_CL C_OP C_CL IDENTIFICADOR IGUAL NEW TIPO_DATO C_OP EXPRESION C_CL C_OP EXPRESION C_CL PCOMA
    | TIPO_DATO C_OP C_CL IDENTIFICADOR IGUAL LL_OP LISTA_VALORES LL_CL PCOMA
    | TIPO_DATO C_OP C_CL C_OP C_CL IDENTIFICADOR IGUAL LL_OP LL_OP LISTA_VALORES LL_CL COMA LL_OP LISTA_VALORES LL_CL LL_CL PCOMA
;

ASIGNACION_VARIABLES
    : IDENTIFICADORES IGUAL EXPRESION PCOMA
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
;

LISTA_VALORES
    : EXPRESION COMA LISTA_VALORES
    | EXPRESION
;

EXPRESION // ----> HAY PROBLEMAS AGREGANDO CASTEO Y TERNARIA, PROBLEMA AMBIGUEDAD
    : IDENTIFICADOR C_OP EXPRESION C_CL
    | IDENTIFICADOR C_OP EXPRESION C_CL C_OP EXPRESION C_CL
    | P_OP EXPRESION P_CL
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






