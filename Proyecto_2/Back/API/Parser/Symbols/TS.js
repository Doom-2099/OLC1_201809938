class TS {
    ts = [];

    static instance = new TS();

    static getInstance() {
        return this.instance;
    }

    addSymbol(symbol) {
        this.ts.push(symbol);
    }

    getTable() {
        return this.ts;
    }

    clearLista() {
        this.ts = [];
    }
}

module.exports = TS;