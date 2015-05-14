
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
		for(int i=0; i<this.buffSize(); i++){
			int votes = 0;
			boolean left=false;
			boolean right = false;
			if((i%this.buffX)!=0){
				left=true;
				votes+=this.buffer.get(i-1).getOnOff() ? 1 : 0;
			}
			if((i%(this.buffX))-(buffX-1)!=0||i==0){
				right=true;
				votes+=this.buffer.get(i+1).getOnOff() ? 1 : 0;
			}
			if(i>=this.buffX){
				votes+=this.buffer.get(i-this.buffX).getOnOff() ? 1 : 0;
				
				if(left)
					votes+=this.buffer.get(i-1-this.buffX).getOnOff() ? 1 : 0;
				if(right)
					votes+=this.buffer.get(i+1-this.buffX).getOnOff() ? 1 : 0;
			}
			if(i<(this.buffSize()-this.buffX)){
				votes+=this.buffer.get(i+this.buffX).getOnOff() ? 1 : 0;
				
				if(left)
					votes+=this.buffer.get(i-1+this.buffX).getOnOff() ? 1 : 0;
				if(right)
					votes+=this.buffer.get(i+1+this.buffX).getOnOff() ? 1 : 0;
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
		}
	}
	
	@Override
	synchronized void setROI(int x, int y){
		this.leftX=0;
		this.topY=0;
	}
	

}
