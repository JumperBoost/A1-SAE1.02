package E3CeteExt12345;

import utils.Ut;

/**
 * La classe Jeu permet de faire des parties du jeu "E3Cète" soit avec un humain, soit avec un ordinateur.
 *
 * Règles :
 *
 *  - On possède un paquet de cartes qui représentent entre une et trois figures (losange, carre ou ovale), une texture
 *   (vide, hachuré ou plein) et une couleur (rouge, jaune ou bleu). La cardinalité des énumérations est fixée à 3 pour cette partie 2 de la SAE uniquement.
 *
 *  - Une table 3x3 permet de stocker 9 cartes. Au début de la partie, on dispose 9 cartes sur la table, piochées sur le dessus du paquet.
 *
 *  - A chaque tour, le joueur doit essayer de trouver un E3C.
 *
 *  - Le joueur doit désigner trois cartes par leurs coordonnées.
 *      - Si ces cartes forment un E3C, il gagne trois points sur son score.
 *      - Si ce n'est pas un E3C, il perd 1 point sur son score.
 *
 *   - Les trois cartes sont remplacées par de nouvelles cartes piochées dans le paquet.
 *
 *   - La partie se termine quand il n'y a plus de cartes dans le paquet (même s'il reste des cartes sur la table).
 *
 * On a donc besoin :
 *
 * - D'un paquet pour stocker toutes les cartes et avoir une pioche.
 * - D'une table.
 * - De quoi stocker le score du joueur (humain ou ordinateur).
 */
public class Jeu {

    // Variables d'instance
    private int score;
    private Paquet paquet;
    private Table table;
    private int xSousEnsemble;

    /**
     * Action :
     * - Initialise le jeu "E3Cète" en initialisant le score du joueur, le paquet et la table.
     * - La table doit être remplie.
     */

    public Jeu() {
        this.score = 0;
        this.paquet = new Paquet(Couleur.randomValues(3), 3, Figure.randomValues(3), Texture.randomValues(3));
        this.table = new Table(3, 3, this.paquet);
        this.xSousEnsemble = 3;
    }

    public Jeu(int xSousEnsemble, int hauteur, int largeur) {
        this.score = 0;
        this.paquet = new Paquet(Couleur.randomValues(xSousEnsemble), xSousEnsemble, Figure.randomValues(xSousEnsemble), Texture.randomValues(xSousEnsemble));
        this.table = new Table(hauteur, largeur, this.paquet);
        this.xSousEnsemble = xSousEnsemble;
        Coordonnees.setTabLettres(hauteur);
    }

    /**
     * Action : Pioche autant de cartes qu'il y a de numéros de cartes dans le tableau numerosDeCartes et les place
     * aux positions correspondantes sur la table.
     */

    public void piocherEtPlacerNouvellesCartes(int[] numerosDeCartes) {
        Carte[] cartes = this.paquet.piocher(numerosDeCartes.length);
        for(int i = 0; i < numerosDeCartes.length; i++) {
            this.table.remplacerCarte(cartes[i], numerosDeCartes[i]);
        }
    }

    /**
     * Action : Ré-initialise les données et variables du jeu afin de rejouer une nouvelle partie.
     */

    public void resetJeu() {
        this.score = 0;
        this.paquet = new Paquet(Couleur.randomValues(this.xSousEnsemble), this.xSousEnsemble, Figure.randomValues(this.xSousEnsemble), Texture.randomValues(this.xSousEnsemble));
        this.table = new Table(this.table.getHauteur(), this.table.getLargeur(), this.paquet);
    }

    /**
     * Résullat : Vrai si les cartes passées en paramètre forment un ExC.
     */

    public static boolean estUnExC(Carte[] cartes) {
        Couleur[] couleurs = new Couleur[cartes.length];
        Figure[] figures = new Figure[cartes.length];
        Texture[] textures = new Texture[cartes.length];
        int[] nbFigures = new int[cartes.length];
        int[] indexes = new int[4];

        // Vérifier si les cartes existent
        for(Carte carte : cartes)
            if(carte == null)
                return false;

        // Ajout des caractéristiques des cartes
        for(Carte carte : cartes) {
            ajoutCaracteristique(couleurs, figures, textures, nbFigures, indexes, carte);
        }

        // Vérifier si les caractéristiques sont toutes différentes ou toutes identiques (via l'index: 0 pour la couleur, 1 pour la figure, 2 pour la texture et 3 pour le nombre de figures)
        if((indexes[0] != 1 && indexes[0] != couleurs.length)
        || (indexes[1] != 1 && indexes[1] != figures.length)
        || (indexes[2] != 1 && indexes[2] != textures.length)
        || (indexes[3] != 1 && indexes[3] != nbFigures.length))
            return false;
        return true;
    }

    /**
    * FONCTION AJOUTEE
    * Pré-requis: indexes[0] <= couleurs.length, indexes[1] <= figures.length, indexes[2] <= textures.length, indexes[3] <= nbFigures.length
    * Action : Ajoute les caractéristiques d'une carte dans les tableaux de caractéristiques
    * @param couleurs tableau de couleurs
    * @param figures tableau de figures
    * @param textures tableau de textures
    * @param nbFigures tableau de nombres de figures
    * @param indexes tableau d'index pour chaque tableau de caractéristiques (0 pour la couleur, 1 pour la figure, 2 pour la texture et 3 pour le nombre de figures)
    * @param carte une carte
     */
    private static void ajoutCaracteristique(Couleur[] couleurs, Figure[] figures, Texture[] textures, int[] nbFigures, int[] indexes, Carte carte) {
        boolean peutAjouter = true;
        int i = 0;

        // Ajout de la couleur
        while (peutAjouter && i < couleurs.length && i < indexes[0])
            if(couleurs[i++].compareTo(carte.getCouleur()) == 0)
                peutAjouter = false;
        if(peutAjouter) {
            couleurs[i] = carte.getCouleur();
            indexes[0]++;
        }
        i = 0;
        peutAjouter = true;

        // Ajout de la figure
        while (peutAjouter && i < figures.length && i < indexes[1])
            if(figures[i++].compareTo(carte.getFigure()) == 0)
                peutAjouter = false;
        if(peutAjouter) {
            figures[i] = carte.getFigure();
            indexes[1]++;
        }
        i = 0;
        peutAjouter = true;

        // Ajout de la texture
        while (peutAjouter && i < textures.length && i < indexes[2])
            if(textures[i++].compareTo(carte.getTexture()) == 0)
                peutAjouter = false;
        if(peutAjouter) {
            textures[i] = carte.getTexture();
            indexes[2]++;
        }
        i = 0;
        peutAjouter = true;

        // Ajout du nombre de figures
        while (peutAjouter && i < nbFigures.length && i < indexes[3])
            if(nbFigures[i++] == carte.getNbFigures())
                peutAjouter = false;
        if(peutAjouter) {
            nbFigures[i] = carte.getNbFigures();
            indexes[3]++;
        }
    }

    /**
     * Action : Recherche un ExC parmi les cartes disposées sur la table.
     * Résullat :
     *  - Si un ExC existe, un tableau contenant les numéros de cartes (de la table) qui forment un ExC.
     *  - Sinon, la valeur null.
     */

    public int[] chercherExCSurTableOrdinateur() {
        Carte[] cartes = this.table.getCartes();
        int[] indexes = new int[this.xSousEnsemble];
        for (int i = 0; i < this.xSousEnsemble; i++)
            indexes[i] = i + 1;

        while (indexes != null) {
            // Vérifier si il y a pas de doublons
            boolean doublon = false;
            int i = 0;
            while (i < this.xSousEnsemble && !doublon) {
                int j = i + 1;
                while (j < this.xSousEnsemble && !doublon) {
                    if (indexes[i] == indexes[j])
                        doublon = true;
                    j++;
                }
                i++;
            }

            if (!doublon) {
                Carte[] selCartes = new Carte[this.xSousEnsemble];
                String t = "";
                for (int y = 0; y < this.xSousEnsemble; y++) {
                    selCartes[y] = cartes[indexes[y] - 1];
                }
                if (estUnExC(selCartes))
                    return indexes;
            }

            indexes = incrementerTabLimite(indexes, cartes.length, this.xSousEnsemble - 1);
        }
        return null;
    }

    private int[] incrementerTabLimite(int[] tab, int limite, int index) {
        if(tab[index]+1 > limite) {
            if(index-1 >= 0) {
                tab[index] = Math.min(tab[index - 1] + 1, limite);
                return incrementerTabLimite(tab, limite, index - 1);
            } else return null;
        } else {
            tab[index]++;
            return tab;
        }
    }

    /**
     * Action : Sélectionne alétoirement x cartes sur la table.
     * La sélection ne doit pas contenir de doublons
     * Résultat : un tableau contenant les numéros des cartes sélectionnées alétaoirement
     */

    public int[] selectionAleatoireDeCartesOrdinateur() {
        int[] selections = new int[this.xSousEnsemble];
        int index = 0;
        while(index < this.xSousEnsemble) {
            int selection;
            boolean refaire;
            do {
                selection = Ut.randomMinMax(1, this.table.getTaille());
                refaire = false;
                // Vérification d'existence
                if(this.table.getCartes()[selection-1] == null)
                    refaire = true;

                // Vérification de doublon
                int i = 0;
                while(i < index && !refaire) {
                    if(selections[i++] == selection)
                        refaire = true;
                }
            } while(refaire);
            selections[index++] = selection;
        }
        return selections;
    }

    /**
     * Résullat : Vrai si la partie en cours est terminée.
     */

    public boolean partieEstTerminee() {
        return this.paquet.estVide() && this.table.getNbCartesRestantes() < this.xSousEnsemble && chercherExCSurTableOrdinateur() == null;
    }

    /**
     * Action : Fait jouer un tour à un joueur humain.
     * La Table et le score du joueur sont affichés.
     * Le joueur sélectionne 3 cartes.
     *  - Si c'est un E3C, il gagne trois points.
     *  - Sinon, il perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouerTourHumain() {
        Ut.afficherSL("Table de jeu :\n" + this.table.toString());
        Ut.afficherSL("Score : " + this.score);
        Ut.sautLigne();
        Ut.afficherSL("Veuillez sélectionner 3 cartes formant un E3C :");

        int[] selection = this.table.selectionnerCartesJoueur(3);
        Ut.afficherSL("Vous avez sélectionné les cartes suivantes :");
        this.table.afficherSelection(selection);
        Carte[] cartes = this.table.getCartes();
        Carte[] selCartes = new Carte[] {cartes[selection[0]-1], cartes[selection[1]-1], cartes[selection[2]-1]};

        if(estUnExC(selCartes)) {
            Ut.afficherSL("C'est un E3C ! Vous gagnez 3 points.");
            this.score += 3;
        } else {
            Ut.afficherSL("Ce n'est pas un E3C ! Vous perdez 1 point.");
            this.score -= 1;
        }
        // Remplacer les cartes
        this.piocherEtPlacerNouvellesCartes(selection);
    }

    /**
     * Action : Fait jouer une partie à un joueur humain.
     * A la fin, le score final du joueur est affiché.
     */

    public void jouerHumain() {
        while(!partieEstTerminee())
            jouerTourHumain();
        Ut.afficherSL("Partie terminée ! Votre score final est de " + this.score + " points.");
    }

    /**
     * Action : Fait jouer un tour à l'ordinateur.
     * La Table et le score de l'ordinateur sont affichés.
     * L'ordinateur sélectionne des cartes :
     *  - L'ordinateur essaye toujours de trouver un E3C sur la table. S'il en trouve un, il gagne donc trois points.
     *  - S'il n'en trouve pas, il se rabat sur 3 cartes sélectionnées aléatoirement et perd un point.
     * Les cartes sélectionnées sont remplacées.
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void joueurTourOrdinateur() {
        Ut.afficherSL("Table de jeu :\n" + this.table.toString());
        Ut.afficherSL("Score : " + this.score);
        Ut.sautLigne();
        Ut.afficherSL("L'ordinateur sélectionne 3 cartes formant un E3C :");

        int[] selection = this.chercherExCSurTableOrdinateur();
        if(selection == null) {
            Ut.afficherSL("L'ordinateur n'a pas trouvé de E3C ! Il perd 1 point.");
            selection = this.selectionAleatoireDeCartesOrdinateur();
            this.score -= 1;
        } else {
            this.table.afficherSelection(selection);
            Ut.afficherSL("L'ordinateur a trouvé un E3C ! Il gagne 3 points.");
            this.score += 3;
        }

        Ut.sautLigne();

        // Remplacer les cartes
        this.piocherEtPlacerNouvellesCartes(selection);
    }

    /**
     * Action : Fait jouer une partie à l'ordinateur.
     * Une pause est faite entre chaque tour (500 ms ou plus) afin de pouvoir observer la progression de l'ordinateur.
     * A la fin, le score final de l'ordinateur est affiché.
     * Rappel : utils.Ut.pause(temps) permet de faire une pause de "temps" millisecondes
     */

    public void jouerOrdinateur() {
        while(!partieEstTerminee()) {
            joueurTourOrdinateur();
            Ut.pause(500);
        }
        Ut.afficherSL("Partie terminée ! Le score final de l'ordinateur est de " + this.score + " points.");
    }

    /**
     * Action : Permet de lancer des parties de "E3Cète" au travers d'un menu.
     * Le menu permet au joueur de sélectionner une option parmi :
     *  - humain : lance une partie avec un joueur humain
     *  - ordinateur : lance une partie avec un ordinateur
     *  - terminer : arrête le programme.
     * Après la fin de chaque partie, les données de jeu sont ré-initialisées et le menu est ré-affiché
     * (afin de faire une nouvelle sélection).
     * Les erreurs de saisie doivent être gérées (si l'utilisateur sélectionne une option inexistante).
     * Divers messages d'informations doivent être affichés pour l'ergonomie.
     */

    public void jouer() {
        boolean fini = false;
        Ut.clearConsole();
        while(!fini) {
            Ut.afficherSL("\n\nBienvenue dans le jeu E3Cète !");
            Ut.sautLigne();
            Ut.afficherSL("Veuillez sélectionner une option :");
            Ut.afficherSL("1. Humain");
            Ut.afficherSL("2. Ordinateur");
            Ut.afficherSL("3. Terminer");
            Ut.sautLigne();

            int option;
            do {
                Ut.afficher("Votre choix : ");
                option = Ut.saisirEntier();
                if (option == 1) {
                    jouerHumain();
                    resetJeu();
                } else if (option == 2) {
                    jouerOrdinateur();
                    resetJeu();
                } else if (option == 3) {
                    fini = true;
                    Ut.afficherSL("Merci d'avoir joué !");
                } else Ut.afficherSL("Veuillez sélectionner une option valide !");
            } while (option != 1 && option != 2 && option != 3);
            Ut.pause(2000);
        }
    }
}
