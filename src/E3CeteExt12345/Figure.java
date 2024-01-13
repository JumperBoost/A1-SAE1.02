package E3CeteExt12345;

public enum Figure {

    /**
     * Représente la figure (forme) d'une Carte : ovale , triangle ...
     */

    CARRE('■'),
    LOSANGE('⧫'),
    CERCLE('O');

    private static int nbEnum = Figure.values().length;

    private char figureChar;

    private Figure(char figureChar) {
        this.figureChar = figureChar;
    }

    public char getFigureChar() {
        return this.figureChar;
    }

    public static int getNbEnumTotal() {
        return nbEnum;
    }

    /**
     * Action : retourne un tableu de Figures dont les ordinaux sont compris entre 
     * begin et end(exclus).
     * Prerequis : begin < end
     * 0 <= begin < nb d'ENUM dans la class (Class.values().length ou getNbEnumTotal)
     * 0 < end <= nb d'ENUM dans la class
     * @param begin
     * @param end
     * @return
    */
    public static Figure[] valuesInRange(int begin, int end) {
        Figure[] tabFigures = new Figure[end - begin];
        for (int i = begin; i < end; i++) {
            tabFigures[i - begin] = Figure.values()[i];
        }
        return tabFigures;
    }



}
