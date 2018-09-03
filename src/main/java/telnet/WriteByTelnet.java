package telnet;

import Equipment.Service.EquipmentListing;
import EquipmentDetail.Service.Eqpmt;
import Port.Service.Port;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author alex_jd
 */
public class WriteByTelnet {

    private String login = null;
    private String password = null;

    private String groupEquipmentName;
    //AutomatedTelnetClient telnet;
    //private HashMap<String, Eqpmt> eqmptList = new HashMap<String, Eqpmt>();

    private Logger logger = LoggerFactory.getLogger(WriteByTelnet.class);

    public WriteByTelnet(String groupEquipmentName, EquipmentListing eqmptList) {
        this.groupEquipmentName = groupEquipmentName;
        //setEnviroment();
        //this.eqmptList = eqmptList;
        setProperties();
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
            // Firs cycle - Get the list of equipments by Entry
            for(Map.Entry<String, Eqpmt> entryList : eqmptList.getIpWithPorts().entrySet()) {
                // Get the telnet access using IP address
                AutomatedTelnetClient telnet = setTelnet(entryList.getKey());
                if (telnet != null) {
                    telnet.write("sys");
                    telnet.readUntil("]");
                    Eqpmt eqpmt = entryList.getValue();
                    // Second cycle - Get the ifname and its description
                    try {
                        for (Map.Entry<String, Port> entryEqpmp : eqpmt.getPortsDescr().entrySet() ) {
                            //Get the IFNAME and go to the interface
                            String ifname = entryEqpmp.getKey();
                            logger.debug("ifname {}", ifname);
                            telnet.write("int " + ifname);
                            telnet.readUntil("]");
                            Thread.sleep(500);
                            // Set Description on the switch
                            String description = entryEqpmp.getValue().getPortDescription();
                            if (!description.equals("")) {
                                description = "description " + description;

                            } else {
                                description = "undo description";
                            }
                            logger.debug("description {}", description);
                            telnet.write(description);
                            telnet.readUntil("]");
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    logger.debug("Error: Can't connect to the IP {}", entryList.getKey());
                }
                telnet.disconnect();
            }

        } else if (groupEquipmentName.equals("DU")) {


        } else if (groupEquipmentName.equals("OPT")) {


        } else {

        }
    }

    private AutomatedTelnetClient setTelnet(String ipAddress) {
        AutomatedTelnetClient telnet = null;
        try {
            telnet = new AutomatedTelnetClient(ipAddress, login, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return telnet;
    }

    private void setProperties() {
        final FileInputStream config;
        final Properties properties = new Properties();

        try {
            config = new FileInputStream("src/main/resources/telnet.properties");
            properties.load(config);

            if (groupEquipmentName.equals("MU")) {
                login = properties.getProperty("telnet.mu.login");
                password = properties.getProperty("telnet.mu.password");
            } else if (groupEquipmentName.equals("DU")) {
                login = properties.getProperty("telnet.du.login");
                password = properties.getProperty("telnet.du.password");
            }


        } catch (IOException e) {
            logger.error("Error: properties file telnet.properties is absent {}", e);
        }
    }

}
