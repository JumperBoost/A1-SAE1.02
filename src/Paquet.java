import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
    private static int nbRepetTest = 1000;

    //caracteristiques par default du Paquet pour TestTri
    private static int cardCouleurs = 3;
    private static int cardRepetFigures = 3;
    private static int cardFigures = 3;
    private static int cardTextures = 3;


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
     * Action : Modifie les valeurs des cardinalités pour les Paquets Test
     * Prerequis : 1 <= cardNbFig
     * 1 <= cardCoul/cardFig/cardText <= <nom_Enum>.values().length 
     */
    public static void setCardPaqTest(int cardCoul, int cardNbFig, int cardFig, int cardText) {
        cardCouleurs = cardCoul;
        cardRepetFigures = cardNbFig;
        cardFigures = cardFig;
        cardTextures = cardText;
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
  

        Paquet paq = new Paquet(Couleur.valuesInRange(0, cardCouleurs), cardRepetFigures, Figure.valuesInRange(0, cardFigures), Texture.valuesInRange(0, cardTextures));
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
     * Action : réalise les 3 méthodes de tris pour un paquet initialisé à nbCartes nombre de carte.
     * Pour cette fonction le nombre de Couleur/nbFigures/Figure/Texture sont fixes (variables de classes : cardCouleurs, cardRepetFigures, cardFigures, cardTextures).
     * Nous répétons ces tris plusieurs fois (valeurs entrés en params) sur des paquets presentant les memes caracteristiques mais mélangés différremment ; pour obtenir des données significatifs :
     * Stock les donées relatifs (nb Cartes, nombre OP et temps exec) aux tris dans un tableau de tableau.
     * tabInfos[O à 2][0] --> nbCartes
     * tabInfos[O à 2][1] --> nbOP moyen
     * tabInfos[O à 2][2] --> tempsExec moyen
     * 
     * tabInfos[0][0 à 2] --> Infos de Méthode SELECTION
     * tabInfos[1][0 à 2] --> Infos de Méthode BULLES
     * tabInfos[2][0 à 2] --> Infos de Méthode INSERTION
     * @param nbCartes
     * @param nbRepetition
     * @return un tableau de tableau (3x3) de double.
     */
    private static double[][] trisPaquet(int nbRepetition) {
        double[][] tabInfos = new double[3][5];
        double tempsExec;
        double[][][] tabInterm = new double [3][2][nbRepetition];

        Paquet paq;

        for (int i = 0; i < nbRepetition; i++) {

            paq = new Paquet(
                Couleur.valuesInRange(0, cardCouleurs), 
                cardRepetFigures, 
                Figure.valuesInRange(0, cardFigures), 
                Texture.valuesInRange(0, cardTextures)
                );

            tempsExec = Ut.getTempsExecution(paq::trierSelection);
            tabInterm[0][0][i] = nbOpApprox;
            tabInterm[0][1][i] = tempsExec;

            tempsExec = Ut.getTempsExecution(paq::trierBulles);
            tabInterm[1][0][i] = nbOpApprox;
            tabInterm[1][1][i] = tempsExec;

            tempsExec = Ut.getTempsExecution(paq::trierInsertion);
            tabInterm[2][0][i] = nbOpApprox;
            tabInterm[2][1][i] = tempsExec;

            
        }

        for (int i = 0; i < 3; i++) {
            tabInfos[i][0] = cardCouleurs * cardRepetFigures * cardFigures * cardTextures;
        }
        tabInfos[0][1] = Ut.moyenne(tabInterm[0][0]);
        tabInfos[0][2] = Ut.ecarttype(tabInterm[0][0]);
        tabInfos[0][3] = Ut.moyenne(tabInterm[0][1]);
        tabInfos[0][4] = Ut.ecarttype(tabInterm[0][1]);
        
        tabInfos[1][1] = Ut.moyenne(tabInterm[1][0]);
        tabInfos[1][2] = Ut.ecarttype(tabInterm[1][0]);
        tabInfos[1][3] = Ut.moyenne(tabInterm[1][1]); 
        tabInfos[1][4] = Ut.ecarttype(tabInterm[1][1]);

        tabInfos[2][1] = Ut.moyenne(tabInterm[2][0]);
        tabInfos[2][2] = Ut.ecarttype(tabInterm[2][0]);
        tabInfos[2][3] = Ut.moyenne(tabInterm[2][1]); 
        tabInfos[2][4] = Ut.ecarttype(tabInterm[2][1]);

        return tabInfos;
    }

    /**
     * FONCTION AJOUTEE
     * Prérequis : 0 < nStart < nEnd ET 0 < nStep 
     * Action : réalise trisPaquet(int nbRepetition) en variant le nbCartes en modifiant la cardinalité de nbFigures, entré en parametres, de nStart à nEnd(exclu) en faisant des pas de nStep.
     * Les cardinalités de couleurs/figures/textures sont fixées à des valeurs constantes par les variables de class.
     * @param nStart premiere valeur de nbFiguresMax
     * @param nEnd valeur limite (exclue)
     * @param nstep pas
     * @return un tableau contenant tout les résultats de chacun des trisPaquet().
     */
    private static double[][][] testTrisVarN(int nStart, int nEnd, int nStep) {
        double[][][] tabInfos = new double[(nEnd - nStart)/nStep][3][5];
        int count = 0;
        
        for (int n = nStart; n < nEnd; n+=nStep) {
            cardRepetFigures = n;
            tabInfos[count] = trisPaquet(nbRepetTest);
            count++;
        }
        return tabInfos;
    }


    /**
     * FONCTION AJOUTEE
     * Action : convertit les données de tabInfos en un tableau de String[] de taille 3.
     * Chacun des String contient toutes les donées d'une méthode de TRI.
     * Le format est adapté pour favoriser l'exportation en fichier .csv
     * Prérequis : tabInfos est retourné par testTrisVarN(int nStart, int nEnd, int nStep) OU
     * format conforme à ce dernier double[nbval de N][3][3];
     * @param tabInfos
     * @return stringInfos, un tableau de String de taille 3.
     */
    private static String[] convertInfosToString(double[][][] tabInfos) {
        String[] stringInfos = new String[3];
        for (int i = 0; i < stringInfos.length; i++) {
            stringInfos[i] = "N, nbOp, u_nbOP, tExec (ms), u_tExec (ms)\n";
        }

        int i, j, k;
        for (i = 0; i < tabInfos.length; i++) {
            for (j = 0; j < tabInfos[i].length; j++){
                for (k = 0; k < tabInfos[i][j].length - 2; k++) {
                    stringInfos[j] += String.format("%.0f", tabInfos[i][j][k]) + ", ";
                }
                stringInfos[j] += String.format("%.3f", tabInfos[i][j][k]) + ", ";
                stringInfos[j] += String.format("%.3f", tabInfos[i][j][k+1]) + "\n";
            }
        }
        return stringInfos;
    }

    /**
     * FONCTION AJOUTEE
     * Action : convertit un tableau de String contenant les donées de chacune des méthodes en fichiers CSV.
     * Prérequis : format des String dans stringInfos adapté OU stringInfos directement retourné par convertInfosToString(double[][][] tabInfos).
     * @param stringInfos
     * @throws FileNotFoundException
     */
    private static void stringInfosToCsv(String[] stringInfos) throws FileNotFoundException {
        for (int i = 0; i < stringInfos.length; i++) {
            File csvFile = new File("./sae_e3cete/datas/datas"+(i+1)+".csv");
            PrintWriter output = new PrintWriter(csvFile);
            output.print(stringInfos[i]);
            output.close();
        }
    }


    /**
     * FONCTION AJOUTEE
     * Prerequis : 0 < nStart < nEnd ET 0 < nStep
     * Action : réunit les méthodes ci-dessus :
     * 1. Realise les 3 tris (plusieurs fois pour un meme N pour different paquet ayant meme caracteristique) en variant N (selon la borne et le pas entrés en parametres).
     * Stockage des donées : double[nStart-nEnd/nStep][3][3]
     * 2. Conversion des donées en String[3] dont chacun des String contient les donées d'une des méthodes sous un format adapté au fichiers .csv
     * String[0] --> SELECTION
     * String[1] --> BULLES
     * String[2] --> INSERION
     * 3. Ecrit chaque String dans un fichier .csv pour bien séparer les données de chacune des méthodes.
     * @throws FileNotFoundException
     */
    public static void testTrisWithDatas(int nStart, int nEnd, int nStep) throws FileNotFoundException {
        stringInfosToCsv(convertInfosToString(Paquet.testTrisVarN(nStart, nEnd, nStep)));
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
