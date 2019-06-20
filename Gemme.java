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
        // if( this.couleur.equals("vert"  )) {return "\033[0;32m"+ "*" +"\033[0m";}
        // if( this.couleur.equals("bleu"  )) {return "\033[0;34m"+ "*" +"\033[0m";}
        // if( this.couleur.equals("rose"  )) {return "\033[0;35m"+ "*" +"\033[0m";}

        return "*";
    }
}
