package data.misc;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.listeners.DialogCreatorUI;
import com.fs.starfarer.api.impl.campaign.RuleBasedInteractionDialogPluginImpl;

public class TestInterface implements DialogCreatorUI {
    @Override
    public void showDialog(SectorEntityToken target, InteractionDialogPlugin plugin) {
      Object  prevValue = ReflectionUtilis.getPrivateVariable("dialogType", Global.getSector().getCampaignUI());
        ReflectionUtilis.setPrivateVariableFromSuperclass("dialogType", Global.getSector().getCampaignUI(), null);
        Global.getSector().getCampaignUI().showInteractionDialogFromCargo(plugin, target, new CampaignUIAPI.DismissDialogDelegate() {
            @Override
            public void dialogDismissed() {
                ReflectionUtilis.setPrivateVariableFromSuperclass("dialogType", Global.getSector().getCampaignUI(), prevValue);
            }
        });
    }

    @Override
    public void showDialog(SectorEntityToken target, String trigger) {
        RuleBasedInteractionDialogPluginImpl dialog = new RuleBasedInteractionDialogPluginImpl(trigger);
        showDialog(target, dialog);
    }

    @Override
    public void showDialog(StoryPointActionDelegate delegate) {

    }

    @Override
    public void showDialog(float customPanelWidth, float customPanelHeight, CustomDialogDelegate delegate) {
        Object  prevValue = ReflectionUtilis.getPrivateVariable("dialogType", Global.getSector().getCampaignUI());
        ReflectionUtilis.setPrivateVariableFromSuperclass("dialogType", Global.getSector().getCampaignUI(), null);
//        Global.getSector().getCampaignUI().showInteractionDialogFromCargo(new CustomDialogHandler(customPanelWidth,customPanelHeight,delegate), null, new CampaignUIAPI.DismissDialogDelegate() {
//            @Override
//            public void dialogDismissed() {
//                ReflectionUtilis.setPrivateVariableFromSuperclass("dialogType", Global.getSector().getCampaignUI(), prevValue);
//            }
//        });
    }
}
