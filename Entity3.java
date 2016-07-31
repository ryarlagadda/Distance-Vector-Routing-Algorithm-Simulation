
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entity3 extends Entity
{    
	int size=NetworkSimulator.NUMENTITIES;
	int[][] table = new int[size][size];
	int[] directneighbour = new int[] { 1, 0, 1, 0 };
	int[] cost = new int[] { 7, 999, 2, 0 };
	int[] temp1 =cost;
    public Entity3()
    {
    	DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
    	Date date=new Date();
    	
    	int i, j;
		System.out.println("Entity3 is called:"
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
table[3][3]=0;
		for (i = 0; i < size; i++) {
			int[] temp = new int[size];
			if (directneighbour[i]>0) {
				for (j = 0; j < size; j++) {
					temp[j] = temp1[j];
				}
				Packet p = new Packet(3, i, temp);
				NetworkSimulator.toLayer2(p);
			}
		}
    	
    }
    public void update(Packet p)
    {
    	DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SS");
    	Date date=new Date();
    	System.out.println("Update3 method is called:"
				+ dateFormat.format(date));
		System.out.println("Packet recieved from node "
				+ p.getSource()+" to node 3");
		int i, j;
		int var = 0;
		int k = 0;
		for (i = 0; i < size; i++) {
			if (i != 3) {
				var = cost[p.getSource()] + p.getMincost(i);
				if (var < table[i][p.getSource()]) {
					table[i][p.getSource()] = var;
					System.out.println("Distance table updated for node 3");
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
					p = new Packet(3, i, temp);
					System.out.println("Packet to node " + i
							+ " from Node 3 based on the updated changes");
					NetworkSimulator.toLayer2(p);
				}
			}
		}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println("         via");
        System.out.println(" D3 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < size; i++)
        {
            if (i == 3)
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
