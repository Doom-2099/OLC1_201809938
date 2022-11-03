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
                TS.getInstance().addSymbol(new Symbol(idValor, 'Variable', ambitoActual, root.linea, root.columna, root.tipoDato));
            }); 

            break;

        case DEC.DEC_VECTOR_T1_D1:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_VECTOR_T1_D2:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_VECTOR_T2_D1:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_VECTOR_T2_D2:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Array', ambitoActual, root.linea, root.columna, root.tipoDato));

            break;

        case DEC.DEC_METODO:
            TS.getInstance().addSymbol(new Symbol(root.id, 'Procedimiento', ambitoActual, root.linea, root.columna, root.tipoRetorno));
            
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'Procedimiento ' + root.id;

            root.parametros.forEach(param => {
                analizarInst(param);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case ASIG.ASIGNACION:
            break;

        case ASIG.CALL_METODO:
            break;

        case ASIG.CASTEO:
            break;

        case TOA.DECREMENTO:
            break;

        case TOA.INCREMENTO:
            break;

        case ASIG.MOD_VECTOR_D1:
            break;

        case ASIG.MOD_VECTOR_D2:
            break;

        case CONTROL.IF:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'IF_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            if(root.elifs.length != 0) {
                root.elifs.forEach(elif => {
                    analizarInst(elif);
                });
            }

            if(root.els != undefined) {
                analizarInst(root.els);
            }

            break;

        case CONTROL.ELIF:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'ELIF_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case CONTROL.ELSE:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'ELSE_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case CONTROL.SWITCH:
            root.casos.forEach(caso => {
                analizarInst(caso);
            });

            break;

        case CONTROL.CASE:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'CASE_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case CONTROL.DEFAULT:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'DEFAULT_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case TRANS.BREAK:
            break;

        case TRANS.CONTINUE:
            break;

        case TRANS.RETURN:
            break;

        case CICLOS.FOR:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'FOR_' + root.linea + '_' + root.columna;

            analizarInst(root.contador);

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case CICLOS.WHILE:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'WHILE_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.push();

            break;

        case CICLOS.DO_WHILE:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'DO_WHILE_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case CICLOS.DO_UNTIL:
            ambitoAnterior.push(ambitoActual);
            ambitoActual = 'DO_UNTIL_' + root.linea + '_' + root.columna;

            root.instrucciones.forEach(instr => {
                analizarInst(instr);
            });

            ambitoActual = ambitoAnterior.pop();

            break;

        case NATIVA.PRINT:
            break;

        case NATIVA.PRINTLN:
            break;

        case NATIVA.ROUND:
            break;

        case NATIVA.LENGTH:
            break;

        case NATIVA.TYPEOF:
            break;

        case NATIVA.TOSTRING:
            break;

        case NATIVA.TOCHARARRAY:
            break;

        case NATIVA.TOLOWER:
            break;

        case NATIVA.TOUPPER:
            break;

        case NATIVA.RUN:
            break;

        case NATIVA.POP:
            break;

        case NATIVA.PUSH:
            break;

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