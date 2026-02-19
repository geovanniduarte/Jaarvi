import { Router } from 'express';
import { getHealthController } from '../presentation/controllers/healthController';

/**
 * Health check routes.
 *
 * Provides endpoints for monitoring application health.
 * These endpoints are typically used by:
 * - Load balancers for health checks
 * - Kubernetes liveness/readiness probes
 * - Monitoring systems
 * - Deployment verification scripts
 */
const healthRouter = Router();

/**
 * GET /api/health
 *
 * Returns the current health status of the application.
 *
 * @response 200 - Application is healthy
 * @response 500 - Health check failed
 */
healthRouter.get('/', (req, res, next) => {
  const controller = getHealthController();
  void controller.getHealth(req, res, next);
});

export { healthRouter };
