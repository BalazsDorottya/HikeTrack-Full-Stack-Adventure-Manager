export interface HikeCreationDTO {
  nameOfTrail: string;
  startLocation: string;
  startTime: string;
  price: number;
  lengthOfTrail: number;
}

export interface HikeShortDTO {
  id: number;
  nameOfTrail: string;
  startLocation: string;
  favorite: boolean;
  timesCompleted: number;
}

export interface HikeLongDTO extends HikeCreationDTO {
  id: number;
  favorite: boolean;
  timesCompleted: number;
}

export interface AuthResponse {
  token: string;
}

export interface RegisterDTO {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
}

export interface AuthContextType {
  token: string | null;
  role: string | null;
  isAdmin: boolean;
  login: (token: string) => void;
  logout: () => void;
  isAuthenticated: boolean;
}

export interface CustomJwtPayload {
  sub: string;
  role: string;
  iat?: number;
  exp?: number;
}
