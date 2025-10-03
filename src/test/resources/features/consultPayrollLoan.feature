# language: es
# encoding: iso-8859-1
Caracter�stica: Consultar libranza
  Yo como cliente del banco
  Quiero consultar mi libranza
  Para conocer el estado del cr�dito en f�brica

  @450228 @regression @POP-17523 @POP-17525 @consultPayrollLoan
  Escenario: Consultar libranza en estado No Aprobado
    Dado John es un cliente con libranza no aprobada
    Cuando el consulta su n�mero de c�dula
    Entonces observa el estado de su cr�dito en f�brica

  @clientWithPunishedPayroll @S_P @regression @POP-30042 @consultPayrollLoan
  Escenario: busqueda de un cliente con libranza no activa, castigada
    Dado Manuel es un cliente con libranza en estado no activa, castigada
    Cuando el hace la consulta de sus datos
    Entonces el ve un mensaje indicando que la libranza no es activa

  @clientWithOutAuthenticationMethod @S_P @regression @POP-30042 @consultPayrollLoan
  Escenario: busqueda de un cliente sin metodo de autenticacion
    Dado Manuel es un cliente sin metodo de autenticacion
    Cuando el hace la consulta de sus datos
    Entonces el ve un mensaje indicando que el cliente no tiene m�todo de autenticaci�n

  @clientWithAbnormalCondition @S_P @regression @POP-30042 @consultPayrollLoan
  Escenario: busqueda de un cliente con condicion anormal
    Dado Manuel es un cliente con condicion anormal
    Cuando el hace la consulta de sus datos
    Entonces el ve un mensaje indicando que el cliente tiene una condici�n anormal

  @C514807 @regression @consultPayrollLoan
  Escenario: Consultar libranza Sygnus que no fue insertada
    Dado Manuel es un cliente Sygnus con venta negada
    Cuando el consulta su n�mero de c�dula
    Entonces el ve una mensaje indicando que la venta no fue insertada con la causal de caida

  @C302819 @POP-30057 @regression @consultPayrollLoan
  Escenario: Consultar reserva de libranza Sygnus que no fue insertada
    Dado Andres es un asesor que termin� su venta en la aplicaci�n con la reserva de la libranza en Sygnus no es exitosa
    Cuando el consulta la libranza con la cc del cliente
    Entonces el debe visualizar un mensaje indicando que la reserva no fue ingresada

  @perfomanceSearch
  Escenario: Consultar datos del cliente
    Dado Andres es un asesor
    Cuando el consulta un cliente
