package E3CeteExt1234;

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
    private int nbCartesRestantes;

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
        this.nbCartesRestantes = hauteur * largeur;
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
        this.nbCartesRestantes = hauteur * largeur;
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
        return coordonnees.getLigne() <= this.hauteur && coordonnees.getColonne() <= this.largeur
                && coordonnees.getLigne() > 0 && coordonnees.getColonne() > 0 && this.cartes[index] != null;
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
        Ut.afficher("Veuillez saisir les coordonnées (ligne, colonne) de la carte que vous souhaitez sélectionner ex : A,1 --> ");
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
        String bg = "\033[41m";
        int curLigne = 0;
        int curCol = 0;
        int lenHauteur = (int) Math.ceil((double) selection.length / this.largeur);
        String[][] affichageLigne = new String[lenHauteur][Carte.getHauteur()];
        for(int i = 0; i < lenHauteur; i++)
            for(int j = 0; j < Carte.getHauteur(); j++) {
                // Vérifier si on affiche toute la table ou seulement la sélection
                if (selection.length == this.hauteur * this.largeur) {
                    if (j == Carte.getHauteur() / 2) {
                        String letters = Coordonnees.getLettres(i + 1);
                        affichageLigne[i][j] = " ".repeat(3 - letters.length()) + Couleur.JAUNE_BRIGHT.getCouleurText() + letters + Couleur.getReset() + bg + " ";
                    } else {
                        affichageLigne[i][j] = bg + "  " + Couleur.JAUNE_BRIGHT.getCouleurText() + "|" + Couleur.getReset() + bg + " ";
                    }
                } else affichageLigne[i][j] = "   ";
            }

        // Insérer l'affichage de chaque carte dans la matrice d'une table
        for(int index : selection) {
            String[] lignesCarte = cartes[index-1] != null ? cartes[index-1].toString().split("\n") : null;
            // Si la carte n'existe pas, on affiche une carte vide
            if(lignesCarte == null) {
                lignesCarte = new String[Carte.getHauteur()];
                for(int i = 0; i < Carte.getHauteur(); i++)
                    lignesCarte[i] = "\033[47m" + " ".repeat(Carte.getLargeur()) + Couleur.getReset();
            }

            for(int i = 0; i < Carte.getHauteur(); i++)
                affichageLigne[curLigne][i] += (curCol != 0 ?
                        bg + " ".repeat(3) : "")
                        + Couleur.getReset() + lignesCarte[i];

            // Vérifier si la largeur max de la table est atteint, afin de sauter à la ligne suivante
            if(curCol == this.largeur-1) {
                curLigne++;
                curCol = 0;
            } else curCol++;
        }

        // Formatter l'affichage final
        String affichage = bg + " ".repeat((selection.length == this.hauteur * this.largeur ? 4 : 3) + Carte.getLargeur() * this.largeur + 3 * this.largeur)
                + Couleur.getReset() + "\n" + bg;
        if (selection.length == this.hauteur * this.largeur) {
            affichage += " ".repeat(4);
            for (int col = 1; col <= this.largeur; col++) {
                // Afficher les numéros de colonnes proportionnellement à la largeur de la carte
                affichage += Couleur.JAUNE_BRIGHT.getCouleurText() +
                        "-".repeat(5 - String.valueOf(col).length() % 2) + col + "-".repeat(5 - String.valueOf(col).length() % 2) +
                        Couleur.getReset() + bg + " ".repeat(3);
            }
            affichage += Couleur.getReset() + "\n";
        }

        for(int ligne = 0; ligne < affichageLigne.length; ligne++) {
            String[] lignesCartes = affichageLigne[ligne];
            for(String ligneCartes : lignesCartes)
                if (ligneCartes != "") {
                    int lenPlainLigne = ligneCartes.replaceAll("\u001B\\[[;\\d]*m", "").length();
                    int nbCartesRestante = (this.largeur - lenPlainLigne / (Carte.getLargeur() + 3));
                    affichage += bg + ligneCartes + bg + " ".repeat(3 * nbCartesRestante + Carte.getLargeur() * nbCartesRestante)
                            + " ".repeat(3) + Couleur.getReset() + "\n";
                } else affichage += bg + " ".repeat(3 + Carte.getLargeur()) + Couleur.getReset() + "\n";
            affichage += bg + " ".repeat((selection.length == this.hauteur * this.largeur ? 4 : 3) + Carte.getLargeur() * this.largeur + 3 * this.largeur) + Couleur.getReset() + "\n";
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
        if (carte == null)
            this.desincrementerNbCartesRestantes();
    }

    /**
     * FONCTION AJOUTEE
     * Résullat : Le tableau de cartes représentant la table.
     */
    public Carte[] getCartes() {
        return this.cartes;
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public int getLargeur() {
        return this.largeur;
    }

    public int getNbCartesRestantes() {
        return this.nbCartesRestantes;
    }

    public void desincrementerNbCartesRestantes() {
        this.nbCartesRestantes--;
    }

}
