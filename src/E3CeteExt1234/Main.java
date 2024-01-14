package E3CeteExt1234;

public class Main {

    /**
     * Action : lance une partie de jeu "E3Cète"
     */
    public static void main(String[] args) {
        Ut.afficherSL("Bienvenue dans le jeu ExCete !");
        Ut.afficher("Voulez-vous jouer avec les paramètres par défaut (O/N) ? ");
        char reponse = Ut.saisirCaractere();

        if(reponse == 'N' || reponse == 'n') {
            Ut.afficherSL("Vous avez choisi de personnaliser les paramètres.");
            boolean valide = false;
            int sousEnsemble, hauteur, largeur;
            do {
                Ut.afficher("Veuillez saisir le sous-ensemble désiré (1-20) : ");
                sousEnsemble = Ut.saisirEntierMinMax(1, 20);
                Ut.afficher("Veuillez saisir la hauteur désirée (1+) : ");
                hauteur = Ut.saisirEntierMinMax(1, Integer.MAX_VALUE);
                Ut.afficher("Veuillez saisir la largeur désirée (1+) : ");
                largeur = Ut.saisirEntierMinMax(1, Integer.MAX_VALUE);

                // On vérifie que la taille de la table soit suffisante pour faire un ExC avec le sous-ensemble choisi
                if(hauteur*largeur >= sousEnsemble) {
                    // On vérifie que la taille de la table soit suffisante pour contenir toutes les cartes du sous-ensemble choisi
                    if(hauteur*largeur <= Math.pow(sousEnsemble, 4)) {
                        valide = true;
                    } else {
                        Ut.afficherSL("La taille de la table est trop grande pour contenir toutes les cartes du sous-ensemble choisi, veuillez réessayer.");
                    }
                } else {
                    Ut.afficherSL("La taille de la table est insuffisante pour contenir toutes les cartes, veuillez réessayer.");
                }
            } while (!valide);
            Ut.afficherSL("Vous venez de lancer un jeu de E" + sousEnsemble + "Cète avec une table de " + hauteur + "x" + largeur + " cartes.");
            Ut.pause(1000);
            Jeu jeu = new Jeu(sousEnsemble, hauteur, largeur);
            jeu.jouer();
        } else {
            Ut.afficherSL("Vous avez choisi les paramètres par défaut.");
            Ut.pause(500);
            Jeu jeu = new Jeu();
            jeu.jouer();
        }
    }

}
