import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Jly_wave on 11/25/15.
 */
public class HamiltonMain {
    public static void main(String[]args) throws FileNotFoundException {
        //read test
        Scanner input=new Scanner(new File("testHamilton.txt"));
        int totalNumber=input.nextInt();
        double[][] pathCost=new double[totalNumber][totalNumber];
        int T=5000,offspringNumber=20000;
        double crossoverProportion=0.9,variationProportion=0.01;
        double minCost=99999999,totalMinCost=99999999;
        int[] minChoose=new int[totalNumber];
        int[] totalMinChoose=new int[totalNumber];
        int[][] offspring=new int[offspringNumber][totalNumber];

        for (int i=0;i<(totalNumber-1)*totalNumber/2;i++){
            int x=input.nextInt();
            int y=input.nextInt();
            double z=input.nextDouble();
            pathCost[x][y]=z;
            pathCost[y][x]=z;
        }
        input.close();
        HamiltonHeredity hamiltonHeredity=new HamiltonHeredity(offspringNumber,totalNumber,pathCost,crossoverProportion,variationProportion);
        hamiltonHeredity.initial(offspring);

        int t=0;

        //main
        while (t<T){

            t++;
            minCost=99999999;
            minCost=hamiltonHeredity.chooseMin(offspring,minChoose,minCost);
            if (minCost<totalMinCost) {
                totalMinCost=minCost;
                for (int j=0;j<totalNumber;j++)
                    totalMinChoose[j]=minChoose[j];
            }
            //execute heredity
            hamiltonHeredity.execute(offspring);
            System.out.print("Generation " + t + " The minCost is " + minCost +" The totalMinCost is " + totalMinCost+  " The totalMinChoose is ");
            for (int i=0;i<totalNumber;i++)
                System.out.print(totalMinChoose[i]+" ");
            System.out.println();
        }
        PrintWriter output=new PrintWriter("TSP_2.txt");
        for (int i=0;i<totalNumber;i++)
                output.println(totalMinChoose[i]);
        output.println(totalMinCost);
        output.close();
    }
}
