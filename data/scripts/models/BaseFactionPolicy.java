package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionPolicySpecManager;

public  class BaseFactionPolicy {
    String specId;
    float daysTillPlaced = 0f;

    public float getDaysTillPlaced() {
        return daysTillPlaced;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public void createTooltipDescription(TooltipMakerAPI tooltip){
        tooltip.addPara("WORK IN PROGRESS",Misc.getTooltipTitleAndLightHighlightColor(),5f).setAlignment(Alignment.MID);
    }
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip){

    }
    public void createEffectSectionForFactionInfoTooltip(TooltipMakerAPI tooltip){

    }
    public FactionPolicySpec getSpec(){
        return FactionPolicySpecManager.getSpec(this.specId);
    }
    public void advance(float amount){
        daysTillPlaced += Global.getSector().getClock().convertToDays(amount);

    }
    public void applyPolicyEffectAfterChangeInUI(boolean removing){


    }
    public void applyPolicy(){

    }
    public void unapplyPolicy(){

    }
    public boolean canUsePolicy(){
        return true;
    }
}
