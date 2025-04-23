package data.scripts;

import com.fs.starfarer.api.campaign.CoreUITabId;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.CoreUITabListener;

public class CoreUiInterceptor implements CoreUITabListener {
    @Override
    public void reportAboutToOpenCoreTab(CoreUITabId tab, Object param) {
        if(param instanceof  String){
            String s = (String) param;
            if(s.equals("income_report")){
                CoreUITrackerScript.setMemFlag("income");
            }
        }
        if(param instanceof MarketAPI){
            CoreUITrackerScript.setMemFlag("colonies");
        }
        CoreUITrackerScript.sendSignalToOpenCore = true;
    }
}
