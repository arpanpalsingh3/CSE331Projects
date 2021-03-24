package marvel;


import com.opencsv.bean.CsvBindByName;

/**
 * Java-bean class meant to supplement OpenCSV,
 * which contains getters and setters for a hero
 * and a book
 */

public class ConnectionModel {
    /*
        No rep invariant, abstraction or check reps
     */

    /**
     * name of hero
     */
    @CsvBindByName
    private String hero;

    /**
     * name of book
     */
    @CsvBindByName
    private String book;

    /**
     * get character
     *
     * @return the hero name
     */
    public String getCharacter() {
        return hero;
    }

    /**
     * Set the hero equal to input character
     *
     * @param character the character
     * @spec.effects this hero
     */
    public void setHero(String character) {
        this.hero = character;
    }

    /**
     * return the name of book
     *
     * @return this book
     */
    public String getBook() {
        return book;
    }

    /**
     * This this book equal to the book
     * name input
     *
     * @param book the book name replacement
     * @spec.effects this book
     */
    public void setBook(String book) {
        this.book = book;
    }
}
