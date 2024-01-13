package E3CeteExt12345;

/**
 * La classe E3CeteBase.Carte représente une carte possèdant une figure répétée un certain nombre de fois avec une texture et une couleur.
 * On a besoin de connaître :
 * - La figure représentée,
 * - Le nombre de fois où la figure est représentée,
 * - La couleur de la figure,
 * - La texture de la figure.
 */
public class Carte  {

    //VARIABLES D'INSTANCE
    private Figure figure;
    private int nbFigures;
    private Couleur couleur;
    private Texture texture;

    //VARIABLES DE CLASSE
    private static final int hauteur = 5;
    private static final int largeur = 9;


    /**
     * Pre-requis : nbFigures > 0
     * Action : Construit une carte contenant nbFigures "figures" qui possèdent une "texture" et une "couleur"
     * Exemple : new E3CeteBase.Carte(E3CeteBase.Couleur.ROUGE, 2, E3CeteBase.Figure.OVALE, E3CeteBase.Texture.PLEIN) représente une carte contenant 2 figures ovales rouge et pleines
     */

    public Carte(Couleur couleur, int nbFigures, Figure figure, Texture texture) {
        this.couleur = couleur;
        this.nbFigures = nbFigures;
        this.figure = figure;
        this.texture = texture;
    }

    /**
     * Résultat : Le nombre de fois où la figure est représentée sur la carte.
     */

    public int getNbFigures() {
        return this.nbFigures;
    }

    /**
     * Résultat : La figure représentée sur la carte.
     */

    public Figure getFigure() {
        return this.figure;
    }

    /**
     * Résultat : La couleur représentée sur les figures de la carte.
     */

    public Couleur getCouleur() {
        return this.couleur;
    }

    /**
     * Résultat : La texture représentée sur les figures de la carte.
     */

    public Texture getTexture() {
        return this.texture;
    }

    /**
     * Action : compare les attributs de "this" et de "carte"
     * afin de déterminer si this est plus petit, égal ou plus grand que "carte"
     *
     * L'odre d'importance des attrbiuts est celui donné dans le constructeur (du plus prioritaire au moins prioritaire) :
     * E3CeteBase.Couleur, nombre de figures, figure, texture.
     * Pour comparer les couleurs, les figures et les textures, on utilisera leur position (ordinal) dans
     * leurs énumérations respectives.
     * Ainsi, pour toute paire {c1,c2} de E3CeteBase.Carte, c1 est inférieure à c2 si et seulement si
     * la valeur de c1 est inférieure à celle de c2 pour la caractéristique ayant la plus grande priorité
     * parmi celles pour lesquelles c1 et c2 ont des valeurs différentes.
     *
     *
     * Résultat :
     *  0 si "this" est égal à "carte"
     *  Un nombre négatif si "this" est inférieur à "carte"
     *  Un nombre strictement positif si "this "est supérieur à "carte"
     */

    public int compareTo(Carte carte) {
        int res;
        res = this.couleur.compareTo(carte.couleur);
        if (res == 0) {
            res = this.compareNbFiguresTo(carte);
            if (res == 0) {
                res = this.figure.compareTo(carte.figure);
                if (res == 0) {
                    res = this.texture.compareTo(carte.texture);
                }
            }
        }
        return res;
    }


    /**
     * FONCTION AJOUTEE
     * Action : compare le nbFigures de this et de carte
     * @param carte une carte
     * @return
     *  0 si "this.nbFigures" égal à "carte.nbFigures"
     * <0 si "this.nbFigures" inférieur à "carte.nbFigures"*
     * >0 si "this.nbFigures" supérieur à "carte.nbFigures"*
     */
    private int compareNbFiguresTo(Carte carte) {
        return this.nbFigures - carte.nbFigures;
    }


    /**
     * Résultat :
     * Une chaîne de caractères représentant la carte de la manière suivante :
     *  - Le texte est coloré selon la couleur de la carte
     *  - La chaîne de caractères retournée doit faire apparaitre toutes les caractériqtiques d'une carte sauf la couleur puisque le texte est affiché en couleur
     *  (Vous devez choisir une représentation agréable pour l'utilisateur)
     */

    @Override
    public String toString() {
        String textureChar = String.valueOf(texture.getTextureChar());
        String bgColor = "\033[47m";
        String cardColor = getCouleur().getCouleurText();
        String bothColor = cardColor + bgColor;
        String textColor = Couleur.getReset() + bgColor + "\u001B[30m";

        String str = (textColor + textureChar + bothColor + getNbFigures() + textColor + textureChar.repeat(8 - String.valueOf(getNbFigures()).length()) + Couleur.getReset() + "\n" +
                textColor + textureChar.repeat(9) + Couleur.getReset() + "\n") +
                textColor + textureChar.repeat(4) + bothColor + getFigure().getFigureChar() + textColor + textureChar.repeat(4) + Couleur.getReset() + "\n" +
                textColor + textureChar.repeat(9) + Couleur.getReset() + "\n" +
                textColor + textureChar.repeat(8 - String.valueOf(getNbFigures()).length()) + bothColor + getNbFigures() + textColor + textureChar + Couleur.getReset();

        return str;
    }

    /*
    * FONCTION AJOUTE
    * Résultat : Hauteur d'affichage d'une carte
     */
    public static int getHauteur() {
        return hauteur;
    }

    /*
    * FONCTION AJOUTE
    * Résultat : Largeur d'affichage d'une carte
     */
    public static int getLargeur() {
        return largeur;
    }

}
