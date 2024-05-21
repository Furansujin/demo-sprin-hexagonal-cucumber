package com.furansujin.demosprinhexagonalcucumber.domain.entities.commun;

public enum FileType {
    XML(".xml"),
    JAVA(".java"),
    JS(".js"),
    C(".c"),
    CPP(".cpp"),
    H(".h"),
    HPP(".hpp"),
    PY(".py"),
    RB(".rb"),
    GO(".go"),
    RS(".rs"),
    PHP(".php"),
    TS(".ts"),
    CS(".cs"),
    SWIFT(".swift"),
    OBJ_C(".m"),
    OBJ_CPP(".mm"),
    FORTRAN(".f"),
    FORTRAN90(".f90"),
    FORTRAN95(".f95"),
    R(".r"),
    PERL(".pl"),
    PERL_MODULE(".pm"),
    SHELL(".sh"),
    SQL(".sql"),
    HTML(".html"),
    CSS(".css"),
    SCSS(".scss"),
    SASS(".sass"),
    MD(".md"),
    RST(".rst"),
    TXT(".txt"),
    YML(".yml"),
    YAML(".yaml"),
    JSON(".json"),
    INI(".ini"),
    CONF(".conf"),
    KOTLIN(".kt"),
    KOTLIN_SCRIPT(".kts");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static FileType fromExtension(String extension) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getExtension().equalsIgnoreCase(extension)) {
                return fileType;
            }
        }
        throw new IllegalArgumentException("Unsupported extension: " + extension);
    }
}
