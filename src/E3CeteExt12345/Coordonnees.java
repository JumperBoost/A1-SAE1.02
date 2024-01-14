package E3CeteExt12345;

import utils.Ut;

public class Coordonnees {

    /**
     * La classe Coordonnees représente les coordonnées (i,j) d'une Carte sur la Table
     * ou i représenta la ligne et j la colonne
     * Cette classe est utilisée uniquement pour intéragir avec l'utilisateur
     **/

    // Variables d'instance
    private int x, y;
    public static String[] lettres = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");


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
        this.x = lettreValide(splited[0]);
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
        return splited.length == 2 && lettreValide(splited[0]) > 0 && Ut.estNombre(splited[1]);
    }

    /**
     * FONCTION AJOUTEE
     * Action : vérifie si le String en paramètre est bien une lettre MAJ ;
     * Si oui, retourne la coordonnée associée à la lettre
     * Ex : A --> 1
     * Sinon retourne 0
     */
    private static int lettreValide(String input) {
        for (int i = 0; i < lettres.length; i++) {
            if (input.equals(lettres[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    public static void setTabLettres(int hauteurTable) {
        if (hauteurTable > lettres.length) {
            int repet = (hauteurTable-1)/lettres.length;
            int indice = 0;
            String[] tempLettres = new String[lettres.length*(repet+1)];
            for (int i = 0; i < lettres.length; i++) {
                tempLettres[indice] = lettres[i];
                for (int j = 1; j <= repet; j++) {
                    tempLettres[indice + j] = lettres[i].repeat(j + 1);
                }
                indice += repet+1;
            }
            lettres = tempLettres;
        }
    }

}
