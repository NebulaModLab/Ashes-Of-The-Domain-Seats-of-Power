package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.util.ArrayList;
import java.util.Arrays;

public class MonopolyEvent extends BaseFactionTimelineEvent {
    ArrayList<String> commoditiesForMonopoly;
    TimelineEventType typeOfMonopoly;
    public static int threshold = 30;
    public String id;

    public MonopolyEvent(TimelineEventType typeOfMonopoly, String id, String... commoditiesForMonopoly) {
        this.typeOfMonopoly = typeOfMonopoly;
        this.id = id;
        this.commoditiesForMonopoly = new ArrayList<>(Arrays.stream(commoditiesForMonopoly).toList());
    }

    @Override
    public String getID() {
        return super.getID() + id;
    }

    @Override
    public boolean checkForCondition() {
        MarketAPI market = Global.getSector().getEconomy().getMarketsCopy().get(0);

        return commoditiesForMonopoly.stream().allMatch(x -> market.getCommodityData(x).getCommodityMarketData().getMarketSharePercent(Global.getSector().getPlayerFaction()) > threshold);

    }
}
