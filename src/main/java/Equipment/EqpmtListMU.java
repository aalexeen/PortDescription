package Equipment;

import Equipment.Service.EquipmentListingA;
import EquipmentDetail.EqpmtCurrent;
import EquipmentDetail.Service.Eqpmt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import simpleDB.GetFromDB;
import snmp.SNMPManager1;
import telnet.WriteByTelnet;

import java.util.*;

/**
 * @author alex_jd
 */
public class EqpmtListMU extends EquipmentListingA {

    private String groupEquipmentName;

    // Integer - id_switch, String - ip_switch
    private HashMap<Integer, String> ipList = new HashMap<Integer, String>();
    // Integer - id_switch, Eqpmt - current Equipment object
    private HashMap<Integer, Eqpmt> equipments = new HashMap<Integer, Eqpmt>();
    // String - ip switch, Eqpmt = current Equipment object
    private HashMap<String, Eqpmt> ipWithPorts = new HashMap<String, Eqpmt>();

    private Logger logger = LoggerFactory.getLogger(EqpmtListMU.class);

    public EqpmtListMU() {
        //getDB = new GetFromDB();
        //snmp = new SNMPManager1();
        this.groupEquipmentName = "MU";
        setIpList();
        setEquipments();
    }

    public EqpmtListMU(String groupEquipmentName) {
        //getDB = new GetFromDB();
        //snmp = new SNMPManager1();
        this.groupEquipmentName = groupEquipmentName;
        setIpList();
        setEquipments();
    }

    public String getGroupEquipmentName() {
        return groupEquipmentName;
    }

    public void setGroupEquipmentName(String groupEquipmentName) {
        this.groupEquipmentName = groupEquipmentName;
    }


    public HashMap<Integer, String> getIpList() {
        return ipList;
    }

    public HashMap<Integer, Eqpmt> getEquipments() {
        return equipments;
    }

    public HashMap<String, Eqpmt> getIpWithPorts() {
        return ipWithPorts;
    }


    public void setEquipments() {
        /*Iterator<Integer> idEqpmt = ipList.keySet().iterator();
        while(idEqpmt.hasNext()) {
            Integer idSwitch = idEqpmt.next();
            // Create the EqpmtCurrent object with id_switch parameter
            Eqpmt eqpmtCurrent = new EqpmtCurrent(idSwitch);
            equipments.put(idSwitch, eqpmtCurrent);
        }*/
        ipList = new HashMap<Integer, String>();
        ipList.put(5126, "10.110.5.160");

        for (Map.Entry<Integer, String> entry : ipList.entrySet()) {
            Integer idSwitch = entry.getKey();
            Eqpmt eqpmtCurrent = new EqpmtCurrent(idSwitch);
            //Get the hashmap ipWithPorts for convenient use by telnet
            ipWithPorts.put(entry.getValue(), eqpmtCurrent);
            equipments.put(idSwitch, eqpmtCurrent);
        }


    }

    public void setEquipments(String groupEquipmentName) {
        setIpList(groupEquipmentName);
        setEquipments();
    }


    public void setIpList() {
       makeIpList();
    }

    public void setIpList(String groupEquipmentName) {
        setGroupEquipmentName(groupEquipmentName);
        setIpList();
    }

    private void makeIpList() {
        Set<List> equipFromDB = new HashSet<List>();

        if(groupEquipmentName.equals("MU")) {
            String sqlRequest = "select id_switch, ip from switch where id_model = '65' or id_model = '85' or id_model = '90';";

            // 1 - id_switch, 2 - ip_switch
            equipFromDB = getDB.selectExecute(sqlRequest, 1, 2);

        } else if (groupEquipmentName.equals("DU")) {

        } else if (groupEquipmentName.equals("OPT")) {

        } else {

        }

        ipList = setToMap(equipFromDB);

    }


    private HashMap<Integer, String> setToMap(Set<List> switchesSet) {
        HashMap<Integer, String> result = new HashMap<>();
        for (List switches : switchesSet) {
            logger.debug("id switch = {} ip_switch = {}", switches.get(0), switches.get(1));
            result.put(Integer.valueOf(switches.get(0).toString()), switches.get(1).toString());
        }
        return result;
    }



    public static void main(String[] args) {

        EqpmtListMU eqmptList = new EqpmtListMU("MU");
        Logger logger = LoggerFactory.getLogger(EqpmtListMU.class);
        Profiler profiler = new Profiler("ListMU");

        // https://www.slf4j.org/faq.html#logging_performance
        profiler.start("Start");
        logger.info("getIpList {}", eqmptList.getIpList());
        profiler.start("step 1");
        logger.debug("test");
        logger.info("getIpWithPorts {}", eqmptList.getIpWithPorts());

        //profiler.start("The end");
        WriteByTelnet telnet = new WriteByTelnet("MU", eqmptList);

        profiler.stop().print();
    }

}
