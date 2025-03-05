package lab3.var_5;

import java.text.DecimalFormat;//
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
   // 应用程序窗口初始大小的常量
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    // 多项式系数数组
    private Double[] coefficients;
    // 用于选择文件的对话框对象
// 该组件最初并未创建，因为可能不需要
// 如果用户不打算将数据保存到文件中
    private JFileChooser fileChooser = null;
    // 菜单元素放置在类数据字段中，因为它们是必要的
// 从不同的地方进行操作
    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem searchInfoMenuItem;
   // 用于读取变量值的输入字段
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult;
    // 表格单元格可视化器
    private GornerTableCellRenderer renderer = new
            GornerTableCellRenderer();
   // 带有计算结果的数据模型
    private GornerTableModel data;
    public MainFrame(Double[] coefficients) {
// 强制调用祖先构造函数
        super("Табулирование многочлена на отрезке по схеме Горнера");
        // 将传递的系数存储在内部字段中
        this.coefficients = coefficients;
// 设置窗口尺寸
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
// 将应用程序窗口置于屏幕中央
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
// 创建菜单
        JMenuBar menuBar = new JMenuBar();
// 设置菜单为应用程序的主菜单
        setJMenuBar(menuBar);
// 将“文件”菜单项添加到菜单中
        JMenu fileMenu = new JMenu("Файл");
// 添加到主菜单
        menuBar.add(fileMenu);
// 创建一个菜单项“Table”
        JMenu tableMenu = new JMenu("Таблица");
// 添加到主菜单
        menuBar.add(tableMenu);
        // 在菜单中添加“帮助”菜单项
        JMenu infoMenu = new JMenu("Справка");
// 添加到主菜单
        menuBar.add(infoMenu);
        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
        public void actionPerformed(ActionEvent event) {
            if (fileChooser==null) {
// 如果尚未创建“打开文件”对话框的实例，
// 然后创建它
                fileChooser = new JFileChooser();
// 并使用当前目录进行初始化
                fileChooser.setCurrentDirectory(new File("."));
            }
// 显示对话框
            if (fileChooser.showSaveDialog(MainFrame.this) ==
                    JFileChooser.APPROVE_OPTION)
// 如果其显示结果成功，
// 将数据保存到文本文件
                saveToTextFile(fileChooser.getSelectedFile());
        }
    };
// 将相应的子菜单项添加到“文件”菜单中
    saveToTextMenuItem = fileMenu.add(saveToTextAction);
// 默认情况下，菜单项不可访问（还没有数据）
saveToTextMenuItem.setEnabled(false);
    // 创建一个新的“动作”以保存到文本文件
    Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
    public void actionPerformed(ActionEvent event) {
        if (fileChooser==null) {
// 如果是对话框实例
//“打开文件”尚未创建，
// 然后创建它
            fileChooser = new JFileChooser();
// 并使用当前目录进行初始化
            fileChooser.setCurrentDirectory(new File("."));
        }
        // 显示对话框
        if (fileChooser.showSaveDialog(MainFrame.this) ==
                JFileChooser.APPROVE_OPTION);
// 如果其显示结果成功，
// 将数据保存到二进制文件
        saveToGraphicsFile(
                fileChooser.getSelectedFile());
    }
};
// 将相应的子菜单项添加到“文件”菜单中
saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
// 默认情况下菜单项可用
saveToGraphicsMenuItem.setEnabled(false);

// 根据程序帮助创建一个新动作
        Action searchInfoAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent event) {
               // String value =
                        JOptionPane.showMessageDialog(MainFrame.this, "Гусева, 9 группа",
                                "Aвтор программы",JOptionPane.INFORMATION_MESSAGE);
            }
        };
// 将相应的子菜单项添加到帮助菜单中
        searchInfoMenuItem = infoMenu.add(searchInfoAction);
// 默认情况下，菜单项不可访问（还没有数据）
        searchInfoMenuItem.setEnabled(true);

// 创建一个新动作来搜索多项式值
Action searchValueAction = new AbstractAction("Найти значение многочлена") {
public void actionPerformed(ActionEvent event) {
// 提示用户输入搜索字符串
    String value =
            JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска",
                    "Поиск значения", JOptionPane.QUESTION_MESSAGE);
// 将输入的值设置为针
    renderer.setNeedle(value);
// 更新表
    getContentPane().repaint();
}
};
// 添加一个动作到“Table”菜单
searchValueMenuItem = tableMenu.add(searchValueAction);
// 默认情况下，菜单项不可访问（还没有数据）
searchValueMenuItem.setEnabled(false);
// 创建一个区域，其中包含段和步骤边界的输入字段
// 创建一个用于输入段左边框的标签
JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
// 创建一个文本字段，用于输入 10 个字符的值
// 默认值0.0
textFieldFrom = new JTextField("0.0", 10);
// 将最大尺寸设置为首选尺寸，以便
// 防止输入字段大小增加
textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
// 创建一个用于输入段左边框的标签
JLabel labelForTo = new JLabel("до:");
// 创建一个文本字段，用于输入 10 个字符的值
// 默认值1.0
textFieldTo = new JTextField("1.0", 10);
// 将最大尺寸设置为首选尺寸，以便
// 防止输入字段大小增加
textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());
// 创建用于输入制表位的标签
JLabel labelForStep = new JLabel("с шагом:");
// 创建一个文本字段，用于输入 10 个字符的值
// 默认值1.0
textFieldStep = new JTextField("0.1", 10);
// 将最大尺寸设置为首选尺寸，以便
// 防止输入字段大小增加
textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());
// 创建容器 1 类型“水平堆叠的盒子”
Box hboxRange = Box.createHorizontalBox();
// 将容器的框架类型设置为“3D”
hboxRange.setBorder(BorderFactory.createBevelBorder(1));
// 添加“胶水”C1-H1
        hboxRange.add(Box.createHorizontalGlue());
// 添加签名“From”
        hboxRange.add(labelForFrom);
// 添加“间隔”C1-H2
hboxRange.add(Box.createHorizontalStrut(10));
// 添加“发件人”输入字段
        hboxRange.add(textFieldFrom);
// 添加“间隔”C1-H3
hboxRange.add(Box.createHorizontalStrut(20));
// 添加“之前”签名
        hboxRange.add(labelForTo);
// 添加“间隔”C1-H4
hboxRange.add(Box.createHorizontalStrut(10));
//添加“之前”输入字段
        hboxRange.add(textFieldTo);
// 添加“间隔”C1-H5
hboxRange.add(Box.createHorizontalStrut(20));
// 添加签名“分步骤”
        hboxRange.add(labelForStep);
// 添加“间隔”C1-H6
hboxRange.add(Box.createHorizontalStrut(10));
// 添加一个字段以输入制表符步骤
        hboxRange.add(textFieldStep);
// 添加“胶水”C1-H7
hboxRange.add(Box.createHorizontalGlue());
// 将首选区域大小设置为双倍
// 最小，以便在布局期间该区域根本不会被挤压
        hboxRange.setPreferredSize(new Dimension(
        new Double(hboxRange.getMaximumSize().getWidth()).intValue(),
new Double(hboxRange.getMinimumSize().getHeight()).intValue()*2));
// 将区域设置为布局的顶部（北部）部分
getContentPane().add(hboxRange, BorderLayout.NORTH);
// 创建一个“计算”按钮
JButton buttonCalc = new JButton("Вычислить");
// 设置按下“计算”的动作并将其绑定到按钮
buttonCalc.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ev) {
        try {
// 读取段首尾的值，步长
            Double from =
                    Double.parseDouble(textFieldFrom.getText());
            Double to =
                    Double.parseDouble(textFieldTo.getText());
            Double step =
                    Double.parseDouble(textFieldStep.getText());
// 根据读取到的数据，创建一个新的表模型实例
                    data = new GornerTableModel(from, to, step,
                    MainFrame.this.coefficients);
// 创建一个新的表实例
            JTable table = new JTable(data);
// 将开发的可视化工具设置为 Double 类的单元格可视化工具
            table.setDefaultRenderer(Double.class,
                    renderer);
// 将表格行大小设置为 30 像素
            table.setRowHeight(30);
// 从容器中移除所有嵌套元素 hBoxResult;
            hBoxResult.removeAll();
// 将表格添加到 hBoxResult，“包裹”在带有滚动条的面板中
            hBoxResult.add(new JScrollPane(table));
// 更新主窗口的内容区域
            getContentPane().validate();
// 将多个菜单项标记为可用
            saveToTextMenuItem.setEnabled(true);
            saveToGraphicsMenuItem.setEnabled(true);
            searchValueMenuItem.setEnabled(true);
        } catch (NumberFormatException ex) {
// 如果有数字转换错误，则显示错误信息
            JOptionPane.showMessageDialog(MainFrame.this,
                    "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
});
// 创建一个“清除字段”按钮
JButton buttonReset = new JButton("Очистить поля");
// 设置点击“清除字段”的动作并将其绑定到按钮
buttonReset.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent ev) {
// 将输入字段设置为默认值
        textFieldFrom.setText("0.0");
        textFieldTo.setText("1.0");
        textFieldStep.setText("0.1");
// 移除 hBoxResult 容器的所有嵌套元素
        hBoxResult.removeAll();
// 添加一个空面板到容器中
        hBoxResult.add(new JPanel());
// 将菜单项标记为不可用
        saveToTextMenuItem.setEnabled(false);
        saveToGraphicsMenuItem.setEnabled(false);
        searchValueMenuItem.setEnabled(false);
// 更新主窗口的内容区域
        getContentPane().validate();
    }
});
// 将创建的按钮放入容器中
Box hboxButtons = Box.createHorizontalBox();
hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
hboxButtons.add(Box.createHorizontalGlue());
// 将首选区域大小设置为最小值的两倍，以便当
// 窗口布局根本没有挤压区域
hboxButtons.setPreferredSize(new Dimension(new
        Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new
Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2));
// 将按钮容器放置在边框布局的底部（南）区域
getContentPane().add(hboxButtons, BorderLayout.SOUTH);
// 显示结果的区域暂时为空
hBoxResult = Box.createHorizontalBox();
hBoxResult.add(new JPanel());
// 将hBoxResult容器设置为边框布局的主要（中心）区域
getContentPane().add(hBoxResult, BorderLayout.CENTER);
}
protected void saveToGraphicsFile(File selectedFile) {
    try {
// 创建一个新的输出字节流，定向到指定文件
        DataOutputStream out = new DataOutputStream(new
                FileOutputStream(selectedFile));
// 将X在某点的值、多项式在某点的值成对写入到输出流
        for (int i = 0; i<data.getRowCount(); i++) {
            out.writeDouble((Double)data.getValueAt(i,0));
            out.writeDouble((Double)data.getValueAt(i,1));
        }
// 关闭输出流
        out.close();
    } catch (Exception e) {
// 这种情况下不需要处理“FileNot Found”异常，
// 因为我们正在创建一个文件，而不是打开它进行读取
    }
}
protected void saveToTextFile(File selectedFile) {
    try {
// 创建一个新的字符输出流，定向到指定文件
        PrintStream out = new PrintStream(selectedFile);
        DecimalFormat formatter = renderer.getFormatter();
// 将头信息写入输出流
        out.println("Результаты табулирования многочлена по схеме Горнера");
                out.print("Многочлен: ");
        for (int i=0; i<coefficients.length; i++) {
            out.print(coefficients[i] + "*X^" +
                    (coefficients.length-i-1));
            if (i!=coefficients.length-1)
                out.print(" + ");
        }
        out.println("");
        out.println("Интервал от " + data.getFrom() + " до " +
                data.getTo() + " с шагом " + data.getStep());
        out.println("====================================================");
// 将points中的值写入输出流
        for (int i = 0; i<data.getRowCount(); i++) {
            out.println("Значение в точке " + formatter.format(data.getValueAt(i,0))
                    + " равно " + formatter.format(data.getValueAt(i,1)));
        }
// 关闭流
        out.close();
    } catch (FileNotFoundException e) {
// “FileNot Found”异常可以忽略
// 进程，因为我们正在创建一个文件，而不是打开它
    }
}
public static void main(String[] args) {
// 如果没有给出命令行参数 -
// 无法继续计算，系数未知
   /* if (args.length==0) {
        System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
        System.exit(-1);
    }*/
// 在系数数组中保留与命令行参数一样多的位置
    Double[] coefficients = new Double[args.length];
    int i = 0;
    try {
// 循环遍历参数，尝试将它们转换为 Double
        for (String arg: args) {
            coefficients[i++] = Double.parseDouble(arg);
        }
    }
    catch (NumberFormatException ex) {
// 如果无法转换，则报告错误并退出
        System.out.println("Ошибка преобразования строки '" +
                args[i] + "' в число типа Double");
        System.exit(-2);
    }
// 创建主窗口的实例，并向其传递系数
    MainFrame frame = new MainFrame(coefficients);
// 设置窗口关闭时执行的操作
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
}
}