# language: es
# encoding: iso-8859-1
Caracter�stica: Validacion de viabilidad externa
  Yo como cliente de la aplicacion
  Quiero validar el numero de mi c�dula
  Para verificar mi viabilidad externa

  @C550169 @C610844 @regression @peaceAndSafe
  Escenario: Validar cuando el cliente presenta reporte de h�bito de pago con otra entidad
    Dado Carlos es un cliente con reporte de h�bito de pago
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @C610813 @C610845 @regression @peaceAndSafe
  Escenario: Validar viabilidad externa exitosa
    Dado Oscar es un cliente
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad exitosa y externa

  @POP-17345 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Hist�rica 30 externa
    Dado Oscar es un cliente con Mora Hist�rica 30 externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Hist�rica 30 y ExternalViable

  @POP-17346 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Hist�rica 60 externa
    Dado Oscar es un cliente con Mora Hist�rica 60 externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Hist�rica 60 y ExternalViable

  @POP-17347 @noAplica
  Escenario: Validar subcausal Mora Hist�rica 90 externa
    Dado Oscar es un cliente con Mora Hist�rica 90 externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Hist�rica 90 y ExternalViable

  @POP-17348 @noAplica
  Escenario: Validar subcausal Mora Hist�rica 120 externa
    Dado Oscar es un cliente con Mora Hist�rica 120 externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Hist�rica 120 y ExternalViable

  @POP-17349 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Libranza externa
    Dado Oscar es un cliente con Mora Actual Libranza externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Libranza y ExternalViable

  @POP-17350 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual TDC externa
    Dado Oscar es un cliente con Mora Actual TDC externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual TDC y ExternalViable

  @POP-17351 @NoAplica
  Escenario: Validar subcausal Mora Actual Libre Inversi�n externa
    Dado Oscar es un cliente con Mora Actual Libre Inversi�n externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Libre Inversi�n y ExternalViable

  @POP-17352 @NoAplica
  Escenario: Validar subcausal Mora Actual Cupo M�ltiple externa
    Dado Oscar es un cliente con Mora Actual Cupo M�ltiple externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Cupo M�ltiple y ExternalViable

  @POP-17353 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Comercial externa
    Dado Oscar es un cliente con Mora Actual Comercial externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Comercial y ExternalViable

  @POP-17354 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Vivienda externa
    Dado Oscar es un cliente con Mora Actual Vivienda externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Vivienda y ExternalViable

  @POP-17355 @NoAplica
  Escenario: Validar subcausal Mora Actual Sobregiro externa
    Dado Oscar es un cliente con Mora Actual Sobregiro externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Sobregiro y ExternalViable

  @POP-17356 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Otro producto externa
    Dado Oscar es un cliente con Mora Actual Otro producto externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Otro producto y ExternalViable

  @POP-17359 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Codeudor externa
    Dado Oscar es un cliente con Mora Actual Codeudor externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Codeudor externa y ExternalViable

  @POP-17360 @regression @peaceAndSafe
  Escenario: Validar subcausal Documento De Identidad No Vigente externa
    Dado Andres es un cliente con Documento De Identidad No Vigente externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-17362 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales externa
    Dado Andres es un cliente con varias subcausales por externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-34176 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales externa y sin paz y salvo
    Dado Andres es un cliente con varias subcausales y sin paz y salvo externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida cliente con varias subcausales y sin paz y salvo y ExternalViable

  @POP-34175 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales externa y solicitar paz y salvo
    Dado Andres es un cliente con varias subcausales y paz y salvo externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida cliente con varias subcausales y paz y salvo y ExternalViable

  @POP-17364 @regression @peaceAndSafe
  Escenario: Validar desborde cuando llega una subcausal externa no esperada
    Dado Andres es un cliente con subcausales no esperadas externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-17989 @regression @peaceAndSafe
  Escenario: Validar subcausal Estados Derogatorios externa
    Dado Oscar es un cliente con Estados Derogatorios externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Estados Derogatorios externa y ExternalViable

  @POP-17357 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificaci�n Trimestre Anterior externa calificaci�n B o C cliente pensionado
    Dado Oscar es un cliente con Calificaci�n Trimestre Anterior calificaci�n B o C pensionado externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Calificaci�n Trimestre Anterior calificaci�n B o C y ExternalViable

  @POP-20975 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificaci�n Trimestre Anterior externa calificaci�n B o C cliente educativo
    Dado Oscar es un cliente con Calificaci�n Trimestre Anterior calificaci�n B o C educativo externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-20977 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificaci�n Trimestre Anterior externa calificaci�n B o C cliente publico
    Dado Oscar es un cliente con Calificaci�n Trimestre Anterior calificaci�n B o C publico externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-20979 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificaci�n Trimestre Anterior externa calificaci�n D o E cliente pensionado
    Dado Oscar es un cliente con Calificaci�n Trimestre Anterior calificaci�n D o E pensionado externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Calificaci�n Trimestre Anterior calificaci�n D o E y externa

  @POP-20981 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificaci�n Trimestre Anterior externa calificaci�n D o E cliente educativo
    Dado Oscar es un cliente con Calificaci�n Trimestre Anterior calificaci�n D o E educativo externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-20983 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificaci�n Trimestre Anterior externa calificaci�n D o E cliente publico
    Dado Oscar es un cliente con Calificaci�n Trimestre Anterior calificaci�n D o E publico externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-22473 @regression @peaceAndSafe
  Escenario: Validar subcausal Estado documento no valido
    Dado Oscar es un cliente con Estado documento no valido externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-22474 @regression @peaceAndSafe
  Escenario: Validar subcausal Estados Derogatorios Productos del Pasivo
    Dado Oscar es un cliente con Estados Derogatorios Productos del Pasivo externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Estados Derogatorios Productos del Pasivo y ExternalViable

  @POP-22477 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Rotativos
    Dado Oscar es un cliente con Mora Actual Rotativos externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Rotativos y ExternalViable

  @POP-22478 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Otros-Real
    Dado Oscar es un cliente con Mora Actual Otros-Real externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Otros-Real y ExternalViable

  @POP-22480 @regression @peaceAndSafe
  Escenario: Validar subcausal Calificacion Modelo de Originacion
    Dado Oscar es un cliente con Calificacion Modelo de Originacion externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida viabilidad no exitosa y externa

  @POP-34163 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Instalamentos externa
    Dado Oscar es un cliente con Mora Actual Instalamentos externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Codeudor externa y ExternalViable

  @POP-34164 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Microcr�dito externa
    Dado Oscar es un cliente con Mora Actual Microcredito externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Mora Actual Microcredito externa y ExternalViable

  @POP-22475 @regression @externalCancelAccount @peaceAndSafe
  Escenario: Validar subcausal Cuenta cancelada mal manejo externa
    Dado Oscar es un cliente con Cuenta cancelada mal manejo externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Cuenta cancelada mal manejo e ExternalViable

  @POP-22476 @regression @peaceAndSafe
  Escenario: Validar subcausal Cuenta embargada externa
    Dado Oscar es un cliente con Cuenta embargada externa
    Cuando el env�a su n�mero de c�dula
    Entonces se valida subcausal Cuenta embargada e ExternalViable
