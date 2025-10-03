# language: es
# encoding: iso-8859-1
@Buro
Característica: Validar los habitos financieros y capacidad de pago
  Yo como cliente del banco
  Quiero saber mi capacidad de pago y mis habitos financieros
  Para poder realizar la ampliacion de mi credito de libranza

  @C16025 @regression
  Escenario: Cliente es viable para realizar ampliacion del credito
    Dado Andres es un cliente
    Cuando el consulta sus datos en preselecta
    Entonces es viable para realizar su crédito de libranza

  @C16026 @regression @POP-34621
  Escenario: Cliente tiene cuenta embargada
    Dado Jorge es un cliente con cuenta embargada
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por cuenta embargada

  @C16027 @regression @POP-34622
  Escenario: Cliente tiene cartera castigada
    Dado Jorge es un cliente con cartera castigada
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por malos hàbitos de pago

  @C16028 @regression @POP-34623
  Escenario: Cliente tiene mora mayor a 90 dias
    Dado Jorge es un cliente con mora mayor a 90 dias
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por malos hàbitos de pago

  @C492813 @C84557 @regression @POP-34624
  Escenario: Cliente tiene capacidad de descuento menor a $300.000
    Dado Carlos es un cliente con capacidad de descuento menor a 300000
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por no tener el mìnimo de capacidad de descuento

  @C98406 @regression
  Escenario: Cliente es viable en toda cuota
    Dado Andres es un cliente no cumple condiciones misma cuota
    Cuando el consulta sus datos en preselecta para toda cuota
    Entonces es viable para realizar su crédito de libranza

  @C130012 @regression
  Escenario: Viabilidad de crèdito por valor màximo en toda cuota con fidelizaciòn
    Dado Andres es un cliente no cumple condiciones misma cuota
    Cuando el solicita el valor máximo aprobado para toda cuota con fidelización
    Entonces es viable para realizar su crédito de libranza

  @C188676 @regression
  Escenario: Viabilidad de crèdito por valor màximo en misma cuota con fidelización
    Dado Andres es un cliente
    Cuando el solicita el valor máximo aprobado para misma cuota con fidelización
    Entonces es viable para realizar su crédito de libranza

  @C188677 @regression
  Escenario: Viabilidad de crèdito por recàlculo en misma cuota con fidelización
    Dado Andres es un cliente
    Cuando el solicita valor recalculado para misma cuota con fidelización
    Entonces es viable para realizar su crédito de libranza

  @C5286 @regression @POP-34625
  Escenario: Cliente no viable porque los Ofi estàn en cero
    Dado Jorge es un cliente con ofi en 0
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por La respuesta no trae oferta para el cliente

  @520822 @regression @POP-34626
  Escenario: Cliente no viable para tramitar por este canal
    Dado Luis es un cliente no viable para tramitar por este canal
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por Negado en preselecta por capacidad de pago

  @POP-34409 @regression
  Escenario: Cliente con error en el proceso de preselecta
    Dado Luis es un cliente con error en preselecta
    Cuando el consulta sus datos en preselecta
    Entonces el proceso de preselecta presenta un error 006

  @POP-35091 @regression
  Escenario: Cliente no viable por paz y salvo sector financiero
    Dado Luis es un cliente con paz y salvo sector financiero
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por Paz y salvo sector financiero

  @POP-35113 @regression
  Escenario: Cliente no viable por paz y salvo por habito de pago actual banco popular
    Dado Luis es un cliente con paz y salvo por habito de pago actual banco popular
    Cuando el consulta sus datos en preselecta
    Entonces no es viable por Paz y salvo por habito pago actual banco popular