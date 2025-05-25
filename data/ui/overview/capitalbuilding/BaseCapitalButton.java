package data.ui.overview.capitalbuilding;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.util.List;

public class BaseCapitalButton implements ExtendUIPanelPlugin {
    public CustomPanelAPI mainPanel;
    public CustomPanelAPI componentPanel;
    public ButtonAPI button;

    public BaseCapitalButton(float width ,float height){
        mainPanel = Global.getSettings().createCustom(width,height,this);
        createUI();

    }
    public void createUI(){
        if(componentPanel!=null){
            mainPanel.removeComponent(componentPanel);
        }
        componentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        TooltipMakerAPI tooltip = componentPanel.createUIElement(componentPanel.getPosition().getWidth(),componentPanel.getPosition().getHeight(),false);
        button = tooltip.addAreaCheckbox("",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Misc.getBrightPlayerColor(),componentPanel.getPosition().getWidth(),componentPanel.getPosition().getHeight(),0f);
        button.getPosition().inTL(0,0);

        createUIImpl(componentPanel,tooltip);
        componentPanel.addUIElement(tooltip).inTL(0,0);
        mainPanel.addComponent(componentPanel).inTL(0,0);
    }
    public void createUIImpl(CustomPanelAPI panel,TooltipMakerAPI tooltip){

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
