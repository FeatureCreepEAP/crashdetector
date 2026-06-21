package com.asbestosstar.crashdetector.analizador.firmas.v0;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.firmas.FiltrodeCodice;
import com.asbestosstar.crashdetector.analizador.firmas.TipoDeFiltrodeCodice;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class VerificacionFirmasV0 implements Verificaciones {

	public String id;
	public Criticalidad criticalidad;
	public int prioridad;
	public String para_buscar;
	public FiltrodeCodice filtro;

	/**
	 * Traducciones dinámicas por código de idioma.
	 *
	 * Ejemplos: es, en, ar, pt, fa, ru, zh, eo, ja, ko, uk...
	 *
	 * También soporta leer códigos heredados como: jp -> ja kp -> ko
	 */
	public final Map<String, String> nombres_por_codigo = new LinkedHashMap<String, String>();
	public final Map<String, String> resultados_por_codigo = new LinkedHashMap<String, String>();

	public boolean activado = false;
	public String enlace = "";

	public VerificacionFirmasV0(String id, Map<String, String> nombres, Map<String, String> resultados,
			Criticalidad criticalidad, int prioridad, String para_buscar, FiltrodeCodice filtro) {

		this.id = nz(id);
		this.criticalidad = criticalidad;
		this.prioridad = prioridad;
		this.para_buscar = nz(para_buscar);
		this.filtro = filtro;

		if (nombres != null) {
			for (Map.Entry<String, String> e : nombres.entrySet()) {
				String codigo = normalizarCodigoIdioma(e.getKey());
				String valor = nz(e.getValue());
				if (codigo != null && !valor.trim().isEmpty()) {
					nombres_por_codigo.put(codigo, valor);
				}
			}
		}

		if (resultados != null) {
			for (Map.Entry<String, String> e : resultados.entrySet()) {
				String codigo = normalizarCodigoIdioma(e.getKey());
				String valor = nz(e.getValue());
				if (codigo != null && !valor.trim().isEmpty()) {
					resultados_por_codigo.put(codigo, valor);
				}
			}
		}
	}

	public static String normalizarCodigoIdioma(String codigo) {
		if (codigo == null) {
			return null;
		}

		String c = codigo.trim().toLowerCase();
		if (c.isEmpty()) {
			return null;
		}

		// Compatibilidad histórica
		if ("kp".equals(c)) {
			return "ko";
		}
		if ("jp".equals(c)) {
			return "ja";
		}

		return c;
	}

	@Override
	public String[] patronesRapidos() {
		if (filtro == null || para_buscar == null || para_buscar.isEmpty()) {
			return new String[0];
		}

		// Aho-Corasick solo puede acelerar búsquedas literales.
		// Los filtros regex siguen usando la ruta legacy.
		if (filtro == FiltrodeCodice.CONTAINE_TODOS || filtro == FiltrodeCodice.CONTAINE_LINEA) {
			return new String[] { para_buscar };
		}

		return new String[0];
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (activado || evento == null || evento.linea == null || filtro == null) {
			return;
		}

		if (filtro == FiltrodeCodice.CONTAINE_LINEA) {
			if (filtro.activar(evento.linea, para_buscar)) {
				this.activado = true;
				this.enlace = evento.consola.agregarErrorALectador(evento.numeroDeLinea, this);
			}
			return;
		}

		if (filtro == FiltrodeCodice.CONTAINE_TODOS) {
			// Si el autómata encontró el patrón literal, ya basta para activar
			// la firma global.
			this.activado = true;
		}
	}

	@Override
	public boolean verificar(Consola cons) {
		// Los contains_linea son manejados por verificarCoincidencia().
		// Solo regex_linea necesita seguir escaneando línea por línea.
		if (filtro != null && filtro == FiltrodeCodice.REGEX_LINEA) {
			return true;
		}
		return false;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null || filtro == null) {
			return;
		}

		// Compatibilidad legacy. En modo rápido, contains_linea normalmente se activa
		// desde verificarCoincidencia(); regex_linea entra por aquí.
		if (filtro.tipo.equals(TipoDeFiltrodeCodice.DE_LINEA)) {
			if (filtro.activar(linea, para_buscar)) {
				this.activado = true;
				this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			}
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new VerificacionFirmasV0(id, new LinkedHashMap<String, String>(nombres_por_codigo),
				new LinkedHashMap<String, String>(resultados_por_codigo), criticalidad, prioridad, para_buscar, filtro);
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return prioridad;
	}

	@Override
	public String mensaje() {
		return "<span style='color:#" + codigo_de_color() + "'>" + obtenerDesc() + "</span> " + enlace;
	}

	public String codigo_de_color() {
		if (Criticalidad.ADVERTENCIA.equals(this.criticalidad)) {
			return Config.obtenerInstancia().obtenerColorAdvertencia();
		}
		return Config.obtenerInstancia().obtenerColorAdvertencia();
	}

	public String obtenerDesc() {
		return resolverTexto(resultados_por_codigo);
	}

	@Override
	public String nombre() {
		return obtenerNombre();
	}

	public String obtenerNombre() {
		return resolverTexto(nombres_por_codigo);
	}

	private String resolverTexto(Map<String, String> mapa) {
		String idiomaActual = normalizarCodigoIdioma(MonitorDePID.idioma.codigo());
		String idiomaRespaldo = normalizarCodigoIdioma(Idioma.idioma_respaldo.obtener());

		String valor = obtenerNoVacio(mapa, idiomaActual);
		if (valor != null) {
			return valor;
		}

		valor = obtenerNoVacio(mapa, idiomaRespaldo);
		if (valor != null) {
			return valor;
		}

		valor = obtenerNoVacio(mapa, "es");
		if (valor != null) {
			return valor;
		}

		for (String v : mapa.values()) {
			if (v != null && !v.trim().isEmpty()) {
				return v;
			}
		}

		return "";
	}

	private String obtenerNoVacio(Map<String, String> mapa, String codigo) {
		if (mapa == null || codigo == null || codigo.isEmpty()) {
			return null;
		}

		String valor = mapa.get(codigo);
		if (valor != null && !valor.trim().isEmpty()) {
			return valor;
		}

		// Compatibilidad inversa por si el mapa cargó códigos viejos
		if ("ko".equals(codigo)) {
			valor = mapa.get("kp");
			if (valor != null && !valor.trim().isEmpty()) {
				return valor;
			}
		}
		if ("ja".equals(codigo)) {
			valor = mapa.get("jp");
			if (valor != null && !valor.trim().isEmpty()) {
				return valor;
			}
		}

		return null;
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return id;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	private static String nz(String s) {
		return s == null ? "" : s;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		VerificacionFirmasV0 v = (VerificacionFirmasV0) o;
		return prioridad == v.prioridad && Objects.equals(id, v.id) && Objects.equals(para_buscar, v.para_buscar)
				&& Objects.equals(filtro, v.filtro) && criticalidad == v.criticalidad
				&& Objects.equals(nombres_por_codigo, v.nombres_por_codigo)
				&& Objects.equals(resultados_por_codigo, v.resultados_por_codigo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombres_por_codigo, resultados_por_codigo, criticalidad, prioridad, para_buscar,
				filtro);
	}

	@Override
	public String toString() {
		String nombre = obtenerNombre();
		return (nombre == null || nombre.trim().isEmpty()) ? id : nombre;
	}

}