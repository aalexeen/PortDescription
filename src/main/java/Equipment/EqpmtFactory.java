package Equipment;

import Equipment.Service.EquipmentListing;

/**
 * @author alex_jd
 */
public class EqpmtFactory {

    static public EquipmentListing makeListing(String groupEquipmentName) {
        EquipmentListing eqpmtListing = null;

        if (groupEquipmentName.equals("MU")) {
            eqpmtListing = new EqpmtListMU();
        } else if (groupEquipmentName.equals("DU")) {
            eqpmtListing = new EqpmtListDU();
        }

        return eqpmtListing;
    }

    public static void main(String[] args) {

        EquipmentListing testLinsing = EqpmtFactory.makeListing("MU");

        System.out.println(testLinsing.getIpList());
    }
}
