# language: es
# encoding: iso-8859-1
Característica: Traer la informacion de las cuentas del cliente
  Yo como cliente del banco
  Quiero ver la informacion de mis cuentas
  Para hacer el abono de mi libranza

  @C16029 @POP-17009 @POP-17010 @regression @SimValidation @Accounts
  Escenario: Cliente con cuentas en estado activa, cancelada, nueva e inactiva
    Dado Cesar es un cliente
    Cuando el consulta sus cuentas
    Entonces el puede conocer la información de estas y se le muestra solo activas e inactivas

  @POP-24108 @regression @Accounts
  Escenario: Flujo exitoso de cliente con cuentas validas para desembolso y tambien cuentas en estado derogatorio
    Dado Cristian es un cliente con cuentas validas para desembolso y tambien cuentas derrogadas, bloqueadas y con doble titularidad
    Cuando el consulta la titularidad de sus cuentas
    Entonces el cliente tiene cuentas derrogadas, bloqueadas y con doble titularidad
    Cuando el consulta sus cuentas
    Entonces el puede conocer la información de estas y se le muestra solo la disponible

  @POP-24113 @regression @Accounts
  Escenario: Flujo exitoso Cliente tiene cuentas validas para desembolso y cuentas con doble titularidad marcada vacio o Null
    Dado Cristian es un cliente con cuentas valida para desembolso con doble titularidad en valor null
    Cuando el consulta la titularidad de sus cuentas
    Entonces el cliente tiene cuentas aptas
    Cuando el consulta sus cuentas
    Entonces el puede conocer la información de estas y se le muestra solo la disponible

  @POP-24121 @regression @Accounts
  Escenario: Flujo no exitoso Cliente no tiene cuenta valida para desembolso y tiene cuenta embargada
    Dado John es un cliente con cuenta de ahorros Flexcube en estado derogatorio
    Cuando el consulta la titularidad de sus cuentas
    Entonces se valida que el cliente tiene cuenta en estado derogatorio

  @POP-24120 @regression @Accounts
  Escenario: Flujo no exitoso Cliente no tiene cuentas validas para desembolso y tiene cuentas con doble titularidad
    Dado John es un cliente con cuenta de ahorros Flexcube en estado doble titularidad
    Cuando el consulta la titularidad de sus cuentas
    Entonces se valida que el cliente tiene cuenta en estado doble titularidad

  @POP-24123 @regression @Accounts
  Escenario: Flujo no exitoso Cliente no tiene cuenta valida para desembolso y tiene cuenta bloqueada
    Dado John es un cliente con cuenta de ahorros Flexcube en estado bloqueado
    Cuando el consulta la titularidad de sus cuentas
    Entonces se valida que el cliente tiene cuenta en estado bloqueado

  @POP-24125 @regression @Accounts
  Escenario: Flujo no exitoso Cliente no tiene cuenta valida para desembolso y tiene cuentas derrogadas, bloqueadas y con doble titularidad
    Dado John es un cliente con cuentas derrogadas, bloqueadas y con doble titularidad
    Cuando el consulta la titularidad de sus cuentas
    Entonces el cliente tiene cuentas derrogadas, bloqueadas y con doble titularidad y el flujo es no exitoso

  @POP-24126 @regression @Accounts
  Escenario:  Flujo no exitoso Cliente no tiene cuentas Flexcube
    Dado John es un cliente que no tiene cuentas Flexcube
    Cuando el consulta la titularidad de sus cuentas
    Entonces el cliente no tiene cuentas Flexcube y el flujo es no exitoso

  @POP-24127 @regression @Accounts
  Escenario:  Flujo no exitoso cuando se presenta error técnico o caída del servicio
    Dado John es un cliente que al consultar obtiene error técnico
    Cuando el consulta la titularidad de sus cuentas
    Entonces se obtiene error técnico y el flujo es no exitoso

  @POP-24128 @regression @Accounts
  Escenario: Flujo exitoso Cliente tiene cuentas validas para desembolso y tiene cuentas sin la estructura correcta
    Dado John es un cliente con cuentas validas para desembolso y sin la estructura correcta
    Cuando el consulta la titularidad de sus cuentas
    Entonces el cliente tiene cuentas aptas
    Cuando el consulta sus cuentas
    Entonces el puede conocer la información de estas y se le muestra solo las de estructura correcta

  @POP-24129 @regression @Accounts
  Escenario:  Flujo no exitoso Cliente tiene cuentas sin la estructura correcta
    Dado John es un cliente con cuentas sin la estructura correcta
    Cuando el consulta la titularidad de sus cuentas
    Entonces el cliente tiene cuentas sin la estructura correcta y el flujo es no exitoso