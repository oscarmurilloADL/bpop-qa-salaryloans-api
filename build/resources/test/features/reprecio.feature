# language: es
# encoding: iso-8859-1
@Reprecio
Característica: Flujo reprecio
  Yo como cliente del banco
  Quiero habilitar el canal de reprecio
  Para realizar ventas por la aplicacion digital

  @POP-26851 @regression
  Escenario: Cliente en BR, pero no pasa BRMS (Política de crecimiento)
    Dado John es un cliente en BR pero no pasa BRMS
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta de sus datos por reprecio
    Y el sigue flujo corto de reprecio
    Entonces se valida la pagaduria rechazada por politica de crecimiento

  @POP-26852 @POP-30086 @regression
  Escenario: Validar BR con Pagaduria no habilitada por el canal - validación por NIT
    Dado John es un cliente en BR con pagaduria no habilitada por el canal
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta de sus datos por reprecio
    Entonces se valida que el cliente no es apto para flujo reprecio

  @POP-30084 @regression
  Escenario: Validar BR con obligación no activa
    Dado John es un cliente en BR con obligación inactiva
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta de sus datos por reprecio
    Entonces se valida que el cliente no es apto para flujo reprecio

  @POP-30083 @regression
  Escenario: Validar cliente que no está en BR
    Dado John es un cliente que no está en BR
    Cuando el es consultado en base reprecio revisando sus 0 registros
    Y el hace la consulta de sus datos por reprecio
    Entonces se valida que el cliente no es apto para flujo reprecio

  @POP-30081 @regression
  Escenario: Validar BR con cuenta inactiva
    Dado John es un cliente en BR con cuenta inactiva
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta de sus datos por reprecio
    Entonces se valida que el cliente no es apto para flujo reprecio

  @POP-30080 @regression
  Escenario: Validar BR con obligación que no está sus obligaciones
    Dado John es un cliente en BR con obligación que no está sus obligaciones
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta de sus datos por reprecio
    Entonces se valida que el cliente no es apto para flujo reprecio

  @POP-30085 @regression
  Escenario: Validar BR con cliente reportado
    Dado John es un cliente en BR reportado en AMLRiskV2
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta del cliente con problemas
    Entonces se valida que el cliente está reportado

  @POP-30079 @regression
  Escenario: Validar BR con cliente que no existe en CRM o MDM
    Dado John es un cliente en BR que no existe en CRM o MDM
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Y el hace la consulta del cliente con problemas
    Entonces se valida que el cliente no existe en CRM o MDM

  @POP-26854 @regression
  Escenario: Validar Cliente con una pagaduría no habilitada para el canal y una válida para flujo reprecio
    Dado John es un Cliente con una pagaduría no habilitada y habilitara para el canal
    Cuando el es consultado en base reprecio revisando sus 2 registros
    Y el hace la consulta de sus datos por reprecio
    Y el sigue flujo corto de reprecio
    Entonces se valida la pagaduria habilitada por el canal

  @POP-26508 @regression
  Escenario: Validar BR con cuenta de ahorros Flexcube en estado derogatorio (Embargada)
    Dado John es un cliente en BR con cuenta de ahorros Flexcube en estado derogatorio
    Cuando el es consultado en base reprecio revisando sus 1 registros
    Entonces se valida que el cliente tiene cuenta en estado derogatorio