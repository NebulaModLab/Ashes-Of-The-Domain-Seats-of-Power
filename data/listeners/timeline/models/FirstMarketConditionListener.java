package data.listeners.timeline.models;

import data.scripts.managers.AoTDFactionManager;
import data.scripts.timelineevents.templates.FirstMarketCondition;

public class FirstMarketConditionListener extends BaseOneTimeListener {
    String marketCondition;
    FirstMarketCondition event;
    boolean includePrevWorlds;
    public FirstMarketConditionListener(String memoryFlagToCheck, String marketCondition, FirstMarketCondition event,boolean includePrevColonizedWorlds) {
        super(memoryFlagToCheck+"_"+marketCondition);
        this.marketCondition = marketCondition;
        this.event = event;
        this.includePrevWorlds = includePrevColonizedWorlds;
    }

    @Override
    public void advanceImpl(float amount) {
        AoTDFactionManager.getMarketsUnderPlayer().stream()
                .filter(x->x.hasCondition(marketCondition))
                .filter(x->!x.getPrimaryEntity().getMemoryWithoutUpdate().is("$aotd_was_colonized",true)||includePrevWorlds)
                .findFirst()
                .ifPresent(x -> {
                    event.init(x.getPrimaryEntity().getId(),marketCondition);
                    AoTDFactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });

    }
}
