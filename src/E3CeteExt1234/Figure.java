package E3CeteExt1234;

public enum Figure {

    /**
     * Représente la figure (forme) d'une Carte : ovale , triangle ...
     */

    CARRE('■'),
    LOSANGE('⧫'),
    CERCLE('O'),
    TRIANGLE('▲'),
    COPYR('©'),
    RECTANGLE('█'),
    SECT('§'),
    PARA('¶'),
    ASTERISQUE('*'),
    YEN('¥'),
    DOLLAR('$'),
    OSLASH('Ø'),
    SOLEIL('¤'),
    CROIX('%'),
    TRIANGLE_INVERSE('▼'),
    CENT('¢'),
    POUND('£'),
    AROBASE('@'),
    AELIG('Æ'),
    SZLIG('ß');

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


    public static Figure[] randomValues(int nb) {
        Figure[] tabFigures = new Figure[nb];
        int index = 0;

        Figure figure;
        while (index < nb) {
            figure = Figure.values()[Ut.randomMinMax(0, Figure.getNbEnumTotal()-1)];
            int i = 0;
            while (i < index && tabFigures[i] != figure) {
                i++;
            }

            // Si i == index, alors la figure n'est pas dans le tableau
            if (i == index) {
                tabFigures[index] = figure;
                index++;
            }
        }
        return tabFigures;
    }



}
