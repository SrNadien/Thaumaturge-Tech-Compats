package nadiendev.ThaumaturgeTechCompats.ponder;

import java.util.List;

public record PonderScript(String id, String title, String spanishTitle, List<String> lines, List<String> spanishLines) {
    public PonderScript {
        if (lines.size() != spanishLines.size()) {
            throw new IllegalStateException("Script " + id + " has mismatched translations");
        }
    }

    public String line(int index) {
        return lines.get(index);
    }
}
