package org.cpo.dlmscosem.cosem.property;

import org.cpo.dlmscosem.cosem.CosemObjectProperty;

public enum DataProperty implements CosemObjectProperty {
    LOGICAL_NAME(1, "Logical Name"),
    VALUE(2, "Value");

    private int id;
    private String label;

    private DataProperty(int id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
