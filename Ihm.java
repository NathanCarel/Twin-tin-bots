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

	public void afficherChoix()
	{

		System.out.println( "Que voulez-vous faire :");
		System.out.println();
		System.out.println("C : Changer un programme");
		System.out.println("P : Passer");

		char choix = Clavier.lire_char(); 

		switch (choix)
		{
			case 'C' : this.afficherAction(); break;
			case 'P' : break;
		}
	}

	public String afficherAction()
	{
		System.out.println("Quelle ordre voulez-vous donner ?");

		System.out.println("M - Modifier une action"         );
		System.out.println("E - Echanger deux actions"       );
		System.out.println("A - Ajouter/remplacer une action");
		System.out.println("R - Retirer une action"          );
		System.out.println("V - Vider"                       );
		System.out.println("P - Ne rien faire"               );

		char action = Clavier.lire_char(); 

		switch(action)
		{
			case 'M' : this.ctrl.getPlateau().getJoueurActif().modifierAction(); break;
			case 'E' : this.ctrl.getPlateau().getJoueurActif().echangerAction(); break;
			case 'A' : this.ctrl.getPlateau().getJoueurActif().ajouterAction (); break;
			case 'R' : this.ctrl.getPlateau().getJoueurActif().retirerAction (); break;
			case 'V' : this.ctrl.getPlateau().getJoueurActif().vider         (); break;
			case 'P' : break;
		}
	}
}