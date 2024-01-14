package E3CeteExt12345;

import utils.Ut;

public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    GRIS_FONCE("Dark Grey", "\u001b[38;5;243m"),
    MARRON("Brown", "\u001b[38;5;94m"),
    POURPRE("Dark Red", "\u001b[38;5;52m"),
    ROUGE("Red", "\033[0;31m"),
    ROUGE_CLAIR("Light Red", "\033[0;91m"),
    ROSE("Pink", "\033[0;95m"),
    FUSHIA("Fushia", "\u001b[38;5;218m"),
    JAUNE_BRIGHT("Yellow Bright", "\u001b[38;5;226m"),
    JAUNE("Yellow", "\033[0;33m"),
    BLANC_CLAIR("Light White", "\033[0;97m"),
    VERT_CLAIR("Light Green", "\033[0;92m"),
    VERT_KAKI("Green Kaki", "\u001b[38;5;106m"),
    VERT("Green", "\033[0;32m"),
    CYAN("Cyan", "\033[0;36m"),
    CYAN_CLAIR("Light Cyan", "\033[0;96m"),
    BLEU_CLAIR("Light Blue", "\033[0;94m"),
    BLEU("Blue", "\033[0;34m"),
    BLEU_FONCE("Dark Blue", "\u001b[38;5;20m"),
    VIOLET("Purple", "\033[0;35m"),
    VIOLET_FONCE("Dark Purple", "\u001b[38;5;57m");


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
