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

	public void  setGemme(Gemme gemme) { this.gemme = gemme; }
	public Gemme getGemme()            { return this.gemme;  }

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
				Tuile tuileEnFace2 = Plateau.getTuile(tempPosX + xPlus2, tempPosY + yPlus2);

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
							Plateau.setTuile((Gemme)(tuileEnFace), tempPosX + xPlus2, tempPosY + yPlus2);

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

		if (tuileEnFace != null)
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
// if(this.numRobot == 0)      {return "\033[0;31m"+this.orientationAffichage+"\033[0m";} //7 surligner et 4 souligner
        // else if (this.gemme != null){return "\033[7;31m"+this.orientationAffichage+"\033[0m";}
        // else                        {return "\033[4;31m"+this.orientationAffichage+"\033[0m";}
        // if( this.couleur.equals("jaune" ))
        // if(this.numRobot == 0)      {return "\033[0;33m"+this.orientationAffichage+"\033[0m";}
        // else if (this.gemme != null){return "\033[7;33m"+this.orientationAffichage+"\033[0m";}
        // else                        {return "\033[4;33m"+this.orientationAffichage+"\033[0m";}
        // if( this.couleur.equals("vert"  ))
        // if(this.numRobot == 0)      {return "\033[0;32m"+this.orientationAffichage+"\033[0m";}
        // else if (this.gemme != null){return "\033[7;32m"+this.orientationAffichage+"\033[0m";}
        // else                        {return "\033[4;32m"+this.orientationAffichage+"\033[0m";}
        // if( this.couleur.equals("bleu"  ))
        // if(this.numRobot == 0)      {return "\033[0;34m"+this.orientationAffichage+"\033[0m";}
        // else if (this.gemme != null){return "\033[7;34m"+this.orientationAffichage+"\033[0m";}
        // else                        {return "\033[4;34m"+this.orientationAffichage+"\033[0m";}
        // if( this.couleur.equals("violet"))
        // if(this.numRobot == 0)      {return "\033[0;35m"+this.orientationAffichage+"\033[0m";}
        // else if (this.gemme != null){return "\033[7;35m"+this.orientationAffichage+"\033[0m";}
        // else                        {return "\033[4;35m"+this.orientationAffichage+"\033[0m";}
        // if( this.couleur.equals("cyan"  ))
        // if(this.numRobot == 0)      {return "\033[0;36m"+this.orientationAffichage+"\033[0m";}
        // else if (this.gemme != null){return "\033[7;31m"+this.orientationAffichage+"\033[0m";}
        // else                        {return "\033[4;36m"+this.orientationAffichage+"\033[0m";}
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
				Plateau.setTuile((Gemme)(tuileEnFace), posCristX, posCristY);
			}

			if ( tuileEnFace.getType() == "Robot" )
			{
				Robot robotEnFace = (Robot)(tuileEnFace);

				if ( robotEnFace.getGemme() == null)
				{ robotEnFace.setGemme(this.getGemme()); }
			}

			if ( tuileEnFace.getType() == "Base" ) //Condition non testée
			{
				for (int i=0; i<Plateau.getNbJoueur(); i++)
				{
					Base baseEnFace = (Base)(tuileEnFace);

					if (Plateau.getJoueur(i).getCouleur() == baseEnFace.getCouleur())
					{
						int gain;

						switch (this.getGemme().getCouleur())
						{
							case "bleu": gain = 2; break;
							case "vert": gain = 3; break;
							case "rose": gain = 4; break;
							default: gain = 0; break;
						}

						Plateau.getJoueur(i).setNbPoints( Plateau.getJoueur(i).getNbPoints() + gain );
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
      if( this.couleur.equals("rouge" ))
			{
      	if(this.numRobot == 0)     //7 surligner et 4 souligner
				{
					if (this.gemme != null) {return "\033[7;31m"+this.orientationAffichage+"\033[0m";}
      		else                    {return "\033[0;31m"+this.orientationAffichage+"\033[0m";}
				}
				else {return "\033[4;31m"+this.orientationAffichage+"\033[0m";}
			}

			if( this.couleur.equals("jaune" ))
			{
				if(this.numRobot == 0)     //7 surligner et 4 souligner
				{
					if (this.gemme != null) {return "\033[7;33m"+this.orientationAffichage+"\033[0m";}
					else                    {return "\033[0;33m"+this.orientationAffichage+"\033[0m";}
				}
				else {return "\033[4;33m"+this.orientationAffichage+"\033[0m";}
			}

			if( this.couleur.equals("vert"  ))
			{
				if(this.numRobot == 0)     //7 surligner et 4 souligner
				{
					if (this.gemme != null) {return "\033[7;32m"+this.orientationAffichage+"\033[0m";}
					else                    {return "\033[0;32m"+this.orientationAffichage+"\033[0m";}
				}
				else {return "\033[4;32m"+this.orientationAffichage+"\033[0m";}
			}

      if( this.couleur.equals("bleu"  ))
			{
				if(this.numRobot == 0)     //7 surligner et 4 souligner
				{
					if (this.gemme != null) {return "\033[7;34m"+this.orientationAffichage+"\033[0m";}
					else                    {return "\033[0;34m"+this.orientationAffichage+"\033[0m";}
				}
				else {return "\033[4;34m"+this.orientationAffichage+"\033[0m";}
			}

      if( this.couleur.equals("violet"))
			{
				if(this.numRobot == 0)     //7 surligner et 4 souligner
				{
					if (this.gemme != null) {return "\033[7;31m"+this.orientationAffichage+"\033[0m";}
					else                    {return "\033[0;31m"+this.orientationAffichage+"\033[0m";}
				}
				else {return "\033[4;31m"+this.orientationAffichage+"\033[0m";}
			}

      if( this.couleur.equals("cyan"  ))
			{
				if(this.numRobot == 0)     //7 surligner et 4 souligner
				{
					if (this.gemme != null) {return "\033[7;36m"+this.orientationAffichage+"\033[0m";}
					else                    {return "\033[0;36m"+this.orientationAffichage+"\033[0m";}
				}
				else {return "\033[4;36m"+this.orientationAffichage+"\033[0m";}
			}

    	return "" + this.orientationAffichage;
    }
}
