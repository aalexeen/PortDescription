package telnet;

import Equipment.Service.EquipmentListing;
import EquipmentDetail.Service.Eqpmt;
import Port.Service.Port;

import javax.swing.text.html.parser.Entity;
import java.util.Map;

/**
 * @author alex_jd
 */
public class WriteByTelnet {

    String server = null;
    String user = null;
    String password = null;

    String groupEquipmentName;
    //AutomatedTelnetClient telnet;

    public WriteByTelnet(String groupEquipmentName, EquipmentListing eqmptList) {
        this.groupEquipmentName = groupEquipmentName;
        //setEnviroment();
        //setTelnet();
        writeDescription(eqmptList);
    }

    /*private void setEnviroment() {
        if (groupEquipmentName.equals("MU")) {
            this.user = "test";
            this.password = "test";

        } else if (groupEquipmentName.equals("DU")) {

        } else if (groupEquipmentName.equals("OPT")) {

        } else {

        }
    }*/

    private void writeDescription(EquipmentListing eqmptList) {
        if (groupEquipmentName.equals("MU")) {
            this.user = "test";
            this.password = "test";
            // Firs cycle - Get the list of equipments by Entry
            for(Map.Entry<String, Eqpmt> entryList : eqmptList.getIpWithPorts().entrySet()) {
                // Get the telnet access using IP address
                AutomatedTelnetClient telnet = setTelnet(entryList.getKey());
                if (telnet != null) {
                    telnet.write("sys");
                    telnet.readUntil("]");
                    Eqpmt eqpmt = entryList.getValue();
                    // Second cycle - Get the ifname and its description
                    for (Map.Entry<String, Port> entryEqpmp : eqpmt.getPortsDescr().entrySet() ) {
                        //Get the IFNAME and go to the interface
                        telnet.write("int " + entryEqpmp.getValue());
                        telnet.readUntil("]");
                        // Set Description on the switch
                        telnet.write("descrip " + entryEqpmp.getValue().getPortDescription());
                        telnet.readUntil("]");
                    }
                } else {
                    System.out.println("Can't connect to the ip " + entryList.getKey());
                }
                telnet.disconnect();
            }

        } else if (groupEquipmentName.equals("DU")) {
            this.user = "test";
            this.password = "test";

        } else if (groupEquipmentName.equals("OPT")) {
            this.user = "test";
            this.password = "test";

        } else {

        }
    }

    private AutomatedTelnetClient setTelnet(String ipAddress) {
        AutomatedTelnetClient telnet = null;
        try {
            telnet = new AutomatedTelnetClient(ipAddress, user,	password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return telnet;
    }

}
