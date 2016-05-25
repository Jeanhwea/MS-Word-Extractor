package cn.edu.buaa.sei.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hujh on 4/28/16.
 */
public class StopWatch {
    private long timeStart;
    private List<Long> stopPoints;

    public StopWatch() {
        timeStart = System.currentTimeMillis();
        stopPoints = new ArrayList<>();
    }

    public void clickStopButton() {
        stopPoints.add(new Long(System.currentTimeMillis()));
    }

    public void clickResetButton() {
        timeStart = System.currentTimeMillis();
        stopPoints.clear();
    }

    public long getElapsedTimeInMillis() {
        return System.currentTimeMillis() - timeStart;
    }

    public double getElapsedTimeInSeconds() {
        return this.getElapsedTimeInMillis() / 1000.0;
    }

    public long getStopTime(int nStopPoint) {
        if (nStopPoint > stopPoints.size() || nStopPoint <= 0)
            return -1;
        return stopPoints.get(nStopPoint - 1) - timeStart;
    }
}
