# language: es
# encoding: iso-8859-1
@LegalConditions
Característica: Validar condiciones legales
  Yo como cliente del banco
  Quiero aceptar las condiciones legales
  Para continuar el proceso del credito

  @C116543 @regression
  Escenario: Cliente acepta tratamiento de datos personales
    Dado Cristian es un cliente
    Cuando acepta tratamiento de datos personales
    Entonces se valida su respuesta correctamente

  @C116544 @regression
  Escenario: Cliente rechaza tratamiento de datos personales
    Dado Cristian es un cliente
    Cuando rechaza el tratamiento de datos personales
    Entonces se valida su respuesta correctamente

  @C151022 @regression
  Escenario: Cliente acepta consulta en centrales de riesgo
    Dado Cristian es un cliente
    Cuando acepta la consulta en centrales de riesgo
    Entonces se valida su respuesta correctamente

  @C151038 @regression
  Escenario: Cliente rechaza consulta en centrales de riesgo
    Dado Cristian es un cliente
    Cuando rechaza la consulta en centrales de riesgo
    Entonces se valida su respuesta correctamente