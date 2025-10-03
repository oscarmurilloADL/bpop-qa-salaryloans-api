# language: es
@Renewal
@NovarLibranza
Característica: Novar una libranza
  Yo como cliente del banco
  Quiero solicitar la novacion de mi credito
  Para tener un valor disponible mayor

  @C34235 @POP-19748 @renewal @C502131 @regression @successfulRenewal
  Escenario: Novacion exitosa de libranza misma cuota
    Dado Cristian es un cliente
    Cuando el solicita una novación
    Entonces la pre aprobación es exitosa

  @C98407 @renewal @regression @successfulRenewal
  Escenario: Novacion exitosa de libranza toda cuota
    Dado Jorge es un cliente no cumple condiciones misma cuota
    Cuando el solicita una novación por toda cuota
    Entonces la pre aprobación es exitosa

  @C275426 @renewal @regression @successfulRenewal
  Escenario: Novacion exitosa de libranza misma cuota con fidelización
    Dado Pedro es un cliente
    Cuando el solicita una novación misma cuota con fidelización
    Entonces la pre aprobación es exitosa

  @C300832 @renewal @regression @successfulRenewal
  Escenario: Novacion exitosa de libranza toda cuota con fidelización
    Dado Pedro es un cliente no cumple condiciones misma cuota
    Cuando el solicita una novación toda cuota con fidelización
    Entonces la pre aprobación es exitosa

  @C434632 @renewal @regression @successfulRenewal @certihuella
  Escenario: Novacion exitosa de libranza misma cuota con Certihuella
    Dado Cristina es un cliente misma cuota con certihuella
    Cuando ella solicita una novación
    Entonces la pre aprobación es exitosa

  @C435476 @renewal @regression @successfulRenewal @certihuella
  Escenario: Novacion exitosa de libranza toda cuota con Certihuella
    Dado Cristina es un cliente toda cuota con certihuella
    Cuando ella solicita una novación por toda cuota
    Entonces la pre aprobación es exitosa

  @C528100 @POP-30066 @renewal @regression @failedRenewall
  Escenario: Novacion fallida de libranza por ser NO apto para recálculo ordinaria y SIN disponible BRMS
    Dado Carmen es un cliente NO apto para recalculo ordinaria y SIN disponible BRMS
    Cuando ella intenta solicitar una novación toda cuota
    Entonces la pre aprobación es fallida por política de crecimiento