public class Joueur
{
  private static String[] tabCouleurs = new String[] {"Rouge", "Jaune", "Vert", "Bleu", "Violet", "Rose"};
  private static int nbJoueurs;

  private Robot robot1;
  private Robot robot2;
  private int   nbPoints;
  private Base  base;
  private Ordre[] stockOrdre;
  private String couleur;

  public Joueur()
  {
    this.nbPoints = 0;
    this.stockOrdre = new Ordre[8];

    for (int i=0 ; i<8 ; i++)
      this.stockOrdre[i] = Ordre.values()[i];

    this.couleur = Joueur.tabCouleurs[Joueur.nbJoueurs++];
  }

  public Robot  getRobot(int i) { if (i == 1) return this.robot1; else return this.robot2; }
  public int    getNbPoints()   { return this.nbPoints;      }
  public Base   getBase()       { return this.base;          }
  public Ordre  getOrdre(int i) { return this.stockOrdre[i]; }
  public String getCouleur()    { return this.couleur;       }

  public void setNbPoints(int i) { this.nbPoints = i; }

  public void attributionRobot(String couleur, Robot robot)
  {
    if(couleur.equals(this.couleur))
    {
      this.robot1 = robot;
    }
  }

}
