package TheElementarium.elements;

import java.awt.Color;
import java.util.Map;

public class Element {
    private final String name;
    private final String symbol;
    private final int atomicNumber;
    private final double atomicWeight;
    private final String group;
    private final String electronConfig;
    private final String applications;

    public static final String[] GROUPS = {
        "All Elements",
        "Alkali metals",
        "Alkaline earth metals",
        "Transition metals",
        "Post-transition metals",
        "Metalloids",
        "Reactive nonmetals",
        "Halogens",
        "Noble gases",
        "Lanthanides",
        "Actinides"
    };

    public static final Color DEFAULT_GROUP_COLOR = new Color(250, 240, 202);

    private static final Map<String, Color> GROUP_COLORS = Map.of(
        "Alkali metals", new Color(255, 153, 153),
        "Alkaline earth metals", new Color(255, 204, 153),
        "Transition metals", new Color(255, 255, 153),
        "Post-transition metals", new Color(153, 255, 204),
        "Metalloids", new Color(204, 153, 255),
        "Reactive nonmetals", new Color(255, 153, 204),
        "Halogens", new Color(255, 153, 255),
        "Noble gases", new Color(153, 255, 255),
        "Lanthanides", new Color(255, 204, 229),
        "Actinides", new Color(255, 204, 178)
    );

    public Element(String name, String symbol, int atomicNumber, double atomicWeight, String group,
            String electronConfig, String applications) {
        this.name = name;
        this.symbol = symbol;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
        this.group = group;
        this.electronConfig = electronConfig;
        this.applications = applications;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public double getAtomicWeight() {
        return atomicWeight;
    }

    public String getGroup() {
        return group;
    }

    public String getGroupImageName() {
        return group + ".png";
    }

    public Color getGroupColor() {
        return getGroupColor(group);
    }

    public static Color getGroupColor(String group) {
        return GROUP_COLORS.getOrDefault(group, DEFAULT_GROUP_COLOR);
    }

    public String getElectronConfig() {
        return electronConfig;
    }

    public String getApplications() {
        return applications;
    }
}
