import { getUserAuth } from "./auth";
import { Book } from "./model/Book";

export const getAllBooks = async () => {
  const response = await fetch("http://localhost:8080/api/books");
  if (!response.ok) {
    throw new Error("Failed to fetch books");
  }
  return response.json();
};

export const getBook = async (id: string) => {
  const response = await fetch(`http://localhost:8080/api/books/${id}`);
  if (!response.ok) {
    throw new Error("Failed to fetch item details");
  }
  const book = await response.json();
  if (
    !book ||
    typeof book !== "object" ||
    !("title" in book) ||
    !("id" in book) ||
    !("price" in book)
  ) {
    throw new Error("Invalid item format");
  }
  return book;
};

export const createBook = async (book: Book) => {
  const response = await fetch("http://localhost:8080/api/books", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(book),
  });
  return response.json();
};

export const updateBook = async (id: string, book: any) => {
  const response = await fetch(`http://localhost:8080/api/books/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(book),
  });
  return response.json();
};

export const deleteBook = async (id: string) => {
  // add basic auth to headers with username and password
  const encodedCredentials = await getUserAuth();

  const response = await fetch(`http://localhost:8080/api/books/${id}`, {
    method: "DELETE",
    headers: {
      Authorization: `Basic ${encodedCredentials}`,
    },
  });
  return response.json();
};
