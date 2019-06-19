public class Ordre
{
	private String type;
    private int nbExemplaires;

    public Ordre(enumOrdre ordre)
    {
    	this.type          = ordre.getType();
        this.nbExemplaires = ordre.getNbExemplaires();
    }

    public String getType()
    {
    	return this.type;
    }

    public int getNbExemplaires()
    {
    	return this.nbExemplaires;
    }

    public void setNbExemplairesMoinsUn() { this.nbExemplaires--; }
    public void setNbExemplairesPlusUn()  { this.nbExemplaires++; }

    public String toString()
    {
        return this.type;
    }
}