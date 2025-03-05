package bsu.rfe.java.group6.lab1.Tuz.varA19;

public class Cheese extends Food {


    public Cheese() {
        super("Сыр");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // 同一对象直接返回true
        if (obj == null || getClass() != obj.getClass()) return false; // 如果类型不同或者为null，则返回false
        return true; // 如果是同类型的对象，认为它们相等
    }

    @Override
    public void consume() {
        System.out.println(this + " съеден");
    }

}
