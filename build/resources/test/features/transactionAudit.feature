# language: es
# encoding: iso-8859-1
@TransactionAudit
Caracter�stica: Validaci�n de datos PEP-USGAP-JD
  Yo como cliente del banco
  Quiero validar mis datos PEP-USGAP-JD
  Para continuar con la solicitud del cr�dito

  @C444510 @regression
  Escenario: Validaci�n PEP
    Dado John es un cliente
    Cuando valida que No es una persona p�blica
    Entonces se permite continuar el proceso

  @C444511 @regression
  Escenario: Validaci�n USGAP
    Dado John es un cliente
    Cuando valida que No tiene impuestos y nacionalidad extranjera
    Entonces se permite continuar el proceso

  @C444512 @regression
  Escenario: Validaci�n Junta Directiva
    Dado John es un cliente
    Cuando valida que No es miembro de Junta Directiva
    Entonces se permite continuar el proceso