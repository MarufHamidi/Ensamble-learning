
import commons.Data;
import commons.Node;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
//        id3.ID3.run();
        Data.init();
        ArrayList trainData = Data.readData("input/train.csv");
        ArrayList testData = Data.readData("input/test.csv");
        
        int[] rounds = {5,10,20,30};        
        ArrayList<Node> classifiers = adaboosting.AdaBoosting.run(trainData, rounds[rounds.length - 1]);
        
        Tester tester = new Tester(classifiers, trainData, testData, rounds);
        tester.runTest();
    }
}
