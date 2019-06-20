/**
 ** @author Romain Loisel
**/

public abstract class Utils
{
  private static String[][] tabCouleurs = new String[][] {{"rouge","31m"}, {"jaune","33m"},
                                                          {"vert","32m" }, {"bleu","34m" },
                                                          {"mauve","35m"}, {"cyan","36m" }};

  private static String[][] tabType     = new String[][] {{"souligne","4;"},{"surligne","7;"},{"normal","0;"},{"souligne-surligne","74;"}};

  public static String couleur(String couleur, String type, String phrase)
  {
    String chaine = "";

    for(int i = 0; i < Utils.tabType.length; i++)
    {
      for(int j = 0; j < Utils.tabCouleurs.length; j++)
      {
        if(couleur.equals(tabCouleurs[j][0]) && type.equals(tabType[i][0]))
        { chaine = ("\033["+tabType[i][1]+tabCouleurs[j][1]); }
      }
    }
    chaine =  chaine + phrase + "\033[0m";
    return chaine;
  }
}
