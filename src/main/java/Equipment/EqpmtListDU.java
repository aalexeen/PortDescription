package Equipment;

import Equipment.Service.EquipmentListingA;
import EquipmentDetail.Service.Eqpmt;

import java.util.HashMap;

/**
 * @author alex_jd
 */
public class EqpmtListDU  extends EquipmentListingA {
    @Override
    public HashMap<Integer, String> getIpList() {
        return null;
    }

    @Override
    public HashMap<Integer, Eqpmt> getEquipments() {
        return null;
    }

    @Override
    public HashMap<String, Eqpmt> getIpWithPorts() {
        return null;
    }
}
