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

import java.util.ArrayList;
import java.util.List;

public class FactionCycleShowcase implements ExtendUIPanelPlugin {

    static class FactionCycleColumnData{
        ArrayList<BaseFactionTimelineEvent>eventsInSameDayAndMonth = new ArrayList<>();
        int day;
        int month;
        public FactionCycleColumnData(int day, int month) {
            this.day = day;
            this.month = month;
        }
        public boolean match(int day, int month) {
            return this.day == day && this.month == month;
        }

    }
    CustomPanelAPI mainPanel;
    CustomPanelAPI contentPanel;
    CycleTimelineEvents events;
    public static float spacerBetweenEvents = 20f;
    public static float spacerBetweenLines = 50f;
    public static float spacerBetweenEventsInOneLine = 20f;
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
        tooltip.addCustom(new CycleBorderComponent(events.recordedCycle, (int) contentPanel.getPosition().getHeight()-85).getMainPanel(),15);
        float currX = spacerBetweenLines;
        ArrayList<FactionCycleColumnData>columns = new ArrayList<FactionCycleColumnData>();
        for (BaseFactionTimelineEvent event : events.getEventsForCycleSorted()) {
            FactionCycleColumnData data =columns.stream().filter(x->x.match(event.getDay(),event.getMonth())).findFirst().orElse(new FactionCycleColumnData(event.getDay(),event.getMonth()));
            data.eventsInSameDayAndMonth.add(event);
            if(columns.stream().noneMatch(x->x.match(data.day,data.month))){
                columns.add(data);
            }

        }
        int maxSize = calculateMAXItemsInRow();
        for (FactionCycleColumnData column : columns) {
            int currSize = column.eventsInSameDayAndMonth.size();
            int index = 0;

            while (currSize >0){
                int picked = Math.min(maxSize, currSize);
                if(picked==0)break;
                CustomPanelAPI row = Global.getSettings().createCustom(NoticeableEventComponent.width,calculateHeightForRow(picked),null);
                float currY = 0;
                for (int i = 0; i < picked; i++) {
                    row.addComponent(new NoticeableEventComponent(column.eventsInSameDayAndMonth.get(index)).getMainPanel()).inTL(0,currY);
                    currY+=NoticeableEventComponent.height+spacerBetweenEventsInOneLine;
                    index++;
                }
                tooltip.addCustom(row,0f).getPosition().inTL(currX,contentPanel.getPosition().getHeight()/2-(row.getPosition().getHeight()/2));
                currX+=NoticeableEventComponent.width+spacerBetweenEvents;
                currSize-=picked;
            }

        }
        columns.forEach(x->x.eventsInSameDayAndMonth.clear());
        columns.clear();

//        for (BaseFactionTimelineEvent event :events.getEventsForCycleSorted() ) {
//            tooltip.addCustom(new NoticeableEventComponent(event).getMainPanel(), 0f).getPosition().inTL(currX,contentPanel.getPosition().getHeight()/2-(NoticeableEventComponent.height/2));
//            currX+=NoticeableEventComponent.width+spacerBetweenEvents;
//        }
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
    public int calculateHeightForRow(int items){
        if(items<=0)return 0;
        return (int) (NoticeableEventComponent.height*items+((items-1)*spacerBetweenEventsInOneLine));
    }
    public int calculateMAXItemsInRow(){
        float effectiveHeight = contentPanel.getPosition().getHeight()-100f;
        int amount = 0;
        while (effectiveHeight>NoticeableEventComponent.height){
            amount++;
            effectiveHeight-=NoticeableEventComponent.height-spacerBetweenEventsInOneLine;
        }
        return amount;
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
