/**
 * Test Mocks Barrel Export
 *
 * Central export point for all test mocks and utilities.
 */

export {
  createMockPrismaClient,
  getMockPrismaClient,
  resetMockPrismaClient,
  clearMockPrismaCalls,
} from './prisma';

/**
 * Mock Express request object
 */
export function createMockRequest(overrides: Record<string, unknown> = {}): unknown {
  return {
    body: {},
    params: {},
    query: {},
    headers: {},
    get: jest.fn(),
    ip: '127.0.0.1',
    method: 'GET',
    path: '/',
    prisma: null,
    ...overrides,
  };
}

/**
 * Mock Express response object
 */
export function createMockResponse(): {
  status: jest.Mock;
  json: jest.Mock;
  send: jest.Mock;
  set: jest.Mock;
  on: jest.Mock;
  statusCode: number;
} {
  const res = {
    status: jest.fn(),
    json: jest.fn(),
    send: jest.fn(),
    set: jest.fn(),
    on: jest.fn(),
    statusCode: 200,
  };
  res.status.mockReturnValue(res);
  res.json.mockReturnValue(res);
  res.send.mockReturnValue(res);
  res.set.mockReturnValue(res);
  return res;
}

/**
 * Mock Express next function
 */
export function createMockNext(): jest.Mock {
  return jest.fn();
}

/**
 * Setup common test environment
 */
beforeEach(() => {
  jest.clearAllMocks();
});
