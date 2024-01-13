package E3CeteExt12345;

public enum Texture {

    /**
     * Représente la texture d'une Carte : pleine , à pois...
     */

    VIDE(' '),
    TIRET('-'),
    RAYURE('/');
   
   

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
     
}
