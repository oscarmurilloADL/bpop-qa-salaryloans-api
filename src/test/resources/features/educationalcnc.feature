# language: es
# encoding: iso-8859-1
Característica: Validación flujo CNC
  Yo como cliente nuevo conocido del banco
  Quiero validar si soy apto para novar una libranza
  Para acceder a un crédito

  @POP-19745 @regression @CNC
  Escenario: Validar control de edad del cliente menor a 70 años en flujo cnc educativo
    Dado Oscar es un CNC sector educativo menor a 70 años
    Cuando se consulta su edad
    Entonces se valida que es apto para novar una libranza

  @POP-19746 @regression @CNC
  Escenario: Validar control de edad del cliente mayor a 70 años en flujo cnc educativo
    Dado Oscar es un CNC sector educativo mayor a 70 años
    Cuando se consulta su edad
    Entonces se valida que no es apto para novar una libranza

  @POP-17902 @POP-18257 @regression @CNC
  Escenario: Validar error desprendible cargado no corresponde a la pagaduría en flujo cnc educativo
    Dado Oscar es un CNC de la secretaria de antioquia
    Cuando el consulta el estado final de sus documentos
    Entonces se muestra el error con codigo [PayrollChecks018]

  @POP-18245 @regression @CNC
  Escenario: Validar desprendible que no corresponde al titular en flujo cnc educativo
    Dado Oscar es un CNC con desprendible de otro titular
    Cuando el consulta el estado final de sus documentos
    Entonces se muestra el error con codigo [PayrollChecks017]

  @POP-17907 @regression @CNC
  Escenario: Validar error cliente ya tienen un descuento activo previo en flujo cnc educativo
    Dado Oscar es un CNC con descuento activo
    Cuando el consulta el estado final de sus documentos
    Entonces se muestra el error con codigo [PayrollChecks021]

  @POP-18247 @regression @CNC
  Escenario: Validar error al subir un desprendible ilegible en flujo cnc educativo
    Dado Oscar es un CNC con desprendible ilegible
    Cuando el consulta el estado final de sus documentos
    Entonces se muestra el error con codigo [PayrollChecks004]

  @POP-17908 @POP-18260 @regression @CNC
  Escenario: Validar error cuando el desprendible esta vencido en flujo cnc educativo
    Dado Oscar es un CNC con desprendible vencido
    Cuando el consulta el estado final de sus documentos
    Entonces se muestra el error con codigo [PayrollChecks007]

  @POP-19648 @regression @CNC
  Escenario: Validar error tiempo de carga agotado en nueva libranza CNC educativo
    Dado Oscar es un CNC que no carga sus documentos en el tiempo limite
    Cuando el consulta el estado final de sus documentos
    Entonces se muestra el error con codigo TIMEOUT

  @POP-19285 @POP-38132 @regression @CNC
  Escenario: Validar que se solicite solo carga del desprendible cuando solo existe un certificado exitoso en dynamo
    Dado Oscar es un CNC con certificado exitoso previamente cargado
    Cuando el consulta el estado inicial de sus documentos
    Entonces se solicita cargue unicamente del desprendible
    Y el declara la fecha 11/09/2001 de la pagaduria
    Y se muestra la fecha 2001-09-11T00:00:00 en cache

  @POP-19286 @NoAplica
  Escenario: Validar que se solicite solo carga del certificado cuando solo existe un desprendible exitoso en dynamo
    Dado Oscar es un CNC con desprendible exitoso previamente cargado
    Cuando el consulta el estado inicial de sus documentos
    Entonces se solicita cargue unicamente del certificado
