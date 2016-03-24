import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Jly_wave on 11/14/15.
 */
public class MakeBagTest {
    public static void main(String[]args) throws FileNotFoundException {
        PrintWriter output=new PrintWriter("testBag.txt");
        int totalSize=10000;
        int totalNumber=1000;
        output.println(totalSize);
        output.println(totalNumber);
        for (int i=0;i<totalNumber;i++){
            int x=(int)Math.floor(Math.random()*totalSize/totalNumber*10+1);
            int y=(int)Math.floor(Math.random()*totalSize/totalNumber*10+1);
            output.println(x+" "+y);
        }
        output.close();
    }
}
