package data.scripts.factiongoals;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.managers.FactionManager;
import data.scripts.models.TimelineEventType;

import java.util.*;

public class BaseFactionGoal {
    TimelineEventType type;
    public LinkedHashMap<String,Integer> goals = new LinkedHashMap<>();
    public HashMap<String,Boolean>grantedGoals = new HashMap<>();
    public void createTooltip(TooltipMakerAPI tooltip){
    }
    public void grantReward(String id){

    }
    public void advance(float amount){
        goals.entrySet().stream().filter(x->x.getValue()<=FactionManager.getInstance().getGoalStat(this.type).getModifiedInt()).forEach(x->grantedGoals.put(x.getKey(),true));
        grantedGoals.entrySet().stream().filter(Map.Entry::getValue).forEach(x->{grantReward(x.getKey());});
    }

    public void createTooltipForSection(String id, TooltipMakerAPI tooltip){

    }
    public String getTitle(){
        return FactionManager.getStringType(type);
    }
    public BaseFactionGoal (TimelineEventType type){
        this.type = type;
    }




}
