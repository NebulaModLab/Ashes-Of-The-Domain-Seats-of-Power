package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
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
        if(!getSpec().canBeRemoved()){
            tooltip.addPara("This policy can't be removed once in effect!",Misc.getNegativeHighlightColor(),3f).setAlignment(Alignment.MID);
        }
    }
    public boolean canBeRemoved(){
        if(!getSpec().canBeRemoved()){
            return !AoTDFactionManager.getInstance().doesHavePolicyEnabled(specId);
        }
        return true;
    }
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip){
        if(!getSpec().canBeRemoved()){
            tooltip.addPara("This policy can't be removed once in effect!",Misc.getNegativeHighlightColor(),3f);
        }
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
