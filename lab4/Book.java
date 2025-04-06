package lab4;

public class Book {
    String title;
    String author;
    int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
    
    public String toString() {
        return String.format("Title: %s, Author: %s, Year: %d", title, author, year);
    }

    public boolean equals(Book book) {
        return this.toString().equals(book.toString());
    }

    public int hashCode() {
        int result = 17;
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + year;
        return result;
    }

}
