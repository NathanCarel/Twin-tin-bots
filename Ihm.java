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
		//System.out.println(this.ctrl.getPlateau());
		return plateau;
	}

	public void afficherChoix() //Modifié
	{
		Joueur jActuel = this.ctrl.getPlateau().getJoueurActif();

        System.out.println("Robots " + jActuel.getCouleur());
        
        for (int r=1; r<3; r++)
        {
            System.out.print("[ ");
            for (int o=0; o<3; o++)
            {
                System.out.print("(");

                System.out.print(jActuel.getRobot(r).getOrdre(o));

                System.out.print(")");
            }

            System.out.print(" ] ");
        }

		System.out.println( "\nJoueur " + this.ctrl.getPlateau().getJoueurActif().getCouleur() + " que voulez-vous faire :");
		System.out.println();
		System.out.println("1 : Changer un programme du robot 1");
		System.out.println("2 : Changer un programme du robot 2");
		System.out.println("P : Passer");

		char choix = Clavier.lire_char();
		//choix.toUpperCase();

		switch (choix)
		{
			case '1' : this.afficherAction(1); break;
			case '2' : this.afficherAction(2); break;
			case 'P' : break;
		}
	}

	public void afficherAction(int numRobot) //Modifié
	{
		Robot robot = this.ctrl.getPlateau().getJoueurActif().getRobot(numRobot);

		System.out.println("\nQuel ordre voulez-vous donner ?");

		System.out.println("A - Ajouter une action"           );
		System.out.println("E - Echanger deux actions"        );
		System.out.println("R - Retirer une action"           );
		System.out.println("V - Vider les 3 actions"          );
		System.out.println("P - Ne rien faire"                );

		char action = Clavier.lire_char();
		//action.toUpperCase();

		int numOrdre      = 0;
		int positionOrdre = 0;

		if (action != 'P')
		{
			System.out.println("\nQuelle action voulez-vous modifier ?"); 
			System.out.println("[1]  : La premiere  : " + robot.getOrdre(0)/*.getType()*/);
			System.out.println("[2]  : La seconde   : " + robot.getOrdre(1)/*.getType()*/);
			System.out.println("[3]  : La troisieme : " + robot.getOrdre(2)/*.getType()*/);
			System.out.println("[4]  : Annuler"     );	

			do
			{
				positionOrdre = Clavier.lire_int()-1;
			}
			while (positionOrdre < 0 || positionOrdre > 3);


			switch(action)
			{
				case 'A' :  System.out.println("\nChoisissez un ordre:");

							for (int i=0; i<8; i++)
							System.out.println(String.format("%-23s",i+1 + " - " + this.ctrl.getPlateau().getJoueurActif().getOrdre(i).getType()) +  " (" + this.ctrl.getPlateau().getJoueurActif().getOrdre(i).getNbExemplaires() + ") "); //Affiche tous les ordres

							do
							{
								numOrdre = Clavier.lire_int()-1;
							}
							while (numOrdre < 0 || numOrdre > 7);

							Ordre ordre = Ordre.values()[numOrdre];

						    if(this.ctrl.getPlateau().getJoueurActif().getOrdre(positionOrdre) == null)
						    {
						    	robot.setOrdre(ordre, positionOrdre);
						     	this.ctrl.getPlateau().getJoueurActif().getOrdre(positionOrdre).getNbExemplairesMoinsUn();
						    }
						    else
						    {
						    	this.ctrl.getPlateau().getJoueurActif().getOrdre(positionOrdre).getNbExemplairesPlusUn();
						    	robot.setOrdre(ordre, positionOrdre);
						    	this.ctrl.getPlateau().getJoueurActif().getOrdre(positionOrdre).getNbExemplairesMoinsUn();
						    }

						    break;
				case 'E' :  int ordre1 = 0; int ordre2 = 0;
						    System.out.println("Quels sont les ordres que vous voulez échanger ?");
						    ordre1 = Clavier.lire_int();
						    ordre2 = Clavier.lire_int();
						    robot.echangerOrdre(ordre1, ordre2);break;
	 
				case 'R' :  robot.setOrdre(null, numOrdre);
						    this.ctrl.getPlateau().getJoueurActif().getOrdre(positionOrdre).getNbExemplairesMoinsUn();
						    break;

				case 'V' : for (int i=0; i<3; i++)
				              robot.setOrdre(null, i);break;

				case 'P' : break;
			}
		}
	}

	public void afficherNbAction() //Modifié
	{
		System.out.println("\nQuelle action voulez-vous modifier ?"); 
		System.out.println("[1]  : La premiere" );
		System.out.println("[2]  : La seconde"  );
		System.out.println("[3]  : La troisieme");
		System.out.println("[10] : Annuler"     );

		int num = Clavier.lire_int();

		/*switch(nbAction)
		{
			case 1  : this.ctrl.getPlateau().getJoueurActif().getRobot1().modifierAction(1); break;
			case 2  : this.ctrl.getPlateau().getJoueurActif().getRobot1().modifierAction(2); break;
			case 3  : this.ctrl.getPlateau().getJoueurActif().getRobot1().modifierAction(3); break;
			case 10 : break;
		}*/
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
