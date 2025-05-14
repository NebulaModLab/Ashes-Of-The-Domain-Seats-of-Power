package data.ui.timeline;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.CycleTimelineEvents;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.util.List;

public class FactionCycleShowcase implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    CustomPanelAPI contentPanel;
    CycleTimelineEvents events;
    public static float spacerBetweenEvents = 20f;
    public static float spacerBetweenLines = 50f;
    public float generatedWidth;

    public float getGeneratedWidth() {
        return generatedWidth;
    }

    public FactionCycleShowcase(CycleTimelineEvents timeline, float height) {
        events = timeline;
        float width = calculateUILength();
        mainPanel = Global.getSettings().createCustom(width,height,this);
        createUI();
    }
    public void createUI(){
        if(contentPanel!=null){
            mainPanel.removeComponent(contentPanel);
        }
        contentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),null);
        TooltipMakerAPI tooltip = contentPanel.createUIElement(mainPanel.getPosition().getWidth(),mainPanel.getPosition().getHeight(),false);
        tooltip.addCustom(new CycleBorderComponent(events.recordedCycle, (int) contentPanel.getPosition().getHeight()-60).getMainPanel(),15);
        float currX = spacerBetweenLines;

        for (BaseFactionTimelineEvent event :events.getEventsForCycleSorted() ) {
            tooltip.addCustom(new NoticeableEventComponent(event).getMainPanel(), 0f).getPosition().inTL(currX,contentPanel.getPosition().getHeight()/2-(NoticeableEventComponent.height/2));
            currX+=NoticeableEventComponent.width+spacerBetweenEvents;
        }
        generatedWidth = currX;
        contentPanel.addUIElement(tooltip).inTL(0,0);
        mainPanel.addComponent(contentPanel);

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
    public float calculateUILength(){
        float width = spacerBetweenLines*2;

        for (BaseFactionTimelineEvent baseFactionTimelineEvent : events.getEventsForCycleSorted()) {
            width+=NoticeableEventComponent.width+spacerBetweenEvents;
        }
        width-=spacerBetweenEvents;
        return width;

    }
}
