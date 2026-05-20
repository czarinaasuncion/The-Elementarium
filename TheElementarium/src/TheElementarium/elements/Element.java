package TheElementarium.elements;

public class Element {
    private final String name;
    private final String symbol;
    private final int atomicNumber;
    private final double atomicWeight;
    private final String group;
    private final String electronConfig;
    private final String applications;

    public Element(String name, String symbol, int atomicNumber, double atomicWeight, String group, String electronConfig, String applications) {
        this.name = name;
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
        this.group = group;
        this.electronConfig = electronConfig;
        this.applications = applications;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public int getAtomicNumber() { return atomicNumber; }
    public double getAtomicWeight() { return atomicWeight; }
    public String getGroup() { return group; }
    public String getElectronConfig() { return electronConfig; }
    public String getApplications() { return applications; }
}

 
