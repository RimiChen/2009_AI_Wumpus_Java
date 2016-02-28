
public class Game {
	static WorldMap worldmap = new WorldMap();
	static int agent_x = 0;
	static int agent_y = 0;
	static int agent_face = 0;	//0:right  1:down  2:left  3:up
	static boolean arrow_allowed = true;
	static boolean wumpus_died = false;
	static int score = 0;
	static int step = 0;
	static boolean win = false;
	static boolean gameover = false;
	
	
	
	
/*	public static void main(String[] args)
	{
		int i, j;
		worldmap.MakeMap();

		for(i=0; i<4; i++){
			System.out.println("-----------------------------");
			for(j=0; j<4; j++){
				System.out.printf("|");
				if( worldmap.map[i][j].gold )
					System.out.printf("G");
				else
					System.out.printf(" ");
				if( worldmap.map[i][j].sensor_glitter )
					System.out.printf("g");
				else
					System.out.printf(" ");
				
				if( worldmap.map[i][j].wumpus )
					System.out.printf("W");
				else
					System.out.printf(" ");
				if( worldmap.map[i][j].sensor_strench )
					System.out.printf("&");
				else
					System.out.printf(" ");
				
				
				if( worldmap.map[i][j].pit )
					System.out.printf("P");
				else
					System.out.printf(" ");
				if( worldmap.map[i][j].sensor_breeze )
					System.out.printf("~");
				else
					System.out.printf(" ");
				
				//System.out.printf("|");
			}
			System.out.printf("|\n");
		}
		System.out.println("-----------------------------");
	}
*/	
	public void TurnLeft()
	{
		score -= 1;
		agent_face--;
		if( agent_face<0 )
			agent_face = 3;
	}
	
	public void TurnRight()
	{
		score -= 1;
		agent_face++;
		if( agent_face>3 )
			agent_face = 0;
	}
	
	public boolean MoveForward()
	{
		score -= 1;
		step++;
		switch( agent_face ){
		case 0: //right
			if( agent_x<3 ){
				agent_x++;			
				UpdateMap(agent_x, agent_y);
				return true;
			}
			else
				return false;
		
		case 1: //down
			if( agent_y>0 ){
				agent_y--;
				UpdateMap(agent_x, agent_y);
				return true;
			}
			else
				return false;
		
		case 2: //left
			if( agent_x>0 ){
				agent_x--;
				UpdateMap(agent_x, agent_y);
				return true;
			}
			else
				return false;
		
		case 3: //up
			if( agent_y<3 ){
				agent_y++;
				UpdateMap(agent_x, agent_y);
				return true;
			}
			else
				return false;
		default:
			return false;	
		}
	}
	
	public boolean Grab()
	{
		score -= 1;
		if( worldmap.map[agent_x][agent_y].gold==true ){
			score += 1000;
			win = true;
			return true;
		}
		else 
			return false;
			
	}

	public boolean Shoot()
	{
		int i;
		if( arrow_allowed ){
			arrow_allowed = false;
			score -= 10;
			switch( agent_face ){
			case 0: //to right
				for(i=agent_x+1; i<4; i++){
					if( worldmap.map[i][agent_y].wumpus==true ){
						worldmap.map[i][agent_y].wumpus = false;
						worldmap.map[i][agent_y].died_wumpus = true;
						worldmap.WumpusKilled();
						wumpus_died = true;
						break;
					}
				}
				break;
			case 1: //down
				for(i=agent_y-1; i>=0; i--){
					if( worldmap.map[agent_x][i].wumpus==true ){
						worldmap.map[agent_x][i].wumpus = false;
						worldmap.map[agent_x][i].died_wumpus = true;
						worldmap.WumpusKilled();
						wumpus_died = true;
						break;
					}
				}
				break;
			case 2: //left
				for(i=agent_x-1; i>=0; i--){
					if( worldmap.map[i][agent_y].wumpus==true ){
						worldmap.map[i][agent_y].wumpus = false;
						worldmap.map[i][agent_y].died_wumpus = true;
						worldmap.WumpusKilled();
						wumpus_died = true;
						break;
					}
				}
				break;
			case 3: //up
				for(i=agent_y+1; i<4; i++){
					if( worldmap.map[agent_x][i].wumpus==true ){
						worldmap.map[agent_x][i].wumpus = false;
						worldmap.map[agent_x][i].died_wumpus = true;
						worldmap.WumpusKilled();
						wumpus_died = true;
						break;
					}
				}
				break;
			
			}
			
			return true;
		}
		else{
			System.out.println("no arrows!");
			return false;
		}
			
	}
	
	public void Print()
	{
		int i, j;
		worldmap.MakeMap();

		for(i=0; i<4; i++){
			System.out.println("-----------------------------");
			for(j=0; j<4; j++){
				System.out.printf("|");
				if( worldmap.map[i][j].gold )
					System.out.printf("G");
				else
					System.out.printf(" ");
				if( worldmap.map[i][j].sensor_glitter )
					System.out.printf("g");
				else
					System.out.printf(" ");
				
				if( worldmap.map[i][j].wumpus )
					System.out.printf("W");
				else
					System.out.printf(" ");
				if( worldmap.map[i][j].sensor_strench )
					System.out.printf("&");
				else
					System.out.printf(" ");
				
				
				if( worldmap.map[i][j].pit )
					System.out.printf("P");
				else
					System.out.printf(" ");
				if( worldmap.map[i][j].sensor_breeze )
					System.out.printf("~");
				else
					System.out.printf(" ");
				
				//System.out.printf("|");
			}
			System.out.printf("|\n");
		}
		System.out.println("-----------------------------");
	}
	
	
	public Room GetRoom()
	{
		return worldmap.map[agent_x][agent_y];
	}
	
	public void UpdateMap(int x, int y)
	{
		if( worldmap.map[x][y].wumpus==true ){
			score -= 1000;
			gameover = true;
		}
		else if( worldmap.map[x][y].pit==true ){
			score -= 1000;
			gameover = true;
		}
//		else
//			agentmap.map[x][y] = worldmap.map[x][y];
		
	}
}
