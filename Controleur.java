/*
import iut.algo.*;
import java.util.*;
import javax.sound.sampled.Control;
	Projet_tut
	@author Tristan Bassa
	17/06/2019
*/

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

	public Plateau getPlateau()
	{
		return Controleur.plateau;
	}

	public void afficherPlateau(Tuile[][] tabTuiles)
	{
		 Controleur.ihm.afficherPlateau(tabTuiles);
	}

	public String getElement()
	{
		return Controleur.plateau.getElement();
	}

	public int initNbJoueur()
	{
		return Controleur.ihm.initNbJoueur();
	}

	public int premierJoueur(Joueur[] ensJoueurs)
	{
		return Controleur.ihm.premierJoueur(ensJoueurs);
	}

	public static void main(String[] args)
	{
		new Controleur();
		while (true)
		{
			Controleur.ihm.afficherPlateau(Controleur.plateau.getNbJoueur());
			Controleur.ihm.afficherChoix();
		}
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