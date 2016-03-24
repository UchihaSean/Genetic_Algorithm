import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Jly_wave on 11/14/15.
 */
public class BagDynamic {
    public static void main(String[]args) throws FileNotFoundException {
        //input
        Scanner input=new Scanner(new File("testBag.txt"));
        int totalSize=input.nextInt();
        int totalNumber=input.nextInt();
        int[] size=new int[totalNumber];
        int[] happy=new int[totalNumber];
        int[] f=new int[totalSize+1];
        int[] t=new int[totalSize+1];
        int[] mark=new int[totalNumber];
        for (int i=0;i<totalNumber;i++){
            size[i]=input.nextInt();
            happy[i]=input.nextInt();
            mark[i]=0;
        }
        input.close();
        for (int i=0;i<=totalSize;i++){
            f[i]=0;
        }
        int max=0,maxSize=0;
        for (int i=0;i<totalNumber;i++){
            for (int j=totalSize;j>=size[i];j--){
                if (f[j]<f[j-size[i]]+happy[i]){
                    f[j]=f[j-size[i]]+happy[i];
                    t[j]=i;
                    if (f[j]>max) {
                        max = f[j];
                        maxSize=j;
                    }
                }
            }
        }

        //
        while (maxSize>0){
            mark[t[maxSize]]=1;
            maxSize-=size[t[maxSize]];
//            System.out.println(maxSize);
        }
        System.out.print("The bestHappy is " + max + " The bestChoose is ");
        for (int i=0;i<totalNumber;i++){
            System.out.print(mark[i] + " ");
        }
        System.out.println();
    }
}
