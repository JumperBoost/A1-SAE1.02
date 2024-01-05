import java.util.Arrays;
import java.util.concurrent.*;

/**
 * etudiant.Tests unitaires à lancer pour vérifier que certains points de votre application fonctionnent comme attendu.
 */
public class TestsEtu {

    public static void main(String[]args) {
        runTest(TestsEtu::testComparaisonCartes, "testComparaisonCartes");
        runTest(TestsEtu::testPeutPiocher, "testPeutPiocher");
        runTest(TestsEtu::testPaquetEstVide, "testPaquetEstVide");
        runTest(TestsEtu::testMelanger, "testMelanger");
        runTest(TestsEtu::testTailleTable, "testTailleTable");
        runTest(TestsEtu::testCarteExisteTable, "testCarteExisteTable");
        runTest(TestsEtu::testFormatValidesCoordonnees, "testFormatValidesCoordonnees");
        runTest(TestsEtu::testE3C, "testE3C");
    }

    public static void runTest(Runnable r, String s){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            Future<?> future = executorService.submit(r);
            future.get(1L, TimeUnit.SECONDS);
            System.out.println("****************************************************");
            System.out.println(" \u001B[32m"+ s + " terminé avec succès\u001B[0m");
            System.out.println("****************************************************");
        } catch (TimeoutException e) {
            System.out.println("****************************************************");
            System.out.println("\u001B[31m" + s + " timeout\u001B[0m");
            System.out.println("****************************************************");
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("****************************************************");
            System.out.println("\u001B[31m" + s + " erreur " + e.getCause().getMessage()+"\u001B[0m");
            System.out.println("****************************************************");
        } finally {
            executorService.shutdown();
        }
    }

    private static Figure getFigure(int index) {
        return Figure.values()[index];
    }

    private static Figure[] selectFigures(int n) {
        return Arrays.copyOfRange(Figure.values(), 0 ,n);
    }

    private static Texture getTexture(int index) {
        return Texture.values()[index];
    }

    private static Texture[] selectTextures(int n) {
        return Arrays.copyOfRange(Texture.values(), 0 ,n);
    }

    private static Couleur getCouleur(int index) {
        return Couleur.values()[index];
    }

    private static Couleur[] selectCouleurs(int n) {
        return Arrays.copyOfRange(Couleur.values(), 0 ,n);
    }

    private static void compareCartes(Carte c1, Carte c2, int attendu) {
        int compareResult = c1.compareTo(c2);
        if(attendu == -1 && compareResult > -1) {
            throw new TestException(String.format("Resultat négatif attendu en comparant %s et %s. Resultat obtenu : %s", c1, c2, compareResult));
        }
        if(attendu == 1 && compareResult < 0) {
            throw new TestException(String.format("Resultat strictement positif attendu en comparant %s et %s. Resultat obtenu : %s", c1, c2, compareResult));
        }
        if(attendu == 0 && compareResult != 0) {
            throw new TestException(String.format("Resultat nul attendu en comparant %s et %s. Resultat obtenu : %s", c1, c2, compareResult));
        }
    }

    public static void testComparaisonCartes() {
        Carte c1 = new Carte(getCouleur(0), 1, getFigure(0), getTexture(0));
        Carte ct1 = new Carte(getCouleur(1), 1, getFigure(0), getTexture(0));

        compareCartes(c1, ct1, -1);
        compareCartes(ct1, c1, 1);

        Carte c2 = new Carte(getCouleur(1), 1, getFigure(0), getTexture(0));
        Carte ct2 = new Carte(getCouleur(1), 2, getFigure(0), getTexture(0));

        compareCartes(c2, ct2, -1);
        compareCartes(ct2, c2, 1);

        Carte c3 = new Carte(getCouleur(1), 2, getFigure(0), getTexture(0));
        Carte ct3 = new Carte(getCouleur(1), 2, getFigure(1), getTexture(0));

        compareCartes(c3, ct3, -1);
        compareCartes(ct3, c3, 1);

        Carte c4 = new Carte(getCouleur(1), 2, getFigure(1), getTexture(1));
        Carte ct4 = new Carte(getCouleur(1), 2, getFigure(1), getTexture(0));

        compareCartes(c4, ct4, 1);
        compareCartes(ct4, c4, -1);
    }

    public static void testMelanger() {
        Paquet p = new Paquet(selectCouleurs(3), 3, selectFigures(3), selectTextures(3));
        p.melanger();
        Carte[] prochainesCartes;
        while(p.peutPiocher(2)) {
            prochainesCartes = p.piocher(2);
            if(prochainesCartes[0].compareTo(prochainesCartes[1]) > 0) {
                return;
            }
        }
        throw new TestException("Le paquet est complètement trié après un mélange. Cela est peu probable sur 81 cartes.");
    }

    public static void testPeutPiocher() {
        Paquet p = new Paquet(selectCouleurs(3), 3, selectFigures(3), selectTextures(3));
        if(!p.peutPiocher(1)) {
            throw new TestException("Le paquet contient 81 cartes, on doit pouvoir piocher une carte");
        }
        if(!p.peutPiocher(81)) {
            throw new TestException("Le paquet contient 81 cartes, on doit pouvoir piocher 81 cartes");
        }
    }

    public static void testPaquetEstVide() {
        Paquet p = new Paquet(selectCouleurs(3), 3, selectFigures(3), selectTextures(3));
        if(p.estVide()) {
            throw new TestException("Le paquet contient 81 cartes, il n'est pas vide.");
        }
        p.piocher(40);
        if(p.estVide()) {
            throw new TestException("Le paquet contient 41 cartes, il n'est pas vide.");
        }
    }

    public static void testTailleTable() {
        Table table = new Table(3, 3);
        if(table.getTaille() != 9) {
            throw new TestException(String.format("etudiant.Table 3x3 : taille 9 attendue (%s obtenu)", table.getTaille()));
        }
        table = new Table(2, 3);
        if(table.getTaille() != 6) {
            throw new TestException(String.format("etudiant.Table 2x3 : taille 6 attendue (%s obtenu)", table.getTaille()));
        }
        table = new Table(4, 9);
        if(table.getTaille() != 36) {
            throw new TestException(String.format("etudiant.Table 4x9 : taille 36 attendue (%s obtenu)", table.getTaille()));
        }
    }

    public static void testCarteExisteTable() {
        Table table = new Table(3, 3);
        if(!table.carteExiste(new Coordonnees(1,1))) {
            throw new TestException("etudiant.Table 3x3, la carte située aux coorodnnées (1,1) doit exister !");
        }
        if(table.carteExiste(new Coordonnees(3,4))) {
            throw new TestException("etudiant.Table 3x3, la carte située aux coorodnnées (3,4) ne doit pas exister !");
        }
    }

    public static void testFormatValidesCoordonnees() {
        if(!Coordonnees.formatEstValide("1,2")) {
            throw new TestException("1,2 est un format valide pour des coordonnées !");
        }
        if(Coordonnees.formatEstValide("1,2,3")) {
            throw new TestException("1,2,3 n'est pas un format valide pour des coordonnées !");
        }
        if(Coordonnees.formatEstValide("12")) {
            throw new TestException("12 n'est pas un format valide pour des coordonnées !");
        }
        if(Coordonnees.formatEstValide("hello,world")) {
            throw new TestException("hello world n'est pas un format valide pour des coordonnées !");
        }
        if(Coordonnees.formatEstValide("1 2")) {
            throw new TestException("1 2 n'est pas un format valide pour des coordonnées !");
        }
    }

    private static void verifE3C(Carte c1, Carte c2, Carte c3, boolean attendu) {
        boolean testE3C = Jeu.estUnE3C(new Carte[]{c1, c2, c3});
        if(testE3C && !attendu) {
            throw new TestException(String.format("%s, %s, %s n'est pas un E3C", c1, c2, c3));
        }
        if(!testE3C && attendu) {
            throw new TestException(String.format("%s, %s, %s est un E3C", c1, c2, c3));
        }
    }

    public static void testE3C() {
        Carte c1, c2, c3;

        c1 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        c2 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        c3 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        verifE3C(c1,c2,c3, true);

        c1 = new Carte(getCouleur(2), 1, getFigure(0), getTexture(0));
        c2 = new Carte(getCouleur(2), 2, getFigure(2), getTexture(1));
        c3 = new Carte(getCouleur(2), 3, getFigure(1), getTexture(2));
        verifE3C(c1,c2,c3, true);

        c1 = new Carte(getCouleur(1), 1, getFigure(2), getTexture(1));
        c2 = new Carte(getCouleur(1), 2, getFigure(2), getTexture(2));
        c3 = new Carte(getCouleur(1), 3, getFigure(2), getTexture(0));
        verifE3C(c1,c2,c3, true);

        c1 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        c2 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        c3 = new Carte(getCouleur(1), 1, getFigure(2), getTexture(1));
        verifE3C(c1,c2,c3, false);

        c1 = new Carte(getCouleur(2), 2, getFigure(2), getTexture(1));
        c2 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        c3 = new Carte(getCouleur(2), 1, getFigure(2), getTexture(1));
        verifE3C(c1,c2,c3, false);

        c1 = new Carte(getCouleur(1), 1, getFigure(1), getTexture(1));
        c2 = new Carte(getCouleur(2), 2, getFigure(2), getTexture(2));
        c3 = new Carte(getCouleur(1), 3, getFigure(1), getTexture(1));
        verifE3C(c1,c2,c3, false);
    }
}
