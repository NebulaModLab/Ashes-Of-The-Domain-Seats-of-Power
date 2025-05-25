package data.scripts.factiongoals;

import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.managers.AoTDFactionManager;
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
    public void initGrantedGoals(){
        goals.keySet().forEach(x->grantedGoals.put(x,false));
    }
    public void advance(float amount){
        goals.entrySet().stream().filter(x->x.getValue()<= AoTDFactionManager.getInstance().getGoalStat(this.type).getModifiedInt()).forEach(x->grantedGoals.put(x.getKey(),true));
        grantedGoals.entrySet().stream().filter(Map.Entry::getValue).forEach(x->{grantReward(x.getKey());});
    }

    public void createTooltipForSection(String id, TooltipMakerAPI tooltip){

    }
    public String getTitle(){
        return AoTDFactionManager.getStringType(type);
    }
    public BaseFactionGoal (TimelineEventType type){
        this.type = type;
    }




}
