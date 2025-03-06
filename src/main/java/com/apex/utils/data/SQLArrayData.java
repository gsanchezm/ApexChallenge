package com.apex.utils.data;

import com.apex.config.FrameworkException;
import com.apex.utils.SQLUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SQLArrayData extends SQLUtil implements IGetTableArray,IGetTwoDimensionalArray{

    @Override
    public Object[][] getTableArray(String sqlFile, String dbName) {
        try {
            ResultSet resultSet = getResultSet(sqlFile, dbName);
            int colCount = resultSet.getMetaData().getColumnCount();
            resultSet.last();
            resultSet.beforeFirst();

            List<LinkedHashMap<Object,Object>> results = new ArrayList<>();

            while (resultSet.next()){
                LinkedHashMap<Object,Object> resultMap = new LinkedHashMap<>();
                for (int i = 1; i <= colCount; i++) {
                    resultMap.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                }
                results.add(resultMap);
            }
            return asTwoDimensionalArray(results);
        }catch (Exception e){
            throw new FrameworkException(e.getMessage());
        }

    }

    @Override
    public Object[][] asTwoDimensionalArray(List<LinkedHashMap<Object,Object>> interimResults){
        Object[][] results = new Object[interimResults.size()][1];
        int index = 0;
        for (LinkedHashMap<Object,Object> interimResult : interimResults){
            results[index++] = new Object[] {interimResult};
        }
        return results;
    }
}