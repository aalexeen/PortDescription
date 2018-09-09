package Pattern;

import simpleDB.GetFromDB;
import simpleDB.Service.InterfaceDB;

import java.util.List;
import java.util.Set;

public class InteractDB {

    public static InterfaceDB interfaceDB;

    public InteractDB(String nameOfInterfaceDB) {

        if(nameOfInterfaceDB.equals("PostgreSQL")) {
            interfaceDB = new GetFromDB();
        } else {

        }
    }

    public static Set<List> getFromDB(String nameSQLRequest) {
        String sqlRequest;
        int[] rowNum;
        switch(nameSQLRequest){
            case "ListOfMU": sqlRequest = "select id_switch, ip from switch where id_model = '65' or id_model = '85' or id_model = '90';";
            rowNum = new int[]{1, 2};
            break;
            case "ListOfDU": sqlRequest = "List of DU";
            rowNum = new int[]{1,2};
            break;
            case "MaxPurpose": sqlRequest = "select max(purpose) from model;" ;
            rowNum = new int[]{1};
            break;
            default: throw new IllegalArgumentException("Invalid name of SQL Request: " + nameSQLRequest);
        }
        return selectExecute(sqlRequest, rowNum);
    }

    public static Set<List> getFromDB(String nameSQLRequest, String criteria) {
        String sqlRequest;
        int[] rowNum;
        switch(nameSQLRequest){
            case "ListOfPorts": sqlRequest = "select * from ports where id_sw = " + criteria + ";";
            rowNum = new int[]{1, 5, 8};
            break;
            case "SwitchByIdConn": sqlRequest = "select * from ports where id = " + criteria + ";";
            rowNum = new int[]{1, 2};
            break;
            case "SwitchModelsByGroup": sqlRequest = "select id_model from model where purpose = " + criteria + ";";
            rowNum = new int[]{1};
            break;
            case "SwitchInfo": sqlRequest = "select  sw.id_model, str.translit, hm.num, " +
                    "hm.subnum_t, mod.name, sw.ip " +
                    "from switch sw left join  home hm on sw.id_home=hm.id_home " +
                    "left join street str on hm.id_street = str.id_street " +
                    "left join model mod on sw.id_model = mod.id_model " +
                    "where sw.id_switch = " + criteria + ";";
            rowNum = new int[]{1, 2, 3, 4, 6};
            break;
            default: throw new IllegalArgumentException("Invalid name of SQL Request: " + nameSQLRequest);
        }
        return selectExecute(sqlRequest, rowNum);
    }

    public static List getTheList(Set<List> set) {
        return interfaceDB.getTheList(set);
    }

    private static Set<List> selectExecute (String sqlRequest, int ... rowNum) {
        return interfaceDB.selectExecute(sqlRequest, rowNum);
    }

}
