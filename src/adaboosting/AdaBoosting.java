package adaboosting;

import commons.Data;
import commons.Node;
import commons.PatientData;
import decisionstump.DecisionStump;
import java.util.ArrayList;
import java.util.Iterator;

public class AdaBoosting {

    public static ArrayList run(ArrayList trainData, int boost) {
        ArrayList<Node> classifiers = new ArrayList<>();
        Node root = null;
        for (int i = 1; i <= boost; i++) {
            trainData = Data.assignWeightsToData(root, trainData);
            root = DecisionStump.getClassifier(trainData);

            int error = 0;
            // first we predict the class by the classifier
            Iterator itr = trainData.iterator();
            PatientData temp;
            while (itr.hasNext()) {
                temp = (PatientData) itr.next();
                temp.setPredictedClass(root.decideClass(temp));
                if (temp.isMisClassified()) {
                    error++;
                }
            }

            double errorRate = error * 1.0 / trainData.size();
            double cWeight = 0.5 * Math.log((1 - errorRate) / errorRate);
            root.setWeight(cWeight);
            classifiers.add(root);
            
            Data.printTree(root, "Tree #" + i + " for total <" + boost + "> rounds; weight = " + root.getWeight());
        }
        return classifiers;
    }
}
