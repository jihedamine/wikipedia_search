package wikipedia.api.serialization.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {
    private String title;
    private List<Revision> revisions;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        if (revisions.isEmpty()) {
            return "";
        }

        return revisions.iterator().next().slots.main.content;
    }

    private class Revision {
        private Slots slots;
    }

    private class Slots {
        private MainSlot main;
    }

    private class MainSlot {
        @SerializedName("*")
        private String content;
    }
}
