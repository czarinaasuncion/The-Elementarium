package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Elementarium {
    private final List<Element> elements = new ArrayList<>();
    public void initializeSystem() {
        loadElements("/TheElementarium/elements/elements.csv");}

    @SuppressWarnings("CallToPrintStackTrace")
    private void loadElements(String resourcePath) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(resourcePath)))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                String[] data = line.split(",");
                if (data.length < 6)
                    continue;

                String name = data[0].trim();
                String symbol = data[1].trim();
                int atomicNumber = Integer.parseInt(data[2].trim());
                double atomicWeight = Double.parseDouble(data[3].trim());
                String group = data[4].trim();
                String config = data[5].trim();
                String apps = data.length > 6 ? data[6].trim() : "";

                Element element = ElementGUI.createElement(name, symbol, atomicNumber, atomicWeight, group, config, apps); 
                elements.add(element);
                System.out.println("Loaded: " + name + " (" + symbol + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();}
    }

    public Element findElement(String query) {
        if (query == null || query.isEmpty())
            return null;
        return elements.stream()
                .filter(e -> e.getName().equalsIgnoreCase(query.trim()) || e.getSymbol().equalsIgnoreCase(query.trim()))
                .findFirst()
                .orElse(null);}

    public List<Element> filterByGroup(String groupType) {
        if (groupType.equalsIgnoreCase("All Elements"))
            return elements;
        return elements.stream()
                .filter(e -> e.getGroup().equalsIgnoreCase(groupType))
                .collect(Collectors.toList());}

    public List<Element> getAllElements() {
        return elements;}

    public String getReactionFromCSV1(List<String> activeSelectionSlots) {
        throw new UnsupportedOperationException("Unimplemented method 'getReactionFromCSV'");}

}