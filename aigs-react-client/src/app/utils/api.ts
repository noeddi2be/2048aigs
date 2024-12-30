import axios from 'axios';

const API_URL = 'http://localhost:50005';

export const registerUser = async (username: string, password: string) => {
  const response = await axios.post(`${API_URL}/users/register`, {
    userName: username,
    password: password,
  });
  return response.data;
};

export const loginUser = async (username: string, password: string) => {
  const response = await axios.post(`${API_URL}/users/login`, {
    userName: username,
    password: password,
  });
  return response.data;
};

export const logoutUser = async (username: string) => {
  const response = await axios.post(`${API_URL}/users/logout`, { userName: username });
  return response.data;
};

export const startNewGame = async (token: string) => {
  const response = await axios.post(`${API_URL}/game/new`, {
    token: token,
    gameType: "TwentyFourtyEight",
  });
  return response.data;
};

export const quitGame = async (token: string) => {
  const response = await axios.post(`${API_URL}/game/quit`, { token: token });
  return response.data;
};

export const makeMove = async (token: string, direction: string) => {
  const response = await axios.post(`${API_URL}/game/move`, {
    token: token,
    move: direction,
  });
  return response.data;
};

export const pingServer = async () => {
  const response = await axios.get(`${API_URL}/ping`);
  return response.data;
};