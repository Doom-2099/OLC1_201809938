def _potenciaManual_ (_base_, _exponente_):
    _i_ = 0
    _acumulado_ = 0
    for _i_ in range(0, _exponente_ - 1):
        _acumulado_ = _acumulado_ + _acumulado_
    
    print(_acumulado_)
    print(" Esta es la potencia Manual")

def _potenciaFuncion_ (_base_, _exponente_):
    _potencia_ = _base_ ** _exponente_
    return _potencia_

def _metodo_1_ ():
    print("estamos entrando al metodo 1")
    _potenciaManual_(3 * 1 + 4 / 2, 3 + 2)
    print(_potenciaFuncion_(3 * 1 + 4 / 2, 3 + 2))
    print("Esta es la potencia Funcion")

def main():
    _variable1_ = 5 
    _v1_, _v2_, _v3_ = "esta es una cadena", "esta es una cadena", "esta es una cadena"
    _v1_ = "esta es la cadena numero 1"
    _v2_, _v3_ = "estas cadenas deben ser diferentes", "estas cadenas deben ser diferentes"
    print(_v3_)
    if _v1_ == _v2_ :
        print("Al parecer no funciona la asignacion")
        while not (_variable1_ >= 5 * 5 + 8 / 2):
            print(_variable1_)
            _variable1_ = _variable1_ + 1

    _varB_ = False
    if _varB_ :
        print("Estas definiendo bien los valores")
        _varaux_ = _variable1_ % 2
        if _varaux_ == 0:
            print("el valor es mayor a 0 y menos a 2")
        elif _varaux_ == 2:
            print("el valor es mayor a 2")

    _metodo_1_()

if __name__ == "__main__":
    main()