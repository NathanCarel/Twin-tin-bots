public enum EnumOrdre
{
	AVANCER1X          ("Avancer 1x"         , 2),
	AVANCER2X          ("Avancer 2x"         , 1),
	TOURNERG           ("Tourner à  gauche"  , 3),
	TOURNERD           ("Tourner à  droite"  , 3),
	CHARGERCRISTAL     ("Charger cristal"    , 2),
	DEPOSERCRISTAL     ("Deposer cristal"    , 2),
	ZAP                ("Zap"                , 2),
	DOUBLEMODIFICATION ("Double modification", 1),
    AUCUN             ("Aucun"              , 0);

	private String type;
    private int nbExemplaires;

    private EnumOrdre(String type, int nbExemplaires)
    {
    	this.type          = type;
        this.nbExemplaires = nbExemplaires;
    }

    public String getType()
    {
    	return this.type;
    }

    public int getNbExemplaires()
    {
    	return this.nbExemplaires;
    }

    public String toString()
    {
        return this.type;
    }
}
