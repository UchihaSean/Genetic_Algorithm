/**
 * Created by Jly_wave on 11/25/15.
 */
public class SatHeredity {
    private int offspringNumber,totalNumber,limitNumber;
    private int[] limitX,limitY,limitZ;
    private double crossoverProportion,variationProportion;
    private double[] proportion;
    public SatHeredity(int offspringNumber,int totalNumber,int limitNumber,int[] limitX,int[] limitY,int[] limitZ,
                                               double crossoverProportion,double variationProportion){
        this.offspringNumber=offspringNumber;
        this.totalNumber=totalNumber;
        this.limitNumber=limitNumber;
        this.limitX=new int[limitNumber];
        this.limitY=new int[limitNumber];
        this.limitZ=new int[limitNumber];
        this.proportion=new double[offspringNumber];
        this.crossoverProportion=crossoverProportion;
        this.variationProportion=variationProportion;
        for (int i=0;i<limitNumber;i++){
            this.limitX[i]=limitX[i];
            this.limitY[i]=limitY[i];
            this.limitZ[i]=limitZ[i];
        }
    }
    //initial
    public void initial(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
            for (int j=1;j<totalNumber;j++) {
                if (Math.random() > 0.5){
                    offspring[i][j]=1;
                } else
                    offspring[i][j]=-1;
            }
        }
    }

    //choose best happy
    public double chooseBest(int[][] offspring,int[] bestChoose,double bestFit){
        for (int i=0;i<offspringNumber;i++){
            double sumFit=0;
            for (int j=0;j<limitNumber;j++){
                int fitNumber=0;
                if (limitX[j]*offspring[i][Math.abs(limitX[j])]>0) fitNumber++;
                if (limitY[j]*offspring[i][Math.abs(limitY[j])]>0) fitNumber++;
                if (limitZ[j]*offspring[i][Math.abs(limitZ[j])]>0) fitNumber++;
                if (fitNumber>0) sumFit++;
            }

            if (sumFit>bestFit){
                bestFit=sumFit;
                for (int j=1;j<totalNumber;j++)
                    bestChoose[j]=offspring[i][j];
            }
        }
        return bestFit;
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
            for (int j=0;j<limitNumber;j++){
                int fitNumber=0;
//                System.out.println(limitX[j]+" "+offspring[i][Math.abs(limitX[j])]);
                if (limitX[j]*offspring[i][Math.abs(limitX[j])]>0) fitNumber++;
                if (limitY[j]*offspring[i][Math.abs(limitY[j])]>0) fitNumber++;
                if (limitZ[j]*offspring[i][Math.abs(limitZ[j])]>0) fitNumber++;
//                System.out.println(fitNumber);
                if (fitNumber>0) proportion[i]++;
            }
        }
        double totalFit=0;
        for (int i=0;i<offspringNumber;i++)
            totalFit+=proportion[i];
        for (int i=0;i<offspringNumber;i++)
            proportion[i]/=totalFit;
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
            for (int j=1;j<totalNumber;j++)
                nowOffspring[i][j]=offspring[selectNumber][j];
        }
        for (int i=0;i<offspringNumber;i++)
            for (int j=1;j<totalNumber;j++)
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

            int selectPosition = (int) Math.floor(Math.random() * (totalNumber-1))+1;
            if (Math.random() > crossoverProportion) {
                for (int j = 1; j < totalNumber; j++) {
                    nowOffspring[nowSelect][j] = offspring[selcetMother][j];
                    //notice the border
                    if (nowSelect+1<offspringNumber)
                        nowOffspring[nowSelect+1][j] = offspring[selcetFather][j];
                }
                nowSelect+=2;
                continue;
            }
            for (int j = 1; j < selectPosition; j++) {
                nowOffspring[nowSelect][j] = offspring[selcetMother][j];
                if (nowSelect+1<offspringNumber)
                    nowOffspring[nowSelect+1][j] = offspring[selcetFather][j];
            }
            for (int j = selectPosition; j < totalNumber; j++) {
                nowOffspring[nowSelect][j] = offspring[selcetFather][j];
                if (nowSelect+1<offspringNumber)
                    nowOffspring[nowSelect + 1][j] = offspring[selcetMother][j];
            }
           nowSelect+=2;

        }
        //renew
        for (int i=0;i<offspringNumber;i++)
            for (int j=1;j<totalNumber;j++)
                offspring[i][j]=nowOffspring[i][j];


    }

    public void calcVariation(int[][] offspring){
        for (int i=0;i<offspringNumber;i++){
                //select one for variation
                int j=(int)Math.floor(Math.random()*(totalNumber-1))+1;
                if (Math.random() < variationProportion)
                    offspring[i][j] = -1*offspring[i][j];

            //test
//            for (int j=1;j<totalNumber;j++)
//                if (Math.random()<variationProportion)
//                    offspring[i][j]=-1*offspring[i][j];
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
}
