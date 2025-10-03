# language: es
# encoding: iso-8859-1
Característica: Consulta en Sygnus
  Yo como cliente de la aplicacion
  Quiero consultar la respuesta de Sygnus
  Para validar si el cliente tiene cupo disponible

  @C294917 @regression @Sygnus
  Escenario: Consulta de vinculaciones en Sygnus exitosa
    Dado Andres es un cliente en Sygnus
    Y la plataforma esta abierta
    Cuando el verifica su disponible
    Entonces obtiene las vinculaciones en Sygnus

  @C294930 @C297192 @regression @POP-17450 @Sygnus
  Escenario: Consulta de vinculaciones no exitosa por cliente sin autorización en Sygnus
    Dado Andres es un cliente sin autorización en Sygnus
    Y la plataforma esta abierta
    Cuando el verifica su disponible
    Entonces el cliente no tiene autorización en Sygnus

  @C296832 @POP-34087 @regression @Sygnus
  Escenario: Consulta de cupo en Sygnus exitosa
    Dado Andres es un cliente en Sygnus
    Y la plataforma esta abierta
    Cuando el selecciona el número de vinculación
    Entonces obtiene el cupo disponible en Sygnus

  @C297341 @POP-34086 @regression @Sygnus
  Escenario: Consulta de cupo en Sygnus no exitosa
    Dado Andres es un cliente sin cupo en Sygnus
    Y la plataforma esta abierta
    Cuando el selecciona el número de vinculación
    Entonces no obtiene cupo disponible en Sygnus

  @C349181 @POP-30055 @regression @Sygnus
  Escenario: Error -2 al reservar cupo en Sygnus
    Dado Andres es un cliente en Sygnus con error en la reserva de cupo -2
    Y la plataforma esta abierta
    Cuando el solicita una novación tipo Sygnus
    Entonces no puede finalizar su proceso de venta con Sygnus -2

  @POP-30056 @regression @Sygnus
  Escenario: Error -9 al reservar cupo en Sygnus
    Dado Andres es un cliente en Sygnus con error en la reserva de cupo -9
    Y la plataforma esta abierta
    Cuando el solicita una novación tipo Sygnus
    Entonces no puede finalizar su proceso de venta con Sygnus -9

  @C530517 @POP-30067 @regression @Sygnus
  Escenario: Desborde cliente no apto para recalculo ordinaria y sin disponible BRMS en Sygnus
    Dado Andres es un cliente no apto para recalculo ordinaria y sin disponible BRMS en Sygnus
    Y la plataforma esta abierta
    Cuando el selecciona el número de vinculación
    Entonces el cliente es desbordado por política de crecimiento


