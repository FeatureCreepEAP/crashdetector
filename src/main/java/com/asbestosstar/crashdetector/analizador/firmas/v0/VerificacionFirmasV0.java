package com.asbestosstar.crashdetector.analizador.firmas.v0;

import java.util.Objects;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.firmas.FiltrodeCodice;
import com.asbestosstar.crashdetector.analizador.firmas.TipoDeFiltrodeCodice;
import com.asbestosstar.crashdetector.idioma.Arabe;
import com.asbestosstar.crashdetector.idioma.Chino;
import com.asbestosstar.crashdetector.idioma.Coreano;
import com.asbestosstar.crashdetector.idioma.Espanol;
import com.asbestosstar.crashdetector.idioma.Ingles;
import com.asbestosstar.crashdetector.idioma.Japones;
import com.asbestosstar.crashdetector.idioma.Persa;
import com.asbestosstar.crashdetector.idioma.Portuges;
import com.asbestosstar.crashdetector.idioma.Ruso;

public class VerificacionFirmasV0 implements Verificaciones {

	public String id;

	public String nombre_ar;
	public String resultado_ar;

	public String nombre_zh;
	public String resultado_zh;

	public String nombre_kp;
	public String resultado_kp;

	public String nombre_es;
	public String resultado_es;

	public String nombre_eo;
	public String resultado_eo;

	public String nombre_en;
	public String resultado_en;

	public String nombre_jp;
	public String resultado_jp;

	public String nombre_fa;
	public String resultado_fa;

	public String nombre_pt;
	public String resultado_pt;

	public String nombre_ru;
	public String resultado_ru;

	public Criticalidad criticalidad;
	public int prioridad;
	public String para_buscar;
	public FiltrodeCodice filtro;

	public boolean activado = false;

	public String enlace = "";

	public VerificacionFirmasV0(String id, String nombre_ar, String resultado_ar, String nombre_zh, String resultado_zh,
			String nombre_kp, String resultado_kp, String nombre_es, String resultado_es, String nombre_eo,
			String resultado_eo, String nombre_en, String resultado_en, String nombre_jp, String resultado_jp,
			String nombre_fa, String resultado_fa, String nombre_pt, String resultado_pt, String nombre_ru,
			String resultado_ru, Criticalidad criticalidad, int prioridad, String para_buscar, FiltrodeCodice filtro) {

		this.id = id;

		this.nombre_ar = nombre_ar;
		this.resultado_ar = resultado_ar;

		this.nombre_zh = nombre_zh;
		this.resultado_zh = resultado_zh;

		this.nombre_kp = nombre_kp;
		this.resultado_kp = resultado_kp;

		this.nombre_es = nombre_es;
		this.resultado_es = resultado_es;

		this.nombre_eo = nombre_eo;
		this.resultado_eo = resultado_eo;

		this.nombre_en = nombre_en;
		this.resultado_en = resultado_en;

		this.nombre_jp = nombre_jp;
		this.resultado_jp = resultado_jp;

		this.nombre_fa = nombre_fa;
		this.resultado_fa = resultado_fa;

		this.nombre_pt = nombre_pt;
		this.resultado_pt = resultado_pt;

		this.nombre_ru = nombre_ru;
		this.resultado_ru = resultado_ru;

		this.criticalidad = criticalidad;
		this.prioridad = prioridad;
		this.para_buscar = para_buscar;
		this.filtro = filtro;
	}

	@Override
	public void verificar(Consola consola) {
		// TODO Auto-generated method stub
		if (filtro.tipo.equals(TipoDeFiltrodeCodice.DE_TODOS)) {
			if (filtro.activar(consola.contenido_verificar, para_buscar)) {
				this.activado = true;
			}
		}

	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// TODO Auto-generated method stub
		if (filtro.tipo.equals(TipoDeFiltrodeCodice.DE_LINEA)) {
			if (filtro.activar(linea, para_buscar)) {
				this.activado = true;
				this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			}
		}

	}

	@Override
	public Verificaciones nueva() {
		// TODO Auto-generated method stub
		return new VerificacionFirmasV0(id, nombre_ar, resultado_ar, nombre_zh, resultado_zh, nombre_kp, resultado_kp,
				nombre_es, resultado_es, nombre_eo, resultado_eo, nombre_en, resultado_en, nombre_jp, resultado_jp,
				nombre_fa, resultado_fa, nombre_pt, resultado_pt, nombre_ru, resultado_ru, criticalidad, prioridad,
				para_buscar, filtro);
	}

	@Override
	public boolean activado() {
		// TODO Auto-generated method stub
		return activado;
	}

	@Override
	public float prioridad() {
		// TODO Auto-generated method stub
		return this.prioridad;
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return "<span style='color:#" + codigo_de_color() + "'>" + obtenerDesc() + "</span> " + enlace;
	}

	public String codigo_de_color() {
		if (this.criticalidad.equals(Criticalidad.ADVERTENCIA)) {
			return Config.obtenerInstancia().obtenerColorAdvertencia();
		} else {
			return Config.obtenerInstancia().obtenerColorAdvertencia();
		}
	}

	public String obtenerDesc() {
		Idioma actual = MonitorDePID.idioma;
		if (actual.getClass().equals(Arabe.class)) {
			return this.resultado_ar;
		} else if (actual.getClass().equals(Chino.class)) {
			return this.resultado_zh;
		}
		if (actual.getClass().equals(Coreano.class)) {
			return this.resultado_kp;
		}
		if (actual.getClass().equals(Espanol.class)) {
			return this.resultado_es;
		}
		if (actual.getClass().equals(Ingles.class)) {
			return this.resultado_en;
		}
		if (actual.getClass().equals(Japones.class)) {
			return this.resultado_jp;
		}
		if (actual.getClass().equals(Persa.class)) {
			return this.resultado_fa;
		}
		if (actual.getClass().equals(Portuges.class)) {
			return this.resultado_pt;
		}
		if (actual.getClass().equals(Ruso.class)) {
			return this.resultado_ru;
		} else {
			return this.resultado_es;
		}

	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return obtenerNombre();
	}

	public String obtenerNombre() {
		Idioma actual = MonitorDePID.idioma;
		if (actual.getClass().equals(Arabe.class)) {
			return this.nombre_ar;
		} else if (actual.getClass().equals(Chino.class)) {
			return this.nombre_zh;
		}
		if (actual.getClass().equals(Coreano.class)) {
			return this.nombre_kp;
		}
		if (actual.getClass().equals(Espanol.class)) {
			return this.nombre_es;
		}
		if (actual.getClass().equals(Ingles.class)) {
			return this.nombre_en;
		}
		if (actual.getClass().equals(Japones.class)) {
			return this.nombre_jp;
		}
		if (actual.getClass().equals(Persa.class)) {
			return this.nombre_fa;
		}
		if (actual.getClass().equals(Portuges.class)) {
			return this.nombre_pt;
		}
		if (actual.getClass().equals(Ruso.class)) {
			return this.nombre_ru;
		} else {
			return this.nombre_es;
		}

	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.noHaySolucionDisponible())
				.construir();// TODO
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return id;
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
				&& Objects.equals(nombre_es, v.nombre_es) && Objects.equals(resultado_es, v.resultado_es)
				&& Objects.equals(nombre_en, v.nombre_en) && Objects.equals(resultado_en, v.resultado_en)
				&& Objects.equals(nombre_ar, v.nombre_ar) && Objects.equals(resultado_ar, v.resultado_ar)
				&& Objects.equals(nombre_pt, v.nombre_pt) && Objects.equals(resultado_pt, v.resultado_pt)
				&& Objects.equals(nombre_fa, v.nombre_fa) && Objects.equals(resultado_fa, v.resultado_fa)
				&& Objects.equals(nombre_ru, v.nombre_ru) && Objects.equals(resultado_ru, v.resultado_ru)
				&& Objects.equals(nombre_zh, v.nombre_zh) && Objects.equals(resultado_zh, v.resultado_zh)
				&& Objects.equals(nombre_eo, v.nombre_eo) && Objects.equals(resultado_eo, v.resultado_eo)
				&& Objects.equals(nombre_jp, v.nombre_jp) && Objects.equals(resultado_jp, v.resultado_jp)
				&& Objects.equals(nombre_kp, v.nombre_kp) && Objects.equals(resultado_kp, v.resultado_kp);
	}

}
