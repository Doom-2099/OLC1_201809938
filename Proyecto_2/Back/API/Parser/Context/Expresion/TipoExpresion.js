const TIPO_DATO = {
    INT: 'INT',
    DOUBLE: 'DOUBLE',
    BOOL: 'BOOL',
    STRING: 'STRING',
    CHAR: 'CHAR',
    ARRAY_D1: 'ARRAY_D1',
    ARRAY_D2: 'ARRAY_D2',
    IDENTIFICADOR: 'IDENTIFICADOR',
    VOID: 'VOID'
}

const TIPO_OP_LOGICA = {
    AND: 'AND',
    OR: 'OR',
    NOT: 'NOT'
}

const TIPO_OP_RELACIONAL = {
    IGUAL: 'IGUAL',
    DIFERENTE: 'DIFERENTE',
    MENOR: 'MENOR',
    MENORIG: 'MENORIGUAL',
    MAYOR: 'MAYOR',
    MAYORIG: 'MAYORIGUAL',
}

const TIPO_OP_ARITMETICA = {
    SUMA: 'SUMA',
    RESTA: 'RESTA',
    MULTIPLICACION: 'MULTIPLICACION',
    DIVISION: 'DIVISION',
    POTENCIA: 'POTENCIA',
    MODULO: 'MODULO',
    NEGATIVO: 'NEGATIVO',
    INCREMENTO: 'INCREMENTO',
    DECREMENTO: 'DECREMENTO',
    AGRUPACION: 'AGRUPACION',
    TERNARIA: 'TERNARIA',
}

module.exports = {
    TIPO_DATO,
    TIPO_OP_LOGICA,
    TIPO_OP_RELACIONAL,
    TIPO_OP_ARITMETICA
}