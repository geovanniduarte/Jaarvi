import dotenv from 'dotenv';

// Load environment variables from .env file
dotenv.config();

/**
 * Valid Node.js environment values
 */
type NodeEnv = 'development' | 'staging' | 'production' | 'test';

/**
 * Environment variable validation result
 */
interface EnvValidationResult {
  isValid: boolean;
  errors: string[];
}

/**
 * Validated environment configuration
 */
export interface ValidatedEnv {
  // Database
  DB_HOST: string;
  DB_PORT: number;
  DB_NAME: string;
  DB_USER: string;
  DB_PASSWORD: string;
  DATABASE_URL: string;

  // Server
  NODE_ENV: NodeEnv;
  PORT: number;
  SERVER_HOST: string;

  // Authentication
  JWT_SECRET: string;
  JWT_ACCESS_EXPIRATION: string;
  JWT_REFRESH_EXPIRATION: string;

  // CORS
  ALLOWED_ORIGINS: string[];

  // Logging
  LOG_LEVEL: string;
}

/**
 * Validates that a string is not empty
 */
function isNonEmpty(value: string | undefined): value is string {
  return value !== undefined && value.trim().length > 0;
}

/**
 * Validates that a string represents a valid port number
 */
function isValidPort(value: string | undefined): boolean {
  if (!value) return false;
  const port = parseInt(value, 10);
  return !isNaN(port) && port > 0 && port <= 65535;
}

/**
 * Validates that NODE_ENV is a valid value
 */
function isValidNodeEnv(value: string | undefined): value is NodeEnv {
  const validEnvs: NodeEnv[] = ['development', 'staging', 'production', 'test'];
  return value !== undefined && validEnvs.includes(value as NodeEnv);
}

/**
 * Constructs DATABASE_URL from individual components
 */
function constructDatabaseUrl(
  user: string,
  password: string,
  host: string,
  port: number,
  name: string
): string {
  return `postgresql://${user}:${password}@${host}:${port}/${name}`;
}

/**
 * Validates all required environment variables and returns errors
 */
function validateEnvVars(): EnvValidationResult {
  const errors: string[] = [];

  // Required database variables
  if (!isNonEmpty(process.env.DB_HOST)) {
    errors.push('DB_HOST is required');
  }
  if (!isNonEmpty(process.env.DB_USER)) {
    errors.push('DB_USER is required');
  }
  if (!isNonEmpty(process.env.DB_PASSWORD)) {
    errors.push('DB_PASSWORD is required');
  }

  // JWT validation
  if (!isNonEmpty(process.env.JWT_SECRET)) {
    errors.push('JWT_SECRET is required');
  } else if (process.env.JWT_SECRET.length < 32) {
    errors.push('JWT_SECRET must be at least 32 characters');
  }

  // Optional with validation
  if (process.env.DB_PORT && !isValidPort(process.env.DB_PORT)) {
    errors.push('DB_PORT must be a valid port number (1-65535)');
  }

  if (process.env.PORT && !isValidPort(process.env.PORT)) {
    errors.push('PORT must be a valid port number (1-65535)');
  }

  if (process.env.NODE_ENV && !isValidNodeEnv(process.env.NODE_ENV)) {
    errors.push('NODE_ENV must be one of: development, staging, production, test');
  }

  return {
    isValid: errors.length === 0,
    errors,
  };
}

/**
 * Validates environment variables at startup and returns typed configuration.
 * Throws an error if validation fails.
 */
export function validateEnvironment(): ValidatedEnv {
  const validation = validateEnvVars();

  if (!validation.isValid) {
    const errorMessage = `Environment validation failed:\n${validation.errors.map((e) => `  - ${e}`).join('\n')}`;
    throw new Error(errorMessage);
  }

  // Apply defaults for optional variables
  const dbHost = process.env.DB_HOST as string;
  const dbPort = process.env.DB_PORT ? parseInt(process.env.DB_PORT, 10) : 5432;
  const dbName = process.env.DB_NAME ?? 'jaarvi_dev';
  const dbUser = process.env.DB_USER as string;
  const dbPassword = process.env.DB_PASSWORD as string;

  const nodeEnv = (process.env.NODE_ENV as NodeEnv) ?? 'development';
  const port = process.env.PORT ? parseInt(process.env.PORT, 10) : 3000;
  const serverHost = process.env.SERVER_HOST ?? '0.0.0.0';

  const jwtSecret = process.env.JWT_SECRET as string;
  const jwtAccessExpiration = process.env.JWT_ACCESS_EXPIRATION ?? '15m';
  const jwtRefreshExpiration = process.env.JWT_REFRESH_EXPIRATION ?? '7d';

  const allowedOriginsRaw = process.env.ALLOWED_ORIGINS ?? '';
  const allowedOrigins = allowedOriginsRaw
    .split(',')
    .map((origin) => origin.trim())
    .filter((origin) => origin.length > 0);

  const logLevel = process.env.LOG_LEVEL ?? 'info';

  // Construct DATABASE_URL from components
  const databaseUrl = constructDatabaseUrl(dbUser, dbPassword, dbHost, dbPort, dbName);

  return {
    DB_HOST: dbHost,
    DB_PORT: dbPort,
    DB_NAME: dbName,
    DB_USER: dbUser,
    DB_PASSWORD: dbPassword,
    DATABASE_URL: databaseUrl,

    NODE_ENV: nodeEnv,
    PORT: port,
    SERVER_HOST: serverHost,

    JWT_SECRET: jwtSecret,
    JWT_ACCESS_EXPIRATION: jwtAccessExpiration,
    JWT_REFRESH_EXPIRATION: jwtRefreshExpiration,

    ALLOWED_ORIGINS: allowedOrigins,

    LOG_LEVEL: logLevel,
  };
}

/**
 * Export validation function for testing
 */
export { validateEnvVars, isNonEmpty, isValidPort, isValidNodeEnv, constructDatabaseUrl };
