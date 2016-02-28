import java.lang.Math;

public class WorldMap {
	public static Room[][] map = new Room[4][];
	public int pits_num = 0;
	
	public WorldMap()
	{
		int x, y;
		
		for(x=0; x<4; x++)
			map[x] = new Room[4];
			
		for(x=0; x<4; x++){
			for(y=0; y<4; y++)
				map[x][y] = new Room(x, y);
		}
	}
	
	public void MakeMap()
	{
		int x, y;
		int p;
		
		//start square
		map[0][0].agent_here = true;
		
		//place the gold
		do{
			x = (int)(Math.random()*4);
			y = (int)(Math.random()*4);
		}while( x==0 && y==0 );
		map[x][y].gold = true;
		
		//place the wumpus
		//wumpus won't be in the same place with the gold
		do{
			x = (int)(Math.random()*4);
			y = (int)(Math.random()*4);	
		}while( (x==0 && y==0) || map[x][y].gold == true );
		map[x][y].wumpus = true;
		
		//place pits
		for(x=0; x<4; x++){
			for(y=0; y<4; y++){
				if( x==0 && y==0 )
					continue;
				if( map[x][y].wumpus == true )
					continue;
				
				p = (int) (Math.random()*10);
				if( p<2 ){
					map[x][y].pit = true;
					pits_num++;
				}
				else
					map[x][y].pit = false;
			}		
		}
		
		//set sensors
		for(x=0; x<4; x++){
			for(y=0; y<4; y++){
				if( map[x][y].gold == true ){
					map[x][y].sensor_glitter = true;
				}
				
				if( map[x][y].wumpus == true ){
					if(x>0) map[x-1][y].sensor_strench = true;
					if(x<3) map[x+1][y].sensor_strench = true;
					if(y>0) map[x][y-1].sensor_strench = true;
					if(y<3) map[x][y+1].sensor_strench = true;
				}
				
				if( map[x][y].pit == true ){
					if(x>0) map[x-1][y].sensor_breeze = true;
					if(x<3) map[x+1][y].sensor_breeze = true;
					if(y>0) map[x][y-1].sensor_breeze = true;
					if(y<3) map[x][y+1].sensor_breeze = true;
				}
					
			}
		}
		
		
		//map[1][2].wumpus = true;
		//map[0][3].sensor_breeze = true;
		
	}
	
	public void WumpusKilled()
	{
		int x, y;
		for(x=0; x<4; x++){
			for(y=0; y<4; y++){
				map[x][y].sensor_scream = true;
				map[x][y].sensor_strench = false;
			}
		}
	}

	
}
