'use client';
import { useEffect, useState } from "react";
import { getBook } from "../../../../data/utils";
import { Book } from "../../../../data/model/Book";

export default function BookDetailsPage({ params }: { params: { id: string }}) {
  const { id } = params;
  const [book, setBook] = useState<Book | null>(null);

  useEffect(() => {
    const fetchItem = async () => {
      try {
        const fetchedItem = await getBook(id);
        console.log("Fetched item:", fetchedItem);
        setBook(fetchedItem);
      } catch (error) {
        console.error("Error fetching item:", error);
      }
    };

    if (id) {
      fetchItem();
    }
  }, [id]);

  return (
    <main className="flex min-h-screen flex-col items-center p-24">
      {book ? (
        <div>
          <h1 className="text-4xl font-bold">{book.title}</h1>
          <p>ID: {book.id}</p>
          <p>Price: ${book.price}</p>
        </div>
      ) : (
        <p>No item found.</p>
      )}
    </main>
  );
}
