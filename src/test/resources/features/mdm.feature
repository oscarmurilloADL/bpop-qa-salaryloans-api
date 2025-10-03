# language: es
# encoding: iso-8859-1
@MDM
Característica: Validar consulta y actualización de datos del cliente
  Yo como cliente del banco
  Quiero ver y actualizar mis datos personales
  Para que mi crédito tenga mis datos al día

  @C16023 @regression
  Escenario: Cliente puede consultar sus datos personales
    Dado Ricardo es un cliente
    Cuando el consulta sus datos personales
    Entonces el puede visualizarlos

  @C16024 @regression @POP-18121 @POP-29791 @POP-29795 @POP-29797 @POP-29798 @POP-29799
  Escenario: Cliente puede actualizar sus datos personales
    Dado Pedro es un cliente
    Cuando el envía sus nuevos datos personales
    Entonces su información es actualizada
    Y el valida los datos enviados en el request de consulta
    Y el valida los datos enviados en el request de actualizacion

  @C514766 @POP-30054 @regression
  Escenario: Error de preselecta en actualización MDM por CNC
    Dado Pedro es un cliente nuevo conocido
    Cuando ella envía los valores para actualizar sus datos
    Entonces se genera un error por preselecta, debe volver a procesar la operación

  @ClienteNuevoConocidoConMalosHabitosDePago @POP-30053 @regression
  Escenario: Validar error en actualización MDM para CNC
    Dado que Ingrid es un cliente NC que presenta reporte por malos hábitos de pago
    Cuando ella envía los valores para actualizar sus datos
    Entonces ella visualiza un mensaje de error indicando que fue negado por Preselecta

  @POP-21958 @regression
  Escenario: Consultar cliente PN cuando no tiene método de contacto y tiene único registro de datos
    Dado Pedro es un cliente sin metodo de contacto y unico componentID
    Cuando el hace la consulta de sus datos
    Entonces se genera un error por no tener metodo de contacto

  @POP-21959 @regression
  Escenario: Consultar cliente PN cuando no tiene método de contacto en primer y segundo registro de datos
    Dado Pedro es un cliente sin metodo de contacto en primer y segundo registro de datos
    Cuando el hace la consulta de sus datos
    Entonces se genera un error por no tener metodo de contacto

  @POP-21960 @regression
  Escenario: Consultar cliente PN cuando tiene método de contacto únicamente en el segundo registro de datos
    Dado Pedro es un cliente con metodo de contacto unicamente en el segundo registro de datos
    Cuando el hace la consulta de sus datos
    Entonces se muestra el numero de celular **********118
