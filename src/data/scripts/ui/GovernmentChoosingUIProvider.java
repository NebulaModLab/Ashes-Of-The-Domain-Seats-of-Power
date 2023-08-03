package data.scripts.ui;

import com.fs.starfarer.api.campaign.CustomDialogDelegate;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.listeners.DialogCreatorUI;
import com.fs.starfarer.api.campaign.listeners.IndustryOptionProvider;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GovernmentChoosingUIProvider implements IndustryOptionProvider {
    public static Object CUSTOM_PLUGIN = new Object();
    @Override
    public List<IndustryOptionData> getIndustryOptions(Industry ind) {
        List<IndustryOptionData> result = new ArrayList<IndustryOptionData>();
        IndustryOptionData opt = new IndustryOptionData("Choose Government", CUSTOM_PLUGIN, ind, this);
        opt.color = new Color(212, 65, 65, 255);
        result.add(opt);
        return result;
    }

    @Override
    public void createTooltip(IndustryOptionData opt, TooltipMakerAPI tooltip, float width) {

    }

    @Override
    public void optionSelected(IndustryOptionData opt, DialogCreatorUI ui) {
        if (opt.id == CUSTOM_PLUGIN) {
            CustomDialogDelegate delegate = new GovernmentChoosingUI();
            ui.showDialog(GovernmentChoosingUI.WIDTH, GovernmentChoosingUI.HEIGHT, delegate);
        }
    }

    @Override
    public void addToIndustryTooltip(Industry ind, Industry.IndustryTooltipMode mode, TooltipMakerAPI tooltip, float width, boolean expanded) {

    }
}
