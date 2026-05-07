export const UserRol = {
  ADMIN: "ADMIN",
  STUDENT: "STUDENT"
} as const;
export type UserRol = (typeof UserRol)[keyof typeof UserRol];

// Interfaz del usuario
export interface User {
  id?: number; 
  nombre: string;
  email: string;
  password: string;
  rol?: UserRol; 
}