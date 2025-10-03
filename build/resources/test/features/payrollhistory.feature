# language: es
# encoding: iso-8859-1
@PayrollHistory
Característica: Validar el historial de libranzas
  Yo como cliente del banco
  Quiero ver mi historial de libranzas
  Para conocer el estado y valores de esta

  @C16021 @regression
  Escenario: Cliente tiene la libranza en estado valido
    Dado Cristian es un cliente con libranza en estado valido
    Cuando el consulta el estado de su libranza
    Entonces su libranza debe ser válida

  @C34237 @regression
  Escenario: Libranza no cumple condiciones misma cuota
    Dado Cristian es un cliente no cumple condiciones misma cuota
    Cuando el consulta el estado de su libranza
    Entonces su libranza no cumple condiciones misma cuota

  @C16022 @regression
  Escenario: Cliente puede ver la informacion de su libranza
    Dado Omar es un cliente
    Cuando el consulta la información de su libranza
    Entonces el puede ver su información

  @C416021 @POP-30122 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Oficina Presencial
    Dado Cristian es un cliente huellas - asesor oficina presencial
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas con extractor

  @POP-30123 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Vendor Presencial
    Dado Diego es un cliente huellas - asesor vendor presencial
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514709 @POP-30026 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Fidelización Presencial
    Dado Cristian es un cliente huellas - asesor fidelización presencial
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas con extractor

  @C514708 @POP-30122 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Oficina Telefónico
    Dado Cristian es un cliente huellas - asesor oficina telefónico
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas con extractor

  @POP-30123 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Vendor Telefónico
    Dado Diego es un cliente huellas - asesor vendor telefónico
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514710 @POP-30026 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Fidelización Telefónico
    Dado Cristian es un cliente huellas - asesor fidelización telefónico
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas con extractor

  @C416022 @regression
  Escenario: Cliente no se encuentra en base huellas - Asesor Oficina
    Dado Cristian es un cliente
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514705 @POP-30121 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Expres Presencial
    Dado Cristian es un cliente huellas - asesor expres presencial
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514701 @POP-30119 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor FVE Presencial
    Dado Cristian es un cliente huellas - asesor FVE presencial
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514703 @POP-30120 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Agendación Presencial
    Dado Cristian es un cliente huellas - asesor agendación presencial
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514706 @POP-30121 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Expres Telefónico
    Dado Cristian es un cliente huellas - asesor expres telefónico
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514702 @POP-30119 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor FVE Telefónico
    Dado Cristian es un cliente huellas - asesor FVE telefónico
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C514704 @POP-30120 @regression
  Escenario: Cliente se encuentra en base huellas - Asesor Agendación Telefónico
    Dado Cristian es un cliente huellas - asesor agendación telefónico
    Cuando el consulta el estado de su libranza
    Entonces no está en base de huellas

  @C416035 @C168857 @regression @POP-17424
  Escenario: Cliente con libranzas activa y desembolsada (sin pago por nomina)
    Dado Cristian es un cliente con libranza activa, desembolsada y sin pago por nomina
  	Cuando el consulta el estado de su libranza
  	Entonces el tipo de pago no es por nómina para su libranza activa y desembolsada

  @C416036 @C186335 @regression
  Escenario: Cliente con una libranza con desembolso negativo debe activar toda cuota
  	Dado Cristian es un cliente con libranza con desembolso negativo
  	Cuando el consulta el estado de su libranza
  	Entonces su libranza no cumple condiciones misma cuota

  @C6732 @C420105 @regression @POP-17419 @POP-17418
  Escenario: Cliente con una pagaduria no incluida en el modelo digital y una libranza en estado Para desembolso
    Dado Cristian es un cliente con pagaduria no incluida en modelo digital
    Cuando el consulta el estado de su libranza
    Entonces su libranza no puede ser tramitada por ADL

  @400258 @C168856 @C202387 @regression @POP-17425 @POP-17423
  Escenario: Cliente con libranzas en estado de desembolso, cancelado y activo
    Dado Cristian es un cliente con libranzas en estado desembolsado, cancelado y activo
    Cuando el consulta el estado de su libranza
    Entonces no se activa oferta para libranzas en estado desembolsado y cancelado

  @416037 @regression
  Escenario: Cliente con cuenta del Banco Popular en estado embargado
    Dado Cristian es un cliente con cuenta del Banco Popular embargada
    Cuando el consulta el estado de su libranza
    Entonces no se activa oferta para cliente con cuenta BP en estado embargada

  @C4325 @regression @POP-17420
  Escenario: Cliente pensionado no cumple con la politica de crecimiento y edad
    Dado Cristian es un cliente que no cumple con la politica de crecimiento
    Cuando el consulta el estado de su libranza
    Entonces no se activa oferta para cliente con máximo de edad excedida

  @C221907 @regression @POP-17421
  Escenario: Cliente educativo mayor de 68 años
    Dado Cristian es un cliente que tiene 68 años y pertenece al sector educativo
    Cuando el consulta el estado de su libranza
    Entonces no se activa oferta para cliente con máximo de edad excedida

  @C416021 @POP-30122 @regression
  Escenario: Cliente se encuentra en base huellas no extractor - Asesor Oficina Presencial
    Dado Cristian es un cliente huellas no extractor - asesor oficina presencial
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas

  @C514708 @POP-30122 @regression
  Escenario: Cliente se encuentra en base huellas no extractor - Asesor Oficina Telefónico
    Dado Cristian es un cliente huellas no extractor - asesor oficina telefónico
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas

  @C514709 @POP-30026 @regression
  Escenario: Cliente se encuentra en base huellas no extractor - Asesor Fidelización Presencial
    Dado Cristian es un cliente huellas no extractor - asesor fidelización presencial
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas

  @C514710 @POP-30026 @regression
  Escenario: Cliente se encuentra en base huellas no extractor - Asesor Fidelización Telefónico
    Dado Cristian es un cliente huellas no extractor - asesor fidelización telefónico
    Cuando el consulta el estado de su libranza
    Entonces está en base de huellas