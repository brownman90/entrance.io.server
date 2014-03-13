
package io.entrance.service.activity;

public enum Verbs {
    COMMENT("comment"), LIKE("like");

    private String label;

    /**
     * Constructor.
     * 
     * @param label - the label name.
     */
    private Verbs(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

}
