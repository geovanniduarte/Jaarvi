import winston from 'winston';

/**
 * Log levels in order of priority (lowest to highest)
 */
type LogLevel = 'error' | 'warn' | 'info' | 'http' | 'debug';

/**
 * Custom log format for development
 */
const developmentFormat = winston.format.combine(
  winston.format.timestamp({ format: 'YYYY-MM-DD HH:mm:ss' }),
  winston.format.colorize({ all: true }),
  winston.format.printf(({ timestamp, level, message, ...metadata }) => {
    const metaString = Object.keys(metadata).length > 0 ? ` ${JSON.stringify(metadata)}` : '';
    return `${String(timestamp)} [${level}]: ${String(message)}${metaString}`;
  })
);

/**
 * Custom log format for production (JSON for structured logging)
 */
const productionFormat = winston.format.combine(
  winston.format.timestamp(),
  winston.format.errors({ stack: true }),
  winston.format.json()
);

/**
 * Creates a Winston logger instance with appropriate configuration
 * based on the environment.
 */
function createLogger(nodeEnv: string, logLevel: string): winston.Logger {
  const isProduction = nodeEnv === 'production';
  const isTest = nodeEnv === 'test';

  const transports: winston.transport[] = [];

  // Console transport (disabled in test mode to reduce noise)
  if (!isTest) {
    transports.push(
      new winston.transports.Console({
        format: isProduction ? productionFormat : developmentFormat,
      })
    );
  }

  return winston.createLogger({
    level: logLevel as LogLevel,
    format: isProduction ? productionFormat : developmentFormat,
    transports,
    // Silent in test mode
    silent: isTest,
  });
}

/**
 * Logger class that wraps Winston for application-wide logging.
 * Provides typed methods for different log levels.
 */
export class Logger {
  private logger: winston.Logger;

  constructor(nodeEnv = 'development', logLevel = 'info') {
    this.logger = createLogger(nodeEnv, logLevel);
  }

  /**
   * Log an error message
   */
  error(message: string, meta?: Record<string, unknown>): void {
    this.logger.error(message, meta);
  }

  /**
   * Log a warning message
   */
  warn(message: string, meta?: Record<string, unknown>): void {
    this.logger.warn(message, meta);
  }

  /**
   * Log an info message
   */
  info(message: string, meta?: Record<string, unknown>): void {
    this.logger.info(message, meta);
  }

  /**
   * Log an HTTP request (used by request logger middleware)
   */
  http(message: string, meta?: Record<string, unknown>): void {
    this.logger.http(message, meta);
  }

  /**
   * Log a debug message
   */
  debug(message: string, meta?: Record<string, unknown>): void {
    this.logger.debug(message, meta);
  }
}

/**
 * Singleton logger instance (lazy initialization to avoid issues before config is loaded)
 */
let loggerInstance: Logger | null = null;

/**
 * Gets the singleton logger instance.
 * Creates it on first access using current environment settings.
 */
export function getLogger(): Logger {
  if (loggerInstance === null) {
    const nodeEnv = process.env.NODE_ENV ?? 'development';
    const logLevel = process.env.LOG_LEVEL ?? 'info';
    loggerInstance = new Logger(nodeEnv, logLevel);
  }
  return loggerInstance;
}

/**
 * Resets the logger singleton (for testing purposes)
 */
export function resetLogger(): void {
  loggerInstance = null;
}

/**
 * Export createLogger for testing
 */
export { createLogger };
