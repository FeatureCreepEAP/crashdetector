package com.asbestosstar.crashdetector.idioma;

import java.util.Locale;

/**
 * Textos localizados para la política corporativa de hardware.
 *
 * Cada clase de idioma conserva métodos explícitos exigidos por Idioma.java;
 * esta tabla evita copiar 49 literales largos en cada clase.
 */
final class TraduccionesPoliticaHardware {

	static final int TITULO = 0;
	static final int BOTON = 1;
	static final int DESCRIPCION = 2;
	static final int SISTEMAS = 3;
	static final int PROCESADORES = 4;
	static final int ARQUITECTURAS = 5;
	static final int BUSCAR = 6;
	static final int FAMILIA = 7;
	static final int PLATAFORMA = 8;
	static final int SUGERENCIA = 9;
	static final int POLITICA = 10;
	static final int SIN_REGLA = 11;
	static final int RECOMENDADO = 12;
	static final int NEUTRAL = 13;
	static final int DESACONSEJADO = 14;
	static final int APLICAR = 15;
	static final int LIMPIAR = 16;
	static final int REQUISITOS = 17;
	static final int RAM_MINIMA = 18;
	static final int GHZ_MINIMOS = 19;
	static final int HILOS_MINIMOS = 20;
	static final int CERO = 21;
	static final int LEYENDA = 22;
	static final int CONFIRMAR_SUGERENCIAS = 23;
	static final int CONFIRMAR_LIMPIAR = 24;
	static final int MOSTRAR_REGLAS = 25;
	static final int GUARDADO = 26;
	static final int NOMBRE_VERIFICACION = 27;
	static final int TITULO_ADVERTENCIA = 28;
	static final int DETALLE_ADVERTENCIA = 29;
	static final int DETALLE_SO = 30;
	static final int DETALLE_CPU = 31;
	static final int DETALLE_ARQUITECTURA = 32;
	static final int DETALLE_RAM = 33;
	static final int DETALLE_GHZ = 34;
	static final int DETALLE_HILOS = 35;
	static final int CONTACTO = 36;
	static final int NO_DETECTADO = 37;
	static final int DETECTOR = 38;
	static final int COLOR_FONDO = 39;
	static final int COLOR_PANEL = 40;
	static final int COLOR_TEXTO = 41;
	static final int COLOR_BOTON = 42;
	static final int COLOR_RECOMENDADO = 43;
	static final int COLOR_NEUTRAL = 44;
	static final int COLOR_DESACONSEJADO = 45;
	static final int COLOR_BORDE = 46;
	static final int COLOR_SELECCION = 47;
	static final int COLOR_TEXTO_SELECCION = 48;

	private static final String[] ES = { "Política corporativa de hardware y sistemas operativos",
			"Hardware y sistemas permitidos",
			"Configure qué plataformas son recomendadas, neutrales o desaconsejadas. Solo las entradas desaconsejadas y los mínimos activados generan advertencias. Un valor de cero desactiva el mínimo.",
			"Sistemas operativos", "Generaciones de CPU", "Arquitecturas de CPU", "Buscar:", "Familia",
			"Plataforma / generación", "Sugerencia incorporada", "Política corporativa", "Sin regla", "Recomendado",
			"Neutral", "Desaconsejado", "Aplicar sugerencias", "Limpiar política", "Requisitos mínimos opcionales",
			"RAM mínima (GB):", "Frecuencia mínima (GHz):", "Hilos mínimos:", "Cero desactiva el requisito.",
			"Verde = recomendado; texto normal = neutral; rojo = desaconsejado.",
			"¿Aplicar todas las sugerencias incorporadas como política corporativa?",
			"¿Eliminar todas las reglas y desactivar todos los mínimos?", "Mostrar solo reglas configuradas",
			"Política de hardware guardada.", "Hardware fuera de la política corporativa",
			"Hardware o sistema operativo desaconsejado", "El equipo no cumple la política corporativa:<br>%1",
			"Sistema operativo desaconsejado: %1", "Procesador desaconsejado: %1", "Arquitectura desaconsejada: %1",
			"RAM inferior a la política: se detectaron %1 GB; el mínimo es %2 GB.",
			"Frecuencia inferior a la política: se detectaron %1 GHz; el mínimo es %2 GHz.",
			"Hilos inferiores a la política: se detectaron %1; el mínimo es %2.",
			"Contacte al administrador que estableció esta política.", "No detectado",
			"<b>Equipo detectado:</b> SO: %1 | CPU: %2 | Arquitectura: %3 | RAM: %4 | Frecuencia: %5 | Hilos: %6",
			"Color de fondo de la política de hardware", "Color de paneles de la política de hardware",
			"Color de texto de la política de hardware", "Color de botones de la política de hardware",
			"Color de plataformas recomendadas", "Color de plataformas neutrales",
			"Color de plataformas desaconsejadas", "Color de bordes de la política de hardware",
			"Color de selección de la política de hardware",
			"Color del texto seleccionado de la política de hardware" };

	private static final String[] EN = { "Corporate hardware and operating-system policy",
			"Allowed hardware and systems",
			"Configure which platforms are recommended, neutral, or discouraged. Only discouraged entries and enabled minimums produce warnings. A value of zero disables the minimum.",
			"Operating systems", "CPU generations", "CPU architectures", "Search:", "Family", "Platform / generation",
			"Built-in suggestion", "Corporate policy", "No rule", "Recommended", "Neutral", "Discouraged",
			"Apply suggestions", "Clear policy", "Optional minimum requirements", "Minimum RAM (GB):",
			"Minimum frequency (GHz):", "Minimum threads:", "Zero disables the requirement.",
			"Green = recommended; normal text = neutral; red = discouraged.",
			"Apply all built-in suggestions as corporate policy?", "Remove all rules and disable all minimums?",
			"Show only configured rules", "Hardware policy saved.", "Hardware outside corporate policy",
			"Discouraged hardware or operating system", "This computer does not meet corporate policy:<br>%1",
			"Discouraged operating system: %1", "Discouraged processor: %1", "Discouraged architecture: %1",
			"RAM is below policy: %1 GB detected; minimum is %2 GB.",
			"Frequency is below policy: %1 GHz detected; minimum is %2 GHz.",
			"Thread count is below policy: %1 detected; minimum is %2.",
			"Contact the administrator who established this policy.", "Not detected",
			"<b>Detected computer:</b> OS: %1 | CPU: %2 | Architecture: %3 | RAM: %4 | Frequency: %5 | Threads: %6",
			"Hardware-policy background colour", "Hardware-policy panel colour", "Hardware-policy text colour",
			"Hardware-policy button colour", "Recommended-platform colour", "Neutral-platform colour",
			"Discouraged-platform colour", "Hardware-policy border colour", "Hardware-policy selection colour",
			"Hardware-policy selected-text colour" };

	private static final String[] AR = { "سياسة الشركة للأجهزة وأنظمة التشغيل", "الأجهزة والأنظمة المسموح بها",
			"اضبط المنصات الموصى بها أو المحايدة أو غير الموصى بها. لا تظهر التحذيرات إلا للعناصر غير الموصى بها والحدود الدنيا المفعلة. القيمة صفر تعطل الحد الأدنى.",
			"أنظمة التشغيل", "أجيال المعالج", "معماريات المعالج", "بحث:", "العائلة", "المنصة / الجيل", "اقتراح مدمج",
			"سياسة الشركة", "بلا قاعدة", "موصى به", "محايد", "غير موصى به", "تطبيق الاقتراحات", "مسح السياسة",
			"متطلبات دنيا اختيارية", "الحد الأدنى للذاكرة (GB):", "الحد الأدنى للتردد (GHz):", "الحد الأدنى للخيوط:",
			"الصفر يعطل المتطلب.", "الأخضر = موصى به؛ النص العادي = محايد؛ الأحمر = غير موصى به.",
			"هل تريد تطبيق جميع الاقتراحات المدمجة كسياسة للشركة؟", "هل تريد حذف جميع القواعد وتعطيل كل الحدود الدنيا؟",
			"إظهار القواعد المضبوطة فقط", "تم حفظ سياسة الأجهزة.", "جهاز خارج سياسة الشركة",
			"جهاز أو نظام تشغيل غير موصى به", "هذا الحاسوب لا يطابق سياسة الشركة:<br>%1", "نظام تشغيل غير موصى به: %1",
			"معالج غير موصى به: %1", "معمارية غير موصى بها: %1",
			"الذاكرة أقل من السياسة: تم اكتشاف %1 GB؛ الحد الأدنى %2 GB.",
			"التردد أقل من السياسة: تم اكتشاف %1 GHz؛ الحد الأدنى %2 GHz.",
			"عدد الخيوط أقل من السياسة: تم اكتشاف %1؛ الحد الأدنى %2.", "اتصل بالمسؤول الذي وضع هذه السياسة.",
			"غير مكتشف",
			"<b>الحاسوب المكتشف:</b> النظام: %1 | المعالج: %2 | المعمارية: %3 | الذاكرة: %4 | التردد: %5 | الخيوط: %6",
			"لون خلفية سياسة الأجهزة", "لون لوحات سياسة الأجهزة", "لون نص سياسة الأجهزة", "لون أزرار سياسة الأجهزة",
			"لون المنصات الموصى بها", "لون المنصات المحايدة", "لون المنصات غير الموصى بها", "لون حدود سياسة الأجهزة",
			"لون التحديد في سياسة الأجهزة", "لون النص المحدد في سياسة الأجهزة" };

	private static final String[] PT = { "Política corporativa de hardware e sistemas operacionais",
			"Hardware e sistemas permitidos",
			"Configure quais plataformas são recomendadas, neutras ou desaconselhadas. Somente entradas desaconselhadas e mínimos ativados geram avisos. O valor zero desativa o mínimo.",
			"Sistemas operacionais", "Gerações de CPU", "Arquiteturas de CPU", "Pesquisar:", "Família",
			"Plataforma / geração", "Sugestão incorporada", "Política corporativa", "Sem regra", "Recomendado",
			"Neutro", "Desaconselhado", "Aplicar sugestões", "Limpar política", "Requisitos mínimos opcionais",
			"RAM mínima (GB):", "Frequência mínima (GHz):", "Threads mínimas:", "Zero desativa o requisito.",
			"Verde = recomendado; texto normal = neutro; vermelho = desaconselhado.",
			"Aplicar todas as sugestões incorporadas como política corporativa?",
			"Remover todas as regras e desativar todos os mínimos?", "Mostrar apenas regras configuradas",
			"Política de hardware salva.", "Hardware fora da política corporativa",
			"Hardware ou sistema operacional desaconselhado",
			"Este computador não atende à política corporativa:<br>%1", "Sistema operacional desaconselhado: %1",
			"Processador desaconselhado: %1", "Arquitetura desaconselhada: %1",
			"RAM abaixo da política: %1 GB detectados; o mínimo é %2 GB.",
			"Frequência abaixo da política: %1 GHz detectados; o mínimo é %2 GHz.",
			"Quantidade de threads abaixo da política: %1 detectadas; o mínimo é %2.",
			"Entre em contato com o administrador que definiu esta política.", "Não detectado",
			"<b>Computador detectado:</b> SO: %1 | CPU: %2 | Arquitetura: %3 | RAM: %4 | Frequência: %5 | Threads: %6",
			"Cor de fundo da política de hardware", "Cor dos painéis da política de hardware",
			"Cor do texto da política de hardware", "Cor dos botões da política de hardware",
			"Cor das plataformas recomendadas", "Cor das plataformas neutras", "Cor das plataformas desaconselhadas",
			"Cor das bordas da política de hardware", "Cor da seleção da política de hardware",
			"Cor do texto selecionado da política de hardware" };

	private static final String[] FA = { "سیاست سازمانی سخت‌افزار و سیستم‌عامل", "سخت‌افزار و سیستم‌های مجاز",
			"پلتفرم‌های پیشنهادی، خنثی یا نامطلوب را تنظیم کنید. فقط موارد نامطلوب و حداقل‌های فعال هشدار ایجاد می‌کنند. مقدار صفر حداقل را غیرفعال می‌کند.",
			"سیستم‌عامل‌ها", "نسل‌های پردازنده", "معماری‌های پردازنده", "جستجو:", "خانواده", "پلتفرم / نسل",
			"پیشنهاد داخلی", "سیاست سازمانی", "بدون قانون", "پیشنهادی", "خنثی", "نامطلوب", "اعمال پیشنهادها",
			"پاک‌کردن سیاست", "حداقل‌های اختیاری", "حداقل RAM (GB):", "حداقل فرکانس (GHz):", "حداقل رشته‌ها:",
			"صفر شرط را غیرفعال می‌کند.", "سبز = پیشنهادی؛ متن عادی = خنثی؛ قرمز = نامطلوب.",
			"همه پیشنهادهای داخلی به‌عنوان سیاست سازمانی اعمال شوند؟", "همه قوانین حذف و همه حداقل‌ها غیرفعال شوند؟",
			"فقط قوانین تنظیم‌شده نمایش داده شوند", "سیاست سخت‌افزار ذخیره شد.", "سخت‌افزار خارج از سیاست سازمانی",
			"سخت‌افزار یا سیستم‌عامل نامطلوب", "این رایانه با سیاست سازمانی مطابقت ندارد:<br>%1",
			"سیستم‌عامل نامطلوب: %1", "پردازنده نامطلوب: %1", "معماری نامطلوب: %1",
			"RAM کمتر از سیاست است: %1 GB شناسایی شد؛ حداقل %2 GB است.",
			"فرکانس کمتر از سیاست است: %1 GHz شناسایی شد؛ حداقل %2 GHz است.",
			"تعداد رشته‌ها کمتر از سیاست است: %1 شناسایی شد؛ حداقل %2 است.",
			"با مدیری که این سیاست را تعیین کرده تماس بگیرید.", "شناسایی نشد",
			"<b>رایانه شناسایی‌شده:</b> سیستم‌عامل: %1 | CPU: %2 | معماری: %3 | RAM: %4 | فرکانس: %5 | رشته‌ها: %6",
			"رنگ پس‌زمینه سیاست سخت‌افزار", "رنگ پنل‌های سیاست سخت‌افزار", "رنگ متن سیاست سخت‌افزار",
			"رنگ دکمه‌های سیاست سخت‌افزار", "رنگ پلتفرم‌های پیشنهادی", "رنگ پلتفرم‌های خنثی", "رنگ پلتفرم‌های نامطلوب",
			"رنگ حاشیه سیاست سخت‌افزار", "رنگ انتخاب سیاست سخت‌افزار", "رنگ متن انتخاب‌شده سیاست سخت‌افزار" };

	private static final String[] RU = { "Корпоративная политика оборудования и операционных систем",
			"Разрешённое оборудование и системы",
			"Настройте рекомендуемые, нейтральные и нежелательные платформы. Предупреждения создаются только для нежелательных записей и включённых минимумов. Ноль отключает минимум.",
			"Операционные системы", "Поколения ЦП", "Архитектуры ЦП", "Поиск:", "Семейство", "Платформа / поколение",
			"Встроенная рекомендация", "Корпоративная политика", "Без правила", "Рекомендуется", "Нейтрально",
			"Не рекомендуется", "Применить рекомендации", "Очистить политику", "Необязательные минимальные требования",
			"Минимум ОЗУ (ГБ):", "Минимальная частота (ГГц):", "Минимум потоков:", "Ноль отключает требование.",
			"Зелёный = рекомендуется; обычный текст = нейтрально; красный = не рекомендуется.",
			"Применить все встроенные рекомендации как корпоративную политику?",
			"Удалить все правила и отключить все минимумы?", "Показывать только настроенные правила",
			"Политика оборудования сохранена.", "Оборудование вне корпоративной политики",
			"Нежелательное оборудование или операционная система",
			"Этот компьютер не соответствует корпоративной политике:<br>%1", "Нежелательная операционная система: %1",
			"Нежелательный процессор: %1", "Нежелательная архитектура: %1",
			"ОЗУ ниже нормы: обнаружено %1 ГБ; минимум %2 ГБ.",
			"Частота ниже нормы: обнаружено %1 ГГц; минимум %2 ГГц.",
			"Число потоков ниже нормы: обнаружено %1; минимум %2.",
			"Обратитесь к администратору, установившему эту политику.", "Не обнаружено",
			"<b>Обнаруженный компьютер:</b> ОС: %1 | ЦП: %2 | Архитектура: %3 | ОЗУ: %4 | Частота: %5 | Потоки: %6",
			"Цвет фона политики оборудования", "Цвет панелей политики оборудования",
			"Цвет текста политики оборудования", "Цвет кнопок политики оборудования", "Цвет рекомендуемых платформ",
			"Цвет нейтральных платформ", "Цвет нежелательных платформ", "Цвет границ политики оборудования",
			"Цвет выделения политики оборудования", "Цвет выделенного текста политики оборудования" };

	private static final String[] ZH = { "企业硬件和操作系统策略", "允许的硬件和系统",
			"配置推荐、中立或不建议的平台。只有不建议的条目和已启用的最低要求会产生警告。数值为零会禁用该最低要求。", "操作系统", "CPU 代际", "CPU 架构", "搜索：", "系列", "平台 / 代际",
			"内置建议", "企业策略", "无规则", "推荐", "中立", "不建议", "应用建议", "清除策略", "可选最低要求", "最低内存（GB）：", "最低频率（GHz）：", "最低线程数：",
			"零表示禁用该要求。", "绿色 = 推荐；普通文字 = 中立；红色 = 不建议。", "要将所有内置建议应用为企业策略吗？", "要删除所有规则并禁用所有最低要求吗？", "仅显示已配置规则",
			"硬件策略已保存。", "硬件不符合企业策略", "不建议的硬件或操作系统", "此计算机不符合企业策略：<br>%1", "不建议的操作系统：%1", "不建议的处理器：%1", "不建议的架构：%1",
			"内存低于策略：检测到 %1 GB；最低要求为 %2 GB。", "频率低于策略：检测到 %1 GHz；最低要求为 %2 GHz。", "线程数低于策略：检测到 %1；最低要求为 %2。",
			"请联系制定此策略的管理员。", "未检测到", "<b>检测到的计算机：</b>操作系统：%1 | CPU：%2 | 架构：%3 | 内存：%4 | 频率：%5 | 线程：%6", "硬件策略背景颜色",
			"硬件策略面板颜色", "硬件策略文字颜色", "硬件策略按钮颜色", "推荐平台颜色", "中立平台颜色", "不建议平台颜色", "硬件策略边框颜色", "硬件策略选择颜色", "硬件策略选中文字颜色" };

	private static final String[] EO = { "Korporacia politiko pri aparataro kaj operaciumoj",
			"Permesitaj aparataro kaj sistemoj",
			"Agordu kiuj platformoj estas rekomendataj, neŭtralaj aŭ malkonsilataj. Nur malkonsilataj eroj kaj aktivaj minimumoj kaŭzas avertojn. Nulo malaktivigas minimumon.",
			"Operaciumoj", "CPU-generacioj", "CPU-arkitekturoj", "Serĉi:", "Familio", "Platformo / generacio",
			"Enkonstruita propono", "Korporacia politiko", "Sen regulo", "Rekomendata", "Neŭtrala", "Malkonsilata",
			"Apliki proponojn", "Forviŝi politikon", "Laŭvolaj minimumaj postuloj", "Minimuma RAM (GB):",
			"Minimuma frekvenco (GHz):", "Minimumaj fadenoj:", "Nulo malaktivigas la postulon.",
			"Verda = rekomendata; normala teksto = neŭtrala; ruĝa = malkonsilata.",
			"Ĉu apliki ĉiujn enkonstruitajn proponojn kiel korporacian politikon?",
			"Ĉu forigi ĉiujn regulojn kaj malaktivigi ĉiujn minimumojn?", "Montri nur agorditajn regulojn",
			"Aparatara politiko konservita.", "Aparataro ekster korporacia politiko",
			"Malkonsilata aparataro aŭ operaciumo", "Ĉi tiu komputilo ne plenumas la korporacian politikon:<br>%1",
			"Malkonsilata operaciumo: %1", "Malkonsilata procesoro: %1", "Malkonsilata arkitekturo: %1",
			"RAM estas sub la politiko: %1 GB detektitaj; minimumo estas %2 GB.",
			"Frekvenco estas sub la politiko: %1 GHz detektitaj; minimumo estas %2 GHz.",
			"Fadennombro estas sub la politiko: %1 detektitaj; minimumo estas %2.",
			"Kontaktu la administranton, kiu starigis ĉi tiun politikon.", "Ne detektita",
			"<b>Detektita komputilo:</b> OS: %1 | CPU: %2 | Arkitekturo: %3 | RAM: %4 | Frekvenco: %5 | Fadenoj: %6",
			"Fona koloro de la aparatara politiko", "Panela koloro de la aparatara politiko",
			"Teksta koloro de la aparatara politiko", "Butona koloro de la aparatara politiko",
			"Koloro de rekomendataj platformoj", "Koloro de neŭtralaj platformoj", "Koloro de malkonsilataj platformoj",
			"Randa koloro de la aparatara politiko", "Elekta koloro de la aparatara politiko",
			"Koloro de elektita teksto de la aparatara politiko" };

	private static final String[] JA = { "企業向けハードウェアおよびオペレーティングシステム方針", "許可するハードウェアとシステム",
			"推奨・中立・非推奨のプラットフォームを設定します。警告が出るのは、非推奨項目と有効な最低要件だけです。ゼロは最低要件を無効にします。", "オペレーティングシステム", "CPU 世代",
			"CPU アーキテクチャ", "検索：", "ファミリー", "プラットフォーム / 世代", "組み込みの提案", "企業方針", "ルールなし", "推奨", "中立", "非推奨", "提案を適用",
			"方針を消去", "任意の最低要件", "最低 RAM（GB）：", "最低周波数（GHz）：", "最低スレッド数：", "ゼロで要件を無効化します。", "緑 = 推奨、通常文字 = 中立、赤 = 非推奨。",
			"すべての組み込み提案を企業方針として適用しますか？", "すべてのルールを削除し、最低要件を無効にしますか？", "設定済みルールのみ表示", "ハードウェア方針を保存しました。", "企業方針外のハードウェア",
			"非推奨のハードウェアまたは OS", "このコンピューターは企業方針を満たしていません：<br>%1", "非推奨のオペレーティングシステム：%1", "非推奨のプロセッサー：%1",
			"非推奨のアーキテクチャ：%1", "RAM が方針を下回っています：検出 %1 GB、最低 %2 GB。", "周波数が方針を下回っています：検出 %1 GHz、最低 %2 GHz。",
			"スレッド数が方針を下回っています：検出 %1、最低 %2。", "この方針を設定した管理者に連絡してください。", "未検出",
			"<b>検出したコンピューター：</b>OS：%1 | CPU：%2 | アーキテクチャ：%3 | RAM：%4 | 周波数：%5 | スレッド：%6", "ハードウェア方針の背景色",
			"ハードウェア方針のパネル色", "ハードウェア方針の文字色", "ハードウェア方針のボタン色", "推奨プラットフォームの色", "中立プラットフォームの色", "非推奨プラットフォームの色",
			"ハードウェア方針の境界色", "ハードウェア方針の選択色", "ハードウェア方針の選択文字色" };

	private static final String[] KO = { "기업 하드웨어 및 운영 체제 정책", "허용된 하드웨어 및 시스템",
			"권장, 중립 또는 비권장 플랫폼을 설정합니다. 비권장 항목과 활성화된 최소 요구 사항만 경고를 생성합니다. 0은 최소 요구 사항을 비활성화합니다.", "운영 체제", "CPU 세대",
			"CPU 아키텍처", "검색:", "제품군", "플랫폼 / 세대", "기본 제안", "기업 정책", "규칙 없음", "권장", "중립", "비권장", "제안 적용", "정책 지우기",
			"선택적 최소 요구 사항", "최소 RAM(GB):", "최소 주파수(GHz):", "최소 스레드 수:", "0은 요구 사항을 비활성화합니다.",
			"녹색 = 권장, 일반 글자 = 중립, 빨간색 = 비권장.", "모든 기본 제안을 기업 정책으로 적용하시겠습니까?", "모든 규칙을 삭제하고 모든 최소 요구 사항을 비활성화하시겠습니까?",
			"설정된 규칙만 표시", "하드웨어 정책을 저장했습니다.", "기업 정책을 벗어난 하드웨어", "비권장 하드웨어 또는 운영 체제", "이 컴퓨터는 기업 정책을 충족하지 않습니다:<br>%1",
			"비권장 운영 체제: %1", "비권장 프로세서: %1", "비권장 아키텍처: %1", "RAM이 정책보다 낮습니다: %1GB 감지, 최소 %2GB.",
			"주파수가 정책보다 낮습니다: %1GHz 감지, 최소 %2GHz.", "스레드 수가 정책보다 낮습니다: %1개 감지, 최소 %2개.", "이 정책을 설정한 관리자에게 문의하십시오.",
			"감지되지 않음", "<b>감지된 컴퓨터:</b> OS: %1 | CPU: %2 | 아키텍처: %3 | RAM: %4 | 주파수: %5 | 스레드: %6", "하드웨어 정책 배경색",
			"하드웨어 정책 패널색", "하드웨어 정책 글자색", "하드웨어 정책 버튼색", "권장 플랫폼 색", "중립 플랫폼 색", "비권장 플랫폼 색", "하드웨어 정책 테두리색",
			"하드웨어 정책 선택색", "하드웨어 정책 선택 글자색" };

	private static final String[] UK = { "Корпоративна політика обладнання та операційних систем",
			"Дозволене обладнання та системи",
			"Налаштуйте рекомендовані, нейтральні та небажані платформи. Попередження створюються лише для небажаних записів і ввімкнених мінімумів. Нуль вимикає мінімум.",
			"Операційні системи", "Покоління ЦП", "Архітектури ЦП", "Пошук:", "Родина", "Платформа / покоління",
			"Вбудована рекомендація", "Корпоративна політика", "Без правила", "Рекомендовано", "Нейтрально",
			"Не рекомендовано", "Застосувати рекомендації", "Очистити політику", "Необов’язкові мінімальні вимоги",
			"Мінімум ОЗП (ГБ):", "Мінімальна частота (ГГц):", "Мінімум потоків:", "Нуль вимикає вимогу.",
			"Зелений = рекомендовано; звичайний текст = нейтрально; червоний = не рекомендовано.",
			"Застосувати всі вбудовані рекомендації як корпоративну політику?",
			"Видалити всі правила та вимкнути всі мінімуми?", "Показувати лише налаштовані правила",
			"Політику обладнання збережено.", "Обладнання поза корпоративною політикою",
			"Небажане обладнання або операційна система", "Цей комп’ютер не відповідає корпоративній політиці:<br>%1",
			"Небажана операційна система: %1", "Небажаний процесор: %1", "Небажана архітектура: %1",
			"ОЗП нижче політики: виявлено %1 ГБ; мінімум %2 ГБ.",
			"Частота нижче політики: виявлено %1 ГГц; мінімум %2 ГГц.",
			"Кількість потоків нижче політики: виявлено %1; мінімум %2.",
			"Зверніться до адміністратора, який установив цю політику.", "Не виявлено",
			"<b>Виявлений комп’ютер:</b> ОС: %1 | ЦП: %2 | Архітектура: %3 | ОЗП: %4 | Частота: %5 | Потоки: %6",
			"Колір фону політики обладнання", "Колір панелей політики обладнання", "Колір тексту політики обладнання",
			"Колір кнопок політики обладнання", "Колір рекомендованих платформ", "Колір нейтральних платформ",
			"Колір небажаних платформ", "Колір меж політики обладнання", "Колір виділення політики обладнання",
			"Колір виділеного тексту політики обладнання" };

	private static final String[] VI = { "Chính sách phần cứng và hệ điều hành của tổ chức",
			"Phần cứng và hệ thống được phép",
			"Cấu hình nền tảng được khuyến nghị, trung lập hoặc không khuyến nghị. Chỉ mục không khuyến nghị và yêu cầu tối thiểu đã bật mới tạo cảnh báo. Giá trị 0 sẽ tắt yêu cầu tối thiểu.",
			"Hệ điều hành", "Thế hệ CPU", "Kiến trúc CPU", "Tìm kiếm:", "Họ", "Nền tảng / thế hệ", "Đề xuất tích hợp",
			"Chính sách tổ chức", "Không có quy tắc", "Khuyến nghị", "Trung lập", "Không khuyến nghị",
			"Áp dụng đề xuất", "Xóa chính sách", "Yêu cầu tối thiểu tùy chọn", "RAM tối thiểu (GB):",
			"Tần số tối thiểu (GHz):", "Số luồng tối thiểu:", "0 sẽ tắt yêu cầu.",
			"Xanh lá = khuyến nghị; chữ thường = trung lập; đỏ = không khuyến nghị.",
			"Áp dụng mọi đề xuất tích hợp làm chính sách tổ chức?", "Xóa mọi quy tắc và tắt mọi yêu cầu tối thiểu?",
			"Chỉ hiển thị quy tắc đã cấu hình", "Đã lưu chính sách phần cứng.", "Phần cứng ngoài chính sách tổ chức",
			"Phần cứng hoặc hệ điều hành không được khuyến nghị",
			"Máy tính này không đáp ứng chính sách tổ chức:<br>%1", "Hệ điều hành không được khuyến nghị: %1",
			"Bộ xử lý không được khuyến nghị: %1", "Kiến trúc không được khuyến nghị: %1",
			"RAM thấp hơn chính sách: phát hiện %1 GB; tối thiểu %2 GB.",
			"Tần số thấp hơn chính sách: phát hiện %1 GHz; tối thiểu %2 GHz.",
			"Số luồng thấp hơn chính sách: phát hiện %1; tối thiểu %2.",
			"Hãy liên hệ quản trị viên đã thiết lập chính sách này.", "Không phát hiện",
			"<b>Máy tính được phát hiện:</b> HĐH: %1 | CPU: %2 | Kiến trúc: %3 | RAM: %4 | Tần số: %5 | Luồng: %6",
			"Màu nền chính sách phần cứng", "Màu bảng chính sách phần cứng", "Màu chữ chính sách phần cứng",
			"Màu nút chính sách phần cứng", "Màu nền tảng được khuyến nghị", "Màu nền tảng trung lập",
			"Màu nền tảng không khuyến nghị", "Màu viền chính sách phần cứng", "Màu lựa chọn chính sách phần cứng",
			"Màu chữ được chọn của chính sách phần cứng" };

	private static final String[] ID = { "Kebijakan perusahaan untuk perangkat keras dan sistem operasi",
			"Perangkat keras dan sistem yang diizinkan",
			"Atur platform yang direkomendasikan, netral, atau tidak disarankan. Hanya entri yang tidak disarankan dan batas minimum aktif yang menghasilkan peringatan. Nilai nol menonaktifkan batas minimum.",
			"Sistem operasi", "Generasi CPU", "Arsitektur CPU", "Cari:", "Keluarga", "Platform / generasi",
			"Saran bawaan", "Kebijakan perusahaan", "Tanpa aturan", "Direkomendasikan", "Netral", "Tidak disarankan",
			"Terapkan saran", "Hapus kebijakan", "Persyaratan minimum opsional", "RAM minimum (GB):",
			"Frekuensi minimum (GHz):", "Thread minimum:", "Nol menonaktifkan persyaratan.",
			"Hijau = direkomendasikan; teks biasa = netral; merah = tidak disarankan.",
			"Terapkan semua saran bawaan sebagai kebijakan perusahaan?",
			"Hapus semua aturan dan nonaktifkan semua batas minimum?", "Tampilkan hanya aturan yang dikonfigurasi",
			"Kebijakan perangkat keras disimpan.", "Perangkat keras di luar kebijakan perusahaan",
			"Perangkat keras atau sistem operasi tidak disarankan",
			"Komputer ini tidak memenuhi kebijakan perusahaan:<br>%1", "Sistem operasi tidak disarankan: %1",
			"Prosesor tidak disarankan: %1", "Arsitektur tidak disarankan: %1",
			"RAM di bawah kebijakan: terdeteksi %1 GB; minimum %2 GB.",
			"Frekuensi di bawah kebijakan: terdeteksi %1 GHz; minimum %2 GHz.",
			"Jumlah thread di bawah kebijakan: terdeteksi %1; minimum %2.",
			"Hubungi administrator yang menetapkan kebijakan ini.", "Tidak terdeteksi",
			"<b>Komputer terdeteksi:</b> OS: %1 | CPU: %2 | Arsitektur: %3 | RAM: %4 | Frekuensi: %5 | Thread: %6",
			"Warna latar kebijakan perangkat keras", "Warna panel kebijakan perangkat keras",
			"Warna teks kebijakan perangkat keras", "Warna tombol kebijakan perangkat keras",
			"Warna platform yang direkomendasikan", "Warna platform netral", "Warna platform yang tidak disarankan",
			"Warna batas kebijakan perangkat keras", "Warna pilihan kebijakan perangkat keras",
			"Warna teks terpilih kebijakan perangkat keras" };

	private static final String[] MS = { "Dasar korporat untuk perkakasan dan sistem pengendalian",
			"Perkakasan dan sistem yang dibenarkan",
			"Tetapkan platform yang disyorkan, neutral atau tidak digalakkan. Hanya entri tidak digalakkan dan had minimum aktif menghasilkan amaran. Nilai sifar menyahaktifkan had minimum.",
			"Sistem pengendalian", "Generasi CPU", "Seni bina CPU", "Cari:", "Keluarga", "Platform / generasi",
			"Cadangan terbina dalam", "Dasar korporat", "Tiada peraturan", "Disyorkan", "Neutral", "Tidak digalakkan",
			"Gunakan cadangan", "Kosongkan dasar", "Keperluan minimum pilihan", "RAM minimum (GB):",
			"Frekuensi minimum (GHz):", "Benang minimum:", "Sifar menyahaktifkan keperluan.",
			"Hijau = disyorkan; teks biasa = neutral; merah = tidak digalakkan.",
			"Gunakan semua cadangan terbina dalam sebagai dasar korporat?",
			"Buang semua peraturan dan nyahaktifkan semua had minimum?", "Paparkan peraturan yang dikonfigurasi sahaja",
			"Dasar perkakasan disimpan.", "Perkakasan di luar dasar korporat",
			"Perkakasan atau sistem pengendalian tidak digalakkan", "Komputer ini tidak memenuhi dasar korporat:<br>%1",
			"Sistem pengendalian tidak digalakkan: %1", "Pemproses tidak digalakkan: %1",
			"Seni bina tidak digalakkan: %1", "RAM di bawah dasar: %1 GB dikesan; minimum %2 GB.",
			"Frekuensi di bawah dasar: %1 GHz dikesan; minimum %2 GHz.",
			"Bilangan benang di bawah dasar: %1 dikesan; minimum %2.", "Hubungi pentadbir yang menetapkan dasar ini.",
			"Tidak dikesan",
			"<b>Komputer dikesan:</b> OS: %1 | CPU: %2 | Seni bina: %3 | RAM: %4 | Frekuensi: %5 | Benang: %6",
			"Warna latar dasar perkakasan", "Warna panel dasar perkakasan", "Warna teks dasar perkakasan",
			"Warna butang dasar perkakasan", "Warna platform disyorkan", "Warna platform neutral",
			"Warna platform tidak digalakkan", "Warna sempadan dasar perkakasan", "Warna pilihan dasar perkakasan",
			"Warna teks dipilih dasar perkakasan" };

	private static final String[] KM = { "គោលការណ៍សាជីវកម្មសម្រាប់ផ្នែករឹង និងប្រព័ន្ធប្រតិបត្តិការ",
			"ផ្នែករឹង និងប្រព័ន្ធដែលអនុញ្ញាត",
			"កំណត់វេទិកាដែលបានណែនាំ អព្យាក្រឹត ឬមិនណែនាំ។ មានតែធាតុមិនណែនាំ និងកម្រិតអប្បបរមាដែលបានបើកប៉ុណ្ណោះដែលបង្កើតការព្រមាន។ តម្លៃសូន្យបិទកម្រិតអប្បបរមា។",
			"ប្រព័ន្ធប្រតិបត្តិការ", "ជំនាន់ CPU", "ស្ថាបត្យកម្ម CPU", "ស្វែងរក៖", "គ្រួសារ", "វេទិកា / ជំនាន់",
			"សំណើដែលភ្ជាប់មកជាមួយ", "គោលការណ៍សាជីវកម្ម", "គ្មានច្បាប់", "បានណែនាំ", "អព្យាក្រឹត", "មិនណែនាំ",
			"អនុវត្តសំណើ", "សម្អាតគោលការណ៍", "លក្ខខណ្ឌអប្បបរមាជាជម្រើស", "RAM អប្បបរមា (GB)៖", "ប្រេកង់អប្បបរមា (GHz)៖",
			"ចំនួន thread អប្បបរមា៖", "សូន្យបិទលក្ខខណ្ឌ។",
			"បៃតង = បានណែនាំ; អក្សរធម្មតា = អព្យាក្រឹត; ក្រហម = មិនណែនាំ។", "អនុវត្តសំណើទាំងអស់ជាគោលការណ៍សាជីវកម្មឬ?",
			"លុបច្បាប់ទាំងអស់ និងបិទកម្រិតអប្បបរមាទាំងអស់ឬ?", "បង្ហាញតែច្បាប់ដែលបានកំណត់",
			"បានរក្សាទុកគោលការណ៍ផ្នែករឹង។", "ផ្នែករឹងក្រៅគោលការណ៍សាជីវកម្ម", "ផ្នែករឹង ឬប្រព័ន្ធប្រតិបត្តិការមិនណែនាំ",
			"កុំព្យូទ័រនេះមិនគោរពគោលការណ៍សាជីវកម្មទេ៖<br>%1", "ប្រព័ន្ធប្រតិបត្តិការមិនណែនាំ៖ %1",
			"ឧបករណ៍ដំណើរការមិនណែនាំ៖ %1", "ស្ថាបត្យកម្មមិនណែនាំ៖ %1",
			"RAM ទាបជាងគោលការណ៍៖ រកឃើញ %1 GB; អប្បបរមា %2 GB។", "ប្រេកង់ទាបជាងគោលការណ៍៖ រកឃើញ %1 GHz; អប្បបរមា %2 GHz។",
			"ចំនួន thread ទាបជាងគោលការណ៍៖ រកឃើញ %1; អប្បបរមា %2។", "សូមទាក់ទងអ្នកគ្រប់គ្រងដែលបានកំណត់គោលការណ៍នេះ។",
			"រកមិនឃើញ",
			"<b>កុំព្យូទ័រដែលបានរកឃើញ៖</b> OS: %1 | CPU: %2 | ស្ថាបត្យកម្ម: %3 | RAM: %4 | ប្រេកង់: %5 | Threads: %6",
			"ពណ៌ផ្ទៃខាងក្រោយគោលការណ៍ផ្នែករឹង", "ពណ៌ផ្ទាំងគោលការណ៍ផ្នែករឹង", "ពណ៌អក្សរគោលការណ៍ផ្នែករឹង",
			"ពណ៌ប៊ូតុងគោលការណ៍ផ្នែករឹង", "ពណ៌វេទិកាដែលបានណែនាំ", "ពណ៌វេទិកាអព្យាក្រឹត", "ពណ៌វេទិកាមិនណែនាំ",
			"ពណ៌ស៊ុមគោលការណ៍ផ្នែករឹង", "ពណ៌ជម្រើសគោលការណ៍ផ្នែករឹង", "ពណ៌អក្សរដែលបានជ្រើសរើស" };

	private static final String[] TH = { "นโยบายองค์กรสำหรับฮาร์ดแวร์และระบบปฏิบัติการ", "ฮาร์ดแวร์และระบบที่อนุญาต",
			"กำหนดแพลตฟอร์มที่แนะนำ เป็นกลาง หรือไม่แนะนำ เฉพาะรายการที่ไม่แนะนำและค่าขั้นต่ำที่เปิดใช้เท่านั้นที่จะสร้างคำเตือน ค่าเป็นศูนย์จะปิดค่าขั้นต่ำ",
			"ระบบปฏิบัติการ", "รุ่นของ CPU", "สถาปัตยกรรม CPU", "ค้นหา:", "ตระกูล", "แพลตฟอร์ม / รุ่น", "คำแนะนำในตัว",
			"นโยบายองค์กร", "ไม่มีกฎ", "แนะนำ", "เป็นกลาง", "ไม่แนะนำ", "ใช้คำแนะนำ", "ล้างนโยบาย",
			"ข้อกำหนดขั้นต่ำแบบเลือกได้", "RAM ขั้นต่ำ (GB):", "ความถี่ขั้นต่ำ (GHz):", "เธรดขั้นต่ำ:",
			"ศูนย์จะปิดข้อกำหนด", "สีเขียว = แนะนำ; ข้อความปกติ = เป็นกลาง; สีแดง = ไม่แนะนำ",
			"ใช้คำแนะนำในตัวทั้งหมดเป็นนโยบายองค์กรหรือไม่?", "ลบกฎทั้งหมดและปิดค่าขั้นต่ำทั้งหมดหรือไม่?",
			"แสดงเฉพาะกฎที่ตั้งค่าไว้", "บันทึกนโยบายฮาร์ดแวร์แล้ว", "ฮาร์ดแวร์อยู่นอกนโยบายองค์กร",
			"ฮาร์ดแวร์หรือระบบปฏิบัติการที่ไม่แนะนำ", "คอมพิวเตอร์นี้ไม่เป็นไปตามนโยบายองค์กร:<br>%1",
			"ระบบปฏิบัติการที่ไม่แนะนำ: %1", "โปรเซสเซอร์ที่ไม่แนะนำ: %1", "สถาปัตยกรรมที่ไม่แนะนำ: %1",
			"RAM ต่ำกว่านโยบาย: ตรวจพบ %1 GB; ขั้นต่ำ %2 GB", "ความถี่ต่ำกว่านโยบาย: ตรวจพบ %1 GHz; ขั้นต่ำ %2 GHz",
			"จำนวนเธรดต่ำกว่านโยบาย: ตรวจพบ %1; ขั้นต่ำ %2", "ติดต่อผู้ดูแลระบบที่กำหนดนโยบายนี้", "ตรวจไม่พบ",
			"<b>คอมพิวเตอร์ที่ตรวจพบ:</b> OS: %1 | CPU: %2 | สถาปัตยกรรม: %3 | RAM: %4 | ความถี่: %5 | เธรด: %6",
			"สีพื้นหลังนโยบายฮาร์ดแวร์", "สีแผงนโยบายฮาร์ดแวร์", "สีข้อความนโยบายฮาร์ดแวร์", "สีปุ่มนโยบายฮาร์ดแวร์",
			"สีแพลตฟอร์มที่แนะนำ", "สีแพลตฟอร์มเป็นกลาง", "สีแพลตฟอร์มที่ไม่แนะนำ", "สีขอบนโยบายฮาร์ดแวร์",
			"สีการเลือกนโยบายฮาร์ดแวร์", "สีข้อความที่เลือกของนโยบายฮาร์ดแวร์" };

	private static final String[] LO = { "ນະໂຍບາຍອົງກອນສຳລັບຮາດແວ ແລະ ລະບົບປະຕິບັດການ", "ຮາດແວ ແລະ ລະບົບທີ່ອະນຸຍາດ",
			"ກຳນົດແພລດຟອມທີ່ແນະນຳ, ເປັນກາງ ຫຼື ບໍ່ແນະນຳ. ມີພຽງລາຍການບໍ່ແນະນຳ ແລະ ຄ່າຕ່ຳສຸດທີ່ເປີດໃຊ້ເທົ່ານັ້ນທີ່ສ້າງຄຳເຕືອນ. ຄ່າສູນປິດຄ່າຕ່ຳສຸດ.",
			"ລະບົບປະຕິບັດການ", "ຮຸ່ນ CPU", "ສະຖາປັດຕະຍະກຳ CPU", "ຄົ້ນຫາ:", "ຕະກູນ", "ແພລດຟອມ / ຮຸ່ນ", "ຄຳແນະນຳໃນຕົວ",
			"ນະໂຍບາຍອົງກອນ", "ບໍ່ມີກົດ", "ແນະນຳ", "ເປັນກາງ", "ບໍ່ແນະນຳ", "ນຳໃຊ້ຄຳແນະນຳ", "ລ້າງນະໂຍບາຍ",
			"ຂໍ້ກຳນົດຕ່ຳສຸດແບບເລືອກ", "RAM ຕ່ຳສຸດ (GB):", "ຄວາມຖີ່ຕ່ຳສຸດ (GHz):", "ຈຳນວນ thread ຕ່ຳສຸດ:",
			"ສູນປິດຂໍ້ກຳນົດ.", "ສີຂຽວ = ແນະນຳ; ຂໍ້ຄວາມປົກກະຕິ = ເປັນກາງ; ສີແດງ = ບໍ່ແນະນຳ.",
			"ນຳໃຊ້ຄຳແນະນຳທັງໝົດເປັນນະໂຍບາຍອົງກອນບໍ?", "ລຶບກົດທັງໝົດ ແລະ ປິດຄ່າຕ່ຳສຸດທັງໝົດບໍ?",
			"ສະແດງສະເພາະກົດທີ່ຕັ້ງໄວ້", "ບັນທຶກນະໂຍບາຍຮາດແວແລ້ວ.", "ຮາດແວນອກນະໂຍບາຍອົງກອນ",
			"ຮາດແວ ຫຼື ລະບົບປະຕິບັດການທີ່ບໍ່ແນະນຳ", "ຄອມພິວເຕີນີ້ບໍ່ກົງກັບນະໂຍບາຍອົງກອນ:<br>%1",
			"ລະບົບປະຕິບັດການບໍ່ແນະນຳ: %1", "ໂປຣເຊສເຊີບໍ່ແນະນຳ: %1", "ສະຖາປັດຕະຍະກຳບໍ່ແນະນຳ: %1",
			"RAM ຕ່ຳກວ່ານະໂຍບາຍ: ພົບ %1 GB; ຕ່ຳສຸດ %2 GB.", "ຄວາມຖີ່ຕ່ຳກວ່ານະໂຍບາຍ: ພົບ %1 GHz; ຕ່ຳສຸດ %2 GHz.",
			"ຈຳນວນ thread ຕ່ຳກວ່ານະໂຍບາຍ: ພົບ %1; ຕ່ຳສຸດ %2.", "ຕິດຕໍ່ຜູ້ບໍລິຫານທີ່ກຳນົດນະໂຍບາຍນີ້.", "ກວດບໍ່ພົບ",
			"<b>ຄອມພິວເຕີທີ່ກວດພົບ:</b> OS: %1 | CPU: %2 | ສະຖາປັດຕະຍະກຳ: %3 | RAM: %4 | ຄວາມຖີ່: %5 | Threads: %6",
			"ສີພື້ນຫຼັງນະໂຍບາຍຮາດແວ", "ສີແຜງນະໂຍບາຍຮາດແວ", "ສີຂໍ້ຄວາມນະໂຍບາຍຮາດແວ", "ສີປຸ່ມນະໂຍບາຍຮາດແວ",
			"ສີແພລດຟອມແນະນຳ", "ສີແພລດຟອມເປັນກາງ", "ສີແພລດຟອມບໍ່ແນະນຳ", "ສີຂອບນະໂຍບາຍຮາດແວ", "ສີການເລືອກນະໂຍບາຍຮາດແວ",
			"ສີຂໍ້ຄວາມທີ່ເລືອກ" };

	private static final String[] FR = { "Politique d’entreprise pour le matériel et les systèmes d’exploitation",
			"Matériel et systèmes autorisés",
			"Configurez les plateformes recommandées, neutres ou déconseillées. Seules les entrées déconseillées et les valeurs minimales activées génèrent des avertissements. La valeur zéro désactive le minimum.",
			"Systèmes d’exploitation", "Générations de processeurs", "Architectures de processeur", "Rechercher :",
			"Famille", "Plateforme / génération", "Suggestion intégrée", "Politique d’entreprise", "Aucune règle",
			"Recommandé", "Neutre", "Déconseillé", "Appliquer les suggestions", "Effacer la politique",
			"Exigences minimales facultatives", "RAM minimale (Go) :", "Fréquence minimale (GHz) :",
			"Nombre minimal de threads :", "Zéro désactive l’exigence.",
			"Vert = recommandé ; texte normal = neutre ; rouge = déconseillé.",
			"Appliquer toutes les suggestions intégrées comme politique d’entreprise ?",
			"Supprimer toutes les règles et désactiver tous les minimums ?",
			"Afficher uniquement les règles configurées", "Politique matérielle enregistrée.",
			"Matériel hors politique d’entreprise", "Matériel ou système d’exploitation déconseillé",
			"Cet ordinateur ne respecte pas la politique d’entreprise :<br>%1",
			"Système d’exploitation déconseillé : %1", "Processeur déconseillé : %1", "Architecture déconseillée : %1",
			"RAM inférieure à la politique : %1 Go détectés ; minimum %2 Go.",
			"Fréquence inférieure à la politique : %1 GHz détectés ; minimum %2 GHz.",
			"Nombre de threads inférieur à la politique : %1 détectés ; minimum %2.",
			"Contactez l’administrateur qui a défini cette politique.", "Non détecté",
			"<b>Ordinateur détecté :</b> SE : %1 | CPU : %2 | Architecture : %3 | RAM : %4 | Fréquence : %5 | Threads : %6",
			"Couleur de fond de la politique matérielle", "Couleur des panneaux de la politique matérielle",
			"Couleur du texte de la politique matérielle", "Couleur des boutons de la politique matérielle",
			"Couleur des plateformes recommandées", "Couleur des plateformes neutres",
			"Couleur des plateformes déconseillées", "Couleur des bordures de la politique matérielle",
			"Couleur de sélection de la politique matérielle",
			"Couleur du texte sélectionné de la politique matérielle" };

	private static final String[] SW = { "Sera ya shirika ya maunzi na mifumo ya uendeshaji",
			"Maunzi na mifumo inayoruhusiwa",
			"Weka majukwaa yanayopendekezwa, ya kawaida au yasiyopendekezwa. Ni vipengee visivyopendekezwa na viwango vya chini vilivyowashwa pekee vinavyotoa onyo. Thamani sifuri huzima kiwango cha chini.",
			"Mifumo ya uendeshaji", "Vizazi vya CPU", "Miundo ya CPU", "Tafuta:", "Familia", "Jukwaa / kizazi",
			"Pendekezo la ndani", "Sera ya shirika", "Hakuna sheria", "Inapendekezwa", "Ya kawaida", "Haipendekezwi",
			"Tumia mapendekezo", "Futa sera", "Mahitaji ya chini ya hiari", "RAM ya chini (GB):",
			"Masafa ya chini (GHz):", "Thread za chini:", "Sifuri huzima hitaji.",
			"Kijani = inapendekezwa; maandishi ya kawaida = ya kawaida; nyekundu = haipendekezwi.",
			"Utumie mapendekezo yote ya ndani kama sera ya shirika?",
			"Uondoe sheria zote na uzime viwango vyote vya chini?", "Onyesha sheria zilizowekwa pekee",
			"Sera ya maunzi imehifadhiwa.", "Maunzi nje ya sera ya shirika",
			"Maunzi au mfumo wa uendeshaji usiopendekezwa", "Kompyuta hii haikidhi sera ya shirika:<br>%1",
			"Mfumo wa uendeshaji usiopendekezwa: %1", "Kichakataji kisichopendekezwa: %1", "Muundo usiopendekezwa: %1",
			"RAM iko chini ya sera: %1 GB zimegunduliwa; kiwango cha chini ni %2 GB.",
			"Masafa yako chini ya sera: %1 GHz zimegunduliwa; kiwango cha chini ni %2 GHz.",
			"Idadi ya thread iko chini ya sera: %1 zimegunduliwa; kiwango cha chini ni %2.",
			"Wasiliana na msimamizi aliyeweka sera hii.", "Haijagunduliwa",
			"<b>Kompyuta iliyogunduliwa:</b> OS: %1 | CPU: %2 | Muundo: %3 | RAM: %4 | Masafa: %5 | Threads: %6",
			"Rangi ya usuli ya sera ya maunzi", "Rangi ya paneli za sera ya maunzi",
			"Rangi ya maandishi ya sera ya maunzi", "Rangi ya vitufe vya sera ya maunzi",
			"Rangi ya majukwaa yanayopendekezwa", "Rangi ya majukwaa ya kawaida", "Rangi ya majukwaa yasiyopendekezwa",
			"Rangi ya mipaka ya sera ya maunzi", "Rangi ya uteuzi wa sera ya maunzi",
			"Rangi ya maandishi yaliyochaguliwa ya sera ya maunzi" };

	private TraduccionesPoliticaHardware() {
	}

	static String t(String codigo, int indice) {
		String[] idioma = seleccionar(codigo);
		if (indice < 0 || indice >= ES.length) {
			return "";
		}
		String valor = idioma[indice];
		return valor == null || valor.isEmpty() ? ES[indice] : valor;
	}

	static String f(String codigo, int indice, String... valores) {
		String texto = t(codigo, indice);
		if (valores != null) {
			for (int i = 0; i < valores.length; i++) {
				texto = texto.replace("%" + (i + 1), valores[i] == null ? "" : valores[i]);
			}
		}
		return texto;
	}

	private static String[] seleccionar(String codigo) {
		String c = codigo == null ? "es" : codigo.trim().toLowerCase(Locale.ROOT).replace('_', '-');
		int guion = c.indexOf('-');
		if (guion > 0) {
			c = c.substring(0, guion);
		}
		switch (c) {
		case "en":
			return EN;
		case "ar":
			return AR;
		case "pt":
			return PT;
		case "fa":
			return FA;
		case "ru":
			return RU;
		case "zh":
			return ZH;
		case "eo":
			return EO;
		case "ja":
			return JA;
		case "ko":
			return KO;
		case "uk":
			return UK;
		case "vi":
			return VI;
		case "id":
			return ID;
		case "ms":
			return MS;
		case "km":
			return KM;
		case "th":
			return TH;
		case "lo":
			return LO;
		case "fr":
			return FR;
		case "sw":
			return SW;
		case "es":
		default:
			return ES;
		}
	}

}
