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

	public int premierJoueur(Joueur[] ensJoueur)
	{
		int joueur = 0;

		do
		{
			System.out.println("Quel est le joueur qui commence : ");
			for(int i = 0; i < ensJoueur.length; i++)
			{
				System.out.println("[" + i + "] " + ensJoueur[i].getCouleur());
			}
			joueur = Clavier.lire_int();
		}while(joueur < 0 || joueur > ensJoueur.length);

		return joueur;
	}

	public String afficherPlateau(int nbJoueur)
	{
		String plateau = "";
		System.out.println(this.ctrl.getPlateau());
		return plateau;
	}

	public void afficherChoix()
	{

		System.out.println( "Que voulez-vous faire :");
		System.out.println();
		System.out.println("C : Changer un programme");
		System.out.println("P : Passer");

		char choix = Clavier.lire_char();
		choix.toUpperCase();

		switch (choix)
		{
			case 'C' : this.afficherAction(); break;
			case 'P' : break;
		}
	}

	public void afficherAction()
	{
		System.out.println();
		System.out.println("Quelle ordre voulez-vous donner ?");

		System.out.println("M - Modifier une action"          );
		System.out.println("E - Echanger deux actions"        );
		System.out.println("A - Ajouter/remplacer une action" );
		System.out.println("R - Retirer une action"           );
		System.out.println("V - Vider"                        );
		System.out.println("P - Ne rien faire"                );

		char action = Clavier.lire_char();
				action.toUpperCase();


		switch(action)
		{
			case 'M' : this.afficherNbAction(); break;
			case 'E' : this.ctrl.getPlateau().getJoueurActif().getRobot1().echangerAction(); break;
			case 'A' : this.ctrl.getPlateau().getJoueurActif().getRobot1().ajouterAction (); break;
			case 'R' : this.ctrl.getPlateau().getJoueurActif().getRobot1().retirerAction (); break;
			case 'V' : this.ctrl.getPlateau().getJoueurActif().getRobot1().vider         (); break;
			case 'P' : break;
		}
	}

	public void afficherNbAction()
	{
		System.out.println();
		System.out.println("Quelle action voulez-vous modifier ?"); 
		System.out.println("[1]  : La premiere" );
		System.out.println("[2]  : La seconde"  );
		System.out.println("[3]  : La troisieme");
		System.out.println("[10] : Aucune"      );

		int nbAction = Clavier.lire_int();

		switch(nbAction)
		{
			case 1  : this.ctrl.getPlateau().getJoueurActif().getRobot1().modifierAction(1); break;
			case 2  : this.ctrl.getPlateau().getJoueurActif().getRobot1().modifierAction(2); break;
			case 3  : this.ctrl.getPlateau().getJoueurActif().getRobot1().modifierAction(3); break;
			case 10 : break;
		}
	}
}
