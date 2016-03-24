/**
 * Created by Jly_wave on 11/13/15.
 */
public class BagHeredity {
    private int offspringNumber,totalNumber;
    private double crossoverProportion,variationProportion,totalSize;
    private double[] size,happy,proportion;
    public BagHeredity(int offspringNumber,int totalNumber,double totalSize,double[] size,double[] happy,
                       double crossoverProportion,double variationProportion){
        this.offspringNumber=offspringNumber;
        this.totalNumber=totalNumber;
        this.totalSize=totalSize;
        this.size=new double[totalNumber];
        this.happy=new double[totalNumber];
        this.proportion=new double[offspringNumber];
        this.crossoverProportion=crossoverProportion;
        this.variationProportion=variationProportion;
        for (int i=0;i<totalNumber;i++){
            this.size[i]=size[i];
            this.happy[i]=happy[i];
        }
    }
    //initial
    public void initial(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
            double selectProportion=0.5;
            while (true){
                for (int j=0;j<totalNumber;j++) {
                    if (Math.random() > selectProportion) {
                        offspring[i][j] = 1;
                    }
                    else
                        offspring[i][j] = 0;
                }
                if (legal(offspring[i])) break;
                selectProportion+=0.05;
            }
        }
    }
    //choose best happy
    public double chooseBest(int[][] offspring,int[] bestChoose,double bestHappy){
        for (int i=0;i<offspringNumber;i++){
            double sumHappy=0;
            for (int j=0;j<totalNumber;j++)
                sumHappy+=offspring[i][j]*happy[j];
            if (sumHappy>bestHappy){
                bestHappy=sumHappy;
                for (int j=0;j<totalNumber;j++)
                    bestChoose[j]=offspring[i][j];
            }
        }
        return bestHappy;
    }
    //execute
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
            for (int j=0;j<totalNumber;j++)
                proportion[i]+=offspring[i][j]*happy[j];
        }
        double totalHappy=0;
        for (int i=0;i<offspringNumber;i++)
            totalHappy+=proportion[i];
        for (int i=0;i<offspringNumber;i++)
            proportion[i]/=totalHappy;
        for (int i=1;i<offspringNumber;i++)
            proportion[i]+=proportion[i-1];
    }
    //select
    public void calcSelect(int[][] offspring){
        int[][] nowOffspring=new int[offspringNumber][totalNumber];
        for (int i=1;i<offspringNumber;i++)
            proportion[i]+=proportion[i-1];
        for (int i=0;i<offspringNumber/2;i++){
            for (int j=1;j<totalNumber;j++)
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
    }

    //crossover
    public void calcCrossover(int[][] offspring){
        int[][] nowOffspring=new int[offspringNumber][totalNumber];
        int nowSelect=offspringNumber/2;
        for (int i=0;i<nowSelect;i++){
            for (int j=1;j<totalNumber;j++)
                nowOffspring[i][j]=offspring[i][j];
        }
            while (true) {
                //enough offspring
                if (nowSelect>=offspringNumber) break;
                int selcetMother = (int) Math.floor(Math.random() * offspringNumber);
                int selcetFather = (int) Math.floor(Math.random() * offspringNumber);

                int selectPosition = (int) Math.floor(Math.random() * totalNumber);
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
                    for (int j = 0; j < selectPosition; j++) {
                        nowOffspring[nowSelect][j] = offspring[selcetMother][j];
                        if (nowSelect+1<offspringNumber)
                        nowOffspring[nowSelect+1][j] = offspring[selcetFather][j];
                    }
                    for (int j = selectPosition; j < totalNumber; j++) {
                        nowOffspring[nowSelect][j] = offspring[selcetFather][j];
                        if (nowSelect+1<offspringNumber)
                        nowOffspring[nowSelect + 1][j] = offspring[selcetMother][j];
                    }
                    if (legal(nowOffspring[nowSelect])) nowSelect++;
                    if (nowSelect<offspringNumber)
                    if (legal(nowOffspring[nowSelect])) nowSelect++;

            }

        //renew
        for (int i=0;i<offspringNumber;i++)
            for (int j=0;j<totalNumber;j++)
                offspring[i][j]=nowOffspring[i][j];
    }
    public void calcVariation(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
            while (true) {
                //select one for variation
                int j=(int)Math.floor(Math.random()*totalNumber);
                    if (Math.random() < variationProportion)
                        offspring[i][j] = 1 - offspring[i][j];
                if (legal(offspring[i])) break;
                offspring[i][j] = 1 - offspring[i][j];
            }
        }
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

    //proportion select
    private int select(){
        double random=Math.random();
        for (int i=0;i<offspringNumber;i++)
            if (random<=proportion[i]) return i;
        return 0;
    }

    //islegal
    private boolean legal(int[] selectOffspring){
        double sumSize=0;
        for (int i=0;i<totalNumber;i++)
            sumSize+=selectOffspring[i]*size[i];
        if (sumSize-1e-7>totalSize) return false;
        return true;
    }
}
