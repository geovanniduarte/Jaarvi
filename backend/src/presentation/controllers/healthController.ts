import { Request, Response, NextFunction } from 'express';
import { getHealthService } from '../../application/services/healthService';
import { getLogger } from '../../infrastructure/logger';

/**
 * Health Controller
 *
 * Handles HTTP requests for health check endpoints.
 * Used by load balancers and monitoring systems to verify application health.
 */
export class HealthController {
  /**
   * Handles GET /api/health request.
   *
   * Returns current health status of the application including:
   * - Success status
   * - Welcome message
   * - Current timestamp
   * - Application version
   * - Environment
   *
   * @param _req - Express request object (unused)
   * @param res - Express response object
   * @param next - Express next function for error handling
   */
  public getHealth(_req: Request, res: Response, next: NextFunction): void {
    try {
      const healthService = getHealthService();
      const healthStatus = healthService.getHealthStatus();

      res.status(200).json(healthStatus);
    } catch (error) {
      const logger = getLogger();
      logger.error('Health check failed', {
        error: error instanceof Error ? error.message : 'Unknown error',
      });

      const healthService = getHealthService();
      const errorResponse = healthService.createErrorResponse(
        error instanceof Error ? error : undefined
      );

      res.status(500).json(errorResponse);
      next(error);
    }
  }
}

/**
 * Singleton instance of HealthController
 */
let healthControllerInstance: HealthController | null = null;

/**
 * Gets the singleton HealthController instance
 */
export function getHealthController(): HealthController {
  if (healthControllerInstance === null) {
    healthControllerInstance = new HealthController();
  }
  return healthControllerInstance;
}

/**
 * Resets the singleton (for testing purposes)
 */
export function resetHealthController(): void {
  healthControllerInstance = null;
}
