def _potenciaManual_ (_base_, _exponenete_):
	_i_ = 0
	_acumulado_ = 0

	for _i_ in range(0,_exponente_ - 1):
		_acumulado_ = _acumulado_ + _acumulado_
	print(" Esta es la potencia Manual")
	print(_acumulado_)

def _potenciaFuncion_ (_base_, _exponente_):
	_potencia_ = _base_ ** _exponente_
	print(" Esta es la potencia Manual pero con una funcion")
	print(_acumulado_)
	return _potencia_

def _metodo_1_ ():
	print("estamos entrando al metodo 1")
	_potenciaManual_(3 * 1 + 4 / 2, 3 + 2)
	print(_potenciaFuncion_(3 * 1 + 4 / 2, 3 + 2))
	print(" Esta es la potencia Funcion")
	print("FIN del metodo1")

def _fibonacci_ (_n_):

	if (_n_ > 1) :
		return _fibonacci_(_n_ - 1) + _fibonacci_(_n_ - 2)
	elif (_n_ == 1) :
		return 1
	elif (_n_ == 0) :
		return 0
	else:
		print(“Debes ingresar un valor mayor o igual a 1”)


def main():
	_variable1_ = 5
	_variable2_ = 50
	_v1_, _v2_, _v3_ = "esta es una cadena", "esta es una cadena", "esta es una cadena"
	_curso1_ = "olc"
	_curso2_ = "olc"
	_curso3_ = "olc"
	_pi1_ = 3
	_pi2_ = 3.1
	_pi3_ = 3.14
	_pi4_ = 3.141
	_anio1_ = 1
	_anio2_ = 9
	_anio3_ = 4
	_anio4_ = 5
	_variableAntesNeg_ = 5.0
	_encabezado1_ = "Universidad San Carlos de Guatemala...;"
	_encabezado2_ = "Escuela de Ciencias y Sistemas\nSegundo semestre\n"
	_flag1_ = True
	_flag2_ = False
	_name1_ = 'f'
	_name2_ = 'e'
	_name3_ = 'r'
	_name4_, _name6_ = 'n', 'n'
	_name5_ = 'a'
	_name7_ = 'd'
	_name8_ = 'o'
	_operaciones1Basica_ = 1 + (1)
	_operaciones1Basica2_ = _operaciones1Basica_ + _operaciones1Basica_
	_operaciones1Intermedia_ = 15 + (9 * 8) + 200 / 8 * 3 + 9
	_operaciones1Avanzadas1_ = ((15 + 9) * 8 + 200 / 8 * 3 + 9)
	_operaciones1Avanzadas2_ = (1 ** 1 + 2) ** 22.2 - 2.2 - 15 + (2)
	_operaciones1Avanzadas3_ = (30 ** 2) + (2)
	_operaciones1Avanzadas4_ = (30 ** 10 - 8 + 9 - 4 * 2 - 1) + (2)
	_operaciones1Avanzadas5_ = 30 ** 10 - 8 + 9 - 4 * 2 - 1 + (15 / (1 ** 1 + 2))
	_operaciones1Avanzadas6_ = (5 * 8) % (1 + 5 + 6)
	_operaciones1Avanzadas7_ = (5 * 8) % ((5 + 3) ** 8)
	_operaciones1Avanzadas8_ = (5 * 8) % ((5 + (2 ** 2 * 2)) ** 8)
	_operacionRela1_ = 5 + 5 > 5
	_operacionRela3_ = _operaciones1Basica_ > 8
	_operacionRela3_ = (_operaciones1Basica_ + 6) >= 8
	_operacionRela3_ = (_operaciones1Basica_ + 6) <= 8
	_operacionRela4_ = _operaciones1Basica_ == 8
	_operacionRela31_ = (_operaciones1Basica_ + 6) < 8
	_operacionRela5_ = _operaciones1Basica_ == _operaciones1Basica_
	_operacionRela6_ = _operaciones1Basica_ == _operaciones1Basica_ + 1
	_operacionRela7_ = _operaciones1Basica_ == (_operaciones1Basica_) * (8 + 5)
	_operacionRela5_ = _operaciones1Basica_ != _operaciones1Basica_
	_v1_ = "esta es la cadena numero 1"
	_v2_, _v3_ = "estas cadenas deben ser diferentes", "estas cadenas deben ser diferentes"
	_curso1_, _curso2_, _curso3_ = "Organizacion de lenguajes y compiladores 1", "Organizacion de lenguajes y compiladores 1", "Organizacion de lenguajes y compiladores 1"
	print(_operaciones1Basica_)
	print(_operaciones1Basica2_)
	print(_operaciones1Intermedia_)
	print(_operaciones1Avanzadas1_)
	print(_operaciones1Avanzadas2_)
	print(_operaciones1Avanzadas3_)
	print(_operaciones1Avanzadas4_)
	print(_operaciones1Avanzadas5_)
	print(_operaciones1Avanzadas6_)
	print(_operaciones1Avanzadas7_)
	print(_operaciones1Avanzadas8_)
	print(_operacionRela1_)
	print(_operacionRela3_)
	print(_operacionRela3_)
	print(_operacionRela3_)
	print(_operacionRela4_)
	print(_operacionRela31_)
	print(_operacionRela5_)
	print(_operacionRela6_)
	print(_operacionRela7_)
	print(_operacionRela5_)
	print(_encabezado1_)
	print(_encabezado2_)
	print("...")
	print(_anio1_)
	print(_anio2_)
	print(_anio3_)
	print(_anio4_)
	print(".")
	print((_v3_))
	print(_name1_)
	print(_name2_)
	print(_name3_)
	print(_name4_)
	print(_name5_)
	print(_name6_)
	print(_name7_)
	print(_name8_)
	print(".")
	print(_variable1_)
	print(_variable2_)
	print(_v1_)
	print(_v2_)
	print(_v3_)
	print(_curso1_)
	print(_curso2_)
	print(_curso3_)
	print(_pi1_)
	print(_pi2_)
	print(_pi3_)
	print(_pi4_)
	print(_anio1_)
	print(_anio2_)
	print(_anio3_)
	print(_anio4_)
	print(_variableAntesNeg_)
	print(_flag1_)
	print(_flag2_)

	if True == True :
		print("El if esta correcto")
	else:
		print("no tiene que imprimir este mensaje, error en instruccion if")

	if _v1_ == _v2_ :
		print("Al parecer no funciona la asignacion, restame puntos en el if")

		while not ((_variable1_ >= 5 * 5 + 8 / 2)):
			print(_variable1_)
			_variable1_ = _variable1_ + 1

	if _v1_ == _v2_ :
		print("no tiene que imprimir este mensaje, error en instruccion if")
	else:
		print("INTRUCCION IF con Else esta correcta")

	if _v1_ == _v2_ :
		print("no tiene que imprimir este mensaje,error en l aintruccion if")
	elif _v1_ == 13 :
		print("no tiene que imprimir este mensaje,error en l aintruccion if")
	elif _v1_ == 14 :
		print("no tiene que imprimir este mensaje,error en l aintruccion if")
	else:
		print("Instruccion if con else if esta correcta")
		_varB_ = False

		if _varB_ :
			print("Estas definiendo mal los valores")
			_varaux_ = _variable1_ % 2

			if _varaux_ == 0:
				print("el valor es mayor a 0 y menos a 2")
			elif _varaux_ == 2:
				print("el valor es mayor a 2")
		else:
			print(“Estas definiendo bien los valores c:”)
		_metodo_1_()
		print("El fibonacci de 5 es ")
		print(_fibonacci_(5))
		print(".")


if __name__ == "__main__":
	main()