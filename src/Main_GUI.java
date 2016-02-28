import java.awt.*;
import java.net.*;
import java.util.*;
import java.io.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Main_GUI  implements Runnable{
	static int agent_x=0;
	static int agent_y=420;
	static int agent_w=140;
	static int agent_h=140;
	static int agent_up=0;
	static int agent_left=0;
	static int agent_down=0;
	static int agent_right=1;
	static int agent_arrow=1;
	static int step_count=0;
	static String step_trace=("step"+step_count+" : start");
	static int arrows;
	static Picsource pic = new Picsource();
	static Brain brain = new Brain();
	static int gogo = 0;
	static int i,j,k,l;  //for use
	
	public static void main(String[] args){
		//宣告
		pic.game_control.worldmap.MakeMap();
		final JFrame game = new JFrame("AI_Game");
		final JLabel game0 = new JLabel(pic.getimage(1));
		final JLabel game1 = new JLabel("");
		final JLabel game_panel = new JLabel(pic.getimage(2));
		final JLabel agent_field = new JLabel("");
		final JTextArea step = new JTextArea(step_trace);
		final JScrollPane bar=new JScrollPane(step);
		
		final JLabel win = new JLabel(pic.getimage(20));
		final JLabel lose = new JLabel(pic.getimage(19));


		final JLabel[][][] each_room = new JLabel[4][4][4];
		for(i=0; i<4; i++){
			for(j=0;j<4;j++){
				for(k=0;k<4;k++){
					each_room[i][j][k]=new JLabel("");
				}
			}
		}
		//row_clown
		
		final JLabel game_1_1 = new JLabel(pic.getimage(0,0,3));
		final JLabel game_1_2 = new JLabel(pic.getimage(0,1,3));
		final JLabel game_1_3 = new JLabel(pic.getimage(0,2,3));
		final JLabel game_1_4 = new JLabel(pic.getimage(0,3,3));
		final JLabel game_2_1 = new JLabel(pic.getimage(1,0,3));
		final JLabel game_2_2 = new JLabel(pic.getimage(1,1,3));
		final JLabel game_2_3 = new JLabel(pic.getimage(1,2,3));
		final JLabel game_2_4 = new JLabel(pic.getimage(1,3,3));
		final JLabel game_3_1 = new JLabel(pic.getimage(2,0,3));
		final JLabel game_3_2 = new JLabel(pic.getimage(2,1,3));
		final JLabel game_3_3 = new JLabel(pic.getimage(2,2,3));
		final JLabel game_3_4 = new JLabel(pic.getimage(2,3,3));
		final JLabel game_4_1 = new JLabel(pic.getimage(3,0,3));
		final JLabel game_4_2 = new JLabel(pic.getimage(3,1,3));
		final JLabel game_4_3 = new JLabel(pic.getimage(3,2,3));
		final JLabel game_4_4 = new JLabel(pic.getimage(3,3,3));
		
		final JLabel score =new JLabel(""+pic.game_control.score);
		if (pic.game_control.arrow_allowed==true){
			arrows=1;
		}
		else{
			arrows=0;
		}
		final JLabel arrow =new JLabel(""+arrows);
		final JLabel step_number =new JLabel(""+step_count);
		
		JButton INITIAL = new JButton("INITIAL");
		JButton NEXT = new JButton("NEXT");
		
		final JLabel walk_agent =new JLabel(pic.getimage(10));
		
		//設定
		game.setLayout(null);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setSize(815,738);
		//game0.setBackground(Color.RED);
		//game0.setOpaque(false);
		win.setBounds(140, 140, 280, 280);
		lose.setBounds(140, 140, 280, 280);
		score.setBounds(100, 25, 50, 30);
		arrow.setBounds(100, 145, 50, 30);
		step_number.setBounds(100, 85, 50, 30);

		game0.setBounds(0, 0, 800, 700);
		game1.setBounds(0, 600, 600, 100);
		game_panel.setBounds(600, 0, 200, 700);
		game_4_1.setBounds(20, 20, 140, 140);
		game_4_2.setBounds(160, 20, 140, 140);
		game_4_3.setBounds(300, 20, 140, 140);
		game_4_4.setBounds(440, 20, 140, 140);
		game_3_1.setBounds(20, 160, 140, 140);
		game_3_2.setBounds(160, 160, 140, 140);
		game_3_3.setBounds(300, 160, 140, 140);
		game_3_4.setBounds(440, 160, 140, 140);
		game_2_1.setBounds(20, 300, 140, 140);
		game_2_2.setBounds(160, 300, 140, 140);
		game_2_3.setBounds(300, 300, 140, 140);
		game_2_4.setBounds(440, 300, 140, 140);
		game_1_1.setBounds(20, 440, 140, 140);
		game_1_2.setBounds(160, 440, 140, 140);
		game_1_3.setBounds(300, 440, 140, 140);
		game_1_4.setBounds(440, 440, 140, 140);
	
		bar.setBounds(25, 250, 150, 300);
		//step.setOpaque(false);
		
		step.setBounds(100, 250, 100, 300);
		//step.setOpaque(false);
		
		step.setForeground(Color.BLACK);
		//step.setBackground(Color.getHSBColor(240, 160, 128));
		step.setBackground(Color.getHSBColor(255, 255, 125));
		
		
		
		for(j=0;j<4;j++){
			for(i=0;i<4;i++){
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
		NEXT.setBounds(50, 55, 100, 20);
		INITIAL.setBounds(450, 55, 100, 20);
		
		//-----------------------------

		//-----------------------------
		
		agent_field.setBounds(20, 20, 560, 560);
		walk_agent.setBounds(agent_x, agent_y, agent_w,agent_h);   //x,y=0,140,280,420
		//UP.setBounds(275, y, 50, 15);
		//面板配置
		agent_field.add(walk_agent);

		game1.add(NEXT);
		game1.add(INITIAL);
		
		
		game0.add(agent_field);
		for(i=0;i<4;i++){
			game_1_1.add(each_room[0][0][i]);
			game_1_2.add(each_room[1][0][i]);
			game_1_3.add(each_room[2][0][i]);
			game_1_4.add(each_room[3][0][i]);
			game_2_1.add(each_room[0][1][i]);
			game_2_2.add(each_room[1][1][i]);
			game_2_3.add(each_room[2][1][i]);
			game_2_4.add(each_room[3][1][i]);
			game_3_1.add(each_room[0][2][i]);
			game_3_2.add(each_room[1][2][i]);
			game_3_3.add(each_room[2][2][i]);
			game_3_4.add(each_room[3][2][i]);
			game_4_1.add(each_room[0][3][i]);
			game_4_2.add(each_room[1][3][i]);
			game_4_3.add(each_room[2][3][i]);
			game_4_4.add(each_room[3][3][i]);

		}
		game0.add(game_1_1);
		game0.add(game_1_2);
		game0.add(game_1_3);
		game0.add(game_1_4);
		game0.add(game_2_1);
		game0.add(game_2_2);
		game0.add(game_2_3);
		game0.add(game_2_4);
		game0.add(game_3_1);
		game0.add(game_3_2);
		game0.add(game_3_3);
		game0.add(game_3_4);
		game0.add(game_4_1);
		game0.add(game_4_2);
		game0.add(game_4_3);
		game0.add(game_4_4);
		game_panel.add(score);
		game_panel.add(arrow);
		game_panel.add(step_number);
		game_panel.add(bar);
		
		game0.add(game1);
		game.add(game_panel);
		game.add(game0);

		
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		
		//---------------------------------------------------------
		//button action
		NEXT.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Room now_room;
						int res=0;
						boolean notwall;
						
						if( !pic.game_control.gameover ){
						res = brain.Ask();

						
						switch( res ){
						case 0:
							pic.game_control.TurnLeft();
							step_trace=step_trace+("\nstep"+step_count+" : turn left");
							break;
						case 1:
							pic.game_control.TurnRight();
							step_trace=step_trace+("\nstep"+step_count+" : turn right");
							break;
						case 2:	
							notwall = pic.game_control.MoveForward();				
							step_trace=step_trace+("\nstep"+step_count+" : move forward");
							if( notwall == false ){ //是牆
								System.out.println("wall!" + pic.game_control.agent_x + " " + pic.game_control.agent_y);
								step_trace=step_trace+("\nwall!!!");
								brain.ThisIsWall();
							}
							else{	//不是牆
								now_room = pic.game_control.GetRoom();
								brain.Tell(now_room);				
								brain.Show();
							}
							break;
						case 3:
							pic.game_control.Shoot();
							step_trace=step_trace+("\nstep"+step_count+" : shoot");
							for(i=0;i<4;i++){
								game_1_1.remove(each_room[0][0][i]);
								game_1_2.remove(each_room[1][0][i]);
								game_1_3.remove(each_room[2][0][i]);
								game_1_4.remove(each_room[3][0][i]);
								game_2_1.remove(each_room[0][1][i]);
								game_2_2.remove(each_room[1][1][i]);
								game_2_3.remove(each_room[2][1][i]);
								game_2_4.remove(each_room[3][1][i]);
								game_3_1.remove(each_room[0][2][i]);
								game_3_2.remove(each_room[1][2][i]);
								game_3_3.remove(each_room[2][2][i]);
								game_3_4.remove(each_room[3][2][i]);
								game_4_1.remove(each_room[0][3][i]);
								game_4_2.remove(each_room[1][3][i]);
								game_4_3.remove(each_room[2][3][i]);
								game_4_4.remove(each_room[3][3][i]);

							}
							for(j=0;j<4;j++){
								for(i=0;i<4;i++){
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
											each_room[i][j][3].setIcon(null);
								}
							}
							for(i=0;i<4;i++){
								game_1_1.add(each_room[0][0][i]);
								game_1_2.add(each_room[1][0][i]);
								game_1_3.add(each_room[2][0][i]);
								game_1_4.add(each_room[3][0][i]);
								game_2_1.add(each_room[0][1][i]);
								game_2_2.add(each_room[1][1][i]);
								game_2_3.add(each_room[2][1][i]);
								game_2_4.add(each_room[3][1][i]);
								game_3_1.add(each_room[0][2][i]);
								game_3_2.add(each_room[1][2][i]);
								game_3_3.add(each_room[2][2][i]);
								game_3_4.add(each_room[3][2][i]);
								game_4_1.add(each_room[0][3][i]);
								game_4_2.add(each_room[1][3][i]);
								game_4_3.add(each_room[2][3][i]);
								game_4_4.add(each_room[3][3][i]);

							}
							game0.repaint();
							break;
						case 4:	
							pic.game_control.Grab();
							step_trace=step_trace+("\nstep"+step_count+" : grab");
							agent_field.add(win);
							agent_field.repaint();
							game0.remove(game1);
							
							
							break;
						default:
							System.out.println("error: res = " + res);
						}
						
						}
						else{
							System.out.println("Game Over!!");
							step_trace=step_trace+("\nGame Over!!");
						}
						//step_trace=step_trace+("\nstep"+step_count+" : " + res);
						
						step_count++;
						step.setText(step_trace);
						
						agent_x = pic.game_control.agent_x*140;
						agent_y = 440-pic.game_control.agent_y*140;
						System.out.println("agent: " + agent_x + " " + agent_y);
						
						agent_field.remove(walk_agent);
						walk_agent.setIcon(pic.getimage(10+pic.game_control.agent_face));
						walk_agent.setBounds(agent_x, agent_y, agent_w,agent_h);
						agent_field.add(walk_agent);
						agent_field.repaint();
						System.out.println("paint ");
						if(pic.game_control.gameover==true){
							agent_field.add(lose);
							agent_field.repaint();
							game0.remove(game1);
						}
						
						score.setText(""+pic.game_control.score);
						if (pic.game_control.arrow_allowed==true){
							arrows=1;
						}
						else{
							arrows=0;
							
						}
						arrow.setText(""+arrows);
						step_number.setText(""+step_count);						
						
						game_panel.repaint();
						game0.repaint();

					}
				}
		);
		INITIAL.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Room now_room;
						int res;
						boolean notwall;
						now_room = pic.game_control.GetRoom();
						brain.Tell(now_room);
						
						
					}

				}
		);
		//---------------------------------------------------------
		//main program
/*		Room now_room;
		int res;
		boolean notwall;
		now_room = pic.game_control.GetRoom();
		brain.Tell(now_room);
		res = brain.Ask();
		
		
/*		while( pic.game_control.gameover!=true )
		{
			
			
			step_trace=step_trace+("\nstep"+step_count+" : " + res);
			step_count++;
			step.setText(step_trace);
			
			agent_x = pic.game_control.agent_x*140;
			agent_y = 440-pic.game_control.agent_y*140;
			System.out.println("agent: " + agent_x + " " + agent_y);
			
			agent_field.remove(walk_agent);
			walk_agent.setBounds(agent_x, agent_y, agent_w,agent_h);
			agent_field.add(walk_agent);
			agent_field.repaint();
			System.out.println("paint ");
			
			game_panel.repaint();
			
			switch( res ){
			case 0:
				pic.game_control.TurnLeft();
				break;
			case 1:
				pic.game_control.TurnRight();
				break;
			case 2:	
				notwall = pic.game_control.MoveForward();				
				if( notwall == false ){ //是牆
					System.out.println("wall!" + pic.game_control.agent_x + " " + pic.game_control.agent_y);
					brain.ThisIsWall();
				}
				else{	//不是牆
					now_room = pic.game_control.GetRoom();
					brain.Tell(now_room);				
					brain.Show();
				}
				break;
			case 3:
				pic.game_control.Shoot();
				break;
			case 4:	
				pic.game_control.Grab();
				break;
			default:
				System.out.println("error: res = " + res);
			}
			
			res = brain.Ask();
			
			
		}	
		
		System.out.println("Game Over!!");		
	*/	
	}
	
	public void run()
	{
		try{
		      Thread.sleep(30000);
		}catch(InterruptedException   ex){}
	
	}
	
	public void delay(int howLong)
	{
		Thread th = new Thread(this);
		th.start();	
	}

}
