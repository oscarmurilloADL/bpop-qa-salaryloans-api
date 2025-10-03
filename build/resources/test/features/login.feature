# language: es
# encoding: iso-8859-1
@Login
Caracter�stica: Validar pagina de inicio
  Yo como asesor del banco popular
  Quiero validar mis datos
  Para conocer si soy un asesor valido para ingresar a la solucion

  @C16015 @regression
  Escenario: Asesor valido puede ingresar a la aplicacion
    Dado Pedro es un asesor valido
    Cuando el env�a la peticion para entrar a la aplicaci�n
    Entonces el debe ser verificado

  @C16016 @regression
  Escenario: Asesor que no existe no puede ingresar a la aplicacion
    Dado Pedro es un asesor no v�lido
    Cuando el env�a la peticion para entrar a la aplicaci�n
    Entonces el no puede ingresar a la aplicaci�n

  @C277877 @regression @POP-17367
  Escenario: Asesor inactivo no puede ingresar a la aplicacion
    Dado Pedro es un asesor inactivo
    Cuando el env�a la peticion para entrar a la aplicaci�n
    Entonces el no puede ingresar a la aplicaci�n