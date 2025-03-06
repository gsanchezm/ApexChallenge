package com.apex.utils.data;

import java.util.LinkedHashMap;
import java.util.List;

public interface IGetTwoDimensionalArray {
    Object[][] asTwoDimensionalArray(List<LinkedHashMap<Object,Object>> results);
}
