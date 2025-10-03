# language: es
# encoding: iso-8859-1
Característica: Validar seguro de vida deudores
  Yo como cliente del banco
  Quiero seleccionar si padezco alguna enfermedad
  Para habilitar el seguro de vida

  @C473898 @NoAplica
  Escenario: Validación de enfermedad en seguro de vida deudores
    Dado Cristian es un cliente
    Cuando el selecciona enfermedad ninguna
    Entonces se valida la información del seguro exitosamente
