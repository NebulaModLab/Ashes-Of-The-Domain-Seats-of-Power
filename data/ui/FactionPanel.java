package data.ui;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CustomUIPanelPlugin;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import data.misc.UIDataSop;
import data.ui.factionpolicies.FactionPolicyPanel;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionPanel implements CustomUIPanelPlugin {
    CustomPanelAPI mainPanel;
    CustomPanelAPI panelForPlugins = null;
    CustomPanelAPI buttonPanel = null;
    ButtonAPI currentlyChosen;
    SoundUIManager manager;
    UILinesRenderer renderer;
    HashMap<ButtonAPI, CustomPanelAPI> panelMap = new HashMap<>();
    boolean pausedMusic = true;

    public HashMap<ButtonAPI, CustomPanelAPI> getPanelMap() {
        return panelMap;
    }

    public CustomPanelAPI getMainPanel() {
        return mainPanel;
    }
    public void init(CustomPanelAPI mainPanel, String panelToShowcase, Object data) {
        this.mainPanel = mainPanel;
        renderer = new UILinesRenderer(0f);
        this.panelForPlugins = mainPanel.createCustomPanel(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight() - 45, null);
        if (panelToShowcase == null) {
            panelToShowcase = "timeline";
        }


        UIDataSop.WIDTH = panelForPlugins.getPosition().getWidth();
        UIDataSop.HEIGHT = panelForPlugins.getPosition().getHeight();
        createButtonsAndMainPanels();
        for (Map.Entry<ButtonAPI, CustomPanelAPI> buttons : panelMap.entrySet()) {
            if (buttons.getKey().getText().toLowerCase().contains(panelToShowcase)) {
                currentlyChosen = buttons.getKey();
                break;
            }
        }


        if (currentlyChosen != null) {
            panelForPlugins.addComponent(panelMap.get(currentlyChosen)).inTL(0, 0);
        }

        this.mainPanel.addComponent(panelForPlugins).inTL(0, 35);
        renderer.setPanel(panelForPlugins);
    }
    public void clearUI(boolean clearMusic) {;
        panelMap.clear();
        mainPanel.removeComponent(panelForPlugins);
        if(clearMusic){
            pauseSound();
        }

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

    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
    public void pauseSound() {
        Global.getSoundPlayer().pauseCustomMusic();
        Global.getSoundPlayer().restartCurrentMusic();
        pausedMusic = true;
    }
    public void createButtonsAndMainPanels() {
        ButtonAPI research, megastructures, customProd,sp;
        this.buttonPanel = this.mainPanel.createCustomPanel(mainPanel.getPosition().getWidth(), 25, null);
        UILinesRenderer renderer = new UILinesRenderer(0f);
        CustomPanelAPI panelHelper = this.buttonPanel.createCustomPanel(490, 0.5f, renderer);
//        renderer.setPanel(panelHelper);
        TooltipMakerAPI buttonTooltip = buttonPanel.createUIElement(mainPanel.getPosition().getWidth(), 20, false);
        Color base, bg;
        base = Global.getSector().getPlayerFaction().getBaseUIColor();
        bg = Global.getSector().getPlayerFaction().getDarkUIColor();
        customProd = buttonTooltip.addButton("Policies", null, base, bg, Alignment.MID, CutStyle.TOP, 140, 20, 0f);
        research = buttonTooltip.addButton("Timeline", null, base, bg, Alignment.MID, CutStyle.TOP, 140, 20, 0f);;
        sp = buttonTooltip.addButton("Overview", null, base, bg, Alignment.MID, CutStyle.TOP, 140, 20, 0f);;
        customProd.setShortcut(Keyboard.KEY_R, false);
        research.setShortcut(Keyboard.KEY_T, false);
        sp.setShortcut(Keyboard.KEY_S, false);
        customProd.getPosition().inTL(0, 0);
        research.getPosition().inTL(141, 0);
        sp.getPosition().inTL(282, 0);
        buttonPanel.addUIElement(buttonTooltip).inTL(0, 0);
        buttonPanel.addComponent(panelHelper).inTL(0, 20);
        mainPanel.addComponent(buttonPanel).inTL(0, 10);
        panelForPlugins.addComponent(new FactionPolicyPanel(panelForPlugins.getPosition().getWidth(),panelForPlugins.getPosition().getHeight()).getMainPanel()).inTL(0,0);

    }
}
