package lab4;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class Library {
    private List<Book> books;
    private Set<String> authors;
    private Map<String, Integer> authorCounts;

    public Library() {
        books = new ArrayList<Book>();
        authors = new HashSet<String>();
        authorCounts = new HashMap<String, Integer>();
    }

    public void addBook(Book book) {
        books.add(book);
        authors.add(book.getAuthor());
        if (authorCounts.containsKey(book.getAuthor())) {
            authorCounts.put(book.getAuthor(), authorCounts.get(book.getAuthor()) + 1);
        } else {
            authorCounts.put(book.getAuthor(), 1);
        }
    }

    public void removeBook(Book book) {
        books.remove(book);
        if (authorCounts.get(book.getAuthor()) == 1) {
            authorCounts.remove(book.getAuthor());
            authors.remove(book.getAuthor());
        } else {
            authorCounts.put(book.getAuthor(), authorCounts.get(book.getAuthor()) - 1);
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> authorBooks = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                authorBooks.add(book);
            }
        }
        return authorBooks;
    }

    public List<Book> findBooksByYear(int year) {
        List<Book> yearBooks = new ArrayList<Book>();
        for (Book book : books) {
            if (book.getYear() == year) {
                yearBooks.add(book);
            }
        }
        return yearBooks;
    }

    public void printAllBooks() {
        System.out.println("Books:");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }

    public void printUniqueAuthors() {
        System.out.println("Authors: ");
        for (String author: authors) {
            System.out.println(author + " ");
        }
    }

    public void printAuthorsStatistics() {
        System.out.println("Statistics:");
        for (String author: authors) {
            System.out.println("Author: " + author + " Books amount: " + authorCounts.get(author));
        }
    }
}
