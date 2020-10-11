package nure.bulhakov.math;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.BiFunction;

public class Operand implements Serializable {

    private Operand leftOperand;

    private Operand rightOperand;

    private BiFunction<Operand, Operand, BigDecimal> function;

    public Operand(BiFunction<Operand, Operand, BigDecimal> function) {
        this.function = function;
    }

    public BigDecimal apply(){
        return function.apply(leftOperand, rightOperand);
    }

    public void setLeftOperand(Operand leftOperand) {
        this.leftOperand = leftOperand;
    }

    public void setRightOperand(Operand rightOperand) {
        this.rightOperand = rightOperand;
    }
}
