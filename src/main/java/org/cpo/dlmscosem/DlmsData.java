package org.cpo.dlmscosem;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.io.BaseEncoding;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class DlmsData {
    private Object value;
    private DataType dataType;
    private List<DlmsData> data;

    @Override
    public String toString() {
        String result = "";
        result += (dataType != null ? dataType.name() : value.getClass().getSimpleName()) + ": ";
        if (dataType != null && dataType == DataType.OCTET_STRING) {
            result += new String((byte[]) getValue()) + BaseEncoding.base16().encode((byte[]) getValue());
        } else {
            result += getValue() + "\n";
        }
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
