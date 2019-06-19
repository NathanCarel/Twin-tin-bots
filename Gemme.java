public class Gemme extends Tuile
{
    private String couleur;

    public Gemme(String type, int x, int y, String couleur)
    {
        super(type, x, y);
        this.couleur = couleur;
    }

    public String getCouleur() { return this.couleur; }

    public String toString()
    {
    	// if( this.couleur.equals("Bleu"  )) {return "\033[0;34m"+ "*" +"\033[0m";}
        // if( this.couleur.equals("Vert"  )) {return "\033[0;32m"+ "*" +"\033[0m";}
        // if( this.couleur.equals("Violet")) {return "\033[0;35m"+ "*" +"\033[0m";}

        return "*";
    }
}
