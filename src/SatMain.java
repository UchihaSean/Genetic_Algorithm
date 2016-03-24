import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by Jly_wave on 11/25/15.
 */
public class SatMain {
    public static void main(String[]args) throws FileNotFoundException {
        Scanner input=new Scanner(new File("data/SAT3/SAT3_1.txt"));
        int totalNumber=input.nextInt()+1;
        int limitNumber=input.nextInt();
        int T=100,offspringNumber=1000;
        double crossoverProportion=0.9,variationProportion=0.01;
        int[] limitX=new int[limitNumber];
        int[] limitY=new int[limitNumber];
        int[] limitZ=new int[limitNumber];
        for (int i=0;i<limitNumber;i++){
            limitX[i]=input.nextInt();
            limitY[i]=input.nextInt();
            limitZ[i]=input.nextInt();
        }
        input.close();
        double bestFit=0,totalBestFit=0;
        int[] bestChoose=new int[totalNumber];
        int[] totalBestChoose=new int[totalNumber];
        int[][] offspring=new int[offspringNumber][totalNumber];

        SatHeredity satHeredity=new SatHeredity(offspringNumber,totalNumber,limitNumber,limitX,limitY,limitZ,crossoverProportion,variationProportion);
        satHeredity.initial(offspring);

        int t=0;

        //main
        while (t<T){

            t++;
            bestFit=0;
            bestFit=satHeredity.chooseBest(offspring,bestChoose,bestFit);
            if (bestFit>totalBestFit){
                totalBestFit=bestFit;
                for (int i=1;i<totalNumber;i++)
                    totalBestChoose[i]=bestChoose[i];
            }
            //execute heredity
            satHeredity.execute(offspring);

            System.out.print("Generation " + t + " The bestFit is " + bestFit +" The totalBestFit is " + totalBestFit + " The totalBestChoose is ");
            for (int i=1;i<totalNumber;i++)
                System.out.print(totalBestChoose[i]+" ");
            System.out.println();
        }
        PrintWriter output=new PrintWriter("SAT3_1.txt");
        for (int i=1;i<totalNumber;i++)
            if (totalBestChoose[i]==1)
                output.println("true");
            else output.println("false");
        output.close();

    }
}
