package lab3.var_5;

import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
// 该模型有两列
        return 3;
    }

    public int getRowCount() {
// 计算线段开头和结尾之间的点数
// 基于制表步骤
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
// 将 X 值计算为 START_SEGMENT + STEP*ROW_NUMBER
        double x = from + step * row;
        if (col == 0) {
// 如果请求第一列的值，则为X
            return x;
        } else {
// 如果请求的是第2列的值，那么就是这个值
// 多项式
            Double result = 0.0;
// 使用霍纳方案计算某个点的值。
            int k = coefficients.length - 1;
            for (int i = 0; i <= k; i++) {
                double prev = result;
                result = coefficients[i] + x * prev;
            }
            if (col == 1)
                return result;
            else
                return isPrime(result.doubleValue());
        }

    }

    private Boolean isPrime(double a) {
        int res = (int) (a - a % 1);
        if (res <= 1)
            return false;
        for (int i = 2; i < Math.sqrt(a)+1; i++) {
            if (res % i == 0)
                return false;
        }
        return true;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
// 第一列的名称
                return "Значение X";
            case 1:
// 第二列的标题
                return "Значение многочлена";
            default:
// 第三列的名称
                return "Значение простое?";
        }
    }

    public Class<?> getColumnClass(int col) {
// 第一列和第二列都包含 Double 值
        if (col != 2)
            return Double.class;
        else
            return Boolean.class;
    }
}
