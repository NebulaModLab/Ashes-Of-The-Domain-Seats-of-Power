package data.scripts.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.Ids.AoDConditions;
import data.Ids.AoDIndustries;
import data.plugins.AoDCapitalUtilis;

public class KaysaarForbiddenCity extends BaseIndustry {
    public static int MIN_SIZE = 20;
    public static float BASE_BONUS = 200f;
    public boolean toosmolfaction=false;
    public boolean toosmollplanet=false;
    public boolean ALREADY_HAVE_CAPITAL=false;
    protected transient SubmarketAPI saved_open = null;
    protected transient SubmarketAPI saved_black = null;
    public void setCapital(){
        if(!market.getPlanetEntity().getMemory().contains("$regional_capital")&&!market.hasCondition(AoDConditions.REGIONAL_POWER_CAPITAL)){
            market.getFaction().getMemory().set("$ashesOfDomainCapitalPlugin",true);
            market.getPlanetEntity().getMemory().set("$ashencapital:"+market.getFactionId(),true);
            market.getPlanetEntity().getMemory().set("$regional_capital",true);
            market.addCondition(AoDConditions.REGIONAL_POWER_CAPITAL);
        }
    }

    @Override
    public void apply() {

        super.apply(true);
        if(isBuilding()){
            if(market.hasIndustry("commerce")){
                market.removeIndustry("commerce",null,false);
                SubmarketAPI open_trade = market.getSubmarket(Submarkets.SUBMARKET_OPEN);
                if(open_trade!=null){
                    market.removeSubmarket(Submarkets.SUBMARKET_OPEN);
                }

            }
            if(market.hasIndustry("underworld")){
                market.removeIndustry("underworld",null,false);
                SubmarketAPI black_trade = market.getSubmarket(Submarkets.SUBMARKET_OPEN);
                if(black_trade!=null){
                    market.removeSubmarket(Submarkets.SUBMARKET_BLACK);
                }
            }
        }
        if (isFunctional() && market.isPlayerOwned()) {
            SubmarketAPI open_trade = market.getSubmarket(Submarkets.SUBMARKET_OPEN);
            SubmarketAPI black_trade = market.getSubmarket(Submarkets.SUBMARKET_BLACK);
            if (open_trade == null) {
                if (saved_open != null) {
                    market.addSubmarket(saved_open);
                } else {
                    market.addSubmarket(Submarkets.SUBMARKET_OPEN);
                    SubmarketAPI sub = market.getSubmarket(Submarkets.SUBMARKET_OPEN);
                    sub.setFaction(Global.getSector().getFaction(Factions.INDEPENDENT));
                    Global.getSector().getEconomy().forceStockpileUpdate(market);
                }


            }
            if (black_trade == null) {
                if (saved_black != null) {
                    market.addSubmarket(saved_black);
                } else {
                    market.addSubmarket(Submarkets.SUBMARKET_BLACK);
                    SubmarketAPI sub = market.getSubmarket(Submarkets.SUBMARKET_BLACK);
                    sub.setFaction(Global.getSector().getFaction(Factions.PIRATES));
                    Global.getSector().getEconomy().forceStockpileUpdate(market);
                }
            }

        }
        if (!isFunctional()&&!market.isFreePort()) {
            if (market.isPlayerOwned()) {
                SubmarketAPI black = market.getSubmarket(Submarkets.SUBMARKET_BLACK);
                saved_black = black;
                market.removeSubmarket(Submarkets.SUBMARKET_BLACK);

            }
        }
        if(!isFunctional()){
            unapply();
        }

        market.getIncomeMult().modifyPercent(getModId(0), BASE_BONUS, getNameForModifier());
    }
    @Override
    protected void buildingFinished() {
        super.buildingFinished();
        setCapital();
    }
    @Override
    public void unapply() {
        super.unapply();
        if (market.isPlayerOwned()) {
            SubmarketAPI open = market.getSubmarket(Submarkets.SUBMARKET_OPEN);
            saved_open = open;
//			if (open.getPlugin() instanceof BaseSubmarketPlugin) {
//				BaseSubmarketPlugin base = (BaseSubmarketPlugin) open.getPlugin();
//				if (base.getSinceLastCargoUpdate() < 30) {
//					savedCargo = open.getCargo();
//				}
//			}
            market.removeSubmarket(Submarkets.SUBMARKET_OPEN);
        }
        if (market.isPlayerOwned()) {
            SubmarketAPI black = market.getSubmarket(Submarkets.SUBMARKET_BLACK);
            saved_black = black;
            market.removeSubmarket(Submarkets.SUBMARKET_BLACK);

        }
        market.removeSubmarket(Submarkets.SUBMARKET_BLACK);
        market.getIncomeMult().unmodifyPercent(getModId(0));
    }

    public boolean isAvailableToBuild() {
      boolean canBuild = AoDCapitalUtilis.canBuildCapital(this.market);
        if(market.hasIndustry("commerce")||market.hasIndustry(AoDIndustries.UNDERWORLD)){
          return false;
        }
        return canBuild;

    }

    public String getUnavailableReason() {
        String toReturn =  AoDCapitalUtilis.getUnavailableReasonForCapital(this.market);
        if(market.hasIndustry("commerce")||market.hasIndustry(AoDIndustries.UNDERWORLD)){
            return toReturn + "\nYou can't build Forbidden city if there is Commerce or Underworld on the planet";
        }
        return toReturn;

    }


    public String getImproveMenuText() {
        return "Change Visual";
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


}
