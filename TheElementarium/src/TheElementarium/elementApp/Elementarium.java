package TheElementarium.elementApp;

import TheElementarium.elements.Element;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Elementarium {
    private final List<Element> elements = new ArrayList<>();

    public void initializeSystem() {
        loadElements("/TheElementarium/elements/elements.csv");
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private void loadElements(String resourcePath) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(resourcePath)))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] data = line.split(",");
                if (data.length < 6)
                    continue;

                elements.add(new Element(
                        data[0].trim(),
                        data[1].trim(),
                        Integer.parseInt(data[2].trim()),
                        Double.parseDouble(data[3].trim()),
                        data[4].trim(),
                        data[5].trim(),
                        data.length > 6 ? data[6].trim() : ""));
                System.out.println("Loaded: " + data[0].trim() + " (" + data[1].trim() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Element findElement(String query) {
        if (query == null || query.isEmpty())
            return null;
        return elements.stream()
                .filter(e -> e.getName().equalsIgnoreCase(query.trim()) || e.getSymbol().equalsIgnoreCase(query.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Element> filterByGroup(String groupType) {
        if (groupType.equalsIgnoreCase("All Elements"))
            return elements;
        return elements.stream()
                .filter(e -> e.getGroup().equalsIgnoreCase(groupType))
                .collect(Collectors.toList());
    }

    public List<Element> getAllElements() {
        return elements;
    }
}
