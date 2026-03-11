import { getPrismaClient, resetPrismaClient, disconnectPrisma } from '../../src/infrastructure/prismaClient';

jest.mock('@prisma/client', () => {
  const mockPrismaClient = jest.fn().mockImplementation(() => ({
    $on: jest.fn(),
    $disconnect: jest.fn().mockResolvedValue(undefined),
  }));
  return { PrismaClient: mockPrismaClient, Prisma: { PrismaClientOptions: {} } };
});

describe('prismaClient', () => {
  beforeEach(() => {
    resetPrismaClient();
  });

  afterEach(() => {
    resetPrismaClient();
  });

  it('getPrismaClient() should return a client instance', () => {
    const client = getPrismaClient();
    expect(client).toBeDefined();
  });

  it('getPrismaClient() should return the same singleton instance', () => {
    const c1 = getPrismaClient();
    const c2 = getPrismaClient();
    expect(c1).toBe(c2);
  });

  it('resetPrismaClient() should clear the singleton', () => {
    const c1 = getPrismaClient();
    resetPrismaClient();
    const c2 = getPrismaClient();
    expect(c1).not.toBe(c2);
  });

  it('disconnectPrisma() should disconnect when instance exists', async () => {
    const client = getPrismaClient();
    await expect(disconnectPrisma()).resolves.not.toThrow();
    expect(client.$disconnect).toHaveBeenCalled();
  });

  it('disconnectPrisma() should do nothing when no instance', async () => {
    await expect(disconnectPrisma()).resolves.not.toThrow();
  });
});
