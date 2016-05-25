package cn.edu.buaa.sei.rucm;

import cn.edu.buaa.sei.rucm.ds.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujh on 4/29/16.
 */
public class RucmHelper {

    public static UseCase allocUseCase(String useCaseName) {
        UseCase useCase = new UseCase();
        useCase.setUseCaseName(useCaseName);
        useCase.setPreConditions(new ArrayList<>());
        useCase.setInputs(new ArrayList<>());
        useCase.setOutputs(new ArrayList<>());
        useCase.setBasicFlow(new ArrayList<>());
        useCase.setPostConditions(new ArrayList<>());
        useCase.setAlterFlow(new ArrayList<>());
        return useCase;
    }

    public static Condition allocCondition(String text) {
        Condition condition = new Condition();
        condition.setText(text);
        return condition;
    }

    public static Input allocInput(String text) {
        Input input = new Input();
        input.setText(text);
        return input;
    }

    public static Output allocOutput(String text) {
        Output output = new Output();
        output.setText(text);
        return output;
    }

    public static Step allocStep(String text) {
        Step step = new Step();
        step.setText(text);
        return step;
    }

    public static Flow allocFlow() {
        Flow flow = new Flow();
        flow.setBasicFlowSteps(new ArrayList<>());
        flow.setSteps(new ArrayList<>());
        flow.setPostConditions(new ArrayList<>());
        return flow;
    }

    public static void addInput2UseCase(Input input, UseCase useCase) {
        useCase.getInputs().add(input);
    }

    public static void addOutput2UseCase(Output output, UseCase useCase) {
        useCase.getOutputs().add(output);
    }

    public static void addPreCondition2UseCase(Condition preCondition, UseCase useCase) {
        useCase.getPreConditions().add(preCondition);
    }

    public static void addPostCondition2UseCase(Condition postCondition, UseCase useCase) {
        useCase.getPostConditions().add(postCondition);
    }

    public static void addBasicFlow2UseCase(Step step, List<Step> basicFlow) {
        basicFlow.add(step);
    }

    public static void addAlterFlow2UseCase(Flow flow, List<Flow> alterFlow) {
        alterFlow.add(flow);
    }

    public static void addReferBasicFlow2AlterFlow(Integer rfsBasicFlow, Flow flow) {
        flow.getBasicFlowSteps().add(rfsBasicFlow);
    }

    public static void addStep2AlterFlow(Step step, Flow flow) {
        flow.getSteps().add(step);
    }

    public static void addPostCondition2AlterFlow(Condition postCondition, Flow flow) {
        flow.getPostConditions().add(postCondition);
    }

    public static void appendReferBasicFlow2UseCase(Integer rfsBasicFlow, UseCase useCase) {
        Flow lastAlterFlow = useCase.getAlterFlow().get(useCase.getAlterFlow().size() - 1);
        addReferBasicFlow2AlterFlow(rfsBasicFlow, lastAlterFlow);
    }

    public static void appendStep2UseCase(Step step, UseCase useCase) {
        Flow lastAlterFlow = useCase.getAlterFlow().get(useCase.getAlterFlow().size() - 1);
        addStep2AlterFlow(step, lastAlterFlow);
    }

    public static void appendPostCondition2UseCase(Condition postCondition, UseCase useCase) {
        Flow lastAlterFlow = useCase.getAlterFlow().get(useCase.getAlterFlow().size() - 1);
        addPostCondition2AlterFlow(postCondition, lastAlterFlow);
    }
}
