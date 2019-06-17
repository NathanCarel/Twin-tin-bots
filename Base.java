public class Base extends Tuile
{
    private String couleur;

    public Base(String type, int x, int y, String couleur)
    {
        super(type, x, y);
        this.couleur = couleur;
    }

    public String getCouleur() { return this.couleur; }

    public String toString()
    {
        if( this.couleur.equals("Rouge" )){return "A";}
        if( this.couleur.equals("Jaune" )){return "B";}
        if( this.couleur.equals("Vert"  )){return "C";}
        if( this.couleur.equals("Bleu"  )){return "D";}
        if( this.couleur.equals("Violet")){return "E";}
        if( this.couleur.equals("Rose"  )){return "F";}

        return "";
    }
}
