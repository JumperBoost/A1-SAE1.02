import java.io.FileNotFoundException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        // TEST AFFICHAGE CARTES
        System.out.println("\n\nTEST AFFICHAGE CARTES----------------------------------------------------");
        Carte carte1 = new Carte(Couleur.ROUGE, 3, Figure.CERCLE, Texture.TIRET);
        System.out.println(carte1 + "\n\n");

        Carte carte2 = new Carte(Couleur.BLEU, 12, Figure.LOSANGE, Texture.RAYURE);
        System.out.println(carte2 + "\n\n");

        Carte carte3 = new Carte(Couleur.VERT, 345, Figure.CARRE, Texture.VIDE);
        System.out.println(carte3);


        //TEST COULEUR
        System.out.println("\n\nTEST COULEUR----------------------------------------------------");
        System.out.println(Couleur.BLEU.getTraduction());
        System.out.println(Couleur.ROUGE.ordinal());
        Couleur[] tabCouleurs = Couleur.values();
        System.out.println(Arrays.toString(tabCouleurs));

        Couleur[] couls = Couleur.valuesInRange(1, Couleur.getNbEnumTotal());
        System.out.println(Arrays.toString(couls));


        //TEST FIGURE, TEXTURES
        System.out.println("\n\nTEST FIGURE/TEXTURES----------------------------------------------------");
        Figure[] figs = Figure.valuesInRange(0, 2);
        System.out.println(Arrays.toString(figs));
        System.out.println("Enum Total Figure : " + Figure.getNbEnumTotal());

        Texture[] texts = Texture.valuesInRange(0, 2);
        System.out.println(Arrays.toString(texts));
        System.out.println("Enum Total Texture : " + Texture.getNbEnumTotal());


        //TEST CARTES
        System.out.println("\n\nTEST CARTES----------------------------------------------------");
        Carte card = new Carte(Couleur.BLEU, 2, Figure.CARRE, Texture.TIRET);
        System.out.println(card);

        Carte card2 = new Carte(Couleur.ROUGE, 3, Figure.LOSANGE, Texture.RAYURE);
        System.out.println(card2);


        //TEST PAQUET
        System.out.println("\n\nTEST PAQUET----------------------------------------------------");
        
        //constructeur
        Paquet paq = new Paquet(tabCouleurs, 3, Figure.values(), Texture.values());
        System.out.println(paq);

        //constructeur copie
        Paquet paq2 = new Paquet(paq);
        System.out.println(paq2);

        //triSelect
        Paquet paqTriSelect = paq.trierSelection();
        System.out.println(paqTriSelect);

        //triBubble
        Paquet paqTriBulles = paq.trierBulles();
        System.out.println(paqTriBulles);

        //triInsert
        Paquet paqTriInsert = paq.trierInsertion();
        System.out.println(paqTriInsert);

        //estTrie()
        System.out.println("Le paquet paq est trié ? " + paq.estTrie());
        System.out.println("Le paquet paqTriInsert est trié ? " + paqTriInsert.estTrie());
        //estEgal()
        System.out.println("paqTriInsert identique à paqTriBulles ? " + paqTriInsert.estIdentique(paqTriBulles));
        System.out.println("paq identique à paq2 ? " + paq.estIdentique(paq2));
        System.out.println("paq identique à paq ? " + paq.estIdentique(paq));
        System.out.println("paq identique à paqTriSelect ? " + paq.estIdentique(paqTriSelect));       


        //piocher()
        System.out.println(paq);
        Carte[] cartesP = paq.piocher(3);
        System.out.println(paq);
        for (Carte carte : cartesP) {
            System.out.println(carte);
        }
        //peutPiocher()
        System.out.println(paq.peutPiocher(81));
        System.out.println(paq.peutPiocher(78));
        System.out.println(paq.peutPiocher(0));
        System.out.println(paq.peutPiocher(-9999));


        //TestTris
        Paquet.testTris();


        //TEST METHODE PRIV pour DATAS
        // //TrisPaquet
        // double[][] tabInfos = Paquet.trisPaquet(500, 1000);
        // for (int i = 0; i < 3; i++) {
        //     System.out.println(Arrays.toString(tabInfos[i]));
        // }
        // //testVariantN
        // double[][][] tabInfos2 = Paquet.testTrisVarN(10, 500, 2);
        // for (int i = 0; i < 490/2; i++) {
        //     System.out.println(Arrays.deepToString(tabInfos2[i]));
        // }
        // //stringinfo
        // String[] stringInfos = Paquet.convertInfosToString(tabInfos2);
        // System.out.println(stringInfos[0]);
        // Paquet.stringInfosToCsv(stringInfos);

        //TESTTRI
        Paquet.testTrisWithDatas(10, 100, 1);

    }
}
