package data.scripts.goverments;

import com.fs.starfarer.api.Global;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GovernmentType {
    public String govId;
    public String firstPerkName;
    public String firstPerkPositive;
    public String firstPerkNegative;
    public  String secondPerkName;
    public String secondPerkPositive;
    public String secondPerkNegative;
    public String secondPerkDescrp;
    public String scriptPath;
    public String govName;

    public static List<GovernmentType> retrieveAllGovernments() throws JSONException, IOException {
        if(Global.getSector().getMemory().contains("$aotd_gov_list_initalized")){
            return (ArrayList<GovernmentType>)Global.getSector().getPersistentData().get("$aotd_list_gov");
        }
        insertGovernmentList();
        return (ArrayList<GovernmentType>)Global.getSector().getPersistentData().get("$aotd_list_gov");
    }
    public GovernmentType(String id ,String name,  String firstPerk , String firstPerkPos, String firstPerkNeg, String secondPerk , String secondPerkPos, String secondPerkNeg,String script){
        govId=id;
        firstPerkName=firstPerk;
        secondPerkName=secondPerk;
        firstPerkPositive=firstPerkPos;
        secondPerkPositive = secondPerkPos;
        firstPerkNegative=firstPerkNeg;
        secondPerkNegative = secondPerkNeg;
        scriptPath = script;
        govName = name;
    }
    public static void insertGovernmentList() throws JSONException, IOException {
       JSONArray array =  Global.getSettings().getMergedSpreadsheetDataForMod("id", "data/campaign/governments.csv", "aod_capital");
       List<GovernmentType> toInsert = new ArrayList<GovernmentType>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            String id = jsonObject.getString("id");
            if (id.isEmpty()) {
                continue;
            }
            String first_perk = jsonObject.getString("perk1_name");
            String second_perk = jsonObject.getString("perk2_name");
            String first_perk_pos = jsonObject.getString("perk1_pos");
            String second_perk_pos= jsonObject.getString("perk2_pos");
            String first_perk_neg = jsonObject.getString("perk1_neg");
            String second_perk_neg= jsonObject.getString("perk2_neg");
            String scriptPath =  jsonObject.getString("script");
            String name =  jsonObject.getString("name");
            if(scriptPath==null||scriptPath.isEmpty()){
                continue;
            }
            GovernmentType newGov = new GovernmentType(id,name,first_perk,first_perk_pos,first_perk_neg,second_perk,second_perk_pos,second_perk_neg,scriptPath);
            toInsert.add(newGov);
            Global.getSector().getMemory().set("$aotd_gov_list_initalized",true);
        }
        Global.getSector().getPersistentData().put("$aotd_list_gov",toInsert);
    }

}

