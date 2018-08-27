package EquipmentDetail.Service;

import Port.Service.Port;

import java.util.HashMap;

/**
 * @author alex_jd
 */
public interface Eqpmt {

    public String getEqpmtDescr();
    public HashMap<String, Port> getPortsDescr();
}
