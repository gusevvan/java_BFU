package lab4;

import java.util.List;

public class LibraryTest {
    private static Library testLibrary;

    public static void main(String[] args) {
        testLibrary = new Library();
        test();
    }

    private static void test() {
        Book book1 = new Book("Book1", "Author1", 1);
        testLibrary.addBook(book1);
        Book book2 = new Book("Book2", "Author1", 2);
        testLibrary.addBook(book2);
        Book book3 = new Book("Book3", "Author1", 1);
        testLibrary.addBook(book3);
        Book book4 = new Book("Book4", "Author2", 2);
        testLibrary.addBook(book4);
        Book book5 = new Book("Book5", "Author2", 1);
        testLibrary.addBook(book5);
        Book book6 = new Book("Book6", "Author2", 2);
        testLibrary.addBook(book6);
        printBooksByYear(1);
        printBooksByYear(2);
        printBooksByAuthor("Author1");
        printBooksByAuthor("Author2");
        printLibraryInfo();
        testLibrary.removeBook(book6);
        testLibrary.removeBook(book3);
        printLibraryInfo();
        testLibrary.removeBook(book2);
        testLibrary.removeBook(book1);
        printLibraryInfo();
        testLibrary.removeBook(book4);
        testLibrary.removeBook(book5);
        printLibraryInfo();
    }

    private static void printLibraryInfo() {
        testLibrary.printAllBooks();
        testLibrary.printAuthorsStatistics();
        testLibrary.printUniqueAuthors();
    }

    private static void printBooksByYear(int year) {
        System.out.println("Books by Year " + year + ":");
        List<Book> booksByYear = testLibrary.findBooksByYear(year);
        for (Book book: booksByYear) {
            System.out.println(book.toString());
        }
    }

    private static void printBooksByAuthor(String author) {
        System.out.println("Books by Author " + author + ":");
        List<Book> booksByAuthor = testLibrary.findBooksByAuthor(author);
        for (Book book: booksByAuthor) {
            System.out.println(book.toString());
        }
    }
}
