import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        //TEST COULEUR
        System.out.println(Couleur.BLEU.getTraduction());
        System.out.println(Couleur.ROUGE.ordinal());
        Couleur[] tabCouleurs = Couleur.values();
        System.out.println(Arrays.toString(tabCouleurs));
    }
}
