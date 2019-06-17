public class Joueur
{
	private static int    numJoueur = 1;

	private String   nom;
	private int      nbPoint;
	private Robot    robot1;
	private Robot    robot2;
	private Action[] ensAction;

	

	public Joueur(int numJoueur)
	{
		numJoueur = Controleur.numJoueur ++;
		Controleur.numJoueur = numJoueur; 

		this.nbPoint = 0;
		this.robot1  = new Robot();
		this.robot2  = new Robot();

	}

	public void ajouterCristaux()
	{
		this.nbPoint++;
	}


}