package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.managers.FactionPolicySpecManager;

public  class BaseFactionPolicy {
    String specId;
    float daysTillPlaced = 0f;

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public void createTooltipDescription(TooltipMakerAPI tooltip){
        tooltip.addPara("Gain %s on all worlds", 10f, Misc.getPositiveHighlightColor(), "+3 Stability").setAlignment(Alignment.MID);
        tooltip.addPara("Decrease size of fleets by %s", 3f, Misc.getNegativeHighlightColor(), "50%").setAlignment(Alignment.MID);

    }
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip){

    }
    public FactionPolicySpec getSpec(){
        return FactionPolicySpecManager.getSpec(this.specId);
    }
    public void advance(float amount){
        daysTillPlaced += Global.getSector().getClock().convertToDays(amount);

    }
    public void applyPolicy(){

    }
    public void unapplyPolicy(){

    }
    public boolean canUsePolicy(){
        boolean canUse = true;
        for (String incompatiblePolicyId : getSpec().getIncompatiblePolicyIds()) {
            if(FactionManager.getInstance().doesHavePolicyEnabled(incompatiblePolicyId)){
                canUse = false;
                break;
            }
        }
        return canUse;
    }
}
