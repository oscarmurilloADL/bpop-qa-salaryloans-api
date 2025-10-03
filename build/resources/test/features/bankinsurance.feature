# language: es
# encoding: iso-8859-1
@Bankinsurance
Característica: Mostrar ofertas para banca seguros
  Yo como cliente del banco
  Quiero conocer mis ofertas de banca seguros
  Para escoger si se adecua a mis necesidades

  @C34240 @C476879 @regression @POP-17465 @POP-17462 @POP-17460 @POP-17459
  Escenario: Oferta exitosa de banca seguros educativo
    Dado Cesar es un cliente
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran correctamente

  @C412871 @C476979 @C477111 @regression
  Escenario: Oferta exitosa de banca seguros Pensionado hasta 69 años con prima 12 meses
    Dado Cesar es un cliente pensionado hasta 69 años con bancaseguro de prima 12 meses
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran los valores correctamente sector Pensionado hasta 69 años con prima 12 meses

  @C544244 @regression @POP-17468 @POP-17467
  Escenario: Oferta exitosa de banca seguros Pensionado hasta 69 años con prima 48 meses
    Dado Mauro es un cliente pensionado hasta 69 años con bancaseguro de prima 48 meses
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran los valores correctamente sector Pensionado hasta 69 años con prima 48 meses

  @C412946 @C477113 @C477114 @C477548 @regression
  Escenario: Oferta exitosa de banca seguros Pensionado mayor a 70 años con prima 12 meses
    Dado Cesar es un cliente pensionado mayor a 70 años con bancaseguro de prima 12 meses
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran los valores correctamente sector Pensionado mayor 70 años con prima 12 meses

  @C412947 @C477116 @C477547 @regression @POP-17470 @POP-17469
  Escenario: Oferta exitosa de banca seguros Pensionado mayor a 70 años con prima 48 meses
    Dado Cesar es un cliente pensionado mayor a 70 años con bancaseguro de prima 48 meses
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran los valores correctamente sector Pensionado mayor 70 años con prima 48 meses

  @C490440 @POP-30149 @regression
  Escenario: Oferta de banca seguros Pensionado mayor o igual a 81 años CNC
    Dado Diego es un cliente pensionado mayor o igual a 81 años en flujo CNC
    Cuando el selecciona las ofertas de banca seguros en CNC
    Entonces no se muestra oferta en CNC

  @C490438 @POP-30148 @regression
  Escenario: Oferta de banca seguros Pensionado 70 a 81 años CNC
    Dado Diego es un cliente pensionado con edad entre 70 y 81 años en flujo CNC
    Cuando el selecciona las ofertas de banca seguros en CNC
    Entonces se muestran los valores correctamente sector Pensionado 70 a 81 años CNC

  @C490437 @POP-30147 @regression
  Escenario: Oferta de banca seguros Pensionado menor de 69 años CNC
    Dado Diego es un cliente pensionado con edad menor a 69 años en flujo CNC
    Cuando el selecciona las ofertas de banca seguros en CNC
    Entonces se muestran los valores correctamente sector Pensionado menor a 69 años CNC

  @C544250 @regression @POP-17458
  Escenario: Sin oferta Bancaseguros por desembolso inferior al valor mínimo de póliza con prima de 12 meses
    Dado Juan es un cliente sin oferta de bancaseguros
    Cuando el selecciona las ofertas de banca seguros
    Entonces se informa que no hay oferta para bancaseguros

  @C544237 @regression @POP-17464 @POP-17461
  Escenario: Oferta exitosa de banca seguros sector educativo 18-40 años con prima 12 meses
    Dado Mauro es un cliente sector del educativo 18-40 años con bancaseguro de prima 12 meses
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran los valores correctamente sector educativo 18-40 años con prima 12 meses

  @C544239 @C544240 @C544242 @regression @POP-17466 @POP-17463
  Escenario: Oferta exitosa de banca seguros sector educativo 56-65 años con prima 48 meses
    Dado Mauro es un cliente sector del educativo 56-65 años con bancaseguro de prima 48 meses
    Cuando el selecciona las ofertas de banca seguros
    Entonces se muestran los valores correctamente sector educativo 56-65 años con prima 48 meses

  @POP-21966 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121005
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121005
    Entonces se muestran los valores correspondientes

  @POP-21968 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121006
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121006
    Entonces se muestran los valores correspondientes

  @POP-21969 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121007
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121007
    Entonces se muestran los valores correspondientes

  @POP-21970 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121008
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121008
    Entonces se muestran los valores correspondientes

  @POP-21971 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121009
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121009
    Entonces se muestran los valores correspondientes

  @POP-21972 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121010
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121010
    Entonces se muestran los valores correspondientes

  @POP-21973 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121011
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121011
    Entonces se muestran los valores correspondientes

  @POP-21974 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121012
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121012
    Entonces se muestran los valores correspondientes

  @POP-21976 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 120905
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 120905
    Entonces se muestran los valores correspondientes

  @POP-21977 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 120906
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 120906
    Entonces se muestran los valores correspondientes

  @POP-21978 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121505
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121505
    Entonces se muestran los valores correspondientes

  @POP-21980 @regression @termConditions
  Escenario: Consultar terminos y condiciones banca seguros 121506
    Dado los terminos y condiciones para los planes bancaseguros
    Cuando se consulta el plan 121506
    Entonces se muestran los valores correspondientes