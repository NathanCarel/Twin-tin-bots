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
	private  int        largeurMax;
	private  int        hauteurMax;
	private  Controleur ctrl;
	private  int        nbJoueur;
	private  int        nbCristaux;
	private  Joueur[]   ensJoueur;
	private  Joueur     joueurActif;
	private  Tuile[][]  tabTuile;


	public Plateau(Controleur ctrl, int nbJoueur)
	{
		this.ctrl      = ctrl;
		this.nbJoueur  = nbJoueur;
		this.ensJoueur = new Joueur[nbJoueur];
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

	public int selectionPlateau(int nbJoueur)
	{
		return 0;
	}

	public void initPlateau(int nbJoueur)
	{
		int largeur;
		try
		{
			FileReader fichier;
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
  		fr.close();
			sc.close();
		}
		catch (Exception e) { e.printStackTrace(); }
		this.tabTuile = new Tuile[this.hauteurMax][this.largeurMax];
		try
		{
			int hauteur;
			int largeur;
			Scanner sc = new Scanner ( fichier );
			while(sc.hasNext())
			{
				while(sc.hasNext())
				{
					//Tuile vide
					if (sc.next() == 'T') this.tabTuile[hauteur][largeur] = new Tuile("Vide", hauteur, largeur);
					//Robot
					if (sc.next() == '1') this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Rouge");
					if (sc.next() == '2') this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Jaune");
					if (sc.next() == '3') this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Vert");
					if (sc.next() == '4') this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Bleu");
					if (sc.next() == '5') this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Violet");
					if (sc.next() == '6') this.tabTuile[hauteur][largeur] = new Robot("Robot", hauteur, largeur, "Rose");
					//Base
					if (sc.next() == 'A') this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Rouge");
					if (sc.next() == 'B') this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Jaune");
					if (sc.next() == 'C') this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Vert");
					if (sc.next() == 'D') this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Bleu");
					if (sc.next() == 'E') this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Violet");
					if (sc.next() == 'F') this.tabTuile[hauteur][largeur] = new Base("Base", hauteur, largeur, "Rose");
					//Cristaux
					if (sc.next() == '*') this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Vert");
					if (sc.next() == '-') this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Bleu");
					if (sc.next() == '+') this.tabTuile[hauteur][largeur] = new Gemme("Gemme", hauteur, largeur, "Rose");
					largeur++;
				}
				largeur = 0;
				hauteur++;
			}
		}
		catch(Exception e) { e.printStackTrace(); }
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
