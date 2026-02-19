import { HealthService, getHealthService, resetHealthService } from '../../../src/application/services/healthService';

// Mock the config module
jest.mock('../../../src/infrastructure/config', () => ({
  getConfig: jest.fn(() => ({
    server: {
      nodeEnv: 'test',
      port: 3000,
      host: '0.0.0.0',
      isProduction: false,
      isDevelopment: false,
      isTest: true,
    },
  })),
  Config: {
    getInstance: jest.fn(),
    reset: jest.fn(),
  },
}));

describe('HealthService', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    resetHealthService();
  });

  describe('getHealthStatus', () => {
    it('should return health status object', () => {
      const healthService = new HealthService();
      const result = healthService.getHealthStatus();

      expect(result).toHaveProperty('success');
      expect(result).toHaveProperty('message');
      expect(result).toHaveProperty('timestamp');
      expect(result).toHaveProperty('version');
      expect(result).toHaveProperty('environment');
    });

    it('should include message "Hola, soy Jaarvi"', () => {
      const healthService = new HealthService();
      const result = healthService.getHealthStatus();

      expect(result.message).toBe('Hola, soy Jaarvi');
    });

    it('should include current timestamp', () => {
      const healthService = new HealthService();
      const before = new Date();
      const result = healthService.getHealthStatus();
      const after = new Date();

      const timestamp = new Date(result.timestamp);
      expect(timestamp.getTime()).toBeGreaterThanOrEqual(before.getTime());
      expect(timestamp.getTime()).toBeLessThanOrEqual(after.getTime());
    });

    it('should include valid ISO timestamp', () => {
      const healthService = new HealthService();
      const result = healthService.getHealthStatus();

      // ISO 8601 format check
      const isoRegex = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}.\d{3}Z$/;
      expect(result.timestamp).toMatch(isoRegex);
    });

    it('should read version from package.json', () => {
      const healthService = new HealthService();
      const result = healthService.getHealthStatus();

      expect(result.version).toBe('1.0.0');
    });

    it('should include environment from config', () => {
      const healthService = new HealthService();
      const result = healthService.getHealthStatus();

      expect(result.environment).toBe('test');
    });

    it('should return success as true', () => {
      const healthService = new HealthService();
      const result = healthService.getHealthStatus();

      expect(result.success).toBe(true);
    });
  });

  describe('createErrorResponse', () => {
    it('should create error response with correct structure', () => {
      const healthService = new HealthService();
      const result = healthService.createErrorResponse();

      expect(result).toEqual({
        success: false,
        error: {
          message: 'Health check failed',
          code: 'HEALTH_CHECK_ERROR',
        },
      });
    });

    it('should return success as false', () => {
      const healthService = new HealthService();
      const result = healthService.createErrorResponse();

      expect(result.success).toBe(false);
    });

    it('should include error code HEALTH_CHECK_ERROR', () => {
      const healthService = new HealthService();
      const result = healthService.createErrorResponse();

      expect(result.error.code).toBe('HEALTH_CHECK_ERROR');
    });
  });

  describe('getHealthService', () => {
    it('should return singleton instance', () => {
      const instance1 = getHealthService();
      const instance2 = getHealthService();

      expect(instance1).toBe(instance2);
    });

    it('should return new instance after reset', () => {
      const instance1 = getHealthService();
      resetHealthService();
      const instance2 = getHealthService();

      expect(instance1).not.toBe(instance2);
    });
  });
});
