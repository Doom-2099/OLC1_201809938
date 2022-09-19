package main

import( 
	"math",
	"fmt",
)


func main() {
	var _variable1_ float64 = 5
	var _variable2_ float64 = 50
	var _v1_, _v2_, _v3_ string = "esta es una cadena", "esta es una cadena", "esta es una cadena"
	var _curso1_ string = "olc"
	var _curso2_ string = "olc"
	var _curso3_ string = "olc"
	var _pi1_ float64 = 3
	var _pi2_ float64 = 3.1
	var _pi3_ float64 = 3.14
	var _pi4_ float64 = 3.141
	var _anio1_ float64 = 1
	var _anio2_ float64 = 9
	var _anio3_ float64 = 4
	var _anio4_ float64 = 5
	var _variableNeg_ float64 = -5.0
	var _encabezado1_ string = "Universidad San Carlos de Guatemala...;"
	var _encabezado2_ string = "Escuela de Ciencias y Sistemas Segundo semestre"
	var _flag1_ bool = true
	var _flag2_ bool = false
	var _name1_ byte = 'f'
	var _name2_ byte = 'e'
	var _name3_ byte = 'r'
	var _name4_, _name6_ byte = 'n', 'n'
	var _name5_ byte = 'a'
	var _name7_ byte = 'd'
	var _name8_ byte = 'o'
	var _operaciones1Basica_ float64 = 1 + (1)
	var _operaciones1Basica2_ float64 = _operaciones1Basica_ + _operaciones1Basica_
	var _operaciones1Intermedia_ float64 = 15 + (9 * 8) + 200 / 8 * 3 + 9
	var _operaciones1Avanzadas1_ float64 = ((15 + 9) * 8 + 200 / 8 * 3 + 9)
	var _operaciones1Avanzadas2_ float64 = math.Pow(float64(30), float64(22.2 - 2.2)) + (2)
	var _operaciones1Avanzadas3_ float64 = (math.Pow(float64(30), float64(2))) + (2)
	var _operaciones1Avanzadas4_ float64 = (math.Pow(float64(30), float64(10 - 8 + 9 - 4 * 2 - 1))) + (2)
	var _operaciones1Avanzadas5_ float64 = (math.Pow(float64(30), float64(10 - 8 + 9 - 4 * 2 - 1))) + (2)
	var _operaciones1Avanzadas6_ float64 = (5 * 8) % (1 + 5 + 6)
	var _operacionRela1_ bool = 5 + 5 > 5
	var _operacionRela3_ bool = _operaciones1Basica_ > 8
	var _operacionRela3_ bool = (_operaciones1Basica_ + 6) >= 8
	var _operacionRela3_ bool = (_operaciones1Basica_ + 6) <= 8
	var _operacionRela4_ bool = _operaciones1Basica_ == 8
	var _operacionRela5_ bool = _operaciones1Basica_ == _operaciones1Basica_
	var _operacionRela6_ bool = _operaciones1Basica_ == _operaciones1Basica_ + 1
	var _operacionRela7_ bool = _operaciones1Basica_ == (_operaciones1Basica_) * (8 + 5)
	var _operacionRela5_ bool = _operaciones1Basica_ != _operaciones1Basica_
	var _operLogica_ bool
	var _encabezado2_ string = "Escuela de Ciencias y Sistemas\nSegundo semestre\n"
	var _v1_ = "esta es la cadena numero 1"
	var _v2_ = "estas cadenas deben ser diferentes"
	var _v3_ = "estas cadenas deben ser diferentes"
	var _curso1_ = "Organizacion de lenguajes y compiladores 1"
	var _curso2_ = "Organizacion de lenguajes y compiladores 1"
	var _curso3_ = "Organizacion de lenguajes y compiladores 1"
	var _curso1_ = "Organizacion de lenguajes y compiladores 1"
	var _curso2_ = "Organizacion de lenguajes y compiladores 1"
	var _curso3_ = "Organizacion de lenguajes y compiladores 1"
	fmt.Println(_encabezado1_)
	fmt.Println(_encabezado2_)
	fmt.Print("...")
	fmt.Print(_anio1_)
	fmt.Print(_anio2_)
	fmt.Print(_anio3_)
	fmt.Print(_anio4_)
	fmt.Println(".")
	fmt.Println((_v3_))

	if _v1_ == _v2_ {
		fmt.Println("Al parecer no funciona la asignacion")

		for true {

			if !(!((_variable1_ >= 5 * 5 + 8 / 2))) {
				break
			}
			fmt.Print(_variable1_)
			var _variable1_ = _variable1_ + 1
		}
	}

	if _v1_ == _v2_ {
		fmt.Println("no tiene que imprimir este mensaje")
	}
	 else {
		fmt.Print("este print es un ejemplo")
	}

	if _v1_ == _v2_ {
		fmt.Println("no tiene que imprimir este mensaje")

		if _v1_ == 13 {
			fmt.Println("mensaje de prueba")
		}
		 else if {_v1_ == 14 {
			fmt.Println("mensaje de prueba")
		}
		 else {
			fmt.Println("este print es un ejemplo")
		}

		if _varB_ {
			fmt.Println("Estas definiendo bien los valores")
			var _varaux_ float64 = _variable1_ % 2

			switch _varaux_ {
				case 0:
					fmt.Print("el valor es mayor a 0 y menos a 2")
				case 2:
					fmt.Print("el valor es mayor a 2")
			}
		}
		var _variable1_ float64 = 50.5
		var _v1_, _v2_, _v3_ string = "esta es una cadena", "esta es una cadena", "esta es una cadena"
		var _v1_ = "esta es la cadena numero 1"
		var _v2_ = "estas cadenas deben ser diferentes"
		var _v3_ = "estas cadenas deben ser diferentes"
		fmt.Println(_v3_)

		if _v1_ == _v2_ {
			fmt.Println("Al parecer no funciona la asignacion")

			for true {

				if !(!(_variable1_ >= 5 * 5 + 8 / 2)) {
					break
				}
				fmt.Print(_variable1_)
				var _variable1_ = _variable1_ + 1
			}
		}
		var _varB_ bool = false

		if _varB_ {
			fmt.Println("Estas definiendo bien los valores")
			var _varaux_ float64 = _variable1_ % 2

			switch _varaux_ {
				case 0:
					fmt.Print("el valor es mayor a 0 y menos a 2")
				case 2:
					fmt.Print("el valor es mayor a 2")
			}
		}
		_metodo_1_()

}


func _potenciaManual_(_base_ float64, _exponente_ float64) {
	var _i_ float64 = 0
	var _acumulado_ float64 = 0

	for _i_:=0; _i_ < _exponente_ - 1; _i_++ {
		var _acumulado_ = _acumulado_ + _acumulado_
	}
	fmt.Print(_acumulado_)
	fmt.Println(" Esta es la potencia Manual")
}

func _potenciaFuncion_(_base_ float64, _exponente_ float64) float64 {
	var _potencia_ float64 = math.Pow(float64(_base_), float64(_exponente_))
	return _potencia_
}

func _metodo_1_() {
	fmt.Println("estamos entrando al metodo 1")
	_potenciaManual_(3 * 1 + 4 / 2, 3 + 2)
	fmt.Print(_potenciaFuncion_(3 * 1 + 4 / 2, 3 + 2))
	fmt.Println("Esta es la potencia Funcion")
}
