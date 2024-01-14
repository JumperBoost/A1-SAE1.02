package E3CeteExt12345;

import utils.Ut;

public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    VIOLET("Purple", "\033[0;35m"),
    ROUGE("Red", "\033[0;31m"),
    VERT("Green", "\033[0;32m"),
    JAUNE("Yellow", "\033[0;33m"),
    ORANGE("Orange", "\033[0;33m"),
    BLEU("Blue", "\033[0;34m"),
    CYAN("Cyan", "\033[0;36m"),
    BLANC("White", "\033[0;37m"),
    ROSE("Pink", "\033[0;95m"),
    GRIS("Gray", "\033[0;90m"),
    ROUGE_CLAIR("Light Red", "\033[0;91m"),
    VERT_CLAIR("Light Green", "\033[0;92m"),
    JAUNE_CLAIR("Light Yellow", "\033[0;93m"),
    BLEU_CLAIR("Light Blue", "\033[0;94m"),
    MAGENTA_CLAIR("Light Magenta", "\033[0;95m"),
    CYAN_CLAIR("Light Cyan", "\033[0;96m"),
    BLANC_CLAIR("Light White", "\033[0;97m"),
    BLEU_MARINE("Navy Blue", "\033[0;34m"),
    JAUNE_OLIVE("Olive Yellow", "\033[0;93m"),
    TURQUOISE("Turquoise", "\033[0;36m");


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


    public static Couleur[] randomValues(int nb) {
        Couleur[] tabCouleurs = new Couleur[nb];
        int index = 0;

        Couleur couleur;
        while (index < nb) {
            couleur = Couleur.values()[Ut.randomMinMax(0, Couleur.getNbEnumTotal()-1)];
            int i = 0;
            while (i < index && tabCouleurs[i] != couleur) {
                i++;
            }

            // Si i == index, alors la couleur n'est pas dans le tableau
            if (i == index) {
                tabCouleurs[index] = couleur;
                index++;
            }
        }
        return tabCouleurs;
    }
}
