import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Jly_wave on 11/13/15.
 */
public class BagMain {
    public static void main(String[]args) throws FileNotFoundException {
        //read test
        Scanner input=new Scanner(new File("testBag.txt"));
        double totalSize=input.nextDouble();
        int totalNumber=input.nextInt();
        int T=10000,offspringNumber=3000;
        double crossoverProportion=0.9,variationProportion=0.9;
        double[] size=new double[totalNumber];
        double[] happy=new double[totalNumber];

        //record best happy & best choose
        double bestHappy=0,totalBestHappy=0;
        int[] bestChoose=new int[totalNumber];
        int[] totalBestChoose=new int[totalNumber];
        int[][] offspring=new int[offspringNumber][totalNumber];
        //read size & happy
        for (int i=0;i<totalNumber;i++){
            size[i]=input.nextDouble();
            happy[i]=input.nextDouble();
        }
        input.close();

        BagHeredity bagHeredity=new BagHeredity(offspringNumber,totalNumber,totalSize,size,happy,crossoverProportion,variationProportion);

        bagHeredity.initial(offspring);
        int t=0;

        //main
        while (t<T){

            t++;
            bestHappy=0;
            bestHappy=bagHeredity.chooseBest(offspring,bestChoose,bestHappy);
            if (bestHappy>totalBestHappy){
                totalBestHappy=bestHappy;
                for (int i=0;i<totalNumber;i++){
                    totalBestChoose[i]=bestChoose[i];
                }
            }
            //execute heredity
            bagHeredity.execute(offspring);
            System.out.print("Generation " + t + " The bestHappy is " + bestHappy +" The totalBestHappy is " + totalBestHappy + " The totalBestChoose is ");
            for (int i=0;i<totalNumber;i++)
                System.out.print(totalBestChoose[i]+" ");
            System.out.println();
        }
        PrintWriter output=new PrintWriter("knapsack_2.txt");
        for (int i=0;i<totalNumber;i++)
            if (totalBestChoose[i]==1)
                output.println("true");
            else output.println("false");
        output.println(totalBestHappy);
        output.close();
    }
}
