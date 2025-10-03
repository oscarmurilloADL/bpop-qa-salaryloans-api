# language: es
# encoding: iso-8859-1
Característica: Pagaré unico
  Yo como cliente del banco
  Quiero visualizar si el cliente se encuentra registrado en la BD de pagare unico
  Para solicitar el cargue del documento del pagare

  @POP-19624 @regression
  Escenario: Cliente registrado en BD de pagare unico con nombre de pagaduria incorrecto
    Dado John es un cliente registrado en BD de pagare unico con nombre de pagaduria incorrecto
    Cuando se realiza el proceso de consulta en la BD de pagare unico
    Entonces el aplicativo si debe solicitar al asesor cargar el documento del pagare