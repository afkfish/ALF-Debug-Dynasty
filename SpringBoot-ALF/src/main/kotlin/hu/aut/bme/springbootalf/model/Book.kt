package hu.aut.bme.springbootalf.model

import hu.aut.bme.springbootalf.enums.Genre
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

/**
 * Entity class representing a Book.
 * This class is used to model books in the application database.
 */
@Entity
@Table(name = "books")
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // The unique identifier of the book.
    var id: Long = 0

    @Column(nullable = false)
    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    // The title of the book.
    lateinit var title: String

    @Column(nullable = false)
    @NotBlank(message = "Author is mandatory")
    @Size(min = 3, max = 50, message = "Author must be between 3 and 50 characters")
    // The author of the book.
    lateinit var author: String

    @Column(nullable = false)
    @NotBlank(message = "Release year is mandatory")
    @Size(min = 4, max = 4, message = "Release year must be 4 characters")
    // The release year of the book.
    lateinit var releaseYear: String

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Genre::class)
    @CollectionTable(name = "book_genre", joinColumns = [JoinColumn(name = "book_id")])
    // The set of genres associated with the book.
    lateinit var genre: Set<Genre>

    @Column(nullable = false)
    @NotNull(message = "Content is mandatory") //NotBlank is not applicable for byte[], legalábbis ezt mondta :D
    @Lob
    // The content of the book (PDF/MD/TXT).
    lateinit var content: ByteArray

    @Column(nullable = false)
    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    // The price of the book.
    var price: Double = 0.0

    /**
     * Secondary constructor to create a Book object with only an ID.
     * Used primarily for updating existing books.
     *
     * @param id The ID of the book.
     */
    constructor(id: Long) {
        this.id = id
    }

    /**
     * Primary constructor to create a Book object with all properties.
     *
     * @param title The title of the book.
     * @param author The author of the book.
     * @param releaseYear The release year of the book.
     * @param genre The set of genres associated with the book.
     * @param content The content of the book.
     * @param price The price of the book.
     */
    constructor(title: String, author: String, releaseYear: String, genre: Set<Genre>, content: ByteArray, price: Double) {
        this.title = title
        this.author = author
        this.releaseYear = releaseYear
        this.genre = genre
        this.content = content
        this.price = price
    }

}