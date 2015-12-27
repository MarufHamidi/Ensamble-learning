
import commons.Data;
import commons.Node;
import commons.PatientData;
import java.util.ArrayList;
import java.util.Iterator;

public class Tester {

    public ArrayList<Node> classifier;
    public ArrayList<PatientData> testData;
    public ArrayList<PatientData> trainData;
    public int[] rounds;

    public Tester(ArrayList<Node> clsfr, ArrayList<PatientData> train, ArrayList<PatientData> td, int[] r) {
        this.classifier = clsfr;
        this.trainData = train;
        this.testData = td;
        this.rounds = r;
    }

    public void testDecisionStump() {
        Node root = classifier.get(0);
        Iterator itr = testData.iterator();
        PatientData pd;
        int error = 0;

        while (itr.hasNext()) {
            pd = (PatientData) itr.next();
            pd.setPredictedClass(root.decideClass(pd));
            if (pd.isMisClassified()) {
                error++;
            }
        }
        double accuracy = ((testData.size() - error) * 100.0) / testData.size();
        Data.output("Accuracy of decision stump : " + accuracy);
        System.out.println("Accuracy of decision stump : " + accuracy);
    }

    public void testAdaBoosting() {
        int totalData = testData.size();
        int[] classes;
        int[] error = new int[rounds.length];

        PatientData pd;
        for (int i = 0; i < totalData; i++) {
            pd = testData.get(i);
            classes = getClasses(pd);

            for (int j = 0; j < classes.length; j++) {
                if (pd.actualClass != classes[j]) {
                    error[j]++;
                }
            }
        }

        double accuracy;
        for (int i = 0; i < error.length; i++) {
            accuracy = ((testData.size() - error[i]) * 100.0) / testData.size();
            System.out.println("Accuracy for rounds <"+rounds[i]+"> : " +accuracy);
        }
    }

    public int[] getClasses(PatientData pd) {
        Node root;
        int totalClsfr = classifier.size();
        int[] classes = new int[rounds.length];

        double posW = 0.0;
        double negW = 0.0;
        int posC = 0;
        int negC = 0;
        for (int i = 0, j = 0; i < totalClsfr; i++) {
            root = classifier.get(i);
            if (root.decideClass(pd) == 0) {
                negW = negW + root.decideClass(pd);
                negC++;
            } else {
                posW = posW + root.decideClass(pd);
                posC++;
            }

            // determine for different rounds
            if (i == (rounds[j] - 1)) {
                if (posW > negW) {
                    classes[j] = 1;
                } else if (negW > posW) {
                    classes[j] = 0;
                } else {
                    if (posC > negC) {
                        classes[j] = 1;
                    } else {
                        classes[j] = 0;
                    }
                }

                j++;
            }
        }

        return classes;
    }

    public void runTest() {
        id3.ID3.run(trainData, testData);
        testDecisionStump();
        testAdaBoosting();
    }
}
