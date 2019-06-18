public class Robot extends Tuile
{
    private static char[]   tabDir = {'N','E','S','O'};
    private        String   couleur;
    private        char     dir;
    private        Ordre[]  ensOrdre;

    public Robot(String type, int x, int y, String couleur)
    {
        super(type, x, y);
        this.couleur = couleur;

    }

    public String getCouleur() { return this.couleur; }


    public void modifierAction(int numAction)
	{

	}

	public void echangerAction()
	{

	}

	public void ajouterAction()
	{

	}

	public void retirerAction()
	{

	}

	public void vider()
	{

	}

	public String toString()
    {
        if( this.couleur.equals("Rouge" )){return "1";}
        if( this.couleur.equals("Jaune" )){return "2";}
        if( this.couleur.equals("Vert"  )){return "3";}
        if( this.couleur.equals("Bleu"  )){return "4";}
        if( this.couleur.equals("Violet")){return "5";}
        if( this.couleur.equals("Rose"  )){return "6";}

        return "";
    }

}
