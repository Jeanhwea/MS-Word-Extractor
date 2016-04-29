package cn.edu.buaa.sei.rucm.ds;

import java.io.Serializable;
import java.util.List;

public class Flow implements Serializable {

    private static final long serialVersionUID = 4094609740211431878L;

    private List<Integer> basicFlowSteps;
    private List<Step> steps;
    private List<Condition> postConditions;

    public Flow() {
    }

    /**
     * @return the basicFlowSteps
     */
    public List<Integer> getBasicFlowSteps() {
        return basicFlowSteps;
    }

    /**
     * @param basicFlowSteps the basicFlowSteps to set
     */
    public void setBasicFlowSteps(List<Integer> basicFlowSteps) {
        this.basicFlowSteps = basicFlowSteps;
    }

    /**
     * @return the steps
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * @param steps the steps to set
     */
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    /**
     * @return the postConditions
     */
    public List<Condition> getPostConditions() {
        return postConditions;
    }

    /**
     * @param postConditions the postConditions to set
     */
    public void setPostConditions(List<Condition> postConditions) {
        this.postConditions = postConditions;
    }

}
