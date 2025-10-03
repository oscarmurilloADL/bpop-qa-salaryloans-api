# language: es
# encoding: iso-8859-1
Caracter�stica: Autenticaci�n del cliente y usuarios banco
  Yo como usuario del sistema
  Quiero adicionar validaciones para autenticar a mis usuarios
  Para evitar que se genere o se acceda a informaci�n sensible

  @C434984 @regression @Auth
  Escenario: Generaci�n exitosa de OTP
    Dado Pedro es un cliente
    Cuando el solicita su OTP
    Entonces se genera su OTP correctamente

  @C455953 @regression @Auth
  Escenario: Generaci�n exitosa de OTP por llamada
    Dado Pedro es un cliente
    Cuando el solicita su OTP por llamada
    Entonces se genera su OTP telefonicamente correctamente

  @C434985 @regression @Auth
  Escenario: Validaci�n exitosa de OTP
    Dado Jose es un cliente
    Cuando el ingresa su OTP
    Entonces se valida su OTP correctamente

  @C485258 @regression @Auth
  Escenario: Validaci�n no exitosa de OTP
    Dado Jose es un cliente
    Cuando el ingresa su OTP incorrecto
    Entonces se valida su OTP incorrecto

  @C98408 @regression @Auth
  Escenario: Generaci�n exitosa de OTP de confirmaci�n
    Dado Pedro es un cliente
    Cuando el solicita su OTP de confirmaci�n de cr�dito
    Entonces se genera su OTP de confirmaci�n correctamente

  @C485259 @regression @Auth
  Escenario: Generaci�n exitosa de OTP de confirmaci�n por llamada
    Dado Pedro es un cliente
    Cuando el solicita su OTP de confirmaci�n por llamada
    Entonces se genera su OTP de confirmaci�n correctamente

  @C98409 @regression @Auth
  Escenario: Validaci�n exitosa de OTP de confirmaci�n
    Dado Jose es un cliente
    Cuando el ingresa su OTP de confirmaci�n de cr�dito
    Entonces se valida su OTP de confirmaci�n correctamente

  @C485260 @regression @Auth
  Escenario: Validaci�n no exitosa de OTP de confirmaci�n
    Dado Jose es un cliente
    Cuando el ingresa su OTP de confirmaci�n incorrecto
    Entonces se valida su OTP de confirmaci�n incorrecto