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

    public void retirer()  { this.nbExemplaires--; }
    public void ajouter()  { this.nbExemplaires++; }

    public String toString()
    {
        return this.type;
    }
}