package data.scripts.industry;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.util.Misc;
import data.Ids.AoDConditions;

public class KaysaarCapital extends BaseIndustry implements MarketImmigrationModifier, EveryFrameScript {
    public static final float IMMIGRATION_BONUS = 10f;
    public static int MIN_SIZE = 20;
    public boolean toosmolfaction=false;
    public boolean toosmollplanet=false;
    public boolean ALREADY_HAVE_CAPITAL=false;


    @Override
    public void apply() {
        super.apply(true);
        }

    @Override
    public void unapply() {
        super.unapply();
    }
    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if (isFunctional()) {
            incoming.add(Factions.INDEPENDENT,IMMIGRATION_BONUS );
            incoming.getWeight().modifyFlat(getModId(), IMMIGRATION_BONUS, getNameForModifier());
        }

    }

    public boolean isAvailableToBuild() {
        int size=0;
        if(market.getFaction().getMemory().contains("$ashesOfDomainCapitalPlugin")){
            if(market.getPlanetEntity()==null){
                return false;
            }
            if(!market.getPlanetEntity().getMemory().is("$ashencapital:"+market.getFactionId(),true)){
                ALREADY_HAVE_CAPITAL= true;

            }
        }
        if(market.getSize()<=5){
            toosmollplanet=true;
        }
        for (MarketAPI factionMarket : Misc.getFactionMarkets(market.getFactionId())) {
            if(factionMarket!=null){
                size+= factionMarket.getSize();
            }

        }
        if(size<MIN_SIZE){
            toosmolfaction=true;

        }
        if(ALREADY_HAVE_CAPITAL||toosmolfaction||toosmollplanet){
            return false;
        }
        return true;


    }


    public String getUnavailableReason() {
        String reason = "";
        if(market.getPlanetEntity()==null){
            return reason+="Capitals can't be built on Stations";
        }
        if(ALREADY_HAVE_CAPITAL){
            reason+=" You already got a capital\n";
            return reason;
        }

        if(toosmollplanet){
            reason +="That plannet is too small for being capital\n";
        }
        if(toosmolfaction) {
            reason += "Your faction have total size of their markets lower than 20\n";
        }
        return reason;


    }
    @Override
    public boolean showWhenUnavailable() {
        if(market.getPlanetEntity()==null){
            return false;
        }
        return true;

    }

    @Override
    public boolean canImprove() {
        return false;
    }

    @Override
    public boolean canInstallAICores() {
        return false;
    }
    @Override
    protected void addRightAfterDescriptionSection(TooltipMakerAPI tooltip, IndustryTooltipMode mode) {
        super.addRightAfterDescriptionSection(tooltip, mode);
        if (IndustryTooltipMode.ADD_INDUSTRY.equals(mode)){
            tooltip.addPara("Building that monument will consolidate yourself as one of the powers to be reckoned with in the Persean Sector.", Misc.getHighlightColor(), 10f);
        }
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }
}
