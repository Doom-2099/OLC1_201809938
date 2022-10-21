class Error{

    constructor(lexema, linea, columna, tipo, msg) {
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.tipo = tipo;
        this.msg = msg;
    }

    getLexema() {
        return this.lexema;
    }

    getLinea() {
        return this.linea;
    }

    getColumna() {
        return this.columna;
    }

    getTipo() {
        return this.tipo;
    }

    getMsg() {
        return this.msg;
    }

    setLexema(lexema) {
        this.lexema = lexema;
    }

    setLinea(linea) {
        this.linea = linea;
    }

    setColumna(columna) {
        this.columna = columna;
    }

    setTipo(tipo) {
        this.tipo = tipo;
    }

    setMsg(msg) {
        this.msg = msg;
    }
}

module.exports = Error;