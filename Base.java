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
      return Utils.couleur(this.couleur,"normal","O");
    }
}
