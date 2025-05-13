package data.ui.timeline;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.List;

public class CycleBorderComponent implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    public int cycle;
    SpriteAPI sprite = Global.getSettings().getSprite("rendering","GlitchSquare");
    public CycleBorderComponent(int cycle,int height) {
        this.cycle = cycle;
        mainPanel = Global.getSettings().createCustom(1,height,this);
        TooltipMakerAPI tooltip = mainPanel.createUIElement(300,100,false);
        tooltip.setParaFont(Fonts.ORBITRON_20AABOLD);
        LabelAPI labelAPI = tooltip.addPara(cycle+".c", Color.ORANGE,0f);
        mainPanel.addUIElement(tooltip).inTL(-labelAPI.computeTextWidth(labelAPI.getText())/2,3f);

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
        sprite.setColor(Color.white);
        sprite.setSize(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight());
        sprite.render(mainPanel.getPosition().getX(),mainPanel.getPosition().getY()-20);
    }

    @Override
    public void render(float alphaMult) {

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
