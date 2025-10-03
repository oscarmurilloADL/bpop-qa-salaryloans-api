# language: es
# encoding: iso-8859-1
@CashierOffice
Característica: Mostrar pagadurías disponibles para un cliente en flujo CNC
  Yo como cliente del banco
  Quiero conocer las pagadurias disponibles para solicitar libranza en flujo CNC
  Para escoger la que se adecua a mis necesidades

  @C487558 @POP-30128 @cashierOffice @regression
  Escenario: Listado completo para cliente sin pagadurías en proceso
    Dado Diego es un cliente sin ventas en proceso
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa

  @C501315 @C502134 @POP-30124 @cashierOffice @regression
  Escenario: Listado completo para cliente con pagaduría cancelada únicamente
    Dado Diego es un cliente con una pagaduria cancelada unicamente
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa

  @C487555 @POP-30126 @cashierOffice @regression
  Escenario: Listado sin pagaduría pendiente de captura
    Dado Diego es un cliente con venta CNC en pendiente de captura
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa excepto FIDEICOMISO PANFLOTA PENSIONADOS

  @C487555 @POP-30126 @cashierOffice @regression
  Escenario: Listado sin pagaduría pendiente de aprobacion
    Dado Diego es un cliente con venta CNC en pendiente de aprobacion
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa excepto FONDO DE PREVISION SOCIAL CONGRESO DE LA REPUBLICA

  @C487555 @POP-30126 @cashierOffice @regression
  Escenario: Listado sin pagaduría pendiente de perfeccionamiento
    Dado Diego es un cliente con venta CNC en pendiente de perfeccionamiento
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa excepto COLPENSIONES

  @C487557 @POP-30127 @cashierOffice @regression
  Escenario: Listado sin pagaduría para desembolso
    Dado Diego es un cliente con pagaduría para desembolso
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa

  @C502135 @POP-30125 @cashierOffice @regression
  Escenario: Listado sin pagaduría activa
    Dado Diego es un cliente con pagaduría activa
    Cuando el despliega la lista de pagadurias disponibles para el sector pensionados
    Entonces se muestra la lista completa excepto FIDUPREVISORA FONDO PENSIONES MAGISTERIO

  @ExcelCashierOffices @regression
  Escenario: A-155-00902 ANEXO DOCUMENTOS REQUERIDOS POR PAGADURIA Y TIPO DOCUMENTAL
    Dado el anexo de documentos requeridos por pagaduria y tipo documental
    Cuando se comparan los datos contra libranzas
    Entonces se valida la consistencia de los datos en mariaDB