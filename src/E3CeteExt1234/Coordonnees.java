package E3CeteExt1234;

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
        this.x = getLettreCoord(splited[0]);
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
        for(int i = 0; i < input.length(); i++) {
            for (int j = 0; j < lettres.length; j++) {
                if (input.charAt(i) == lettres[j].charAt(0)) {
                    return j + 1;
                }
            }
        }
        return 0;
    }

    /**
     * FONCTION AJOUTEE
     * Action : Retourne la coordonnée correspond à/aux lettre(s) en paramètre
     * Ex : AA --> 27
     * Sinon retourne 0
     */
    public static int getLettreCoord(String lettre) {
        if(lettreValide(lettre) == 0)
            return 0;

        int index = 0;
        for(int i = lettre.length()-1; i >= 0; i--) {
            int j = lettreValide(String.valueOf(lettre.charAt(i)));
            index += (int) (j * Math.pow(lettres.length, lettre.length() - i - 1));
        }
        return index;
    }

    /**
     * FONCTION AJOUTEE
     * Action : Retourne la/les lettre(s) correspondant à la coordonnée en paramètre
     * Ex : 26 --> Z, 29 --> AC, 676 --> ZZ, 677 --> AAA
     */
    public static String getLettres(int coord) {
        String res = "";
        int i = 0;
        while(coord > 0) {
            res = lettres[(coord - 1) % lettres.length] + res;
            coord = (coord - 1) / lettres.length;
        }
        return res;
    }

}
