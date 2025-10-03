# language: es
# encoding: iso-8859-1
@Simulation
Característica: Simulacion de mi credito toda cuota
  Yo como cliente del banco
  Quiero conocer el monto y plazo de mi credito
  Para solicitarlo y acceder a este

  @C34242 @regression
  Escenario: Monto y plazo maximo
    Dado Diego es un cliente no cumple condiciones misma cuota
    Cuando el envia la petición para monto y plazo máximo
    Entonces se valida los valores máximos del crédito

  @pentienteTestRail @C4924 @regression
  Escenario: Validación de estructura de respuesta del simulador
    Dado Carlos es un cliente toda cuota que no cumple con la estructura en el simulador
    Cuando el envia la petición al simulador
    Entonces el no puede continuar debido a que no tiene la estructura correcta

  @POP-20472 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 2
    Dado Diego es un cliente con codigo de error 2 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 2

  @POP-20473 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 105
    Dado Diego es un cliente con codigo de error 105 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 105

  @POP-20474 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 111
    Dado Diego es un cliente con codigo de error 111 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 111

  @POP-20475 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 114
    Dado Diego es un cliente con codigo de error 114 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 114

  @POP-20476 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 117
    Dado Diego es un cliente con codigo de error 117 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 117

  @POP-20477 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 122
    Dado Diego es un cliente con codigo de error 122 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 122

  @POP-20478 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 125
    Dado Diego es un cliente con codigo de error 125 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 125

  @POP-20479 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 141
    Dado Diego es un cliente con codigo de error 141 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 141

  @POP-20480 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 145
    Dado Diego es un cliente con codigo de error 145 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 145

  @POP-20481 @regression
  Escenario: Validación respuesta del primer llamado simulador con codigo de error 147
    Dado Diego es un cliente con codigo de error 147 en el primer llamado al simulador
    Cuando el envia la petición por primera vez al simulador
    Entonces se valida el codigo de error del simulador 147


