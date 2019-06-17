/*
	Projet_tut
	@author Tristan Bassa
	17/06/2019
*/

import java.util.*;
import iut.algo.*;

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
		this.selectionPlateau(this.nbJoueur));
		if(nbJoueur == 2 || nbJoueur == 3 || nbJoueur == 4)
		{	this.hauteurMax = this.largeurMax = 9; }
		else
		{ this.hauteurMax = this.largeurMax = 15; }
		this.tabTuile = new Tuile[this.hauteurMax][this.largeurMax]
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
	}

	public void initPlateau()
	{
		int nbCaseGauche = 3;
		int nbCaseDroite = this.largeurMax-3;
		//Initialisation du plateau à vide
		for(int i = 0; i < this.hauteurMax; i++)
		{
			for(int j = 0; j < this.largeurMax; j++)
			{
				this.tabTuile[i][j] = null;
			}
		}

		for(int i = 0; i < this.largeurMax; i++)
		{
			for(j = nbCaseGauche; j < nbCaseDroite; j++)
			{
				this.tabTuile[i][j] = new Tuile();
			}
			nbCaseGauche = nbCaseGauche -1;
			nbCaseDroite = nbCaseDroite +1;
		}
		for(int i = 0; i < this.largeurMax; i++)
		{
			this.tabTuile[this.hauteurMax/2][i] = new Tuile();
		}
		nbCaseGauche = 0;
		nbCaseDroite = this.largeurMax;
		for(int i = this.hauteurMax; i < this.hauteurMax; i++)
		{
			for(j = nbCaseGauche; j < nbCaseDroite ; j++)
			{
				this.tabTuile[i][j] = new Tuile();
			}
			nbCaseGauche = nbCaseGauche+1;
			nbCaseDroite = nbCaseDroite-1;
		}
	}
}
