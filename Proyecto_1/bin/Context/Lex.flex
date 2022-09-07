package Context;
import java_cup.runtime.Symbol;
import Error.ListError;
import Error.Error;

%%
%class Lex
%public
%cup
%line
%char
%unicode
%ignorecase

%init{
    yyline = 1;
    yychar = 1;
%init}

E = [0-9]+
L = [a-zA-Z]+
ESP = [ \t\r]+
NUM = ({E})("."({E})*)?
ID = ("_")({L}|{E}|({L}"_")|({E}"_"))+("_")
CADENA = [\"\“][^\"\”\'\n]*[\"\”\n]
CARACTER = [\']([a-zA-Z]|"${"{E}"}")[\']
CMNTSINGLE = ("//".*\r\n)|("//".*\n)|("//".*\r)
CMNTMULTI = \/\*([^\*\/]|[^\*]\/|(\*)*[^/])*\*\/
SL = [\n]+
%%

{CMNTSINGLE}    {}
{CMNTMULTI}     {}

"+"     {return new Symbol(sym.Suma, (int)yychar, yyline, yytext());}
"-"     {return new Symbol(sym.Resta, (int)yychar, yyline, yytext());}
"*"     {return new Symbol(sym.Producto, (int)yychar, yyline, yytext());}
"/"     {return new Symbol(sym.Division, (int)yychar, yyline, yytext());}
"("     {return new Symbol(sym.PAbre, (int)yychar, yyline, yytext());}
")"     {return new Symbol(sym.PCierra, (int)yychar, yyline, yytext());}
";"     {return new Symbol(sym.PComa, (int)yychar, yyline, yytext());}
","     {return new Symbol(sym.Coma, (int)yychar, yyline, yytext());}
"¿"     {return new Symbol(sym.IntAbre, (int)yychar, yyline, yytext());}
"?"     {return new Symbol(sym.IntCierra, (int)yychar, yyline, yytext());}
"->"    {return new Symbol(sym.Flecha, (int)yychar, yyline, yytext());}

// PALABRAS RESERVADAS
"numero"            {return new Symbol(sym.TNumero, (int)yychar, yyline, yytext());}
"boolean"           {return new Symbol(sym.TBoolean, (int)yychar, yyline, yytext());}
"cadena"            {return new Symbol(sym.TCadena, (int)yychar, yyline, yytext());}
"caracter"          {return new Symbol(sym.TChar, (int)yychar, yyline, yytext());}
"verdadero"         {return new Symbol(sym.True, (int)yychar, yyline, yytext());}
"falso"             {return new Symbol(sym.False, (int)yychar, yyline, yytext());}
"potencia"          {return new Symbol(sym.Potencia, (int)yychar, yyline, yytext());}
"modulo"            {return new Symbol(sym.Modulo, (int)yychar, yyline, yytext());}
"mayor"             {return new Symbol(sym.Mayor, (int)yychar, yyline, yytext());}
"menor"             {return new Symbol(sym.Menor, (int)yychar, yyline, yytext());}
"mayor_o_igual"     {return new Symbol(sym.MayorIg, (int)yychar, yyline, yytext());}
"menor_o_igual"     {return new Symbol(sym.MenorIg, (int)yychar, yyline, yytext());}
"es_igual"          {return new Symbol(sym.Igual, (int)yychar, yyline, yytext());}
"es_diferente"      {return new Symbol(sym.Diferente, (int)yychar, yyline, yytext());}
"or"                {return new Symbol(sym.Or, (int)yychar, yyline, yytext());}
"and"               {return new Symbol(sym.And, (int)yychar, yyline, yytext());}
"not"               {return new Symbol(sym.Not, (int)yychar, yyline, yytext());}
"inicio"            {return new Symbol(sym.Inicio, (int)yychar, yyline, yytext());}
"fin"               {return new Symbol(sym.Fin, (int)yychar, yyline, yytext());}
"ingresar"          {return new Symbol(sym.Ingresar, (int)yychar, yyline, yytext());}
"como"              {return new Symbol(sym.Como, (int)yychar, yyline, yytext());}
"con_valor"         {return new Symbol(sym.ConValor, (int)yychar, yyline, yytext());}
"si"                {return new Symbol(sym.Si, (int)yychar, yyline, yytext());}
"entonces"          {return new Symbol(sym.Entonces, (int)yychar, yyline, yytext());}
"fin_si"            {return new Symbol(sym.FinSi, (int)yychar, yyline, yytext());}
"de_lo_contrario"   {return new Symbol(sym.DeLoContrario, (int)yychar, yyline, yytext());}
"o_si"              {return new Symbol(sym.OSi, (int)yychar, yyline, yytext());}
"segun"             {return new Symbol(sym.Segun, (int)yychar, yyline, yytext());}
"hacer"             {return new Symbol(sym.Hacer, (int)yychar, yyline, yytext());}
"fin_segun"         {return new Symbol(sym.FinSegun, (int)yychar, yyline, yytext());}
"para"              {return new Symbol(sym.Para, (int)yychar, yyline, yytext());}
"hasta"             {return new Symbol(sym.Hasta, (int)yychar, yyline, yytext());}
"fin_para"          {return new Symbol(sym.FinPara, (int)yychar, yyline, yytext());}
"con_incremental"   {return new Symbol(sym.Incremental, (int)yychar, yyline, yytext());}
"mientras"          {return new Symbol(sym.Mientras, (int)yychar, yyline, yytext());}
"fin_mientras"      {return new Symbol(sym.FinMientras, (int)yychar, yyline, yytext());}
"repetir"           {return new Symbol(sym.Repetir, (int)yychar, yyline, yytext());}
"hasta_que"         {return new Symbol(sym.HastaQue, (int)yychar, yyline, yytext());}
"retornar"          {return new Symbol(sym.Retornar, (int)yychar, yyline, yytext());}
"metodo"            {return new Symbol(sym.Metodo, (int)yychar, yyline, yytext());}
"fin_metodo"        {return new Symbol(sym.FinMetodo, (int)yychar, yyline, yytext());}
"con_parametros"    {return new Symbol(sym.ConParametros, (int)yychar, yyline, yytext());}
"funcion"           {return new Symbol(sym.Funcion, (int)yychar, yyline, yytext());}
"fin_funcion"       {return new Symbol(sym.FinFuncion, (int)yychar, yyline, yytext());}
"ejecutar"          {return new Symbol(sym.Ejecutar, (int)yychar, yyline, yytext());}
"imprimir"          {return new Symbol(sym.Imprimir, (int)yychar, yyline, yytext());}
"imprimir_nl"       {return new Symbol(sym.ImprimirNl, (int)yychar, yyline, yytext());}


{SL}                {yychar = 1;}
{ID}                {return new Symbol(sym.Identificador, (int)yychar, yyline, yytext());}
{NUM}               {return new Symbol(sym.Numero, (int)yychar, yyline, yytext());}
{ESP}               {}
{CADENA}            {return new Symbol(sym.Cadena, (int)yychar, yyline, yytext());}
{CARACTER}          {return new Symbol(sym.Caracter, (int)yychar, yyline, yytext());}

  . {
     String msg = "Error Lexico \n"
        + "En la Linea: " + yyline + "\n"
        + " En La Columna: " + (int)yychar +"\n"
        + " Lexema: " + yytext() + "\n";

     ListError.getInstance().addError(new Error((int)yychar, yyline, yytext(), msg, "LEXICO"));
 }