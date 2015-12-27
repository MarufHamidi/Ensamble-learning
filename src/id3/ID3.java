package id3;

import java.util.ArrayList;

public class ID3 {

    public static void run(ArrayList trainDataList, ArrayList testDataList) {
        Data.reset();
        Learning learn;
        Node root;

        learn = new Learning(trainDataList, 9);
        root = learn.id3(trainDataList, 10, learn.attributeIndexSet);

        Data.printTree(root);
        Data.log(root.getDecisionAttribute());
        Data.log("|||||||||||||||||||||||||||||||||||||||||||");


        learn.decide(root, testDataList);
        learn.outputDecisions();

        learn.calculateTestPermance(testDataList);       
        
    }
}
