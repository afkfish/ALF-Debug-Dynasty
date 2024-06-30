'use client';
import { useState } from "react";
import { getUserAuth } from "../../data/auth";
import { redirect } from "next/navigation";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [isSignUp, setIsSignUp] = useState(false);
  const [error, setError] = useState("");

  const handleLogin = async () => {
    try {
      const encodedCredentials = await getUserAuth();
      if (encodedCredentials) {
        redirect("/books");
      } else {
        setError("Invalid username or password");
      }
    } catch (error) {
      setError("An error occurred while logging in");
    }
  };

  const signUpUser = async () => {
    try {
      const response = await fetch("", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });

      if (response.ok) {
        redirect("/items/all");
      } else {
        setError("Failed to sign up");
      }
    } catch (error) {
      setError("An error occurred during sign-up");
    }
  };

  return (
    <main className="flex min-h-screen flex-col items-center p-24">
      <h1 className="text-4xl font-bold">{isSignUp ? "Sign Up" : "Login"}</h1>
      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        className="p-2 m-2"
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="p-2 m-2"
      />
      {!isSignUp ? (
        <>
          <button
            onClick={handleLogin}
            className="p-2 m-2 bg-blue-500 text-white rounded-lg"
          >
            Login
          </button>
          <p
            className="text-blue-500 cursor-pointer"
            onClick={() => setIsSignUp(true)}
          >
            Sign Up
          </p>
        </>
      ) : (
        <>
          <button
            onClick={signUpUser}
            className="p-2 m-2 bg-green-500 text-white rounded-lg"
          >
            Sign Up
          </button>
          <p
            className="text-blue-500 cursor-pointer"
            onClick={() => setIsSignUp(false)}
          >
            Back to Login
          </p>
        </>
      )}
      {error && <p className="text-red-500">{error}</p>}
    </main>
  );
}
