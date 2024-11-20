package org.cpo.dlmscosem;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DlmsData {
    private DataType dataType;
    private Object value;
    private List<DlmsData> data;

    @Override
    public String toString() {
        String result = "";
        if (dataType != null) {
            result += dataType.name() + ": ";
        }
        result += getValue() + "\n";
        if (data != null && !data.isEmpty()) {
            for (DlmsData child : data) {
                result += indentChildren(child.toString());
            }
        }
        return result;
    }

    private String indentChildren(String child) {
        String result = "";
        String[] children = child.split("\n");
        for (String c : children) {
            result += "  " + c + "\n";
        }
        return result;
    }

    public Object getValue() {
        return value;
    }
}
