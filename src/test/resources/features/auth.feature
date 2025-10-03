# language: es
# encoding: iso-8859-1
Característica: Autenticación del cliente y usuarios banco
  Yo como usuario del sistema
  Quiero adicionar validaciones para autenticar a mis usuarios
  Para evitar que se genere o se acceda a información sensible

  @C434984 @regression @Auth
  Escenario: Generación exitosa de OTP
    Dado Pedro es un cliente
    Cuando el solicita su OTP
    Entonces se genera su OTP correctamente

  @C455953 @regression @Auth
  Escenario: Generación exitosa de OTP por llamada
    Dado Pedro es un cliente
    Cuando el solicita su OTP por llamada
    Entonces se genera su OTP telefonicamente correctamente

  @C434985 @regression @Auth
  Escenario: Validación exitosa de OTP
    Dado Jose es un cliente
    Cuando el ingresa su OTP
    Entonces se valida su OTP correctamente

  @C485258 @regression @Auth
  Escenario: Validación no exitosa de OTP
    Dado Jose es un cliente
    Cuando el ingresa su OTP incorrecto
    Entonces se valida su OTP incorrecto

  @C98408 @regression @Auth
  Escenario: Generación exitosa de OTP de confirmación
    Dado Pedro es un cliente
    Cuando el solicita su OTP de confirmación de crédito
    Entonces se genera su OTP de confirmación correctamente

  @C485259 @regression @Auth
  Escenario: Generación exitosa de OTP de confirmación por llamada
    Dado Pedro es un cliente
    Cuando el solicita su OTP de confirmación por llamada
    Entonces se genera su OTP de confirmación correctamente

  @C98409 @regression @Auth
  Escenario: Validación exitosa de OTP de confirmación
    Dado Jose es un cliente
    Cuando el ingresa su OTP de confirmación de crédito
    Entonces se valida su OTP de confirmación correctamente

  @C485260 @regression @Auth
  Escenario: Validación no exitosa de OTP de confirmación
    Dado Jose es un cliente
    Cuando el ingresa su OTP de confirmación incorrecto
    Entonces se valida su OTP de confirmación incorrecto