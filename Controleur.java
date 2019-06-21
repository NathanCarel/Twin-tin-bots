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

	public Tuile[][] getTabTuiles()
	{
		return Controleur.plateau.getTabTuiles();
	}

	public void afficherPlateau(Tuile[][] tabTuiles)
	{
		 Controleur.ihm.afficherPlateau(tabTuiles);
	}

	public void afficherChoix(boolean premierTour)
	{
		Controleur.ihm.afficherChoix(premierTour);
	}

	public void avancerScenario(String ordre)
	{
		Controleur.ihm.avancerScenario(ordre);
	}

	public int premierJoueur(Joueur[] ensJoueurs)
	{
		return Controleur.ihm.premierJoueur(ensJoueurs);
	}

	public static void main(String[] args)
	{
		new Controleur();
		Controleur.plateau.jouer();
	}
}
