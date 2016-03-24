/**
 * Created by Jly_wave on 11/25/15.
 */
public class HamiltonHeredity {
    private int offspringNumber,totalNumber;
    private double[][] pathCost;
    private double[] proportion;
    private double crossoverProportion,variationProportion;
    public HamiltonHeredity(int offspringNumber,int totalNumber,double[][] pathCost,double crossoverProportion,double variationProportion){
        this.offspringNumber=offspringNumber;
        this.totalNumber=totalNumber;
        this.pathCost=new double[totalNumber][totalNumber];
        this.crossoverProportion=crossoverProportion;
        this.variationProportion=variationProportion;
        this.proportion=new double[offspringNumber];
        for (int i=0;i<totalNumber;i++)
            for (int j=i+1;j<totalNumber;j++){
                this.pathCost[i][j]=pathCost[i][j];
                this.pathCost[j][i]=pathCost[j][i];
            }
    }
    //initial
    public void initial(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
            for (int j=0;j<totalNumber;j++)
                offspring[i][j]=j;
            for (int j=0;j<totalNumber*100;j++){
                int x=(int)Math.floor(Math.random()*totalNumber);
                int y=(int)Math.floor(Math.random()*totalNumber);
                if (x!=y)
                swap(offspring[i],x,y);
            }
        }
    }
    //choose best happy
    public double chooseMin(int[][] offspring,int[] minChoose,double minCost){
        for (int i=0;i<offspringNumber;i++){
            double sumCost=0;
            for (int j=0;j<totalNumber;j++) {
                int t=j+1;
                if (t==totalNumber) t=0;
                sumCost += pathCost[offspring[i][j]][offspring[i][t]];
            }
            if (sumCost<minCost){
                minCost=sumCost;
                for (int j=0;j<totalNumber;j++)
                    minChoose[j]=offspring[i][j];
            }
        }
        return minCost;
    }
    public void execute(int[][] offspring){
        calcProportion(offspring);
        calcSort(offspring);
        calcSelect(offspring);
        calcProportion(offspring);
        calcSort(offspring);
        calcCrossover(offspring);
        calcVariation(offspring);
    }

    //calc proportion
    public void calcProportion(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
            proportion[i]=0;
            for (int j=0;j<totalNumber;j++) {
                int t=j+1;
                if (t==totalNumber) t=0;
                proportion[i] +=pathCost[offspring[i][j]][offspring[i][t]];
            }
        }
        for (int i=0;i<offspringNumber;i++)
            proportion[i]=proportion[offspringNumber-1]/proportion[i];
        double totalProportion=0;
        for (int i=0;i<offspringNumber;i++)
            totalProportion+=proportion[i];
        for (int i=0;i<offspringNumber;i++)
            proportion[i]/=totalProportion;
        for (int i=1;i<offspringNumber;i++)
            proportion[i]+=proportion[i-1];
        //test
//        for (int i=0;i<offspringNumber;i++){
//            System.out.print(proportion[i]+" ");
//            for (int j=0;j<totalNumber;j++)
//                System.out.print(offspring[i][j]+" ");
//            System.out.println();
//        }
    }
    //select
    public void calcSelect(int[][] offspring){
        int[][] nowOffspring=new int[offspringNumber][totalNumber];
        for (int i=1;i<offspringNumber;i++)
            proportion[i]+=proportion[i-1];
        for (int i=0;i<offspringNumber/2;i++){
//            int selectNumber=select();
            for (int j=0;j<totalNumber;j++)
                nowOffspring[i][j]=offspring[i][j];
        }
        for (int i=offspringNumber/2;i<offspringNumber;i++){
            int selectNumber=select();
            for (int j=0;j<totalNumber;j++)
                nowOffspring[i][j]=offspring[selectNumber][j];
        }
        for (int i=0;i<offspringNumber;i++)
            for (int j=0;j<totalNumber;j++)
                offspring[i][j]=nowOffspring[i][j];

        //test
//        System.out.println();
//        for (int i=0;i<offspringNumber;i++){
////            System.out.print(proportion[i]+" ");
//            for (int j=0;j<totalNumber;j++)
//                System.out.print(offspring[i][j]+" ");
//            System.out.println();
//        }
    }

    //crossover
    public void calcCrossover(int[][] offspring){
        int[][] nowOffspring=new int[offspringNumber][totalNumber];
        int nowSelect=offspringNumber/2;
        for (int i=0;i<nowSelect;i++){
//            int selectNumber=select();
            for (int j=0;j<totalNumber;j++)
                nowOffspring[i][j]=offspring[i][j];
        }
        while (true) {
            //enough offspring
            if (nowSelect>=offspringNumber) break;
            int selcetMother = (int) Math.floor(Math.random() * offspringNumber);
            int selcetFather = (int) Math.floor(Math.random() * offspringNumber);
            if (Math.random() > crossoverProportion) {
                for (int j = 0; j < totalNumber; j++) {
                    nowOffspring[nowSelect][j] = offspring[selcetMother][j];
                    //notice the border
                    if (nowSelect+1<offspringNumber)
                        nowOffspring[nowSelect+1][j] = offspring[selcetFather][j];
                }
                nowSelect+=2;
                continue;
            }
            int selectPositionx = (int) Math.floor(Math.random() * totalNumber);
            int selectPositiony =(int) Math.floor(Math.random() * totalNumber);

            if (selectPositionx>selectPositiony){
                int selectPosition=selectPositionx;
                selectPositionx=selectPositiony;
                selectPositiony=selectPosition;
            }

            //test
//            System.out.println(nowSelect+" "+selcetFather+" "+selcetMother+" "+selectPositionx+" "+selectPositiony);


            boolean[] flag=new boolean[totalNumber];
            //new offspring
            for (int i=0;i<totalNumber;i++)
                flag[i]=false;
            for (int i=selectPositionx;i<selectPositiony+1;i++) {
                flag[offspring[selcetMother][i]] = true;
                nowOffspring[nowSelect][i]=offspring[selcetMother][i];
            }
            int j=0;
            for (int i=selectPositiony+1;i<totalNumber;i++){
                if (!flag[offspring[selcetFather][i]]){
                    if (j==selectPositionx) j=selectPositiony+1;
                    nowOffspring[nowSelect][j]=offspring[selcetFather][i];
                    j++;
                }
            }
            for (int i=0;i<selectPositiony+1;i++){
                if (!flag[offspring[selcetFather][i]]){
                    if (j==selectPositionx) j=selectPositiony+1;
                    nowOffspring[nowSelect][j]=offspring[selcetFather][i];
                    j++;
                }
            }
            nowSelect++;
            if (nowSelect>=offspringNumber) break;

            //new offspring
            for (int i=0;i<totalNumber;i++)
                flag[i]=false;
            for (int i=selectPositionx;i<selectPositiony+1;i++) {
                flag[offspring[selcetFather][i]] = true;
                nowOffspring[nowSelect][i]=offspring[selcetFather][i];
            }
            j=0;
            for (int i=selectPositiony+1;i<totalNumber;i++){
                if (!flag[offspring[selcetMother][i]]){
                    if (j==selectPositionx) j=selectPositiony+1;
                    nowOffspring[nowSelect][j]=offspring[selcetMother][i];
                    j++;
                }
            }
            for (int i=0;i<selectPositiony+1;i++){
                if (!flag[offspring[selcetMother][i]]){
                    if (j==selectPositionx) j=selectPositiony+1;
                    nowOffspring[nowSelect][j]=offspring[selcetMother][i];
                    j++;
                }
            }
            nowSelect++;


        }

        //renew
        for (int i=0;i<offspringNumber;i++)
            for (int j=0;j<totalNumber;j++)
                offspring[i][j]=nowOffspring[i][j];

        //test
//        System.out.println();
//        for (int i=0;i<offspringNumber;i++){
////            System.out.print(proportion[i]+" ");
//            for (int j=0;j<totalNumber;j++)
//                System.out.print(offspring[i][j]+" ");
//            System.out.println();
//        }
    }
    public void calcVariation(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
                //select section for variation
            int selectPositionx = (int) Math.floor(Math.random() * totalNumber);
            int selectPositiony =(int) Math.floor(Math.random() * totalNumber);

            if (selectPositionx>selectPositiony){
                int selectPosition=selectPositionx;
                selectPositionx=selectPositiony;
                selectPositiony=selectPosition;
            }
                if (Math.random() < variationProportion){
                    int[] selected=new int[selectPositiony-selectPositionx+1];
                    for (int j=0;j<selectPositiony-selectPositionx+1;j++)
                        selected[selectPositiony-selectPositionx-j]=offspring[i][selectPositionx+j];
                    for (int j=selectPositionx;j<selectPositiony+1;j++)
                        offspring[i][j]=selected[j-selectPositionx];
                }
        }

        //test
//        System.out.println();
//        for (int i=0;i<offspringNumber;i++){
////            System.out.print(proportion[i]+" ");
//            for (int j=0;j<totalNumber;j++)
//                System.out.print(offspring[i][j]+" ");
//            System.out.println();
//        }
    }

    //sort
    private void calcSort(int[][] offspring){
        for (int i=offspringNumber-1;i>0;i--){
            proportion[i]=proportion[i]-proportion[i-1];
        }
        for (int i=0;i<offspringNumber;i++)
            for (int j=i+1;j<offspringNumber;j++){
                if (proportion[i]<proportion[j]){
                    double t=proportion[i];
                    proportion[i]=proportion[j];
                    proportion[j]=t;
                    int[] s=offspring[i];
                    offspring[i]=offspring[j];
                    offspring[j]=s;
                }
            }
    }
    private void swap(int[] u,int x,int y){
        int t=u[x];
        u[x]=u[y];
        u[y]=t;
    }
    //proportion select
    private int select(){
        double random=Math.random();
        for (int i=0;i<offspringNumber;i++)
            if (random<=proportion[i]) return i;
        return 0;
    }
}
