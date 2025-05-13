package data.ui.timeline;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.misc.ReflectionUtilis;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.CycleTimelineEvents;
import data.ui.basecomps.ExtendUIPanelPlugin;
import data.ui.basecomps.RightMouseInterceptor;
import data.ui.basecomps.RightMouseTooltipMoverV2;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class FactionTimelineViewerComponent implements ExtendUIPanelPlugin {
    public CustomPanelAPI mainPanel;
    CustomPanelAPI dummy;
    UILinesRenderer renderer;
    RightMouseTooltipMoverV2 mover;
    TooltipMakerAPI tooltip;
    CustomPanelAPI content;
    RightMouseInterceptor interceptor = new RightMouseInterceptor();
    ButtonAPI left, right;
    float currX = 0;

    public FactionTimelineViewerComponent(float width, float height) {
        renderer = new UILinesRenderer(0f);
        mainPanel = Global.getSettings().createCustom(width, height, this);
        interceptor = new RightMouseInterceptor();
        renderer.setPanel(mainPanel);
        init();
    }

    public void init() {
        UILinesRenderer renderer = new UILinesRenderer(0f);
        dummy = Global.getSettings().createCustom(mainPanel.getPosition().getWidth() * 3, mainPanel.getPosition().getHeight(), renderer);
        CustomPanelAPI blocker = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight(), interceptor);
        renderer.setPanel(dummy);
        tooltip = mainPanel.createUIElement(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight(), true);
        tooltip.addSpacer(mainPanel.getPosition().getHeight() * 2);
        createUI();

        tooltip.addCustom(dummy, 0f).getPosition().inTL(0, 0);

        mainPanel.addUIElement(tooltip).inTL(0, 0);
        interceptor.setPanelPos(blocker);
        mainPanel.addComponent(blocker).inTL(0, 0);


        ;
        mover = new RightMouseTooltipMoverV2();
        mover.init(dummy, mainPanel);
        tooltip.setHeightSoFar(0f);
        mover.setBorders(-dummy.getPosition().getWidth() + mainPanel.getPosition().getWidth(), 0);
        mover.setCurrOffset(0f);
        if (tooltip.getExternalScroller() != null) {
            ReflectionUtilis.invokeMethodWithAutoProjection("setMaxShadowHeight", tooltip.getExternalScroller(), 10);
            ReflectionUtilis.invokeMethodWithAutoProjection("setShowScrollbars", tooltip.getExternalScroller(), false);
        }
    }

    public void createUI() {
        if (content != null) {
            dummy.removeComponent(content);
        }
        content = Global.getSettings().createCustom(dummy.getPosition().getWidth(), dummy.getPosition().getHeight(), null);
        TooltipMakerAPI tooltip = content.createUIElement(content.getPosition().getWidth(), content.getPosition().getHeight(), false);
        tooltip.addCustom(new DashLinePanel(dummy.getPosition().getWidth(),dummy.getPosition().getHeight()).getMainPanel(),0f).getPosition().inTL(0,0);
        CycleTimelineEvents events = new CycleTimelineEvents(206);
        CycleTimelineEvents second = new CycleTimelineEvents(207);
        events.addNewEvent(new BaseFactionTimelineEvent("Colonized new world",206, MathUtils.getRandomNumberInRange(1,31),MathUtils.getRandomNumberInRange(1,12)));
        events.addNewEvent(new BaseFactionTimelineEvent("Colonized new world",206, MathUtils.getRandomNumberInRange(1,31),MathUtils.getRandomNumberInRange(1,12)));
        events.addNewEvent(new BaseFactionTimelineEvent("Colonized new world",206, MathUtils.getRandomNumberInRange(1,31),MathUtils.getRandomNumberInRange(1,12)));
        events.addNewEvent(new BaseFactionTimelineEvent("Colonized new world",206, MathUtils.getRandomNumberInRange(1,31),MathUtils.getRandomNumberInRange(1,12)));

        FactionCycleShowcase showcase = new FactionCycleShowcase(events,content.getPosition().getHeight());
        FactionCycleShowcase showcase2 = new FactionCycleShowcase(second,content.getPosition().getHeight());
        tooltip.addCustom(showcase.getMainPanel(),0f).getPosition().inTL(25,0);
        tooltip.addCustom(showcase2.getMainPanel(),0f).getPosition().inTL(showcase.getMainPanel().getPosition().getWidth()+FactionCycleShowcase.spacerBetweenEvents,0);
        content.addUIElement(tooltip).inTL(0, 0);
        dummy.addComponent(content).inTL(0, 0);
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

        mover.advance(amount);
    }



    @Override
    public void processInput(List<InputEventAPI> events) {
        events.stream().filter(x -> !x.isConsumed() && x.getEventValue() == Keyboard.KEY_H).findFirst().ifPresent(x -> {
            mover.moveBy(-mainPanel.getPosition().getWidth());
        });
    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
