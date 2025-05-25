package data.ui.overview;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.industry.NovaExploraria;
import data.ui.basecomps.ExtendUIPanelPlugin;
import data.ui.basecomps.FactionBonusPanel;
import data.ui.basecomps.FactionFlagButtonComponent;
import data.ui.basecomps.FactionXPPanel;
import data.ui.overview.capitalbuilding.BaseCapitalButton;
import data.ui.overview.marketdata.FactionAllData;
import data.ui.overview.marketdata.FactionMarketData;
import data.ui.overview.marketdata.UIData;
import data.ui.overview.population.PopulationChartPanel;
import data.ui.overview.population.PopulationPanel;
import data.ui.timeline.FactionTimelineGoalsPanel;
import data.ui.timeline.FactionTimelineViewerComponent;

import java.util.List;

public class OverviewPanel implements ExtendUIPanelPlugin {
    float goalsWidth = 400;
    CustomPanelAPI mainPanel;
    OverviewShortInfoPanel shortInfoPanel;
    String current;
    CustomPanelAPI currentPanel;
    PopulationPanel populationPanel;
    FactionAllData  commodityData;
    public OverviewPanel(float width, float height) {
        mainPanel = Global.getSettings().createCustom(width, height, this);
        createUI();
    }

    public void initalizeCurrentPanel() {
        if (currentPanel != null) {
            mainPanel.removeComponent(currentPanel);
        }
        else{
            populationPanel =  new PopulationPanel(mainPanel.getPosition().getWidth() - 410, mainPanel.getPosition().getHeight());
            commodityData =  new FactionAllData(mainPanel.getPosition().getWidth() - 410, mainPanel.getPosition().getHeight());


        }
        if (current.equals("pop")) {
            currentPanel =populationPanel.getMainPanel();
        }
        if (current.equals("star")) {

        }
        if (current.equals("colonies")) {

        }
        if (current.equals("commodities")) {
            currentPanel =commodityData.getMainPanel();
        }
        populationPanel.createUI();
        commodityData.createUI();
        mainPanel.addComponent(currentPanel).inTL(405, 0);
    }

    public void createUI() {
        float width = mainPanel.getPosition().getWidth();
        float height = mainPanel.getPosition().getHeight();

        float widthT = width * 0.5f - 10f;
        float heightT = height - 160;

        if(shortInfoPanel==null){
            shortInfoPanel = new OverviewShortInfoPanel(400, height);
            current = shortInfoPanel.current;
        }


        mainPanel.addComponent(shortInfoPanel.getMainPanel()).inTL(0, 0);
        BaseCapitalButton button = NovaExploraria.getNova().createButton(width-410,130);
        button.createUI();

        mainPanel.addComponent(button.getMainPanel()).inTL(405,0);

        UIData.recompute(width - 410);
//        initalizeCurrentPanel();

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
        if (shortInfoPanel.recreateUI) {
            shortInfoPanel.recreateUI = false;
            this.current = shortInfoPanel.current;
            initalizeCurrentPanel();
        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
