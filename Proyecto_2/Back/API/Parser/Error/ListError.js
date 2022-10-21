class ListError {
    listaErrores = [];

    static instance = new ListError();

    static getInstance() {
        return this.instance;
    }

    addLista(error) {
        this.listaErrores.push(error);
    }

    getLista() {
        return this.listaErrores;
    }

    clearLista() {
        this.listaErrores = [];
    }
}

module.exports = ListError;