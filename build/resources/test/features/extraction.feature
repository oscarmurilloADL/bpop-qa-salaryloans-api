# language: es
# encoding: iso-8859-1
@Extraction
Característica: Extractor e interpretador de desprendibles
  Yo como cliente del banco
  Quiero consumir extractor e interpretador de desprendibles
  Para evitar la carga de un desprendible por parte del cliente

  @POP-32870
  @POP-32868
  @POP-32872
  @POP-32878
  @POP-32880
  @POP-32884
  @POP-32967
  @regression
  @colpensiones
  Escenario: Validar extraccion e interpretacion exitosa para cliente COLPENSIONES CNC
    Dado John es un cliente CNC con extractor colpensiones
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para NEW_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para COLPENSIONES
    Y el consulta los datos enviados a interpretador para COLPENSIONES
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @fopep @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente FOPEP CNC
    Dado John es un cliente CNC con extractor fopep
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para NEW_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para FOPEP
    Y el consulta los datos enviados a interpretador para FOPEP
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @fiduprevisora @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente FIDUPREVISORA CNC
    Dado John es un cliente CNC con extractor fiduprevisora
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para NEW_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para FIDUPREVISORA
    Y el consulta los datos enviados a interpretador para FIDUPREVISORA
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @ferrocarriles @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente FERROCARRILES CNC
    Dado John es un cliente CNC con extractor ferrocarries
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para NEW_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para FERROCARRILES
    Y el consulta los datos enviados a interpretador para FERROCARRILES
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @positiva @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente POSITIVA CNC
    Dado John es un cliente CNC con extractor positiva
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para NEW_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para POSITIVA
    Y el consulta los datos enviados a interpretador para POSITIVA
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles


  @POP-32969
  @POP-32912
  @POP-32908
  @POP-32906
  @POP-32900
  @POP-32898
  @POP-32896
  @colpensionesExt
  @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente COLPENSIONES Ampliacion un desprendible
    Dado John es un cliente con extractor colpensiones
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para COLPENSIONES
    Y el consulta los datos enviados a interpretador para COLPENSIONES
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @fopepExt
  @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente FOPEP Ampliacion un desprendible
    Dado John es un cliente con extractor fopep
    Y se ajusta la fecha de la lambda de extractor en 2025/09/05
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para FOPEP
    Y el consulta los datos enviados a interpretador para FOPEP
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @fiduprevisoraExt
  @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente FIDUPREVISORA Ampliacion un desprendible
    Dado John es un cliente extractor fiduprevisora
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para FIDUPREVISORA
    Y el consulta los datos enviados a interpretador para FIDUPREVISORA
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles


  @ferrocarrilesExt
  @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente FERROCARRILES Ampliacion un desprendible
    Dado John es un cliente extractor ferrocarriles
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para FERROCARRILES
    Y el consulta los datos enviados a interpretador para FERROCARRILES
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @positivaExt
  @regression
  Escenario: Validar extraccion e interpretacion exitosa para cliente POSITIVA Ampliacion un desprendible
    Dado John es un cliente con extractor positiva
    Cuando el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Y el valida la respuesta para extractor e interpretador de desprendibles
    Y el consulta el nit homologado para POSITIVA
    Y el consulta los datos enviados a interpretador para POSITIVA
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado EXTRACT_SUCCESS
    Y el consulta el evento INTERPRETER_PROCESS y se espera en estado INTERPRETER_SUCCESS
    Y el consulta en base de datos la informacion de interpretador
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @regression @POP-32904 @case5
  Escenario: Validar cliente con extractor por fecha actual en la base de desprendibles Ampliacion
    Dado Luis es un cliente con fecha 2024/10 en su desprendible ampliacion
    Cuando se ajusta la fecha de la lambda de extractor en 2024/11/05
    Y el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Entonces el valida la respuesta para extractor e interpretador de desprendibles

  @regression @POP-32904 @case6
  Escenario: Validar cliente con fecha al corte con el dia 5 del mes Ampliacion
    Dado Luis es un cliente con fecha 2024/09 en su desprendible ampliacion
    Cuando se ajusta la fecha de la lambda de extractor en 2024/11/05
    Y el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Entonces el valida la respuesta para extractor e interpretador de desprendibles


  @regression @POP-32904 @case7
  Escenario: Validar cliente sin extractor en la base de desprendibles por fecha no valida Ampliacion
    Dado Luis es un cliente con fecha 2024/08 en su desprendible ampliacion
    Cuando se ajusta la fecha de la lambda de extractor en 2024/11/05
    Y el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Entonces el valida la respuesta para extractor cuando su desprendible no esta vigente
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado CUSTOMER_WITHOUT_EXTRACT

  @regression @POP-32904 @case8
  Escenario: Validar cliente sin extractor en la base de desprendibles dia despues de fecha de corte Ampliacion
    Dado Luis es un cliente sin extractor con fecha 2024/09 en su desprendible ampliacion
    Cuando se ajusta la fecha de la lambda de extractor en 2024/11/06
    Y el realiza el proceso de extractor e interpretador de desprendibles para EXTEND_PAYROLL_LOAN
    Entonces el valida la respuesta para extractor cuando su desprendible no esta vigente
    Y el consulta el evento EXTRACTOR_PROCESS y se espera en estado CUSTOMER_WITHOUT_EXTRACT


