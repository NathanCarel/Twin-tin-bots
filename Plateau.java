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
		this.initJoueur(this.ctrl.initNbJoueur());
		this.joueurActif = this.ensJoueur[this.ctrl.premierJoueur(this.ensJoueur)];
		this.selectionPlateau(this.nbJoueur);

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

		try
		{
			fichier = new FileReader( "Plateau/Plateau"+nbJoueur+".data");
			Scanner sc = new Scanner ( fichier );
			while ( sc.hasNext() )
			{
				while (sc.hasNext())
				{
					sc.next();
					largeur++;
					if(this.largeurMax < largeur ) this.largeurMax = largeur;
				}
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
			String ligne = "";
			fichier = new FileReader( "Plateau/Plateau"+nbJoueur+".data");
			Scanner sc = new Scanner ( fichier );

			while(sc.hasNext())
			{
				ligne = sc.nextLine();
				for(int i = 0; i < ligne.length(); i++)
				{
					//Tuile vide
					if (sc.next().charAt(i) == 'T') this.tabTuile[hauteur][largeur] = new Tuile("Vide", hauteur, largeur);
					//Robot
					if (sc.nextInt() == '1') this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Rouge")));
					if (sc.nextInt() == '2') this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Jaune")));
					if (sc.nextInt() == '3') this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Vert")));
					if (sc.nextInt() == '4') this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Bleu")));
					if (sc.nextInt() == '5') this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Violet")));
					if (sc.nextInt() == '6') this.ensRobot.add((Robot)(this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Rose")));
					//Base
					if (sc.next().charAt(i) == 'A') this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Rouge")));
					if (sc.next().charAt(i) == 'B') this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Jaune")));
					if (sc.next().charAt(i) == 'C') this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Vert")));
					if (sc.next().charAt(i) == 'D') this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Bleu")));
					if (sc.next().charAt(i) == 'E') this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Violet")));
					if (sc.next().charAt(i) == 'F') this.ensBase.add((Base)(this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Rose")));
					//Cristaux
					if (sc.next().charAt(i) == '*') this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Vert");
					if (sc.next().charAt(i) == '-') this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Bleu");
					if (sc.next().charAt(i) == '+') this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Rose");
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

	public String toString()
	{
		String chaine = "";

		for(int i = 0; i < this.hauteurMax; i++)
		{
			for(int j = 0; j < this.largeurMax; j++)
			{
				chaine += this.tabTuile[i][j];
			}
			chaine += "\n";
		}
		return chaine;
	}
}
