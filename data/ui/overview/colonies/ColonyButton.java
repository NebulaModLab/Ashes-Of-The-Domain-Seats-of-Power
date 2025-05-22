package data.ui.overview.colonies;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.util.List;

public class ColonyButton implements ExtendUIPanelPlugin {
    MarketAPI market;
    Object originalColonyPanel;
    CustomPanelAPI mainPanel;
    ButtonAPI button;
    public static float height =50;

    public ColonyButton(MarketAPI market, Object originalColonyPanel,float width) {
        mainPanel = Global.getSettings().createCustom(width,height,this);
        this.market = market;
        this.originalColonyPanel = originalColonyPanel;

    }
    @Override
    public CustomPanelAPI getMainPanel() {
        return null;
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
