import { validateEnvironment, ValidatedEnv } from './env';

/**
 * Application configuration singleton.
 * Validates environment on first access and caches the result.
 */
class Config {
  private static instance: Config | null = null;
  private env: ValidatedEnv;

  private constructor() {
    this.env = validateEnvironment();
  }

  /**
   * Gets the singleton Config instance.
   * Validates environment on first call.
   */
  public static getInstance(): Config {
    if (Config.instance === null) {
      Config.instance = new Config();
    }
    return Config.instance;
  }

  /**
   * Resets the singleton (for testing purposes only)
   */
  public static reset(): void {
    Config.instance = null;
  }

  /**
   * Database configuration
   */
  get database(): {
    host: string;
    port: number;
    name: string;
    user: string;
    password: string;
    url: string;
  } {
    return {
      host: this.env.DB_HOST,
      port: this.env.DB_PORT,
      name: this.env.DB_NAME,
      user: this.env.DB_USER,
      password: this.env.DB_PASSWORD,
      url: this.env.DATABASE_URL,
    };
  }

  /**
   * Server configuration
   */
  get server(): {
    port: number;
    host: string;
    nodeEnv: string;
    isProduction: boolean;
    isDevelopment: boolean;
    isTest: boolean;
  } {
    return {
      port: this.env.PORT,
      host: this.env.SERVER_HOST,
      nodeEnv: this.env.NODE_ENV,
      isProduction: this.env.NODE_ENV === 'production',
      isDevelopment: this.env.NODE_ENV === 'development',
      isTest: this.env.NODE_ENV === 'test',
    };
  }

  /**
   * Authentication configuration
   */
  get auth(): {
    jwtSecret: string;
    accessTokenExpiration: string;
    refreshTokenExpiration: string;
  } {
    return {
      jwtSecret: this.env.JWT_SECRET,
      accessTokenExpiration: this.env.JWT_ACCESS_EXPIRATION,
      refreshTokenExpiration: this.env.JWT_REFRESH_EXPIRATION,
    };
  }

  /**
   * CORS configuration
   */
  get cors(): {
    allowedOrigins: string[];
  } {
    return {
      allowedOrigins: this.env.ALLOWED_ORIGINS,
    };
  }

  /**
   * Logging configuration
   */
  get logging(): {
    level: string;
  } {
    return {
      level: this.env.LOG_LEVEL,
    };
  }
}

/**
 * Export singleton getter function
 */
export function getConfig(): Config {
  return Config.getInstance();
}

/**
 * Export Config class for testing
 */
export { Config };
