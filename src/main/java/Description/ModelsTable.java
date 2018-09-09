package Description;

import Pattern.InteractDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author alex_jd
 */
public class ModelsTable {
    public static Map<Integer, Set> models = new HashMap<>();

    static {
        String sqlRequestMaxPurpose = "select max(purpose) from model;" ;
        // Get the max of purpose and convert into integer.
        Integer maxPurpose = Integer.valueOf(InteractDB.getTheList(InteractDB.getFromDB("MaxPurpose")).iterator().next().toString());

        for (; maxPurpose > 0; maxPurpose--) {
            // Get the Sets of model numbers according to the group number.
            Set<List> modelNumbers = InteractDB.getFromDB("SwitchModelsByGroup", maxPurpose.toString());
            // Put the Sets into the HashMap
            models.put(maxPurpose, modelNumbers);
        }

    }

    // Get the prepared prefix
    public static String getThePrefix(Integer idModel) {
        return generatePrefix(getGroupNumber(idModel));
    }

    // Get the Group Number
    public static Integer getGroupNumber(Integer idModel) {
        for (Map.Entry<Integer, Set> entry : ModelsTable.models.entrySet()) {
            Set<List> set = entry.getValue();
            for(List list : set) {
                if (list.contains(idModel.toString())) {
                    return entry.getKey();
                }
            }
        }

        return 100;
    }

    public static String generatePrefix(Integer groupNumber) {
        switch(groupNumber) {
            case 1: return "CORE";
            case 2: return "BRAS";
            case 3: return "[MBH]";
            case 4: return "";
            case 5: return "[DU]";
            case 6: return "[MN_SBE]_UPS";
            case 7: return "TV-DU";
            case 8: return "[MN_FAMP]_OptAmp";
            case 9: return "[MN_OSW]_OptSWitch";
            case 10: return "TV-CORE-ACTIVE";
            case 11: return "WiFi-BRIDGE";
            case 100: return "model_isn't_binded";
            default: return "";
        }
    }

}
