import { PrismaClient } from '@prisma/client';

/**
 * Type for mocked Prisma client methods.
 * Provides Jest mock functions for all Prisma operations.
 */
type MockPrismaClient = {
  [K in keyof PrismaClient]: PrismaClient[K] extends (...args: unknown[]) => unknown
    ? jest.Mock
    : MockPrismaClient[K];
};

/**
 * Creates a deep mock of the Prisma client.
 *
 * This mock can be used in unit tests to simulate database operations
 * without actual database connections.
 *
 * @returns Mocked Prisma client
 *
 * @example
 * const mockPrisma = createMockPrismaClient();
 * mockPrisma.user.findUnique.mockResolvedValue({ id: '1', email: 'test@example.com' });
 */
export function createMockPrismaClient(): MockPrismaClient {
  return {
    $connect: jest.fn().mockResolvedValue(undefined),
    $disconnect: jest.fn().mockResolvedValue(undefined),
    $executeRaw: jest.fn(),
    $executeRawUnsafe: jest.fn(),
    $queryRaw: jest.fn(),
    $queryRawUnsafe: jest.fn(),
    $transaction: jest.fn(),
    $on: jest.fn(),
    $use: jest.fn(),
    $extends: jest.fn(),
    // Add model mocks as needed when models are implemented
    // user: {
    //   findUnique: jest.fn(),
    //   findMany: jest.fn(),
    //   create: jest.fn(),
    //   update: jest.fn(),
    //   delete: jest.fn(),
    // },
  } as unknown as MockPrismaClient;
}

/**
 * Singleton mock instance for tests
 */
let mockPrismaInstance: MockPrismaClient | null = null;

/**
 * Gets or creates the mock Prisma client singleton
 */
export function getMockPrismaClient(): MockPrismaClient {
  if (mockPrismaInstance === null) {
    mockPrismaInstance = createMockPrismaClient();
  }
  return mockPrismaInstance;
}

/**
 * Resets the mock Prisma client (call in beforeEach/afterEach)
 */
export function resetMockPrismaClient(): void {
  mockPrismaInstance = null;
}

/**
 * Clears all mock calls without resetting the mock itself
 */
export function clearMockPrismaCalls(): void {
  if (mockPrismaInstance) {
    jest.clearAllMocks();
  }
}
