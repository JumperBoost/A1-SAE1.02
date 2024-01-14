package E3CeteExt12345;

import utils.Ut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
    private int nbCartesLeft;
    private int idPaquet;

    //VARIABLES DE CLASSE
    private static int nbPaquets = 0;
    private static int nbOpApprox = 0;

    //Nombre de fois qu'on répète l'experience des 3 tris pour un N donné.
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
        this.nbCartesLeft = 0;
        this.cartes = new Carte[getNombreCartesAGenerer(couleurs, nbFiguresMax, figures, textures)];

        for (Couleur coul : couleurs)
            for (int i = 1; i <= nbFiguresMax; i++)
                for (Figure fig : figures)
                    for (Texture text : textures)
                        this.cartes[this.nbCartesLeft++] = new Carte(coul, i, fig, text);

        this.melanger();
        this.idPaquet = ++nbPaquets;
    }

    /**
     * Action : Construit un paquet par recopie en copiant les données du paquet passé en paramètre.
     */

    public Paquet(Paquet paquet) {
        this.nbCartesLeft = paquet.nbCartesLeft;
        this.cartes = new Carte[paquet.cartes.length];
        for (int i = 0; i < cartes.length; i++)
            this.cartes[i] = paquet.cartes[i];

        this.idPaquet = ++nbPaquets;
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
        int nbSwap = this.cartes.length * 2;
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
        Paquet paqTri = new Paquet(this);
        // si vide ne fait rien
        nbOpApprox = 0;

        for (int i = 0; i < paqTri.nbCartesLeft-1; i++) {
            int indiceMin = i;
            for (int j = i+1; j < paqTri.nbCartesLeft; j++) {
                if (paqTri.cartes[j].compareTo(paqTri.cartes[indiceMin]) < 0) {
                    indiceMin = j;
                    nbOpApprox += 1; // 1 pour l'affectation
                }
                nbOpApprox += 4 + 6 + 1 ; //4 pour le for, 6 pour le compareTo, 1 pour le if,
            }
            paqTri.swap2Cartes(i, indiceMin);
            nbOpApprox += 4 + 4 + 1; // 4 pour le swap, 4 pour le for, 1 pour l'affectation
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
        Paquet paqTri = new Paquet(this);
        boolean inOrder = false;
        nbOpApprox = 1;
        while (!inOrder) {
            inOrder = true;
            for (int i = 0; i < paqTri.nbCartesLeft - 1; i++) {
                if (paqTri.cartes[i+1].compareTo(paqTri.cartes[i]) < 0) {
                    paqTri.swap2Cartes(i+1, i);
                    inOrder = false;
                    nbOpApprox += 1 + 5; // 1 pour l'affectation, 5 pour le swap
                }
                nbOpApprox += 4 + 6 + 2; // 4 pour le for, 6 pour le compareTo, 2 pour le if
            }
            nbOpApprox += 2; // 2 pour le while
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
        Paquet paqTri = new Paquet(this);
        nbOpApprox = 0;
        for (int i = 1; i < paqTri.nbCartesLeft; i++) {
            int j = i;
            while (j > 0 && paqTri.cartes[j].compareTo(paqTri.cartes[j-1]) < 0) {
                paqTri.swap2Cartes(j, j-1);
                j--;
                nbOpApprox += 6 + 2 + 1 + 5; // 6 pour le compareTo, 2 pour le while, 1 pour l'affectation, 5 pour le swap
            }
            nbOpApprox += 4 + 6 + 2; // 4 pour le for, (6+2) pour le while dans la cas on ne rentre pas
        }
        return paqTri;
    }


    /**
     * FONCTION AJOUTEE
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
        //STRAT
        // 1. Generer paquet mélangé
        // 2. Verifier qu'il est bien mélangé
        // 3. Trier ce paquet pour chacune des méthodes en générant 3 nouveaux paquets P1, P2, P3.
        // 4. Vérifier que P1, P2 et P3 sont triés
        // 5. Afficher temps d'execution et nbOp des 3 méthodes
        // 6. Bonus : verifier que P1 = P2 = P3

        Paquet paq = new Paquet(Couleur.valuesInRange(0, cardCouleurs), cardRepetFigures, Figure.valuesInRange(0, cardFigures), Texture.valuesInRange(0, cardTextures));
        int note = 0;
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
        if (paqTriSelect.estTrie())
            note++;
        System.out.println("TRI SELECTION : " + (paqTriSelect.estTrie() ? 1 : 0) + "/1");

        System.out.println("nbOP :\t" + nbOpApprox);
        System.out.println("Time :\t" + tempsExec + " ms\n");

        //triBubble
        Paquet paqTriBulles = paq.trierBulles();
        tempsExec = Ut.getTempsExecution(paq::trierBulles);
        if (paqTriBulles.estTrie())
            note++;
        System.out.println("TRI BULLES : " + (paqTriBulles.estTrie() ? 1 : 0) + "/1");

        System.out.println("nbOP :\t" + nbOpApprox);
        System.out.println("Time :\t" + tempsExec + " ms\n");

        //triInsert
        Paquet paqTriInsert = paq.trierInsertion();
        tempsExec = Ut.getTempsExecution(paq::trierInsertion);
        if (paqTriInsert.estTrie())
            note++;
        System.out.println("TRI INSERT : " + (paqTriInsert.estTrie() ? 1 : 0) + "/1");

        System.out.println("nbOP :\t" + nbOpApprox);
        System.out.println("Time :\t" + tempsExec + " ms\n");

        System.out.println("RESULTS : " + note + "/3");
        if (note == 3)
            System.out.println("TEST SUCCESFUL");
        else System.out.println("TEST FAILED");

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
        for(int i = 0; i < this.nbCartesLeft - 1; i++) {
            if (this.cartes[i].compareTo(this.cartes[i+1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * FONCTION AJOUTEE 
     * Action : verifie si le paquet this est identique à paquet
     * c-a-d ayant le meme nombre de cartes restantes ET qui sont dans le même ordre
     * @param paquet
     * @return true si oui, false sinon
     */
    public boolean estIdentique(Paquet paquet) {
        if (this.nbCartesLeft != paquet.nbCartesLeft)
            return false;

        for (int i = 0; i < this.nbCartesLeft; i++) {
            if (this.cartes[i].compareTo(paquet.cartes[i]) != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * Pre-requis : 0 <= nbCartes <= nombre de cartes restantes dans le paquet.
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
        int nbCartesPioch = nbCartes;
        if (!peutPiocher(nbCartes)) {
            nbCartesPioch = this.nbCartesLeft;
        }
        Carte[] tabCartes = new Carte[nbCartes];
        for (int i = 0; i < nbCartesPioch; i++) {
            tabCartes[i] = this.cartes[--this.nbCartesLeft];
        }
        return tabCartes;
    }

    /**
     * Résultat : Vrai s'il reste assez de cartes dans le paquet pour piocher nbCartes.
     */

    public boolean peutPiocher(int nbCartes) {
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
            str += this.cartes[i].toString() + ",\n";
        }
        str += this.cartes[i].toString() + "\n]\n";

        return str;
    }



    //CI-DESSOUS LES FONCTIONS LIES A LA PARTIE MATHS ====================================================
    
    /**
     * FONCTION AJOUTEE
     * Action : réalise les 3 méthodes de tris pour un paquet initialisé à nbCartes nombre de carte.
     * Pour cette fonction le nombre de Couleur/nbFigures/Figure/Texture sont fixes (variables de classes : cardCouleurs, cardRepetFigures, cardFigures, cardTextures).
     * Nous répétons ces tris plusieurs fois (valeurs entrés en params) sur des paquets presentant les memes caracteristiques mais mélangés différremment ; pour obtenir des données significatifs :
     * Stock les donées relatifs (nb Cartes, nombre OP et temps exec) aux tris dans un tableau de tableau.
     * tabInfos[O à 2][0] --> nbCartes
     * tabInfos[O à 2][1] --> nbOP moyen
     * tabInfos[O à 2][2] --> ecart-type nbOp
     * tabInfos[O à 2][3] --> tempsExec moyen
     * tabInfos[O à 2][4] --> ecart-type tempsExec
     * 
     * tabInfos[0][0 à 4] --> Infos de Méthode SELECTION
     * tabInfos[1][0 à 4] --> Infos de Méthode BULLES
     * tabInfos[2][0 à 4] --> Infos de Méthode INSERTION
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
     * @param nStep pas
     * @return un tableau contenant tout les résultats de chacun des trisPaquet().
     */
    private static double[][][] testTrisVarN(int nStart, int nEnd, int nStep) {
        int range = (int) Math.ceil((float) (nEnd - nStart)/nStep);
        double[][][] tabInfos = new double[range][3][5];
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
     * Action : convertit les données de tabInfos en un String.
     * Le String contient toutes les donées de chacune des méthodes de TRI.
     * Le format est adapté pour favoriser l'exportation en fichier .csv
     * Prérequis : tabInfos est retourné par testTrisVarN(int nStart, int nEnd, int nStep) OU
     * format conforme à ce dernier double[nbval de N][3][5];
     * @param tabInfos
     * @return stringInfos, un String.
     */
    private static String convertInfosToString(double[][][] tabInfos) {
        String stringInfos = 
            "N, nbOpSEL, u_nbOPSEL, tExecSEL (ms), u_tExecSEL (ms), " + 
            "nbOpBUL, u_nbOPBUL, tExecBUL (ms), u_tExecBUL (ms), " + 
            "nbOpINS, u_nbOPINS, tExecINS (ms), u_tExecINS (ms)\n";

        int i, j, k, l=0;
        for (i = 0; i < tabInfos.length; i++) {
            for (j = 0; j < tabInfos[i].length; j++){
                for (k = l; k < tabInfos[i][j].length - 2; k++) {
                    stringInfos += String.format("%.0f", tabInfos[i][j][k]) + ", ";
                }
                l=1;
                stringInfos += String.format("%.3f", tabInfos[i][j][k]) + ", ";
                stringInfos += String.format("%.3f", tabInfos[i][j][k+1]);
                if (j == tabInfos[i].length - 1) {
                    stringInfos += "\n";
                }
                else {
                    stringInfos += ", ";
                }
            }
            l=0;
        }
        return stringInfos;
    }

    /**
     * FONCTION AJOUTEE
     * Action : convertit le String contenant les donées de chacune des méthodes en fichiers CSV.
     * Prérequis : format des String dans stringInfos adapté OU stringInfos directement retourné par convertInfosToString(double[][][] tabInfos).
     * @param stringInfos
     * @throws FileNotFoundException
     */
    private static void stringInfosToCsv(String stringInfos, String fileName) throws FileNotFoundException {
        File csvFile = new File("./datas/"+fileName+".csv");
        PrintWriter output = new PrintWriter(csvFile);
        output.print(stringInfos);
        output.close();
    }

    /**
     * FONCTION AJOUTEE
     * Prerequis : 0 < nStart < nEnd ET 0 < nStep
     * Action : réunit les méthodes ci-dessus :
     * 1. Realise les 3 tris (plusieurs fois pour un meme N pour different paquet ayant meme caracteristique) en variant N (selon la borne et le pas entrés en parametres).
     * Stockage des donées : double[nStart-nEnd/nStep][3][5]
     * 2. Conversion des donées en String contenant les donées des 3 méthodes sous un format adapté au fichiers .csv
     * Ordre des infos
     * col1 --> N
     * col2-5 --> SELECTION
     * col6-9 --> BULLES
     * col10-13 --> INSERION
     * 3. Ecrit le String dans un fichier .csv pour favoriser le traitement de données
     * @throws FileNotFoundException
     */
    public static void testTrisWithDatas(int nStart, int nEnd, int nStep) throws FileNotFoundException {
        stringInfosToCsv(convertInfosToString(Paquet.testTrisVarN(nStart, nEnd, nStep)), "datas");
    }


//FONCTIONS PROBABILITE ================================================================================

    /**
     * FONCTION AJOUTEE
     * Action : Pour un paquet de 81 cartes distincts (3 Couleurs/ nbFiguresMax=3/ 3 Figures/ 3 Textures possibles par carte) :
     * calcul expérimentalement et retourne la fréquence d'obtenir une Table (arrangement de 9 cartes distincts parmi les 81 du Paquet) contenant exactement 3 Cartes Rouges.
     * Valeur théorique : 0.28956680871386137
     * @param nbEssai nombre d'essai
     */
    public static double proba3CR(int nbEssai) {
        double countCasFavorable = 0;
        Paquet paq;
        for (int j = 0; j < nbEssai; j++) {
            paq = new Paquet(
                Couleur.valuesInRange(0, 3),
                3,
                Figure.valuesInRange(0, 3),
                Texture.valuesInRange(0, 3)
                );
            Carte[] tab = paq.piocher(9);
            double countRed=0;
            for (int k = 0; k < tab.length; k++) {
                if (tab[k].getCouleur() == Couleur.ROUGE) {
                    countRed++;
                }
            }
            if (countRed==3) {
                countCasFavorable++;
            }
        }
        return countCasFavorable;
    }

    /**
     * FONCTION AJOUTEE
     * Prerequis : 0 <= startEssai < endEssai ET 0 <= stepEssai
     * experience = 1 pour probabilité de 3CR
     * experience = 2 pour probabilité de E3C
     * experience = 3 pour probabilité de E3C&2CL
     * Action : Réalise les fonctions proba3CR(int nbEssai) ou probaE3C(int nb) en
     * faisant varier le nombre d'essai de startEssai à endEssai en faisant des pas
     * de stepEssai.
     * Stock les données dans un tableau de double de taille 4 :
     *  tabStats[0] --> Nombre d'essai
     *  tabStats[1] --> Fréquence de cas favorables de l'événement
     *  tabStats[2] --> Probablilité individuelle : P = fréquence/nbEssai
     *  tabStats[3] --> Pourcentage d'Erreur de la probabilité individuelle de l'évenement par rapport à la valeur théorique ; calculable seulement en connaissance de la valeur théorique
     * Convertion de ces données en String adaptés au format .csv et écrit le String dans un fichier
     * "proba3CR.csv" ou "probaE3C.csv" ou "proba3CR&2L"
     */
    public static void probaVarEssai(int startEssai, int endEssai, int stepEssai, int experience) throws FileNotFoundException {
        int range = (int) Math.ceil((float) (endEssai - startEssai)/stepEssai);
        double[][] tabStats = new double[range][4];
        int count = 0;
        for (int i = startEssai; i < endEssai; i+=stepEssai) {
            tabStats[count][0] = i;
            if (experience == 1) {
                tabStats[count][1] = proba3CR(i);
                tabStats[count][2] = tabStats[count][1]/i; //proba individuelle
                tabStats[count][3] = Ut.pourcentageErreur(0.28956680871386137, tabStats[count][2]);
            }
            else if (experience == 2) {
                tabStats[count][1] = probaE3C(i);
                tabStats[count][2] = tabStats[count][1]/i; //proba individuelle
                //na calcul pas erreur car pas de val theorique
            }
            else if (experience == 3) {
                tabStats[count][1] = proba3CRAnd2CL(i);
                tabStats[count][2] = tabStats[count][1]/i; //proba individuelle
                tabStats[count][3] = Ut.pourcentageErreur(0.06884362550959248, tabStats[count][2]);
            }
            count++;
        }
        if (experience == 1) {
            stringInfosToCsv(probaDatasToString(tabStats), "proba3CR");
        }
        else if (experience == 2) {
            stringInfosToCsv(probaDatasToString(tabStats), "probaE3C");
        }
        else if (experience == 3) {
            stringInfosToCsv(probaDatasToString(tabStats), "proba3CR&2L");
        }
    }


    /**
     * FONCTION AJOUTEE
     * Prérequis : tabStats un tableau générer dans probaVarEssai
     * Action : Convertion du tableau de double en un String adapté au fichier .csv
     * @param tabStats
     */
    private static String probaDatasToString(double[][] tabStats) {
        String stringDatas = "NbEssai, Freq, P, Erreur (%)\n";
        int i,j;
        for (i = 0; i < tabStats.length; i++) {
            for (j = 0; j < tabStats[i].length - 1; j++) {
                stringDatas += tabStats[i][j] + ", ";
            }
            stringDatas += tabStats[i][j] + "\n";
        }
        return stringDatas;
    }


    /**
     * FONCTION AJOUTEE
     * Action : Pour un paquet de 81 cartes distincts (3 Couleurs/ nbFiguresMax=3/ 3 Figures/ 3 Textures possibles par carte) :
     * calcul expérimentalement et retourne la fréquence d'obtenir une Table (arrangement de 9 cartes distincts parmi les 81 du Paquet) contenant exactement 3 Cartes Rouges et 2 cartes ayant au moins 1 Losange.
     * Valeur théorique : 0.06884362550959248
     * @param nb nombre d'essai
     */
    public static double proba3CRAnd2CL(int nb) {
        double countCasFavorable = 0;
        Paquet paq;
        for (int i = 0; i < nb; i++) {
            paq = new Paquet(
                Couleur.valuesInRange(0, 3),
                3,
                Figure.valuesInRange(0, 3),
                Texture.valuesInRange(0, 3)
                );
            Carte[] tab = paq.piocher(9);
            double countRed=0;
            double countLosange=0;
            for (int j = 0; j < tab.length; j++) {
                if (tab[j].getCouleur() == Couleur.ROUGE) {
                    countRed++;
                }
                if (tab[j].getFigure() == Figure.LOSANGE) {
                    countLosange++;
                }
            }
            if (countRed==3 && countLosange==2) {
                countCasFavorable++;
            }
        }
        return countCasFavorable;
    }

    /**
     * FONCTION AJOUTEE
     * Action : Pour un paquet de 81 cartes distincts (3 Couleurs/ nbFiguresMax=3/ 3 Figures/ 3 Textures possibles par carte) :
     * calcul expérimentalement et retourne la fréquence d'obtenir une Table (arrangement de 9 cartes distincts parmi les 81 du Paquet) contenant exactement 3 Cartes Rouges et 2 cartes ayant au moins 1 Losange.
     * Valeur théorique : non connue
     * @param nb nombre d'essai
     */
    public static double probaE3C(int nb) {
        double countCasFavorable = 0;
        Paquet paq;
        for (int i = 0; i < nb; i++) {
            paq = new Paquet(
                Couleur.valuesInRange(0, 3),
                3,
                Figure.valuesInRange(0, 3),
                Texture.valuesInRange(0, 3)
                );
            Carte[] tab = paq.piocher(9);
            if (trouverE3C(tab)) {
                countCasFavorable++;
            }
        }
        return countCasFavorable;
    }

    /**
     * FONCTION AJOUTEE
     * Action : verifie si un ensemble de cartes contient un E3C
     * @param cartes
     * @return vrai si oui, false sinon;
     */
    private static boolean trouverE3C(Carte[] cartes) {
        if (cartes.length < 3) {
            return false;
        }
        for(int i = 0; i < cartes.length; i++)
            for(int j = i+1; j < cartes.length; j++)
                for(int k = j+1; k < cartes.length; k++)
                    if(Jeu.estUnExC(new Carte[]{cartes[i], cartes[j], cartes[k]})) // estUnExC agit comme estUnE3C (juste x est défini dans Jeu). Par défaut x = 3, donc don't worry.
                        return true;
        return false;
    }
}
