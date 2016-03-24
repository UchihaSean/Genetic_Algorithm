import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Jly_wave on 11/26/15.
 */
public class MakeHamiltonTest {
    public static void main(String[]args) throws FileNotFoundException {
        PrintWriter output=new PrintWriter("testHamilton.txt");
        int totalNumber=100;
        output.println(totalNumber);
        for (int i=0;i<totalNumber;i++)
            for (int j=i+1;j<totalNumber;j++){
                if (j==i+1 || i==j+1-totalNumber)
                    output.println(i+" "+j+" "+1);
                else {
                    int t=(int)(Math.random()*10)+2;
                    output.println(i + " " + j + " " + t);
                }
            }
        output.close();
    }
}
