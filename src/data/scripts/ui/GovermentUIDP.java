package data.scripts.ui;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;

import java.util.Map;

public class GovermentUIDP implements InteractionDialogPlugin {
    public InteractionDialogAPI dialog;

    static enum OptionID {
        INIT,
        LEAVE
    }

    @Override
    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        dialog.hideVisualPanel();
        dialog.hideTextPanel();
        dialog.setPromptText("");
        dialog.setOpacity(0.95f);

        optionSelected(null, OptionID.INIT);
    }

    @Override
    public void optionSelected(String optionText, Object optionData) {

        if (optionData == OptionID.INIT) {
            //this is where the size of the panel is set, automatically centered
            dialog.showCustomVisualDialog(1224,
                    844,
                    new GoveremntUIDelegate(GovermentUIPlugin.createDefault(), dialog));
        }
    }

    @Override
    public void optionMousedOver(String optionText, Object optionData) {

    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public void backFromEngagement(EngagementResultAPI battleResult) {

    }

    @Override
    public Object getContext() {
        return null;
    }

    @Override
    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }
}
