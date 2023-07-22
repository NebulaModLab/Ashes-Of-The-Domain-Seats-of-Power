package data.plugins;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import data.Ids.AoDConditions;
import data.Ids.AoDIndustries;
import data.Ids.AodMemFlags;
import data.scripts.campaign.fleets.ExploratoriaRouteManager;
import com.fs.starfarer.api.util.Misc;

public class AshesCapitalPlugin implements EveryFrameScript {
    public boolean firstTick = true;
    public int lastDayChecked = 0;
    public boolean initalized = false;
    public MarketAPI capitalMarket=null;


    private boolean newDay() { //New day check, stolen from VIC mod
        CampaignClockAPI clock = Global.getSector().getClock();
        if (firstTick) {
            lastDayChecked = clock.getDay();
            firstTick = false;
            return false;
        } else if (clock.getDay() != lastDayChecked) {
            lastDayChecked = clock.getDay();
            return true;
        }
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }



    private void exploratoriaInitalization(){
        if(!initalized&&capitalMarket!=null){
            if (capitalMarket.hasIndustry(AoDIndustries.EXPLORATORIA_CORPS)) {
                if (capitalMarket.getIndustry(AoDIndustries.EXPLORATORIA_CORPS).isFunctional()) {
                    for (StarSystemAPI system : Global.getSector().getStarSystems()) {
                        if (system.getTags().equals(Tags.THEME_RUINS) || system.getTags().equals(Tags.THEME_REMNANT_NO_FLEETS) || system.getTags().equals(Tags.THEME_RUINS_MAIN)) {
                            ExploratoriaRouteManager fleets = new ExploratoriaRouteManager(system);
                            system.addScript(fleets);
                        }
                    }
                    initalized=true;
                }
            }
        }
    }
    private void capitalValidation() {
        boolean to_destabilize;
        for (FactionAPI faction : Global.getSector().getAllFactions()) {
            to_destabilize = true;
            if (!faction.getMemory().contains(AodMemFlags.CAPITAL_ENABLED_FOR_FACTION_FLAG)) {
                continue;
            }
            for (MarketAPI market : Misc.getFactionMarkets(faction.getId())) {
                if(market.getPlanetEntity()!=null){
                    if (market.getPlanetEntity().getMemory().is(AodMemFlags.CAPITAL_NAME_FLAG + faction.getId(), true)) {
                        to_destabilize = false;
                        if(faction.isPlayerFaction()){
                            capitalMarket = market;
                        }
                        break;
                    }
                }

            }

            if (to_destabilize) {
                for (MarketAPI market : Misc.getFactionMarkets(faction.getId())) {
                    market.removeCondition(  AoDConditions.REGIONAL_POWER);
                    if(!market.hasCondition(AoDConditions.REGIONAL_POWER_CAPITAL_LOST)){
                        market.addCondition(AoDConditions.REGIONAL_POWER_CAPITAL_LOST);
                    }
                }
            }
            else{
                for (MarketAPI market : Misc.getFactionMarkets(faction.getId())) {
                    if(market.hasCondition(AoDConditions.REGIONAL_POWER_CAPITAL_LOST)){
                        market.removeCondition(AoDConditions.REGIONAL_POWER_CAPITAL_LOST);
                    }
                    if(!market.hasCondition(AoDConditions.REGIONAL_POWER)&&!market.hasCondition(AoDConditions.REGIONAL_POWER_CAPITAL)){
                        market.addCondition(AoDConditions.REGIONAL_POWER);
                    }

                }
            }
        }
    }
    @Override
    public void advance(float amount) {
        if (newDay()) { //Calls once every day, stolen from VIC mod
           capitalValidation();
           exploratoriaInitalization();

        }


    }
}
