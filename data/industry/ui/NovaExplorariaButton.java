package data.industry.ui;

import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.dialogs.PlanetSurveyChooseDialog;
import data.industry.NovaExploraria;
import data.ui.overview.capitalbuilding.BaseCapitalButton;

import java.awt.*;

public class NovaExplorariaButton extends BaseCapitalButton {
    public NovaExploraria industry;
    public NovaExplorariaButton(float width, float height,NovaExploraria exploraria) {
        super(width, height);
        this.industry = exploraria;
    }
    public ButtonAPI surveyFleetButton,techHunterPatrol;


    @Override
    public void createUIImpl(CustomPanelAPI panel, TooltipMakerAPI tooltip) {
        if(industry!=null) {
            tooltip.setParaFont(Fonts.ORBITRON_16);
            button.setClickable(false);
            button.setHighlightBrightness(0f);
            button.setGlowBrightness(0f);
            button.setHighlightBounceDown(false);
            button.setMouseOverSound(null);
            tooltip.addPara(industry.getCurrentName(),0f).getPosition().inTL(5,5);
            ImageViewer viewer = new ImageViewer(190,95,industry.getSpec().getImageName());
            tooltip.addCustom(viewer.getComponentPanel(),5f).getPosition().inTL(5,25);

            float width = panel.getPosition().getWidth()-200;
            float effectiveWidth = width/2;
            TooltipMakerAPI tl = tooltip.beginSubTooltip(width/2);
            tl.setParaFont(Fonts.ORBITRON_16);
            tl.addPara("Survey fleets",0f).setAlignment(Alignment.MID);
            industry.maxAmountOfExpeditions.modifyFlat("bonus",2);
            LabelAPI label = tl.addPara("%s / %s",5f, Color.ORANGE,industry.amountOfExpeditions+"",industry.getMaxAmountOfExpeditions().getModifiedInt()+"");
            label.setAlignment(Alignment.MID);
            surveyFleetButton= tl.addButton("Sent Survey Fleet",null,Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Alignment.MID, CutStyle.NONE,250,40,5f);
            surveyFleetButton.getPosition().belowMid((UIComponentAPI) label,5);

            tooltip.endSubTooltip();
            TooltipMakerAPI tl2= tooltip.beginSubTooltip(width/2);
            tl2.setParaFont(Fonts.ORBITRON_16);
            tl2.addPara("Tech Hunter fleets",0f).setAlignment(Alignment.MID);
            label = tl2.addPara("%s / %s",5f, Color.ORANGE,"1","3");
            label.setAlignment(Alignment.MID);

            techHunterPatrol = tl2.addButton("Sent Tech Hunter Fleet",null,Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Alignment.MID, CutStyle.NONE,250,40,5f);
            techHunterPatrol.getPosition().belowMid((UIComponentAPI) label,5);

            tooltip.endSubTooltip();
            tooltip.addCustom(tl,0f).getPosition().inTL(200,5);
            tooltip.addCustom(tl2,0f).getPosition().inTL(200+(width/2),5);
        }
    }

    @Override
    public void advance(float amount) {
        super.advance(amount);
        if(surveyFleetButton.isChecked()){
            surveyFleetButton.setChecked(false);
            Global.getSector().getCampaignUI().showInteractionDialogFromCargo(new PlanetSurveyChooseDialog(), null, new CampaignUIAPI.DismissDialogDelegate() {
                @Override
                public void dialogDismissed() {
                    createUI();
                }
            });
        }
    }
}
