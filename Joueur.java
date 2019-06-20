public class Joueur
{
	private static final String[] tabCouleurs = new String[] {"rouge", "jaune", "vert", "bleu", "mauve", "cyan"};
	private static       int      nbJoueurs;

	private Robot[] tabRobot = new Robot[2];
	private int     nbPoints;
	private Base    base;
	private Ordre[] stockOrdre;
	private String  couleur;

	public Joueur()
	{
		this.nbPoints = 0;
		this.tabRobot[0] = new Robot("null", 0, 0, "null", 'a');
		this.tabRobot[1] = new Robot("null", 0, 0, "null", 'a');

		this.stockOrdre  = new Ordre[6];

		for (int i=0 ; i<6 ; i++)
			this.stockOrdre[i] = new Ordre (enumOrdre.values()[i]);

		this.couleur = Joueur.tabCouleurs[Joueur.nbJoueurs++];
	}

	public Robot   getRobot(int i)  { return this.tabRobot[i-1]; }
	public int     getNbPoints()    { return this.nbPoints;      }
	public Base    getBase()        { return this.base;          }
	public Ordre   getOrdre(int i)  { return this.stockOrdre[i]; }
	public Ordre[] getStockOrdre()  { return this.stockOrdre;    }
	public String  getCouleur()     { return this.couleur;       }

	public void setNbPoints(int i) { this.nbPoints = i; }

	public void attributionRobot(String couleur, Robot robot)
	{
		if(couleur.equals(this.couleur))
		{
			if (this.tabRobot[0].getType().equals("null"))
				this.tabRobot[0] = robot;
			else
				this.tabRobot[1] = robot;
		}
	}

	public void actionsRobots()
	{
		this.tabRobot[0].faireActions();
		this.tabRobot[1].faireActions();
	}
}
