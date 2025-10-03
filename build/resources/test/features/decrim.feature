#language: es

Característica: Validacion autenticación Decrim
  Yo como cliente de la aplicacion
  Quiero probar la validación Decrim
  Para verificar que pueda tramitar una libranza

  @C514733
  Escenario: Ampliación fallida en Autenticación fuerte Decrim
    Dado Andres es un cliente que solicita una venta por ampliación enviando documentos incorrectos a Decrim
    Cuando el solicita validar los documentos a Decrim
    Entonces el recibe un mensaje indicando que la autenticación fue fallida porque el rostro no coincide con el de referencia

