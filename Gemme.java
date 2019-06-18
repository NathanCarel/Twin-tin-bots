public class Gemme extends Tuile
{
    private String couleur;

    public Gemme(String type, int x, int y, String couleur)
    {
        super(type, x, y);
        this.couleur = couleur;
    }

    public String getCouleur() { return this.couleur; }
}
