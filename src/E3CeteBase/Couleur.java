package E3CeteBase;

public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    ROUGE("Red", "\033[0;31m"),
    BLEU("Blue", "\033[0;34m"),
    VERT("Green", "\033[0;32m"),;


    private String traduction;
    private String codeCouleurText;

    private static String codeReset = "\033[0m";

    private static int nbEnum = Couleur.values().length;


    private Couleur(String name, String codeText) {
        this.traduction = name;
        this.codeCouleurText = codeText;
    }



    public String getTraduction() {
        return this.traduction;
    }
    public String getCouleurText() {
        return this.codeCouleurText;
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
