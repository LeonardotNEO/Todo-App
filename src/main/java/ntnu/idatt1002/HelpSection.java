package ntnu.idatt1002;

import java.util.ArrayList;

/**
 * Class that represents a section in the help menu
 */
public class HelpSection {
    public String section;
    public String description;
    public ArrayList<Info> fields;

    /**
     * Gets the section of this instance
     * @return String section
     */
    public String getSection() {
        return section;
    }

    /**
     * Gets the description of this instance
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets all fields in of this instance
     * @return Arraylist of Info classes
     */
    public ArrayList<Info> getFields() {
        return fields;
    }

    /**
     * Class that represents a info field in a HelpSection
     */
    public class Info {
        public String image;
        public String text;
        public String title;

        /**
         * Gets the image location
         * @return String location of the image on this computer
         */
        public String getImage() {return image;}

        /**
         * Gets the text
         * @return String text
         */
        public String getText() {return text;}

        /**
         * Gets the title
         * @return String title
         */
        public String getTitle() {return title;}

        @Override
        public String toString() {
            return "Info{" +
                    "image='" + image + '\'' +
                    ", text='" + text + '\'' +
                    ", tile='" + text + '\'' +
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
