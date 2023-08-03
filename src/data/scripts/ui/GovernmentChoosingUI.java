package data.scripts.ui;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCustomUIPanelPlugin;
import com.fs.starfarer.api.campaign.CustomDialogDelegate;
import com.fs.starfarer.api.campaign.CustomUIPanelPlugin;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.goverments.GovernmentType;
import org.json.JSONException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GovernmentChoosingUI implements CustomDialogDelegate {
    public static final float WIDTH = 600f;
    public static final float HEIGHT = Global.getSettings().getScreenHeight() - 300f;
    public static final float ENTRY_HEIGHT = 200; //MUST be even
    public static final float ENTRY_WIDTH = WIDTH - 5f; //MUST be even
    public static final float CONTENT_HEIGHT = 80;


    public Industry industry;
    public String selected = null;
    public List<ButtonAPI> buttons = new ArrayList<>();

    @Override
    public void createCustomDialog(CustomPanelAPI panel, CustomDialogCallback callback) {
        TooltipMakerAPI panelTooltip = panel.createUIElement(WIDTH, HEIGHT, true);
        panelTooltip.addSectionHeading("Choose Government", Alignment.MID, 0f);
        float opad = 10f;
        float spad = 2f;
        float ySpacer = 30f;
        int index =0;
        buttons.clear();
        try {
            for (GovernmentType govType : GovernmentType.retrieveAllGovernments()) {

                Color baseColor = Misc.getButtonTextColor();
                Color bgColour = Misc.getDarkPlayerColor();
                Color brightColor = Misc.getBrightPlayerColor();
                float adjustedWidth = CONTENT_HEIGHT ;
                CustomPanelAPI researcherInfo = panel.createCustomPanel(ENTRY_WIDTH, ENTRY_HEIGHT + 2f, new ButtonReportingCustomPanel(this));
                TooltipMakerAPI anchor =researcherInfo.createUIElement(ENTRY_WIDTH - adjustedWidth - (3 * opad), CONTENT_HEIGHT, false);
                ButtonAPI areaCheckbox = anchor.addAreaCheckbox("",govType.govId, baseColor, bgColour, brightColor, //new Color(255,255,255,0)
                        ENTRY_WIDTH+15,
                        ENTRY_HEIGHT + 20,
                        0f,
                        true);

                researcherInfo.addUIElement(anchor).inTL(-10,5+ySpacer*index);
                anchor = researcherInfo.createUIElement(ENTRY_WIDTH, CONTENT_HEIGHT, false);
                anchor.addSectionHeading(govType.govName,Alignment.MID,10f);
                anchor.addPara("Perk:" +govType.firstPerkName,Color.ORANGE,10f);
                anchor.addPara(govType.firstPerkPositive,Misc.getStoryBrightColor(),10f);
                anchor.addPara(govType.firstPerkNegative,Misc.getNegativeHighlightColor(),10f);
                anchor.addPara("Perk:" +govType.secondPerkName,Color.ORANGE,10f);
                anchor.addPara(govType.secondPerkPositive,Misc.getStoryBrightColor(),10f);
                anchor.addPara(govType.secondPerkNegative,Misc.getNegativeHighlightColor(),10f);

                buttons.add(areaCheckbox);
                researcherInfo.addUIElement(anchor).inTL(-1,5+ySpacer*index);
                panelTooltip.addCustom(researcherInfo,0f);
                index++;
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        panel.addUIElement(panelTooltip).inTL(0.0F, 5.0F);
    }

    @Override
    public boolean hasCancelButton() {
        return false;
    }

    @Override
    public String getConfirmText() {
        return null;
    }

    @Override
    public String getCancelText() {
        return null;
    }

    @Override
    public void customDialogConfirm() {

    }

    @Override
    public void customDialogCancel() {

    }
    public void reportButtonPressed(Object id) {
        if (id instanceof String) {


        }

        for (ButtonAPI button : buttons) {
            if (button.isChecked() && button.getCustomData() != id) button.setChecked(false);
        }
    }
    @Override
    public CustomUIPanelPlugin getCustomPanelPlugin() {
        return null;
    }
    public static class ButtonReportingCustomPanel extends BaseCustomUIPanelPlugin {
        public GovernmentChoosingUI delegate;

        public ButtonReportingCustomPanel(GovernmentChoosingUI delegate) {
            this.delegate = delegate;
        }

        @Override
        public void buttonPressed(Object buttonId) {
            super.buttonPressed(buttonId);
            delegate.reportButtonPressed(buttonId);
        }
    }
}
