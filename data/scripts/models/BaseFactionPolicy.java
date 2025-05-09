package data.scripts.models;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.managers.FactionPolicySpecManager;

import java.awt.*;

public  class BaseFactionPolicy {
    String specId;
    float daysTillPlaced = 0f;

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public void createTooltipDescription(TooltipMakerAPI tooltip){
        tooltip.addPara("WORK IN PROGRESS",Misc.getTooltipTitleAndLightHighlightColor(),5f).setAlignment(Alignment.MID);
    }
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip){
        if(FactionManager.getInstance().doesHavePolicyEnabled(this.getSpec().getId())){
            if(FactionManager.getInstance().doesHavePolicyInCopy(this.getSpec().getId())){
                tooltip.addPara("This policy is already in effect for about %s ",5f, Color.ORANGE,Misc.getStringForDays((int) daysTillPlaced));
            }
            else{
                tooltip.addPara("This policy was in effect for about %s ",5f, Color.ORANGE,Misc.getStringForDays((int) daysTillPlaced));
                tooltip.addPara("If left Command UI tab, this policy will unapply!",Misc.getNegativeHighlightColor(),5f);

            }

        }
        else{
            tooltip.addPara("This policy will be in effect once chosen and left Command UI tab ",Color.ORANGE,5f);

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
