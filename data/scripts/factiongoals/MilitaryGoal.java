package data.scripts.factiongoals;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.TimelineEventType;

import java.awt.*;
import java.util.Map;

public class MilitaryGoal extends BaseFactionGoal{
    public MilitaryGoal() {
        super(TimelineEventType.MILITARY);
        this.goals.put("goal_1",100);
        this.goals.put("goal_2",400);
        this.goals.put("goal_3",700);
        this.goals.put("goal_4",1000);
    }
    @Override
    public void createTooltipForSection(String id, TooltipMakerAPI tooltip) {
        float pad = 2f;
        if(id.equals("goal_1")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Unlock policy : Services Guarantees Citizenship", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Unlock policy : Military Academies", pad);
        }
        if(id.equals("goal_2")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Increase ground defence modifier of planet by 1.5", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Gain 150 Faction XP monthly", pad);
        }
        if(id.equals("goal_3")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"All stations will regain combat readiness much quicker", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Gain access to new capital building : Enforcer HQ: they will hunt all luddic path bases and pirate bases, that affect our colonies.", pad);

        }
        if(id.equals("goal_4")){
            tooltip.addPara(BaseIntelPlugin.INDENT+"Unlock policy : Full Surveillance", pad);
            tooltip.addPara(BaseIntelPlugin.INDENT+"Gain one time 10000 Faction XP", pad);
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
        tooltip.addPara("Focus on military advances of faction and it's strength to repel all dangers",5f);
        tooltip.addPara("For each threshold reached, you will get one time reward, ranging from new policies to new industries", Misc.getTooltipTitleAndLightHighlightColor(),5f);
        for (Map.Entry<String, Integer> entry : goals.entrySet()) {
            tooltip.addPara(BaseIntelPlugin.BULLET+"At %s points",10f,FactionManager.getColorForEvent(type),entry.getValue()+"");
            createTooltipForSection(entry.getKey(),tooltip);
        }

    }
}
