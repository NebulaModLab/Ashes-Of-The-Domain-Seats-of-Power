package data.ui.factionpolicies;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionPolicy;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FactionCurrentPoliciesListPanel implements ExtendUIPanelPlugin {
    ArrayList<PolicyPanel> panels = new ArrayList<>();
    CustomPanelAPI mainPanel;
    CustomPanelAPI tooltipPanel;
    CustomPanelAPI headerPanel;
    TooltipMakerAPI tooltip;
    float offset = 0f;

    public FactionCurrentPoliciesListPanel(float width, float height) {
        mainPanel = Global.getSettings().createCustom(width, height, this);
        createUI();
    }

    public void createUI() {
        panels.clear();

        if (tooltipPanel != null) {
            mainPanel.removeComponent(tooltipPanel);
            mainPanel.removeComponent(headerPanel);
            tooltip = null;
        }
        FactionManager manager = FactionManager.getInstance();
        List<BaseFactionPolicy> chosenPolicies = new ArrayList<>(manager.getCurrentFactionPolicies());
        int policiesChosen = chosenPolicies.size();
        float panelWidth = mainPanel.getPosition().getWidth();
        float panelHeight = mainPanel.getPosition().getHeight();

        headerPanel = Global.getSettings().createCustom(panelWidth,30,null);
        TooltipMakerAPI tooltipHeader = headerPanel.createUIElement(panelWidth,30,false);
        tooltipHeader.setParaFont(Fonts.ORBITRON_24AABOLD);
        tooltipHeader.addPara("Faction Policies ( %s / %s )",2f, Misc.getTooltipTitleAndLightHighlightColor(), Color.ORANGE,""+chosenPolicies.size(),""+manager.getAvailablePolicies().getModifiedInt()).setAlignment(Alignment.MID);

        tooltipPanel = Global.getSettings().createCustom(panelWidth, panelHeight-30, null);
        tooltip = tooltipPanel.createUIElement(panelWidth, panelHeight-30, true);

      ;


        int amountOfItems = Math.max(manager.getAvailablePolicies().getModifiedInt(), policiesChosen);
        int amountOfPlusItems = amountOfItems - policiesChosen;

// Fill the remaining items with `null` to represent empty placeholders
        for (int i = 0; i < amountOfPlusItems; i++) {
            chosenPolicies.add(null); // `null` represents an empty policy slot
        }

        float separator = 5f;
        int itemsPerRow = calculateMaxAmountOfItems(panelWidth, separator);

        int created = 0;
        while (created < amountOfItems) {
            int itemsThisRow = Math.min(itemsPerRow, amountOfItems - created);
            float currX = 0;

            float widthT = calculateTotalWidthOfItems(itemsThisRow, separator);
            CustomPanelAPI container = Global.getSettings().createCustom(panelWidth, PolicyPanel.HEIGHT, null);
            CustomPanelAPI row = Global.getSettings().createCustom(widthT, PolicyPanel.HEIGHT, null);

            for (int i = 0; i < itemsThisRow; i++) {
                BaseFactionPolicy policy = chosenPolicies.get(created); // Chosen or null
                PolicyPanel item = new PolicyPanel(policy==null, policy);
                row.addComponent(item.getMainPanel()).inTL(currX, 0);
                currX += PolicyPanel.WIDTH + separator;
                created++;
            }

            container.addComponent(row).inTL((panelWidth - widthT) / 2, 0f);
            tooltip.addCustom(container, 0f);
            tooltip.addSpacer(10f);
        }
        chosenPolicies.clear();

        tooltipPanel.addUIElement(tooltip).inTL(0, 0);
        headerPanel.addUIElement(tooltipHeader).inTL(0,0);

        mainPanel.addComponent(tooltipPanel).inTL(0, 30);
        mainPanel.addComponent(headerPanel).inTL(0,0);
    }


    public static int calculateMaxAmountOfItems(float width, float separator) {
        float objectWidth = PolicyPanel.WIDTH;

        // One item takes up its width, and every item after the first needs a separator
        int count = 0;
        float totalUsed = 0;

        while (true) {
            float nextItemWidth = objectWidth;
            if (count > 0) {
                nextItemWidth += separator; // separator only after the first item
            }

            if (totalUsed + nextItemWidth > width) {
                break;
            }

            totalUsed += nextItemWidth;
            count++;
        }

        return count;
    }
    public static float calculateTotalWidthOfItems(int itemCount, float separator) {
        if (itemCount <= 0) return 0;

        float objectWidth = PolicyPanel.WIDTH;

        // Total width is the sum of all item widths plus separators between them
        float totalWidth = (itemCount * objectWidth) + ((itemCount - 1) * separator);

        return totalWidth;
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

    @Override
    public CustomPanelAPI getMainPanel() {
       return mainPanel;
    }
}


