"use client";

import React, { useState, useEffect } from 'react';
import GameBoard from './components/GameBoard';
import { registerUser, loginUser, startNewGame, makeMove } from './utils/api';

const HomePage: React.FC = () => {
  const [board, setBoard] = useState<number[][]>(Array(4).fill(Array(4).fill(0)));
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [token, setToken] = useState('');
  const [isGameOver, setIsGameOver] = useState(false);
  const [score, setScore] = useState(0);

  const fetchGameBoard = async () => {
    if (!token) return;
    try {
      const data = await startNewGame(token);
      setBoard(data.board);
    } catch (error) {
      console.error(error);
    }
  };

  const handleRegister = async () => {
    try {
      const data = await registerUser(username, password);
      if (data.userExpiry) {
        alert('Registration successful! User: ' + data.userName);
      } else if (data.error_description)
        alert(data.error_description);
    } catch (error) {
      console.error(error);
      alert('Registration failed!');
    }
  };

  const handleLogin = async () => {
    try {
      const data = await loginUser(username, password);
      if (data.token) {
        setToken(data.token);
        alert('Login successful!');
        await fetchGameBoard();
      } else {
        alert('Login failed! Have you registered?');
      }
    } catch (error) {
      console.error(error);
      alert('Login failed!');
    }
  };

  const handleLogout = async () => {
    try {
      setToken('');
      setBoard(Array(4).fill(Array(4).fill(0)));
      setUsername('');
      setPassword('');
      setIsGameOver(false);
      setScore(0);
      alert('Logout successful!');
    } catch (error) {
      console.error(error);
      alert('Logout failed!');
    }
  };

  const handleNewGame = async () => {
    if (!token) return;
    try {
      await startNewGame(token);
      await fetchGameBoard();
      setScore(0);
      setIsGameOver(false);
    } catch (error) {
      console.error(error);
    }
  };

  const handleKeyDown = async (event: KeyboardEvent) => {
    let direction: string | null = null;
    switch (event.key) {
      case 'ArrowUp':
        direction = 'up';
        break;
      case 'ArrowDown':
        direction = 'down';
        break;
      case 'ArrowLeft':
        direction = 'left';
        break;
      case 'ArrowRight':
        direction = 'right';
        break;
    }

    if (direction) {
      try {
        const data = await makeMove(token, direction);
        setBoard(data.board);
        setScore(data.difficulty);
        if (data.result === true) {
          setIsGameOver(true);
        }
      } catch (error) {
        console.error(error);
      }
    }
  };

  useEffect(() => {
    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, [token]);

  return (
    <main className="flex flex-col items-center">
      <h1 className="text-4xl font-extrabold my-5 text-[#4c4444]">2048</h1>
      <div className=" bg-[#4c4444] rounded-lg p-4 shadow-neutral-800 shadow-md max-w-100">
        <div className="flex flex-row">
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="border border-gray-400 rounded p-2 m-2 w-40"
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="border border-gray-400 rounded p-2 m-2 w-40"
          />
        </div>
        <div className="flex flex-row">
          <button onClick={handleRegister} className=" bg-[#486ba8] text-white font-bold rounded p-2 m-2 w-40 shadow-lg">Register</button>
          <button onClick={handleLogin} className="bg-[#5a933e] text-white font-bold rounded p-2 m-2 w-40 shadow-lg">Login</button>
        </div>
        <div className='flex items-center justify-center'>
          <button onClick={handleNewGame} className="bg-[#cfa84e] text-white font-bold rounded mt-2 p-2 w-8/12 shadow-lg">New Game</button>
        </div>
      </div>
      <div className="relative">
        {isGameOver && (
          <div className="absolute inset-0 flex items-center justify-center rounded-lg bg-black/50 mt-5 z-10">
            <div className="bg-red-500 text-white font-bold text-2xl rounded-lg p-4 text-center animate-bounce">
              Game Over!
            </div>
          </div>
        )}
        <GameBoard board={board} />
      </div>
      <div className=" bg-[#4c4444] rounded-lg p-4 shadow-neutral-800 shadow-md mt-5 mb-5 w-100">
        <div className="flex flex-row">
          <div
            className="flex rounded-xl m-2 w-40 items-center justify-center text-center text-stone-50 font-extrabold text-xl"
          >
            {`Score: ${score}`}
          </div>
          <button onClick={handleLogout} className=" bg-[#a84848] text-white font-bold rounded p-2 m-2 w-40 shadow-lg">Logout</button>
          </div>
        </div>
    </main>
  );
};

export default HomePage;