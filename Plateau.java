/*
Projet_tut
@author Tristan Bassa
17/06/2019
*/

import java.util.*;
import iut.algo.*;
import java.io.*;

public class Plateau
{
	private static Tuile[][] tabTuile;  //3 variables static pour la classe Robot
	private static Joueur[]  ensJoueur;
	private static int       nbJoueur;
	private static int       largeurMax;
	private static int       hauteurMax;
	private static int       nbCristaux = 8;
	private static Gemme[]   tabCristal = new Gemme[8];
	private static boolean   premierTour = true;

	private final  int       NB_POINTS_REQUIS = 11; //point requis pour 2 joueurs qui est la base de calcul de victoire


	private  Controleur       ctrl;
	private  Joueur           joueurActif;
	private  ArrayList<Robot> ensRobot;
	private  ArrayList<Base>  ensBase;

	private Gemme critalVert   = new Gemme("Gemme", 0, 0, "vert"  );
	private Gemme critalViolet = new Gemme("Gemme", 0, 0, "mauve");


	public Plateau(Controleur ctrl, int nbJoueur)
	{
		this.ctrl      = ctrl;
		Plateau.nbJoueur  = nbJoueur;
		Plateau.ensJoueur = new Joueur[nbJoueur];
		this.ensBase   = new ArrayList<Base>();
		this.ensRobot  = new ArrayList<Robot>();
		this.initJoueur(Plateau.nbJoueur);
		this.joueurActif = Plateau.ensJoueur[this.ctrl.premierJoueur(Plateau.ensJoueur)];
		this.initPlateau(nbJoueur);
		this.initPisteCristaux();
	}

	public void initPisteCristaux()
	{
		for(int i=0; i<tabCristal.length; i++)
			if(i<4)
				tabCristal[i] = this.critalVert;
			else
				tabCristal[i] = this.critalViolet;
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
	public static int    getNbJoueur ()                       { return Plateau.nbJoueur;        }
	public static void   setTuile (Tuile tuile, int x, int y) { Plateau.tabTuile[x][y] = tuile; }


	public void jouer()
	{
		while(!this.gagne())
		{
			this.ctrl.afficherPlateau(Plateau.tabTuile);
			this.ctrl.afficherChoix(Plateau.premierTour);
			this.joueurActif.actionsRobots(); //Lance les 3 actions des 2 robots
			this.JoueurSuivant();
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
	}

	public boolean gagne()
	{
		int nbPointRequis = NB_POINTS_REQUIS;
		for(Joueur joueur : Plateau.ensJoueur)
		{
			for(int i = 2; i <= Plateau.nbJoueur; i++)
			{
				if(Plateau.nbJoueur == i && joueur.getNbPoints() == nbPointRequis-(i-2)) return true;
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
		FileReader fichier;
		String ligne = "";

		try
		{
			fichier = new FileReader( "./Plateau/Plateau"+nbJoueur+".data");
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
		try
		{
			int hauteur = 0;
			ligne = "";
			fichier = new FileReader( "./Plateau/Plateau"+nbJoueur+".data");
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
					if (composant[i].charAt(1) == '-') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "bleu");
					if (composant[i].charAt(1) == '*') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "vert");
					if (composant[i].charAt(1) == '+') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "rose");
					largeur++;
				}
				largeur = 0;
				hauteur++;
			}
			sc.close();
		}
		catch(Exception e) { e.printStackTrace(); }

		for(int i = 0; i < Plateau.ensJoueur.length; i++)
		{
			for (int j = 0; j < this.ensRobot.size(); j++)
				Plateau.ensJoueur[i].attributionRobot(this.ensRobot.get(j).getCouleur(), this.ensRobot.get(j));
		}
	}
}
