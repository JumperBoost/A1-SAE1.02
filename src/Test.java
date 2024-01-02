import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        //TEST COULEUR
        System.out.println("\n\nTEST COULEUR----------------------------------------------------");
        System.out.println(Couleur.BLEU.getTraduction());
        System.out.println(Couleur.ROUGE.ordinal());
        Couleur[] tabCouleurs = Couleur.values();
        System.out.println(Arrays.toString(tabCouleurs));


        //TEST CARTES
        System.out.println("\n\nTEST CARTES----------------------------------------------------");
        Carte card = new Carte(Couleur.BLEU, 2, Figure.CARRE, Texture.PLEINE);
        System.out.println(card);

        Carte card2 = new Carte(Couleur.ROUGE, 3, Figure.TRIANGLE, Texture.RAYURE);
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

        //TestTris
        Paquet.testTris();

        // //piocher()
        // System.out.println(paq);
        // Carte[] cartesP = paq.piocher(3);
        // System.out.println(paq);
        // for (Carte carte : cartesP) {
        //     System.out.println(carte);
        // }
        // //peutPiocher()
        // System.out.println(paq.peutPicoher(81));
        // System.out.println(paq.peutPicoher(78));
        // System.out.println(paq.peutPicoher(0));
        // System.out.println(paq.peutPicoher(-9999));
    }
}
