import type { User } from "../entities/User";

const API_URL = "http://localhost:8090";

// 🔑 helper para token
const getAuthHeader = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

// 🔍 GET all users
export const getUsers = async (): Promise<User[]> => {
  const res = await fetch(`${API_URL}/users`, {
    headers: getAuthHeader(),
  });

  if (!res.ok) throw new Error("Error al obtener usuarios");

  return res.json();
};

// 🔍 GET by email
export const getUserByEmail = async (email: string): Promise<User> => {
  const res = await fetch(`${API_URL}/users/email/${email}`, {
    headers: getAuthHeader(),
  });

  if (!res.ok) throw new Error("Usuario no encontrado");

  return res.json();
};

// ➕ CREATE
export const createUser = async (user: User): Promise<User> => {
  const res = await fetch(`${API_URL}/users`, {
    method: "POST",
    headers: getAuthHeader(),
    body: JSON.stringify(user),
  });

  if (!res.ok) throw new Error("Error al crear usuario");

  return res.json();
};

// ✏️ UPDATE
export const updateUser = async (user: User): Promise<User> => {
  const res = await fetch(`${API_URL}/users/${user.id}`, {
    method: "PUT",
    headers: getAuthHeader(),
    body: JSON.stringify(user),
  });

  if (!res.ok) throw new Error("Error al actualizar usuario");

  return res.json();
};

// ❌ DELETE
export const deleteUser = async (id: number): Promise<void> => {
  const res = await fetch(`${API_URL}/users/${id}`, {
    method: "DELETE",
    headers: getAuthHeader(),
  });

  if (!res.ok) throw new Error("Error al eliminar usuario");
};