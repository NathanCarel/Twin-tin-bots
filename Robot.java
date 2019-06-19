public class Robot extends Tuile
{
  private static char[] tabOrientation = new char[] {'N', 'E', 'S', 'O'};
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
    this.tabOrdre = new Ordre[3];

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

  public void  setGemme(Gemme gemme) { this.gemme = gemme; }
  public Gemme getGemme()            { return this.gemme;  }

  public void  setOrdre(Ordre ordre, int indice) { this.tabOrdre[indice] = ordre; }
  public Ordre getOrdre(int i)                   { return this.tabOrdre[i];       }
  public Ordre[] getTabOrdre()                   { return this.tabOrdre;          }


  public void avancer(int nbCases)
  {
    for(int i = 0; i<nbCases; i++)
    switch (this.orientation)
    {
      case 0: this.posX--; break;
      case 1: this.posY++; break;
      case 2: this.posX++; break;
      case 3: this.posY--; break;
    }
  }


  public void tourner(int direction)
  {
    this.orientation = Robot.tabOrientation[(this.orientation+direction)%4];
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

    if ( tuileEnFace.getType().equals("Gemme") ) 
    {
        this.setGemme ((Gemme)tuileEnFace);
        tuileEnFace = new Tuile("Tuile", posCristX, posCristY);
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

    if ( tuileEnFace.getType().equals("Tuile")  ) 
    {
        tuileEnFace = this.getGemme();
        this.setGemme(null);
    }

    if ( tuileEnFace.getType().equals("Robot") ) 
    {
        Robot robotEnFace = (Robot)(tuileEnFace);

        if (robotEnFace.getGemme() == null)
        {
            robotEnFace.setGemme(this.getGemme());
            this.setGemme(null);
        }
    }

    if ( tuileEnFace.getType().equals("Base")  ) 
    {
        for (int i=0; i<Plateau.getNbJoueur(); i++)
        {
            Base baseEnFace = (Base)(tuileEnFace);

            if (Plateau.getJoueur(i).getCouleur() == baseEnFace.getCouleur())
            {
                int gain;

                switch (this.getGemme().getCouleur())
                {
                    case "Bleu": gain = 2; break;
                    case "Vert": gain = 3; break;
                    case "Rose": gain = 4; break;
                    default: gain = 0; break;
                }

                Plateau.getJoueur(i).setNbPoints( Plateau.getJoueur(i).getNbPoints() + gain );
            }
        }

        this.setGemme(null);
    }
  }


  public String getCouleur() { return this.couleur; }

  public String toString()
  {
    if( this.couleur.equals("Rouge" )){return "\033[0;31m"+this.orientationAffichage+"\033[0m";}
    if( this.couleur.equals("Jaune" )){return "\033[0;33m"+this.orientationAffichage+"\033[0m";}
    if( this.couleur.equals("Vert"  )){return "\033[0;32m"+this.orientationAffichage+"\033[0m";}
    if( this.couleur.equals("Bleu"  )){return "\033[0;34m"+this.orientationAffichage+"\033[0m";}
    if( this.couleur.equals("Violet")){return "\033[0;35m"+this.orientationAffichage+"\033[0m";}
    if( this.couleur.equals("Cyan"  )){return "\033[0;36m"+this.orientationAffichage+"\033[0m";}

    return "";
  }
}