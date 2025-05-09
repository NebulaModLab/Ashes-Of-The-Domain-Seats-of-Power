package data.scripts.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CycleTimelineEvents {
    public int recordedCycle;
    public CycleTimelineEvents(int recordedCycle) {
        this.recordedCycle = recordedCycle;
    }
    ArrayList<BaseFactionTimelineEvent>eventsDuringCycle;

    public ArrayList<BaseFactionTimelineEvent> getEventsDuringCycle() {
        return eventsDuringCycle;
    }
    public void addNewEvent(BaseFactionTimelineEvent event) {
        if(event.getCycle()==recordedCycle){
            eventsDuringCycle.add(event);

        }
    }

    public ArrayList<BaseFactionTimelineEvent>getEventsFromMonthDuringCycle(int month){
        ArrayList<BaseFactionTimelineEvent> eventsDuringCycle = (ArrayList<BaseFactionTimelineEvent>)
                this.eventsDuringCycle.stream()
                        .filter(x -> x.getMonth() == month)
                        .collect(Collectors.toCollection(ArrayList::new));

        eventsDuringCycle.sort(Comparator.comparingInt(BaseFactionTimelineEvent::getDay));
        return eventsDuringCycle;

    }
    public ArrayList<BaseFactionTimelineEvent>getEventBasedOnClass(Class<?>clazz){
        return eventsDuringCycle.stream()
                .filter(x -> x.getClass().equals(clazz))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<BaseFactionTimelineEvent>getEventsForCycleSorted(){
        ArrayList<BaseFactionTimelineEvent>events = new ArrayList<>();
        for (int i = 1; i <=12 ; i++) {
            events.addAll(getEventsFromMonthDuringCycle(i));
        }
        return events;
    }
}
