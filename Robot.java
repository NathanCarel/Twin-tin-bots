import iut.algo.*;
public class Robot extends Tuile
{
	private static char[] tabOrientation = new char[] {'N', 'E', 'S', 'O'};
	private static int num;
	private int numRobot;
	private int orientation = 0;
	private char orientationAffichage;
	private String couleur;
	private Gemme  gemme;
	private Ordre[] tabOrdre;

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


	public void setOrientation(int orientation) { this.orientation = orientation; };

	public int getPosX() { return this.posX; }
	public int getPosY() { return this.posY; }

	public void  setGemme(Gemme gemme) { this.gemme = gemme;   }
	public Gemme getGemme()            { return this.gemme;    }
	public int   getNum()              { return this.numRobot; }

	public void  setOrdre(Ordre ordre, int indice) { this.tabOrdre[indice] = ordre; }
	public Ordre getOrdre(int i)                   { return this.tabOrdre[i];       }
	public Ordre[] getTabOrdre()                   { return this.tabOrdre;          }

	public void echangerOrdre(int indice1, int indice2)
	{
		Ordre tmp;

		tmp = this.tabOrdre[indice1];
		this.tabOrdre[indice1] = this.tabOrdre[indice2];
		this.tabOrdre[indice2] = tmp;

	}

	public void avancer(int nbCases)
	{
		for(int i = 0; i<nbCases; i++)
		{
			int tempPosX = this.posX;
			int tempPosY = this.posY;

			int xPlus2 = 0;
			int yPlus2 = 0;


			switch (this.orientation)
			{
				case 0: tempPosX--; xPlus2 = -1; break;
				case 1: tempPosY++; yPlus2 = +1; break;
				case 2: tempPosX++; xPlus2 = +1; break;
				case 3: tempPosY--; yPlus2 = -1; break;
			}

			if (tempPosX < Plateau.getHauteurMax() && tempPosY < Plateau.getLargeurMax() && tempPosX >= 0 && tempPosY >= 0)
			{
				boolean poussable  = false;
				Tuile tuileEnFace  = Plateau.getTuile(tempPosX, tempPosY);
				Tuile tuileEnFace2 = null;

				try {tuileEnFace2 = Plateau.getTuile(tempPosX + xPlus2, tempPosY + yPlus2);}
				catch (ArrayIndexOutOfBoundsException e) {}

				if ( tuileEnFace2 != null && tuileEnFace2.getType() == "Tuile" ) {poussable = true;}

				if ( tuileEnFace != null && tuileEnFace.getType() != "Base" )
				{
					if ( tuileEnFace.getType() == "Tuile" || (tuileEnFace.getType() == "Robot" && poussable) || (tuileEnFace.getType() == "Gemme" && poussable) )
					{
						Plateau.setTuile( new Tuile("Tuile", this.posX, this.posY) , this.posX, this.posY);
						this.posX = tempPosX;
						this.posY = tempPosY;

						if ( tuileEnFace.getType() == "Robot" && poussable )
						{
							tuileEnFace.setPosition(tempPosX + xPlus2, tempPosY + yPlus2);
							Plateau.setTuile((Robot)(tuileEnFace), tempPosX + xPlus2, tempPosY + yPlus2);
						}

						if ( tuileEnFace.getType() == "Gemme" && poussable )
						Plateau.setTuile((tuileEnFace), tempPosX + xPlus2, tempPosY + yPlus2);

						Plateau.setTuile(this, this.posX, this.posY);
					}
				}
				else
				{ Plateau.setTuile(this, this.posX, this.posY); }
			}
		}
	}


	public void tourner(int direction)
	{
		this.orientation = Math.floorMod(this.orientation + direction, 4); //modulo 4 pour n'avoir que 4 directions possibles

		switch(this.orientation)
		{
			case 0: this.orientationAffichage = '^'; break;
			case 1: this.orientationAffichage = '>'; break;
			case 2: this.orientationAffichage = 'v'; break;
			case 3: this.orientationAffichage = '<'; break;
		}

		Plateau.setTuile(this, this.posX, this.posY);
	}


	public void prendreCristal()
	{
		int posCristX = this.posX;
		int posCristY = this.posY;

		switch (this.orientation)
		{
			case 0: posCristX--; break;
			case 1: posCristY++; break;
			case 2: posCristX++; break;
			case 3: posCristY--; break;
		}

		Tuile tuileEnFace = Plateau.getTuile(posCristX, posCristY);

		if (tuileEnFace != null && this.gemme == null)
		{
			if ( tuileEnFace.getType().equals("Gemme") )
			{
				this.setGemme ((Gemme)tuileEnFace);
				tuileEnFace = new Tuile("Tuile", posCristX, posCristY);

				Plateau.setTuile(tuileEnFace, posCristX, posCristY);
			}

			if ( tuileEnFace.getType().equals("Robot") )
			{
				Robot robotEnFace = (Robot)(tuileEnFace);
				if (robotEnFace.getGemme() != null)
				{
					this.setGemme (robotEnFace.getGemme());
					robotEnFace.setGemme(null);
				}
			}
		}
	}


	public void deposerCristal()
	{
		int posCristX = this.posX;
		int posCristY = this.posY;

		switch (this.orientation)
		{
			case 0: posCristX--; break;
			case 1: posCristY++; break;
			case 2: posCristX++; break;
			case 3: posCristY--; break;
		}

		Tuile tuileEnFace = Plateau.getTuile(posCristX, posCristY);

		if ( this.getGemme() != null && tuileEnFace != null && tuileEnFace.getType() != "Gemme")
		{
			if ( tuileEnFace.getType() == "Tuile" )
			{
				tuileEnFace = this.getGemme();
				Plateau.setTuile((tuileEnFace), posCristX, posCristY);
			}

			if ( tuileEnFace.getType() == "Robot" )
			{
				Robot robotEnFace = (Robot)(tuileEnFace);

				if ( robotEnFace.getGemme() == null)
				{ robotEnFace.setGemme(this.getGemme()); }
			}

			if ( tuileEnFace.getType() == "Base" )
			{
				for (int i=0; i<Plateau.getNbJoueur(); i++)
				{
					Base baseEnFace = (Base)(tuileEnFace);

					if (Plateau.getJoueur(i).getCouleur() == baseEnFace.getCouleur())
					{
						int gain;

						gain = this.getGemme().getGain();

						Plateau.getJoueur(i).setNbPoints( Plateau.getJoueur(i).getNbPoints() + gain );

						if ( Plateau.getTuile((Plateau.getLargeurMax()-1)/2, (Plateau.getHauteurMax()-1)/2).getType() == "Tuile" )
							Plateau.setTuile(Plateau.enleverCristal(), (Plateau.getLargeurMax()-1)/2, (Plateau.getHauteurMax()-1)/2);

						else
						{
							int nbCouches = 1;
							boolean caseVide = false;

							while ( !caseVide )
							{
								for (int c=1; c<nbCouches+1; c++)
									for (int d=1; d<nbCouches+1; d++)
										if ( Plateau.getTuile( (Plateau.getLargeurMax()-1)/2 + c, (Plateau.getHauteurMax()-1)/2 + d).getType() == "Tuile" )
											caseVide = true;

								if (!caseVide)
									nbCouches++;
							}

							System.out.println("Le centre est occup�, choisissez une case o� placer le cristal (Ctr = le centre du terrain).\n");
							//System.out.println(" "  + -nbCouches + "/" + -nbCouches + " | "  + -nbCouches + "/0 | "  + -nbCouches + "/" + nbCouches + " "  );
							//System.out.println("  0/"                  + -nbCouches +          " |  Ctr | "          + " 0/"            + nbCouches + " "  );
							//System.out.println("  " +  nbCouches + "/" + -nbCouches + " |  " +  nbCouches + "/0 |  " +  nbCouches + "/" + nbCouches + " \n");

							System.out.println(" -1/-1 | -1/0 | -1/1 "  );
							System.out.println("  0/-1 |  Ctr |  0/1 "  );
							System.out.println("  1/-1 |  1/0 |  1/1 \n");


							int choixPosX = Clavier.lire_int();
							int choixPosY = Clavier.lire_int();

							while ( (choixPosX == 0 && choixPosY == 0) || (choixPosX < -nbCouches || choixPosX > nbCouches || choixPosY < -nbCouches || choixPosY > nbCouches) || Plateau.getTuile( (Plateau.getLargeurMax()-1)/2 + choixPosX, (Plateau.getHauteurMax()-1)/2 + choixPosY).getType() != "Tuile" )
							{
								System.out.println("Choix incorrect\n");
								choixPosX = Clavier.lire_int();
								choixPosY = Clavier.lire_int();
							}

							Plateau.setTuile(Plateau.enleverCristal(), (Plateau.getLargeurMax()-1)/2 + choixPosX, (Plateau.getHauteurMax()-1)/2 + choixPosY);
						}

					}
				}

			}

			this.setGemme(null);
		}
	}


	public void faireActions() //Méthode qui effectue toutes les actions du robot
	{
		for (int i=0; i<this.tabOrdre.length; i++)
		{
			if (this.tabOrdre[i].getType().equals("Avancer 1x"      )) { this.avancer(1);       }
			if (this.tabOrdre[i].getType().equals("Avancer 2x"      )) { this.avancer(2);       }
			if (this.tabOrdre[i].getType().equals("Tourner à gauche")) { this.tourner(-1);      }
			if (this.tabOrdre[i].getType().equals("Tourner à droite")) { this.tourner(1);       }
			if (this.tabOrdre[i].getType().equals("Charger cristal" )) { this.prendreCristal(); }
			if (this.tabOrdre[i].getType().equals("Deposer cristal" )) { this.deposerCristal(); }
		}
	}


	public String getCouleur() { return this.couleur; }

	public String toString()
	{
		if(this.numRobot == 0)
		{
			if (this.gemme != null) {return Utils.couleur(this.couleur,"surligne", ""+this.orientationAffichage);}
			else                    {return Utils.couleur(this.couleur,"normal", ""+this.orientationAffichage);  }
		}
		else
		{
			if (this.gemme != null) {return Utils.couleur(this.couleur,"souligne-surligne", ""+this.orientationAffichage);}
			else                    {return Utils.couleur(this.couleur,"souligne", ""+this.orientationAffichage);         }
		}
	}
}
