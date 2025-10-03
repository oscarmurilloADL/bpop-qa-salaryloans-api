# language: es
# encoding: iso-8859-1
@internalViability
Característica: Validacion de viabilidad interna
  Yo como cliente de la aplicacion
  Quiero validar el numero de mi cédula
  Para verificar mi viabilidad interna

  @C300768 @regression @peaceAndSafe
  Escenario: Validar de Viabilidad interna exitosa
    Dado Andres es un cliente
    Cuando el envía su número de cédula
    Entonces se valida viabilidad exitosa e interna

  @C436885 @C610830 @regression @peaceAndSafe
  Escenario: Validar de viabilidad interna no exitosa
    Dado Andres es un cliente sin viabilidad interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @C610814 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Histórica 30 interna
    Dado Oscar es un cliente con Mora Histórica 30 interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Histórica 30 e InternalViable

  @C610815 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Histórica 60 interna
    Dado Oscar es un cliente con Mora Histórica 60 interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Histórica 60 e InternalViable

  @C610818 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Libranza interna
    Dado Oscar es un cliente con Mora Actual Libranza interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Libranza e InternalViable

  @C610819 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual TDC interna
    Dado Oscar es un cliente con Mora Actual TDC interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual TDC e InternalViable

  @C610820 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Libre Inversión interna
    Dado Oscar es un cliente con Mora Actual Libre Inversión interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Libre Inversión e InternalViable

  @C610821 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Cupo Múltiple interna
    Dado Oscar es un cliente con Mora Actual Cupo Múltiple interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Cupo Múltiple e InternalViable

  @C610822 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Microcrédito interna
    Dado Oscar es un cliente con Mora Actual Microcrédito interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Microcrédito e InternalViable

  @C610823 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Comercial interna
    Dado Oscar es un cliente con Mora Actual Comercial interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Comercial e InternalViable

  @C610824 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Vivienda interna
    Dado Oscar es un cliente con Mora Actual Vivienda interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Vivienda e InternalViable

  @C610825 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Sobregiro interna
    Dado Oscar es un cliente con Mora Actual Sobregiro interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Sobregiro e InternalViable

  @C610826 @regression @peaceAndSafe
  Escenario: Validar subcausal Mora Actual Otro producto interna
    Dado Oscar es un cliente con Mora Actual Otro producto interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Mora Actual Otro producto e InternalViable

  @C610828 @regression @peaceAndSafe
  Escenario: Validar subcausal Huellas de Consulta interna
    Dado Oscar es un cliente con Huellas de Consulta interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Huellas de Consulta interna e InternalViable

  @POP-17343 @regression @peaceAndSafe
  Escenario: Validar subcausal Estados Derogatorios interna
    Dado Oscar es un cliente con Estados Derogatorios interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Estados Derogatorios interna e InternalViable

  @POP-17361 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales interna
    Dado Andres es un cliente con varias subcausales por interna
    Cuando el envía su número de cédula
    Entonces se valida lista de subcausales e InternalViable

  @POP-34177 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales interna y desborde
    Dado Andres es un cliente con lista de subcausales con desborde interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-34178 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales no esperadas interna
    Dado Andres es un cliente con lista de subcausales no esperadas interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-34179 @regression @peaceAndSafe
  Escenario: Validar lista de subcausales interna y no pedir paz y salvo
    Dado Andres es un cliente con varias subcausales y sin paz y salvo interna
    Cuando el envía su número de cédula
    Entonces se valida cliente con varias subcausales y sin paz y salvo y InternalViable

  @POP-17363 @regression @peaceAndSafe @PoliticaCredito
  Escenario: Validar desborde cuando llega subcausal interna politica de credito
    Dado Andres es un cliente con Politica de credito interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-17340 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificación Trimestre Anterior interna calificación B o C cliente pensionado
    Dado Oscar es un cliente con Calificación Trimestre Anterior calificación B o C pensionado interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Calificación Trimestre Anterior calificación B o C y InternalViable

  @POP-20976 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificación Trimestre Anterior interna calificación B o C cliente educativo
    Dado Oscar es un cliente con Calificación Trimestre Anterior calificación B o C educativo interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-20978 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificación Trimestre Anterior interna calificación B o C cliente publico
    Dado Oscar es un cliente con Calificación Trimestre Anterior calificación B o C publico interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-20980 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificación Trimestre Anterior interna calificación D o E cliente pensionado
    Dado Oscar es un cliente con Calificación Trimestre Anterior calificación D o E pensionado interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Calificación Trimestre Anterior calificación D o E e interna

  @POP-20982 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificación Trimestre Anterior interna calificación D o E cliente educativo
    Dado Oscar es un cliente con Calificación Trimestre Anterior calificación D o E educativo interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-20984 @regression @peaceAndSafe @quarterGrade
  Escenario: Validar subcausal Calificación Trimestre Anterior interna calificación D o E cliente publico
    Dado Oscar es un cliente con Calificación Trimestre Anterior calificación D o E publico interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna

  @POP-22475 @regression @internalCancelAccount @peaceAndSafe
  Escenario: Validar subcausal Cuenta cancelada mal manejo interna
    Dado Oscar es un cliente con Cuenta cancelada mal manejo interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Cuenta cancelada mal manejo e InternalViable

  @POP-22476 @regression @peaceAndSafe @SeizedInternalAccount
  Escenario: Validar subcausal Cuenta embargada interna
    Dado Oscar es un cliente con Cuenta embargada interna
    Cuando el envía su número de cédula
    Entonces se valida subcausal Cuenta embargada e InternalViable

  @POP-22479 @regression @peaceAndSafe
  Escenario: Validar subcausal Calificacion Modelo Comportamiento interna
    Dado Oscar es un cliente con Calificacion Modelo Comportamiento interna
    Cuando el envía su número de cédula
    Entonces se valida viabilidad no exitosa e interna