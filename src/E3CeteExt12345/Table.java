package E3CeteExt12345;

import utils.Ut;

/**
 * La classe Table représente une table de jeu contenant des cartes.
 *
 * La table est représentée graphiquement par une matrice.
 * On peut donc avoir des tables de dimensions 3x3, 3x4, 4x4, 5x5, 10x15...
 * En mémoire, la Table est représentée par un simple tableau (à une dimension)
 * Quand elle est initialisée, la table est vide.
 *
 * Pour désigner une carte sur la table, on utilise des coordonées (i,j) ou i représenta la ligne et j la colonne.
 * Les lignes et colonnes sont numérotés à partir de 1.
 * Les cartes sont numérotées à partir de 1.
 *
 * Par exemple, sur une table 3x3, la carte en position (1,1) est la premiere carte du tableau, soit celle à l'indice 0.
 * La carte (2,1) => carte numéro 4 stockée à l'indice 3  dans le tableau représenatnt la table
 * La carte (3,3) => carte numéro 9 stockée à l'indice 8  dans le tableau représenatnt la table
 */
public class Table {

    // Variables d'instance
    private Carte[] cartes;
    private int hauteur, largeur;

    /**
     * Pre-requis : hauteur >=3, largeur >=3
     *
     * Action : Construit une table "vide" avec les dimensions précisées en paramètre.
     *
     * Exemple : hauteur : 3, largeur : 3 => construit une table 3x3 (pouvant donc accueillir 9 cartes).
     */

    public Table(int hauteur, int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.cartes = new Carte[hauteur * largeur];
    }

    /*
    * FONCTION AJOUTEE
    * Pré-requis : hauteur >= 3, largeur >= 3, paquet.nbCartesLeft >= hauteur * largeur
    * Action : Construit une table rempli avec les dimensions et paquet précisées en paramètre.
    * Exemple : hauteur : 3, largeur : 3 => construit une table 3x3 (avec 9 cartes piochées dans le paquet précisé en paramètre)
     */
    public Table(int hauteur, int largeur, Paquet paquet) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.cartes = paquet.piocher(getTaille());
    }

    /**
     * Résullat : Le nombre de cartes que la table peut stocker.
     */

    public int getTaille() {
        return this.hauteur * this.largeur;
    }

    /**
     * Pre-requis : la table est pleine
     * Action : Affiche des cartes de la table sous forme de matrice
     * L'affichage des cartes doit respecter le format défini dans la classe Carte (chaque carte doit donc être colorée).
     * On ne donne volontairement pas d'exemple puisque celà depend du choix fait pour votre représentation de Carte
     */

    public String toString() {
        int[] selections = new int[getTaille()];
        for(int i = 0; i < getTaille(); i++)
            selections[i] = i+1;
        return recupererSelection(selections);
    }

    /**
     * Résullat : Vrai la carte située aux coordonnées précisées en paramètre est une carte possible pour la table.
     */
    public boolean carteExiste(Coordonnees coordonnees) {
        int index = selectionnerIndex(coordonnees);
        return index >= 0 && index < getTaille();
    }

    /**
     * Pre-requis :
     *  Il reste des cartes sur la table.
     *
     * Action : Fait sélectionner au joueur (par saisie de ses coordonnées) une carte valide (existante) de la table.
     * L'algorithme doit faire recommencer la saisie au joueur s'il ne saisit pas une carte valide.
     *
     * Résullat : Le numéro de carte sélectionné.
     *
     */

    public int faireSelectionneUneCarte() {
        Ut.afficher("Veuillez saisir les coordonnées de la carte que vous souhaitez sélectionner (x,y): ");
        String input = Ut.saisirChaine();
        if(!Coordonnees.formatEstValide(input)) {
            Ut.afficherSL("Format invalide, veuillez réessayer.");
            return faireSelectionneUneCarte();
        }

        Coordonnees coordCarte = new Coordonnees(input);
        if(!carteExiste(coordCarte)) {
            Ut.afficherSL("Cette carte n'existe pas, veuillez réessayer.");
            return faireSelectionneUneCarte();
        }
        return selectionnerIndex(coordCarte)+1;
    }

    /**
     * Pre-requis : 1<=nbCartes <= nombre de Cartes de this
     * Action : Fait sélectionner nbCartes Cartes au joueur  sur la table en le faisant recommencer jusqu'à avoir une sélection valide.
     * Il ne doit pas y avoir de doublons dans les numéros de cartes sélectionnées.
     * Résullat : Un tableau contenant les numéros de cartes sélectionnées.
     */

    public int[] selectionnerCartesJoueur(int nbCartes) {
        int[] selections = new int[nbCartes];
        int index = 0;
        while(index < nbCartes) {
            int selection;
            boolean doublon;
            do {
                selection = faireSelectionneUneCarte();
                doublon = false;
                // Vérification de doublon
                int i = 0;
                while(i < index && !doublon) {
                    if(selections[i++] == selection) {
                        Ut.afficherSL("Vous avez déjà sélectionné cette carte, veuillez en choisir une autre.");
                        doublon = true;
                    }
                }
            } while(doublon);
            selections[index++] = selection;
        }
        return selections;
    }

    /**
     * Action : Affiche les cartes de la table correspondantes aux numéros de cartes contenus dans selection
     * Exemple de format d'affichage : "Sélection : 2-O-H 3-O-H 2-C-H"
     * Les cartes doivent être affichées "colorées"
     */

    public void afficherSelection(int[] selection) {
        Ut.afficherSL(recupererSelection(selection));
    }

    /**
     * Pré-requis : 1 <= nbCartes <= nombre de Cartes de this
     * @param selection
     * @return Le champ de texte représentant les cartes de la table correspondantes aux numéros de cartes contenus dans selection
     */
    private String recupererSelection(int[] selection) {
        int curLigne = 0;
        int curCol = 0;
        String[][] affichageLigne = new String[this.hauteur][Carte.getHauteur()];
        for(int i = 0; i < this.hauteur; i++)
            for(int j = 0; j < Carte.getHauteur(); j++)
                affichageLigne[i][j] = "";

        // Insérer l'affichage de chaque carte dans la matrice d'une table
        for(int index : selection) {
            String[] lignesCarte = cartes[index-1].toString().split("\n");
            for(int i = 0; i < Carte.getHauteur(); i++)
                affichageLigne[curLigne][i] += (curCol != 0 ? " ".repeat(5) : "") + lignesCarte[i];

            // Vérifier si la largeur max de la table est atteint, afin de sauter à la ligne suivante
            if(curCol == this.largeur-1) {
                curLigne++;
                curCol = 0;
            } else curCol++;
        }

        // Formatter l'affichage final
        String affichage = "";
        for(int ligne = 0; ligne < affichageLigne.length; ligne++) {
            String[] lignesCartes = affichageLigne[ligne];
            for(String ligneCartes : lignesCartes)
                if(ligneCartes != "")
                    affichage += ligneCartes + "\n";
            affichage += "\n";
        }
        return affichage;
    }

    /**
    * FONCTION AJOUTEE
    * Action : Détermine l'index de la carte aux coordonnées précisées en paramètre
    * Résultat : L'index de la carte par rapport au tableau de cartes
     */
    public int selectionnerIndex(Coordonnees coordonnees) {
        return (coordonnees.getLigne() - 1) * this.largeur + coordonnees.getColonne() - 1;
    }

    /**
    * FONCTION AJOUTEE
    * Action : Remplace la carte à la position précisée en paramètre par la carte précisée en paramètre
     */
    public void remplacerCarte(Carte carte, int position) {
        this.cartes[position-1] = carte;
    }

    /**
     * FONCTION AJOUTEE
     * Résullat : Le tableau de cartes représentant la table.
     */
    public Carte[] getCartes() {
        return this.cartes;
    }

}
