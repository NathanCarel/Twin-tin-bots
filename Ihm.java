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
		System.out.println("Choisissez le nombre de joueur : ");

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

		System.out.println("Quel est le joueur qui commence : \n");
		for(int i = 0; i < ensJoueur.length; i++)
		{
			System.out.println("[" + (i+1) + "] " + ensJoueur[i].getCouleur());
		}

		joueur = Clavier.lire_int() - 1;

		while (joueur < 0 || joueur >= ensJoueur.length)
		{
			System.out.println("Ce numéro n'est pas correct, veuillez recommencer.");
			joueur = Clavier.lire_int() - 1;
		}

		return joueur;
	}

	public String afficherPlateau(int nbJoueur)
	{
		String plateau = "";
		//System.out.println(this.ctrl.getPlateau());
		return plateau;
	}


	public void afficherChoix()
	{
		this.affichertabOrdre();
		System.out.println("Vous avez " + this.ctrl.getPlateau().getJoueurActif().getNbPoints() + " points\n" );
		System.out.println( "\nJoueur " + this.ctrl.getPlateau().getJoueurActif().getCouleur() + " que voulez-vous faire :\n");
		System.out.println("[1] : Changer le programme du robot 1");
		System.out.println("[2] : Changer le programme du robot 2");
		System.out.println("[P]asser");

		char choix = Clavier.lireString().toUpperCase().charAt(0);

		while (choix != '1' && choix != '2' && choix != 'P')
		{
			System.out.println("Choix incorrect !");
			choix = Clavier.lireString().toUpperCase().charAt(0);
		}

		switch (choix)
		{
			case '1' : this.afficherAction(1); break;
			case '2' : this.afficherAction(2); break;
			case 'P' : break;
		}
	}

	public void afficherAction(int numRobot)
	{
		Joueur jActuel = this.ctrl.getPlateau().getJoueurActif();

		Robot robot = this.ctrl.getPlateau().getJoueurActif().getRobot(numRobot);

		System.out.println("\nQuel ordre voulez-vous donner ?\n");

		System.out.println("[A]jouter une action"           );
		System.out.println("[E]changer deux actions"        );
		System.out.println("[R]etirer une action"           );
		System.out.println("[V]ider les 3 actions"          );
		System.out.println("[N]e rien faire"                );

		char action = Clavier.lireString().toUpperCase().charAt(0);

		while (action != 'A' && action != 'E' && action != 'R' && action != 'V' && action != 'N')
		{
			System.out.println("Lettre incorrecte !\n");
			action = Clavier.lireString().toUpperCase().charAt(0);
		}

		int numOrdre      = 0;
		int positionOrdre = 0;


		switch(action)
		{
			case 'A' :  this.afficherNbAction('A', robot);

			do
			{ positionOrdre = Clavier.lire_int()-1; }
			while (positionOrdre < 0 || positionOrdre > 2);

			System.out.println("\nChoisissez un ordre:\n");

			for (int i=0; i<6; i++)
			System.out.println(String.format("%-23s", "[" + (i+1) + " ] - " + jActuel.getOrdre(i).getType()) +  " (" + jActuel.getOrdre(i).getNbExemplaires() + ") "); //Affiche tous les ordres

			System.out.println("[10] - Annuler");
			numOrdre = Clavier.lire_int()-1;

			if(numOrdre == 9) { this.afficherAction(numRobot); break; }
			while ( (numOrdre < 0 || numOrdre > 5) || jActuel.getOrdre(numOrdre).getNbExemplaires() <= 0)
			{
				if(jActuel.getOrdre(numOrdre).getNbExemplaires() <= 0) { System.out.println("Carte déja toutes utilisées");}
				else                                                   { System.out.println("Numéro incorrect");           }
				numOrdre = Clavier.lire_int()-1;
				if(numOrdre == 9) { this.afficherAction(numRobot); break; }
			}
			if(numOrdre > 8) { break; }
			if (!jActuel.getOrdre(positionOrdre).getType().equals(enumOrdre.AUCUN.getType()))
			{ modifStockJoueur(jActuel, robot, 1, positionOrdre); }

			robot.setOrdre( new Ordre (enumOrdre.values()[numOrdre]), positionOrdre);
			modifStockJoueur(jActuel, robot, -1, positionOrdre);

			this.affichertabOrdre();

			break;


			case 'E' :  this.afficherNbAction('E', robot);
			int ordre1 = 0; int ordre2 = 0;

			do
			{
				System.out.print("Premier ordre à échanger: ");
				ordre1 = Clavier.lire_int() - 1;
				System.out.print("Second ordre à échanger : ");
				ordre2 = Clavier.lire_int() - 1;
			} while((ordre1 < 1 || ordre1 > 3) && (ordre2 < 1 || ordre2 > 3));

			robot.echangerOrdre(ordre1, ordre2);
			this.affichertabOrdre();
			break;


			case 'R' :  this.affichertabOrdre();
			this.afficherNbAction('R', robot);

			do
			{ positionOrdre = Clavier.lire_int()-1; }
			while (positionOrdre < 0 || positionOrdre > 2);

			modifStockJoueur(jActuel, robot, 1, positionOrdre);

			robot.setOrdre(new Ordre (enumOrdre.AUCUN), positionOrdre);
			this.affichertabOrdre();
			break;


			case 'V' :	this.affichertabOrdre();
			for (int i=0; i<3; i++)
			{
				modifStockJoueur(jActuel, robot, 1, i);
				robot.setOrdre(new Ordre (enumOrdre.AUCUN), i);
			}
			this.affichertabOrdre();
			break;

			case 'P' : break;
		}
	}

	public void modifStockJoueur(Joueur jActuel, Robot robot, int choix, int positionOrdre) //A mettre dans Joueur (pour MVC)
	{
		for (int o=0; o<jActuel.getStockOrdre().length; o++)
		if (robot.getOrdre(positionOrdre).getType().equals(jActuel.getOrdre(o).getType()))
		{
			if (choix == 1)  jActuel.getOrdre(o).ajouter();
			if (choix == -1) jActuel.getOrdre(o).retirer();
		}
	}

	public void affichertabOrdre()
	{

		Joueur jActuel = this.ctrl.getPlateau().getJoueurActif();
		String ordre = "";

		System.out.print("+---------------------------------------------------------+");
		System.out.print(String.format("%-3s", " ")+"+---------------------------------------------------------+\n");
		System.out.print(String.format("%-58s",("| Robot " + jActuel.getCouleur())) +                        "|   ");
		System.out.print(String.format("%-65s",("| \033[4mRobot " + jActuel.getCouleur()) + "\033[0m") +     " |\n");
		System.out.print("+---------------------------------------------------------+");
		System.out.print(String.format("%-3s", " ")+"+---------------------------------------------------------+\n");

		for (int i = 1; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				System.out.print("| ");
				if(jActuel.getRobot(i).getOrdre(j) != null ) { ordre = jActuel.getRobot(i).getOrdre(j).toString(); }
				System.out.print(String.format("%-16s",ordre) + " ");
				ordre = "";
			}

			System.out.print(String.format("%-5s"," | "));
		}
		System.out.print("\n+---------------------------------------------------------+");
		System.out.print(String.format("%-3s", " ")+"+---------------------------------------------------------+\n");
	}

	public void afficherNbAction(char nomAction, Robot robot)
	{
		System.out.println("\n" + this.getNomAction(nomAction) + "\n");
		System.out.println("[1]  : " + robot.getOrdre(0) );
		System.out.println("[2]  : " + robot.getOrdre(1) );
		System.out.println("[3]  : " + robot.getOrdre(2) );
		//System.out.println("[4]  : Annuler"     );

	}

	public String getNomAction(char nomAction)
	{
		switch(nomAction)
		{
			case 'A' : return "Où voulez-vous ajouter votre action ?" ;
			case 'E' : return "Quelles actions voulez-vous échanger ?";
			case 'R' : return "Quelle action voulez-vous retirer ?"   ;
		}
		return "";
	}

	public void afficherPlateau(Tuile[][] tabTuiles)
	{
		String chaine = "";
		boolean test;

		for (int i = 0; i < tabTuiles.length; i++)
		{
			test = true;
			for (int j = 0; j < tabTuiles[i].length; j++)
			{
				if (i <= tabTuiles.length / 2) {
					if (j > 1 && tabTuiles[i][j - 1] != null && tabTuiles[i][j] == null)
					{
						chaine += "+\n";
						test = false;
					}
					if (test && j + 1 >= tabTuiles[i].length)
					{
						chaine += "+---+\n";
						test = false;
					}
					if (test)
					if (tabTuiles[i][j] != null)
					chaine += "+---";
					else
					chaine += "    ";
				}
				if (i > tabTuiles.length / 2) {
					if (j > 1 && tabTuiles[i - 1][j - 1] != null && tabTuiles[i - 1][j] == null)
					{
						chaine += "+\n";
						test = false;
					}
					if (test && i > 1 && j + 1 >= tabTuiles[i - 1].length)
					{
						chaine += "+---+\n";
						test = false;
					}
					if (test)
					if (i > 1 && tabTuiles[i - 1][j] != null)
					chaine += "+---";
					else
					chaine += "    ";
				}
			}

			for (int j = 0; j < tabTuiles[i].length; j++)
			{
				if (tabTuiles[i][j] == null) { chaine += "    ";                       }
				else                         { chaine += "| " + tabTuiles[i][j] + " "; }

				if (j > 1 && j + 1 < tabTuiles[i].length)
				{
					if (tabTuiles[i][j - 1] != null && tabTuiles[i][j + 1] == null)
					{
						chaine += "|";
						break;
					}
				}
				if (j + 1 >= tabTuiles[j].length) { chaine += "|"; }
			}
			chaine += "\n";
		}

		test = true;
		for (int j = 0; j < tabTuiles[tabTuiles.length - 1].length; j++)
		{
			if (j > 1 && tabTuiles[tabTuiles.length - 1][j - 1] != null && tabTuiles[tabTuiles.length - 1][j] == null)
			{
				chaine += "+\n";
				test = false;
			}
			if (test && j + 1 >= tabTuiles[tabTuiles.length - 1].length)
			{
				chaine += "+\n";
				test = false;
			}
			if (test)
			if (tabTuiles[tabTuiles.length - 1][j] != null) { chaine += "+---"; }
			else                                            { chaine += "    "; }
		}
		System.out.println(chaine);
	}
}
