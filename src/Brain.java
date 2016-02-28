import java.util.Vector;



public class Brain {
	int[] pos = new int[2];
	int face = 0;
	Vector<MapNode> mymap = new Vector<MapNode>();
	MapNode current;
	MapNode pre;
	
	boolean wumpus_known = false;
	boolean wumpus_died = false;
	
	int check_search_wumpus=0;	//���X�Ӧ��������l
	int wumpus_x=0;		//�T�w��wumpus��m
	int wumpus_y=0;
	MapNode[] wumpus_potential = new MapNode[3]; 	//���������l
	
	Vector<Integer> response = new Vector<Integer>();
	
	public Brain()
	{
		MapNode node;
		node = new MapNode(0, 0, 0);
		mymap.add(node);
		current = node;
	}
	
	public MapNode FindNode(int x, int y)
	{
		MapNode temp;
		int size = mymap.size();
		for(int i=0; i<size; i++){
			temp = mymap.get(i);
			if( temp.x==x && temp.y==y )
				return temp;
		}
		return null;
	}
	
	public void ThisIsWall()
	{
		
		current.wall = 1;
		current = pre;
		pos[0] = pre.x;
		pos[1] = pre.y;
	}
	
	private boolean HasBeen(int x, int y)
	{
		MapNode temp;
		
		for(int i=0; i<mymap.size(); i++){
			temp = mymap.get(i);
			if( temp.x==x && temp.y==y){
				return temp.agent_been;
			}
		}
		
		return false;
	}
	
	//����room
	public void Tell(Room now_room)
	{	
		
		int i;
		pos[0] = now_room.location_x;
		pos[1] = now_room.location_y;
		
		//grab gold
		//�Y�ثe�Ҧb�a�Ϧ�gold�A�i��grab�ʧ@
		if( now_room.sensor_glitter ){
			response.add(0, Action.grab);
			return;
		}
		
		if( now_room.sensor_scream ){
			wumpus_died = true;
		}
		
		System.out.println("location: " + pos[0] + " " + pos[1]);		
		//shoot wumpus
		//�Y�w��wumpus��m�A�B�{�b���i�H�g��wumpus���a��A���Ӥ�V��shoot
		if( wumpus_known && (wumpus_died==false) ){
			//�P�@��y�b�W
			if( pos[0]==wumpus_x ){
				SetTarget(0);
			}
			//�P�@��x�b�W
			else if( pos[1]==wumpus_y ){
				SetTarget(1);
			}
		}

		
		if( HasBeen(pos[0], pos[1])==true ){
			return;
		}
		
		
		MapNode node, temp;
		int[][] pos = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};
		int[] from = {2, 3, 0, 1};
		int j;
		//�ثe�Ҧbnode���|�P�]�n�إߦa�ϡA�H�ƼаO
		for(i=0; i<4; i++){
			if( current.nodes[i]==null ){
				node = new MapNode(mymap.size(), current.x+pos[i][0], current.y+pos[i][1]);
				
				for(j=0; j<4; j++){
					temp = FindNode(node.x+pos[j][0], node.y+pos[j][1]);
					if( temp!=null ){
						temp.nodes[from[j]] = node;
						node.nodes[j] = temp;
					}
				}
				mymap.add(node);
				current.nodes[i] = node;
				System.out.println( node.id+" "+node.x +" "+ node.y);
			}
		}
			
		//�ѶǶi�Ӫ���ơA�o��ثe�Ҧb�a�Ϫ�sensor���p
		current.sensor_breeze = now_room.sensor_breeze;
		current.sensor_strench = now_room.sensor_strench;
		current.agent_been = true;
		current.wall = 0;
		
		System.out.println("node["+pos[0]+"]["+pos[1]+"]:"+current.agent_been);		
		
		
		
		UpdateMap();
		
		if( now_room.sensor_strench ){
			
			wumpus_known = SearchWumpus();
		}
		
		
	}
	
	private void SetTarget(int xory)
	{
		int w_face;	//���~�b�H�����@��
		int time, i;
		
		if( xory==0 ){ //y�b
			if( pos[1]>wumpus_y )	//wumpus�b�U��
				w_face = Direction.down;
			else
				w_face = Direction.up;
		}
		else{
			if( pos[0]>wumpus_x )	//wumpus�b����
				w_face = Direction.left;
			else
				w_face = Direction.right;
		}
			
		time = face-w_face;
		if( time>0 ){
			for(i=0; i<time; i++)
				response.add(0, Action.turn_left);	
		}
		else if( time<0 ){
			for(i=0; i<(0-time); i++)
				response.add(0, Action.turn_right);
		}
		
		response.add(1, Action.shoot);
		
	}
	
	private void UpdateMap(){
		int i;
		int sum = 0;
		MapNode from_node;
		
		
		/*�L��*/
		if( current.sensor_breeze ){
			//�֭p�P��w�T�w�S���M�I�����
			for(i=0; i<4; i++){
				if( current.nodes[i].agent_been == true ){
					sum++;
				}
			}
			
			//�良������l�����M�I��
			for(i=0; i<4; i++){
				if( current.nodes[i].agent_been == false ){
					current.nodes[i].pit_maybe += 6 + ( sum*6 / (4-sum) );
					current.nodes[i].b_from = current;
				}
			}	
			
		}
		
		//�o�榳�M�I�A�����L��o�{�S���A��M�I�Ȥ����t�~�X��
		if( current.pit_maybe>0 ){
			from_node = current.b_from;
			
			sum = 0;
			for(i=0; i<4; i++){
				if( from_node.nodes[i].agent_been == true ){
					sum++;
				}
			}
			
			for(i=0; i<4; i++){
				if( from_node.nodes[i].agent_been == false ){
					from_node.nodes[i].pit_maybe += current.pit_maybe/sum; 
				}
			}
		}
		

		/*���*/
		if( current.sensor_strench ){
			//�֭p�P��w�T�w�S���M�I�����
			sum = 0;
			for(i=0; i<4; i++){
				if( current.nodes[i].agent_been == true ){
					sum++;
				}
			}
			
			//�良������l�����M�I��
			for(i=0; i<4; i++){
				if( current.nodes[i].agent_been == false ){
					current.nodes[i].wumpus_maybe += 6 + ( sum*6 / (4-sum) );
					current.nodes[i].s_from = current;
				}
			}	
			
		}
		
		//�o�榳�M�I�A�����L��o�{�S���A��M�I�Ȥ����t�~�X��
		if( current.wumpus_maybe>0 ){
			from_node = current.s_from;
			
			sum = 0;
			for(i=0; i<4; i++){
				if( from_node.nodes[i].agent_been == true ){
					sum++;
				}
			}
			
			for(i=0; i<4; i++){
				if( from_node.nodes[i].agent_been == false ){
					from_node.nodes[i].wumpus_maybe += current.wumpus_maybe/sum; 
				}
			}
		}

		
	}
	
	
	//�O�_���Dwumpus����m
	private boolean SearchWumpus()
	{
		MapNode node1, node2, node3;
		int i, j, k;
		
		//if sensor get strench then call this function to search the wumpus
		//get x,y  from agent?  from brain?
		//check if already have two position t can know where is the wumpus
		if(check_search_wumpus==0){
			//the first time call this function
			wumpus_potential[0] = current;
		}
		else if(check_search_wumpus==1){
			wumpus_potential[1] = current;

			/*
					  x,y-1
				x-1,y		x+1,y
					  x,y+1
			   1.x equals then y not equal
			   2.|x1-x2|=1 |y1-y2|=1
			   3.y equals then x not equal 
			*/
			node1 = wumpus_potential[0];
			node2 = wumpus_potential[1];
			
			//��槨�ۡA�T�wwumpus��m
			if( node1.x==node2.x ){
				wumpus_x = node1.x;
				wumpus_y = (node1.y+node2.y)/2;
				return true;
			}
			else if(node1.y==node2.y){
				wumpus_y=node1.y;
				wumpus_x=(node1.x+node2.x)/2;
				return true;
			}
			//���﨤�u
			else{
				MapNode a, b;
				MapNode same1 = null;
				MapNode same2 = null;
				int find = 0;
				
				for( i=0; i<4; i++){
					a = node1.nodes[i];
					for( j=0; j<4; j++){
						b = node2.nodes[j];
						if( a==b ){
							if( find==0 ){
								same1 = a;
								find++;
							}
							else
								same2 = a;
						}
					}
				}
				
				if( same1.agent_been == true ){
					wumpus_x = same2.x;
					wumpus_y = same2.y;
					return true;
				}
				
				if( same2.agent_been == true ){
					wumpus_x = same1.x;
					wumpus_y = same1.y;
					return true;
				}
			}
			
		}
		else if(check_search_wumpus==2){
			wumpus_potential[2] = current;
			//node1 = wumpus_potential[0];
			//node2 = wumpus_potential[1];
			//node3 = wumpus_potential[2];
			for(i=0; i<4; i++){
				node1 = wumpus_potential[0].nodes[i];
				for(j=0; j<4; j++){
					node2 = wumpus_potential[1].nodes[j];
					if( node1 == node2 ){
						for(k=0; k<4; k++){
							node3 = wumpus_potential[2].nodes[k];
							if( node3 == node2 ){
								wumpus_x = node3.x;
								wumpus_y = node3.y;
								return true;
							}
								
						}
					
					}
				}
			}
		}
		
		check_search_wumpus++;
		return false;
	}
	
	private void ComputeRoute(MapNode node1, MapNode node2, int face)
	{
		int[] way;
		boolean[] record = new boolean[mymap.size()];
		record[node1.id] = true;
		int i, j;
		
		way = Route(node1, node2, face, record);
		System.out.print("Route["+way.length+"] = [");
		for(i=0; i<way.length; i++){
			System.out.print(way[i]+", ");
		}
		System.out.print("]\n");
		
		int temp_face = face;
		for(i=way.length-1; i>=0; i--){
			if( temp_face!=way[i] ){ //�վ��V
				j = way[i]-temp_face;
				
				if( j==1 || j==-3 ){ //�k��@�� 
					response.add(Action.turn_right);
				}
				else if( j==2 || j==-2 ){ //�k��⦸
					response.add(Action.turn_right);
					response.add(Action.turn_right);
				}
				else if( j==-1 || j==3 ){ //����@�� 
					response.add(Action.turn_left);
				}		
			}
			response.add(Action.move_forward);
			temp_face = way[i];
		}
		
		int t;
		System.out.print("response["+response.size()+"] = [");
		for(i=0; i<response.size(); i++){
			t = response.get(i);
			System.out.print(t+", ");
		}
		System.out.print("]\n");
		
	}
	
	private int[] Route(MapNode node1, MapNode node2, int temp_face, boolean[] record)
	{
		int[] way;	
		int i;
		int min = mymap.size()+1, min_i = 0;
		int[][] res_way = new int[4][];
		int step;
		int[] from = {2, 3, 0, 1};
		
		
		//node1 �Mnode2�۳s
		for(i=0; i<4; i++){
			if( node1.nodes[i]==node2 ){
				way = new int[1];
				way[0] = i;
				System.out.println("way[0] = " + i);
				return way;
			}
		}
		
		for(i=0; i<4; i++){
			if(temp_face>-1 && i==from[temp_face]){ //�Ӫ���V
				continue;
			}
			if( node1.nodes[i]==null ){
				continue;
		}
			if( node1.nodes[i].wall==1 ){
				continue;
			}
			if( record[node1.nodes[i].id] ){
				continue;
		}
			record[node1.nodes[i].id] = true;
			res_way[i] = Route(node1.nodes[i], node2, i, record);
			record[node1.nodes[i].id] = false;
			
			step = res_way[i].length;
			System.out.println("	" + i + ": e " + step);
			if( step<min ){
				min = step;
				min_i = i;
			}
		}
		
		
		way = new int[min+1];
		if( min!=(mymap.size()+1)){
			
			
			for(i=0; i<min; i++)
				way[i] = res_way[min_i][i];
				//way = res_way[min_i].clone();
			way[min] = min_i;
			
/*			System.out.print("Route["+way.length+"] = [");
			for(i=0; i<way.length; i++){
				System.out.print(way[i]+", ");
			}
			System.out.print("]\n");
*/		}
		return way;
	}
	

	public int Ask()
	{
		int res;
		int i, rec = 0, rec2 = 0;
		int min;
		MapNode temp, node_togo;
		boolean b; 
		
		System.out.println(response.toString());
		
		if( !response.isEmpty() ){
			res = response.remove(0);
			if( res==Action.move_forward ){
				pre = current;
				current = pre.nodes[face];	
				//System.out.println("Forward: " + face + " node["+current.x+"]["+current.y+"]");
			}
			else if( res==Action.turn_left ){
				face = (face-1)%4;
				
			}
			else if( res==Action.turn_right ){
				face = (face+1)%4;
			}
			
			return res;
		}
		
		
		else{
			
			//���q�|�P�S���L��
/*			min = 10000;
			node_togo = null;
			for(i=0; i<4; i++){
				temp = current.nodes[i];
				//System.out.println("current.nodes["+i+"] = " + temp.x +" " + temp.y + " wall=" + temp.wall);
				if( temp.wall!=1 && temp.agent_been==false && ((temp.pit_maybe+temp.wumpus_maybe)<20) ){
					if(  (temp.pit_maybe+temp.wumpus_maybe)<min ){
						min = temp.pit_maybe+temp.wumpus_maybe;
						node_togo = temp;
					}
				} 
			}
			if( min<10000 && min<6 ){
				Route(current, node_togo, face, 0);
				System.out.println("Forward to: node["+node_togo.x+"]["+node_togo.y+"]" + node_togo.agent_been);
				System.out.println("2nd " + response.toString());
			}
			else{
*/			
				//��Ҧ�map���M�I�׳̤p��
				min = 10000;
				node_togo = null;
				for(i=0; i<mymap.size(); i++){
					temp = mymap.get(i);
					if( temp!=current ){
						if( temp.wall!=1 && !temp.agent_been  ){
							if(  (temp.pit_maybe+temp.wumpus_maybe) <= min ){
								min = temp.pit_maybe+temp.wumpus_maybe;
								node_togo = temp;
								rec = i;
							}
						} 
					}
				}
				if( min<10000){
					ComputeRoute(current, node_togo, face);
					System.out.println("3rd " + response.toString());
				}
			
				
	/*		while( response.isEmpty() )
			{
				rec2 = rec;
				min = 10000;
				node_togo = null;
				for(i=0; i<mymap.size(); i++){
					temp = mymap.get(i);
					if( temp!=current ){
						if( temp.wall!=1 && !temp.agent_been  ){
							if(  (temp.pit_maybe+temp.wumpus_maybe) <= min && i!=rec2  ){
								min = temp.pit_maybe+temp.wumpus_maybe;
								node_togo = temp;
								rec = i;
							}
						} 
					}
				}
				if( min<10000){
					Route(current, node_togo, face, 0);
					System.out.println("3rd " + response.toString());
				}
			
				
			}*/
//			}		
			
		}
		
		if( !response.isEmpty() ){
			res = response.remove(0);
			if( res==Action.move_forward ){
				pre = current;
				current = pre.nodes[face];		
			}
			else if( res==Action.turn_left ){
				face = (face-1);
				if(face<0) face = 3;
			}
			else if( res==Action.turn_right ){
				face = (face+1)%4;
			}
			
			return res;
		}
		
		return Action.gameover;
	}
	
	public void Show()
	{
		
		System.out.println("show node["+current.x+"]["+current.y+"]" + current.agent_been + current.toString());
	}
	
}
