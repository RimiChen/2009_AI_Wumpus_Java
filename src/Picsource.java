import javax.swing.*;
import java.awt.*;

public class Picsource {
	static int wumupus_x=-1;
	static int wumupus_y=-1;
	
	ImageIcon icon1=new ImageIcon("back.jpg");
	ImageIcon icon2=new ImageIcon("side.jpg");
	ImageIcon icon3=new ImageIcon("blank.jpg");
	ImageIcon icon4=new ImageIcon("pit.jpg");
	ImageIcon icon5=new ImageIcon("wind.jpg");
	ImageIcon icon6=new ImageIcon("stench.jpg");
	ImageIcon icon7=new ImageIcon("wumpus.jpg");
	ImageIcon icon8=new ImageIcon("wumpus2.jpg");
	ImageIcon icon9=new ImageIcon("golden.jpg");
	ImageIcon icon11=new ImageIcon("master.gif");
	ImageIcon icon13=new ImageIcon("master_b.gif");
	ImageIcon icon12=new ImageIcon("master_l.gif");
	ImageIcon icon10=new ImageIcon("master_r.gif");
	
	ImageIcon icon14=new ImageIcon("golden.gif");
	ImageIcon icon15=new ImageIcon("pit.gif");
	ImageIcon icon16=new ImageIcon("wind.gif");
	ImageIcon icon17=new ImageIcon("stench.gif");
	ImageIcon icon18=new ImageIcon("wumpus.gif");
	ImageIcon icon19=new ImageIcon("lose2.jpg");
	ImageIcon icon20=new ImageIcon("win2.jpg");
	static Game game_control = new Game();
	//gold pit breeze strench wumupus master
	static int[][][] record=new int[4][4][6];

	public int check_record(int pic_x,int pic_y){
		int count=0;
		if(game_control.worldmap.map[pic_x][pic_y].gold==true){
			record[pic_x][pic_y][0]=1;
			count++;
		}			
		else{
			record[pic_x][pic_y][0]=0;	
		}

		if(game_control.worldmap.map[pic_x][pic_y].pit==true){
			record[pic_x][pic_y][1]=1;
			count++;
		}
		else{
			record[pic_x][pic_y][1]=0;	
		}

		if(game_control.worldmap.map[pic_x][pic_y].sensor_breeze==true){
			record[pic_x][pic_y][2]=1;
			count++;
		}
		else{
			record[pic_x][pic_y][2]=0;	
		}

		if(game_control.worldmap.map[pic_x][pic_y].sensor_strench==true){
        	record[pic_x][pic_y][3]=1;
			if(game_control.wumpus_died==true){
				record[pic_x][pic_y][3]=0;
			}
			count++;
		}
		else{
				record[pic_x][pic_y][3]=0;
						count++;
		}

		if(game_control.worldmap.map[pic_x][pic_y].wumpus==true){


				record[pic_x][pic_y][4]=1;
				if(game_control.wumpus_died==true){
					record[pic_x][pic_y][4]=0;
				}
			
			count++;
		}
		else{
			record[pic_x][pic_y][4]=0;	
		}

		if(game_control.worldmap.map[pic_x][pic_y].agent_here==true){
			record[pic_x][pic_y][5]=1;	
		}
		else{
			record[pic_x][pic_y][5]=0;	
		}
		return count;
	}
	
	public ImageIcon getimage(int pic_x,int pic_y,int pic_number){
		ImageIcon temp = null;
		check_record(pic_x,pic_y);
		temp=chooseimage(pic_number);
		return temp;
	}
	public ImageIcon getimage(int pic_number) {
		ImageIcon temp = null;
		temp=chooseimage(pic_number);
		return temp;
	}

	public ImageIcon chooseimage(int pic_number){
		ImageIcon temp = null;
		//§PÂ_¿ï¾Ü¹Ï¤ù
		int count=0;
		if(pic_number==1){
			temp=icon1;
		}
		else if(pic_number==2){
			temp=icon2;
		}
		else if (pic_number==3){
			temp=icon3;
		}
		else if(pic_number==4){
			temp=icon4;
		}
		else if(pic_number==5){
			temp=icon5;
		}
		else if(pic_number==6){
			temp=icon6;
		}
		else if(pic_number==7){
			temp=icon7;
		}		
		else if(pic_number==8){
			temp=icon8;
		}
		else if(pic_number==9){
			temp=icon9;
		}
		else if(pic_number==10){
			temp=icon10;
		}
		else if(pic_number==11){
			temp=icon11;
		}
		else if(pic_number==12){
			temp=icon12;
		}
		else if(pic_number==13){
			temp=icon13;
		}
		else if(pic_number==14){
			temp=icon14;
		}
		else if(pic_number==15){
			temp=icon15;
		}
		else if(pic_number==16){
			temp=icon16;
		}
		else if(pic_number==17){
			temp=icon17;
		}
		else if(pic_number==18){
			temp=icon18;
		}
		else if(pic_number==19){
			temp=icon19;
		}
		else if(pic_number==20){
			temp=icon20;
		}
		return temp;
	
	}

}
