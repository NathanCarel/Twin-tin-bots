/*
Projet_tut
@author Tristan Bassa
17/06/2019
*/

import java.util.*;
import iut.algo.*;

public class Ihm
{
	private static int        numOrdre;
	private static boolean    premierTour;
	private        Controleur ctrl;
	private        int        tour1;

	public Ihm(Controleur ctrl)
	{
		this.ctrl = ctrl;

	}

	public int initNbJoueur()
	{
		int mode;

		System.out.println("Bienvenu sur Twin Tin Bots !\n");
		System.out.println("[1] Mode Scenario");
		System.out.println("[2] Mode Normal");


		mode = Clavier.lire_int();
		while (!(mode == 1 || mode == 2))
		{
			System.out.println("Ce numéro n'est pas correct, veuillez recommencer.");
			mode = Clavier.lire_int();
		}
		if(mode == 1) return 0;

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
			System.out.println("[" + (i+1) + "] " + Utils.couleur(ensJoueur[i].getCouleur(), "normal", ensJoueur[i].getCouleur()) );
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


	public void afficherChoix(boolean premierTour)
	{
		String couleur = this.ctrl.getPlateau().getJoueurActif().getCouleur();
		String resultat;
		char   choix = 0;
		Ihm.premierTour = premierTour;
		int positionOrdre = 0;


		if (Ihm.premierTour) //Premier tour de jeu
		{
			System.out.println("C'est votre premier tour, vous devez ajouter une action sur chacun de vos robots.");

			for (int r=0; r<2; r++)
			{
				Joueur jActuel = this.ctrl.getPlateau().getJoueurActif();
				Robot robot    = jActuel.getRobot(r+1);

				this.affichertabOrdre();

				System.out.println( "\nJoueur " + Utils.couleur(couleur,"normal",couleur) + ", quelle action voulez-vous ajouter sur le robot " + (r+1) + " ?\n");

				for (int i=0; i<6; i++)
				System.out.println(String.format("%-23s", "[" + (i+1) + " ] - " + jActuel.getOrdre(i).getType()) +  " (" + jActuel.getOrdre(i).getNbExemplaires() + ") ");

				Ihm.numOrdre = Clavier.lire_int()-1;

				while (!this.verifStock(jActuel, Ihm.numOrdre))
				{
					System.out.println("Ordre déja tous utilisés");
					Ihm.numOrdre = Clavier.lire_int()-1;
				}

				this.afficherNbAction('A', robot);

				do
				{ positionOrdre = Clavier.lire_int()-1; }
				while (positionOrdre < 0 || positionOrdre > 2);

				if (!jActuel.getOrdre(positionOrdre).getType().equals(enumOrdre.AUCUN.getType()))
				{ modifStockJoueur(jActuel, robot, 1, positionOrdre); }

				robot.setOrdre( new Ordre (enumOrdre.values()[Ihm.numOrdre]), positionOrdre);
				modifStockJoueur(jActuel, robot, -1, positionOrdre);
			}

			this.tour1++;

			if (this.tour1 == Plateau.getNbJoueur()) Plateau.setPremierTour(false);
		}


		//Tour normal
		else
		{
			System.out.println("Vous avez " + this.ctrl.getPlateau().getJoueurActif().getNbPoints() + " points.\n" );
			this.affichertabOrdre();
			System.out.println( "\nJoueur " + Utils.couleur(couleur,"normal",couleur) + " que voulez-vous faire :\n");
			System.out.println("[1] : Changer le programme du robot 1");
			System.out.println("[2] : Changer le programme du robot 2");
			System.out.println("[P]asser");

			resultat = Clavier.lireString().toUpperCase();
			if(!resultat.equals("")) choix = resultat.charAt(0);

			while (choix != '1' && choix != '2' && (choix != 'P' || premierTour))
			{
				if(premierTour) System.out.println("Erreur premier tour");
				else            System.out.println("Choix incorrect !");
				resultat = Clavier.lireString().toUpperCase();
				if(!resultat.equals("")) choix = resultat.charAt(0);
			}

			switch (choix)
			{
				case '1' : this.afficherAction(1); break;
				case '2' : this.afficherAction(2); break;
				case 'P' : break;
			}
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
		System.out.println("[P]recedent"                    );

		char action = Clavier.lireString().toUpperCase().charAt(0);

		while (action != 'A' && action != 'E' && action != 'R' && action != 'V' && action != 'P')
		{
			System.out.println("Lettre incorrecte !\n");
			action = Clavier.lireString().toUpperCase().charAt(0);
		}

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

			//System.out.println("[10] - Annuler");
			Ihm.numOrdre = Clavier.lire_int()-1;

			//if(Ihm.numOrdre == 9) { this.afficherChoix(Ihm.premierTour);}

			while (!this.verifStock(jActuel, Ihm.numOrdre))
			{
				System.out.println("Ordre déja tous utilisés");
				Ihm.numOrdre = Clavier.lire_int()-1;
				//if(Ihm.numOrdre == 9) { this.afficherChoix(Ihm.premierTour); break; }
			}
			//if(Ihm.numOrdre > 7) { break; }

			if (!jActuel.getOrdre(positionOrdre).getType().equals(enumOrdre.AUCUN.getType()))
			{ modifStockJoueur(jActuel, robot, 1, positionOrdre); }

			robot.setOrdre( new Ordre (enumOrdre.values()[Ihm.numOrdre]), positionOrdre);
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

			case 'P' : this.afficherChoix(Ihm.premierTour);break;
		}
		//this.ctrl.afficherPlateau(plateau.tabTuile);
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
		String gemmePossedee  = "";
		String gemmePossedee2 = "";


		if( jActuel.getRobot(1).getGemme() != null)
		{
			gemmePossedee = " possède une gemme " + Utils.couleur(jActuel.getRobot(1).getGemme().getCouleur(),"normal",jActuel.getRobot(1).getGemme().getCouleur());
			gemmePossedee += "(" + jActuel.getRobot(1).getGemme().getGain() + ")                 ";
		}

		if(jActuel.getRobot(2).getGemme() != null)
		{
			gemmePossedee2 = " possède une gemme " + Utils.couleur(jActuel.getRobot(2).getGemme().getCouleur(),"normal",jActuel.getRobot(2).getGemme().getCouleur());
			gemmePossedee2 += "(" + jActuel.getRobot(2).getGemme().getGain() + ")                 ";
		}

		System.out.print("+---------------------------------------------------------+");
		System.out.print(String.format("%-3s", " ")+"+---------------------------------------------------------+\n");
		System.out.print(String.format("%-69s",("| " + Utils.couleur(jActuel.getCouleur(),"normal","Robot "+jActuel.getCouleur() + " 1") + gemmePossedee)) + "|   ");
		System.out.print(String.format("%-69s",("| " + Utils.couleur(jActuel.getCouleur(),"souligne","Robot "+jActuel.getCouleur() + " 2") + gemmePossedee2)) +"|\n");
		System.out.print("+------------------+------------------+-------------------+");
		System.out.print(String.format("%-3s", " ")+"+------------------+------------------+-------------------+\n");

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
		System.out.print("\n+------------------+------------------+-------------------+");
		System.out.print(String.format("%-3s", " ")+"+------------------+------------------+-------------------+\n\n");

		for (int i = 0; i< Plateau.getNbJoueur(); i++)
		{
			Joueur joueur = Plateau.getJoueur(i);

			if(joueur != jActuel)
			{
				System.out.println(Utils.couleur(joueur.getCouleur(), "normal",String.format("%-11s","Robot "+ joueur.getCouleur())) + " " + joueur.getNbPoints() + " points"); this.afficherInfoJoueurs(joueur);
			}

		}

	}

	public void afficherInfoJoueurs(Joueur joueur)
	{

		String ordre = "";

		for (int i = 1; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				System.out.print("| ");
				if(joueur.getRobot(i).getOrdre(j) != null ) { ordre = joueur.getRobot(i).getOrdre(j).toString(); }
				System.out.print(String.format("%-16s",ordre) + " ");
				ordre = "";
			}


			System.out.print(String.format("%-5s"," |  "));
		}

		System.out.print("\n");
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

	public void avancerScenario(String ordre)
	{
		System.out.println("Appuyer sur une touche pour continuer ");
		Clavier.lireString();
		System.out.println("Action : " + ordre + "\n");
		this.afficherPlateau(this.ctrl.getTabTuiles());
	}

	public boolean verifStock(Joueur jActuel, int numOrdre)
	{
		while ( (Ihm.numOrdre < 0 || Ihm.numOrdre > 5) )
		{
			System.out.println("Numéro incorrect");
			Ihm.numOrdre = Clavier.lire_int()-1;
			if(Ihm.numOrdre == 9 && !Ihm.premierTour) { this.afficherChoix(Ihm.premierTour); break;}
		}
		return(jActuel.getOrdre(Ihm.numOrdre).getNbExemplaires() > 0);
	}

	public static String chargerScenario()
	{
		System.out.println("Quel scenario voulez vous charger ?");
		return Clavier.lireString();
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

		chaine += "\n ";

		for (int nbC = 0; nbC<Plateau.getTabCristal().length; nbC++)
		chaine += "+---";

		chaine += "+\n";

		for(int l=0; l<Plateau.getTabCristal().length; l++)
		{
			chaine += " | ";
			if (Plateau.getCristal(l) == null) { chaine += " ";                   }
			else                               { chaine += Plateau.getCristal(l); }
		}

		chaine += " |\n ";

		for (int nbC = 0; nbC<Plateau.getTabCristal().length; nbC++)
		chaine += "+---";

		chaine += "+\n";

		System.out.println(chaine);
	}
}
