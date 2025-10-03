# language: es
# encoding: iso-8859-1
@Accept
Característica: Aceptación de mi crédito
  Yo como cliente del banco
  Quiero aceptar los valores de mi crédito
  Para que posteriormente se haga mi novación

  @C34241 @regression
  Escenario: Aceptación exitosa de mi crédito
    Dado John es un cliente
    Cuando el acepta los valores de su crédito
    Entonces los valores son procesados


