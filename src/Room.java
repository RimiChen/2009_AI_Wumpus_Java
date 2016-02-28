
public class Room {
	public int location_x;
	public int location_y;
	
	
	public boolean wumpus;
	public boolean died_wumpus;
	public boolean pit;
	public boolean gold;
	
	public boolean sensor_strench;
	public boolean sensor_breeze;
	public boolean sensor_glitter;
	public boolean sensor_scream;
	
	public boolean agent_here;
	
	public Room(int x, int y)
	{
		//System.out.println("room");
		location_x = x;
		location_y = y;
		
		wumpus = false;
		died_wumpus = false;
		pit = false;
		gold = false;
		sensor_strench = false;
		sensor_breeze = false;
		sensor_glitter = false;
		sensor_scream = false;
		
		agent_here = false;
	}
	
}
