# language: es
# encoding: iso-8859-1
@LaboralCertification
Caracter�stica: Validaci�n de mi certificado
  Yo como cliente del banco
  Quiero validar mi certificado laboral
  Para acceder a un valor mayor en mi cr�dito

  @POP-17921 @NoAplica @laboralCertificationError
  Escenario: Validar error en el certificado c�digo 101
    Dado Oscar es un CNC sector educativo con error 101 en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza codigo de error 101 en el certificado

  @POP-18249 @NoAplica @laboralCertificationError
  Escenario: Validar error en el certificado c�digo 102
    Dado Oscar es un CNC sector educativo con error 102 en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza codigo de error 102 en el certificado

  @POP-18250 @NoAplica @laboralCertificationError
  Escenario: Validar error en el certificado c�digo 103
    Dado Oscar es un CNC sector educativo con error 103 en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza codigo de error 103 en el certificado

  @POP-17917 @NoAplica @laboralCertificationError
  Escenario: Validar error en el certificado c�digo 104
    Dado Oscar es un CNC sector educativo con error 104 en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza codigo de error 104 en el certificado

  @POP-18251 @NoAplica @laboralCertificationError
  Escenario: Validar error en el certificado c�digo 105
    Dado Oscar es un CNC sector educativo con error 105 en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza codigo de error 105 en el certificado

  @POP-17919 @NoAplica @laboralCertificationError
  Escenario: Validar error en el certificado c�digo 106
    Dado Oscar es un CNC sector educativo con error 106 en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza codigo de error 106 en el certificado

  @POP-17922 @POP-18258 @NoAplica @laboralCertificationError
  Escenario: Validar cuando el certificado presenta varios errores
    Dado Oscar es un CNC sector educativo con varios errores en certificado
    Cuando el consulta el estado final de sus documentos
    Entonces se visualiza varios codigo de error en el certificado

  @POP-18248 @POP-18261 @NoAplica @laboralCertificationError
  Escenario: Validar cuando el certificado esta vencido
    Dado Oscar es un CNC sector educativo con su certificado vencido
    Cuando el consulta el estado final de sus documentos
    Entonces se informa que su certificado esta vencido

  @POP-19702 @POP-18259 @NoAplica
  Escenario:  Validar estado exitoso del certificado laboral y desprendible
    Dado Juan es un CNC sector educativo con documentos exitoso
    Cuando el consulta el estado final de sus documentos
    Entonces se valida estado exitoso de ambos documentos

  @POP-18256 @NoAplica
  Escenario:  Validar el estado del desprendible y certificado cuando no existen registros en dynamoDB
    Dado Juan es un CNC sector educativo
    Cuando el valida sus documentos
    Entonces se valida que no tiene documentos precargados




