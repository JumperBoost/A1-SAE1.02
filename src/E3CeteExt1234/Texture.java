package E3CeteExt1234;

public enum Texture {

    /**
     * Représente la texture d'une Carte : pleine , à pois...
     */

    VIDE(' '),
    TIRET('-'),
    RAYURE('/'),
    RAYURE_INVERSE('\\'),
    BLK14('░'),
    BLK34('▓'),
    POINTS_DOUBLE(':'),
    POIS('•'),
    LIGNES_CROISEES('┼'),
    LIGNES_DOUBLE_VERTICAL('║'),
    LIGNES_POINTILLEES('.'),
    CROIX('+'),
    TRAITS_CROISES_BAS('┬'),
    VAGUES_LEGERES('~'),
    EQUIVALENT('≡'),
    ZIGZAG('≈'),
    TRAITS_CROISES_HAUT('┴'),
    LIGNES_POINTS_CROISEES('╋'),
    LIGNES_DOUBLE_HORIZONTAL('═'),
    LIGNES_DOUBLE_DIAGONALES('╳');
   
   

    private static int nbEnum = Texture.values().length;

    public static int getNbEnumTotal() {
        return nbEnum;
    }

    private char textureChar;

    private Texture(char textureChar) {
        this.textureChar = textureChar;
    }

    public char getTextureChar() {
        return this.textureChar;
    }

    /**
     * Action : retourne un tableu de textures dont les ordinaux sont compris entre 
     * begin et end(exclus).
     * Prerequis : begin < end
     * 0 <= begin < nb d'ENUM dans la class (Class.values().length ou getNbEnumTotal)
     * 0 < end <= nb d'ENUM dans la class
     * @param begin
     * @param end
     * @return
    */
    public static Texture[] valuesInRange(int begin, int end) {
        Texture[] tabTextures = new Texture[end - begin];
        for (int i = begin; i < end; i++) {
            tabTextures[i - begin] = Texture.values()[i];
        }
        return tabTextures;
    }


    public static Texture[] randomValues(int nb) {
        Texture[] tabTextures = new Texture[nb];
        int index = 0;

        Texture texture;
        while (index < nb) {
            texture = Texture.values()[Ut.randomMinMax(0, Texture.getNbEnumTotal()-1)];
            int i = 0;
            while (i < index && tabTextures[i] != texture) {
                i++;
            }

            // Si i == index, alors la texture n'est pas dans le tableau
            if (i == index) {
                tabTextures[index] = texture;
                index++;
            }
        }
        return tabTextures;
    }
     
}
