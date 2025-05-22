package data.ui.overview.marketdata;

public class UIData {

    public static float width;
    public static float WIDTH_OF_TOTAL_MARKET_VALUE,WIDTH_OF_COMMODITY,WIDTH_OF_MARKET_SHARE,WIDTH_ON_PROFIT,WIDTH_ON_SUPPLIERS;

    public static void recompute(float newWidth){
        width = newWidth;
        WIDTH_OF_TOTAL_MARKET_VALUE = width*0.25f;
        WIDTH_OF_COMMODITY = width*0.30f;
        WIDTH_OF_MARKET_SHARE = width*0.15f;
        WIDTH_ON_PROFIT = width*0.30f;
    }
}
