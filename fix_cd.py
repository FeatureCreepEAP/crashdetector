import re

def replace_in_file(filepath, old, new):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    if old in content:
        content = content.replace(old, new, 1)
        with open(filepath, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"  OK: {filepath}")
    else:
        print(f"  NOT FOUND in {filepath}: {repr(old[:60])}")

# Ingles.java - curly apostrophe U+2019
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/idioma/Ingles.java',
    '\u2022 The right to use CrashDetector\u2019s built-in log anonymisation feature.',
    '\u2022 The right to use " + Statics.nombre_cd.obtener() + "\u2019s built-in log anonymisation feature.'
)

# DiscordRichPresenceManager.java
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/discord/DiscordRichPresenceManager.java',
    'builder.setState("CrashDetector")',
    'builder.setState(Statics.nombre_cd.obtener())'
)

# StikkedAPI.java
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/api_sito_registro/StikkedAPI.java',
    'String nameEnc = URLEncoder.encode("CrashDetector", StandardCharsets.UTF_8.toString());',
    'String nameEnc = URLEncoder.encode(Statics.nombre_cd.obtener(), StandardCharsets.UTF_8.toString());'
)

# DialogoConflictoImportacionGUI.java - two replacements
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/gui/tipos/importador/DialogoConflictoImportacionGUI.java',
    '"Despues puede ser necesario reiniciar CrashDetector para que entre al classpath."',
    '"Despues puede ser necesario reiniciar " + Statics.nombre_cd.obtener() + " para que entre al classpath."'
)
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/gui/tipos/importador/DialogoConflictoImportacionGUI.java',
    '"Reinicie CrashDetector si la fusion de SNBT todavia dice que falta el motor NBT."',
    '"Reinicie " + Statics.nombre_cd.obtener() + " si la fusion de SNBT todavia dice que falta el motor NBT."'
)

# ConfigsModsGUIYunenoms.java
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/gui/tipos/configmods/ConfigsModsGUIYunenoms.java',
    'mensaje += "\\n\\nReinicie CrashDetector si las clases todavia no aparecen en el classpath.";',
    'mensaje += "\\n\\nReinicie " + Statics.nombre_cd.obtener() + " si las clases todavia no aparecen en el classpath.";'
)

# JGitHubIzzy.java
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/gui/tipos/jgit/JGitHubIzzy.java',
    '"Repositorio creado desde CrashDetector"',
    '"Repositorio creado desde " + Statics.nombre_cd.obtener()'
)

# JGitHubBase.java
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/gui/tipos/jgit/JGitHubBase.java',
    '"Commit manual de CrashDetector"',
    '"Commit manual de " + Statics.nombre_cd.obtener()'
)

# JGitAutoCommit.java
replace_in_file(
    '/Users/macbook/git/CrashDetectorMC/src/main/java/com/asbestosstar/crashdetector/gui/tipos/jgit/JGitAutoCommit.java',
    '"Commit autom\u00e1tico de CrashDetector - " + fecha',
    '"Commit autom\u00e1tico de " + Statics.nombre_cd.obtener() + " - " + fecha'
)

print("Done")
