package data.scripts.goverments;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Misc;

import java.util.HashMap;

public abstract class BaseGovernment implements EveryFrameScript,GovermentAPI {

    
    public FactionAPI faction;
    public BaseGovernment (FactionAPI factionAPI, String id){
        this.faction= factionAPI;
        this.GovernmentType = id;
    }

    public String getGovernmentType() {
        return GovernmentType;
    }

    public void setGovernmentType(String governmentType) {
        GovernmentType = governmentType;
    }

    protected String GovernmentType;
    public HashMap<FactionAPI,AoTDDiplomacyStatus> currentFactionDiplomacy;


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

    }

    @Override
    public void DiplomacyAssignRole() {
        for (FactionAPI factionAPI : currentFactionDiplomacy.keySet()) {

        }
    }
    @Override
    public void DeclareWarOnFaction() {

    }

    @Override
    public void GovernmentEffectOnColonyApplier(String marketConditionId) {
        if(faction!=null){
            for (MarketAPI factionMarket : Misc.getFactionMarkets(this.faction)) {
                if(!factionMarket.hasCondition(marketConditionId)){
                    factionMarket.addCondition(marketConditionId);
                }
            }
        }


    }

    @Override
    public void GovernmentEffectOnPlayerFleet() {

    }

    @Override
    public void GovermentEffectsOnSector() {

    }

    @Override
    public String GetGovermentId() {
        return null;
    }

    @Override
    public void SetGovernmentId() {

    }

    @Override
    public void DeclearePeace() {

    }

    @Override
    public boolean CanDecleareWarOnFaction(FactionAPI factionAPI) {
        return false;
    }

    @Override
    public void ImmidiateEffectOfGoverment() {

    }

}
