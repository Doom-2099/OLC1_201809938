%{
    // ---> ERRORES
    const ListError = require('./Error/ListError.js');
    const Error = require('./Error/Error.js');

    // ---> EXPRESIONES
    const Expresion = require('./Context/Expresion/Expresion.js');
    const TD = require('./Context/Expresion/TipoExpresion.js').TIPO_DATO;
    const TOL = require('./Context/Expresion/TipoExpresion.js').TIPO_OP_LOGICA;
    const TOR = require('./Context/Expresion/TipoExpresion.js').TIPO_OP_RELACIONAL;
    const TOA = require('./Context/Expresion/TipoExpresion.js').TIPO_OP_ARITMETICA;

    // ---> INSTRUCCIONES
    const Instruccion = require('./Context/Instruccion/Instruccion.js');
    const IDEC = require('./Context/Instruccion/TipoInstruccion.js').INST_DEC;
    const IASIG = require('./Context/Instruccion/TipoInstruccion.js').INST_ASIG;
    const ICON = require('./Context/Instruccion/TipoInstruccion.js').INST_CONTROL;
    const ITRAN = require('./Context/Instruccion/TipoInstruccion.js').INST_TRANSFERENCIA;
    const ICICL = require('./Context/Instruccion/TipoInstruccion.js').INST_CICLOS;
    const NATIVA = require('./Context/Instruccion/TipoInstruccion.js').INST_NATIVA;
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
"switch"        return 'SWITCH';
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
".push"          return 'PUSH';
".pop"           return 'POP';
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
"["        return 'C_OP';
"]"        return 'C_CL';
"{"        return 'LL_OP';
"}"        return 'LL_CL';

// ---> Expresiones Regulares
\'([a-zA-Z0-9]|(\\\\)|(\\n)|(\\t)|(\\\')|(\\\"))\'      return 'CARACTER';
\"[^\"]*\"                                              return 'CADENA';
[0-9]+("."[0-9]+){1}\b  	                            return 'DECIMAL';
[0-9]+\b				                                return 'ENTERO';
([a-zA-Z])[a-zA-Z0-9_]*	   	                            return 'IDENTIFICADOR';
<<EOF>>                                                 return 'EOF';

. {
    var msg = 'Error Lexico: ' + yytext + ', en la linea: ' + yylloc.first_line + ', en la columna: ' + yylloc.first_column;
    ListError.getInstance().addLista(new Error(yytext, yylloc.first_line, (yylloc.first_column + 1), 'LEXICO', msg));
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
        {
            return $1;
        }
;

INSTRUCCIONES 
    : INSTRUCCION INSTRUCCIONES
        {
            $2.unshift($1);
            $$ = $2;
        }

    | EJECTUAR INSTRUCCIONES
        {
            $2.unshift($1);
            $$ = $2;
        }

    | METODO INSTRUCCIONES
        {
            $2.unshift($1);
            $$ = $2;
        }

    | INSTRUCCION
        {
            $$ = [$1];
        }

    | EJECTUAR
        {
            $$ = [$1];
        }

    | METODO
        {
            $$ = [$1];
        }

    | error { 
        var msg = 'Hay Un Error Sintactico En La Linea: ' + this._$.first_line + ', En La Columna: ' + (this._$.first_column + 1) + ' Lexema: ' + yytext;
        ListError.getInstance().addLista(new Error(yytext, this._$.first_line, (this._$.first_column + 1), 'SINTACTICO', msg));
;

INSTRUCCION
    : DECLARACION_VARIABLES PCOMA
        {
            $$ = $1;
        }

    | DECLARACION_VECTORES
        {
            $$ = $1;
        }

    | ASIGNACION_VARIABLES PCOMA
        {
            $$ = $1;
        }

    | ASIGNACION_VECTORES
        {
            $$ = $1;
        }

    | RETURN EXPRESION PCOMA
        {
            $$ = Instruccion.Transferencia(ITRAN.RETURN, $2, this._$.first_line, (this._$.first_column + 1));
        }

    | CONTINUE PCOMA
        {
            $$ = Instruccion.Transferencia(ITRAN.CONTINUE, undefined, this._$.first_line, (this._$.first_column + 1));
        }

    | BREAK PCOMA
        {
            $$ = Instruccion.Transferencia(ITRAN.BREAK, undefined, this._$.first_line, (this._$.first_column + 1));
        }

    | CASTEO PCOMA
        {
            $$ = $1;
        }

    | LLAMADAS PCOMA
        {
            $$ = $1;
        }

    | WHILE_CICLO
        {
            $$ = $1;
        }

    | SWITCH_CASE
        {
            $$ = $1;
        }

    | FUNC_VECTOR
        {
            $$ = $1;
        }

    | FOR_CICLO
        {
            $$ = $1;
        }

    | DO_WHILE
        {
            $$ = $1;
        }

    | DO_UNTIL
        {
            $$ = $1;
        }

    | INC_DEC PCOMA
        {
            $$ = $1;
        }

    | PRINTS
        {
            $$ = $1;
        }

    | IFS
        {
            $$ = $1;
        }
;

DECLARACION_VARIABLES
    : TIPO_DATO IDENTIFICADORES
        {
            $$ = Instruccion.Declaracion($2, IDEC.DEC, $1, undefined, this._$.first_line, (this._$.first_column + 1));
        }

    | TIPO_DATO IDENTIFICADORES IGUAL EXPRESION
        {
            $$ = Instruccion.Declaracion($2, IDEC.DEC, $1, $4, this._$.first_line, (this._$.first_column + 1));
        }
;

DECLARACION_VECTORES
    : TIPO_DATO C_OP C_CL IDENTIFICADOR IGUAL NEW TIPO_DATO C_OP EXPRESION C_CL PCOMA
        {
            $$ = Instruccion.DeclaracionVector1($4, IDEC.DEC_VECTOR_T1_D1, $1, $9,  this._$.first_line, (this._$.first_column + 1));
        }

    | TIPO_DATO C_OP C_CL C_OP C_CL IDENTIFICADOR IGUAL NEW TIPO_DATO C_OP EXPRESION C_CL C_OP EXPRESION C_CL PCOMA
        {
            $$ = Instruccion.DeclaracionVector1($6, IDEC.DEC_VECTOR_T1_D2, $1, [$11, $14], this._$.first_line, (this._$.first_column + 1));
        }

    | TIPO_DATO C_OP C_CL IDENTIFICADOR IGUAL LL_OP LISTA_VALORES LL_CL PCOMA
        {
            $$ = Instruccion.DeclaracionVector2($4, IDEC.DEC_VECTOR_T2_D1, $1, $7, this._$.first_line, (this._$.first_column + 1));
        }

    | TIPO_DATO C_OP C_CL C_OP C_CL IDENTIFICADOR IGUAL LL_OP LL_OP LISTA_VALORES LL_CL COMA LL_OP LISTA_VALORES LL_CL LL_CL PCOMA
        {
            $$ = Instruccion.DeclaracionVector2($6, IDEC.DEC_VECTOR_T2_D2, $1, [$10, $14], this._$.first_line, (this._$.first_column + 1));
        }
;

ASIGNACION_VARIABLES
    : IDENTIFICADORES IGUAL EXPRESION
        {
            $$ = Instruccion.Asignacion($1, IASIG.ASIGNACION, $3, this._$.first_line, (this._$.first_column + 1));
        }
;

ASIGNACION_VECTORES
    : IDENTIFICADOR C_OP EXPRESION C_CL IGUAL EXPRESION PCOMA
        {
            $$ = Instruccion.ModificacionVector($1, IASIG.MOD_VECTOR_D1, $3, $6, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR C_OP EXPRESION C_CL C_OP EXPRESION C_CL IGUAL EXPRESION PCOMA
        {
            $$ = Instruccion.ModificacionVector($1, IASIG.MOD_VECTOR_D2, [$3, $6], $9, this._$.first_line, (this._$.first_column + 1));
        }
;

IDENTIFICADORES
    : IDENTIFICADOR COMA IDENTIFICADORES
        {
            $3.unshift($1);
            $$ = $3;
        }

    | IDENTIFICADOR
        {
            $$ = [$1];
        }
;

TIPO_DATO
    : INT
        {
            $$ = TD.INT;
        }

    | DOUBLE
        {
            $$ = TD.DOUBLE;
        }

    | BOOLEAN
        {
            $$ = TD.BOOL;
        }

    | CHAR
        {
            $$ = TD.CHAR;
        }
        
    | STRING
        {
            $$ = TD.STRING;
        }

    | VOID
        {
            $$ = TD.VOID;
        }
;

LISTA_VALORES
    : EXPRESION COMA LISTA_VALORES
        {
            $3.unshift($1);
            $$ = $3;
        }

    | EXPRESION
        {
            $$ = [$1];
        }
;

EXPRESION 
    : IDENTIFICADOR C_OP EXPRESION C_CL
        {
            $$ = Expresion.nuevoValorArray($1, $3, TD.ARRAY_D1, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR C_OP EXPRESION C_CL C_OP EXPRESION C_CL
        {
            $$ = Expresion.nuevoValorArray($1, [$3, $6], TD.ARRAY_D2, this._$.first_line, (this._$.first_column + 1));
        }

    | P_OP EXPRESION P_CL
        {
            $$ = Expresion.nuevaOperacion($2, undefined, TOA.AGRUPACION, this._$.first_line, (this._$.first_column + 1));
        }

    | LLAMADAS
        {
            $$ = $1;
        }

    | FUNC_NAT
        {
            $$ = $1;
        }

    | ARITMETICA
        {
            $$ = $1;
        }

    | RELACIONAL
        {
            $$ = $1;
        }

    | TERNARIA
        {
            $$ = $1;
        }

    | CASTEO
        {
            $$ = $1;
        }

    | LOGICO
        {
            $$ = $1;
        }

    | INC_DEC
        {
            $$ = $1;
        }

    | VALOR
        {
            $$ = $1;
        }
;

ARITMETICA
    : EXPRESION SUM EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOA.SUMA, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION RES EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOA.RESTA, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION MULT EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOA.MULTIPLICACION, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION DIV EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOA.DIVISION, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION POT EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOA.POTENCIA, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION MOD EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOA.MODULO, this._$.first_line, (this._$.first_column + 1));
        }
;

RELACIONAL
    : EXPRESION MAYOR EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOR.MAYOR, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION MENOR EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOR.MENOR, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION MAY_IG EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOR.MAYORIG, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION MEN_IG EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOR.MENORIG, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION IG EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOR.IGUAL, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION DIF EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOR.DIFERENTE, this._$.first_line, (this._$.first_column + 1));
        }
;

LOGICO
    : EXPRESION OR EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOL.OR, this._$.first_line, (this._$.first_column + 1));
        }

    | EXPRESION AND EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, $3, TOL.AND, this._$.first_line, (this._$.first_column + 1));
        }

    | NOT EXPRESION
        {
            $$ = Expresion.nuevaOperacion($1, undefined, TOL.NOT, this._$.first_line, (this._$.first_column + 1));
        }
;

TERNARIA
    : EXPRESION TERTKN EXPRESION DPUNTOS EXPRESION
        {
            $$ = Expresion.nuevaTernaria(TOA.TERNARIA, $1, $3, $5, this._$.first_line, (this._$.first_column + 1));
        }
;

CASTEO
    : P_OP TIPO_DATO P_CL EXPRESION %prec P_CAST
        {
            $$ = Instruccion.Casteo(IASIG.CASTEO, $2, $4, this._$.first_line, (this._$.first_column + 1));
        }
;

INC_DEC
    : IDENTIFICADOR INC
        {
            $$ = Expresion.nuevaOperacion($1, undefined, TOA.INCREMENTO, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR DEC
        {
            $$ = Expresion.nuevaOperacion($1, undefined, TOA.DECREMENTO, this._$.first_line, (this._$.first_column + 1));
        }
;

VALOR
    : MENOS EXPRESION %prec UMENOS
        {
            $$ = Expresion.nuevaOperacion($2, undefined, TOA.NEGATIVO, this._$.first_line, (this._$.first_column + 1));
        }

    | ENTERO
        {
            $$ = Expresion.nuevoValor($1, TD.INT, this._$.first_line, (this._$.first_column + 1));
        }

    | DECIMAL
        {
            $$ = Expresion.nuevoValor($1, TD.DOUBLE, this._$.first_line, (this._$.first_column + 1));
        }

    | CADENA
        {
            $$ = Expresion.nuevoValor($1, TD.STRING, this._$.first_line, (this._$.first_column + 1));
        }

    | CARACTER
        {
            $$ = Expresion.nuevoValor($1, TD.CHAR, this._$.first_line, (this._$.first_column + 1));
        }

    | TRUE
        {
            $$ = Expresion.nuevoValor($1, TD.BOOL, this._$.first_line, (this._$.first_column + 1));
        }

    | FALSE
        {
            $$ = Expresion.nuevoValor($1, TD.BOOL, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR
        {
            $$ = Expresion.nuevoValor($1, TD.IDENTIFICADOR, this._$.first_line, (this._$.first_column + 1));
        }
;

FUNC_NAT
    : TOLOWER P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.TOLOWER, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | TOUPPER P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.TOUPPER, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | ROUND P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.ROUND, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | LENGTH P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.LENGTH, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | TYPEOF P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.TYPEOF, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | TOSTRING P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.TOSTRING, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | TOCHARARR P_OP EXPRESION P_CL
        {
            $$ = Instruccion.Nativas(NATIVA.TOCHARARRAY, $3, this._$.first_line, (this._$.first_column + 1));
        }
;

FUNC_VECTOR
    : IDENTIFICADOR PUSH P_OP EXPRESION P_CL PCOMA
        {
            $$ = Instruccion.NativasVec($1, NATIVA.PUSH, $4, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR POP P_OP P_CL PCOMA
        {
            $$ = Instruccion.NativasVec($1, NATIVA.POP, undefined, this._$.first_line, (this._$.first_column + 1));
        }
;

PRINTS
    : PRINT P_OP EXPRESION P_CL PCOMA
        {
            $$ = Instruccion.Nativas(NATIVA.PRINT, $3, this._$.first_line, (this._$.first_column + 1));
        }

    | PRINTLN P_OP EXPRESION P_CL PCOMA
        {
            $$ = Instruccion.Nativas(NATIVA.PRINTLN, $3, this._$.first_line, (this._$.first_column + 1));
        }
;

LLAMADAS
    : IDENTIFICADOR P_OP LISTA_VALORES P_CL
        {
            $$ = Instruccion.LlamadaMetodo($1, $3, IASIG.CALL_METODO, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR P_OP P_CL
        {
            $$ = Instruccion.LlamadaMetodo($1, [], IASIG.CALL_METODO, this._$.first_line, (this._$.first_column + 1));
        }
;

METODO
    : IDENTIFICADOR P_OP PARAMETROS P_CL DPUNTOS TIPO_DATO LL_OP INSTRUCCIONES LL_CL
        {
            $$ = Instruccion.DeclaracionMetodo($1, IDEC.DEC_METODO, $6, $3, $8, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR P_OP P_CL DPUNTOS TIPO_DATO LL_OP INSTRUCCIONES LL_CL
        {
            $$ = Instruccion.DeclaracionMetodo($1, IDEC.DEC_METODO, $6, [], $8, this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR P_OP PARAMETROS P_CL DPUNTOS TIPO_DATO LL_OP LL_CL
        {
            $$ = Instruccion.DeclaracionMetodo($1, IDEC.DEC_METODO, $6, $3, [], this._$.first_line, (this._$.first_column + 1));
        }

    | IDENTIFICADOR P_OP P_CL TIPO_DATO LL_OP LL_CL
        {
            $$ = Instruccion.DeclaracionMetodo($1, IDEC.DEC_METODO, $4, [], [], this._$.first_line, (this._$.first_column + 1));
        }
;

PARAMETROS
    : TIPO_DATO IDENTIFICADOR COMA PARAMETROS
        {      
            var param = Instruccion.Declaracion([$2], IDEC.DEC, $1, undefined, this._$.first_line, (this._$.first_column + 1));
            $4.unshift(param);
            $$ = $4;
        }

    | TIPO_DATO IDENTIFICADOR
        {
            var param = Instruccion.Declaracion([$2], IDEC.DEC, $1, undefined, this._$.first_line, (this._$.first_column + 1));
            $$ = [param];
        }
;

EJECTUAR
    : RUN IDENTIFICADOR P_OP LISTA_VALORES P_CL PCOMA
        {
            $$ = Instruccion.Run(NATIVA.RUN, $2, $4, this._$.first_line, (this._$.first_column + 1));
        }

    | RUN IDENTIFICADOR P_OP P_CL PCOMA
        {
            $$ = Instruccion.Run(NATIVA.RUN, $2, [], this._$.first_line, (this._$.first_column + 1));
        }
;

IFS
    : IFSENCILLO
        {
            $$ = $1;
        }

    | IFSENCILLO ELSE LL_OP INSTRUCCIONES LL_CL
        {
            $1.els = Instruccion.nuevoElse(ICON.ELSE, $4, this._$.first_line, (this._$.first_column + 1));
            $$ = $1;
        }

    | IFSENCILLO ELIFS
        {
            $1.elifs = $2
            $$ = $1;
        }

    | IFSENCILLO ELIFS ELSE LL_OP INSTRUCCIONES LL_CL
        {
            $1.elifs = $2;
            $1.els = Instruccion.nuevoElse(ICON.ELSE, $5, this._$.first_line, (this._$.first_column + 1));
            $$ = $1;
        }
;

IFSENCILLO
    : IF P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL
        {
            $$ = Instruccion.nuevoIF($3, $6, [], undefined, ICON.IF, this._$.first_line, (this._$.first_column + 1));
        }
;

ELIFS
    : ELIF P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL
        {
            var node = Instruccion.nuevoIF($3, $6, [], undefined, ICON.ELIF, this._$.first_line, (this._$.first_column + 1));
            $$ = [node];
        }

    | ELIF P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL ELIFS
        {
            var node = Instruccion.nuevoIF($3, $6, [], undefined, ICON.ELIF, this._$.first_line, (this._$.first_column + 1));
            $8.unshift(node);
            $$ = $8;
        }
;

SWITCH_CASE
    : SWITCH P_OP EXPRESION P_CL LL_OP CASES_LIST LL_CL
        {
            $$ = Instruccion.Switch($3, $6, ICON.SWITCH, this._$.first_line, (this._$.first_column + 1));
        }
;

CASES_LIST
    : CASE EXPRESION DPUNTOS INSTRUCCIONES CASES_LIST
        {
            var caso = Instruccion.Caso($2, $4, ICON.CASE, this._$.first_line, (this._$.first_column + 1));
            $5.unshift(caso);
            $$ = $5;
        }

    | DEFAULT DPUNTOS INSTRUCCIONES CASES_LIST
        {
            var caso = Instruccion.Caso(undefined, $3, ICON.DEFAULT, this._$.first_line, (this._$.first_column + 1));
            $4.unshift(caso);
            $$ = $4;
        }

    | CASE EXPRESION DPUNTOS INSTRUCCIONES
        {
            var caso = Instruccion.Caso($2, $4, ICON.CASE, this._$.first_line, (this._$.first_column + 1));
            $$ = [caso];
        }

    | DEFAULT DPUNTOS INSTRUCCIONES
        {
            var caso = Instruccion.Caso(undefined, $3, ICON.DEFAULT, this._$.first_line, (this._$.first_column + 1));
            $$ = [caso];
        }
;

WHILE_CICLO
    : WHILE P_OP EXPRESION P_CL LL_OP INSTRUCCIONES LL_CL
        {
            $$ = Instruccion.CicloWhile(ICICL.WHILE, $3, $6, this._$.first_line, (this._$.first_column + 1));
        }
;

FOR_CICLO
    : FOR P_OP DECLARACION_VARIABLES PCOMA RELACIONAL PCOMA ACTUALIZACION P_CL LL_OP INSTRUCCIONES LL_CL
        {
            $$ = Instruccion.CicloFor(ICICL.FOR, $3, $5, $7, $10, this._$.first_line, (this._$.first_column + 1));
        }

    | FOR P_OP ASIGNACION_VARIABLES PCOMA RELACIONAL PCOMA ACTUALIZACION P_CL LL_OP INSTRUCCIONES LL_CL
        {
            $$ = Instruccion.CicloFor(ICICL.FOR, $3, $5, $7, $10, this._$.first_line, (this._$.first_column + 1));
        }
;

ACTUALIZACION
    : INC_DEC
        {
            $$ = $1;
        }

    | ASIGNACION_VARIABLES
        {
            $$ = $1;
        }
;

DO_WHILE
    : DO LL_OP INSTRUCCIONES LL_CL WHILE P_OP EXPRESION P_CL PCOMA
        {
            $$ = Instruccion.CicloWhile(ICICL.DO_WHILE, $7, $3, this._$.first_line, (this._$.first_column + 1));
        }
;

DO_UNTIL
    : DO LL_OP INSTRUCCIONES LL_CL UNTIL P_OP EXPRESION P_CL PCOMA
        {
            $$ = Instruccion.CicloWhile(ICICL.DO_UNTIL, $7, $3, this._$.first_line, (this._$.first_column + 1));
        }
;

