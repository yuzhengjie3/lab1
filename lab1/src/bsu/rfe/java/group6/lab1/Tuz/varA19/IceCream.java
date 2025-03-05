package bsu.rfe.java.group6.lab1.Tuz.varA19;

public class IceCream extends Food {

    private String sirup;

    public IceCream(String sirup) {
        super("Мороженое");
        this.sirup = sirup;
    }


    @Override
    public void consume() {
        System.out.println(this + " съедено");
    }

    public String getsirup() {
        return sirup;
    }
    public void setsirup(String sirup) {
        this.sirup = sirup;
    }


    @Override
    public boolean equals(Object arg0) {
        if (super.equals(arg0)) {
            if (!(arg0 instanceof IceCream)) return false;
            return sirup.equals(((IceCream)arg0).sirup);
        } else
            return false;
    }

    public String toString() {
        return super.toString() +" " + sirup.toUpperCase() ;
    }

}
