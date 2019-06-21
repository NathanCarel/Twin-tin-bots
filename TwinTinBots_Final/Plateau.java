/*
Projet_tut
@author Tristan Bassa
17/06/2019
*/

import java.util.*;
import iut.algo.*;
import java.io.*;
import java.io.File;

public class Plateau
{
	private final  int       NB_POINTS_REQUIS = 11; //point requis pour 2 joueurs qui est la base de calcul de victoire

	private static Tuile[][]  tabTuile;  //3 variables static pour la classe Robot
	private static Joueur[]   ensJoueur;
	private static int        nbJoueur;
	private static int        largeurMax;
	private static int        hauteurMax;
	private static int        nbCristaux = 8;
	private static Gemme[]    tabCristal = new Gemme[8];
	private static boolean    premierTour = true;
	private static boolean    chargement = false;
	private static File       scenario;




	private  Controleur       ctrl;
	private  Joueur           joueurActif;
	private  ArrayList<Robot> ensRobot;
	private  ArrayList<Base>  ensBase;
	private  int              decompte;

	private Gemme cristalVert   = new Gemme("Gemme", 0, 0, "vert"  );
	private Gemme cristalMauve  = new Gemme("Gemme", 0, 0, "mauve");


	public Plateau(Controleur ctrl, int nbJoueur)
	{
		this.ctrl      = ctrl;
		Plateau.nbJoueur  = nbJoueur;

		this.ensBase   = new ArrayList<Base>();
		this.ensRobot  = new ArrayList<Robot>();
		if(Plateau.nbJoueur != 0)
		{
			Plateau.ensJoueur = new Joueur[nbJoueur];
			this.initJoueur(Plateau.nbJoueur);
			this.joueurActif = Plateau.ensJoueur[this.ctrl.premierJoueur(Plateau.ensJoueur)];
		}
		this.initPlateau(nbJoueur);
		this.initPisteCristaux();
	}

	public void initPisteCristaux()
	{
		Plateau.nbCristaux = 11 - Plateau.nbJoueur;

		if (Plateau.nbJoueur == 2) { Plateau.nbCristaux = 7; }

		Plateau.tabCristal = new Gemme[Plateau.nbCristaux];

		for(int i=tabCristal.length-1; i>=0; i--)
		{
			if(i<tabCristal.length-4)
				tabCristal[i] = this.cristalVert;
			else
				tabCristal[i] = this.cristalMauve;
		}
	}

	public static Gemme enleverCristal()
	{
		Gemme gemme = tabCristal[tabCristal.length - nbCristaux];
		tabCristal[tabCristal.length - nbCristaux] = null;
		nbCristaux--;
		return gemme;
	}

	public static Gemme getCristal(int num)
	{
		return Plateau.tabCristal[num];
	}

	public static Tuile  getTuile (int x, int y)
	{
		if (x>Plateau.tabTuile.length || y>Plateau.tabTuile[0].length || x<0 || y<0)
		return null;

		return Plateau.tabTuile[x][y];
	}
	//M�thodes static utilis�es pour modifier le tableau
	public static Joueur getJoueur(int i)                     { return Plateau.ensJoueur[i];    } //et pour avoir des informations sur les joueurs
	public static int    getLargeurMax()		                  { return Plateau.largeurMax;      }
	public static int    getHauteurMax()		                  { return Plateau.hauteurMax;      }
	public static int    getNbJoueur ()                       { return Plateau.ensJoueur.length;}
	public static void   setTuile (Tuile tuile, int x, int y) { Plateau.tabTuile[x][y] = tuile; }
	public static Gemme[] getTabCristal()                     { return Plateau.tabCristal;      }
	public static void    setPremierTour(boolean premier)     { Plateau.premierTour = premier;  }

	public Tuile[][] getTabTuiles()
	{
		return Plateau.tabTuile;
	}


	public void jouer()
	{
		if(Plateau.chargement)
		{
			this.ctrl.afficherPlateau(Plateau.tabTuile);
			this.scanScenario();
		}
		else
		{
			while(!this.gagne())
			{
				this.ctrl.afficherPlateau(Plateau.tabTuile);
				this.ctrl.afficherChoix(Plateau.premierTour);
				this.joueurActif.actionsRobots(); //Lance les 3 actions des 2 robots
				this.JoueurSuivant();
			}

			for (int i=0; i<ensJoueur.length; i++)
			{
				for (int j=0; j<2; j++)
					if (ensJoueur[i].getRobot(j+1).getGemme() != null)
						ensJoueur[i].setNbPoints( ensJoueur[i].getNbPoints() + (ensJoueur[i].getRobot(j+1).getGemme().getGain()-1) );
			}

			Joueur gagnant = getJoueurActif();

			for (int i=0; i<ensJoueur.length; i++)
			{
				if (ensJoueur[i].getNbPoints() > gagnant.getNbPoints())
					gagnant = ensJoueur[i];
			}

			System.out.println( Utils.couleur(gagnant.getCouleur(), "normal" , "Le joueur " + gagnant.getCouleur() + " a gagne avec " + gagnant.getNbPoints() + " points !") );
		}
	}

	public Joueur getJoueurActif()
	{
		return this.joueurActif;
	}

	public void setJoueurActif(Joueur joueur)
	{
		this.joueurActif = joueur;
	}

	public void JoueurSuivant()
	{
		for(int i = 0; i < Plateau.ensJoueur.length; i++)
		{
			if(Plateau.ensJoueur[i] == this.joueurActif )
			{
				if(Plateau.nbJoueur == 2)           { this.joueurActif = Plateau.ensJoueur[1-i]; break;}
				if(i < Plateau.ensJoueur.length-1)  { this.joueurActif = Plateau.ensJoueur[1+i]; break;}
				else                                { this.joueurActif = Plateau.ensJoueur[0];   break;}
			}
		}

		if (tabCristal[tabCristal.length-1] == null)
			decompte++;
	}

	public boolean gagne()
	{
		int nbPointRequis = NB_POINTS_REQUIS;
		for(Joueur joueur : Plateau.ensJoueur)
		{
			for(int i = 2; i <= Plateau.nbJoueur; i++)
			{
				if( (Plateau.nbJoueur == i && joueur.getNbPoints() == nbPointRequis-(i-2)) || this.decompte == (3 * ensJoueur.length + 1) ) return true;
			}
		}
		return false;
	}

	public void initJoueur(int nbJoueur)
	{
		Plateau.ensJoueur = new Joueur[nbJoueur];
		for(int i = 0; i < nbJoueur; i++)
		{
			Plateau.ensJoueur[i] = new Joueur();
		}
	}

	public void initPlateau(int nbJoueur)
	{
		int largeur = 0;
		File fichier;
		String ligne = "";
		int nbJoueurScenario = 0;

		try
		{
			if(nbJoueur == 0) { fichier = new File( "./Plateau/Test.data"); }
			else                fichier = new File( "./Plateau/Plateau"+nbJoueur+".data");
			Scanner sc = new Scanner ( fichier );
			while ( sc.hasNext() )
			{
				ligne = sc.nextLine();
				for(int i = 0; i < ligne.length(); i++)
				{
					if(ligne.charAt(i) == ':') { largeur++; }
				}
				if(Plateau.largeurMax < largeur ) Plateau.largeurMax = largeur;
				largeur = 0;
				Plateau.hauteurMax++;
			}
			sc.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		Plateau.tabTuile = new Tuile[Plateau.hauteurMax][Plateau.largeurMax];

		//CHARGEMENT NORMAL

		try
		{
			int hauteur = 0;
			ligne = "";
			if(nbJoueur == 0) { fichier = new File( "./Plateau/Test.data"); }
			else                fichier = new File( "./Plateau/Plateau"+nbJoueur+".data");
			Scanner sc = new Scanner ( fichier );
			String[] composant;
			while(sc.hasNext())
			{
				ligne = sc.nextLine();
				composant = ligne.split(":");
				for(int i = 0; i < composant.length; i++)
				{
					composant[i].substring(1,2);
					//Tuile vide
					if (composant[i].charAt(1) == 'T') Plateau.tabTuile[hauteur][largeur] = new Tuile("Tuile", hauteur, largeur);
					//Robot
					if (composant[i].charAt(1) == 'R') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "rouge",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'J') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "jaune",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'V') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "vert" ,composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'B') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "bleu" ,composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'M') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "mauve",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'C') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "cyan" ,composant[i].charAt(2))));
					//Base
					if (composant[i].charAt(1) == '1') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "rouge")));
					if (composant[i].charAt(1) == '2') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "jaune")));
					if (composant[i].charAt(1) == '3') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "vert" )));
					if (composant[i].charAt(1) == '4') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "bleu" )));
					if (composant[i].charAt(1) == '5') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "mauve")));
					if (composant[i].charAt(1) == '6') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "cyan" )));
					//Cristaux
					if (composant[i].charAt(1) == '-') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "cyan");
					if (composant[i].charAt(1) == '*') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "vert");
					if (composant[i].charAt(1) == '+') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "mauve");
					largeur++;
				}
				largeur = 0;
				hauteur++;
			}
			sc.close();
		}
		catch(Exception e) { e.printStackTrace(); }

		//CHARGEMENT DE SCENARIO

		if(nbJoueur == 0)
		{
			Plateau.chargement = true;
			try
			{
				do
				{
					Plateau.scenario = new File( "./Scenario/"+Ihm.chargerScenario()+".data");
				}while(!Plateau.scenario.exists());

				Scanner sc = new Scanner( Plateau.scenario );
				String[] composant = new String[5];
				int x;
				int y;
				char orientation;
				String couleur;

				while ( sc.hasNext() )
				{
					ligne = sc.nextLine();
					composant = ligne.split(":");
					if(!composant[0].equals("init")) break;

					if(composant[2].equals("1")) nbJoueurScenario++;
					x = Integer.valueOf(composant[3]);
					y = Integer.valueOf(composant[4]);
					orientation = composant[composant.length-1].charAt(0);
					couleur = composant[1];
					this.ensRobot.add((Robot)(Plateau.tabTuile[x][y] = new Robot("Robot",x,y,couleur,orientation)));
				}
				sc.close();
			}
			catch (Exception e) {e.printStackTrace(); }
			this.initJoueur(nbJoueurScenario);
			this.joueurActif = Plateau.ensJoueur[0];

		}
		Plateau.nbJoueur = nbJoueurScenario;

		for(int i = 0; i < Plateau.ensJoueur.length; i++)
		{
			for (int j = 0; j < this.ensRobot.size(); j++)
			Plateau.ensJoueur[i].attributionRobot(this.ensRobot.get(j).getCouleur(), this.ensRobot.get(j));
		}
	}

	public Joueur chercherJoueur(String couleur)
	{
		for(Joueur joueur : Plateau.ensJoueur)
		{
			if (couleur.equals(joueur.getCouleur())) return joueur;
		}
		return null;
	}

	public void scanScenario()
	{
		String   ligne = "";
		String[] composant;
		Ordre    ordre = null;

		try
		{
			Scanner sc = new Scanner(Plateau.scenario);
			while(sc.hasNext())
			{
				ligne = sc.nextLine();
				composant = ligne.split(":");
				if(composant[0].equals("action"))
				{
					for (int i = 0; i < this.ensRobot.size(); i++)
					{
						if(this.ensRobot.get(i).getCouleur().equals(composant[1]) && this.ensRobot.get(i).getNum() == Integer.valueOf(composant[2])-1)
						{
							for(int j = 3; j < composant.length; j++)
							{
								if (composant[j].equals("A1")) { ordre = new Ordre(enumOrdre.values()[0]); }
								if (composant[j].equals("A2")) { ordre = new Ordre(enumOrdre.values()[1]); }
								if (composant[j].equals("TG")) { ordre = new Ordre(enumOrdre.values()[2]); }
								if (composant[j].equals("TD")) { ordre = new Ordre(enumOrdre.values()[3]); }
								if (composant[j].equals("CC")) { ordre = new Ordre(enumOrdre.values()[4]); }
								if (composant[j].equals("DC")) { ordre = new Ordre(enumOrdre.values()[5]); }
								if (composant[j].equals("  ")) { ordre = new Ordre(enumOrdre.values()[6]); }

								this.ensRobot.get(i).setOrdre(ordre, j-3);
								this.chercherJoueur(composant[1]).actionRobot(this.ensRobot.get(i).getNum(), j-3);
								this.ctrl.avancerScenario(this.ensRobot.get(i).getOrdre(j-3).toString(), this.ensRobot.get(i), Plateau.ensJoueur);
							}
						}
					}
				}
			}
			sc.close();
		}catch (Exception e) {e.printStackTrace();}
	}
}