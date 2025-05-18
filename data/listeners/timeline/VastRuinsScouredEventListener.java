package data.listeners.timeline;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.listeners.timeline.models.BaseOneTimeListener;
import data.scripts.managers.FactionManager;
import data.scripts.timelineevents.research_explo.VastRuinsScouredEvent;

import static com.fs.starfarer.api.impl.campaign.ids.Stats.TECH_MINING_MULT;

public class VastRuinsScouredEventListener extends BaseOneTimeListener {
    public VastRuinsScouredEventListener(String memoryFlagToCheck) {
        super(memoryFlagToCheck);
    }

    @Override
    public void advanceImpl(float amount) {
        FactionManager.getMarketsUnderPlayer().stream()
                .filter(x -> x.hasCondition(Conditions.RUINS_VAST))
                .filter(x -> getTechMiningMult(x) < 0.25f)
                .findFirst()
                .ifPresent(x -> {
                    addEvent(new VastRuinsScouredEvent(x.getPlanetEntity().getId()));
                });
    }

    public float getTechMiningMult(MarketAPI market) {

        MemoryAPI mem = market.getMemoryWithoutUpdate();
        if (mem.contains(TECH_MINING_MULT)) {
            return mem.getFloat(TECH_MINING_MULT);
        }
        return 1f;
    }
}
