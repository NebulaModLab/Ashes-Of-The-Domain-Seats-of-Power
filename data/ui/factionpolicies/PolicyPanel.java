package data.ui.factionpolicies;

import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.loading.IndustrySpecAPI;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionPolicySpecManager;
import data.scripts.models.BaseFactionPolicy;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.List;

public class PolicyPanel implements ExtendUIPanelPlugin {
    public static int HEIGHT = 200;
    public static int WIDTH = 305;
    ButtonAPI button;
    CustomPanelAPI mainPanel;
    CustomPanelAPI tooltipPanel;
    UILinesRenderer renderer = new UILinesRenderer(0f);
    LabelAPI plusLabel;
    IntervalUtil faderUtil = new IntervalUtil(2f,2f);
    // optimal size : 330 x 260
    boolean addingPanel = false;
    String spec;

    public ButtonAPI getButton() {
        return button;
    }

    public PolicyPanel(boolean addingPanel,String spec) {
        mainPanel = Global.getSettings().createCustom(WIDTH, HEIGHT, this);
        renderer.setPanel(mainPanel);
        this.addingPanel = addingPanel;
        this.spec = spec;
        createUI();

    }

    public void createUI() {
        if (tooltipPanel != null) {
            mainPanel.removeComponent(tooltipPanel);
        }
        tooltipPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth(), mainPanel.getPosition().getHeight(), null);
        TooltipMakerAPI tooltip = tooltipPanel.createUIElement(tooltipPanel.getPosition().getWidth(), tooltipPanel.getPosition().getHeight(), false);
        if(!addingPanel){
            ImageViewer viewer = new ImageViewer(tooltipPanel.getPosition().getWidth(), tooltipPanel.getPosition().getHeight(), Global.getSettings().getSpriteName("aotd_policy_banners","aotd_mil"));
            viewer.setAlphaMult(0.85f);
            tooltip.addCustom(viewer.getComponentPanel(), 0f).getPosition().inTL(0, 0);
        }

        button = tooltip.addAreaCheckbox(null, null, Misc.getBasePlayerColor(), Misc.getDarkPlayerColor(), Misc.getBrightPlayerColor(), tooltipPanel.getPosition().getWidth(), tooltipPanel.getPosition().getHeight(), 0f);
        button.getPosition().inTL(0, 0);
        tooltip.setParaFont(Fonts.ORBITRON_20AA);
        if(!addingPanel){
            BaseFactionPolicy policy = FactionPolicySpecManager.getSpec(spec).getPlugin();
            LabelAPI label = tooltip.addPara(policy.getSpec().getName(), Misc.getTooltipTitleAndLightHighlightColor(), 5f);
            tooltip.setParaFont(Fonts.ORBITRON_12);
            label.getPosition().inTL(0, 2);
            label.setAlignment(Alignment.MID);
            TooltipMakerAPI tool = tooltip.beginSubTooltip(WIDTH-4f);

            policy.createTooltipDescription(tool);

            if(!policy.getSpec().getIncompatiblePolicyIds().isEmpty()){
                tool.addPara("Incompatible with", Misc.getTooltipTitleAndLightHighlightColor(), 10f).setAlignment(Alignment.MID);
                tool.addPara("Testing policy1, Testing policy2", Misc.getNegativeHighlightColor(), 5f).setAlignment(Alignment.MID);
            }
            float effectiveY = Math.abs(label.getPosition().getY());
            LabelAPI labelInfo = tooltip.addPara("Hover over policy for more info",Color.ORANGE,0f);
            labelInfo.setAlignment(Alignment.MID);
            labelInfo.getPosition().inTL(0,tooltipPanel.getPosition().getHeight()-labelInfo.computeTextHeight(labelInfo.getText())-5f);
            tool.getPosition().setXAlignOffset(2f);
            float effectiveY2= (Math.abs(labelInfo.getPosition().getY())-labelInfo.getPosition().getHeight());
            float leftHeight = effectiveY2 - effectiveY;
            float left = (leftHeight-tool.getHeightSoFar()  )/2;
            if(left <=0){
                left  = 0;
            }
            tooltip.addCustomDoNotSetPosition(tool).getPosition().inTL(0,effectiveY+left);

        }
        else{
            tooltip.setParaFont(Fonts.ORBITRON_24AA);
            ImageViewer viewer = new ImageViewer(20,20,Global.getSettings().getSpriteName("ui","aotd_plus"));
            viewer.setColorOverlay(Color.ORANGE);
            tooltip.addCustom(viewer.getComponentPanel(), 0f).getPosition().inTL((WIDTH/2f)-(viewer.getComponentPanel().getPosition().getWidth()/2),(HEIGHT/2f)-(viewer.getComponentPanel().getPosition().getHeight()/2f));
        }
        tooltipPanel.addUIElement(tooltip).inTL(0, 0);
        mainPanel.addComponent(tooltipPanel).inTL(0, 0);

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
        if(addingPanel){
            faderUtil.advance(amount);
            if(faderUtil.intervalElapsed()){
            }
        }
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

    public CustomPanelAPI getTooltipPanel() {
        return tooltipPanel;
    }
}
