public class Tuile
{
    protected String type;
    protected int    posX;
    protected int    posY;

    public Tuile(String type, int x, int y)
    {
        this.type = type;
        this.posX = x;
        this.posY = y;
    }

    public String getType () { return this.type; }
    public int    getPosX () { return this.posX; }
    public int    getPosY () { return this.posY; }

    public void   setPosition(int x, int y) { this.posX = x; this.posY = y; }

    public String toString()
    {
        String chaine = "";

        chaine += " ";

        return chaine;
    }
}
