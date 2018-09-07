package Pattern;

import simpleDB.GetFromDB;
import simpleDB.Service.InterfaceDB;

import java.util.List;
import java.util.Set;

public class InteractDB {

    String nameSQLRequest;
    //String sqlRequest;

    public static InterfaceDB interfaceDB;

    public InteractDB(String nameOfInterfaceDB) {

        if(nameOfInterfaceDB.equals("PostgreSQL")) {
            interfaceDB = new GetFromDB();
        } else {

        }

        this.nameSQLRequest = nameSQLRequest;
    }

    public static Set<List> getFromDB(String nameSQLRequest) {
        String sqlRequest;
        int[] rowNum;
        switch(nameSQLRequest){
            case "ListOfMU": sqlRequest = "select id_switch, ip from switch where id_model = '65' or id_model = '85' or id_model = '90';"; rowNum = new int[]{1,2}; break;
            case "ListOfDU": sqlRequest = "List of DU"; break;
            default: throw new IllegalArgumentException("Invalid name of SQL Request: " + nameSQLRequest);
        }
        return interfaceDB.selectExecute(sqlRequest, rowNum);
    }

}
