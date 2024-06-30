'use client';
import { getAllBooks } from "../../data/utils";
import { Book } from "../../data/model/Book";
import { useEffect, useState } from "react";
import Link from "next/link";

export default function AllBooks() {
  const [books, setBooks] = useState<Book[]>([]);

  useEffect(() => {
    getAllBooks().then((data) => {
      setBooks(data);
    });
  }, []);

  return (
    <main className="flex min-h-screen flex-col items-center p-24">
      <div className="flex justify-between w-full">
        <h1 className="text-4xl font-bold">All books</h1>
        <Link href="/" className="text-2xl font-bold">
          Home
        </Link>
      </div>
      <table className="table-auto">
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {books.flatMap((book) => (
            <tr>
              <td>{book.id}</td>
              <td>
                <Link href={`/books/details/${book.id}`}>
                  {book.title}
                </Link>
              </td>
              <td>{book.price}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  );
}
