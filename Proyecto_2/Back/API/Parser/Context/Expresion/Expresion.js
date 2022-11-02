function nuevaOperacion(OpIzq, OpDer, TipoOp, linea, columna){
    return {
        OpIzq: OpIzq,
        OpDer: OpDer,
        tipo: TipoOp,
        linea: linea,
        columna: columna
    }
}

function nuevoValor(valor, tipo, linea, columna){
    return {
        valor: valor,
        tipo: tipo,
        linea: linea,
        columna: columna
    }
}

function nuevoValorArray(id, pos, tipo, linea, columna){
    return {
        id: id,
        tipo: tipo,
        posicion: pos,
        linea: linea,
        columna: columna
    }
}

function nuevaTernaria(tipo, condicion, valor1, valor2, linea, columna){
    return {
        tipo: tipo,
        condicion: condicion,
        valor1: valor1,
        valor2: valor2,
        linea: linea,
        columna: columna
    }
}

module.exports = {
    nuevaOperacion,
    nuevoValor,
    nuevoValorArray,
    nuevaTernaria
}