import { Request, Response, NextFunction } from 'express';
import {
  HealthController,
  getHealthController,
  resetHealthController,
} from '../../../src/presentation/controllers/healthController';
import {
  getHealthService,
} from '../../../src/application/services/healthService';
import { createMockRequest, createMockResponse, createMockNext } from '../../../test-utils/mocks';

// Mock the health service
jest.mock('../../../src/application/services/healthService', () => {
  const mockHealthService = {
    getHealthStatus: jest.fn(),
    createErrorResponse: jest.fn(),
  };

  return {
    getHealthService: jest.fn(() => mockHealthService),
    resetHealthService: jest.fn(),
    HealthService: jest.fn(() => mockHealthService),
  };
});

// Mock the logger
jest.mock('../../../src/infrastructure/logger', () => ({
  getLogger: jest.fn(() => ({
    error: jest.fn(),
    warn: jest.fn(),
    info: jest.fn(),
    http: jest.fn(),
    debug: jest.fn(),
  })),
  resetLogger: jest.fn(),
}));

// Mock the config
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
}));

describe('HealthController', () => {
  let controller: HealthController;
  let mockReq: Request;
  let mockRes: ReturnType<typeof createMockResponse>;
  let mockNext: NextFunction;
  let mockHealthService: {
    getHealthStatus: jest.Mock;
    createErrorResponse: jest.Mock;
  };

  beforeEach(() => {
    jest.clearAllMocks();
    resetHealthController();

    controller = new HealthController();
    mockReq = createMockRequest() as Request;
    mockRes = createMockResponse();
    mockNext = createMockNext();
    mockHealthService = getHealthService() as unknown as {
      getHealthStatus: jest.Mock;
      createErrorResponse: jest.Mock;
    };
  });

  describe('GET /api/health', () => {
    it('should return 200 with success response', async () => {
      const healthStatus = {
        success: true,
        message: 'Hola, soy Jaarvi',
        timestamp: '2026-01-31T12:00:00.000Z',
        version: '1.0.0',
        environment: 'test',
      };
      mockHealthService.getHealthStatus.mockReturnValue(healthStatus);

      await controller.getHealth(mockReq, mockRes as unknown as Response, mockNext);

      expect(mockRes.status).toHaveBeenCalledWith(200);
      expect(mockRes.json).toHaveBeenCalledWith(healthStatus);
    });

    it('should include message "Hola, soy Jaarvi"', async () => {
      const healthStatus = {
        success: true,
        message: 'Hola, soy Jaarvi',
        timestamp: '2026-01-31T12:00:00.000Z',
        version: '1.0.0',
        environment: 'test',
      };
      mockHealthService.getHealthStatus.mockReturnValue(healthStatus);

      await controller.getHealth(mockReq, mockRes as unknown as Response, mockNext);

      expect(mockRes.json).toHaveBeenCalledWith(
        expect.objectContaining({
          message: 'Hola, soy Jaarvi',
        })
      );
    });

    it('should include valid ISO timestamp', async () => {
      const timestamp = new Date().toISOString();
      const healthStatus = {
        success: true,
        message: 'Hola, soy Jaarvi',
        timestamp,
        version: '1.0.0',
        environment: 'test',
      };
      mockHealthService.getHealthStatus.mockReturnValue(healthStatus);

      await controller.getHealth(mockReq, mockRes as unknown as Response, mockNext);

      const calledWith = mockRes.json.mock.calls[0]?.[0] as { timestamp: string };
      expect(calledWith.timestamp).toBe(timestamp);
      expect(new Date(calledWith.timestamp).toISOString()).toBe(timestamp);
    });

    it('should include version and environment', async () => {
      const healthStatus = {
        success: true,
        message: 'Hola, soy Jaarvi',
        timestamp: '2026-01-31T12:00:00.000Z',
        version: '1.0.0',
        environment: 'test',
      };
      mockHealthService.getHealthStatus.mockReturnValue(healthStatus);

      await controller.getHealth(mockReq, mockRes as unknown as Response, mockNext);

      expect(mockRes.json).toHaveBeenCalledWith(
        expect.objectContaining({
          version: '1.0.0',
          environment: 'test',
        })
      );
    });

    it('should return 500 when health service throws error', async () => {
      const error = new Error('Database connection failed');
      mockHealthService.getHealthStatus.mockImplementation(() => {
        throw error;
      });
      mockHealthService.createErrorResponse.mockReturnValue({
        success: false,
        error: {
          message: 'Health check failed',
          code: 'HEALTH_CHECK_ERROR',
        },
      });

      await controller.getHealth(mockReq, mockRes as unknown as Response, mockNext);

      expect(mockRes.status).toHaveBeenCalledWith(500);
      expect(mockRes.json).toHaveBeenCalledWith({
        success: false,
        error: {
          message: 'Health check failed',
          code: 'HEALTH_CHECK_ERROR',
        },
      });
    });

    it('should call next with error when health check fails', async () => {
      const error = new Error('Database connection failed');
      mockHealthService.getHealthStatus.mockImplementation(() => {
        throw error;
      });
      mockHealthService.createErrorResponse.mockReturnValue({
        success: false,
        error: {
          message: 'Health check failed',
          code: 'HEALTH_CHECK_ERROR',
        },
      });

      await controller.getHealth(mockReq, mockRes as unknown as Response, mockNext);

      expect(mockNext).toHaveBeenCalledWith(error);
    });
  });

  describe('getHealthController', () => {
    it('should return singleton instance', () => {
      const instance1 = getHealthController();
      const instance2 = getHealthController();

      expect(instance1).toBe(instance2);
    });

    it('should return new instance after reset', () => {
      const instance1 = getHealthController();
      resetHealthController();
      const instance2 = getHealthController();

      expect(instance1).not.toBe(instance2);
    });
  });
});
