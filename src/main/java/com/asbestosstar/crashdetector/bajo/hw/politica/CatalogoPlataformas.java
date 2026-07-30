package com.asbestosstar.crashdetector.bajo.hw.politica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Entrada;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Estado;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.TipoEntrada;

/**
 * Catálogo amplio de plataformas que la política corporativa puede clasificar.
 *
 * Los estados de esta clase son sugerencias visuales. No se convierten en
 * reglas hasta que el administrador pulsa "Aplicar sugerencias". Por tanto, la
 * instalación inicial no desaconseja ningún equipo.
 */
public final class CatalogoPlataformas {

	private static final List<Entrada> SISTEMAS = crearSistemas();
	private static final List<Entrada> ARQUITECTURAS = crearArquitecturas();
	private static final List<Entrada> PROCESADORES = crearProcesadores();

	private CatalogoPlataformas() {
	}

	public static List<Entrada> sistemasOperativos() {
		return SISTEMAS;
	}

	public static List<Entrada> arquitecturas() {
		return ARQUITECTURAS;
	}

	public static List<Entrada> procesadores() {
		return PROCESADORES;
	}

	public static List<Entrada> porTipo(TipoEntrada tipo) {
		if (tipo == TipoEntrada.SISTEMA_OPERATIVO) {
			return SISTEMAS;
		}
		if (tipo == TipoEntrada.ARQUITECTURA) {
			return ARQUITECTURAS;
		}
		return PROCESADORES;
	}

	public static List<Entrada> todas() {
		List<Entrada> ret = new ArrayList<Entrada>(SISTEMAS.size() + ARQUITECTURAS.size() + PROCESADORES.size());
		ret.addAll(SISTEMAS);
		ret.addAll(ARQUITECTURAS);
		ret.addAll(PROCESADORES);
		return Collections.unmodifiableList(ret);
	}

	private static List<Entrada> crearSistemas() {
		List<Entrada> ret = new ArrayList<Entrada>();
		ret.add(new Entrada("linux_generic", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Linux", Estado.RECOMENDADO,
				"gnu linux"));
		ret.add(new Entrada("rhel", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Red Hat Enterprise Linux",
				Estado.RECOMENDADO, "red hat enterprise linux", "rhel"));
		ret.add(new Entrada("fedora", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Fedora Linux", Estado.RECOMENDADO,
				"fedora"));
		ret.add(new Entrada("centos_stream", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "CentOS Stream",
				Estado.RECOMENDADO, "centos stream", "centos"));
		ret.add(new Entrada("rocky", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Rocky Linux", Estado.RECOMENDADO,
				"rocky linux"));
		ret.add(new Entrada("alma", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "AlmaLinux", Estado.RECOMENDADO,
				"almalinux", "alma linux"));
		ret.add(new Entrada("debian", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Debian GNU/Linux", Estado.RECOMENDADO,
				"debian"));
		ret.add(new Entrada("ubuntu", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Ubuntu", Estado.RECOMENDADO, "ubuntu"));
		ret.add(new Entrada("linux_mint", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Linux Mint", Estado.RECOMENDADO,
				"linux mint"));
		ret.add(new Entrada("sles", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "SUSE Linux Enterprise Server",
				Estado.RECOMENDADO, "suse linux enterprise", "sles"));
		ret.add(new Entrada("opensuse", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "openSUSE", Estado.RECOMENDADO,
				"opensuse"));
		ret.add(new Entrada("oracle_linux", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Oracle Linux", Estado.RECOMENDADO,
				"oracle linux"));
		ret.add(new Entrada("arch_linux", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Arch Linux", Estado.RECOMENDADO,
				"arch linux"));
		ret.add(new Entrada("gentoo", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Gentoo Linux", Estado.RECOMENDADO,
				"gentoo"));
		ret.add(new Entrada("alpine", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Alpine Linux", Estado.RECOMENDADO,
				"alpine linux"));
		ret.add(new Entrada("nixos", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "NixOS", Estado.RECOMENDADO, "nixos"));
		ret.add(new Entrada("guix", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "GNU Guix System", Estado.RECOMENDADO,
				"guix system"));
		ret.add(new Entrada("slackware", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Slackware Linux", Estado.RECOMENDADO,
				"slackware"));
		ret.add(new Entrada("void_linux", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Void Linux", Estado.RECOMENDADO,
				"void linux"));
		ret.add(new Entrada("clear_linux", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Clear Linux OS", Estado.RECOMENDADO,
				"clear linux"));
		ret.add(new Entrada("amazon_linux", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Amazon Linux", Estado.RECOMENDADO,
				"amazon linux"));
		ret.add(new Entrada("steam_os", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "SteamOS", Estado.RECOMENDADO,
				"steamos"));
		ret.add(new Entrada("chrome_os", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "ChromeOS", Estado.RECOMENDADO,
				"chrome os", "chromeos"));
		ret.add(new Entrada("android", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Android", Estado.RECOMENDADO,
				"android"));
		ret.add(new Entrada("tizen", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "Tizen", Estado.RECOMENDADO, "tizen"));
		ret.add(new Entrada("openwrt", TipoEntrada.SISTEMA_OPERATIVO, "Linux", "OpenWrt", Estado.RECOMENDADO,
				"openwrt"));
		ret.add(new Entrada("solaris_11", TipoEntrada.SISTEMA_OPERATIVO, "Solaris", "Oracle Solaris 11",
				Estado.RECOMENDADO, "sunos 5.11", "solaris 11"));
		ret.add(new Entrada("solaris_generic", TipoEntrada.SISTEMA_OPERATIVO, "Solaris", "Oracle Solaris / SunOS",
				Estado.RECOMENDADO, "sunos", "solaris"));
		ret.add(new Entrada("illumos", TipoEntrada.SISTEMA_OPERATIVO, "illumos", "illumos", Estado.RECOMENDADO,
				"illumos"));
		ret.add(new Entrada("openindiana", TipoEntrada.SISTEMA_OPERATIVO, "illumos", "OpenIndiana", Estado.RECOMENDADO,
				"openindiana"));
		ret.add(new Entrada("omnios", TipoEntrada.SISTEMA_OPERATIVO, "illumos", "OmniOS", Estado.RECOMENDADO,
				"omnios"));
		ret.add(new Entrada("smartos", TipoEntrada.SISTEMA_OPERATIVO, "illumos", "SmartOS", Estado.RECOMENDADO,
				"smartos"));
		ret.add(new Entrada("tribblix", TipoEntrada.SISTEMA_OPERATIVO, "illumos", "Tribblix", Estado.RECOMENDADO,
				"tribblix"));
		ret.add(new Entrada("dilos", TipoEntrada.SISTEMA_OPERATIVO, "illumos", "DilOS", Estado.RECOMENDADO, "dilos"));
		ret.add(new Entrada("macos", TipoEntrada.SISTEMA_OPERATIVO, "Apple", "macOS / Mac OS X", Estado.NEUTRAL,
				"mac os x", "macos", "darwin"));
		ret.add(new Entrada("aix", TipoEntrada.SISTEMA_OPERATIVO, "IBM UNIX", "IBM AIX", Estado.NEUTRAL, "aix"));
		ret.add(new Entrada("ibm_i", TipoEntrada.SISTEMA_OPERATIVO, "IBM", "IBM i / OS/400", Estado.NEUTRAL, "os/400",
				"ibm i"));
		ret.add(new Entrada("zos", TipoEntrada.SISTEMA_OPERATIVO, "IBM", "IBM z/OS", Estado.NEUTRAL, "z/os", "zos"));
		ret.add(new Entrada("zvm", TipoEntrada.SISTEMA_OPERATIVO, "IBM", "IBM z/VM", Estado.NEUTRAL, "z/vm", "zvm"));
		ret.add(new Entrada("zvse", TipoEntrada.SISTEMA_OPERATIVO, "IBM", "IBM z/VSE", Estado.NEUTRAL, "z/vse",
				"zvse"));
		ret.add(new Entrada("hpux", TipoEntrada.SISTEMA_OPERATIVO, "HP UNIX", "HP-UX", Estado.NEUTRAL, "hp-ux",
				"hpux"));
		ret.add(new Entrada("irix", TipoEntrada.SISTEMA_OPERATIVO, "SGI UNIX", "SGI IRIX", Estado.NEUTRAL, "irix"));
		ret.add(new Entrada("tru64", TipoEntrada.SISTEMA_OPERATIVO, "DEC UNIX", "Tru64 UNIX / Digital UNIX",
				Estado.NEUTRAL, "tru64", "digital unix", "osf1"));
		ret.add(new Entrada("openvms", TipoEntrada.SISTEMA_OPERATIVO, "DEC / VMS", "OpenVMS", Estado.NEUTRAL, "openvms",
				"vms"));
		ret.add(new Entrada("nonstop", TipoEntrada.SISTEMA_OPERATIVO, "HPE", "HPE NonStop OS", Estado.NEUTRAL,
				"nonstop os", "guardian"));
		ret.add(new Entrada("freebsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "FreeBSD", Estado.NEUTRAL, "freebsd"));
		ret.add(new Entrada("openbsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "OpenBSD", Estado.NEUTRAL, "openbsd"));
		ret.add(new Entrada("netbsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "NetBSD", Estado.NEUTRAL, "netbsd"));
		ret.add(new Entrada("dragonflybsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "DragonFly BSD", Estado.NEUTRAL,
				"dragonfly bsd"));
		ret.add(new Entrada("midnightbsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "MidnightBSD", Estado.NEUTRAL,
				"midnightbsd"));
		ret.add(new Entrada("hardenedbsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "HardenedBSD", Estado.NEUTRAL,
				"hardenedbsd"));
		ret.add(new Entrada("ghostbsd", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "GhostBSD", Estado.NEUTRAL, "ghostbsd"));
		ret.add(new Entrada("truenas_core", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "TrueNAS CORE", Estado.NEUTRAL,
				"truenas core", "freenas"));
		ret.add(new Entrada("pfsense", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "pfSense", Estado.NEUTRAL, "pfsense"));
		ret.add(new Entrada("opnsense", TipoEntrada.SISTEMA_OPERATIVO, "BSD", "OPNsense", Estado.NEUTRAL, "opnsense"));
		ret.add(new Entrada("unixware", TipoEntrada.SISTEMA_OPERATIVO, "SCO / Xinuos", "UnixWare", Estado.NEUTRAL,
				"unixware", "unixware 7"));
		ret.add(new Entrada("openunix", TipoEntrada.SISTEMA_OPERATIVO, "SCO / Xinuos", "OpenUNIX", Estado.NEUTRAL,
				"openunix", "open unix"));
		ret.add(new Entrada("openserver", TipoEntrada.SISTEMA_OPERATIVO, "SCO / Xinuos", "OpenServer", Estado.NEUTRAL,
				"openserver", "sco openserver"));
		ret.add(new Entrada("haiku", TipoEntrada.SISTEMA_OPERATIVO, "BeOS family", "Haiku", Estado.NEUTRAL, "haiku"));
		ret.add(new Entrada("beos", TipoEntrada.SISTEMA_OPERATIVO, "BeOS family", "BeOS", Estado.NEUTRAL, "beos"));
		ret.add(new Entrada("serenity", TipoEntrada.SISTEMA_OPERATIVO, "Hobby OS", "SerenityOS", Estado.NEUTRAL,
				"serenityos"));
		ret.add(new Entrada("minix", TipoEntrada.SISTEMA_OPERATIVO, "Research / Unix", "MINIX", Estado.NEUTRAL,
				"minix"));
		ret.add(new Entrada("plan9", TipoEntrada.SISTEMA_OPERATIVO, "Research", "Plan 9", Estado.NEUTRAL, "plan 9"));
		ret.add(new Entrada("inferno", TipoEntrada.SISTEMA_OPERATIVO, "Research", "Inferno", Estado.NEUTRAL,
				"inferno"));
		ret.add(new Entrada("qnx", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "QNX Neutrino", Estado.NEUTRAL, "qnx",
				"qnx neutrino"));
		ret.add(new Entrada("vxworks", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "VxWorks", Estado.NEUTRAL, "vxworks"));
		ret.add(new Entrada("integrity", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "INTEGRITY", Estado.NEUTRAL,
				"integrity"));
		ret.add(new Entrada("lynxos", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "LynxOS", Estado.NEUTRAL, "lynxos"));
		ret.add(new Entrada("pikeos", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "PikeOS", Estado.NEUTRAL, "pikeos"));
		ret.add(new Entrada("rtems", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "RTEMS", Estado.NEUTRAL, "rtems"));
		ret.add(new Entrada("zephyr", TipoEntrada.SISTEMA_OPERATIVO, "RTOS", "Zephyr", Estado.NEUTRAL, "zephyr"));
		ret.add(new Entrada("arcaos", TipoEntrada.SISTEMA_OPERATIVO, "OS/2 family", "ArcaOS", Estado.NEUTRAL,
				"arcaos"));
		ret.add(new Entrada("os2", TipoEntrada.SISTEMA_OPERATIVO, "OS/2 family", "IBM OS/2", Estado.NEUTRAL, "os/2",
				"os2"));
		ret.add(new Entrada("ecomstation", TipoEntrada.SISTEMA_OPERATIVO, "OS/2 family", "eComStation", Estado.NEUTRAL,
				"ecomstation"));
		ret.add(new Entrada("amigaos", TipoEntrada.SISTEMA_OPERATIVO, "Amiga", "AmigaOS", Estado.NEUTRAL, "amigaos",
				"amiga os"));
		ret.add(new Entrada("morphos", TipoEntrada.SISTEMA_OPERATIVO, "Amiga", "MorphOS", Estado.NEUTRAL, "morphos"));
		ret.add(new Entrada("aros", TipoEntrada.SISTEMA_OPERATIVO, "Amiga", "AROS", Estado.NEUTRAL, "aros"));
		ret.add(new Entrada("riscos", TipoEntrada.SISTEMA_OPERATIVO, "Acorn", "RISC OS", Estado.NEUTRAL, "risc os"));
		ret.add(new Entrada("reactos", TipoEntrada.SISTEMA_OPERATIVO, "Windows-compatible", "ReactOS", Estado.NEUTRAL,
				"reactos"));
		ret.add(new Entrada("free_dos", TipoEntrada.SISTEMA_OPERATIVO, "DOS", "FreeDOS", Estado.NEUTRAL, "freedos"));
		ret.add(new Entrada("msdos", TipoEntrada.SISTEMA_OPERATIVO, "DOS", "MS-DOS", Estado.NEUTRAL, "ms-dos",
				"msdos"));
		ret.add(new Entrada("cp_m", TipoEntrada.SISTEMA_OPERATIVO, "Classic", "CP/M", Estado.NEUTRAL, "cp/m"));
		ret.add(new Entrada("nextstep", TipoEntrada.SISTEMA_OPERATIVO, "NeXT", "NeXTSTEP / OPENSTEP", Estado.NEUTRAL,
				"nextstep", "openstep"));
		ret.add(new Entrada("sunos4", TipoEntrada.SISTEMA_OPERATIVO, "Sun UNIX", "SunOS 4", Estado.NEUTRAL, "sunos 4"));
		ret.add(new Entrada("ultrix", TipoEntrada.SISTEMA_OPERATIVO, "DEC UNIX", "ULTRIX", Estado.NEUTRAL, "ultrix"));
		ret.add(new Entrada("xenix", TipoEntrada.SISTEMA_OPERATIVO, "Microsoft/SCO UNIX", "Xenix", Estado.NEUTRAL,
				"xenix"));
		ret.add(new Entrada("domain_os", TipoEntrada.SISTEMA_OPERATIVO, "Apollo", "Domain/OS", Estado.NEUTRAL,
				"domain/os"));
		ret.add(new Entrada("bs2000", TipoEntrada.SISTEMA_OPERATIVO, "Fujitsu", "BS2000", Estado.NEUTRAL, "bs2000"));
		ret.add(new Entrada("acos", TipoEntrada.SISTEMA_OPERATIVO, "NEC", "NEC ACOS", Estado.NEUTRAL, "acos"));
		ret.add(new Entrada("mcp", TipoEntrada.SISTEMA_OPERATIVO, "Unisys", "Unisys MCP", Estado.NEUTRAL,
				"unisys mcp"));
		ret.add(new Entrada("os2200", TipoEntrada.SISTEMA_OPERATIVO, "Unisys", "Unisys OS 2200", Estado.NEUTRAL,
				"os 2200"));
		ret.add(new Entrada("windows_11", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows 11",
				Estado.DESACONSEJADO, "windows 11"));
		ret.add(new Entrada("windows_10", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows 10",
				Estado.DESACONSEJADO, "windows 10"));
		ret.add(new Entrada("windows_8_1", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows 8.1",
				Estado.DESACONSEJADO, "windows 8.1"));
		ret.add(new Entrada("windows_8", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows 8",
				Estado.DESACONSEJADO, "windows 8"));
		ret.add(new Entrada("windows_7", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows 7",
				Estado.DESACONSEJADO, "windows 7"));
		ret.add(new Entrada("windows_vista", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows",
				"Microslop Windows Vista", Estado.DESACONSEJADO, "windows vista"));
		ret.add(new Entrada("windows_xp", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows XP",
				Estado.DESACONSEJADO, "windows xp"));
		ret.add(new Entrada("windows_2000", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows",
				"Microslop Windows 2000", Estado.DESACONSEJADO, "windows 2000"));
		ret.add(new Entrada("windows_nt", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows NT",
				Estado.DESACONSEJADO, "windows nt"));
		ret.add(new Entrada("windows_server_2025", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2025", Estado.DESACONSEJADO, "windows server 2025"));
		ret.add(new Entrada("windows_server_2022", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2022", Estado.DESACONSEJADO, "windows server 2022"));
		ret.add(new Entrada("windows_server_2019", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2019", Estado.DESACONSEJADO, "windows server 2019"));
		ret.add(new Entrada("windows_server_2016", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2016", Estado.DESACONSEJADO, "windows server 2016"));
		ret.add(new Entrada("windows_server_2012", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2012 / 2012 R2", Estado.DESACONSEJADO, "windows server 2012"));
		ret.add(new Entrada("windows_server_2008", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2008 / 2008 R2", Estado.DESACONSEJADO, "windows server 2008"));
		ret.add(new Entrada("windows_server_2003", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows Server",
				"Microslop Windows Server 2003", Estado.DESACONSEJADO, "windows server 2003"));
		ret.add(new Entrada("windows_arm", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows",
				"Microslop Windows on ARM", Estado.DESACONSEJADO, "windows on arm"));
		ret.add(new Entrada("windows_iot", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows IoT",
				Estado.DESACONSEJADO, "windows iot"));
		ret.add(new Entrada("windows_generic", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows",
				"Microslop Windows (generic)", Estado.DESACONSEJADO, "microsoft windows", "windows"));
		ret.add(new Entrada("solaris_10_old", TipoEntrada.SISTEMA_OPERATIVO, "Solaris", "Oracle Solaris 10",
				Estado.DESACONSEJADO, "sunos 5.10", "solaris 10"));
		ret.add(new Entrada("sco_unix_old", TipoEntrada.SISTEMA_OPERATIVO, "SCO", "SCO UNIX System V",
				Estado.DESACONSEJADO, "sco unix", "sco_sv"));
		ret.add(new Entrada("sysv_old", TipoEntrada.SISTEMA_OPERATIVO, "UNIX", "AT&T UNIX System V",
				Estado.DESACONSEJADO, "unix system v", "system v"));
		ret.add(new Entrada("a_ux", TipoEntrada.SISTEMA_OPERATIVO, "Apple UNIX", "A/UX", Estado.DESACONSEJADO, "a/ux"));
		ret.add(new Entrada("machten", TipoEntrada.SISTEMA_OPERATIVO, "Classic Mac UNIX", "MachTen",
				Estado.DESACONSEJADO, "machten"));
		ret.add(new Entrada("netware", TipoEntrada.SISTEMA_OPERATIVO, "Novell", "Novell NetWare", Estado.DESACONSEJADO,
				"netware"));
		ret.add(new Entrada("windows_ce", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows", "Microslop Windows CE",
				Estado.DESACONSEJADO, "windows ce"));
		ret.add(new Entrada("windows_phone", TipoEntrada.SISTEMA_OPERATIVO, "Microslop Windows",
				"Microslop Windows Phone / Mobile", Estado.DESACONSEJADO, "windows phone", "windows mobile"));
		return Collections.unmodifiableList(ret);
	}

	private static List<Entrada> crearArquitecturas() {
		List<Entrada> ret = new ArrayList<Entrada>();
		ret.add(new Entrada("sparcv9", TipoEntrada.ARQUITECTURA, "SPARC", "SPARC V9 / sparcv9", Estado.RECOMENDADO,
				"sparcv9", "sparc64"));
		ret.add(new Entrada("sparc", TipoEntrada.ARQUITECTURA, "SPARC", "SPARC 32-bit", Estado.DESACONSEJADO, "sparc"));
		ret.add(new Entrada("ppc64le", TipoEntrada.ARQUITECTURA, "POWER", "PowerPC 64-bit little-endian",
				Estado.RECOMENDADO, "ppc64le", "powerpc64le"));
		ret.add(new Entrada("ppc64", TipoEntrada.ARQUITECTURA, "POWER", "PowerPC 64-bit big-endian", Estado.NEUTRAL,
				"ppc64", "powerpc64"));
		ret.add(new Entrada("ppc", TipoEntrada.ARQUITECTURA, "PowerPC", "PowerPC 32-bit", Estado.DESACONSEJADO, "ppc",
				"powerpc"));
		ret.add(new Entrada("s390x", TipoEntrada.ARQUITECTURA, "IBM Z", "IBM Z / s390x", Estado.RECOMENDADO, "s390x"));
		ret.add(new Entrada("s390", TipoEntrada.ARQUITECTURA, "IBM Z", "IBM S/390 31-bit", Estado.DESACONSEJADO,
				"s390"));
		ret.add(new Entrada("amd64", TipoEntrada.ARQUITECTURA, "x86-64", "AMD64 / x86-64", Estado.RECOMENDADO, "amd64",
				"x86_64", "x86-64"));
		ret.add(new Entrada("x86", TipoEntrada.ARQUITECTURA, "x86", "x86 32-bit", Estado.DESACONSEJADO, "x86", "i386",
				"i486", "i586", "i686"));
		ret.add(new Entrada("ia64", TipoEntrada.ARQUITECTURA, "Itanium", "IA-64 / Itanium", Estado.DESACONSEJADO,
				"ia64", "itanium"));
		ret.add(new Entrada("aarch64", TipoEntrada.ARQUITECTURA, "Arm", "AArch64 / Arm64", Estado.RECOMENDADO,
				"aarch64", "arm64"));
		ret.add(new Entrada("arm32", TipoEntrada.ARQUITECTURA, "Arm", "Arm 32-bit", Estado.DESACONSEJADO, "armv7",
				"armv6", "arm"));
		ret.add(new Entrada("riscv64", TipoEntrada.ARQUITECTURA, "RISC-V", "RISC-V 64-bit", Estado.NEUTRAL, "riscv64",
				"rv64"));
		ret.add(new Entrada("riscv32", TipoEntrada.ARQUITECTURA, "RISC-V", "RISC-V 32-bit", Estado.DESACONSEJADO,
				"riscv32", "rv32"));
		ret.add(new Entrada("loongarch64", TipoEntrada.ARQUITECTURA, "LoongArch", "LoongArch64", Estado.NEUTRAL,
				"loongarch64"));
		ret.add(new Entrada("mips64", TipoEntrada.ARQUITECTURA, "MIPS", "MIPS64", Estado.NEUTRAL, "mips64"));
		ret.add(new Entrada("mips", TipoEntrada.ARQUITECTURA, "MIPS", "MIPS 32-bit", Estado.DESACONSEJADO, "mips"));
		ret.add(new Entrada("parisc64", TipoEntrada.ARQUITECTURA, "PA-RISC", "PA-RISC 2.0 64-bit", Estado.NEUTRAL,
				"parisc64", "hppa64"));
		ret.add(new Entrada("parisc", TipoEntrada.ARQUITECTURA, "PA-RISC", "PA-RISC / HPPA", Estado.DESACONSEJADO,
				"parisc", "hppa"));
		ret.add(new Entrada("alpha", TipoEntrada.ARQUITECTURA, "DEC Alpha", "DEC Alpha", Estado.NEUTRAL, "alpha"));
		ret.add(new Entrada("m68k", TipoEntrada.ARQUITECTURA, "Motorola 68k", "Motorola 680x0", Estado.DESACONSEJADO,
				"m68k", "m68000"));
		ret.add(new Entrada("vax", TipoEntrada.ARQUITECTURA, "DEC VAX", "DEC VAX", Estado.DESACONSEJADO, "vax"));
		ret.add(new Entrada("sh4", TipoEntrada.ARQUITECTURA, "SuperH", "SuperH SH-4", Estado.DESACONSEJADO, "sh4",
				"sh-4"));
		ret.add(new Entrada("e2k", TipoEntrada.ARQUITECTURA, "Elbrus", "Elbrus E2K", Estado.NEUTRAL, "e2k"));
		return Collections.unmodifiableList(ret);
	}

	private static List<Entrada> crearProcesadores() {
		List<Entrada> ret = new ArrayList<Entrada>();
		ret.add(new Entrada("intel_core_ultra_3", TipoEntrada.CPU, "Intel Core Ultra", "Intel Core Ultra Series 3",
				Estado.RECOMENDADO, "core ultra series 3", "core ultra x9 3", "core ultra x7 3", "core ultra 9 3",
				"core ultra 7 3", "core ultra 5 3"));
		ret.add(new Entrada("intel_core_ultra_2", TipoEntrada.CPU, "Intel Core Ultra",
				"Intel Core Ultra Series 2 / Arrow Lake", Estado.RECOMENDADO, "core ultra series 2", "core ultra 9 2",
				"core ultra 7 2", "core ultra 5 2", "arrow lake"));
		ret.add(new Entrada("intel_core_ultra_1", TipoEntrada.CPU, "Intel Core Ultra",
				"Intel Core Ultra Series 1 / Meteor Lake", Estado.NEUTRAL, "core ultra series 1", "core ultra 9 1",
				"core ultra 7 1", "core ultra 5 1", "meteor lake"));
		ret.add(new Entrada("intel_core_15", TipoEntrada.CPU, "Intel Core",
				"Intel Core 15th Generation (informal Arrow Lake label)", Estado.RECOMENDADO, "15th gen intel core",
				"i9-15", "i7-15", "i5-15", "i3-15"));
		ret.add(new Entrada("intel_core_14", TipoEntrada.CPU, "Intel Core",
				"Intel Core 14th Generation / Raptor Lake Refresh", Estado.NEUTRAL, "14th gen intel core", "i9-14",
				"i7-14", "i5-14", "i3-14", "raptor lake refresh"));
		ret.add(new Entrada("intel_core_13", TipoEntrada.CPU, "Intel Core", "Intel Core 13th Generation / Raptor Lake",
				Estado.DESACONSEJADO, "13th gen intel core", "i9-13", "i7-13", "i5-13", "i3-13", "raptor lake"));
		ret.add(new Entrada("intel_core_12", TipoEntrada.CPU, "Intel Core", "Intel Core 12th Generation / Alder Lake",
				Estado.NEUTRAL, "12th gen intel core", "i9-12", "i7-12", "i5-12", "i3-12", "alder lake"));
		ret.add(new Entrada("intel_core_11", TipoEntrada.CPU, "Intel Core",
				"Intel Core 11th Generation / Rocket Lake, Tiger Lake", Estado.NEUTRAL, "11th gen intel core", "i9-11",
				"i7-11", "i5-11", "i3-11", "rocket lake", "tiger lake"));
		ret.add(new Entrada("intel_core_10", TipoEntrada.CPU, "Intel Core",
				"Intel Core 10th Generation / Comet Lake, Ice Lake", Estado.NEUTRAL, "10th gen intel core", "i9-10",
				"i7-10", "i5-10", "i3-10", "comet lake", "ice lake"));
		ret.add(new Entrada("intel_core_9", TipoEntrada.CPU, "Intel Core",
				"Intel Core 9th Generation / Coffee Lake Refresh", Estado.DESACONSEJADO, "9th gen intel core", "i9-9",
				"i7-9", "i5-9", "i3-9", "coffee lake refresh"));
		ret.add(new Entrada("intel_core_8", TipoEntrada.CPU, "Intel Core",
				"Intel Core 8th Generation / Coffee Lake, Kaby Lake R", Estado.DESACONSEJADO, "8th gen intel core",
				"i7-8", "i5-8", "i3-8", "coffee lake", "kaby lake r"));
		ret.add(new Entrada("intel_core_7", TipoEntrada.CPU, "Intel Core", "Intel Core 7th Generation / Kaby Lake",
				Estado.DESACONSEJADO, "7th gen intel core", "i7-7", "i5-7", "i3-7", "kaby lake"));
		ret.add(new Entrada("intel_core_6", TipoEntrada.CPU, "Intel Core", "Intel Core 6th Generation / Skylake",
				Estado.DESACONSEJADO, "6th gen intel core", "i7-6", "i5-6", "i3-6", "skylake"));
		ret.add(new Entrada("intel_core_5", TipoEntrada.CPU, "Intel Core", "Intel Core 5th Generation / Broadwell",
				Estado.DESACONSEJADO, "5th gen intel core", "i7-5", "i5-5", "i3-5", "broadwell"));
		ret.add(new Entrada("intel_core_4", TipoEntrada.CPU, "Intel Core", "Intel Core 4th Generation / Haswell",
				Estado.DESACONSEJADO, "4th gen intel core", "i7-4", "i5-4", "i3-4", "haswell"));
		ret.add(new Entrada("intel_core_3", TipoEntrada.CPU, "Intel Core", "Intel Core 3rd Generation / Ivy Bridge",
				Estado.DESACONSEJADO, "3rd gen intel core", "i7-3", "i5-3", "i3-3", "ivy bridge"));
		ret.add(new Entrada("intel_core_2gen", TipoEntrada.CPU, "Intel Core",
				"Intel Core 2nd Generation / Sandy Bridge", Estado.DESACONSEJADO, "2nd gen intel core", "i7-2", "i5-2",
				"i3-2", "sandy bridge"));
		ret.add(new Entrada("intel_core_1gen", TipoEntrada.CPU, "Intel Core",
				"Intel Core 1st Generation / Nehalem, Westmere", Estado.DESACONSEJADO, "1st gen intel core", "nehalem",
				"westmere"));
		ret.add(new Entrada("intel_core2", TipoEntrada.CPU, "Intel Core", "Intel Core 2 Duo / Quad / Extreme",
				Estado.DESACONSEJADO, "core 2 duo", "core 2 quad", "core 2 extreme"));
		ret.add(new Entrada("intel_core_old", TipoEntrada.CPU, "Intel Core", "Intel Core Duo / Solo",
				Estado.DESACONSEJADO, "core duo", "core solo"));
		ret.add(new Entrada("xeon_6_clearwater", TipoEntrada.CPU, "Intel Xeon 6",
				"Intel Xeon 6 E-core / Clearwater Forest", Estado.RECOMENDADO, "clearwater forest", "xeon 6"));
		ret.add(new Entrada("xeon_6_granite", TipoEntrada.CPU, "Intel Xeon 6", "Intel Xeon 6 P-core / Granite Rapids",
				Estado.RECOMENDADO, "granite rapids", "xeon 6900p", "xeon 6700p", "xeon 6500p"));
		ret.add(new Entrada("xeon_6_sierra", TipoEntrada.CPU, "Intel Xeon 6", "Intel Xeon 6 E-core / Sierra Forest",
				Estado.RECOMENDADO, "sierra forest", "xeon 6700e", "xeon 6500e"));
		ret.add(new Entrada("xeon_emerald", TipoEntrada.CPU, "Intel Xeon Scalable",
				"5th Gen Xeon Scalable / Emerald Rapids", Estado.RECOMENDADO, "emerald rapids",
				"5th gen xeon scalable"));
		ret.add(new Entrada("xeon_sapphire", TipoEntrada.CPU, "Intel Xeon Scalable",
				"4th Gen Xeon Scalable / Sapphire Rapids", Estado.RECOMENDADO, "sapphire rapids",
				"4th gen xeon scalable"));
		ret.add(new Entrada("xeon_ice_lake", TipoEntrada.CPU, "Intel Xeon Scalable",
				"3rd Gen Xeon Scalable / Ice Lake-SP", Estado.NEUTRAL, "ice lake-sp", "ice lake sp",
				"3rd gen xeon scalable"));
		ret.add(new Entrada("xeon_cooper", TipoEntrada.CPU, "Intel Xeon Scalable",
				"3rd Gen Xeon Scalable / Cooper Lake", Estado.NEUTRAL, "cooper lake"));
		ret.add(new Entrada("xeon_cascade", TipoEntrada.CPU, "Intel Xeon Scalable",
				"2nd Gen Xeon Scalable / Cascade Lake", Estado.NEUTRAL, "cascade lake", "2nd gen xeon scalable"));
		ret.add(new Entrada("xeon_skylake_sp", TipoEntrada.CPU, "Intel Xeon Scalable",
				"1st Gen Xeon Scalable / Skylake-SP", Estado.NEUTRAL, "skylake-sp", "skylake sp",
				"1st gen xeon scalable"));
		ret.add(new Entrada("xeon_broadwell", TipoEntrada.CPU, "Intel Xeon", "Xeon E5/E7 v4 / Broadwell-EP",
				Estado.DESACONSEJADO, "broadwell-ep", "xeon e5-26 v4", "xeon e7 v4"));
		ret.add(new Entrada("xeon_haswell", TipoEntrada.CPU, "Intel Xeon", "Xeon E5/E7 v3 / Haswell-EP",
				Estado.DESACONSEJADO, "haswell-ep", "xeon e5-26 v3", "xeon e7 v3"));
		ret.add(new Entrada("xeon_ivy", TipoEntrada.CPU, "Intel Xeon", "Xeon E5/E7 v2 / Ivy Bridge-EP",
				Estado.DESACONSEJADO, "ivy bridge-ep", "xeon e5-26 v2"));
		ret.add(new Entrada("xeon_sandy", TipoEntrada.CPU, "Intel Xeon", "Xeon E5 v1 / Sandy Bridge-EP",
				Estado.DESACONSEJADO, "sandy bridge-ep", "xeon e5-26"));
		ret.add(new Entrada("xeon_westmere", TipoEntrada.CPU, "Intel Xeon", "Xeon 5600/7500 / Westmere-EP/EX",
				Estado.DESACONSEJADO, "xeon 5600", "xeon 7500", "westmere-ep", "westmere-ex"));
		ret.add(new Entrada("xeon_nehalem", TipoEntrada.CPU, "Intel Xeon", "Xeon 5500/6500/7500 / Nehalem-EP/EX",
				Estado.DESACONSEJADO, "xeon 5500", "nehalem-ep", "nehalem-ex"));
		ret.add(new Entrada("xeon_core2", TipoEntrada.CPU, "Intel Xeon", "Xeon 3000/5000/7000 Core-era",
				Estado.DESACONSEJADO, "woodcrest", "clovertown", "harpertown", "dunnington"));
		ret.add(new Entrada("xeon_netburst", TipoEntrada.CPU, "Intel Xeon", "Xeon NetBurst / Foster, Prestonia, Nocona",
				Estado.DESACONSEJADO, "foster", "prestonia", "nocona", "paxville", "tulsa"));
		ret.add(new Entrada("xeon_phi", TipoEntrada.CPU, "Intel Xeon Phi", "Intel Xeon Phi / Knights family",
				Estado.DESACONSEJADO, "xeon phi", "knights corner", "knights landing", "knights mill"));
		ret.add(new Entrada("itanium_kittson", TipoEntrada.CPU, "Intel Itanium", "Itanium 9700 Kittson", Estado.NEUTRAL,
				"kittson", "itanium 9700"));
		ret.add(new Entrada("itanium_poulson", TipoEntrada.CPU, "Intel Itanium", "Itanium 9500 Poulson",
				Estado.DESACONSEJADO, "poulson", "itanium 9500"));
		ret.add(new Entrada("itanium_tukwila", TipoEntrada.CPU, "Intel Itanium", "Itanium 9300 Tukwila",
				Estado.DESACONSEJADO, "tukwila", "itanium 9300"));
		ret.add(new Entrada("itanium_montvale", TipoEntrada.CPU, "Intel Itanium", "Itanium 9100 Montvale",
				Estado.DESACONSEJADO, "montvale", "itanium 9100"));
		ret.add(new Entrada("itanium_montecito", TipoEntrada.CPU, "Intel Itanium", "Itanium 9000 Montecito",
				Estado.DESACONSEJADO, "montecito", "itanium 9000"));
		ret.add(new Entrada("itanium_madison", TipoEntrada.CPU, "Intel Itanium", "Itanium 2 Madison / Deerfield",
				Estado.DESACONSEJADO, "madison", "deerfield", "itanium 2"));
		ret.add(new Entrada("itanium_mckinley", TipoEntrada.CPU, "Intel Itanium", "Itanium 2 McKinley",
				Estado.DESACONSEJADO, "mckinley"));
		ret.add(new Entrada("itanium_merced", TipoEntrada.CPU, "Intel Itanium", "Itanium Merced", Estado.DESACONSEJADO,
				"merced", "itanium"));
		ret.add(new Entrada("intel_atom_tremont", TipoEntrada.CPU, "Intel Atom",
				"Intel Atom Tremont / Jasper Lake / Elkhart Lake", Estado.NEUTRAL, "tremont", "jasper lake",
				"elkhart lake"));
		ret.add(new Entrada("intel_atom_goldmont", TipoEntrada.CPU, "Intel Atom",
				"Intel Atom Goldmont / Apollo Lake / Gemini Lake", Estado.DESACONSEJADO, "goldmont", "apollo lake",
				"gemini lake"));
		ret.add(new Entrada("intel_atom_silvermont", TipoEntrada.CPU, "Intel Atom",
				"Intel Atom Silvermont / Bay Trail / Avoton", Estado.DESACONSEJADO, "silvermont", "bay trail",
				"avoton"));
		ret.add(new Entrada("intel_atom_bonnell", TipoEntrada.CPU, "Intel Atom", "Intel Atom Bonnell / Saltwell",
				Estado.DESACONSEJADO, "bonnell", "saltwell", "atom n", "atom d"));
		ret.add(new Entrada("pentium4", TipoEntrada.CPU, "Intel legacy", "Pentium 4 / Pentium D / NetBurst",
				Estado.DESACONSEJADO, "pentium 4", "pentium d", "netburst"));
		ret.add(new Entrada("pentium3", TipoEntrada.CPU, "Intel legacy", "Pentium III", Estado.DESACONSEJADO,
				"pentium iii"));
		ret.add(new Entrada("pentium2", TipoEntrada.CPU, "Intel legacy", "Pentium II", Estado.DESACONSEJADO,
				"pentium ii"));
		ret.add(new Entrada("pentium_pro", TipoEntrada.CPU, "Intel legacy", "Pentium Pro", Estado.DESACONSEJADO,
				"pentium pro"));
		ret.add(new Entrada("pentium_m", TipoEntrada.CPU, "Intel legacy", "Pentium M", Estado.DESACONSEJADO,
				"pentium m"));
		ret.add(new Entrada("intel_486", TipoEntrada.CPU, "Intel legacy", "Intel 80486", Estado.DESACONSEJADO, "80486",
				"i486"));
		ret.add(new Entrada("intel_386", TipoEntrada.CPU, "Intel legacy", "Intel 80386", Estado.DESACONSEJADO, "80386",
				"i386"));
		ret.add(new Entrada("amd_zen5", TipoEntrada.CPU, "AMD Zen", "AMD Zen 5 / Ryzen 9000 / Ryzen AI 300",
				Estado.RECOMENDADO, "zen 5", "ryzen 9000", "ryzen ai 300"));
		ret.add(new Entrada("amd_zen4", TipoEntrada.CPU, "AMD Zen", "AMD Zen 4 / Ryzen 7000-8000", Estado.RECOMENDADO,
				"zen 4", "ryzen 7000", "ryzen 8000"));
		ret.add(new Entrada("amd_zen3", TipoEntrada.CPU, "AMD Zen", "AMD Zen 3 / Ryzen 5000", Estado.RECOMENDADO,
				"zen 3", "ryzen 5000"));
		ret.add(new Entrada("amd_zen2", TipoEntrada.CPU, "AMD Zen", "AMD Zen 2 / Ryzen 3000", Estado.NEUTRAL, "zen 2",
				"ryzen 3000"));
		ret.add(new Entrada("amd_zenplus", TipoEntrada.CPU, "AMD Zen", "AMD Zen+ / Ryzen 2000", Estado.NEUTRAL, "zen+",
				"zen plus", "ryzen 2000"));
		ret.add(new Entrada("amd_zen1", TipoEntrada.CPU, "AMD Zen", "AMD Zen 1 / Ryzen 1000", Estado.NEUTRAL, "zen 1",
				"ryzen 1000"));
		ret.add(new Entrada("epyc_9005", TipoEntrada.CPU, "AMD EPYC", "5th Gen AMD EPYC 9005 / Turin / Zen 5",
				Estado.RECOMENDADO, "epyc 9005", "turin"));
		ret.add(new Entrada("epyc_9004", TipoEntrada.CPU, "AMD EPYC", "4th Gen AMD EPYC 9004 / Genoa, Bergamo, Siena",
				Estado.RECOMENDADO, "epyc 9004", "genoa", "bergamo", "siena"));
		ret.add(new Entrada("epyc_7003", TipoEntrada.CPU, "AMD EPYC", "3rd Gen AMD EPYC 7003 / Milan",
				Estado.RECOMENDADO, "epyc 7003", "milan"));
		ret.add(new Entrada("epyc_7002", TipoEntrada.CPU, "AMD EPYC", "2nd Gen AMD EPYC 7002 / Rome", Estado.NEUTRAL,
				"epyc 7002", "rome"));
		ret.add(new Entrada("epyc_7001", TipoEntrada.CPU, "AMD EPYC", "1st Gen AMD EPYC 7001 / Naples", Estado.NEUTRAL,
				"epyc 7001", "naples"));
		ret.add(new Entrada("threadripper_7000", TipoEntrada.CPU, "AMD Threadripper",
				"Ryzen Threadripper 7000 / PRO 7000 WX", Estado.RECOMENDADO, "threadripper 7000"));
		ret.add(new Entrada("threadripper_5000", TipoEntrada.CPU, "AMD Threadripper", "Ryzen Threadripper PRO 5000 WX",
				Estado.RECOMENDADO, "threadripper pro 5000"));
		ret.add(new Entrada("amd_excavator", TipoEntrada.CPU, "AMD legacy", "AMD Excavator", Estado.DESACONSEJADO,
				"excavator"));
		ret.add(new Entrada("amd_steamroller", TipoEntrada.CPU, "AMD legacy", "AMD Steamroller", Estado.DESACONSEJADO,
				"steamroller"));
		ret.add(new Entrada("amd_piledriver", TipoEntrada.CPU, "AMD legacy", "AMD Piledriver", Estado.DESACONSEJADO,
				"piledriver"));
		ret.add(new Entrada("amd_bulldozer", TipoEntrada.CPU, "AMD legacy", "AMD Bulldozer", Estado.DESACONSEJADO,
				"bulldozer"));
		ret.add(new Entrada("amd_jaguar", TipoEntrada.CPU, "AMD low-power", "AMD Jaguar / Puma", Estado.DESACONSEJADO,
				"jaguar", "puma"));
		ret.add(new Entrada("amd_bobcat", TipoEntrada.CPU, "AMD low-power", "AMD Bobcat", Estado.DESACONSEJADO,
				"bobcat"));
		ret.add(new Entrada("amd_k10", TipoEntrada.CPU, "AMD legacy", "AMD K10 / Phenom II", Estado.DESACONSEJADO,
				"phenom ii", "amd k10"));
		ret.add(new Entrada("amd_k8", TipoEntrada.CPU, "AMD legacy", "AMD K8 / Athlon 64", Estado.DESACONSEJADO,
				"athlon 64", "amd k8"));
		ret.add(new Entrada("amd_athlon_xp", TipoEntrada.CPU, "AMD legacy", "AMD Athlon XP / K7", Estado.DESACONSEJADO,
				"athlon xp", "amd k7"));
		ret.add(new Entrada("amd_k6", TipoEntrada.CPU, "AMD legacy", "AMD K6", Estado.DESACONSEJADO, "amd-k6",
				"amd k6"));
		ret.add(new Entrada("amd_k5", TipoEntrada.CPU, "AMD legacy", "AMD K5", Estado.DESACONSEJADO, "amd-k5",
				"amd k5"));
		ret.add(new Entrada("opteron_zen", TipoEntrada.CPU, "AMD Opteron", "AMD Opteron X3000 / Zen-derived embedded",
				Estado.NEUTRAL, "opteron x3000"));
		ret.add(new Entrada("opteron_6300", TipoEntrada.CPU, "AMD Opteron", "AMD Opteron 6300 / Abu Dhabi",
				Estado.DESACONSEJADO, "opteron 6300", "abu dhabi"));
		ret.add(new Entrada("opteron_6200", TipoEntrada.CPU, "AMD Opteron", "AMD Opteron 6200 / Interlagos",
				Estado.DESACONSEJADO, "opteron 6200", "interlagos"));
		ret.add(new Entrada("opteron_6100", TipoEntrada.CPU, "AMD Opteron", "AMD Opteron 6100 / Magny-Cours",
				Estado.DESACONSEJADO, "opteron 6100", "magny-cours"));
		ret.add(new Entrada("opteron_k8", TipoEntrada.CPU, "AMD Opteron", "AMD Opteron K8 generations",
				Estado.DESACONSEJADO, "opteron"));
		ret.add(new Entrada("sparc_m8", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC M8 / T8", Estado.RECOMENDADO,
				"sparc m8", "sparc t8", "sun4v cpu sparc m8"));
		ret.add(new Entrada("sparc_m7", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC M7 / T7", Estado.RECOMENDADO,
				"sparc m7", "sparc t7"));
		ret.add(new Entrada("sparc_s7", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC S7", Estado.RECOMENDADO,
				"sparc s7"));
		ret.add(new Entrada("sparc_m6", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC M6", Estado.NEUTRAL,
				"sparc m6"));
		ret.add(new Entrada("sparc_m5", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC M5", Estado.NEUTRAL,
				"sparc m5"));
		ret.add(new Entrada("sparc_t5", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC T5", Estado.NEUTRAL,
				"sparc t5"));
		ret.add(new Entrada("sparc_t4", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC T4", Estado.NEUTRAL,
				"sparc t4"));
		ret.add(new Entrada("sparc_t3", TipoEntrada.CPU, "Oracle SPARC", "Oracle SPARC T3", Estado.DESACONSEJADO,
				"sparc t3"));
		ret.add(new Entrada("ultrasparc_t2plus", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC T2+", Estado.DESACONSEJADO,
				"ultrasparc t2+", "ultrasparc-t2+"));
		ret.add(new Entrada("ultrasparc_t2", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC T2", Estado.DESACONSEJADO,
				"ultrasparc t2", "ultrasparc-t2"));
		ret.add(new Entrada("ultrasparc_t1", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC T1", Estado.DESACONSEJADO,
				"ultrasparc t1", "ultrasparc-t1"));
		ret.add(new Entrada("ultrasparc_iv", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC IV / IV+", Estado.DESACONSEJADO,
				"ultrasparc iv", "ultrasparc iv+"));
		ret.add(new Entrada("ultrasparc_iii", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC III / IIIi / III+",
				Estado.DESACONSEJADO, "ultrasparc iii", "ultrasparc iiii"));
		ret.add(new Entrada("ultrasparc_ii", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC II family", Estado.DESACONSEJADO,
				"ultrasparc ii"));
		ret.add(new Entrada("ultrasparc_i", TipoEntrada.CPU, "Sun SPARC", "UltraSPARC I", Estado.DESACONSEJADO,
				"ultrasparc i"));
		ret.add(new Entrada("supersparc", TipoEntrada.CPU, "Sun SPARC", "SuperSPARC", Estado.DESACONSEJADO,
				"supersparc"));
		ret.add(new Entrada("hypersparc", TipoEntrada.CPU, "Ross SPARC", "hyperSPARC", Estado.DESACONSEJADO,
				"hypersparc"));
		ret.add(new Entrada("sparc64_xii", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 XII / SPARC M12",
				Estado.RECOMENDADO, "sparc64 xii", "sparc m12"));
		ret.add(new Entrada("sparc64_xifx", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 XIfx / PRIMEHPC FX100",
				Estado.RECOMENDADO, "sparc64 xifx", "primehpc fx100"));
		ret.add(new Entrada("sparc64_xplus", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 X+ / SPARC M10",
				Estado.RECOMENDADO, "sparc64 x+", "sparc m10"));
		ret.add(new Entrada("sparc64_x", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 X", Estado.NEUTRAL,
				"sparc64 x"));
		ret.add(new Entrada("sparc64_vii_plus", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 VII+",
				Estado.NEUTRAL, "sparc64 vii+"));
		ret.add(new Entrada("sparc64_vii", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 VII / VIIfx",
				Estado.DESACONSEJADO, "sparc64 vii", "sparc64 viifx"));
		ret.add(new Entrada("sparc64_vi", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 VI",
				Estado.DESACONSEJADO, "sparc64 vi"));
		ret.add(new Entrada("sparc64_v", TipoEntrada.CPU, "Fujitsu SPARC64", "Fujitsu SPARC64 V / V+",
				Estado.DESACONSEJADO, "sparc64 v"));
		ret.add(new Entrada("power11", TipoEntrada.CPU, "IBM POWER", "IBM POWER11", Estado.RECOMENDADO, "power11",
				"power 11"));
		ret.add(new Entrada("power10", TipoEntrada.CPU, "IBM POWER", "IBM POWER10", Estado.RECOMENDADO, "power10",
				"power 10"));
		ret.add(new Entrada("power9", TipoEntrada.CPU, "IBM POWER", "IBM POWER9", Estado.RECOMENDADO, "power9",
				"power 9"));
		ret.add(new Entrada("power8", TipoEntrada.CPU, "IBM POWER", "IBM POWER8", Estado.NEUTRAL, "power8", "power 8"));
		ret.add(new Entrada("power7plus", TipoEntrada.CPU, "IBM POWER", "IBM POWER7+", Estado.NEUTRAL, "power7+",
				"power 7+"));
		ret.add(new Entrada("power7", TipoEntrada.CPU, "IBM POWER", "IBM POWER7", Estado.DESACONSEJADO, "power7",
				"power 7"));
		ret.add(new Entrada("power6", TipoEntrada.CPU, "IBM POWER", "IBM POWER6 / POWER6+", Estado.DESACONSEJADO,
				"power6", "power 6"));
		ret.add(new Entrada("power5", TipoEntrada.CPU, "IBM POWER", "IBM POWER5 / POWER5+", Estado.DESACONSEJADO,
				"power5", "power 5"));
		ret.add(new Entrada("power4", TipoEntrada.CPU, "IBM POWER", "IBM POWER4 / POWER4+", Estado.DESACONSEJADO,
				"power4", "power 4"));
		ret.add(new Entrada("powerpc_970", TipoEntrada.CPU, "IBM / Apple PowerPC", "PowerPC 970 / G5",
				Estado.DESACONSEJADO, "powerpc 970", "ppc970", "g5"));
		ret.add(new Entrada("powerpc_g4", TipoEntrada.CPU, "Motorola PowerPC", "PowerPC G4 / 74xx",
				Estado.DESACONSEJADO, "powerpc g4", "ppc 74"));
		ret.add(new Entrada("powerpc_g3", TipoEntrada.CPU, "Motorola/IBM PowerPC", "PowerPC G3 / 7xx",
				Estado.DESACONSEJADO, "powerpc g3", "ppc 7"));
		ret.add(new Entrada("cell_be", TipoEntrada.CPU, "IBM Cell", "Cell Broadband Engine", Estado.DESACONSEJADO,
				"cell broadband engine", "cell be"));
		ret.add(new Entrada("ibm_z17", TipoEntrada.CPU, "IBM Z", "IBM z17 / Telum II", Estado.RECOMENDADO, "z17",
				"telum ii", "telum 2"));
		ret.add(new Entrada("ibm_z16", TipoEntrada.CPU, "IBM Z", "IBM z16 / Telum", Estado.RECOMENDADO, "z16",
				"telum"));
		ret.add(new Entrada("ibm_z15", TipoEntrada.CPU, "IBM Z", "IBM z15", Estado.RECOMENDADO, "z15"));
		ret.add(new Entrada("ibm_z14", TipoEntrada.CPU, "IBM Z", "IBM z14", Estado.NEUTRAL, "z14"));
		ret.add(new Entrada("ibm_z13", TipoEntrada.CPU, "IBM Z", "IBM z13", Estado.NEUTRAL, "z13"));
		ret.add(new Entrada("ibm_zec12", TipoEntrada.CPU, "IBM Z", "IBM zEnterprise EC12 / BC12", Estado.DESACONSEJADO,
				"zec12", "zbc12"));
		ret.add(new Entrada("ibm_z196", TipoEntrada.CPU, "IBM Z", "IBM zEnterprise 196 / 114", Estado.DESACONSEJADO,
				"z196", "z114"));
		ret.add(new Entrada("ibm_z10", TipoEntrada.CPU, "IBM Z", "IBM System z10", Estado.DESACONSEJADO, "system z10",
				"z10"));
		ret.add(new Entrada("ibm_z9", TipoEntrada.CPU, "IBM Z", "IBM System z9", Estado.DESACONSEJADO, "system z9",
				"z9"));
		ret.add(new Entrada("ibm_z900", TipoEntrada.CPU, "IBM Z", "IBM z900 / z990", Estado.DESACONSEJADO, "z900",
				"z990"));
		ret.add(new Entrada("parisc_8800", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-8800 / PA-8900 (HP 9000)",
				Estado.NEUTRAL, "pa-8800", "pa 8800", "pa-8900", "pa 8900"));
		ret.add(new Entrada("parisc_8700", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-8700 / PA-8700+ (HP 9000)",
				Estado.DESACONSEJADO, "pa-8700", "pa 8700"));
		ret.add(new Entrada("parisc_8600", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-8600 (HP 9000)",
				Estado.DESACONSEJADO, "pa-8600", "pa 8600"));
		ret.add(new Entrada("parisc_8500", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-8500 / PA-8500+ (HP 9000)",
				Estado.DESACONSEJADO, "pa-8500", "pa 8500"));
		ret.add(new Entrada("parisc_8200", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-8200 (HP 9000)",
				Estado.DESACONSEJADO, "pa-8200", "pa 8200"));
		ret.add(new Entrada("parisc_8000", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-8000 (HP 9000)",
				Estado.DESACONSEJADO, "pa-8000", "pa 8000"));
		ret.add(new Entrada("parisc_7300", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-7300LC (HP 9000)",
				Estado.DESACONSEJADO, "pa-7300", "pa 7300"));
		ret.add(new Entrada("parisc_7200", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-7200 (HP 9000)",
				Estado.DESACONSEJADO, "pa-7200", "pa 7200"));
		ret.add(new Entrada("parisc_7100", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "PA-7100 / PA-7150 (HP 9000)",
				Estado.DESACONSEJADO, "pa-7100", "pa 7100", "pa-7150"));
		ret.add(new Entrada("hp9000_generic", TipoEntrada.CPU, "HP PA-RISC / HP 9000", "HP 9000 PA-RISC (generic)",
				Estado.DESACONSEJADO, "hp 9000", "pa-risc", "parisc"));
		ret.add(new Entrada("alpha_ev7", TipoEntrada.CPU, "DEC Alpha", "Alpha 21364 EV7 / EV7z", Estado.NEUTRAL,
				"alpha 21364", "ev7"));
		ret.add(new Entrada("alpha_ev6", TipoEntrada.CPU, "DEC Alpha", "Alpha 21264 EV6 family", Estado.DESACONSEJADO,
				"alpha 21264", "ev6"));
		ret.add(new Entrada("alpha_ev5", TipoEntrada.CPU, "DEC Alpha", "Alpha 21164 EV5 family", Estado.DESACONSEJADO,
				"alpha 21164", "ev5"));
		ret.add(new Entrada("alpha_ev4", TipoEntrada.CPU, "DEC Alpha", "Alpha 21064 EV4 family", Estado.DESACONSEJADO,
				"alpha 21064", "ev4"));
		ret.add(new Entrada("mips_r16000", TipoEntrada.CPU, "MIPS / SGI", "MIPS R16000", Estado.NEUTRAL, "r16000"));
		ret.add(new Entrada("mips_r14000", TipoEntrada.CPU, "MIPS / SGI", "MIPS R14000", Estado.DESACONSEJADO,
				"r14000"));
		ret.add(new Entrada("mips_r12000", TipoEntrada.CPU, "MIPS / SGI", "MIPS R12000", Estado.DESACONSEJADO,
				"r12000"));
		ret.add(new Entrada("mips_r10000", TipoEntrada.CPU, "MIPS / SGI", "MIPS R10000", Estado.DESACONSEJADO,
				"r10000"));
		ret.add(new Entrada("mips_r8000", TipoEntrada.CPU, "MIPS / SGI", "MIPS R8000", Estado.DESACONSEJADO, "r8000"));
		ret.add(new Entrada("mips_r5000", TipoEntrada.CPU, "MIPS", "MIPS R5000", Estado.DESACONSEJADO, "r5000"));
		ret.add(new Entrada("mips_r4000", TipoEntrada.CPU, "MIPS", "MIPS R4000 / R4400 / R4600", Estado.DESACONSEJADO,
				"r4000", "r4400", "r4600"));
		ret.add(new Entrada("mips_r3000", TipoEntrada.CPU, "MIPS", "MIPS R3000", Estado.DESACONSEJADO, "r3000"));
		ret.add(new Entrada("mips_r2000", TipoEntrada.CPU, "MIPS", "MIPS R2000", Estado.DESACONSEJADO, "r2000"));
		ret.add(new Entrada("octeon3", TipoEntrada.CPU, "Cavium MIPS", "Cavium OCTEON III", Estado.NEUTRAL,
				"octeon iii", "octeon3"));
		ret.add(new Entrada("octeon2", TipoEntrada.CPU, "Cavium MIPS", "Cavium OCTEON II", Estado.DESACONSEJADO,
				"octeon ii", "octeon2"));
		ret.add(new Entrada("apple_m5", TipoEntrada.CPU, "Apple Silicon", "Apple M5", Estado.RECOMENDADO, "apple m5",
				" m5 "));
		ret.add(new Entrada("apple_m4", TipoEntrada.CPU, "Apple Silicon", "Apple M4", Estado.RECOMENDADO, "apple m4",
				" m4 "));
		ret.add(new Entrada("apple_m3", TipoEntrada.CPU, "Apple Silicon", "Apple M3", Estado.RECOMENDADO, "apple m3",
				" m3 "));
		ret.add(new Entrada("apple_m2", TipoEntrada.CPU, "Apple Silicon", "Apple M2", Estado.NEUTRAL, "apple m2",
				" m2 "));
		ret.add(new Entrada("apple_m1", TipoEntrada.CPU, "Apple Silicon", "Apple M1", Estado.NEUTRAL, "apple m1",
				" m1 "));
		ret.add(new Entrada("neoverse_v3", TipoEntrada.CPU, "Arm Neoverse", "Arm Neoverse V3", Estado.RECOMENDADO,
				"neoverse v3"));
		ret.add(new Entrada("neoverse_n3", TipoEntrada.CPU, "Arm Neoverse", "Arm Neoverse N3", Estado.RECOMENDADO,
				"neoverse n3"));
		ret.add(new Entrada("neoverse_v2", TipoEntrada.CPU, "Arm Neoverse", "Arm Neoverse V2", Estado.RECOMENDADO,
				"neoverse v2"));
		ret.add(new Entrada("neoverse_n2", TipoEntrada.CPU, "Arm Neoverse", "Arm Neoverse N2", Estado.RECOMENDADO,
				"neoverse n2"));
		ret.add(new Entrada("neoverse_v1", TipoEntrada.CPU, "Arm Neoverse", "Arm Neoverse V1", Estado.NEUTRAL,
				"neoverse v1"));
		ret.add(new Entrada("neoverse_n1", TipoEntrada.CPU, "Arm Neoverse", "Arm Neoverse N1", Estado.NEUTRAL,
				"neoverse n1"));
		ret.add(new Entrada("cortex_x925", TipoEntrada.CPU, "Arm Cortex-X", "Arm Cortex-X925", Estado.RECOMENDADO,
				"cortex-x925", "cortex x925"));
		ret.add(new Entrada("cortex_x4", TipoEntrada.CPU, "Arm Cortex-X", "Arm Cortex-X4", Estado.RECOMENDADO,
				"cortex-x4", "cortex x4"));
		ret.add(new Entrada("cortex_x3", TipoEntrada.CPU, "Arm Cortex-X", "Arm Cortex-X3", Estado.RECOMENDADO,
				"cortex-x3", "cortex x3"));
		ret.add(new Entrada("cortex_x2", TipoEntrada.CPU, "Arm Cortex-X", "Arm Cortex-X2", Estado.NEUTRAL, "cortex-x2",
				"cortex x2"));
		ret.add(new Entrada("cortex_x1", TipoEntrada.CPU, "Arm Cortex-X", "Arm Cortex-X1", Estado.NEUTRAL, "cortex-x1",
				"cortex x1"));
		ret.add(new Entrada("cortex_a725", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A725", Estado.RECOMENDADO,
				"cortex-a725", "cortex a725"));
		ret.add(new Entrada("cortex_a720", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A720", Estado.RECOMENDADO,
				"cortex-a720", "cortex a720"));
		ret.add(new Entrada("cortex_a715", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A715", Estado.NEUTRAL,
				"cortex-a715", "cortex a715"));
		ret.add(new Entrada("cortex_a710", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A710", Estado.NEUTRAL,
				"cortex-a710", "cortex a710"));
		ret.add(new Entrada("cortex_a78", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A78", Estado.NEUTRAL,
				"cortex-a78", "cortex a78"));
		ret.add(new Entrada("cortex_a77", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A77", Estado.NEUTRAL,
				"cortex-a77", "cortex a77"));
		ret.add(new Entrada("cortex_a76", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A76", Estado.NEUTRAL,
				"cortex-a76", "cortex a76"));
		ret.add(new Entrada("cortex_a75", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A75", Estado.DESACONSEJADO,
				"cortex-a75", "cortex a75"));
		ret.add(new Entrada("cortex_a73", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A73", Estado.DESACONSEJADO,
				"cortex-a73", "cortex a73"));
		ret.add(new Entrada("cortex_a72", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A72", Estado.DESACONSEJADO,
				"cortex-a72", "cortex a72"));
		ret.add(new Entrada("cortex_a57", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A57", Estado.DESACONSEJADO,
				"cortex-a57", "cortex a57"));
		ret.add(new Entrada("cortex_a55", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A55", Estado.DESACONSEJADO,
				"cortex-a55", "cortex a55"));
		ret.add(new Entrada("cortex_a53", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A53", Estado.DESACONSEJADO,
				"cortex-a53", "cortex a53"));
		ret.add(new Entrada("cortex_a15", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A15", Estado.DESACONSEJADO,
				"cortex-a15", "cortex a15"));
		ret.add(new Entrada("cortex_a9", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A9", Estado.DESACONSEJADO,
				"cortex-a9", "cortex a9"));
		ret.add(new Entrada("cortex_a8", TipoEntrada.CPU, "Arm Cortex-A", "Arm Cortex-A8", Estado.DESACONSEJADO,
				"cortex-a8", "cortex a8"));
		ret.add(new Entrada("ampere_one", TipoEntrada.CPU, "Ampere Arm", "AmpereOne", Estado.RECOMENDADO, "ampereone"));
		ret.add(new Entrada("ampere_altra", TipoEntrada.CPU, "Ampere Arm", "Ampere Altra / Altra Max", Estado.NEUTRAL,
				"ampere altra"));
		ret.add(new Entrada("aws_graviton4", TipoEntrada.CPU, "AWS Arm", "AWS Graviton4", Estado.RECOMENDADO,
				"graviton4", "graviton 4"));
		ret.add(new Entrada("aws_graviton3", TipoEntrada.CPU, "AWS Arm", "AWS Graviton3", Estado.RECOMENDADO,
				"graviton3", "graviton 3"));
		ret.add(new Entrada("aws_graviton2", TipoEntrada.CPU, "AWS Arm", "AWS Graviton2", Estado.NEUTRAL, "graviton2",
				"graviton 2"));
		ret.add(new Entrada("fujitsu_a64fx", TipoEntrada.CPU, "Fujitsu Arm", "Fujitsu A64FX", Estado.RECOMENDADO,
				"a64fx"));
		ret.add(new Entrada("riscv_p670", TipoEntrada.CPU, "RISC-V", "SiFive Performance P670", Estado.RECOMENDADO,
				"sifive p670", "performance p670"));
		ret.add(new Entrada("riscv_p550", TipoEntrada.CPU, "RISC-V", "SiFive Performance P550", Estado.NEUTRAL,
				"sifive p550", "performance p550"));
		ret.add(new Entrada("riscv_u74", TipoEntrada.CPU, "RISC-V", "SiFive U74", Estado.NEUTRAL, "sifive u74"));
		ret.add(new Entrada("riscv_u54", TipoEntrada.CPU, "RISC-V", "SiFive U54", Estado.DESACONSEJADO, "sifive u54"));
		ret.add(new Entrada("riscv_veyron", TipoEntrada.CPU, "RISC-V", "Ventana Veyron", Estado.RECOMENDADO,
				"ventana veyron"));
		ret.add(new Entrada("loongson_3a6000", TipoEntrada.CPU, "LoongArch", "Loongson 3A6000 / LA664",
				Estado.RECOMENDADO, "3a6000", "la664"));
		ret.add(new Entrada("loongson_3a5000", TipoEntrada.CPU, "LoongArch", "Loongson 3A5000 / LA464", Estado.NEUTRAL,
				"3a5000", "la464"));
		ret.add(new Entrada("elbrus_8sv", TipoEntrada.CPU, "Elbrus", "Elbrus-8SV / E2K", Estado.NEUTRAL, "elbrus-8sv",
				"elbrus 8sv"));
		ret.add(new Entrada("elbrus_old", TipoEntrada.CPU, "Elbrus", "Older Elbrus E2K", Estado.DESACONSEJADO,
				"elbrus"));
		ret.add(new Entrada("transmeta_efficeon", TipoEntrada.CPU, "Transmeta", "Transmeta Efficeon",
				Estado.DESACONSEJADO, "efficeon"));
		ret.add(new Entrada("transmeta_crusoe", TipoEntrada.CPU, "Transmeta", "Transmeta Crusoe", Estado.DESACONSEJADO,
				"crusoe"));
		ret.add(new Entrada("via_nano", TipoEntrada.CPU, "VIA x86", "VIA Nano / Isaiah", Estado.DESACONSEJADO,
				"via nano", "isaiah"));
		ret.add(new Entrada("via_c7", TipoEntrada.CPU, "VIA x86", "VIA C7", Estado.DESACONSEJADO, "via c7"));
		ret.add(new Entrada("via_c3", TipoEntrada.CPU, "VIA x86", "VIA C3", Estado.DESACONSEJADO, "via c3"));
		ret.add(new Entrada("cyrix_6x86", TipoEntrada.CPU, "Cyrix x86", "Cyrix 6x86 / MII", Estado.DESACONSEJADO,
				"cyrix 6x86", "cyrix mii"));
		ret.add(new Entrada("motorola_68060", TipoEntrada.CPU, "Motorola 68k", "Motorola 68060", Estado.DESACONSEJADO,
				"68060"));
		ret.add(new Entrada("motorola_68040", TipoEntrada.CPU, "Motorola 68k", "Motorola 68040", Estado.DESACONSEJADO,
				"68040"));
		ret.add(new Entrada("motorola_68030", TipoEntrada.CPU, "Motorola 68k", "Motorola 68030", Estado.DESACONSEJADO,
				"68030"));
		ret.add(new Entrada("motorola_68020", TipoEntrada.CPU, "Motorola 68k", "Motorola 68020", Estado.DESACONSEJADO,
				"68020"));
		ret.add(new Entrada("motorola_68000", TipoEntrada.CPU, "Motorola 68k", "Motorola 68000 / 68010",
				Estado.DESACONSEJADO, "68000", "68010"));
		ret.add(new Entrada("superh_sh4", TipoEntrada.CPU, "SuperH", "Hitachi/Renesas SH-4", Estado.DESACONSEJADO,
				"sh-4", "sh4"));
		ret.add(new Entrada("vax_generic", TipoEntrada.CPU, "DEC VAX", "DEC VAX family", Estado.DESACONSEJADO, "vax"));
		ret.add(new Entrada("z80", TipoEntrada.CPU, "Zilog", "Zilog Z80", Estado.DESACONSEJADO, "z80"));
		ret.add(new Entrada("mos6502", TipoEntrada.CPU, "MOS", "MOS 6502 family", Estado.DESACONSEJADO, "6502"));
		ret.add(new Entrada("intel_xeon_generic", TipoEntrada.CPU, "Intel Xeon", "Intel Xeon (generic family)",
				Estado.NEUTRAL, "xeon"));
		ret.add(new Entrada("intel_core_generic", TipoEntrada.CPU, "Intel Core", "Intel Core (generic family)",
				Estado.NEUTRAL, "intel core", "core tm"));
		ret.add(new Entrada("intel_core_ultra_generic", TipoEntrada.CPU, "Intel Core Ultra",
				"Intel Core Ultra (generic family)", Estado.RECOMENDADO, "core ultra"));
		ret.add(new Entrada("intel_atom_generic", TipoEntrada.CPU, "Intel Atom", "Intel Atom (generic family)",
				Estado.DESACONSEJADO, "atom"));
		ret.add(new Entrada("itanium_generic", TipoEntrada.CPU, "Intel Itanium", "Intel Itanium (generic family)",
				Estado.DESACONSEJADO, "itanium", "ia64"));
		ret.add(new Entrada("amd_epyc_generic", TipoEntrada.CPU, "AMD EPYC", "AMD EPYC (generic family)",
				Estado.NEUTRAL, "epyc"));
		ret.add(new Entrada("amd_ryzen_generic", TipoEntrada.CPU, "AMD Ryzen", "AMD Ryzen (generic family)",
				Estado.NEUTRAL, "ryzen"));
		ret.add(new Entrada("amd_threadripper_generic", TipoEntrada.CPU, "AMD Threadripper",
				"AMD Ryzen Threadripper (generic family)", Estado.NEUTRAL, "threadripper"));
		ret.add(new Entrada("amd_opteron_generic", TipoEntrada.CPU, "AMD Opteron", "AMD Opteron (generic family)",
				Estado.DESACONSEJADO, "opteron"));
		ret.add(new Entrada("sparc_generic", TipoEntrada.CPU, "SPARC", "SPARC processor (generic family)",
				Estado.NEUTRAL, "sparc"));
		ret.add(new Entrada("fujitsu_sparc64_generic", TipoEntrada.CPU, "Fujitsu SPARC64",
				"Fujitsu SPARC64 (generic family)", Estado.NEUTRAL, "sparc64"));
		ret.add(new Entrada("ibm_power_generic", TipoEntrada.CPU, "IBM POWER", "IBM POWER processor (generic family)",
				Estado.NEUTRAL, "powerpc", "ibm power"));
		ret.add(new Entrada("ibm_z_generic", TipoEntrada.CPU, "IBM Z", "IBM Z / zSeries processor (generic family)",
				Estado.NEUTRAL, "zseries", "s390x"));
		ret.add(new Entrada("parisc_generic", TipoEntrada.CPU, "HP PA-RISC", "HP PA-RISC / HP 9000 (generic family)",
				Estado.DESACONSEJADO, "pa risc", "parisc", "hp 9000"));
		ret.add(new Entrada("apple_m_generic", TipoEntrada.CPU, "Apple Silicon", "Apple M-series (generic family)",
				Estado.RECOMENDADO, "apple silicon", "apple m"));
		ret.add(new Entrada("arm_generic", TipoEntrada.CPU, "Arm", "Arm processor (generic family)", Estado.NEUTRAL,
				"aarch64", "arm64"));
		ret.add(new Entrada("mips_generic", TipoEntrada.CPU, "MIPS", "MIPS processor (generic family)", Estado.NEUTRAL,
				"mips"));
		ret.add(new Entrada("riscv_generic", TipoEntrada.CPU, "RISC-V", "RISC-V processor (generic family)",
				Estado.NEUTRAL, "riscv", "risc v"));
		return Collections.unmodifiableList(ret);
	}
}
