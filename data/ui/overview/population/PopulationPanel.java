package data.ui.overview.population;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationPanel implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    CustomPanelAPI contentPanel;
    UILinesRenderer renderer;
    boolean showSectorPopulation = true;
    ButtonAPI sectorData,localData;
    ButtonAPI highlight;
    public PopulationPanel(float width, float height) {
        mainPanel = Global.getSettings().createCustom(width, height, this);
        renderer = new UILinesRenderer(0f);
        renderer.setPanel(mainPanel);
        createUI();
    }

    public void createUI() {
        if (contentPanel != null) {
            mainPanel.removeComponent(contentPanel);
        }
        contentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight(), null);
        TooltipMakerAPI tooltip = contentPanel.createUIElement(contentPanel.getPosition().getWidth(), 30, false);
        tooltip.setTitleOrbitronVeryLarge();
        tooltip.addTitle("Population Data : " + Global.getSector().getPlayerFaction().getDisplayNameLong(), Color.ORANGE).setAlignment(Alignment.MID);
        tooltip.setParaFont(Fonts.ORBITRON_16);
        tooltip.addPara("Combined size of all markets under our control : %s", 10f, Color.ORANGE, (int) FactionManager.getTotalSize(Global.getSector().getPlayerFaction()) + "").setAlignment(Alignment.MID);
        if(showSectorPopulation){
            tooltip.addPara("Combined size of entire sector : %s", 5f, Color.ORANGE, (int) FactionManager.getSizeOfSector() + "").setAlignment(Alignment.MID);

        }
        else{
            tooltip.addPara(" ",5f);

        }
        float chartHeight = 400;
        CustomPanelAPI rowPanel = Global.getSettings().createCustom(contentPanel.getPosition().getWidth(), chartHeight, null);
        PopulationChartPanel panel = new PopulationChartPanel(chartHeight, showSectorPopulation);
        rowPanel.addComponent(panel.getMainPanel()).inTL(rowPanel.getPosition().getWidth() / 2 - (chartHeight / 2), 0);
        tooltip.addCustom(rowPanel, 15f);
        tooltip.addSpacer(10);
        float y = tooltip.getHeightSoFar();

         localData = tooltip.addButton("Local data",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Alignment.MID,CutStyle.TL_BR,250,30,0f);
         sectorData= tooltip.addButton("Sector data",null, Misc.getBasePlayerColor(),Misc.getDarkPlayerColor(),Alignment.MID,CutStyle.TL_BR,250,30,0f);

        localData.getPosition().inTL(5,y);
        sectorData.getPosition().inTL(contentPanel.getPosition().getWidth()-sectorData.getPosition().getWidth()-5,y);

        y+=40;
        CustomPanelAPI content = Global.getSettings().createCustom(contentPanel.getPosition().getWidth(), contentPanel.getPosition().getHeight()-y-5, null);
        TooltipMakerAPI tooltipConent = content.createUIElement(contentPanel.getPosition().getWidth(), content.getPosition().getHeight(), true);


        float contentWidth = contentPanel.getPosition().getWidth();
        float spacing = 5f;
        float itemHeight = 40f;
        float xCursor = 0f;

        CustomPanelAPI row = Global.getSettings().createCustom(contentWidth, itemHeight, null);
        if (showSectorPopulation) {
            for (FactionAPI market : FactionManager.getAllFactionsRelevant()) {
                MarketPopData marketPanel = new MarketPopData(market, itemHeight);
                float panelWidth = marketPanel.getMainPanel().getPosition().getWidth();

                // If it doesn't fit in the current row, start a new one
                if (xCursor + panelWidth > contentWidth) {
                    tooltipConent.addCustom(row, spacing);
                    row = Global.getSettings().createCustom(contentWidth, itemHeight, null);
                    xCursor = 0f;
                }

                row.addComponent(marketPanel.getMainPanel()).inTL(xCursor, 0f);
                xCursor += panelWidth + spacing;
            }
        } else {
            ArrayList<MarketAPI>markets = new ArrayList<MarketAPI>( FactionManager.getMarketsUnderPlayer().stream().sorted((a, b) -> Integer.compare((int) b.getSize(), a.getSize())).toList());
            for (MarketAPI market :markets) {
                MarketPopData marketPanel = new MarketPopData(market, itemHeight);
                float panelWidth = marketPanel.getMainPanel().getPosition().getWidth();

                // If it doesn't fit in the current row, start a new one
                if (xCursor + panelWidth > contentWidth) {
                    tooltipConent.addCustom(row, spacing);
                    row = Global.getSettings().createCustom(contentWidth, itemHeight, null);
                    xCursor = 0f;
                }

                row.addComponent(marketPanel.getMainPanel()).inTL(xCursor, 0f);
                xCursor += panelWidth + spacing;
            }
        }


// Add the last row if it has any content
        if (xCursor > 0f) {
            tooltipConent.addCustom(row, spacing);
        }
        content.addUIElement(tooltipConent);
        tooltip.addCustomDoNotSetPosition(content).getPosition().inTL(0,y);


        contentPanel.addUIElement(tooltip).inTL(0, 0);
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
        renderer.render(alphaMult);
    }

    @Override
    public void advance(float amount) {
        if(sectorData.isChecked()){
            sectorData.setChecked(false);

            showSectorPopulation = true;
            createUI();
            highlight = sectorData;
        }
        if(localData.isChecked()){
            localData.setChecked(false);

            showSectorPopulation = false;
            createUI();
            highlight = localData;
        }
        if(highlight!=null){
            highlight.highlight();
        }
    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
