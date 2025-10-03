# language: es
# encoding: iso-8859-1
@SimValidation
Característica: Validacion de SIM
  Yo como cliente de la aplicacion
  Quiero validar el numero de mi telefono celular
  Para verificar que pueda acceder a una libranza

  @C127946 @C127946 @POP-16908 @regression
  Escenario: Validación de Sim exitosa
    Dado Andres es un cliente
    Cuando envía su número de teléfono para ser verificado
    Entonces se valida sim correctamente

  @C435108 @POP-16910 @regression
  Escenario: Validación de Sim no exitosa
    Dado Cristina es un cliente misma cuota con validación Sim no exitosa
    Cuando envía su número de teléfono para ser verificado
    Entonces se valida sim no exitosa

  @AutenticacionFuerteClienteNuevoConocido @POP-16911 @regression
  Escenario: Validar solicitud de autenticación fuerte en cliente nuevo conocido con única cuenta
    Dado Julieta es un cliente cumple con la validación CRM y SIM exitosa y tiene una única cuenta para desembolso
    Cuando ella envía el OTP de manera correcta
    Entonces ella puede validar que se solicita autenticación fuerte - método de validación DECRIM o Certihuella

  @AutenticacionFuerteClientePermanenciaMenor90Dias @POP-16909 @regression
  Escenario: Validación SIM No exitosa por fecha inferior a 90 días
    Dado Matilda es un cliente que tiene una fecha inferior a 90 dias en CRM y el número de teléfono tiene validación SIM exitosa
    Cuando ella envía el OTP de manera correcta
    Entonces ella puede validar que se solicita autenticación fuerte - método de validación DECRIM o Certihuella

  @POP-18199 @regression
  Escenario: Validación SIM No exitosa por cuenta Flexcube nativa
    Dado Matilda es un cliente con cuenta Flexcube nativa
    Cuando ella consulta sus cuentas
    Entonces ella sí tiene cuentas Flexcube que inician con 500
    Cuando ella envía el OTP de manera correcta
    Entonces ella puede validar que se solicita autenticación fuerte - método de validación DECRIM o Certihuella

  @POP-18200 @regression
  Escenario: Validación SIM exitosa por tener únicamente cuentas Flexcube migradas
    Dado Matilda es un cliente únicamente con cuentas Flexcube migradas
    Cuando ella consulta sus cuentas
    Entonces ella no tiene cuentas Flexcube que inician con 500
    Cuando ella envía el OTP de manera correcta
    Entonces se valida sim correctamente