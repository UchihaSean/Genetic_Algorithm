import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Jly_wave on 11/27/15.
 */
public class MakeSatTest {
    public static void main(String[]args) throws FileNotFoundException {
        PrintWriter output=new PrintWriter("testSat.txt");
        int totalNumber=100;
        int limitNumber=1000;
        int[] test=new int[totalNumber+1];
        output.println(totalNumber+" "+limitNumber);
        for (int i=1;i<totalNumber+1;i++){
            if (Math.random()>0.5)
                test[i]=1; else
                test[i]=-1;
        }
        for (int i=0;i<limitNumber;i++){
            int x=(int)(Math.floor(Math.random()*totalNumber)+1);
            int y=(int)(Math.floor(Math.random()*totalNumber)+1);
            while (y==x) y=(int)(Math.floor(Math.random()*totalNumber)+1);
            int z=(int)(Math.floor(Math.random()*totalNumber)+1);
            while (z==x || z==y) z=(int)(Math.floor(Math.random()*totalNumber)+1);
            x=test[x]*x;
            y=-1*test[y]*y;
            if (Math.random()>0.5)
                z=test[z]*z;
            else
                z=-1*test[z]*z;
            output.println(x+" "+y+" "+z);

        }

        output.close();
    }
}
