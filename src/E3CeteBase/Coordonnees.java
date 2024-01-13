package E3CeteBase;

import utils.Ut;

public class Coordonnees {

    /**
     * La classe E3CeteBase.Coordonnees représente les coordonnées (i,j) d'une E3CeteBase.Carte sur la E3CeteBase.Table
     * ou i représenta la ligne et j la colonne
     * Cette classe est utilisée uniquement pour intéragir avec l'utilisateur
     **/

    // Variables d'instance
    private int x, y;


    /**
     * Pre-requis : x,y>=0
     * Action : Construit des Coordonnées ayant x comme numéro de ligne et y comme numéro de colonne
     */
    public Coordonnees(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Pre-requis : input est sous la forme  suivante : int,int
     * Action : Construit des Coordonnées ayant x comme numéro de ligne et y comme numéro de colonne
     */
    public Coordonnees(String input) {
        String[] splited = input.split(",");
        //splited est un tableau de String qui contient les sous chaines de caracteres contenues dans input et séparées par ','
        this.x = Integer.parseInt(splited[0]);
        this.y = Integer.parseInt(splited[1]);
    }

    /**
     * Action : Retourne le numéro de la ligne
     */
    public int getLigne() {
        return this.x;
    }

    /**
     * Action : Retourne le numéro de la colonne
     */
    public int getColonne() {
        return this.y;
    }

    /**
     * Pre-requis : aucun
     * Action : Retourne vrai si la variable input est dans un format valide à savoir int,int
     * Aide : On peut utiliser utils.Ut.estNombre pour vérifier qu'une chaîne de caractères est bien un nombre.
     */
    public static boolean formatEstValide(String input) {
        String[] splited = input.split(",");
        return splited.length == 2 && Ut.estNombre(splited[0]) && Ut.estNombre(splited[1]);
    }
}
