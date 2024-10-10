package com.example.calculatorproject.level3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Number -> int, double, long등 부모형 타입

//제네릭이 Number type에 맞게 결과 값 가져오기
public class ArithmeticCalculator<T extends Number> {

    private final List<T> resultList = new ArrayList<>();

    public Number calculate(T a, T b, Operation operatorType) {
        return operatorType.apply(a, b);
    }

    // List에서 입력된 값보다 큰 값을 필터링하는 메서드
    public List<Number> getResultsGreaterThan(List<Number> resultList, Number compareVal) {
        List<Number> filteredResults = new ArrayList<>();
        for (Number result : resultList) {
            if (result.doubleValue() > compareVal.doubleValue()) {
                filteredResults.add(result);
            }
        }
        return filteredResults;
    }

    public List<T> getResultList() {
        return resultList;
    }
}
