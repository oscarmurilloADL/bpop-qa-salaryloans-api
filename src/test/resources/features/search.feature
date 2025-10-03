# language: es
# encoding: iso-8859-1
Característica: Busqueda del cliente
  Yo como cliente de la aplicacion
  Quiero poder validar mis datos
  Para verificar que pueda acceder a una libranza

  @C16018 @C439084 @regression @Search
  Escenario: busqueda de un cliente exitosa
    Dado Andres es un cliente
    Cuando el envía sus datos para ser verificados
    Entonces se validan correctamente

  @C16019 @regression @Search
  Escenario: busqueda de un cliente inexistente
    Dado Jorge es un no cliente
    Cuando el envía sus datos para ser verificados
    Entonces no debe aparecer en el sistema

  @C16020 @regression @C550165 @Search
  Escenario: busqueda de un cliente sin numero de celular
    Dado Manuel es un cliente sin celular
    Cuando el envía sus datos para ser verificados
    Entonces se debe indicar que no tiene celular

  @C391541 @C192957 @regression @POP-17422 @Search
  Escenario: busqueda de un cliente con libranza en estado derogatorio
    Dado Manuel es un cliente con libranza en estado derogatorio
    Cuando el envía sus datos para ser verificados
    Entonces se indica que la libranza no es activa

  @C514725 @C514726 @POP-30108 @regression @Search
  Escenario: busqueda de un cliente sin libranzas en estado activo
    Dado Diego es un cliente que no tiene pagadurias en estado activo
    Cuando el envía sus datos para ser verificados
    Entonces se retorna la marca CUSTOMER_WITHOUT_LOANS

  @C514727 @C514728 @POP-30108 @regression @Search
  Escenario: busqueda de un cliente que nunca ha tenido pagaduría
    Dado Diego es un cliente que nunca ha tenido pagaduría
    Cuando el envía sus datos para ser verificados
    Entonces se retorna la marca CUSTOMER_WITHOUT_LOANS
