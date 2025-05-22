package data.ui.overview;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.ui.basecomps.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class OverviewShortInfoPanel implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel, contentPanel;
    UILinesRenderer renderer;
    CustomPanelAPI panelOfGatheringPoint;
    ArrayList<ButtonAPI> buttons = new ArrayList<>();
    public boolean recreateUI = false;
    public String current = "pop";

    public OverviewShortInfoPanel(float width, float height) {
        mainPanel = Global.getSettings().createCustom(width, height, this);
        renderer = new UILinesRenderer(0f);
        renderer.setPanel(mainPanel);
        createUI();
    }

    public CustomPanelAPI createGatheringPointBar(float width) {
        UILinesRenderer renderer = new UILinesRenderer(0f);
        panelOfGatheringPoint = Global.getSettings().createCustom(width, 100, renderer);
        TooltipMakerAPI tooltip = panelOfGatheringPoint.createUIElement(width, 100, false);
        tooltip.addSectionHeading("Faction Capital", Alignment.MID, 0f);
        if (!Misc.getPlayerMarkets(true).isEmpty()) {
            if (Global.getSector().getPlayerFaction().getProduction().getGatheringPoint() != null) {
                Pair<CustomPanelAPI, ButtonAPI> pair = AoTDGatehringPointPlugin.getMarketEntitySpriteButton(width - 15f, 75, 75, Global.getSector().getPlayerFaction().getProduction().getGatheringPoint());
                tooltip.addCustom(pair.one, 5f);
                pair.two.setClickable(false);
            }
        }


        panelOfGatheringPoint.addUIElement(tooltip).inTL(0, 0);
        return panelOfGatheringPoint;
    }

    public void createUI() {
        if (contentPanel != null) {
            mainPanel.removeComponent(contentPanel);
        }
        contentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight(), null);
        TooltipMakerAPI tooltip = contentPanel.createUIElement(contentPanel.getPosition().getWidth(), contentPanel.getPosition().getHeight(), false);
        tooltip.addCustom(new FactionFlagButtonComponent(200, 125, true).getPanelOfButton(), 5f).getPosition().inTL(100, 28);
        tooltip.addCustom(new FactionXPPanel(contentPanel.getPosition().getWidth(), 125, false).getMainPanel(), 5f).getPosition().inTL(0, 155);

        TooltipMakerAPI tooltipSub = tooltip.beginSubTooltip(contentPanel.getPosition().getWidth());

        tooltipSub.addSectionHeading("Additional info", Alignment.MID, 5f);

        buttons.add(tooltipSub.addButton("Population", "pop", Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Alignment.MID, CutStyle.TL_BR, contentPanel.getPosition().getWidth() - 10, 30, 10f));
        buttons.add(tooltipSub.addButton("Star Systems", "star", Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Alignment.MID, CutStyle.TL_BR, contentPanel.getPosition().getWidth() - 10, 30, 10f));
        buttons.add(tooltipSub.addButton("Colonies", "colonies", Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Alignment.MID, CutStyle.TL_BR, contentPanel.getPosition().getWidth() - 10, 30, 10f));
        buttons.add(tooltipSub.addButton("Global Market Data", "commodities", Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Alignment.MID, CutStyle.TL_BR, contentPanel.getPosition().getWidth() - 10, 30, 10f));
        tooltip.endSubTooltip();

        tooltip.addCustom(new FactionBonusPanel(contentPanel.getPosition().getWidth(), contentPanel.getPosition().getHeight() - tooltipSub.getHeightSoFar() - tooltip.getHeightSoFar() - 110f, false).getMainPanel(), 5f);
        float y = contentPanel.getPosition().getHeight() - tooltipSub.getHeightSoFar() - 5f;
        tooltip.addCustom(tooltipSub, 0f).getPosition().inTL(-5, y);
        tooltip.addCustom(createGatheringPointBar(contentPanel.getPosition().getWidth()), 5f).getPosition().inTL(-5, y - 105);
        contentPanel.addUIElement(tooltip).inTL(5, 0);
        mainPanel.addComponent(contentPanel);
        //        mainPanel.addComponent(new FactionFlagButtonComponent(130, 130).getPanelOfButton()).inTL(((width - 10) / 2) - 65, height - 130);
//        mainPanel.addComponent(new FactionXPPanel(450, 130).getMainPanel()).inTL(((width - 10) / 2) - 65 - 455, height - 130);
//        mainPanel.addComponent(new FactionBonusPanel(450, 130).getMainPanel()).inTL(((width - 10) / 2) + 70, height - 130);

    }

    @Override
    public CustomPanelAPI getMainPanel() {
        return mainPanel;
    }


    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {

    }

    @Override
    public void render(float alphaMult) {
        renderer.render(alphaMult);
    }

    @Override
    public void advance(float amount) {
        buttons.stream().filter(x->!x.getCustomData().equals(current)).forEach(ButtonAPI::unhighlight);
        buttons.stream().filter(x->x.getCustomData().equals(current)).findFirst().ifPresent(ButtonAPI::highlight);
        for (ButtonAPI button : buttons) {
            if(button.isChecked()){
                button.setChecked(false);
                current = (String) button.getCustomData();
                recreateUI = true;
                break;
            }
        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
