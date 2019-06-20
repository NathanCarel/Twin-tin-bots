public class Gemme extends Tuile
{
    private String couleur;
    private int    gain;

    public Gemme(String type, int x, int y, String couleur)
    {
        super(type, x, y);
        this.couleur = couleur;
        this.setGain(this.couleur);
    }

    public String getCouleur() { return this.couleur; }

    public void   setGain   (String couleur)
    {
      switch(couleur)
      {
        case "bleu" : this.gain = 2; break;
        case "vert" : this.gain = 3; break;
        case "mauve": this.gain = 4; break;
        default     : this.gain = 0; break;
      }
    }

    public int getGain(){ return this.gain;}

    public String toString()
    {
      return Utils.couleur(this.couleur,"normal","*");
    }
}
