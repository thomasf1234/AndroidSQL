package com.abstractx1.androidsql;

import java.util.Map;
import java.util.Set;

/**
 * Created by tfisher on 22/01/2017.
 */

public class ColumnInfo {
    private Map<String, String> columnNameTypeNameMap;

    public ColumnInfo(Map<String, String> columnNameTypeNameMap) {
        this.columnNameTypeNameMap = columnNameTypeNameMap;
    }

    public Set<String> getColumnNames() {
        return columnNameTypeNameMap.keySet();
    }

    public String getTypeName(String columnName) {
        return columnNameTypeNameMap.get(columnName);
    }
}
