# language: es
# encoding: iso-8859-1
@Payrollcheck
Característica: Validación de mi desprendible
  Yo como cliente del banco
  Quiero validar mi desprendible de pago
  Para acceder a un valor mayor en mi crédito

  @C34238 @regression @POP-17456
  Escenario: Subida exitosa de mi desprendible
    Dado William es un cliente no cumple condiciones misma cuota
    Cuando el envía su desprendible de pago
    Entonces se valida que subió correctamente su desprendible

  @C34239 @regression @POP-38510 @POP-38514
  Escenario: Validación exitosa de mi desprendible
    Dado William es un cliente no cumple condiciones misma cuota
    Cuando el pregunta por el estado de su desprendible de pago
    Entonces se valida correctamente su desprendible

  @C492579 @C3696 @regression @POP-17394
  Escenario: Validación de desprendible que no corresponde al titular
    Dado William es un cliente toda cuota con desprendible que no corresponde al titular
    Cuando el pregunta por el estado de su desprendible de pago
    Entonces se informa que debe revisar la titularidad del desprendible

  @C492811 @POP-16956 @regression @POP-17393
  Escenario: Validación de desprendible vencido
    Dado William es un cliente toda cuota con desprendible vencido
    Cuando el pregunta por el estado de su desprendible de pago
    Entonces se informa que su desprendible está vencido y debe actualizarlo

  @C492812 @C4177 @regression @POP-17396
  Escenario: Validación de desprendible en cliente no sujeto de libranza
    Dado William es un cliente toda cuota con desprendible no sujeto de libranza
    Cuando el pregunta por el estado de su desprendible de pago
    Entonces se informa que su tipo de mesada no es sujeta a oferta de libranza

  @C84553 @regression @POP-17397
  Escenario: Validación de desprendible en cliente no sujeto de libranza por política de crecimiento
    Dado Jose es un cliente toda cuota con desprendible menor a 300000
    Cuando el pregunta por el estado de su desprendible de pago con valor menor a 300000
    Entonces se informa que no cumple con la política de crecimiento de monto

  @C531427 @regression @POP-17398
  Escenario: Continuidad de la venta cuando el valor en descuento 3 es igual o mayor a la cuota actual
    Dado Jose es un cliente con valor recibido en descuento 3 igual o mayor a la cuota actual
    Cuando el envía su desprendible de pago con valor recibido en descuento 3 igual o mayor a la cuota actual
    Entonces se informa la lectura exitosa del desprendible y continuidad de la venta

  @C531418 @regression @POP-17395
  Escenario: Desprendible ilegible cuando la respuesta del servicio TAP es 11
    Dado Jose es un cliente con desprendible no legible
    Cuando el sube un desprendible con respuesta COMPLETED
    Entonces se informa la lectura no exitosa del desprendible

  @C440047 @regression @POP-17403
  Escenario: Validación cliente no sujeto de libranza por desprendible de COLPENSIONES con código de pensión no valido
    Dado Jose es un cliente con codigo invalido en desprendible colpensiones
    Cuando el pregunta por el estado de su desprendible de pago
    Entonces se informa que no es sujeto de libranza

  @C531435 @regression @POP-17401
  Escenario: Verificar mensaje al subir desprendible cuando la respuesta de TAP tiene código 201
    Dado Carlos es un cliente con un desprendible de nomina invalido
    Cuando el sube un desprendible con respuesta COMPLETED
    Entonces se informa que no se pudo leer el desprendible se debe envíar de nuevo

  @C531425 @regression
  Escenario: Validación exitosa de mi desprendible con cliente misma cuota en la seccion pide tu credito
    Dado Jose es un cliente misma cuota
    Cuando el solicita una nueva oferta
    Entonces se valida correctamente nuevo desprendible

  @C550153 @regression @POP-17451
  Escenario: Verificar que venta por Casur no consuma servicios de Sygnus
    Dado Camilo es un cliente que tiene una venta de pagaduria Casur
    Cuando carga el desprendible de pago y consulta el estado de su desprendible de pago
    Entonces se valida que no se consuman los servicios de Sygnus

  @POP-38507 @POP-38511 @regression @positionLevel
  Escenario: Validación el nivel del cargo cuando es PROVISIONAL
    Dado Luis es un cliente con un nivel de cargo provisional
    Cuando el envía su desprendible de pago
    Entonces se muestra el error con codigo [PayrollChecks009]

  @POP-38508 @POP-38512 @regression @positionLevel
  Escenario: Validación el nivel del cargo cuando es ENCARGADO
    Dado Luis es un cliente con un nivel de cargo encargado
    Cuando el envía su desprendible de pago
    Entonces se muestra el error con codigo [PayrollChecks009]

  @POP-38509 @POP-38513 @regression @positionLevel
  Escenario: Validación el nivel del cargo cuando es PERIODO DE PRUEBA
    Dado Luis es un cliente con un nivel de cargo periodo de prueba
    Cuando el envía su desprendible de pago
    Entonces se muestra el error con codigo [PayrollChecks009]

  @POP-38516 @POP-38517 @regression @positionLevel
  Escenario: Validación el nivel del cargo cuando llega vacio
    Dado Luis es un cliente con un nivel de cargo vacio
    Cuando el envía su desprendible de pago
    Entonces se muestra el error con codigo [PayrollChecks003]

  @POP-38680 @regression @positionLevel
  Escenario: Validación el nivel del cargo cuando llega un valor inesperado
    Dado Luis es un cliente con un nivel de cargo desconocido
    Cuando el envía su desprendible de pago
    Entonces se muestra el error con codigo [PayrollChecks009]


