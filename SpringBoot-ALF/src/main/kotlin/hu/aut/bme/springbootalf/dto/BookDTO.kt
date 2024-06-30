package hu.aut.bme.springbootalf.dto

import hu.aut.bme.springbootalf.enums.Genre
import hu.aut.bme.springbootalf.model.Book

/**
 * Data Transfer Object (DTO) representing a Book.
 * This class is used for transferring Book data between layers of the application.
 */
class BookDTO(
    var bookId: Long,                       // The ID of the book.
    var title: String,                      // The title of the book.
    var author: String,                     // The author of the book.
    var releaseYear: String,                // The release year of the book.
    var genre: Set<Genre>,                  // The set of genres associated with the book.
    var price: Double                       // The price of the book.
) {

    // Secondary constructor that initializes a BookDTO object from a Book object.
    constructor(book: Book) : this(0, "", "", "", setOf(), 0.0) {
        // Copying data from the Book object to the BookDTO object.
        bookId = book.id
        title = book.title
        author = book.author
        releaseYear = book.releaseYear
        genre = book.genre
        price = book.price
    }
}