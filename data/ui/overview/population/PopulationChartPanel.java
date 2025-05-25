package data.ui.overview.population;

import ashlib.data.plugins.ui.plugins.UILinesRenderer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import data.scripts.managers.AoTDFactionManager;
import data.ui.basecomps.ExtendUIPanelPlugin;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationChartPanel implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;

    UILinesRenderer renderer = new UILinesRenderer(0f);
    boolean displaySector = false;

    public PopulationChartPanel(float size) {
        mainPanel = Global.getSettings().createCustom(size, size, this);
        renderer = new UILinesRenderer(0f);
        renderer.setPanel(mainPanel);

    }

    public PopulationChartPanel(float size, boolean displaySector) {
        mainPanel = Global.getSettings().createCustom(size, size, this);
        renderer = new UILinesRenderer(0f);
        this.displaySector = displaySector;
        renderer.setPanel(mainPanel);

    }

    @Override
    public CustomPanelAPI getMainPanel() {
        return mainPanel;
    }

    @Override
    public void createUI() {

    }

    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {

    }

    @Override
    public void render(float alphaMult) {
        float centerX = mainPanel.getPosition().getCenterX();
        float centerY = mainPanel.getPosition().getCenterY();
        float radius = mainPanel.getPosition().getWidth() / 2;

        float angleStart = 0f;

        if (!displaySector) {
            List<MarketAPI> markets = new ArrayList<>(AoTDFactionManager.getMarketsUnderPlayer());
            if (markets == null || markets.isEmpty()) return;


            float cumulative = 0f;

            for (int i = 0; i < markets.size(); i++) {
                MarketAPI market = markets.get(i);
                float percent = AoTDFactionManager.getInstance().getPercentageOfPopulationOnMarket(market);
                Color color = AoTDFactionManager.getInstance().getMarketColor(market.getId()).darker();

                if (i == markets.size() - 1) percent = 1f - cumulative; // ensure total = 1.0
                float angleExtent = percent * 360f;

                drawPieSlice(centerX, centerY, radius, angleStart, angleStart + angleExtent, color, alphaMult);
                angleStart += angleExtent;
                cumulative += percent;
            }
        } else {
            List<FactionAPI> factions = new ArrayList<>(AoTDFactionManager.getAllFactionsRelevant());
            // Sort by sector presence descending
            factions.sort((a, b) -> Float.compare(
                    AoTDFactionManager.getPercentageOfFactionInSector(b),
                    AoTDFactionManager.getPercentageOfFactionInSector(a)
            ));

            float cumulative = 0f;

            for (int i = 0; i < factions.size(); i++) {
                FactionAPI faction = factions.get(i);
                float percent = AoTDFactionManager.getPercentageOfFactionInSector(faction);
                Color color = faction.getBaseUIColor().darker();

                if (i == factions.size() - 1) percent = 1f - cumulative;
                float angleExtent = percent * 360f;

                drawPieSlice(centerX, centerY, radius, angleStart, angleStart + angleExtent, color, alphaMult);
                angleStart += angleExtent;
                cumulative += percent;
            }
        }

        drawCircleOutline(centerX, centerY, radius, Color.WHITE, alphaMult);
    }


    private void drawPieSlice(float cx, float cy, float r, float angleStartDeg, float angleEndDeg, Color color, float alphaMult) {
        int segments = 360;
        float alpha = color.getAlpha() / 255f * alphaMult;

        double startRad = Math.toRadians(angleStartDeg);
        double endRad = Math.toRadians(angleEndDeg);
        double angleStep = (endRad - startRad) / segments;

        //  Snap center coordinates to exact floats (optional, helps with precision)
        cx = Math.round(cx * 100f) / 100f;
        cy = Math.round(cy * 100f) / 100f;

        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        //  Use exact center vertex
        GL11.glVertex2f(cx, cy);

        for (int i = 0; i <= segments; i++) {
            double angle = startRad + i * angleStep;
            float x = (float) (cx + Math.cos(angle) * r);
            float y = (float) (cy + Math.sin(angle) * r);
            GL11.glVertex2f(x, y);
        }

        GL11.glEnd();
        GL11.glPopAttrib();
    }


    private void drawCircleOutline(float cx, float cy, float r, Color color, float alphaMult) {
        int numSegments = 360;
        float alpha = color.getAlpha() / 255f * alphaMult;

        GL11.glPushAttrib(GL11.GL_ENABLE_BIT); // Save current state

        // Enable blending and line smoothing for antialiasing
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);

        // Optional: increase line thickness slightly for visibility
        GL11.glLineWidth(1.5f);

        // Disable textures for solid color lines
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, alpha);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        for (int i = 0; i < numSegments; i++) {
            double angle = 2 * Math.PI * i / numSegments;
            float x = (float) (cx + Math.cos(angle) * r);
            float y = (float) (cy + Math.sin(angle) * r);
            GL11.glVertex2f(x, y);
        }
        GL11.glEnd();

        // Restore OpenGL state
        GL11.glPopAttrib();
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
