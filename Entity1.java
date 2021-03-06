
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entity1 extends Entity
{    
	int size=NetworkSimulator.NUMENTITIES;
	int[][] table = new int[size][size];
	int[] cost = new int[] { 1, 0, 1, 999 };
	int[] directneighbour = new int[] { 1, 0, 1, 0 };
	int[] temp1 = cost;
    public Entity1()
    {
    	DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
    	Date date=new Date();
    	int i, j;
		System.out.println("Entity1 is called:"
				+ dateFormat.format(date));
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				if (((directneighbour[i]>0) && (i == j))) {
					table[i][j] = cost[i];
				} else {
					table[i][j] = 999;
				}
			}
		}
table[1][1]=0;
		for (i = 0; i < size; i++) {
			int[] temp = new int[size];
			
			if (directneighbour[i]>0) {
				for (j = 0; j < size; j++) {
					temp[j] = temp1[j];
				}
				Packet p = new Packet(1, i, temp);
				NetworkSimulator.toLayer2(p);
			}
		}
    }

    public void update(Packet p)
    {
    	DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
    	Date date=new Date();
    	
    	System.out.println("Update1 method is called: "
				+ dateFormat.format(date));
		System.out.println("Packet received from "
				+ p.getSource()+"to node 1");
		int i, j;
		int var = 0;
		int k = 0;
		for (i = 0; i < size; i++) {
			if (i != 1) {
				var = cost[p.getSource()] + p.getMincost(i);
				if (var < table[i][p.getSource()]) {
					table[i][p.getSource()] = var;
					System.out.println("Distance table updated for node 1");
					printDT();
				}
				if (var < temp1[i]) {
					k = 1;
					temp1[i] = var;
				}
			}
		}

		if (k == 1) {

			for (i = 0; i < size; i++) {
				int[] temp = new int[size];
				
				if (directneighbour[i]>0) {
					for (j = 0; j < size; j++) {
						temp[j] = temp1[j];
					}
					p = new Packet(1, i, temp);
					System.out.println("Packet sent to" + i
							+ " from Node 1 based on updated changes");
					NetworkSimulator.toLayer2(p);
				
			}}
		}

    	
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("         via");
        System.out.println(" D1 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < size; i++)
        {
            if (i == 1)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < size; j += 2)
            {
            
                if (table[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (table[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(table[i][j]);
            }
            System.out.println();
        }
    }
}
