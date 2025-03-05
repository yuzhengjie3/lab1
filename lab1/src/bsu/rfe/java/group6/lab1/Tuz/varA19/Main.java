package bsu.rfe.java.group6.lab1.Tuz.varA19;

public class Main {
    public static void main(String args[]) {
        Food[] breakfast = new Food[20];
        int count = 0;

        // 从命令行参数创建食物对象
        for (String arg : args) {
            String[] parts = arg.split("/");
            if (parts[0].equals("Cheese")) {
                breakfast[count] = new Cheese(); // 直接创建 Cheese 对象
            } else if (parts[0].equals("Apple")) {
                breakfast[count] = new Apple(parts[1]);
            } else if (parts[0].equals("IceCream")) {
                breakfast[count] = new IceCream(parts[1]);
            }
            count++;
        }

        // 统计 Cheese 的数量
        int CheeseCount = countProductsByType(breakfast, new Cheese());
        System.out.println("Количество мороженого в меню: " + CheeseCount); // 输出Cheese的数量

        // 遍历并消费食物
        for (Food item : breakfast) {
            if (item != null) {
                item.consume();
            } else {
                break;
            }
        }
    }

    public static int countProductsByType(Food[] breakfast, Food typeToCount) {
        int count = 0;
        for (Food item : breakfast) {
            if (item != null && item.getClass().equals(typeToCount.getClass())) { // 使用类的比较来判断类型
                count++;
            }
        }
        return count;
    }

}

