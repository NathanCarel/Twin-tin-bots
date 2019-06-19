public class Joueur
{
  private static String[] tabCouleurs = new String[] {"Rouge", "Jaune", "Vert", "Bleu", "Violet", "Rose"};
  private static int nbJoueurs;

  private Robot robot1 = new Robot("null", 0, 0, "null", 'a');
  private Robot robot2 = new Robot("null", 0, 0, "null", 'a');
  private int   nbPoints;
  private Base  base;
  private Ordre[] stockOrdre;
  private String couleur;

  public Joueur()
  {
    this.nbPoints = 0;
    this.stockOrdre = new Ordre[8];

    for (int i=0 ; i<8 ; i++)
      this.stockOrdre[i] = new Ordre (enumOrdre.values()[i]);

    this.couleur = Joueur.tabCouleurs[Joueur.nbJoueurs++];
  }

  public Robot  getRobot(int i) { if (i == 1) return this.robot1; if (i == 2) return this.robot2; return null;}
  public int    getNbPoints()   { return this.nbPoints;      }
  public Base   getBase()       { return this.base;          }
  public Ordre  getOrdre(int i) { return this.stockOrdre[i]; }
  public String getCouleur()    { return this.couleur;       }

  public void setNbPoints(int i) { this.nbPoints = i; }

  public void attributionRobot(String couleur, Robot robot)
  {
    if(couleur.equals(this.couleur))
    {
      if (this.robot1.getType().equals("null"))
        this.robot1 = robot;
      else
        this.robot2 = robot;

      System.out.println(robot.getPosX() + "/" + robot.getPosY());
    }
  }

  public void actionsRobots()
  {
    this.robot1.actions();
    this.robot2.actions();
  }

}
