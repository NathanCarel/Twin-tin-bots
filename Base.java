/*----------------------------------------------------------*/
/*     CLASSE QUI HERITE DE TUILE ET QUI CREE UNE BASE      */
/*----------------------------------------------------------*/

public class Base extends Tuile
{

    //ATTRIBUTS

    private String couleur;


    //CONSTRUCTEUR

    public Base(String type, int x, int y, String couleur)
    {
        super(type, x, y);
        this.couleur = couleur;
    }


    //ACCESSEURS

    public String getCouleur() { return this.couleur; }


    //AFFICHAGE

    public String toString() { return Utils.couleur(this.couleur,"normal","O"); }
}
