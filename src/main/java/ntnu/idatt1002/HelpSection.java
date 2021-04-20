package ntnu.idatt1002;


import java.util.ArrayList;

public class HelpSection {
    public String section;
    public String description;
    public ArrayList<Info> fields;

    public String getSection() {
        return section;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Info> getFields() {
        return fields;
    }

    public class Info {
        public String image;
        public String text;
        public String title;

        public String getImage() {return image;}

        public String getText() {return text;}

        public String getTitle() {return title;}

        @Override
        public String toString() {
            return "Info{" +
                    "image='" + image + '\'' +
                    ", text='" + text + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HelpSection{" +
                "section='" + section + '\'' +
                ", description='" + description + '\'' +
                ", fields=" + fields.toString() +
                '}';
    }
}
