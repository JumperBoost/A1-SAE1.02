import java.util.Random;

/**
 * La classe Paquet représente un paquet de cartes.
 * Les cartes sont stockées dans un tableau fixe et un indice (entier) permet de connaître le nombre de cartes
 * restantes (non piochées) dans le paquet. Quand on pioche, cet indice diminue.
 * Dans les traitements, on considère alors seulement les cartes se trouvant entre 0 et cet indice (exclus).
 * Par conséquent, on ne supprime pas vraiment les cartes piochées, on les ignore juste.
 * On a donc besoin de connaître :
 * - Le tableau stockant les cartes.
 * - Le nombre de cartes restantes dans le paquet.
 */
public class Paquet {


    //VARIABLE D'INSTANCES
    private Carte[] cartes;
    private Couleur[] couleurs;
    private int nbFiguresMax;
    private Figure[] figures;
    private Texture[] textures;
    private int nbCartesLeft;
    private int idPaquet;
    //initialisation au nombre max de cartes créer

    //VARIABLES DE CLASSE
    private static int nbPaquets = 0;
    private static int nbOpApprox = 0;


    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Action : Construit un paquet de cartes mélangé contenant toutes les cartes incluant 1 à nbFiguresMax figures
     * qu'il est possibles de créer en combinant les différentes figures, couleurs et textures précisées en paramètre.
     * Bien sûr, il n'y a pas de doublons.
     *
     * Exemple :
     *  - couleurs = [Rouge, Jaune]
     *  - nbFiguresMax = 2
     *  - figures = [A, B]
     *  - textures = [S, T]
     *  Génère un paquet (mélangé) avec toutes les combinaisons de cartes possibles pour ces caractéristiques : 1-A-S (rouge), 1-A-T (rouge), etc...
     */

    public Paquet(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {
        this.couleurs = couleurs;
        this.nbFiguresMax = nbFiguresMax;
        this.figures = figures;
        this.textures = textures;

        this.nbCartesLeft = 0;
        this.cartes = new Carte[getNombreCartesAGenerer(couleurs, nbFiguresMax, figures, textures)];

        for (Couleur coul : couleurs) {
            for (int i = 1; i <= nbFiguresMax; i++) {
                for (Figure fig : figures) {
                    for (Texture text : textures) {
                        this.cartes[this.nbCartesLeft] = new Carte(coul, i, fig, text);
                        this.nbCartesLeft++;
                    }
                }
            }
        }
        this.melanger();
        nbPaquets++;
        this.idPaquet = nbPaquets;
    }

    /**
     * Action : Construit un paquet par recopie en copiant les données du paquet passé en paramètre.
     */

    public Paquet(Paquet paquet) {
        this.couleurs = paquet.couleurs;
        this.nbFiguresMax = paquet.nbFiguresMax;
        this.figures = paquet.figures;
        this.textures = paquet.textures;

        this.nbCartesLeft = paquet.nbCartesLeft;
        this.cartes = new Carte[paquet.cartes.length];

        for (int i = 0; i < cartes.length; i++) {
            this.cartes[i] = paquet.cartes[i];
        }

        nbPaquets++;
        this.idPaquet = nbPaquets;
    }


    /**
     * Pre-requis : figures.length > 0, couleurs.length > 0, textures.length > 0, nbFiguresMax > 0
     *
     * Resultat : Le nombre de cartes uniques contenant entre 1 et nbFiguresMax figures qu'il est possible de générer en
     * combinant les différentes figures, couleurs et textures précisées en paramètre.
     */

    public static int getNombreCartesAGenerer(Couleur[] couleurs, int nbFiguresMax, Figure[] figures, Texture[] textures) {

        return couleurs.length * nbFiguresMax * figures.length * textures.length; 
    }

    /**
     * Action : Mélange aléatoirement les cartes restantes dans le paquet.
     * Attention, on rappelle que le paquet peut aussi contenir des cartes déjà piochées qu'il faut ignorer.
     */

    public void melanger() {
        Random r = new Random();
        int nbSwap = getNombreCartesAGenerer(this.couleurs, this.nbFiguresMax, this.figures, this.textures) * 2;
        int choix1;
        int choix2;
        for (int i = 0; i < nbSwap; i++) {
            choix1 = r.nextInt(this.nbCartesLeft);
            choix2 = r.nextInt(this.nbCartesLeft);
            this.swap2Cartes(choix1, choix2);          
        }
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri selection.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=Ns4TPTC8whw&t=2s vidéo explicative
     */

    public Paquet trierSelection() {
        //STRAT
        //create copy
        //ignore cartes piochées
        //select current first from left
        //compare from first on right
            //if inf than actual swap places and inf becomes current
        //repeat until end of table and put current in current
        
        Paquet paqTri = new Paquet(this);
        // si vide ne fait rien
        nbOpApprox = 0;

        for (int i = 0; i < paqTri.nbCartesLeft - 1; i++) {
            nbOpApprox = nbOpApprox + 3;
            for (int j = i + 1; j < paqTri.nbCartesLeft; j++) {
                if (paqTri.cartes[j].compareTo(paqTri.cartes[i]) < 0) {
                    paqTri.swap2Cartes(i, j);
                    nbOpApprox = nbOpApprox + 6 + 4;
                }
                nbOpApprox = nbOpApprox + 4;
            }
        }
        return paqTri;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri bulles.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=lyZQPjUT5B4&embeds_referring_euri=https%3A%2F%2Fwww.developpez.com%2F&source_ve_path=Mjg2NjY&feature=emb_logo
     * vidéo explicative
     */

    public Paquet trierBulles() {
        //STRAT
        //create copy
        //parcourir tout le paquet
        //comparer par paire croissant 0-1 puis 1-2 etc.
            //si carte(indiceSup) infereieur à carte(indiceInf) alors swap
        //repeter tant que tout n'est pas en ordre (bool)
        //note : max nombre de repet = pour deplacer la plus petite carte située en derniere position = this.nbCartesLeft - 1
        Paquet paqTri = new Paquet(this);
        boolean inOrder = false;
        nbOpApprox = 1;
        while (!inOrder) {
            inOrder = true;
            for (int i = 0; i < paqTri.nbCartesLeft - 1; i++) {
                if (paqTri.cartes[i+1].compareTo(paqTri.cartes[i]) < 0) {
                    paqTri.swap2Cartes(i+1, i);
                    inOrder = false;
                    nbOpApprox = nbOpApprox + 6 + 4 + 1;
                }
                nbOpApprox = nbOpApprox + 4;
            }
            nbOpApprox = nbOpApprox + 2;
        }
        return paqTri;
    }

    /**
     * Action : Calcule et renvoie un paquet trié à partir du paquet courant (this) selon la méthode du tri insertion.
     * Le tri est effectué à partir des données du paquet courant (this) mais celui-ci ne doit pas être modifié !
     * Une nouvelle instance du paquet est traitée et renvoyée.
     * On rappelle que le paquet peut aussi contenir des cartes déjà piochées  qu'il faut ignorer (voir partie 2 de la SAE).
     * Le tri doit fonctionner que le Paquet soit plein ou non.
     * https://www.youtube.com/watch?v=ROalU379l3U&t=1s : vidéo explicative
     */

    public Paquet trierInsertion() {
        //STRAT
        //create copy
        //parcourir UNE FOIS tout le paquet de 1 à nbCartesLeft(exclus)
        //tant que carte current inferieur à carte de gauche OU pas au bord gauche
            //swap et stocker nouvel indice current-1
        Paquet paqTri = new Paquet(this);
        nbOpApprox = 0;
        for (int i = 1; i < paqTri.nbCartesLeft; i++) {
            boolean inPlace = false;
            int j = i;
            while (!inPlace && j!=0) {
                if (paqTri.cartes[j].compareTo(paqTri.cartes[j-1]) < 0) {
                    paqTri.swap2Cartes(j, j-1);
                    j--;
                    nbOpApprox = nbOpApprox + 6 + 4 + 1;
                }
                else {
                    inPlace = true;
                    nbOpApprox = nbOpApprox + 1;
                }
                nbOpApprox = nbOpApprox + 2;  
            }
            nbOpApprox = nbOpApprox + 5;
        }
        return paqTri;
    }

    /**
     * Action : Permet de tester les différents tris.
     * On doit s'assurer que chaque tri permette effectivement de trier un paquet mélangé.
     * Des messages d'informations devront être affichés.
     * La méthode est "static" et ne s'effectue donc pas sur la paquet courant "this".
     */
    public static void testTris() {
        //generer paquet mélangé
        //trier ce paquet selon les differentes methodes P1, P2, P3
        //verifier que P1, P2, P3 sont triés
        //bonus verifier P1 = P2 = P3
        // si all true all works :)
        Couleur[] couleurs = Couleur.values();
        Figure[] figures = Figure.values();
        Texture[] textures = Texture.values();

        Paquet paq = new Paquet(couleurs, 50, figures, textures);
        int note1, note2, note3;
        note1 = note2 = note3 = 0;
        double tempsExec;
        System.out.println("\033[0;32m");
        System.out.println("\033[40m");
        System.out.println("\nBEGIN-TEST-TRIS---------------------------------------------------------\n");
        System.out.println("Paquet à trier est-il trié ? " + paq.estTrie());
        System.out.println("Nb de cartes : " + paq.nbCartesLeft);
        System.out.println();
    
        //triSelect
        Paquet paqTriSelect = paq.trierSelection();
        tempsExec = Ut.getTempsExecution(paq::trierSelection);
        if (paqTriSelect.estTrie()) {
            note1++;
        }
        System.out.println("TRI SELECTION : " + note1 + "/1");

        System.out.println("nbOP :\t" + nbOpApprox);
        System.out.println("Time :\t" + tempsExec + " ms");
        System.out.println();

        //triBubble
        Paquet paqTriBulles = paq.trierBulles();
        tempsExec = Ut.getTempsExecution(paq::trierBulles);
        if (paqTriBulles.estTrie()) {
            note2++;
        }
        System.out.println("TRI BULLES : " + note2 + "/1");

        System.out.println("nbOP :\t" + nbOpApprox);
        System.out.println("Time :\t" + tempsExec + " ms");
        System.out.println();

        //triInsert
        Paquet paqTriInsert = paq.trierInsertion();
        tempsExec = Ut.getTempsExecution(paq::trierInsertion);
        if (paqTriInsert.estTrie()) {
            note3++;
        }
        System.out.println("TRI INSERT : " + note3 + "/1");

        System.out.println("nbOP :\t" + nbOpApprox);
        System.out.println("Time :\t" + tempsExec + " ms");
        System.out.println();

        System.out.println("RESULTS : " + (note1+note2+note3) + "/3");
        if (note1+note2+note3 == 3) {
            System.out.println("TEST SUCCESFUL");
        }
        else {
            System.out.println("TEST FAILED");
        }

        //bonus
        System.out.println("Bonus : Les 3 paquets triés sont identiques ? " + (paqTriSelect.estIdentique(paqTriBulles) && paqTriBulles.estIdentique(paqTriInsert)));

        System.out.println("\nEND-TEST-TRIS-----------------------------------------------------------");
        System.out.println(Couleur.getReset());
    }

    /**
     * FONCTION AJOUTEE
     * Action : verifie si this est un paquet trié (sans se préoccuper des cartes "piochés")
     * Rq : un paquet vide est considéré comme trié
     * @return true si oui, false sinon
     */
    public boolean estTrie() {
        boolean isSorted = true;
        int i = 0;
        while (isSorted && i<this.nbCartesLeft-1) {
            if (this.cartes[i].compareTo(this.cartes[i+1]) > 0) {
                isSorted = false;
            }
            else {
                i++;
            }
        }
        return isSorted;
    }

    /**
     * FONCTION AJOUTEE 
     * Action : verifie si le paquet this est identique à paquet
     * c-a-d ayant le meme nombre de cartes restantes ET qui sont dans le même ordre
     * @param paquet
     * @return true si oui, false sinon
     */
    public boolean estIdentique(Paquet paquet) {
        boolean estIdentique = true;
        int i = 0;
        if (this.nbCartesLeft == paquet.nbCartesLeft) {
            while (estIdentique && i<this.nbCartesLeft) {
                if (this.cartes[i].compareTo(paquet.cartes[i]) != 0) {
                    estIdentique = false;
                }
                else {
                    i++;
                }
            }            
        }
        else {
            estIdentique = false;
        }
        return estIdentique;
    }





    /**
     * Pre-requis : 0 < nbCartes <= nombre de cartes restantes dans le paquet.
     *
     * Action : Pioche nbCartes Cartes au dessus du Paquet this (et met à jour son état).
     *
     * Résultat : Un tableau contenant les nbCartes Cartes piochees dans le Paquet.
     *
     * Exemple :
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 5. On considère donc seulement les cartes de 0 à 4.
     *
     * piocher(3)
     * Contenu paquet : [A,B,C,D,E,F,G]
     * Nombre de cartes restantes : 2
     * Renvoie [E,D,C]
     */

    public Carte[] piocher(int nbCartes) {
        Carte[] tabCartes = new Carte[nbCartes];
        for (int i = 0; i < nbCartes; i++) {
            tabCartes[i] = this.cartes[this.nbCartesLeft - 1 - i];
        }
        this.nbCartesLeft = this.nbCartesLeft - nbCartes;
        return tabCartes;
    }

    /**
     * Résultat : Vrai s'il reste assez de cartes dans le paquet pour piocher nbCartes.
     */

    public boolean peutPicoher(int nbCartes) {
        return 0 <= nbCartes && nbCartes <= this.nbCartesLeft;
    }

    /**
     * Résultat : Vrai s'il ne reste plus aucune cartes dans le paquet.
     */

    public boolean estVide() {
        return this.nbCartesLeft == 0;
    }

    /**
     * FONCTION AJOUTEE
     * Action : echange l'emplacement de deux cartes dans un paquet.
     * Prérequis : 0 <= indice1 et indice2 < this.nbCartesLeft et indice1 =/ indice2
     * @param indice1 indice de la premiere carte dans le paquet
     * @param indice2 indice de la deuxieme carte dans le paquet
     */
    public void swap2Cartes(int indice1, int indice2) {
            Carte temp;
            temp = this.cartes[indice1];
            this.cartes[indice1] = this.cartes[indice2];
            this.cartes[indice2] = temp;
    }


    /**
     * Résultat : Une chaîne de caractères représentant le paquet sous la forme d'un tableau
     * [X, Y, Z...] représentant les cartes restantes dans le paquet.
     *
     * Exemple :
     * Contenu paquet : 1-O-P (rouge), 2-C-V (jaune), 3-L-P (jaune), 3-L-P (rouge), 1-L-V (bleu)
     * Nombre de cartes restantes : 3
     * Retourne [1-O-P, 2-C-V, 3-L-P] (et chaque représentation d'une carte est coloré selon la couleur de la carte...)
     */

    @Override
    public String toString() {
        String str = "\nPAQUET n°" + this.idPaquet + "\nNombre de cartes restantes : " + this.nbCartesLeft + "\n[\n";
        int i;
        for (i = 0; i < this.nbCartesLeft - 1; i++) {
            str = str + this.cartes[i].toString() + ",\n";
        }
        str = str + this.cartes[i].toString() + "\n]\n";

        return str;
    }
}
