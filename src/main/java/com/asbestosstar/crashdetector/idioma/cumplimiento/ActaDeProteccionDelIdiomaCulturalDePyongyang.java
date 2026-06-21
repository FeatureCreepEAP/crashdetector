package com.asbestosstar.crashdetector.idioma.cumplimiento;

import java.util.Arrays;
import java.util.List;

import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.aplic.ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn;

/**
 * Implementación del cumplimiento del Acta de Protección del Idioma Cultural de
 * Pyongyang. Proporciona funcionalidad para verificar que un texto en coreano
 * no contenga jerga del sur, conforme a la Ley de Protección del Idioma
 * Cultural de Pyongyang.
 */
public class ActaDeProteccionDelIdiomaCulturalDePyongyang {

	/**
	 * Lista de términos prohibidos según la jerga sureña (República de Corea).
	 * Incluye jerga vulgar, modismos juveniles, abreviaturas de internet, y
	 * vocabulario influenciado por préstamos del inglés o culturas extranjeras, no
	 * utilizados en la RPDC. Prohibidos bajo el Artículo 3, Sección 2 del Acta de
	 * Protección del Idioma Cultural de Pyongyang.
	 */
	private static final List<String> TERMINOS_JERGA_SUR = Arrays.asList(
			// Expresiones emocionales/modales sureñas
			"헐", "대박", "짱", "완전", "진짜", "걍", "걍", "걍", "걍", "걍", "ㄹㅇ", "ㅈㄴ", "ㅅㅂ", "ㅂㄷ", "ㄷㄷ", "ㅇㅇ", "ㄴㄴ", "ㄱㅊ", "ㅈㄱ",
			"ㅍㅎ",

			// Términos de relación social sureños (no usados en el norte)
			"오빠", "언니", "형", "누나", "동생", "친구", "썸", "썸남", "썸녀",

			// Jerga de internet+ y redes sociales
			"갑분싸", "핵노잼", "꿀잼", "멘붕", "스압", "인성", "찐", "플", "관종", "틀딱", "노답", "고인물", "유튭", "인스타", "페북", "카톡", "디엠",
			"팔로우", "언팔", "스토리",

			// Préstamos del inglés o romanización
			"파이팅", "화이팅", "굿", "베리", "리얼", "프로", "갬성", "힙", " vibe", "센스", "쿨", "핫", "핏", "룩", "템", "픽", "버서", "겜", "핵",
			"쩐다",

			// Verbos y expresiones coloquiales sureñas
			"빡치다", "열받다", "빡세다", "존예", "미쳤다", "쩔다", "갑이다", "노잼", "꿀잼", "빵터지다", "웃기고", "갑툭튀", "어그로", "티키타카", "솔로", "커플",
			"혼코노");

	/**
	 * Abre la interfaz gráfica asociada al cumplimiento del acta.
	 */
	public static void abrirGUI() {
		TipoGUI.APLIC.obtenerGUIPredeterminado(ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn.ID,
				() -> new ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn()).init();
	}

	/**
	 * Verifica si un texto en coreano contiene términos de jerga sureña prohibidos
	 * según la Ley de Protección del Idioma Cultural de Pyongyang.
	 *
	 * @param textoCoreano el texto en coreano a inspeccionar.
	 * @return {@code true} si el texto contiene al menos un término prohibido;
	 *         {@code false} en caso contrario.
	 */
	public static boolean contieneJergaSur(String textoCoreano) {
		if (textoCoreano == null) {
			return false;
		}

		for (String termino : TERMINOS_JERGA_SUR) {
			if (textoCoreano.contains(termino)) {
				abrirGUI();
				return true;
			}
		}
		return false;
	}
}