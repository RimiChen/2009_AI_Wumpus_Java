
public class MapNode {
	MapNode[] nodes = new MapNode[4];
	
	public int id;
	public int x;
	public int y;
	
	public int wumpus_maybe = 0;
	public int pit_maybe = 0; 

	public boolean sensor_strench;
	public MapNode s_from;
	public boolean sensor_breeze;
	public MapNode b_from;
	
	public boolean agent_been;
	public int wall = -1; //unknown
	
	public MapNode(int idid, int xx, int yy)
	{
		id = idid;
		x = xx;
		y = yy;
		agent_been = false;
		
		
	}
	
	public void Add(int direct, MapNode new_node)
	{
		nodes[direct] = new_node;
	}
	
	
	
	
	
	
}
