package data.listeners.timeline.models;

import com.fs.starfarer.api.EveryFrameScript;

public abstract class BaseTimelineListener implements EveryFrameScript {

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        advanceImpl(amount);

    }

    public void advanceImpl(float amount) {

    }
}
