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
        // if( this.couleur.equals("Rouge" )){return "\033[0;31m"+ "O" +"\033[0m";}
        // if( this.couleur.equals("Jaune" )){return "\033[0;33m"+ "O" +"\033[0m";}
        // if( this.couleur.equals("Vert"  )){return "\033[0;32m"+ "O" +"\033[0m";}
        // if( this.couleur.equals("Bleu"  )){return "\033[0;34m"+ "O" +"\033[0m";}
        // if( this.couleur.equals("Mauve" )){return "\033[0;35m"+ "O" +"\033[0m";}
        // if( this.couleur.equals("Cyan"  )){return "\033[0;36m"+ "O" +"\033[0m";}

        return "O";
    }
}
