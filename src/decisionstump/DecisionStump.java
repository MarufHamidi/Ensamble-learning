package decisionstump;

import commons.Node;
import commons.PatientData;
import java.util.ArrayList;
import java.util.Random;

public class DecisionStump {
    
    public static ArrayList getWeightedSelection(ArrayList<PatientData> trainData){
        int totalData = trainData.size();
        ArrayList weightedSelection = new ArrayList();
        Random random = new Random();
        double rn;
        double cumuProb;
        
        for(int i=0;i<totalData;i++){
            rn = random.nextDouble();
            cumuProb = 0.0;
            for(int j=0;j<totalData;j++){
                cumuProb = cumuProb + trainData.get(i).getWeight();
                if(cumuProb >= rn){
                    weightedSelection.add(trainData.get(j));
                    break;
                }
            }
        }
        
        return weightedSelection;
    }
    
    public static Node getClassifier(ArrayList trainData){
        Learning learn = new Learning(trainData, 9);
        Node root = learn.id3Adjusted(getWeightedSelection(trainData), 10, learn.attributeIndexSet);
        System.gc();        
        return root;
    }
}
