/*----------------------------------------------------------*/
/*                CLASSE QUI CREE UN JOUEUR                 */
/*----------------------------------------------------------*/

public class Joueur
{

	//ATTRIBUTS

	private static final String[] tabCouleurs = new String[] {"rouge", "jaune", "vert", "bleu", "mauve", "cyan"};

	private static int nbJoueurs;

	private int     nbPoints;
	private Base    base;
	private String  couleur;
	private Ordre[] stockOrdre;
	private Robot[] tabRobot = new Robot[2];


	//CONSTRUTEURS

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


	//METHODES

	//Attribue les robots aux joueurs
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


	//Effectue les actions des deux joueurs
	public void actionsRobots()
	{
		this.tabRobot[0].faireActions();
		this.tabRobot[1].faireActions();
	}


	//Effectue une action d'un robot
	public void actionRobot(int numRobot, int ordre)
	{
		this.tabRobot[numRobot].faireUneAction(ordre);
	}


	//ACCESSEURS

	public int     getNbPoints  ()      { return this.nbPoints     ; }
	public Base    getBase      ()      { return this.base         ; }
	public Robot   getRobot     (int i) { return this.tabRobot[i-1]; }
	public Ordre   getOrdre     (int i) { return this.stockOrdre[i]; }
	public String  getCouleur   ()      { return this.couleur      ; }
	public Ordre[] getStockOrdre()      { return this.stockOrdre   ; }


	//MODIFICATEUR

	public void setNbPoints(int i)  { this.nbPoints = i; }
}