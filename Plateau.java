/*
	Projet_tut
	@author Tristan Bassa
	17/06/2019
*/

import java.util.*; 
import iut.algo.*;

public class Plateau
{
	private Controleur ctrl;
	private int        nbJoueur;
	private int        nbCristaux;
	private Joueur[]   ensJoueur;


	public Plateau(Controleur ctrl, int nbJoueur)
	{
		this.ctrl      = ctrl;
		this.nbJoueur  = nbJoueur;
		this.ensJoueur = new Joueur[nbJoueur];

	}

	public String getPlateau2()
	{
		return "Plateau 2";
	}

	public String getPlateau3()
	{
		return "Plateau 3";

	}

	public String getPlateau4()
	{
		return "Plateau 4";

	}

	public String getPlateau5()
	{
		return "Plateau 5";

	}

	public String getPlateau6()
	{
		return "Plateau 6";

	}

	public int getNbJoueur()
	{
		return this.nbJoueur;
	}

	public void initPlateau()
	{

	}
}