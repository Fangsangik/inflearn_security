package com.example.calculatorproject.level3;


//코드 중복이 많은데 해결 방벙?
public enum Operation {
    ADD("+") {
        @Override
        public Number apply(Number a, Number b) {
            if (a instanceof Integer && b instanceof Integer) {
                return a.intValue() + b.intValue();
            } else {
                return a.doubleValue() + b.doubleValue();
            }
        }
    },
    SUBTRACT("-") {
        @Override
        public Number apply(Number a, Number b) {
            if (a instanceof Integer && b instanceof Integer) {
                return a.intValue() - b.intValue();
            } else {
                return a.doubleValue() - b.doubleValue();
            }
        }
    },
    MULTIPLY("*") {
        @Override
        public Number apply(Number a, Number b) {
            if (a instanceof Integer && b instanceof Integer) {
                return a.intValue() * b.intValue();
            } else {
                return a.doubleValue() * b.doubleValue();
            }
        }
    },
    DIVIDE("/") {
        @Override
        public Number apply(Number a, Number b) {
            if (b.doubleValue() == 0) {
                throw new ArithmeticException("0으로 나눌 수 없습니다.");
            }
            if (a instanceof Integer && b instanceof Integer) {
                return a.intValue() / b.intValue();
            } else {
                return a.doubleValue() / b.doubleValue();
            }
        }
    };

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract Number apply(Number a, Number b);
}