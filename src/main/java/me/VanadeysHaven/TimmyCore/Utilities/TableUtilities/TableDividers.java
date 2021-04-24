package me.VanadeysHaven.TimmyCore.Utilities.TableUtilities;

public enum TableDividers {

    HORIZONTAL      ("─"),
    VERTICAL        ("│"),

    DOWN_LEFT       ("┐"),
    DOWN_RIGHT      ("┌"),
    UP_LEFT         ("┘"),
    UP_RIGHT        ("└"),

    VERTICAL_RIGHT  ("├"),
    VERTICAL_LEFT   ("┤"),
    HORIZONTAL_DOWN ("┬"),
    HORIZONTAL_UP   ("┴"),

    CENTRAL         ("┼");

    private String character;

    TableDividers(String character){
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }
}
