//////SALIDA EN GOLANG

package main

import (
	"fmt"
	"math"
)

/////inicio de la traduccion

func main() {

	var _variable1_ int = 5
	var _v1_, _v2_, _v3_ string = "esta es una cadena", "esta es una cadena", "esta es una cadena"

	_v1_ = "esta es la cadena numero 1"
	_v2_, _v3_ = "estas cadenas deben ser diferentes", "estas cadenas deben ser diferentes"
	fmt.Println((_v3_))
	if _v1_ == _v2_ {
		fmt.Println("Al parecer no funciona la asignacion")
		for true {
			if !(!(_variable1_ >= 5*5+8/2)) {
				break
			}
			fmt.Println(_variable1_)
			_variable1_ = _variable1_ + 1
		}
	}

	var _varB_ bool = false //tomar como "falso" o "verdadero" los valores booleanos

	if _varB_ {
		fmt.Println("Estas definiendo bien los valores")
		var _varaux_ int = _variable1_ % 2
		switch _varaux_ {
			case 0:
				fmt.Print("el valor es mayor a 0 y menos a 2")
			case 2:
				fmt.Print("el valor es mayor a 2")
		}
	}
	_metodo_1_()
}

/*Ahora empezamos con las funciones y procedimientos*/

func _potenciaManual_(_base_ int, _exponente_ int) {
	var _i_ int = 0
	var _acumulado_ int = 0
	for _i_ = 0; _i_ == _exponente_-1; _i_++ {
		_acumulado_ = _acumulado_ + _acumulado_
	}
	fmt.Print(_acumulado_)
	fmt.Println(" Esta es la potencia Manual")
}

func _potenciaFuncion_(_base_ int, _exponente_ int) float64 {
	var _potencia_ float64 = math.Pow(float64(_base_), float64(_exponente_))
	return _potencia_
}

func _metodo_1_() {
	fmt.Println("estamos entrando al metodo 1")
	_potenciaManual_(3*1+4/2, 3+2)
	fmt.Print(_potenciaFuncion_(3*1+4/2, 3+2))
	fmt.Println(" Esta es la potencia Funcion")
}