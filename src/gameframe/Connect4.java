package gameframe;

public class Connect4
{
	int [][] status;
	int max_hoehe, max_breite;
	int zug_hoehe, zug_breite;
	
	/**
	 * Spielfeld als Array mit Groesse und Staus 
	 * 
	 * @param field Array mit drei verschiedenen Zustaenden 
	 * [0] nicht belegt, [1] belegt von Spieler 1, [2] belegt von Spieler 2
	 * @param maxhoehe  die maximale Hoehe des Array
	 * @param maxbreite die maximale Breite des Array
	 */
	Connect4(int field [][],int maxhoehe, int maxbreite)
	{
		status = field ;
		max_hoehe = maxhoehe;
		max_breite = maxbreite;
	}
	
	/**
       	 * Die Methode player erhaelt die Koordinaten des Feldes auf das der
         * Spieler geklickt hat. Danach wird das Feld von 0 auf 1 oder 2
	 * gesetzt
	 * 
	 * @param hoehe
	 * @param breite
	 * @param player_id
	 * @return gibt den Staus des Spielfeldes zurueck
	 */
	public int [][] player (int hoehe, int breite, int player_id)
	{
		for (int i = max_hoehe-1; i >= 0; i--)
		{
			if(status[i][breite] == 0)
			{
				status[i][breite] = player_id;
				zug_hoehe = i;
				zug_breite = breite;
				break;
			}
		}		
		return status;
	}
  public void testFunktion(){
    System.out.println("hallo");
  }
	/**
	 * In dieser Methode wird überprueft, ob ein Spieler gewonnen hat.
	 * @return
	 */
	public boolean has_won ()
	{
		int spieler_id = status[zug_hoehe][zug_breite];
		int zahl = 0;
		for (int i = zug_hoehe+3,j = zug_breite-3;
                        j <= zug_breite+3 && i >= zug_hoehe-3;j++, i--)
		{
                        try
                        {
                            if(status[i][j] == spieler_id)
                            {
                                zahl++;

                                if(zahl>=4) {
                                    return true;
                                }
                            }
                            else
                            {
                                zahl = 0;						
                            }
                        }
                        catch (Exception e)	{}
		}
		zahl=0;
		for (int i = zug_hoehe+3,j = zug_breite+3;
                        j >= zug_breite-3 && i >= zug_hoehe-3;j--, i--)
		{
                        try
                        {
                            if(status[i][j] == spieler_id)
                            {
                                zahl++;

                                if(zahl>=4) {
                                    return true;
                                }
                            }
                            else
                            {
                                zahl = 0;						
                            }
                        }
                        catch (Exception e){}
		}
		zahl=0;
		for(int i = zug_breite-3; i<=zug_breite+3;i++)
		{
                    try
                    {
                        if(status[zug_hoehe][i]==spieler_id)
                        {
                            zahl++;

                            if(zahl>=4) {
                                return true;
                            }
                        }
                        else
                        {
                            zahl = 0;
                        }
                    }
                    catch(Exception e){}
		}
		zahl=0;
		for(int i = zug_hoehe; i<= zug_hoehe+3;i++)
		{
                    try
                    {
                        if(status[i][zug_breite]==spieler_id)
                        {
                                zahl++;

                                if(zahl>=4) {
                                return true;
                            }
                        }
                        else
                        {
                                zahl = 0;
                        }
                    }
                    catch(Exception e){}
		}
		System.out.println();
		return false;
	}
}
