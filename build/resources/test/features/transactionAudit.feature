# language: es
# encoding: iso-8859-1
@TransactionAudit
Característica: Validación de datos PEP-USGAP-JD
  Yo como cliente del banco
  Quiero validar mis datos PEP-USGAP-JD
  Para continuar con la solicitud del crédito

  @C444510 @regression
  Escenario: Validación PEP
    Dado John es un cliente
    Cuando valida que No es una persona pública
    Entonces se permite continuar el proceso

  @C444511 @regression
  Escenario: Validación USGAP
    Dado John es un cliente
    Cuando valida que No tiene impuestos y nacionalidad extranjera
    Entonces se permite continuar el proceso

  @C444512 @regression
  Escenario: Validación Junta Directiva
    Dado John es un cliente
    Cuando valida que No es miembro de Junta Directiva
    Entonces se permite continuar el proceso