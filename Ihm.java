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
			System.out.println("Quel est le joueur qui commence : \n");
			for(int i = 0; i < ensJoueur.length; i++)
			{
				System.out.println("[" + i + "] " + ensJoueur[i].getCouleur());
			}
			joueur = Clavier.lire_int();
			while (joueur < 0 || joueur > ensJoueur.length-1)
			{
				System.out.println("\nCe numéro n'est pas correct, veuillez recommencer\n");
				joueur = Clavier.lire_int();
			}
		}while (joueur < 0 || joueur > ensJoueur.length);

		return joueur;
	}

	public String afficherPlateau(int nbJoueur)
	{
		String plateau = "";
		//System.out.println(this.ctrl.getPlateau());
		return plateau;
	}


	public void afficherChoix() //Modifié
	{
        this.affichertabOrdre();

		System.out.println( "\nJoueur " + this.ctrl.getPlateau().getJoueurActif().getCouleur() + " que voulez-vous faire :\n");
		System.out.println("1 : Changer le programme du robot 1");
		System.out.println("2 : Changer le programme du robot 2");
		System.out.println("P : Passer");

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

	public void afficherAction(int numRobot) //Modifié
	{
		Joueur jActuel = this.ctrl.getPlateau().getJoueurActif();

		Robot robot = this.ctrl.getPlateau().getJoueurActif().getRobot(numRobot);

		System.out.println("\nQuel ordre voulez-vous donner ?\n");

		System.out.println("A - Ajouter une action"           );
		System.out.println("E - Echanger deux actions"        );
		System.out.println("R - Retirer une action"           );
		System.out.println("V - Vider les 3 actions"          );
		System.out.println("P - Ne rien faire"                );

		char action = Clavier.lireString().toUpperCase().charAt(0);

        while (action != 'A' && action != 'E' && action != 'R' && action != 'V' && action != 'N' && action != 'P')
        {
            System.out.println("Lettre incorrecte !\n");
            action = Clavier.lireString().toUpperCase().charAt(0);
        }

		int numOrdre      = 0;
		int positionOrdre = 0;

		if (action != 'P')
		{
			
			switch(action)
			{
				case 'A' :  this.afficherNbAction('A');

							do
							{
								positionOrdre = Clavier.lire_int()-1;
							}
							while (positionOrdre < 0 || positionOrdre > 3);

							System.out.println("\nChoisissez un ordre:\n");

							for (int i=0; i<8; i++)
								System.out.println(String.format("%-23s",i+1 + " - " + jActuel.getOrdre(i).getType()) +  " (" + jActuel.getOrdre(i).getNbExemplaires() + ") "); //Affiche tous les ordres

							do
							{
								numOrdre = Clavier.lire_int()-1;
								System.out.println("lol" + jActuel.getOrdre(numOrdre).getNbExemplaires());

							}
							while ( numOrdre < 0 || numOrdre > 7 && jActuel.getOrdre(numOrdre).getNbExemplaires() > 0);

							Ordre ordre = new Ordre (enumOrdre.values()[numOrdre]);
							Ordre aucun = new Ordre (enumOrdre.AUCUN);

						    if(jActuel.getOrdre(positionOrdre).getType().equals(aucun.getType()))
						    {
						    	robot.setOrdre(ordre, positionOrdre);

						     	for (int i=0; i<jActuel.getStockOrdre().length; i++)
									if (robot.getOrdre(positionOrdre).getType().equals(jActuel.getOrdre(i).getType()))
										jActuel.getOrdre(i).setNbExemplairesMoinsUn();
						    }
						    else
						    {

						    	for (int i=0; i<jActuel.getStockOrdre().length; i++)
									if (robot.getOrdre(positionOrdre).getType().equals(jActuel.getOrdre(i).getType()))
										jActuel.getOrdre(i).setNbExemplairesPlusUn();

						    	robot.setOrdre(ordre, positionOrdre);
						    	
						    	for (int i=0; i<jActuel.getStockOrdre().length; i++)
									if (robot.getOrdre(positionOrdre).getType().equals(jActuel.getOrdre(i).getType()))
										jActuel.getOrdre(i).setNbExemplairesMoinsUn();
						    }

						    this.affichertabOrdre();

						    break;

				case 'E' :  this.afficherNbAction('E');
							int ordre1 = 0; int ordre2 = 0;
						    System.out.println("Entrez la pemière puis entrez la seconde ?\n");
						    do
                            {
                                ordre1 = Clavier.lire_int() - 1;
                                ordre2 = Clavier.lire_int() - 1;
                            } 
                            while((ordre1 < 1 || ordre1 > 3) && (ordre2 < 1 || ordre2 > 3));
						    robot.echangerOrdre(ordre1, ordre2);
						    this.affichertabOrdre();
						    break;
	 
				case 'R' :  this.affichertabOrdre();
							this.afficherNbAction('R');
							do
							{
								positionOrdre = Clavier.lire_int()-1;
							}
							while (positionOrdre < 0 || positionOrdre > 2);

							ordre = new Ordre (enumOrdre.values()[positionOrdre]);

							for (int i=0; i<jActuel.getStockOrdre().length; i++)
								if (robot.getOrdre(positionOrdre).getType().equals(jActuel.getOrdre(i).getType()))
									jActuel.getOrdre(i).setNbExemplairesPlusUn();

							robot.setOrdre(new Ordre (enumOrdre.AUCUN), positionOrdre);
						    this.affichertabOrdre();
						    break;

				case 'V' : this.affichertabOrdre();

						   for (int i=0; i<3; i++)
				              robot.setOrdre(new Ordre (enumOrdre.AUCUN), i);

				          	this.affichertabOrdre();
				        	break;

				case 'P' : break;
			}
		}
	}

	public void affichertabOrdre()
	{
		
		Joueur jActuel = this.ctrl.getPlateau().getJoueurActif();
		String ordre = "";

		System.out.print("+---------------------------------------------------------+");
		System.out.print(String.format("%-3s", " ")+"+---------------------------------------------------------+\n");
		System.out.print(String.format("%-58s",("| Robots " + jActuel.getCouleur()))+ "|   ");
		System.out.print(String.format("%-58s",("| Robots " + jActuel.getCouleur()))+ "|\n");
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

	public void afficherNbAction(char nomAction) //Modifié
	{
		System.out.println("\nQuelle action voulez-vous " + this.getNomAction(nomAction) + " ?\n"); 
		System.out.println("[1]  : La premiere" );
		System.out.println("[2]  : La seconde"  );
		System.out.println("[3]  : La troisieme");
		System.out.println("[4]  : Annuler"     );

	}

	public String getNomAction(char nomAction)
	{
		switch(nomAction)
		{
			case 'A' : return "ajouter";
			case 'E' : return "échanger";
			case 'R' : return "retirer";
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
