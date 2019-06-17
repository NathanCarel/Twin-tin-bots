/*
	Projet_tut
	@author Tristan Bassa
	17/06/2019
*/

import java.util.*;
import iut.algo.*;

public class Ihm
{
	private Controleur ctrl;

	public Ihm(Controleur ctrl)
	{
		this.ctrl = ctrl; 
		
	}

	public int initNbJoueur()
	{
		System.out.println("Choississez le nombre de joueur : ");

		int nbJoueur = Clavier.lire_int();

		while(nbJoueur <= 1 || nbJoueur >= 7 )
		{
			System.out.println("Le nombre de joueurs doit être compris entre 2 et 6");

			nbJoueur = Clavier.lire_int(); 
		}

		return nbJoueur;
	}

	public String afficherPlateau(int nbJoueur)
	{
		String plateau = "";

		switch(nbJoueur)
		{
			case 2 : plateau = this.ctrl.getPlateau2(); break;
			case 3 : plateau = this.ctrl.getPlateau3(); break;
			case 4 : plateau = this.ctrl.getPlateau4(); break;
			case 5 : plateau = this.ctrl.getPlateau5(); break;
			case 6 : plateau = this.ctrl.getPlateau6(); break;
		}
		System.out.println(plateau);
		return plateau;
	}
}