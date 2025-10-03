# language: es
Característica: Verificacion de factor seguro de libranza por edad para tener seguridad del monto a prestar al cliente
Yo como cliente de la aplicacion
Quiero enviar la edad del cliente
Para verificar el valor de factor seguro

  @POP-18593
  Escenario: Verificacion de porcentaje de factor de seguridad de 18 a 50 con valor de 0.0073
    Dado Andres es un cliente con edad 20
    Cuando el envia su edad
    Entonces se verifica el porcentaje 0.0073

  @POP-18593
  Escenario: Verificacion de porcentaje de factor de seguridad de 51 a 69 con valor de 0.112
    Dado Andres es un cliente con edad 55
    Cuando el envia su edad
    Entonces se verifica el porcentaje 0.112

  @POP-18593
  Escenario: Verificacion de porcentaje de factor de seguridad de 70 a 74 con valor de 0.0245
    Dado Andres es un cliente con edad 72
    Cuando el envia su edad
    Entonces se verifica el porcentaje 0.0245

  @POP-18593
  Escenario: Verificacion de porcentaje de factor de seguridad de 75 a 79 con valor de 0.075
    Dado Andres es un cliente con edad 77
    Cuando el envia su edad
    Entonces se verifica el porcentaje 0.075

  @POP-18593
  Escenario: Verificacion de porcentaje de factor de seguridad de 80 a 89 con valor de 0.125
    Dado Andres es un cliente con edad 85
    Cuando el envia su edad
    Entonces se verifica el porcentaje 0.125