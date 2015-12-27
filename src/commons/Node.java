package commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Node {

    // 1  positive leaf node (+)
    // 0  negative leaf node (-)
    // -1 this is not the leaf node so go to the branches
    public int label;
    // atibute index 1 - 9
    public int decisionAttribute;
    // total example data set at this node
    public List<PatientData> examples;
    // baranches 
    // Integer - attribute value as key
    // Node - branch node
    public Map<Integer, Node> branchMap;
    public double weight;
    
    public Node() {
        examples = new ArrayList<>();
        branchMap = null;
        setLabel(-1);
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getDecisionAttribute() {
        return decisionAttribute;
    }

    public void setDecisionAttribute(int decisionAttribute) {
        this.decisionAttribute = decisionAttribute;
    }

    public void initBranching(Set<Integer> possibleValues) {
        branchMap = new HashMap<>(possibleValues.size());
        Iterator itr = possibleValues.iterator();
        int tempVal;
        while (itr.hasNext()) {
            tempVal = (int) itr.next();
            branchMap.put(tempVal, new Node());
        }
    }

    public List<PatientData> getExamples() {
        return examples;
    }

    public Map<Integer, Node> getBranchMap() {
        return branchMap;
    }

    public int decideClass(PatientData pd) {
        if (label == -1) {
            Node next = branchMap.get(pd.getAttributeValue(decisionAttribute));
            return next.decideClass(pd);
        } else {
            return label;
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }   
    
    public void freeData() {
        this.examples = null;
    }
}
