# language: es

  Característica: Validar transacciones en administrador

    **Como** Usuario administrador
    **Quiero** gestionar la administración SalaryLoans
    **Para** mantener actualizado el sistema

  @ConsultarUltimaVenta @Parameters @POP-17013 @renewal
  Escenario: Consultar última venta realizada
  Dado que Diana tiene rol de administrador y quiere consultar la última venta realizada
    Cuando ella consulta las ultimas ventas
    Entonces ella ve la información correspondiente a la última venta