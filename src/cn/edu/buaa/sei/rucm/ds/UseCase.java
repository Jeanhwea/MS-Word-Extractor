package cn.edu.buaa.sei.rucm.ds;

import java.io.Serializable;
import java.util.List;

public class UseCase implements Serializable {

    private static final long serialVersionUID = -7870142969599648864L;
    
    private String useCaseName;
    private List<Condition> preConditions;
    private List<Input> inputs;
    private List<Output> outputs;
    private List<Condition> postConditions;
    private List<Step> basicFlow;
    private List<Flow> alterFlow;

    public UseCase()
    {
    }

    /**
     * @return the useCaseName
     */
    public String getUseCaseName()
    {
        return useCaseName;
    }

    /**
     * @param useCaseName the useCaseName to set
     */
    public void setUseCaseName(String useCaseName)
    {
        this.useCaseName = useCaseName;
    }

    /**
     * @return the preConditions
     */
    public List<Condition> getPreConditions()
    {
        return preConditions;
    }

    /**
     * @param preConditions the preConditions to set
     */
    public void setPreConditions(List<Condition> preConditions)
    {
        this.preConditions = preConditions;
    }

    /**
     * @return the inputs
     */
    public List<Input> getInputs()
    {
        return inputs;
    }

    /**
     * @param inputs the inputs to set
     */
    public void setInputs(List<Input> inputs)
    {
        this.inputs = inputs;
    }

    /**
     * @return the outputs
     */
    public List<Output> getOutputs()
    {
        return outputs;
    }

    /**
     * @param outputs the outputs to set
     */
    public void setOutputs(List<Output> outputs)
    {
        this.outputs = outputs;
    }

    /**
     * @return the postConditions
     */
    public List<Condition> getPostConditions()
    {
        return postConditions;
    }

    /**
     * @param postConditions the postConditions to set
     */
    public void setPostConditions(List<Condition> postConditions)
    {
        this.postConditions = postConditions;
    }

    /**
     * @return the basicFlow
     */
    public List<Step> getBasicFlow()
    {
        return basicFlow;
    }

    /**
     * @param basicFlow the basicFlow to set
     */
    public void setBasicFlow(List<Step> basicFlow)
    {
        this.basicFlow = basicFlow;
    }

    /**
     * @return the alterFlow
     */
    public List<Flow> getAlterFlow()
    {
        return alterFlow;
    }

    /**
     * @param alterFlow the alterFlow to set
     */
    public void setAlterFlow(List<Flow> alterFlow)
    {
        this.alterFlow = alterFlow;
    }

}
