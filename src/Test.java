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
        Paquet paq = new Paquet(tabCouleurs, 3, Figure.values(), Texture.values());

        System.out.println("===============================");
        for (Carte carte : paq.cartes) {
            System.out.println(carte);
        }
        System.out.println("===============================");
        System.out.println(paq.nbCartesLeft);


    }
}
