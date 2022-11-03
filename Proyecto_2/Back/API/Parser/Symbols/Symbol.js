class Symbol {
    constructor(simbolo, tipo, ambito, linea, columna, tipoDato) {
        this.simbolo = simbolo;
        this.ambito = ambito;
        this.tipoDato = tipoDato;
        this.tipo = tipo,
        this.linea = linea;
        this.columna = columna;
    }
}

module.exports = Symbol;