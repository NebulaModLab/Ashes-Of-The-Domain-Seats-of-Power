package data.ui.timeline;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.util.List;

public class FactionTimelineGoalsPanel implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    UILinesRenderer renderer;

    public FactionTimelineGoalsPanel(float width,float height){

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
