/*----------------------------------------------------------*/
/* CLASSE QUI DEFINIT LES ORDRES POUVANT ETRE CREES AVEC    */
/* LA CLASSE ORDRE                                          */
/*----------------------------------------------------------*/

public enum enumOrdre
{
    //DEFINITION DES ENUMS

    AVANCER1X          ("Avancer 1x"         , 2),
    AVANCER2X          ("Avancer 2x"         , 1),
    TOURNERG           ("Tourner a gauche"   , 3),
    TOURNERD           ("Tourner a droite"   , 3),
    CHARGERCRISTAL     ("Charger cristal"    , 2),
    DEPOSERCRISTAL     ("Deposer cristal"    , 2),
    AUCUN              ("Aucun"              , 0); //L'ordre Aucun est attribué lorsque qu'il n'y a pas d'action à faire


    //ATTRIBUTS

    private String type;
    private int nbExemplaires;


    //CONSTRUCTEUR

    private enumOrdre(String type, int nbExemplaires)
    {
        this.type          = type;
        this.nbExemplaires = nbExemplaires;
    }


    //ACCESSEURS

    public String getType()        { return this.type;          }
    public int getNbExemplaires()  { return this.nbExemplaires; }


    //AFFICHAGE

    public String toString() { return this.type; }
}