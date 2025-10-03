# language: es
# encoding: iso-8859-1
@Events
Característica: Eventos de aceptación de condiciones del crédito
  Yo como cliente del banco
  Quiero aceptar las condiciones de mi crédito
  Para completar la solicitud del mismo

  @C447391 @regression
  Escenario: Aceptación de la oferta del crédito
    Dado John es un cliente
    Cuando él acepta la oferta del crédito
    Entonces se valida el evento

  @C447392 @regression
  Escenario: Confirmación de los términos y condiciones del crédito
    Dado John es un cliente
    Cuando él confirma los términos y condiciones del crédito
    Entonces se valida el evento

  @C447393 @regression
  Escenario: Selección de cuenta y documentos del cliente
    Dado John es un cliente
    Cuando él finaliza la selección de la cuenta y los documentos
    Entonces se valida el evento
