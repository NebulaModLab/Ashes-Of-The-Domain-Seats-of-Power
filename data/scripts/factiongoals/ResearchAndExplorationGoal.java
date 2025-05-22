package data.scripts.factiongoals;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.TimelineEventType;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class ResearchAndExplorationGoal extends BaseFactionGoal{
    public ResearchAndExplorationGoal() {
        super(TimelineEventType.RESEARCH_AND_EXPLORATION);
        this.goals.put("goal_1",100);
        this.goals.put("goal_2",400);
        this.goals.put("goal_3",700);
        this.goals.put("goal_4",1000);
    }

    @Override
    public void createTooltipForSection(String id, TooltipMakerAPI tooltip) {
        float pad = 2f;
        if(id.equals("goal_1")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Gain access to Nova Exploraria Industry - That will sent survey teams to specified star systems.", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Unlock policy : Research Grants", pad);
        }
        if(id.equals("goal_2")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Unlock policy : Paperclip Initiative", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Gain 100 Faction XP monthly", pad);
        }
        if(id.equals("goal_3")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Nova Exploraria Expeditions can now perform advanced tech-mining operations across the Sector", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Nova Exploraria Expedition cost is reduced by 50%", pad);

        }
        if(id.equals("goal_4")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Increase chance of finding colony items during tech mining operations done by Nova Exploraria", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Gain one time 5000 Faction XP", pad);
            if(Global.getSettings().getModManager().isModEnabled("aotd_vok")){
                tooltip.addPara(BaseIntelPlugin.INDENT+"Unlock new unique capital building : Persean University", pad);
            }
        }
    }
    @Override
    public void grantReward(String id) {
        List<MarketAPI> markets = FactionManager.getMarketsUnderPlayer();
        if(id.equals("goal_1")){
            markets.forEach(x->x.getStability().modifyFlat("aotd_prosperity_1",1,"Reached Property Threshold"));
        }
        else if(id.equals("goal_2")){

        }
        else if(id.equals("goal_3")){
            Global.getSector().getMemoryWithoutUpdate().set("$aotd_logistic_unlocked",true);
        }
        else if(id.equals("goal_4")){
            if(FactionManager.doesHaveMonopolyOverCommodities(30, Commodities.FOOD)){
                markets.forEach(x->x.getStability().modifyFlat("aotd_monopoly_food",2,"Monopoly over Food"));
            }
            else{
                markets.forEach(x->{x.getStability().unmodifyFlat("aotd_monopoly_food");});
            }
            if(FactionManager.doesHaveMonopolyOverCommodities(30, Commodities.DRUGS,Commodities.ORGANS)){
                Global.getSector().getPlayerStats().getDynamic().getStat(
                        Stats.getCommodityExportCreditsMultId(Commodities.DRUGS)).modifyMult("aotd_monopoly_underworld", 1f + 0.5f,"Concierge of Crime");
                Global.getSector().getPlayerStats().getDynamic().getStat(
                        Stats.getCommodityExportCreditsMultId(Commodities.ORGANS)).modifyMult("aotd_monopoly_underworld", 1f + 0.5f,"Concierge of Crime");
            }
            else{
                Global.getSector().getPlayerStats().getDynamic().getStat(
                        Stats.getCommodityExportCreditsMultId(Commodities.DRUGS)).unmodifyMult("aotd_monopoly_underworld");
                Global.getSector().getPlayerStats().getDynamic().getStat(
                        Stats.getCommodityExportCreditsMultId(Commodities.ORGANS)).unmodifyMult("aotd_monopoly_underworld");
            }
        }

    }
    @Override
    public void createTooltip(TooltipMakerAPI tooltip) {
        tooltip.setTitleOrbitronVeryLarge();
        tooltip.addTitle(getTitle()).setAlignment(Alignment.MID);
        Color[]colors = new Color[]{Color.ORANGE,Color.ORANGE};
        tooltip.setParaFont(Fonts.ORBITRON_20AA);
        tooltip.addPara("%s / %s",5f,colors, FactionManager.getInstance().getGoalStat(type).getModifiedInt()+"",""+FactionManager.maxPerCategory).setAlignment(Alignment.MID);
        tooltip.setParaFont(Fonts.DEFAULT_SMALL);
        tooltip.addPara("Focus on exploration and research of unknown",5f);
        tooltip.addPara("For each threshold reached, you will get one time reward, ranging from new policies to new industries", Misc.getTooltipTitleAndLightHighlightColor(),5f);
        for (Map.Entry<String, Integer> entry : goals.entrySet()) {
            tooltip.addPara(BaseIntelPlugin.BULLET+"At %s points",10f,FactionManager.getColorForEvent(type),entry.getValue()+"");
            createTooltipForSection(entry.getKey(),tooltip);
        }

    }
}
