

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entity0 extends Entity {
	int size=NetworkSimulator.NUMENTITIES;
	int[][] table = new int[size][size];
	int[] cost = new int[] { 0, 1, 3, 7 };
	int[] directneighbour = new int[] { 1, 2, 3};
	int[] temp1 = cost;
	public Entity0() {
		DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
    	Date date=new Date();
		int i, j,z;
		int[] temp = new int[size];
		System.out.println("Entity0 is called: "
				+ dateFormat.format(date));
		for(i=0;i<size;i++)
    	{
    		for(j=0;j<size;j++)
    		{
    			if(i==j)
    			{
    				for(z=0;z<directneighbour.length;z++)
    				{
    					if(directneighbour[z]==i)
    					{
    						table[i][j]=cost[i];
    					}
    				}
    			}
    			else
    			{
    				table[i][j]=999;
    			}
    		}
    	}
		table[0][0]=0;

		for (i = 0; i < directneighbour.length; i++) {
			
			if (directneighbour[i]>0) {
				for (j = 0; j < size; j++) {
					temp[j] = temp1[j];
				}
				Packet p = new Packet(0, i, temp);
				NetworkSimulator.toLayer2(p);
			}
			
		}

	}

	public void update(Packet p) {
		int i, j;
		boolean var=false;
		int[] temp = new int[size];
		DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
    	Date date=new Date();
		System.out.println("Update0 method is called at:"+ dateFormat.format(date));
		System.out.println("Packet received from node "+ p.getSource()+"to node0");
		for (i = 0; i < size; i++) {
			if (i == 0) {
			}
			else{
				if ((p.getMincost(i)+cost[p.getSource()])< temp1[i]) {
					var= true;
					temp1[i] = p.getMincost(i)+cost[p.getSource()];
				}
				else if ((p.getMincost(i)+cost[p.getSource()]) < table[i][p.getSource()]) {
					table[i][p.getSource()] = p.getMincost(i)+cost[p.getSource()];
					System.out.println("Table updated for Node-0");
					printDT();
				}
				
			}
		}

		if (var==true) {
			for (i = 0; i < directneighbour.length; i++) {
			
				if (directneighbour[i]>=0) {
				for (j = 0; j < size; j++) {
						temp[j] = temp1[j];
					}
					p = new Packet(0, i, temp);
					NetworkSimulator.toLayer2(p);
					System.out.println("Packet is sent to node " + i
							+ " from Node 0 based on the updated changes");
				}
				
			}
		}
	}

	public void linkCostChangeHandler(int whichLink, int newCost) {
	}

	public void printDT() {
		System.out.println();
		System.out.println("           via");
		System.out.println(" D0 |   1   2   3");
		System.out.println("----+------------");
		for (int i = 1; i < size; i++) {
			System.out.print("   " + i + "|");
			for (int j = 1; j < size; j++) {
				if (table[i][j] < 10) {
					System.out.print("   ");
				} else if (table[i][j] < 100) {
					System.out.print("  ");
				} else {
					System.out.print(" ");
				}

				System.out.print(table[i][j]);
			}
			System.out.println();
		}
	}
}
