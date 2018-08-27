package Equipment.Service;

import EquipmentDetail.Service.Eqpmt;

import java.util.HashMap;

/**
 * @author alex_jd
 */
public interface EquipmentListing {

    public String getEqpmtInfo();

    public HashMap<Integer, String> getIpList();
    public HashMap<Integer, Eqpmt> getEquipments();
    public HashMap<String, Eqpmt> getIpWithPorts();
}
