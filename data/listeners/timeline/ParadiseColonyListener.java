package data.listeners.timeline;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.listeners.timeline.models.BaseOneTimeListener;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.research_explo.ParadiseColonizationEvent;

public class ParadiseColonyListener extends BaseOneTimeListener {
    public ParadiseColonyListener(String memoryFlagToCheck) {
        super(memoryFlagToCheck);
    }

    @Override
    public void advance(float amount) {
        if(isDone())return;
    }

    @Override
    public void advanceImpl(float amount) {
        if(!isDone()){
            Misc.getFactionMarkets(Factions.PLAYER).stream()
                    .filter(x -> x.getPlanetEntity() != null)
                    .filter(x -> Misc.getPlanetSurveyClass(x.getPlanetEntity()).equals("Class V"))
                    .findFirst()
                    .ifPresent(x -> {
                        BaseFactionTimelineEvent event = new ParadiseColonizationEvent(x.getPlanetEntity().getId());
                        FactionManager.getInstance().addEventToTimeline(event);
                        finish(event);
                    });
        }

    }
}
