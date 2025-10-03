# language: es
# encoding: iso-8859-1
@PhoneModel
Característica: Validacion de desprendible en venta telefónica
  Yo como cliente del banco
  Quiero subir mi desprendible de nómina
  Para realizar el proceso de venta telefónica

  @C487553 @regression
  Escenario: Envío de enlace para subir desprendible en venta telefónica
    Dado John es un cliente de venta telefónica
    Cuando el solicita el enlace para subir su desprendible
    Entonces se envia el enlace exitosamente

  @C487619 @regression @POP-17511
  Escenario: Consultar en enlace de VT cliente habilitado para cargar desprendible
    Dado John es un cliente de venta telefónica
    Cuando el consulta su número de documento en el enlace
    Entonces ingresa al cargue del desprendible

  @C487620 @regression @POP-17514
  Escenario: Consultar en enlace de VT cliente no habilitado para cargar desprendible
    Dado John es un cliente
    Cuando el consulta su número de documento en el enlace
    Entonces no es posible ingresar al cargue del desprendible

  @C492578 @regression @POP-17510
  Escenario: Cargar desprendible en enlace de venta telefónica
    Dado John es un cliente de venta telefónica
    Cuando el selecciona el archivo a cargar
    Entonces se carga el desprendible exitosamente