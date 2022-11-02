function Declaracion(id, tipo, tipoDato, expresion, linea, columna){
    return {
        id: id,
        tipo: tipo,
        tipoDato: tipoDato,
        expresion: expresion,
        linea: linea,
        columna: columna
    }
}

function DeclaracionVector1(id, tipo, tipoDato, expresiones, linea, columna){
    return {
        id: id,
        tipo: tipo,
        tipoDato: tipoDato,
        expresiones: expresiones,
        linea: linea,
        columna: columna
    }
}

function DeclaracionVector2(id, tipo, tipoDato, valores, linea, columna){
    return {
        id: id,
        tipo: tipo,
        tipoDato: tipoDato,
        valores: valores,
        linea: linea,
        columna: columna
    }
}

function DeclaracionMetodo(id, tipo, tipoRetorno, parametros, instrucciones, linea, columna) {
    return {
        id: id,
        tipo: tipo,
        tipoRetorno: tipoRetorno,
        parametros: parametros,
        instrucciones: instrucciones,
        linea: linea,
        columna: columna
    }
}

function LlamadaMetodo(id, valores, tipo, linea, columna) {
    return {
        id: id,
        valores: valores,
        tipo: tipo,
        linea: linea,
        columna: columna
    }
}

function Casteo(tipo, tipoDato, expresion, linea, columna) {
    return {
        tipo: tipo,
        tipoDato: tipoDato,
        expresion: expresion,
        linea: linea,
        columna: columna
    }
}

function Asignacion(id, tipo, expresion, linea, columna) {
    return {
        id: id,
        tipo: tipo,
        expresion: expresion,
        linea: linea,
        columna: columna
    }
}

function ModificacionVector(id, tipo, posicion, valor, linea, columna) {
    return {
        id: id,
        tipo: tipo,
        posicion: posicion,
        valor: valor,
        linea: linea,
        columna: columna
    }
}

function nuevoIF(condicion, instrucciones, elifs, els, tipo, linea, columna) {
    return {
        condicion: condicion,
        tipo: tipo,
        instrucciones: instrucciones,
        elifs: elifs,
        else: els,
        linea: linea, 
        columna: columna
    }
}

function nuevoElse(tipo, instrucciones, linea, columna) {
    return {
        tipo: tipo,
        instrucciones: instrucciones,
        linea: linea,
        columna: columna
    }
}

function Switch(condicion, casos, tipo, linea, columna) {
    return {
        condicion: condicion, 
        casos: casos,
        tipo: tipo, 
        linea: linea,
        columna: columna
    }
}

function Caso(valor, instrucciones, tipo, linea, columna) {
    return {
        valor: valor,
        instrucciones: instrucciones,
        tipo: tipo,
        linea: linea,
        columna: columna
    }
}

function Transferencia(tipo, valor, linea, columna) {
    return {
        tipo: tipo,
        valor: valor,
        linea: linea,
        columna: columna
    }
}

function CicloWhile(tipo, condicion, instrucciones, linea, columna) {
    return {
        tipo: tipo,
        condicion: condicion,
        instrucciones: instrucciones,
        linea: linea,
        columna: columna
    }
}

function CicloFor(tipo, contador, condicion, actualizacion, instrucciones, linea, columna) {
    return {
        tipo: tipo, 
        contador: contador,
        condicion: condicion,
        actualizacion: actualizacion,
        instrucciones: instrucciones,
        linea: linea,
        columna: columna
    }
}

function Run(tipo, id, parametros, linea, columna) {
    return {
        tipo: tipo,
        id: id,
        parametros: parametros,
        linea: linea,
        columna: columna
    }
}

function Nativas(tipo, expresion, linea, columna) {
    return {
        tipo: tipo,
        expresion: expresion,
        linea: linea,
        columna: columna
    }
}

function NativasVec(id, tipo, expresion, linea, columna){
    return  {
        id: id,
        tipo: tipo,
        expresion: expresion,
        linea: linea,
        columna: columna
    }
}

module.exports = {
    Declaracion,
    DeclaracionVector1,
    DeclaracionVector2,
    DeclaracionMetodo,
    LlamadaMetodo,
    Casteo,
    Asignacion,
    ModificacionVector,
    nuevoIF,
    nuevoElse,
    Switch,
    Caso,
    Transferencia,
    CicloWhile,
    CicloFor,
    Run,
    Nativas,
    NativasVec
}