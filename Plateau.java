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
	private  int              largeurMax;
	private  int              hauteurMax;
	private  Controleur       ctrl;
	private  int              nbJoueur;
	private  int              nbCristaux;
	private  Joueur[]         ensJoueur;
	private  Joueur           joueurActif;
	private  Tuile[][]        tabTuile;
	private  ArrayList<Robot> ensRobot;
	private  ArrayList<Base>  ensBase;


	public Plateau(Controleur ctrl, int nbJoueur)
	{
		this.ctrl      = ctrl;
		this.nbJoueur  = nbJoueur;
		this.ensJoueur = new Joueur[nbJoueur];
		this.ensBase   = new ArrayList<Base>();
		this.ensRobot  = new ArrayList<Robot>();
		this.initJoueur(this.nbJoueur);
		this.joueurActif = this.ensJoueur[this.ctrl.premierJoueur(this.ensJoueur)];
		this.initPlateau(nbJoueur);
		this.jouer();
	}

	public void jouer()
	{
		this.ctrl.getDessin();
	}

	public int getNbJoueur()
	{
		return this.nbJoueur;
	}

	public Joueur getJoueurActif()
	{
		return this.joueurActif;
	}

	public void setJoueurActif(Joueur joueur)
	{
		this.joueurActif = joueur;
	}

	public int selectionPlateau(int nbJoueur)
	{
		return 0;
	}

	public void initJoueur(int nbJoueur)
	{
		this.ensJoueur = new Joueur[nbJoueur];
		for(int i = 0; i < nbJoueur; i++)
		{
			this.ensJoueur[i] = new Joueur();
		}
	}

	public void initPlateau(int nbJoueur)
	{
		int largeur = 0;
		FileReader fichier;
		String ligne = "";

		try
		{
			fichier = new FileReader( "./Plateau/Plateau" + nbJoueur + ".data");
			Scanner sc = new Scanner ( fichier );
			while ( sc.hasNext() )
			{
				ligne = sc.nextLine();
				for(int i = 0; i < ligne.length(); i++)
				{
					largeur++;
				}
				if(this.largeurMax < largeur ) this.largeurMax = largeur;
				largeur = 0;
				this.hauteurMax++;
			}
			sc.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		this.tabTuile = new Tuile[this.hauteurMax][this.largeurMax];
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
				for(int i = 0; i < ligne.length(); i++)
				{
					//Tuile vide
					if (composant[i] == " T ") this.tabTuile[hauteur][largeur] = new Tuile("Vide", hauteur, largeur);
					//Robot
					if (composant[i] == " R") this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Rouge")));
					if (composant[i] == " J") this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Jaune")));
					if (composant[i] == " V") this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Vert")));
					if (composant[i] == " B") this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Bleu")));
					if (composant[i] == " M") this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Mauve")));
					if (composant[i] == " S") this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Saumon")));
					//Base
					if (composant[i] == " 1") this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Rouge")));
					if (composant[i] == " 2") this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Jaune")));
					if (composant[i] == " 3") this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Vert")));
					if (composant[i] == " 4") this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Bleu")));
					if (composant[i] == " 5") this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Mauve")));
					if (composant[i] == " 6") this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Saumon")));
					//Cristaux
					if (composant[i] == " *") this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Vert");
					if (composant[i] == " -") this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Bleu");
					if (composant[i] == " +") this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Saumon");
					largeur++;
				}
				largeur = 0;
				hauteur++;
			}
			sc.close();
		}
		catch(Exception e) { e.printStackTrace(); }

		for(int i = 0; i < this.ensJoueur.length; i++)
		{
			this.ensJoueur[i].attributionRobot(this.ensRobot.get(i).getCouleur(), this.ensRobot.get(i));
		}
	}

	public String getElement()
	{
		String chaine = "";

		for(int i = 0; i < this.hauteurMax; i++)
		{
			for(int j = 0; j < this.largeurMax; j++)
			{
				if(this.tabTuile[i][j] == null) { return " "; }
				else
				{
					chaine += this.tabTuile[i][j]; 
				}
			}
		}
		return chaine;				
	}

	public String toString()
	{
		String chaine = "";

		for(int i = 0; i < this.hauteurMax; i++)
		{
			for(int j = 0; j < this.largeurMax; j++)
			{
				if(this.tabTuile[i][j] == null) { chaine+= " ";                  }
				else                            { chaine += this.tabTuile[i][j]; }

			}
			chaine += "\n";
		}
		return chaine;
	}
}
