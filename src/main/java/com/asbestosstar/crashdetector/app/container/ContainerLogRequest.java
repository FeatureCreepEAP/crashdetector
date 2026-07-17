package com.asbestosstar.crashdetector.app.container;

/**
 * Solicitud inmutable compartida por las integraciones de contenedores.
 */
public final class ContainerLogRequest {

	public static final long TIMEOUT_PREDETERMINADO_SEGUNDOS = 120L;
	public static final long MAX_BYTES_PREDETERMINADO = 64L * 1024L * 1024L;

	private final String objetivo;
	private final String namespace;
	private final String contenedor;
	private final String contexto;
	private final String kubeconfig;
	private final String tail;
	private final String since;
	private final String until;
	private final String binario;
	private final String rutaLogSolaris;
	private final boolean previous;
	private final boolean timestamps;
	private final boolean prefix;
	private final boolean todosLosContenedores;
	private final boolean details;
	private final long timeoutSegundos;
	private final long maxBytes;

	private ContainerLogRequest(Builder builder) {
		this.objetivo = builder.objetivo;
		this.namespace = builder.namespace;
		this.contenedor = builder.contenedor;
		this.contexto = builder.contexto;
		this.kubeconfig = builder.kubeconfig;
		this.tail = builder.tail;
		this.since = builder.since;
		this.until = builder.until;
		this.binario = builder.binario;
		this.rutaLogSolaris = builder.rutaLogSolaris;
		this.previous = builder.previous;
		this.timestamps = builder.timestamps;
		this.prefix = builder.prefix;
		this.todosLosContenedores = builder.todosLosContenedores;
		this.details = builder.details;
		this.timeoutSegundos = builder.timeoutSegundos;
		this.maxBytes = builder.maxBytes;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public String getNamespace() {
		return namespace;
	}

	public String getContenedor() {
		return contenedor;
	}

	public String getContexto() {
		return contexto;
	}

	public String getKubeconfig() {
		return kubeconfig;
	}

	public String getTail() {
		return tail;
	}

	public String getSince() {
		return since;
	}

	public String getUntil() {
		return until;
	}

	public String getBinario() {
		return binario;
	}

	public String getRutaLogSolaris() {
		return rutaLogSolaris;
	}

	public boolean isPrevious() {
		return previous;
	}

	public boolean isTimestamps() {
		return timestamps;
	}

	public boolean isPrefix() {
		return prefix;
	}

	public boolean isTodosLosContenedores() {
		return todosLosContenedores;
	}

	public boolean isDetails() {
		return details;
	}

	public long getTimeoutSegundos() {
		return timeoutSegundos;
	}

	public long getMaxBytes() {
		return maxBytes;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {

		private String objetivo;
		private String namespace;
		private String contenedor;
		private String contexto;
		private String kubeconfig;
		private String tail;
		private String since;
		private String until;
		private String binario;
		private String rutaLogSolaris;
		private boolean previous;
		private boolean timestamps;
		private boolean prefix;
		private boolean todosLosContenedores;
		private boolean details;
		private long timeoutSegundos = TIMEOUT_PREDETERMINADO_SEGUNDOS;
		private long maxBytes = MAX_BYTES_PREDETERMINADO;

		public Builder objetivo(String valor) {
			this.objetivo = valor;
			return this;
		}

		public Builder namespace(String valor) {
			this.namespace = valor;
			return this;
		}

		public Builder contenedor(String valor) {
			this.contenedor = valor;
			return this;
		}

		public Builder contexto(String valor) {
			this.contexto = valor;
			return this;
		}

		public Builder kubeconfig(String valor) {
			this.kubeconfig = valor;
			return this;
		}

		public Builder tail(String valor) {
			this.tail = valor;
			return this;
		}

		public Builder since(String valor) {
			this.since = valor;
			return this;
		}

		public Builder until(String valor) {
			this.until = valor;
			return this;
		}

		public Builder binario(String valor) {
			this.binario = valor;
			return this;
		}

		public Builder rutaLogSolaris(String valor) {
			this.rutaLogSolaris = valor;
			return this;
		}

		public Builder previous(boolean valor) {
			this.previous = valor;
			return this;
		}

		public Builder timestamps(boolean valor) {
			this.timestamps = valor;
			return this;
		}

		public Builder prefix(boolean valor) {
			this.prefix = valor;
			return this;
		}

		public Builder todosLosContenedores(boolean valor) {
			this.todosLosContenedores = valor;
			return this;
		}

		public Builder details(boolean valor) {
			this.details = valor;
			return this;
		}

		public Builder timeoutSegundos(long valor) {
			this.timeoutSegundos = valor;
			return this;
		}

		public Builder maxBytes(long valor) {
			this.maxBytes = valor;
			return this;
		}

		public ContainerLogRequest build() {
			return new ContainerLogRequest(this);
		}
	}
}
