package org.cpo.dlmscosem.cosem;

import java.util.List;

import org.cpo.dlmscosem.Obis;

import lombok.Data;

@Data
public class CosemObject {
    private int classId;
    private Obis obisCode;
    private List<Integer> properties;
    private List<Integer> actions;
}
