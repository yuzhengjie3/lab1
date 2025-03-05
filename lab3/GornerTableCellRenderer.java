package lab3.var_5;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    // 我们寻找字符串表示与needle匹配的单元格
//（用针）。使用大海捞针的比喻，在角色中
// 干草堆 - 表
    private String needle = null;
    private DecimalFormat formatter =
            (DecimalFormat)NumberFormat.getInstance();
    public GornerTableCellRenderer() {
// 只显示小数点后5位
        formatter.setMaximumFractionDigits(5);
// 不要使用分组（即不要分隔数千个
// 既不是逗号也不是空格），即将数字显示为“1000”
// 不是“1,000”或“1,000”
        formatter.setGroupingUsed(false);
// 将小数点分隔符设置为点而不是
// 逗号。默认情况下，在区域设置中
// 俄罗斯/白俄罗斯，小数部分用逗号分隔
        DecimalFormatSymbols dottedDouble =
                formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
// 将标签放置在面板内
        panel.add(label);
// 将标题对齐到面板的左边缘
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public  DecimalFormat getFormatter(){
    return formatter;}

    // 通过输入计算本身的结果来查找多项式的值
//    public Component getTableCellRendererComponent(JTable table,
//                                                   Object value, boolean isSelected, boolean hasFocus, int row, int col) {
//// 使用格式化程序将双精度数转换为字符串
//        String formattedDouble = formatter.format(value);
//// 将标签的文本设置为等于数字的字符串表示形式
//        label.setText(formattedDouble);
//        if (col==1 && needle!=null && needle.equals(formattedDouble)) {
//// 列号 = 1（即第二列）+ 针不为空
////（意味着我们正在寻找某些东西）+
//// 针的值与表格单元格的值匹配 -
//// 将面板背景设置为红色
//            panel.setBackground(Color.RED);
//        } else {
//// 否则 - 为正常白色
//            panel.setBackground(Color.WHITE);
//        }
//        return panel;
//    }

  //通过输入相应的 X 求多项式的值
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row, int col) {
// 使用格式化程序将双精度数转换为字符串
           String formattedDouble = formatter.format(value);
// 将标签文本设置为等于数字的字符串表示形式
            label.setText(formattedDouble);
         Object xValue = table.getValueAt(row, 0);
         String formattedX = formatter.format(xValue);
         if (col == 1 && needle != null && needle.equals(formattedX)) {
             panel.setBackground(Color.RED); }
         else {
             panel.setBackground(Color.WHITE); }



           // 检查字符串中的所有字符是否为 1、3 或 5
            if (col == 1 ) {
                String formattedRemainder = formattedDouble.contains(".") ? formattedDouble.split("\\.")[1]: "";
                for (char part : formattedRemainder.toCharArray()) {
                    if (part != '1' && part != '3' && part != '5') {
                        if (!panel.getBackground().equals(Color.RED))
                        panel.setBackground(Color.WHITE);
                        break;
                    }
                    else
                        if (!panel.getBackground().equals(Color.RED))
                    panel.setBackground(Color.CYAN);
                }
            }
         return panel;
        }

    public void setNeedle(String needle) {
        this.needle = needle;
        System.out.println("Needle set to: " + needle); // 调试信息
    }
}
