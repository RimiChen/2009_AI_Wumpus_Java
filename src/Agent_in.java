import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class Agent_in {
	
	public static void in(){
		int[] bound={10,110,210,310,410,510};
		int line=100;
		//宣告
		Picsource pic=new Picsource();
		pic=Main_GUI.pic;
		
		int agent_x=Main_GUI.agent_x;
		int agent_y=Main_GUI.agent_y;
		int agent_w=Main_GUI.agent_w;
		int agent_h=Main_GUI.agent_h;
		int agent_up=Main_GUI.agent_up;
		int agent_left=Main_GUI.agent_left;
		int agent_down=Main_GUI.agent_down;
		int agent_right=Main_GUI.agent_right;
		
		pic.game_control.worldmap.MakeMap();
		final JFrame game = new JFrame("AI_Game");
		final JLabel game0 = new JLabel(pic.getimage(1));



		final JLabel agent_field = new JLabel("");

		int i,j,k,l;  //for use

		JLabel[][][] each_room = new JLabel[4][4][4];
		for(i=0; i<4; i++){
			for(j=0;j<4;j++){
				for(k=0;k<4;k++){
					each_room[i][j][k]=new JLabel("");
				}
			}
		}
		//row_clown
		
		JLabel game_1_1 = new JLabel(pic.getimage(0,0,3));
		JLabel game_1_2 = new JLabel(pic.getimage(0,1,3));
		JLabel game_1_3 = new JLabel(pic.getimage(0,2,3));
		JLabel game_1_4 = new JLabel(pic.getimage(0,3,3));
		JLabel game_1_5 = new JLabel(pic.getimage(3));
		JLabel game_1_6 = new JLabel(pic.getimage(3));

		
		JLabel game_2_1 = new JLabel(pic.getimage(1,0,3));
		JLabel game_2_2 = new JLabel(pic.getimage(1,1,3));
		JLabel game_2_3 = new JLabel(pic.getimage(1,2,3));
		JLabel game_2_4 = new JLabel(pic.getimage(1,3,3));
		JLabel game_2_5 = new JLabel(pic.getimage(3));
		JLabel game_2_6 = new JLabel(pic.getimage(3));

		
		JLabel game_3_1 = new JLabel(pic.getimage(2,0,3));
		JLabel game_3_2 = new JLabel(pic.getimage(2,1,3));
		JLabel game_3_3 = new JLabel(pic.getimage(2,2,3));
		JLabel game_3_4 = new JLabel(pic.getimage(2,3,3));
		JLabel game_3_5 = new JLabel(pic.getimage(3));
		JLabel game_3_6 = new JLabel(pic.getimage(3));

		
		JLabel game_4_1 = new JLabel(pic.getimage(3,0,3));
		JLabel game_4_2 = new JLabel(pic.getimage(3,1,3));
		JLabel game_4_3 = new JLabel(pic.getimage(3,2,3));
		JLabel game_4_4 = new JLabel(pic.getimage(3,3,3));
		JLabel game_4_5 = new JLabel(pic.getimage(3));
		JLabel game_4_6 = new JLabel(pic.getimage(3));

		
		JLabel game_5_1 = new JLabel(pic.getimage(3,0,3));
		JLabel game_5_2 = new JLabel(pic.getimage(3,1,3));
		JLabel game_5_3 = new JLabel(pic.getimage(3,2,3));
		JLabel game_5_4 = new JLabel(pic.getimage(3,3,3));
		JLabel game_5_5 = new JLabel(pic.getimage(3));
		JLabel game_5_6 = new JLabel(pic.getimage(3));

		
		JLabel game_6_1 = new JLabel(pic.getimage(3,0,3));
		JLabel game_6_2 = new JLabel(pic.getimage(3,1,3));
		JLabel game_6_3 = new JLabel(pic.getimage(3,2,3));
		JLabel game_6_4 = new JLabel(pic.getimage(3,3,3));
		JLabel game_6_5 = new JLabel(pic.getimage(3));
		JLabel game_6_6 = new JLabel(pic.getimage(3));
		
		
		final JLabel walk_agent =new JLabel(pic.getimage(10));
		
		//設定
		game.setLayout(null);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(740,760);
		//game0.setBackground(Color.RED);
		//game0.setOpaque(false);
		

		game0.setBounds(0, 0, 620, 620);

		game_6_1.setBounds(bound[0], bound[5], line, line);
		game_6_2.setBounds(bound[1], bound[5], line, line);
		game_6_3.setBounds(bound[2], bound[5], line, line);
		game_6_4.setBounds(bound[3], bound[5], line, line);
		game_6_5.setBounds(bound[4], bound[5], line, line);
		game_6_6.setBounds(bound[5], bound[5], line, line);

		game_5_1.setBounds(bound[0], bound[4], line, line);
		game_5_2.setBounds(bound[1], bound[4], line, line);
		game_5_3.setBounds(bound[2], bound[4], line, line);
		game_5_4.setBounds(bound[3], bound[4], line, line);
		game_5_5.setBounds(bound[4], bound[4], line, line);
		game_5_6.setBounds(bound[5], bound[4], line, line);

		game_4_1.setBounds(bound[0], bound[3], line, line);
		game_4_2.setBounds(bound[1], bound[3], line, line);
		game_4_3.setBounds(bound[2], bound[3], line, line);
		game_4_4.setBounds(bound[3], bound[3], line, line);
		game_4_5.setBounds(bound[4], bound[3], line, line);
		game_4_6.setBounds(bound[5], bound[3], line, line);
		
		game_3_1.setBounds(bound[0], bound[2], line, line);
		game_3_2.setBounds(bound[1], bound[2], line, line);
		game_3_3.setBounds(bound[2], bound[2], line, line);
		game_3_4.setBounds(bound[3], bound[2], line, line);
		game_3_5.setBounds(bound[4], bound[2], line, line);
		game_3_6.setBounds(bound[5], bound[2], line, line);

		game_2_1.setBounds(bound[0], bound[1], line, line);
		game_2_2.setBounds(bound[1], bound[1], line, line);
		game_2_3.setBounds(bound[2], bound[1], line, line);
		game_2_4.setBounds(bound[3], bound[1], line, line);
		game_2_5.setBounds(bound[4], bound[1], line, line);
		game_2_6.setBounds(bound[5], bound[1], line, line);

		game_1_1.setBounds(bound[0], bound[0], line, line);
		game_1_2.setBounds(bound[1], bound[0], line, line);
		game_1_3.setBounds(bound[2], bound[0], line, line);
		game_1_4.setBounds(bound[3], bound[0], line, line);
		game_1_5.setBounds(bound[4], bound[0], line, line);
		game_1_6.setBounds(bound[5], bound[0], line, line);



		
		
		for(i=0;i<4;i++){
			for(j=0;j<4;j++){
				each_room[i][j][0].setBounds(0, 0, 70, 70);
					if(pic.record[i][j][1]==1){
						each_room[i][j][0].setIcon(pic.getimage(15));
					}
				each_room[i][j][1].setBounds(70, 0, 70, 70);
					if(pic.record[i][j][2]==1){
						each_room[i][j][1].setIcon(pic.getimage(16));
					}
				each_room[i][j][2].setBounds(0, 70, 70, 70);
					if(pic.record[i][j][0]==1){
						each_room[i][j][2].setIcon(pic.getimage(14));
					}
				each_room[i][j][3].setBounds(70,70, 70, 70);
					if(pic.record[i][j][3]==1){
						each_room[i][j][3].setIcon(pic.getimage(17));
					}
					if(pic.record[i][j][4]==1){
						each_room[i][j][3].setIcon(pic.getimage(18));
					}
			}
		}
		
		//-----------------------------

		//-----------------------------
		
		agent_field.setBounds(20, 20, 560, 560);
		walk_agent.setBounds(agent_x, agent_y, agent_w,agent_h);   //x,y=0,140,280,420
		//UP.setBounds(275, y, 50, 15);
		//面板配置
		agent_field.add(walk_agent);

		

		
		game0.add(agent_field);
		for(i=0;i<4;i++){
			game_1_1.add(each_room[0][0][i]);
			game_1_2.add(each_room[0][1][i]);
			game_1_3.add(each_room[0][2][i]);
			game_1_4.add(each_room[0][3][i]);
			game_2_1.add(each_room[1][0][i]);
			game_2_2.add(each_room[1][1][i]);
			game_2_3.add(each_room[1][2][i]);
			game_2_4.add(each_room[1][3][i]);
			game_3_1.add(each_room[2][0][i]);
			game_3_2.add(each_room[2][1][i]);
			game_3_3.add(each_room[2][2][i]);
			game_3_4.add(each_room[2][3][i]);
			game_4_1.add(each_room[3][0][i]);
			game_4_2.add(each_room[3][1][i]);
			game_4_3.add(each_room[3][2][i]);
			game_4_4.add(each_room[3][3][i]);

		}
		game0.add(game_1_1);
		game0.add(game_1_2);
		game0.add(game_1_3);
		game0.add(game_1_4);
		game0.add(game_1_5);

		
		game0.add(game_2_1);
		game0.add(game_2_2);
		game0.add(game_2_3);
		game0.add(game_2_4);
		game0.add(game_2_5);

		
		game0.add(game_3_1);
		game0.add(game_3_2);
		game0.add(game_3_3);
		game0.add(game_3_4);
		game0.add(game_3_5);

		
		game0.add(game_4_1);
		game0.add(game_4_2);
		game0.add(game_4_3);
		game0.add(game_4_4);
		game0.add(game_4_5);

		
		game0.add(game_5_1);
		game0.add(game_5_2);
		game0.add(game_5_3);
		game0.add(game_5_4);
		game0.add(game_5_5);
		

		game.add(game0);

		
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		
		//---------------------------------------------------------
		//button action
		//---------------------------------------------------------
		//main program
	}
}
