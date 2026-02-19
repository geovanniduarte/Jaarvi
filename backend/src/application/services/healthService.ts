import { getConfig } from '../../infrastructure/config';
import packageJson from '../../../package.json';

/**
 * Health status response structure
 */
export interface HealthStatus {
  success: boolean;
  message: string;
  timestamp: string;
  version: string;
  environment: string;
}

/**
 * Error response structure for health check failures
 */
export interface HealthErrorResponse {
  success: false;
  error: {
    message: string;
    code: string;
  };
}

/**
 * Gets the application version from package.json
 * Falls back to '1.0.0' if not available
 */
function getAppVersion(): string {
  return (packageJson as { version?: string }).version ?? '1.0.0';
}

/**
 * Health Service
 *
 * Provides health check functionality for the application.
 * Used by load balancers, monitoring systems, and deployment verification.
 */
export class HealthService {
  /**
   * Gets the current health status of the application.
   *
   * @returns Health status object with server information
   */
  public getHealthStatus(): HealthStatus {
    const config = getConfig();

    return {
      success: true,
      message: 'Hola, soy Jaarvi',
      timestamp: new Date().toISOString(),
      version: getAppVersion(),
      environment: config.server.nodeEnv,
    };
  }

  /**
   * Creates an error response for health check failures
   *
   * @param error - The error that caused the health check to fail
   * @returns Formatted error response
   */
  public createErrorResponse(_error?: Error): HealthErrorResponse {
    return {
      success: false,
      error: {
        message: 'Health check failed',
        code: 'HEALTH_CHECK_ERROR',
      },
    };
  }
}

/**
 * Singleton instance of HealthService
 */
let healthServiceInstance: HealthService | null = null;

/**
 * Gets the singleton HealthService instance
 */
export function getHealthService(): HealthService {
  if (healthServiceInstance === null) {
    healthServiceInstance = new HealthService();
  }
  return healthServiceInstance;
}

/**
 * Resets the singleton (for testing purposes)
 */
export function resetHealthService(): void {
  healthServiceInstance = null;
}
