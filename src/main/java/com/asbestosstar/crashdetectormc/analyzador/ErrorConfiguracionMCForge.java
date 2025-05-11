package com.asbestosstar.crashdetectormc.analyzador;

import com.asbestosstar.crashdetector.CDStringBuilder;
import com.asbestosstar.crashdetector.MonitorDePID;

public class ErrorConfiguracionMCForge implements Verificaciones {

 public boolean activado = false;

 @Override
 public void verificar(String contenido_de_consola, CDStringBuilder constructor) {
     // Detectar error específico de configuración corrupta
     if (contenido_de_consola.matches("(?si).*ParsingException:\\s*Not enough data available.*")) {
         constructor.append(MonitorDePID.idioma.errorConfigMCForge())
                   .append(nl_html);
         activado = true;
     }
 }

 @Override
 public Verificaciones nueva() {
     return new ErrorConfiguracionMCForge();
 }

 @Override
 public boolean activado() {
     return activado;
 }
}
