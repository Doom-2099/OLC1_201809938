const parser = require('./Parser/grammar');
const graph = require('./Parser/Graph/AST');
const fs = require('fs');
const path = require('path');
const ListError = require('./Parser/Error/ListError');


fs.readFile(path.resolve(__dirname, './Upload/prueba2.txt'), 'utf-8', (err, data) => {
    if (err) {
        console.log('error al leer el archivo');
        throw err;
    }

    var ast = parser.parse(data);
    
    fs.writeFileSync(path.resolve(__dirname, './JSON/parser.json'), JSON.stringify(ast, null, 2), (err) => {
        if(err) {
            console.log('Ocurrio Un Error Al Escribir El Json');
            throw err;
        }
    });

    graph.generarDOT(ast);
    console.log(ListError.getInstance().getLista());
    
});