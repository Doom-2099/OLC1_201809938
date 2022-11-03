const DEC = require('../Context/Instruccion/TipoInstruccion').INST_DEC;
const ASIG = require('../Context/Instruccion/TipoInstruccion').INST_ASIG;
const CONTROL = require('../Context/Instruccion/TipoInstruccion').INST_CONTROL;
const CICLOS = require('../Context/Instruccion/TipoInstruccion').INST_CICLOS;
const TRANS = require('../Context/Instruccion/TipoInstruccion').INST_TRANSFERENCIA;
const NATIVA = require('../Context/Instruccion/TipoInstruccion').INST_NATIVA;

// ---> EXPRESIONES
const TD = require('../Context/Expresion/TipoExpresion').TIPO_DATO;
const TOA = require('../Context/Expresion/TipoExpresion').TIPO_OP_ARITMETICA;
const TOL = require('../Context/Expresion/TipoExpresion').TIPO_OP_LOGICA;
const TOR = require('../Context/Expresion/TipoExpresion').TIPO_OP_RELACIONAL;

// ---> TS
const Symbol = require('./Symbol');
const TS = require('./TS');

var ambitoActual = 'global';
var ambitoAnterior = [];

function analizarCode(root) {
    root.forEach(nodo => {
        analizarInst(nodo);
    });
}

function analizarInst(root){
    switch(root.tipo) {
        case DEC.DEC:
            root.id.forEach(idValor => {
                if(root.expresion == undefined) {
                    TS.getInstance().addSymbol(new Symbol(idValor, 'variable', ambitoActual, getValorDefecto(root.tipoDato), root.linea, root.columna, root.tipoDato));
                } else {
                    TS.getInstance().addSymbol(new Symbol(idValor, 'Variable', ambitoActual, analizarOp(root.expresion), root.linea, root.columna, root.tipoDato));
                }
            }); 

            break;

        case DEC.DEC_VECTOR_T1_D1:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, analizarOp(root.expresion), root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_VECTOR_T1_D2:
            var valor = analizarOp(root.expresiones[0]) + ', ' + analizarOp(root.expresiones[1]);
            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, valor, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_VECTOR_T2_D1:
            var valores = '';

            root.valores.forEach(valor => {
                valores += analizarOp(valor) + ', ';
            });

            valores = valores.substring(0, valores.length - 2);

            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, valores, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_VECTOR_T2_D2:
            var valores = '';

            root.valores[0].forEach(valor => {
                valores += analizarOp(valor) + ', ';
            });

            valores = valores.substring(0, valores.length - 2);
            valores = ' | ';

            root.valores[1].forEach(valor => {
                valores += analizarOp(valor) + ', ';
            });

            valores = valores.substring(0, valores.length - 2);

            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, valores, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_METODO:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Procedimiento', ambitoActual, 'ninguno', root.linea, root.columna, root.tipoRetorno));
            
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'Procedimiento' + root.id;

            root.parametros.forEach(param => {
                analizarInst(param);
            });

            ambitoActual = ambitoAnterior[-1];
            ambitoAnterior.pop();

            break;

        case ASIG.ASIGNACION:

        case ASIG.CALL_METODO:

        case ASIG.CASTEO:

        case TOA.DECREMENTO:

        case TOA.INCREMENTO:

        case ASIG.MOD_VECTOR_D1:

        case ASIG.MOD_VECTOR_D2:

        case CONTROL.IF:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'IF_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior[-1];
            ambitoAnterior.pop();

            if(root.elifs.length != 0) {
                // Recorrer Elifs
            }

        case CONTROL.ELIF:

        case CONTROL.ELSE:

        case CONTROL.SWITCH:

        case CONTROL.CASE:

        case CONTROL.DEFAULT:

        case TRANS.BREAK:

        case TRANS.CONTINUE:

        case TRANS.RETURN:

        case CICLOS.FOR:

        case CICLOS.WHILE:

        case CICLOS.DO_WHILE:

        case CICLOS.DO_UNTIL:

        case NATIVA.PRINT:

        case NATIVA.PRINTLN:

        case NATIVA.ROUND:

        case NATIVA.LENGTH:

        case NATIVA.TYPEOF:

        case NATIVA.TOSTRING:

        case NATIVA.TOCHARARRAY:

        case NATIVA.TOLOWER:

        case NATIVA.TOUPPER:

        case NATIVA.RUN:

        case NATIVA.POP:

        case NATIVA.PUSH:


    }
}

function analizarOp(root) {
    switch(root.tipo) {
        case TOA.SUMA:

        case TOA.RESTA:

        case TOA.MULTIPLICACION:

        case TOA.DIVISION:

        case TOA.POTENCIA:

        case TOA.MODULO:

        case TOA.NEGATIVO:

        case TOA.INCREMENTO:

        case TOA.DECREMENTO:

        case TOA.AGRUPACION:

        case TOA.TERNARIA:

        case TOR.IGUAL:

        case TOR.DIFERENTE:

        case TOR.MENOR:

        case TOR.MAYOR:

        case TOR.MAYORIG:

        case TOR.MENORIG:

        case TOL.AND:

        case TOL.OR:

        case TOL.NOT:

        case TD.ARRAY_D1:

        case TD.ARRAY_D2:

        case TD.INT:

        case TD.DOUBLE:

        case TD.BOOL:

        case TD.STRING:

        case TD.CHAR:

        case TD.IDENTIFICADOR:

        default:
            return analizarInst(root);
    }
}

function getValorDefecto(tipoDato) {
    switch(tipoDato) {
        case TD.BOOL:
            return 'true';
        
        case TD.CHAR:
            return '\0';

        case TD.DOUBLE:
            return '0.0';

        case TD.INT:
            return '0';

        case TD.STRING:
            return '';
    }
}

module.exports = {
    analizarCode
}