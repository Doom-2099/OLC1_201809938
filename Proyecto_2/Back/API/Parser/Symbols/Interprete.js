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

function analizarCode(root) {
    root.forEach(nodo => {
        analizarInst(nodo);
    });
}

function analizarInst(root){
    switch(root.tipo) {
        
    }
}

function analizarOp(root) {

}

module.exports = {
    analizarCode
}