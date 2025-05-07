package data.scripts.models;

import ashlib.data.plugins.misc.AshMisc;
import com.fs.starfarer.api.Global;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class FactionPolicySpec {
    String id;
    String name;
    FactionPolicyType type;
    String plugin;
    int order;
    boolean canBeRemoved;
    Set<String> incompatiblePolicyIds = new HashSet<>();
    public void setCanBeRemoved(boolean canBeRemoved) {
        this.canBeRemoved = canBeRemoved;
    }

    public Set<String> getIncompatiblePolicyIds() {
        return incompatiblePolicyIds;
    }

    public boolean canBeRemoved() {
        return canBeRemoved;
    }

    public FactionPolicySpec(String id, String name, FactionPolicyType type, String plugin, int order,boolean canBeRemoved,Set<String> incompatiblePolicyIds) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.plugin = plugin;
        this.order = order;
        this.incompatiblePolicyIds = incompatiblePolicyIds;
        this.canBeRemoved = canBeRemoved;
    }

    public int getOrder() {
        return order;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BaseFactionPolicy getPlugin() {
        try {
            BaseFactionPolicy policy = (BaseFactionPolicy) Global.getSettings().getScriptClassLoader().loadClass(plugin).newInstance();
            policy.setSpecId(this.getId());
            return policy;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public FactionPolicyType getType() {
        return type;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(FactionPolicyType type) {
        this.type = type;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }
    public static FactionPolicySpec createSpecFromJson(JSONObject obj)  {
        try {
            String id = obj.getString("id");
            if (id == null || id.isEmpty()) return null;
            String name = obj.getString("name");
            int order = obj.getInt("order");
            String plugin = obj.getString("plugin");
            boolean canBeRemoved = obj.getBoolean("canBeRemoved");
            if(!AshMisc.isStringValid(plugin)){
                plugin = BaseFactionPolicy.class.getName();
            }
            Set<String> incompatiblePolicyIds = new HashSet<>(AshMisc.loadEntries(obj.getString("incompatiblePolicies"),","));
            return new FactionPolicySpec(id, name, FactionPolicyType.valueOf(obj.getString("type")),plugin, order,canBeRemoved,incompatiblePolicyIds);
        }
        catch (Exception e) {
            return null;
        }

    }
    public boolean isIncompatibleWith(String policyId){
        return incompatiblePolicyIds.contains(policyId);
    }
    public boolean removeFromIncompatibility(String policyId){
        return incompatiblePolicyIds.remove(policyId);
    }
    public boolean addIncompatibility(String policyId){
        return incompatiblePolicyIds.add(policyId);
    }
}
