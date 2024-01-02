public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    BLEU("Blue", "\033[0;34m", "\033[44m"),
    BLANC("White", "\033[0;37m", "\033[47m"),
    ROUGE("Red", "\033[0;31m", "\033[41m");

    private String traduction;
    private String codeCouleurText;
    private String codeCouleurBg;

    private static String codeReset = "\033[0m";

    private static int nbEnum = Couleur.values().length;


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

    public static int getNbEnumTotal() {
        return nbEnum;
    }


    /**
     * Action : retourne un tableu de couleurs dont les ordinaux sont compris entre 
     * begin et end(exclus).
     * Prerequis : begin < end
     * 0 <= begin < nb d'ENUM dans la class (Class.values().length ou getNbEnumTotal)
     * 0 < end <= nb d'ENUM dans la class
     * @param begin
     * @param end
     * @return
     */
    public static Couleur[] valuesInRange(int begin, int end) {
        Couleur[] tabCouleurs = new Couleur[end - begin];
        for (int i = begin; i < end; i++) {
            tabCouleurs[i - begin] = Couleur.values()[i];
        }
        return tabCouleurs;
    }



}
