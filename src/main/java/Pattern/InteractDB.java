package Pattern;

import simpleDB.Service.InterfaceDB;

import java.util.List;
import java.util.Set;

public class InteractDB {

    public static InterfaceDB interfaceDB;


    public InteractDB(InterfaceDB interfaceDB) {
        this.interfaceDB = interfaceDB;
    }

    public Set<List> getFromDB (String sqlRequest, int ... rowNum) {

        return interfaceDB.selectExecute(sqlRequest, rowNum);
    }
}
