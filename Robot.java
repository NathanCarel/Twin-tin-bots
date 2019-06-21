import iut.algo.*;

/*----------------------------------------------------------*/
/*     CLASSE QUI HERITE DE TUILE ET QUI CREE UN ROBOT      */
/*----------------------------------------------------------*/

public class Robot extends Tuile
{
	//ATTRIBUTS

	private static int num;

	private int     numRobot            ;
	private int     orientation = 0     ;
	private char    orientationAffichage;
	private String  couleur             ;
	private Gemme   gemme               ;
	private Ordre[] tabOrdre            ;


	//CONSTRUCTEUR

	public Robot(String type, int x, int y, String couleur, char orientation)
	{
		super(type, x, y);
		this.orientationAffichage = orientation;
		this.couleur = couleur;
		this.tabOrdre = new Ordre[] {new Ordre (enumOrdre.AUCUN), new Ordre (enumOrdre.AUCUN), new Ordre (enumOrdre.AUCUN)};
		if(Robot.num > 1){ Robot.num = 0;}
		this.numRobot = Robot.num++;

		switch(orientation)
		{
			case '^': this.orientation = 0; break;
			case '>': this.orientation = 1; break;
			case 'v': this.orientation = 2; break;
			case '<': this.orientation = 3; break;
			default: ;
		}
	}


	//METHODES

	//Methode qui echange deux ordres du robot (appelee dans la classe Ihm lorsque le joueur choisit d'echanger deux ordres)
	public void echangerOrdre(int indice1, int indice2)
	{
		Ordre tmp;

		tmp = this.tabOrdre[indice1];
		this.tabOrdre[indice1] = this.tabOrdre[indice2];
		this.tabOrdre[indice2] = tmp;

	}

	//Methode qui fait avancer le robot (une ou deux foix)
	public void avancer(int nbCases)
	{
		for(int i = 0; i<nbCases; i++)
		{
			int tempPosX = this.posX; //variable temporaire creee pour tester les coordonnees des cases en face
			int tempPosY = this.posY;

			int xPlus2 = 0;
			int yPlus2 = 0;


			switch (this.orientation) //Incremente ou decremente les positions selon l'orientation du robot
			{
				case 0: tempPosX--; xPlus2 = -1; break; //xPlus2 et yPlus2 servent à recuperer la case situee deux cases devant le robot
				case 1: tempPosY++; yPlus2 = +1; break;
				case 2: tempPosX++; xPlus2 = +1; break;
				case 3: tempPosY--; yPlus2 = -1; break;
			}

			if (tempPosX < Plateau.getHauteurMax() && tempPosY < Plateau.getLargeurMax() && tempPosX >= 0 && tempPosY >= 0) //Si la case devant le robot est dans le tableau de tuiles
			{
				boolean poussable    = false; //Variable qui est true si il y a une case vide deux cases devant le robot
				Tuile   tuileEnFace  = null ; //La case en face du robot
				Tuile   tuileEnFace2 = null ; //Deux cases en face du robot


				//Recupere la position devant le robot
				try {tuileEnFace  = Plateau.getTuile(tempPosX, tempPosY);} //Si il y a une erreur, on ignore cette ligne et la variable est null
				catch (ArrayIndexOutOfBoundsException e) {}                //Cela veut dire qu'il n'y a pas de case devant, 
				                                                           //nous sommes obliges de tester cela car le terrain n'est pas un carre contrairement au tableau de tuiles


				//Recupere la position deux cases devant le robot
				try {tuileEnFace2 = Plateau.getTuile(tempPosX + xPlus2, tempPosY + yPlus2);}
				catch (ArrayIndexOutOfBoundsException e) {}


				if ( tuileEnFace2 != null && tuileEnFace2.getType() == "Tuile" ) { poussable = true; } //Modifie la variable poussable si il y a une case vide deux cases devant le robot


				if ( tuileEnFace  != null && tuileEnFace.getType()  != "Base"  ) //Si il y a une tuile devant le robot ET que ce n'est pas une base
				{

					if ( tuileEnFace.getType() == "Tuile" || (tuileEnFace.getType() == "Robot" && poussable) || (tuileEnFace.getType() == "Gemme" && poussable) ) //si le robot peut bouger (vide ou robot/gemme puis vide)
					{

						Plateau.setTuile( new Tuile("Tuile", this.posX, this.posY) , this.posX, this.posY); //La tuile ou etait le robot devient vide
						
						this.posX = tempPosX; //Les coordonnees s'actualisent
						this.posY = tempPosY;


						if ( tuileEnFace.getType() == "Robot" && poussable ) //si c'est un robot et qu'il est poussable, le pousse
						{
							tuileEnFace.setPosition(tempPosX + xPlus2, tempPosY + yPlus2); //actualise les coordonnees du robot pousse
							Plateau.setTuile((Robot)(tuileEnFace), tempPosX + xPlus2, tempPosY + yPlus2);
						}


						if ( tuileEnFace.getType() == "Gemme" && poussable ) //si c'est une gemme et qu'elle est poussable, la pousse
							Plateau.setTuile((tuileEnFace), tempPosX + xPlus2, tempPosY + yPlus2);

						Plateau.setTuile(this, this.posX, this.posY); //Le robot se place dans le tableau selon ses nouvelles coordonnees
					}

				}
			}
		}
	}

	//Methode qui fait pivoter le robot (a droite si direction = 1, a gauche si -1)
	public void tourner(int direction)
	{
		this.orientation = Math.floorMod(this.orientation + direction, 4); //modulo 4 pour n'avoir que 4 directions possibles

		switch(this.orientation) //Modifie le symbole du robot selon sa nouvelle orientation
		{
			case 0: this.orientationAffichage = '^'; break;
			case 1: this.orientationAffichage = '>'; break;
			case 2: this.orientationAffichage = 'v'; break;
			case 3: this.orientationAffichage = '<'; break;
		}

		Plateau.setTuile(this, this.posX, this.posY); //Replace le robot a son ancienne place mais avec sa nouvelle orientation
	}


	//Methode qui prend un cristal situe en face du joueur (s'il y en a un)
	public void prendreCristal()
	{
		int posCristX = this.posX; //variable temporaire creee pour tester les coordonnees de la case en face
		int posCristY = this.posY; //(qui sera la nouvelle position du cristal si il est deposable)

		switch (this.orientation)
		{
			case 0: posCristX--; break;
			case 1: posCristY++; break;
			case 2: posCristX++; break;
			case 3: posCristY--; break;
		}

		Tuile tuileEnFace = null;

		try {tuileEnFace  = Plateau.getTuile(posCristX, posCristY);} //Recupere la position devant le robot
		catch (ArrayIndexOutOfBoundsException e) {}

		if (tuileEnFace != null && this.gemme == null) //Si le robot ne possede pas de gemme et qu'il y a une case devant lui
		{
			if ( tuileEnFace.getType().equals("Gemme") ) //Si la case devant lui est une gemme, la case en face devient vide et le robot obtient une gemme
			{
				this.setGemme ((Gemme)tuileEnFace);
				tuileEnFace = new Tuile("Tuile", posCristX, posCristY);

				Plateau.setTuile(tuileEnFace, posCristX, posCristY);
			}

			if ( tuileEnFace.getType().equals("Robot") ) //Si la case devant lui est un robot
			{
				Robot robotEnFace = (Robot)(tuileEnFace);
				if (robotEnFace.getGemme() != null) //Si le robot en face a une gemme, lui prend sa gemme
				{
					this.setGemme (robotEnFace.getGemme());
					robotEnFace.setGemme(null);
				}
			}
		}
	}


	//Methode qui depose le cristal possede par le robot devant lui s'il le peut
	public void deposerCristal()
	{
		int posCristX = this.posX; //variable temporaire creee pour tester les coordonnees de la case en face
		int posCristY = this.posY; //(qui sera la nouvelle position du cristal si il est deposable)

		switch (this.orientation) //Detecte la position devant le robot
		{
			case 0: posCristX--; break;
			case 1: posCristY++; break;
			case 2: posCristX++; break;
			case 3: posCristY--; break;
		}

		Tuile tuileEnFace = null;

		try {tuileEnFace  = Plateau.getTuile(posCristX, posCristY);} //Recupere la position devant le robot
		catch (ArrayIndexOutOfBoundsException e) {}


		if ( this.getGemme() != null && tuileEnFace != null && tuileEnFace.getType() != "Gemme") //Si le robot a une gemme, s'il y a une case en face et si la case en face n'est pas une gemme
		{

			if ( tuileEnFace.getType() == "Tuile" )  //Si c'est une tuile, la gemme se depose sur la case en face
			{
				tuileEnFace = this.getGemme();
				Plateau.setTuile((tuileEnFace), posCristX, posCristY);
				this.setGemme(null);
			}


			if ( tuileEnFace.getType() == "Robot" )  //Si c'est une robot et qu'il n'a pas de gemme, lui donne notre gemme
			{
				Robot robotEnFace = (Robot)(tuileEnFace);

				if ( robotEnFace.getGemme() == null)
				{ robotEnFace.setGemme(this.getGemme()); this.setGemme(null);}
			}


			if ( tuileEnFace.getType() == "Base" )  //Si c'est une base
			{
				for (int i=0; i<Plateau.getNbJoueur(); i++) //Cette boucle est utilisee pour obtenir le joueur a qui appartient la base
				{
					Base baseEnFace = (Base)(tuileEnFace);

					if (Plateau.getJoueur(i).getCouleur() == baseEnFace.getCouleur()) //Si le joueur est de la meme couleur que la base
					{
						int gain;

						gain = this.getGemme().getGain();

						Plateau.getJoueur(i).setNbPoints( Plateau.getJoueur(i).getNbPoints() + gain ); //Ajuste le nombre de points du joueur

						if ( Plateau.getTuile((Plateau.getLargeurMax()-1)/2, (Plateau.getHauteurMax()-1)/2).getType() == "Tuile" ) //Si la case au ventre est une tuile, place une nouvelle gemme dessus
							Plateau.setTuile(Plateau.enleverCristal(), (Plateau.getLargeurMax()-1)/2, (Plateau.getHauteurMax()-1)/2);

						else //Sinon, le joueur peut choisir une case autour de cette case où placer la gemme
						{
							int nbCouches = 0;
							boolean caseVide = false;


							//Cette boucle permet d'elargir la surface ou l'on peut poser la gemme si toutes les cases autour du centre sont occupees
							while ( !caseVide )
							{
								for (int c=-nbCouches; c<nbCouches+1; c++)
									for (int d=-nbCouches; d<nbCouches+1; d++)
										if ( Plateau.getTuile( (Plateau.getLargeurMax()-1)/2 + c, (Plateau.getHauteurMax()-1)/2 + d).getType().equals("Tuile") )
											caseVide = true;


								if (!caseVide)
									nbCouches++;
							}

							//Choix de la case ou l'on place la gemme
							System.out.println("Le centre est occupe, choisissez une case ou placer le cristal (Ctr = le centre du terrain).\n");

							System.out.println(" -1/-1 | -1/0 | -1/1 "  );
							System.out.println("  0/-1 |  Ctr |  0/1 "  );
							System.out.println("  1/-1 |  1/0 |  1/1 \n");

							System.out.print("?/    ? = ");
							int choixPosX = Clavier.lire_int();
							System.out.print("\n" + choixPosX + "/?    ? = ");
							int choixPosY = Clavier.lire_int();

							//Verification du choix, s'il est incorrect on le redemande
							while ( (choixPosX == 0 && choixPosY == 0) || (choixPosX < -nbCouches || choixPosX > nbCouches || choixPosY < -nbCouches || choixPosY > nbCouches) || Plateau.getTuile( (Plateau.getLargeurMax()-1)/2 + choixPosX, (Plateau.getHauteurMax()-1)/2 + choixPosY).getType() != "Tuile" )
							{
								System.out.println("Choix incorrect\n");
								System.out.print("?/    ? = ");
								choixPosX = Clavier.lire_int();
								System.out.print("\n" + choixPosX + "/?    ? = ");
								choixPosY = Clavier.lire_int();
							}

							//Enleve un cristal du tableau de cristaux et le place a la position determinee avant
							Plateau.setTuile(Plateau.enleverCristal(), (Plateau.getLargeurMax()-1)/2 + choixPosX, (Plateau.getHauteurMax()-1)/2 + choixPosY);
						}

					}
				}

				this.setGemme(null);

			}

		}
	}

	//Methode qui effectue toutes les actions du robot
	public void faireActions()
	{
		for (int i=0; i<this.tabOrdre.length; i++)
		{
			if (this.tabOrdre[i].getType().equals("Avancer 1x"      )) { this.avancer(1);       }
			if (this.tabOrdre[i].getType().equals("Avancer 2x"      )) { this.avancer(2);       }
			if (this.tabOrdre[i].getType().equals("Tourner a gauche")) { this.tourner(-1);      }
			if (this.tabOrdre[i].getType().equals("Tourner a droite")) { this.tourner(1);       }
			if (this.tabOrdre[i].getType().equals("Charger cristal" )) { this.prendreCristal(); }
			if (this.tabOrdre[i].getType().equals("Deposer cristal" )) { this.deposerCristal(); }
		}
	}

	//Methode qui effectue une action du robot
	public void faireUneAction(int numAction)
	{
		if (this.tabOrdre[numAction].getType().equals("Avancer 1x"      )) { this.avancer(1);       }
		if (this.tabOrdre[numAction].getType().equals("Avancer 2x"      )) { this.avancer(2);       }
		if (this.tabOrdre[numAction].getType().equals("Tourner a gauche")) { this.tourner(-1);      }
		if (this.tabOrdre[numAction].getType().equals("Tourner a droite")) { this.tourner(1);       }
		if (this.tabOrdre[numAction].getType().equals("Charger cristal" )) { this.prendreCristal(); }
		if (this.tabOrdre[numAction].getType().equals("Deposer cristal" )) { this.deposerCristal(); }
	}


	//ACCESSEURS

	public int     getPosX    ()      { return this.posX       ; }
	public int     getPosY    ()      { return this.posY       ; }
	public int     getNum     ()      { return this.numRobot   ; }
	public String  getCouleur ()      { return this.couleur    ; }
	public Gemme   getGemme   ()      { return this.gemme      ; }
	public Ordre   getOrdre   (int i) { return this.tabOrdre[i]; }
	public Ordre[] getTabOrdre()      { return this.tabOrdre   ; }


	//MODIFICATEURS

	public void setOrientation(int orientation        ) { this.orientation      = orientation; }
	public void setGemme      (Gemme gemme            ) { this.gemme            = gemme      ; }
	public void setOrdre      (Ordre ordre, int indice) { this.tabOrdre[indice] = ordre      ; }


	//AFFICHAGE

	public String toString()
	{
		if (this.numRobot == 0)
		{
			if (this.gemme != null) {return Utils.couleur(this.couleur,"surligne", ""+this.orientationAffichage);}
			else                    {return Utils.couleur(this.couleur,"normal", ""+this.orientationAffichage)  ;}
		}
		else
		{
			if (this.gemme != null) {return Utils.couleur(this.couleur,"souligne-surligne", ""+this.orientationAffichage);}
			else                    {return Utils.couleur(this.couleur,"souligne", ""+this.orientationAffichage)         ;}
		}
	}
}