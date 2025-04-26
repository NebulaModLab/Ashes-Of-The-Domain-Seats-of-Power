package data.ui.basecomps;

import ashlib.data.plugins.ui.models.ProgressBarComponent;
import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;
import java.util.List;

public class FactionBonusPanel implements ExtendUIPanelPlugin{

    public CustomPanelAPI mainPanel;
    public CustomPanelAPI panelForUI;
    UILinesRenderer renderer = new UILinesRenderer(0f);

    public FactionBonusPanel(float width, float height) {
        mainPanel = Global.getSettings().createCustom(width, height, this);
        mainPanel = Global.getSettings().createCustom(width, height, this);
        createUI();
        renderer = new UILinesRenderer(0f);
        renderer.setPanel(mainPanel);

    }

    public void createUI(){
        if(panelForUI!=null){
            mainPanel.removeComponent(panelForUI);
        }

        panelForUI = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        TooltipMakerAPI tooltip = panelForUI.createUIElement(panelForUI.getPosition().getWidth(),panelForUI.getPosition().getHeight(),false);
        float width = panelForUI.getPosition().getWidth();
        tooltip.setTitleFont(Fonts.ORBITRON_24AABOLD);
        LabelAPI label =tooltip.addTitle("Current Effects");
        label.setAlignment(Alignment.MID);


        panelForUI.addUIElement(tooltip).inTL(0,2f);
        mainPanel.addComponent(panelForUI);

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

    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
