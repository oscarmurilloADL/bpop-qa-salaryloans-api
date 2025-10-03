# language: es
# encoding: iso-8859-1
@SimValidation
Caracter�stica: Validacion de SIM
  Yo como cliente de la aplicacion
  Quiero validar el numero de mi telefono celular
  Para verificar que pueda acceder a una libranza

  @C127946 @C127946 @POP-16908 @regression
  Escenario: Validaci�n de Sim exitosa
    Dado Andres es un cliente
    Cuando env�a su n�mero de tel�fono para ser verificado
    Entonces se valida sim correctamente

  @C435108 @POP-16910 @regression
  Escenario: Validaci�n de Sim no exitosa
    Dado Cristina es un cliente misma cuota con validaci�n Sim no exitosa
    Cuando env�a su n�mero de tel�fono para ser verificado
    Entonces se valida sim no exitosa

  @AutenticacionFuerteClienteNuevoConocido @POP-16911 @regression
  Escenario: Validar solicitud de autenticaci�n fuerte en cliente nuevo conocido con �nica cuenta
    Dado Julieta es un cliente cumple con la validaci�n CRM y SIM exitosa y tiene una �nica cuenta para desembolso
    Cuando ella env�a el OTP de manera correcta
    Entonces ella puede validar que se solicita autenticaci�n fuerte - m�todo de validaci�n DECRIM o Certihuella

  @AutenticacionFuerteClientePermanenciaMenor90Dias @POP-16909 @regression
  Escenario: Validaci�n SIM No exitosa por fecha inferior a 90 d�as
    Dado Matilda es un cliente que tiene una fecha inferior a 90 dias en CRM y el n�mero de tel�fono tiene validaci�n SIM exitosa
    Cuando ella env�a el OTP de manera correcta
    Entonces ella puede validar que se solicita autenticaci�n fuerte - m�todo de validaci�n DECRIM o Certihuella

  @POP-18199 @regression
  Escenario: Validaci�n SIM No exitosa por cuenta Flexcube nativa
    Dado Matilda es un cliente con cuenta Flexcube nativa
    Cuando ella consulta sus cuentas
    Entonces ella s� tiene cuentas Flexcube que inician con 500
    Cuando ella env�a el OTP de manera correcta
    Entonces ella puede validar que se solicita autenticaci�n fuerte - m�todo de validaci�n DECRIM o Certihuella

  @POP-18200 @regression
  Escenario: Validaci�n SIM exitosa por tener �nicamente cuentas Flexcube migradas
    Dado Matilda es un cliente �nicamente con cuentas Flexcube migradas
    Cuando ella consulta sus cuentas
    Entonces ella no tiene cuentas Flexcube que inician con 500
    Cuando ella env�a el OTP de manera correcta
    Entonces se valida sim correctamente