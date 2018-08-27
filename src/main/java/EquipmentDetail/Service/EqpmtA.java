package EquipmentDetail.Service;

import Service.StatConPostDB;

/**
 * @author alex_jd
 */
public abstract class EqpmtA implements Eqpmt, StatConPostDB {

    @Override
    public String getEqpmtDescr() {
        return null;
    }
}
