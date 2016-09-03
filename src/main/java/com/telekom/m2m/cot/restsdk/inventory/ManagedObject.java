package com.telekom.m2m.cot.restsdk.inventory;

import com.telekom.m2m.cot.restsdk.util.ExtensibleObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by breucking on 30.01.16.
 */
public class ManagedObject extends ExtensibleObject {

    public ManagedObject() {
        super();
    }

    public ManagedObject(ExtensibleObject extensibleObject) {
        super(extensibleObject);
    }

    public String getId() {
        return (String) anyObject.get("id");
    }

    public String getName() {
        return (String) anyObject.get("name");
    }

    public void setName(String name) {
        anyObject.put("name", name);
    }

    public void setId(String id) {
        anyObject.put("id", id);
    }


    public String getType() {
        return (String) anyObject.get("type");
    }

    public Date getLastUpdated() {
        return (Date) anyObject.get("lastUpdated");
    }

    public ManagedObjectReferenceCollection getChildDevices() {
        if (anyObject.containsKey("childDevices")) {
            return (ManagedObjectReferenceCollection) anyObject.get("childDevices");
        } else {
            return new ManagedObjectReferenceCollection(new ArrayList<ManagedObjectReference>());
        }

    }
}
