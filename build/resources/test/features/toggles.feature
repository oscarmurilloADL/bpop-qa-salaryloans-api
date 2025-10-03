# language: es
# encoding: iso-8859-1
Característica: Consulta de los estados de los toggles de libranzas
  Yo como usuario del sistema
  Quiero obtener los toggles que se tienen en la aplicación de libranzas
  Para prender o apagar funcionalidades dentro de la aplicación

  @C478611 @regression
  Escenario: Consulta estado de los toggles
    Dado Samuel es un cliente
    Cuando el consulta la lista de los toggles
    Entonces puede ver la información de las funcionalidades de la aplicación

  @castlemockCRM
  Escenario: Crear mocks CRM
    Cuando se crean los mocks REST de CRM

  @castlemockSIM
  Escenario: Crear mocks simValidation
    Cuando se crean los mocks REST de SIM

  @castlemockActivas
  Escenario: Crear mocks obligaciones activas
    Cuando se crean los mocks SOAP de ACTIVAS

  @castlemockSimulador
  Escenario: Crear mocks simulador
    Cuando se crean los mocks REST de SIMULATION