public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    BLEU("blue", "\033[0;34m", "\033[44m"),
    BLANC("white", "\033[0;37m", "\033[47m"),
    ROUGE("red", "\033[0;31m", "\033[41m");

    private String traduction;
    private String codeCouleurText;
    private String codeCouleurBg;

    private static String codeReset = "\033[0m";


    private Couleur(String s, String codeText, String codeBg) {
        this.traduction = s;
        this.codeCouleurText = codeText;
        this.codeCouleurBg = codeBg;
    }



    public String getTraduction() {
        return this.traduction;
    }
    public String getCouleurText() {
        return this.codeCouleurText;
    }
    public String getCouleurBg() {
        return this.codeCouleurBg;
    }

    
    public static String getReset() {
        return codeReset;
    }



}
