// ---> INSTRUCCIONES
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

// ---> MODULOS EXTRA
const { exec } = require('child_process');
const graphviz = require('graphviz');
const path = require('path');
const fs = require('fs');

var grafo = graphviz.digraph('G');
var contador = 0;

function generarDOT(root) {    
    grafo.set('shape', 'circle');

    var nodoAux = grafo.addNode('instrucciones0000', { 'label': 'INSTRUCCIONES' });

    root.forEach(node => {
        var nodo = analizarNodoInst(node);
        grafo.addEdge(nodoAux, nodo);
    });
    
    fs.writeFile(path.resolve(__dirname, '../../Img/AST.dot'), grafo.to_dot(), (err) => {
        if(err) {
            console.log(err);
            return false
        }

        var ruta1 = path.resolve(__dirname, '../../Img/AST.dot');
        var ruta2 = path.resolve(__dirname, '../../Img/AST.png');
        exec('dot -Tpng ' + ruta1 + ' -o ' + ruta2, (err, stdout, stderr) => {
            if(err) {
                console.log('err');
                console.log(err);
                return false;
            }

            if(stderr) {
                console.log('stderr');
                console.log(stderr);
                return false;
            }

            console.log(stdout);
            return true;
        });
    }); 
}

function analizarNodoInst(element) {
    switch(element.tipo) {
        case DEC.DEC:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DECLARACION' });
            var nodo1 = grafo.addNode('identificadores' + element.linea + element.columna, { 'label': 'IDENTIFICADORES' });

            element.id.forEach(idVar => {
                var nodo = grafo.addNode(idVar + element.linea + element.columna, { 'label': idVar });
                grafo.addEdge(nodo1, nodo);
            });

            var nodo2 = grafo.addNode(element.tipoDato + element.linea + element.columna, { 'label': '[' + element.tipoDato + ']' });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            if(element.expresion != undefined) {
                var nodo3 = grafo.addNode('valor' + element.linea + element.columna, { 'label': 'VALOR' });
                var nodo4 = analizarNodoOp(element.expresion);
                grafo.addEdge(nodo3, nodo4);
                grafo.addEdge(nodoPadre, nodo3);
            }            

            return nodoPadre;

        case DEC.DEC_VECTOR_T1_D1:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DEC_VEC_1D' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode(element.tipoDato + element.linea + element.columna, { 'label': '[' + element.tipoDato + ']' });
            var nodo3 = grafo.addNode('longitud' + element.linea + element.columna, { 'label': '[LENGTH_1]' });
            var nodo4 = analizarNodoOp(element.expresiones);
            
            grafo.addEdge(nodo3, nodo4);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case DEC.DEC_VECTOR_T1_D2:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DEC_VEC_2D' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode(element.tipoDato + element.linea + element.columna, { 'label': '[' + element.tipoDato + ']' });
            var nodo3 = grafo.addNode('longitud' + element.linea + element.columna, { 'label': '[LENGTH_1]' });
            var nodo4 = analizarNodoOp(element.expresiones[0]);
            var nodo5 = grafo.addNode('longitud' + element.linea + element.columna, { 'label': '[LENGTH_2]' });
            var nodo6 = analizarNodoOp(element.expresiones[1]);
            
            grafo.addEdge(nodo3, nodo4);
            grafo.addEdge(nodo5, nodo6);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);
            grafo.addEdge(nodoPadre, nodo5);

            return nodoPadre;

        case DEC.DEC_VECTOR_T2_D1:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DEC_VEC_1D'});
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode(element.tipoDato + element.linea + element.columna, { 'label': '[' + element.tipoDato + ']' });
            var nodo3 = grafo.addNode('valores1' + element.linea + element.columna, { 'label': 'VALORES_1' });


            element.valores.forEach(valor => {
                var nodo = analizarNodoOp(valor);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case DEC.DEC_VECTOR_T2_D2:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DEC_VEC_2D'});
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode(element.tipoDato + element.linea + element.columna, { 'label': '[' + element.tipoDato + ']' });
            var nodo3 = grafo.addNode('valores1' + element.linea + element.columna, { 'label': 'VALORES_1' });
            var nodo4 = grafo.addNode('valores2' + element.linea + element.columna, { 'label': 'VALORES_2' });


            element.valores[0].forEach(valor => {
                var nodo = analizarNodoOp(valor);
                grafo.addEdge(nodo3, nodo);
            });

            element.valores[1].forEach(valor => {
                var nodo = analizarNodoOp(valor);
                grafo.addEdge(nodo4, nodo);
            });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);
            grafo.addEdge(nodoPadre, nodo4);

            return nodoPadre;

        case ASIG.CASTEO:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'CASTEO' });
            var nodo1 = grafo.addNode(element.tipoDato + element.linea + element.columna, { 'label': '[' + element.tipoDato + ']' });
            var nodo2 = grafo.addNode('valor' + element.linea + element.columna, { 'label': 'VALOR' });
            var nodo3 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodo2, nodo3);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.INCREMENTO:
            var nodoPadre = grafo.addNode(element.tipo, element.linea + element.columna, { 'label': 'INCREMENTO' });
            var nodo1 = analizarNodoOp(element);

            grafo.addEdge(nodoPadre, nodo1);
            
            return nodoPadre;

        case TOA.DECREMENTO:
            var nodoPadre = grafo.addNode(element.tipo, element.linea + element.columna, { 'label': 'DECREMENTO' });
            var nodo1 = analizarNodoOp(element);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case ASIG.ASIGNACION:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ASIGNACION' });
            var nodo1 = grafo.addNode('identificadores' + element.linea + element.columna, { 'label': 'IDENTIFICADORES' });

            element.id.forEach(idVar => {
                var nodo = grafo.addNode(idVar + element.linea + element.columna, { 'label': idVar });
                grafo.addEdge(nodo1, nodo);
            });
            
            var nodo2 = grafo.addNode('valor' + element.linea + element.columna, { 'label': 'VALOR' });
            var nodo3 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodo2, nodo3);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case ASIG.MOD_VECTOR_D1:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'MOD_VEC_2D' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode('posicion' + element.linea + element.columna, { 'label': '[POSICON_1]' });
            var nodo3 = analizarNodoOp(element.posicion);
            var nodo4 = grafo.addNode('valor' + element.linea + element.columna, { 'label': 'VALOR' });
            var nodo5 = analizarNodoOp(element.valor);

            grafo.addEdge(nodo2, nodo3);
            grafo.addEdge(nodo4, nodo5);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo4);

            return nodoPadre;

        case ASIG.MOD_VECTOR_D2:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'MOD_VEC_2D' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode('posicion1' + element.linea + element.columna, { 'label': '[POSICION_1]' });
            var nodo3 = analizarNodoOp(element.posicion[0]);
            var nodo4 = grafo.addNode('posicion2' + element.linea + element.columna, { 'label': '[POSICION_2]' });
            var nodo5 = analizarNodoOp(element.posicion[1]);
            var nodo6 = grafo.addNode('valor' + element.linea + element.columna, { 'label': 'VALOR' });
            var nodo7 = analizarNodoOp(element.valor);

            grafo.addEdge(nodo2, nodo3);
            grafo.addEdge(nodo4, nodo5);
            grafo.addEdge(nodo6, nodo7);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo4);
            grafo.addEdge(nodoPadre, nodo6);

            return nodoPadre;

        case DEC.DEC_METODO:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DECLARACION METODO' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode('retorno' + element.linea + element.columna, { 'label': 'RETORNO' });
            var nodo3 = grafo.addNode(element.tipoRetorno + element.linea + element.columna, { 'label': element.tipoRetorno });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodo2, nodo3);

            if(element.parametros.length != 0) {
                var nodo4 = grafo.addNode('parametros' + element.linea + element.columna, { 'label': 'PARAMETROS' });

                element.parametros.forEach(param => {
                    var nodo = analizarNodoInst(param);
                    grafo.addEdge(nodo4, nodo);
                });

                grafo.addEdge(nodoPadre, nodo4);
            }
            
            var nodo5 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });
            
            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo5, nodo);
            });

            grafo.addEdge(nodoPadre, nodo5);

            return nodoPadre;

        case ASIG.CALL_METODO:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'LLAMADA METODO' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode('parametros' + element.linea + element.columna, { 'label': 'PARAMETROS' });

            element.valores.forEach(valor => {
                var nodo = analizarNodoOp(valor);
                grafo.addEdge(nodo2, nodo);
            });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case CONTROL.IF:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'IF' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo2 = analizarNodoOp(element.condicion);
            var nodo3 = grafo.addNode('instrucciones' + element.linea + element.columna + contador, { 'label': 'INSTRUCCIONES' });
            contador++;

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodoPadre, nodo3);

            if(element.elifs.length != 0) {
                var nodo4 = grafo.addNode('elifs' + element.linea + element.columna, { 'label': 'ELIFS' });
                
                element.elifs.forEach(elif => {
                    var nodo = analizarNodoInst(elif);
                    grafo.addEdge(nodo4, nodo);
                });

                grafo.addEdge(nodoPadre, nodo4);
            }

            if(element.els != undefined) {
                var nodo = analizarNodoInst(element.els);
                grafo.addEdge(nodoPadre, nodo);
            }

            return nodoPadre;

        case CONTROL.ELIF:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ELSE IF' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo2 = analizarNodoOp(element.condicion);
            var nodo3 = grafo.addNode('instrucciones' + element.linea + element.columna + contador, { 'label': 'INSTRUCCIONES' });
            contador++;
            
            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case CONTROL.ELSE:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ELSE' });
            var nodo1 = grafo.addNode('instrucciones' + element.linea + element.columna + contador, { 'label': 'INSTRUCCIONES' });
            contador++;

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo1, nodo);
            });

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case CONTROL.SWITCH:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'SWITCH' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo2 = analizarNodoOp(element.condicion);
            var nodo3 = grafo.addNode('casos' + element.linea + element.columna, { 'label': 'CASOS' });
            
            element.casos.forEach(caso => {
                var nodo = analizarNodoInst(caso);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case CONTROL.CASE:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'CASO' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'VALOR' });
            var nodo2 = analizarNodoOp(element.valor);
            var nodo3 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case CONTROL.DEFAULT:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'CASO' });
            var nodo1 = grafo.addNode('default' + element.linea + element.columna, { 'label': 'DEFAULT' });
            var nodo2 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo2, nodo);
            });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TRANS.BREAK:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'BREAK' });

            return nodoPadre;

        case TRANS.CONTINUE:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'CONTINUE' });

            return nodoPadre;

        case TRANS.RETURN:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'RETURN' });
            var nodo = analizarNodoOp(element.valor);

            grafo.addEdge(nodoPadre, nodo);

            return nodoPadre;

        case CICLOS.FOR:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'FOR' });
            var nodo1 = grafo.addNode('contador' + element.linea + element.columna, { 'label': 'CONTADOR' });
            var nodo2 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo3 = grafo.addNode('actualizacion' + element.linea + element.columna, { 'label': 'ACTUALIZACION' });
            var nodo4 = analizarNodoInst(element.contador);
            var nodo5 = analizarNodoOp(element.condicion);

            if(element.actualizacion.tipo in TOA) {
                var nodo6 = analizarNodoOp(element.actualizacion);
            } else {
                var nodo6 = analizarNodoInst(element.actualizacion);
            }
            
            var nodo7 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });
            
            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo7, nodo);
            });

            grafo.addEdge(nodo1, nodo4);
            grafo.addEdge(nodo2, nodo5);
            grafo.addEdge(nodo3, nodo6);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);
            grafo.addEdge(nodoPadre, nodo7);

            return nodoPadre;

        case CICLOS.WHILE:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'WHILE' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo2 = analizarNodoOp(element.condicion);
            var nodo3 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case CICLOS.DO_WHILE:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DO_WHILE' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo2 = analizarNodoOp(element.condicion);
            var nodo3 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case CICLOS.DO_UNTIL:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'DO_UNTIL' });
            var nodo1 = grafo.addNode('condicion' + element.linea + element.columna, { 'label': 'CONDICION' });
            var nodo2 = analizarNodoOp(element.condicion);
            var nodo3 = grafo.addNode('instrucciones' + element.linea + element.columna, { 'label': 'INSTRUCCIONES' });

            element.instrucciones.forEach(instr => {
                var nodo = analizarNodoInst(instr);
                grafo.addEdge(nodo3, nodo);
            });

            grafo.addEdge(nodo1, nodo2);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case NATIVA.PRINT:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'Print' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.PRINTLN:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'Println' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.ROUND:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'Round' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.LENGTH:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'Length' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.TYPEOF:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'TypeOf' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.TOSTRING:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ToString' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.TOCHARARRAY:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ToCharArray' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.TOLOWER:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ToLower' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.TOUPPER:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'ToUpper' });
            var nodo1 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case NATIVA.RUN:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'RUN' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode('parametros' + element.linea + element.columna, { 'label': 'PARAMETROS' });
            
            element.parametros.forEach(param => {
                var nodo = analizarNodoOp(param);
                grafo.addEdge(nodo2, nodo);
            });

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case NATIVA.PUSH:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'PUSH' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });
            var nodo2 = grafo.addNode('expresion' + element.linea + element.columna, { 'label': 'EXPRESION' });
            var nodo3 = analizarNodoOp(element.expresion);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);
            
            return nodoPadre;

        case NATIVA.POP:
            var nodoPadre = grafo.addNode(element.tipo + element.linea + element.columna, { 'label': 'POP' });
            var nodo1 = grafo.addNode(element.id + element.linea + element.columna, { 'label': element.id });

            grafo.addEdge(nodoPadre, nodo1);
            
            return nodoPadre;
    }
}

function analizarNodoOp(root) {
    switch(root.tipo) {
        case TOA.SUMA:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '+' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.RESTA:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '-' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.MULTIPLICACION:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '*' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.DIVISION:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '/' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.POTENCIA:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '^' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.MODULO:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '%' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOA.NEGATIVO:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '*-1' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case TOA.INCREMENTO:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '++' });
            contador++;
            var nodo1 = grafo.addNode(root.OpIzq + root.linea + root.columna, { 'label': root.OpIzq });

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case TOA.DECREMENTO:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '--' });
            contador++;
            var nodo1 = grafo.addNode(root.OpIzq + root.linea + root.columna, { 'label': root.OpIzq });

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case TOA.AGRUPACION:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '()' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case TOA.TERNARIA:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna, { 'label': 'TERNARIA' });
            var nodo1 = grafo.addNode('condicion' + root.linea + root.columna, { 'label': 'CONDICION' });
            var nodo2 = grafo.addNode('?' + root.linea + root.columna, { 'label': '?' });
            var nodo3 = grafo.addNode('valor1' + root.linea + root.columna, { 'label': 'VALOR_1' });
            var nodo4 = grafo.addNode('valor2' + root.linea + root.columna, { 'label': 'VALOR_2' });
            var nodo5 = analizarNodoOp(root.valor1);
            var nodo6 = analizarNodoOp(root.valor2);
            var nodo7 = grafo.addNode(':' + root.linea + root.columna, { 'label': ':' });
            var nodo8 = analizarNodoOp(root.condicion);

            grafo.addEdge(nodo3, nodo5);
            grafo.addEdge(nodo4, nodo6);
            grafo.addEdge(nodo1, nodo8);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);
            grafo.addEdge(nodoPadre, nodo7);
            grafo.addEdge(nodoPadre, nodo4);

            return nodoPadre;

        case TOR.IGUAL:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '==' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOR.DIFERENTE:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '!=' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOR.MENOR:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '<' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOR.MAYOR:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '>' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOR.MENORIG:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '<=' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOR.MAYORIG:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '>=' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            
            return nodoPadre;

        case TOL.AND:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '&&' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOL.OR:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '||' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);
            var nodo2 = analizarNodoOp(root.OpDer);

            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;

        case TOL.NOT:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna + contador, { 'label': '!' });
            contador++;
            var nodo1 = analizarNodoOp(root.OpIzq);

            grafo.addEdge(nodoPadre, nodo1);

            return nodoPadre;

        case TD.ARRAY_D1:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna, { 'label': 'VALOR ARRAY' });
            var nodo1 = grafo.addNode(root.id + root.linea + root.columna, { 'label': root.id });
            var nodo2 = grafo.addNode('posicion1' + root.linea + root.columna, { 'label': 'EXPRESION' });
            var nodo3 = analizarNodoOp(root.posicion);

            grafo.addEdge(nodo2, nodo3);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);

            return nodoPadre;
        
        case TD.ARRAY_D2:
            var nodoPadre = grafo.addNode(root.tipo + root.linea + root.columna, { 'label': 'VALOR ARRAY' });
            var nodo1 = grafo.addNode(root.id + root.linea + root.columna, { 'label': root.id });
            var nodo2 = grafo.addNode('posicion1' + root.linea + root.columna, { 'label': 'EXPRESION_1' });
            var nodo3 = grafo.addNode('posicion2' + root.linea + root.columna, { 'label': 'EXPRESION_2' });
            var nodo4 = analizarNodoOp(root.posicion[0]);
            var nodo5 = analizarNodoOp(root.posicion[1]);

            grafo.addEdge(nodo2, nodo4);
            grafo.addEdge(nodo3, nodo5);
            grafo.addEdge(nodoPadre, nodo1);
            grafo.addEdge(nodoPadre, nodo2);
            grafo.addEdge(nodoPadre, nodo3);

            return nodoPadre;

        case TD.INT:
            return grafo.addNode(root.valor + root.linea + root.columna, { 'label': root.valor });

        case TD.DOUBLE:
            return grafo.addNode(root.valor + root.linea + root.columna, { 'label': root.valor });

        case TD.BOOL:
            return grafo.addNode(root.valor + root.linea + root.columna, { 'label': root.valor });

        case TD.STRING:
            root.valor = root.valor.replaceAll('\"', '\\"');
            return grafo.addNode(root.valor + root.linea + root.columna, { 'label': root.valor });

        case TD.CHAR:
            if(root.valor.length > 5) {
                root.valor = root.valor.slice(1, -1);
            }

            return grafo.addNode(root.valor + root.linea + root.columna, { 'label': root.valor });

        case TD.IDENTIFICADOR:
            return grafo.addNode(root.valor + root.linea + root.columna, { 'label': root.valor });

        default:
            return analizarNodoInst(root);
    }
}

module.exports = {
    generarDOT
}