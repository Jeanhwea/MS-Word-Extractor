package cn.edu.buaa.sei.rucm;

import cn.edu.buaa.sei.AppConfig;
import cn.edu.buaa.sei.rucm.ds.*;
import cn.edu.buaa.sei.util.ConfigMgr;
import cn.edu.buaa.sei.util.GenericFileIO;
import com.alibaba.fastjson.JSON;

/**
 * Created by hujh on 5/2/16.
 */
public class RucmReader {
    GenericFileIO gfio = null;
    AppConfig conf;

    public RucmReader () {
        conf = ConfigMgr.getConfig();
        gfio = new GenericFileIO();
    }

    public UseCase loadFile(String filename) {
        String text = gfio.read(filename);
        if (null == text) return null;
        return JSON.parseObject(text, UseCase.class);
    }


    public String processFile(String filename) {
        UseCase uc = loadFile(filename);
        String text = rucm2String(uc);
//        String outFileName = conf.getPath2WordOutput() + filename.substring(filename.indexOf("/")+2) + ".txt";
//        gfio.write(text, outFileName);
        return text;
    }

    private String rucm2String(UseCase uc) {
        StringBuilder sb = new StringBuilder();
        int i = 0;

        sb.append(String.format("UseCase  : %s\n", uc.getUseCaseName()));
        i = 0;
        for (Condition cond : uc.getPreConditions()) {
            if (i==0)
                sb.append(String.format("PreCond  : %s\n", cond.getText()));
            else
                sb.append(String.format("         : %s\n", cond.getText()));
            ++i;
        }
        i = 0;
        for (Input input : uc.getInputs()) {
            if (i==0)
                sb.append(String.format("Input:\n          %s\n", input.getText()));
            else
                sb.append(String.format("          %s\n", input.getText()));
            ++i;
        }
        i = 0;
        for (Output output: uc.getOutputs()) {
            if (i==0)
                sb.append(String.format("Output:\n          %s\n", output.getText()));
            else
                sb.append(String.format("          %s\n", output.getText()));
            ++i;
        }
        i = 0;
        for (Step step : uc.getBasicFlow()) {
            if (i==0)
                sb.append(String.format("BasicFlow:\n          %3d. %s\n", ++i, step.getText()));
            else
                sb.append(String.format("          %3d. %s\n", ++i, step.getText()));
        }
        i = 0;
        for (Condition cond : uc.getPostConditions()) {
            if (i==0)
                sb.append(String.format("PostCond : %s\n", cond.getText()));
            else
                sb.append(String.format("         : %s\n", cond.getText()));
            ++i;
        }
        for (Flow flow : uc.getAlterFlow()) {
            String rfs = "RFS ";
            for (Integer iRfs : flow.getBasicFlowSteps())
                rfs += iRfs + ",";
            sb.append(String.format("AlterFlow: %s\n", rfs));
            int j = 0;
            for (Step step : flow.getSteps()) {
                sb.append(String.format("           %3d. %s\n", ++j, step.getText()));
            }
            j = 0;
            for (Condition cond : flow.getPostConditions()) {
                if (j==0)
                    sb.append(String.format("     Post: %s\n", cond.getText()));
                else
                    sb.append(String.format("         : %s\n", cond.getText()));
                ++j;
            }
        }

        sb.append("\n");
        sb.append("\n");

        return sb.toString();
    }
}
