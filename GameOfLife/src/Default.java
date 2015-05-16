
import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.Color;

public class Default extends Agent {

	public Default(Simulator sim, Grid g) {
		super(sim, g, false, 1, Color.GRAY, g.maxX, g.maxY);
	}

	@Override
	void update() {
		Hashtable<Integer,Integer> voteList = new Hashtable<Integer,Integer>();
		Hashtable<Integer,Color> colorList = new Hashtable<Integer,Color>();
		for(int i=0; i<this.buffSize(); i++){
			int votes = 0;
			boolean left=false;
			boolean right = false;
			
			ArrayList<Color> colors=new ArrayList<Color>();
			
			if((i%this.buffX)!=0){
				left=true;
				votes+=this.buffer.get(i-1).getOnOff() ? 1 : 0;
				colors.add(this.buffer.get(i-1).getColor().getInitRGB());
			}
			if((i%(this.buffX))-(buffX-1)!=0||i==0){
				right=true;
				votes+=this.buffer.get(i+1).getOnOff() ? 1 : 0;
				colors.add(this.buffer.get(i+1).getColor().getInitRGB());
			}
			if(i>=this.buffX){
				votes+=this.buffer.get(i-this.buffX).getOnOff() ? 1 : 0;
				colors.add(this.buffer.get(i-this.buffX).getColor().getInitRGB());
				
				if(left){
					votes+=this.buffer.get(i-1-this.buffX).getOnOff() ? 1 : 0;
					colors.add(this.buffer.get(i-1-this.buffX).getColor().getInitRGB());
				}
				if(right){
					votes+=this.buffer.get(i+1-this.buffX).getOnOff() ? 1 : 0;
					colors.add(this.buffer.get(i+1-this.buffX).getColor().getInitRGB());
				}
			}
			if(i<(this.buffSize()-this.buffX)){
				votes+=this.buffer.get(i+this.buffX).getOnOff() ? 1 : 0;
				colors.add(this.buffer.get(i+this.buffX).getColor().getInitRGB());
				
				if(left){
					votes+=this.buffer.get(i-1+this.buffX).getOnOff() ? 1 : 0;
					colors.add(this.buffer.get(i-1+this.buffX).getColor().getInitRGB());
				}
				if(right){
					votes+=this.buffer.get(i+1+this.buffX).getOnOff() ? 1 : 0;
					colors.add(this.buffer.get(i+1+this.buffX).getColor().getInitRGB());
				}
			}
			
			int red=0;
			int green=0;
			int blue=0;
			for(int j =0; j<colors.size();j++){
				if(colors.get(j).equals(Color.BLUE)){
					blue++;
				}else if(colors.get(j).equals(Color.GREEN)){
					green++;
				}else if(colors.get(j).equals(Color.RED)){
					red++;
				}
			}
			
			colorList.put(i, Color.GRAY);
			if(red>green){
				if(red>blue){
					colorList.put(i, Color.RED);
				}else if(blue>red){
					colorList.put(i, Color.BLUE);
				}
			}else if(green>red){
				if(green>blue){
					colorList.put(i, Color.GREEN);
				}else if(blue>green){
					colorList.put(i, Color.BLUE);
				}
			}
			
			voteList.put(i, votes);
		}
		

		for(int i=0; i<this.buffSize(); i++){
			int votes = voteList.get(i);
			if(this.buffer.get(i).getOnOff()){
				if(votes>3||votes<2){
					this.buffer.get(i).flip();
				}
			}else{
				if(votes==3){
					this.buffer.get(i).flip();
				}
			}

			this.buffer.get(i).decayTile();
			this.buffer.get(i).changeColor(colorList.get(i));//, this.buffer.get(i).getDecay());
		}
	}
	
	@Override
	synchronized void setROI(int x, int y){
		this.leftX=0;
		this.topY=0;
	}
	

}
