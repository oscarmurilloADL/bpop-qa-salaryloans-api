# language: es
# encoding: iso-8859-1
@PhoneModel
Caracter�stica: Validacion de desprendible en venta telef�nica
  Yo como cliente del banco
  Quiero subir mi desprendible de n�mina
  Para realizar el proceso de venta telef�nica

  @C487553 @regression
  Escenario: Env�o de enlace para subir desprendible en venta telef�nica
    Dado John es un cliente de venta telef�nica
    Cuando el solicita el enlace para subir su desprendible
    Entonces se envia el enlace exitosamente

  @C487619 @regression @POP-17511
  Escenario: Consultar en enlace de VT cliente habilitado para cargar desprendible
    Dado John es un cliente de venta telef�nica
    Cuando el consulta su n�mero de documento en el enlace
    Entonces ingresa al cargue del desprendible

  @C487620 @regression @POP-17514
  Escenario: Consultar en enlace de VT cliente no habilitado para cargar desprendible
    Dado John es un cliente
    Cuando el consulta su n�mero de documento en el enlace
    Entonces no es posible ingresar al cargue del desprendible

  @C492578 @regression @POP-17510
  Escenario: Cargar desprendible en enlace de venta telef�nica
    Dado John es un cliente de venta telef�nica
    Cuando el selecciona el archivo a cargar
    Entonces se carga el desprendible exitosamente