public class Robot extends Tuile
{
    private static char[] tabOrientation = new char[] {'N', 'E', 'S', 'O'};
    private int orientation = 0;
    private String couleur;
    private Gemme  gemme;
    private Ordre[] tabOrdre;

    public Robot(String type, int x, int y, String couleur, char orientation)
    {
        super(type, x, y);
        this.couleur = couleur;
        this.tabOrdre = new Ordre[3];

        switch(orientation)
        {
            case '^': this.orientation = 0; break;
            case '>': this.orientation = 1; break;
            case 'v': this.orientation = 2; break;
            case '<': this.orientation = 3; break;
            default: ;
        }
    }

    public String getCouleur() { return this.couleur; }

    public void setOrientation(int orientation) { this.orientation = orientation; };

    public void  setGemme(Gemme gemme) { this.gemme = gemme; }
    public Gemme getGemme()            { return this.gemme;  }

    public void  setOrdre(Ordre ordre, int indice) { this.tabOrdre[indice] = ordre; }
    public Ordre[] getTabOrdre()                   { return this.tabOrdre;          }

    public void avancer(int nbCases)
    {
        for(int i = 0; i<nbCases; i++)
            switch (this.orientation)
            {
                case 0: this.posX--; break;
                case 1: this.posY++; break;
                case 2: this.posX++; break;
                case 3: this.posY--; break;
            }
    }

    public void tourner(int direction)
    {
        this.orientation = Robot.tabOrientation[(this.orientation+direction)%4];
    }

    public void prendreCristal()
    {
        switch (this.orientation)
        {
            //case 0: if(this.posX-1 == Tuile.getGemme().getType().equals("Gemme")); break;
            case 1: this.posY++; break;
            case 2: this.posX++; break;
            case 3: this.posY--; break;
        }
    }
}
