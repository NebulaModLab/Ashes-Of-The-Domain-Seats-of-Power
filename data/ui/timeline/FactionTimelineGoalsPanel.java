package data.ui.timeline;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.util.List;

public class FactionTimelineGoalsPanel implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    UILinesRenderer renderer;
    CustomPanelAPI panelOfContent;
    public FactionTimelineGoalsPanel(float width,float height){
        mainPanel = Global.getSettings().createCustom(width,height,this);
        renderer = new UILinesRenderer(0f);
        renderer.setPanel(mainPanel);
        createUI();
    }
    public void createUI(){
        if(panelOfContent!=null){
            mainPanel.removeComponent(panelOfContent);
        }
        panelOfContent = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        TooltipMakerAPI tooltip = panelOfContent.createUIElement(panelOfContent.getPosition().getWidth(),panelOfContent.getPosition().getHeight(),true);
        tooltip.setTitleOrbitronVeryLarge();
        tooltip.addTitle("Faction Goals").setAlignment(Alignment.MID);
        tooltip.setParaFont(Fonts.ORBITRON_20AA);
        LabelAPI label = tooltip.addPara("WORK IN PROGRESS",5f);
        label.setAlignment(Alignment.MID);
        label.getPosition().inTL(0,panelOfContent.getPosition().getHeight()/2-(label.computeTextHeight(label.getText())/2));
        panelOfContent.addUIElement(tooltip).inTL(0,0);
        mainPanel.addComponent(panelOfContent);
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
