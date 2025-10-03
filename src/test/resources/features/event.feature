# language: es
# encoding: iso-8859-1
@Events
Caracter�stica: Eventos de aceptaci�n de condiciones del cr�dito
  Yo como cliente del banco
  Quiero aceptar las condiciones de mi cr�dito
  Para completar la solicitud del mismo

  @C447391 @regression
  Escenario: Aceptaci�n de la oferta del cr�dito
    Dado John es un cliente
    Cuando �l acepta la oferta del cr�dito
    Entonces se valida el evento

  @C447392 @regression
  Escenario: Confirmaci�n de los t�rminos y condiciones del cr�dito
    Dado John es un cliente
    Cuando �l confirma los t�rminos y condiciones del cr�dito
    Entonces se valida el evento

  @C447393 @regression
  Escenario: Selecci�n de cuenta y documentos del cliente
    Dado John es un cliente
    Cuando �l finaliza la selecci�n de la cuenta y los documentos
    Entonces se valida el evento
