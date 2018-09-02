package Description;

import Description.Service.DescriptionA;

import java.util.*;

/**
 * @author alex_jd
 */
public class DescriptionGen extends DescriptionA {

    private String description = null;
    private List eqpmtInfo;
    private Integer idModel = null;
    private String prefix = null;

    // List eqpmtInfo indexes
    // 0 - id_model, 1 - street name (translit), 2 - home number, 3 - home subnumber, 4 - switch ip
    public DescriptionGen(List eqpmtInfo) {
        this.eqpmtInfo = eqpmtInfo;
        setIdModel();
        setThePrefix();
        makeDescription();
    }

    public String getDescription() {
        return description;
    }

    public Integer getIdModel() {
        return idModel;
    }

    public String getThePrefix() {
        return prefix;
    }

    private void setIdModel() {
        idModel = Integer.valueOf(eqpmtInfo.get(0).toString());
    }

    private void setThePrefix() {
        prefix = ModelsTable.getThePrefix(idModel);
    }

    private String getTheDrob() {
        String drob = "";
        String rawDrob = eqpmtInfo.get(3).toString();
        if(rawDrob != "") {
            drob = "-" + rawDrob;
        }

        return drob;
    }

    private void  makeDescription() {
        if (!prefix.equals("[MBH]")) {
            description = getThePrefix() + "_" + eqpmtInfo.get(1) + "-" + eqpmtInfo.get(2) + "" + getTheDrob() + "-L2";
        } else {
            description = getThePrefix() + "_" + makeSysInfo()+ "_" + eqpmtInfo.get(1) + "-" + eqpmtInfo.get(2) + "" + getTheDrob();
        }



    }

    private String makeSysInfo() {
        String sysInfo = null;
        try {
            snmp.start();
            sysInfo = snmp.get(eqpmtInfo.get(4).toString(), "1.3.6.1.2.1.1.5.0");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                snmp.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sysInfo;
    }


    public static void main(String[] args) {
        //System.out.println("group prefix=" + DescriptionGen.getThePrefix(144));
    }
}
