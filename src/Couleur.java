public enum Couleur {

    /**
     * Représente la couleur d'une Carte : jaune, rouge ...
     * En plus de donner une liste énumérative des couleurs possibles,
     * cette enumération doit permettre à la méthode toString d'une Carte de réaliser un affichage en couleur.
     */

    BLEU("blue"),
    BLANC("white"),
    ROUGE("rouge");

    private String traduction;
    private Couleur(String s) {
        this.traduction = s;
    }
    public String getTraduction() {
        return this.traduction;
    }


}
