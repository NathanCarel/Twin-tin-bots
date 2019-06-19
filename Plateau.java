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
	private static Tuile[][] tabTuile;  //3 statics à mettre pour la classe Robot (+ changer les this pour des Plateau)
	private static Joueur[]  ensJoueur;
	private static int       nbJoueur;

	private final  int       NB_POINTS_REQUIS = 11; //point requis pour 2 joueurs qui est la base de calcul de victoire

	private  int              largeurMax;
	private  int              hauteurMax;
	private  Controleur       ctrl;
	private  int              nbCristaux;
	private  Joueur           joueurActif;
	private  ArrayList<Robot> ensRobot;
	private  ArrayList<Base>  ensBase;


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
	}

	public static Tuile  getTuile  (int x, int y) { return Plateau.tabTuile[x][y]; } //Méthodes static à ajouter
	public static Joueur getJoueur(int i)         { return Plateau.ensJoueur[i];   }
	public static int    getNbJoueur ()           { return Plateau.nbJoueur;       }


	public void jouer()
	{
		while(!this.gagne())
		{
			this.ctrl.afficherPlateau(Plateau.tabTuile);
			this.ctrl.afficherChoix();
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
				if(this.largeurMax < largeur ) this.largeurMax = largeur;
				largeur = 0;
				this.hauteurMax++;
			}
			sc.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		Plateau.tabTuile = new Tuile[this.hauteurMax][this.largeurMax];
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
					if (composant[i].charAt(1) == 'T') Plateau.tabTuile[hauteur][largeur] = new Tuile("Vide", hauteur, largeur);
					//Robot
					if (composant[i].charAt(1) == 'R') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Rouge",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'J') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Jaune",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'V') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Vert",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'B') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Bleu",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'M') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Mauve",composant[i].charAt(2))));
					if (composant[i].charAt(1) == 'S') this.ensRobot.add((Robot)(Plateau.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Cyan",composant[i].charAt(2))));
					//Base
					if (composant[i].charAt(1) == '1') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Rouge")));
					if (composant[i].charAt(1) == '2') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Jaune")));
					if (composant[i].charAt(1) == '3') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Vert")));
					if (composant[i].charAt(1) == '4') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Bleu")));
					if (composant[i].charAt(1) == '5') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Mauve")));
					if (composant[i].charAt(1) == '6') this.ensBase.add((Base)(Plateau.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Cyan")));
					//Cristaux
					if (composant[i].charAt(1) == '*') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Vert");
					if (composant[i].charAt(1) == '-') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Bleu");
					if (composant[i].charAt(1) == '+') Plateau.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Cyan");
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
			Plateau.ensJoueur[i].attributionRobot(this.ensRobot.get(i).getCouleur(), this.ensRobot.get(i));
		}
	}
}
