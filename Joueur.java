public class Joueur
{
    private static String[] tabCouleurs = new String[] {"Rouge", "Jaune", "Vert", "Bleu", "Violet", "Rose"};
    private static int nbJoueurs;

    private Robot robot1;
    private Robot robot2;
    private int   nbPoints;
    private Base  base;
    private Ordre[] stockOrdre;

    public Joueur()
    {
        this.robot1 = new Robot("Robot",,,tabCouleurs[Joueur.nbJoueurs]);
        this.robot2 = new Robot("Robot",,,tabCouleurs[Joueur.nbJoueurs]);
        this.base   = new Base ("Base" ,,,tabCouleurs[Joueur.nbJoueurs]);
        this.nbPoints = 0;
        this.stockOrdre = new Ordre[8];
        Joueur.nbJoueurs++;
    }

    public Robot getRobot1()     { return this.robot1;        }
    public Robot getRobot2()     { return this.robot2;        }
    public int   getNbPoints()   { return this.nbPoints;      }
    public Base  getBase()       { return this.base;          }
    public Ordre getOrdre(int i) { return this.stockOrdre[i]; }
}
