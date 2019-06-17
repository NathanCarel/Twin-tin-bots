/*
	Projet_tut
	@author Tristan Bassa
	17/06/2019
*/

import java.util.*; 
import iut.algo.*;

public class Controleur
{
	private static Ihm     ihm;
	private static Plateau plateau;

	public Controleur()
	{
		Controleur.ihm      = new Ihm    (this);
		Controleur.plateau  = new Plateau(this, Controleur.ihm.initNbJoueur());		
	}

	public String getPlateau2()
	{
		return Controleur.plateau.getPlateau2();
	}

	public String getPlateau3()
	{
		return Controleur.plateau.getPlateau3();
	}

	public String getPlateau4()
	{
		return Controleur.plateau.getPlateau4();
	}

	public String getPlateau5()
	{
		return Controleur.plateau.getPlateau5();
	}

	public String getPlateau6()
	{
		return Controleur.plateau.getPlateau6();
	}



	public static void main(String[] args)
	{
		new Controleur();
		Controleur.ihm.afficherPlateau(Controleur.plateau.getNbJoueur());
	}

	// public String getNomJoueur()
	// {
	// 	return Controleur.metier.getNomJoueur();
	// }

	// public String getInfosJoueur()
	// {
	// 	return Controleur.metier.getInfosJoueur();
	// }
}