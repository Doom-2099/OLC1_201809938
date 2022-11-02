class ListSymbol {
    listaSimbolos = [];

    static instance = new ListSymbol();

    static getInstance() {
        return this.instance;
    }

    addSymbol(symbol) {
        this.listaSimbolos.push(symbol);
    }

    getLista() {
        return this.listaSimbolos;
    }

    clearLista() {
        this.listaSimbolos = [];
    }
}

module.exports = ListSymbol;