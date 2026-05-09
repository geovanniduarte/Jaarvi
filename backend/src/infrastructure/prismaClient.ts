import { Prisma, PrismaClient } from '@prisma/client';
import { getConfig } from './config';
import { getLogger } from './logger';

type PrismaClientWithLogs = PrismaClient<Prisma.PrismaClientOptions, 'query' | 'error' | 'warn'>;

/**
 * Prisma client singleton instance.
 * Lazy-initialized to avoid connection before configuration is validated.
 */
let prismaInstance: PrismaClientWithLogs | null = null;

/**
 * Gets the singleton Prisma client instance.
 * Creates and connects on first access.
 *
 * Uses {@link getConfig}().database.url as `datasourceUrl` so runtime connections
 * follow `DB_*` (e.g. `DB_HOST=postgres` in Kubernetes) instead of a stale
 * `process.env.DATABASE_URL` from the Secret.
 *
 * Note: The Prisma client handles connection pooling automatically.
 * Connection is established lazily on first query, not on instantiation.
 */
export function getPrismaClient(): PrismaClientWithLogs {
  if (prismaInstance === null) {
    const logger = getLogger();

    prismaInstance = new PrismaClient({
      datasourceUrl: getConfig().database.url,
      log: [
        { level: 'query', emit: 'event' },
        { level: 'error', emit: 'event' },
        { level: 'warn', emit: 'event' },
      ] as const,
    }) as PrismaClientWithLogs;

    // Log database events in development
    if (process.env.NODE_ENV === 'development') {
      prismaInstance.$on('query', (e: Prisma.QueryEvent) => {
        logger.debug('Prisma Query', {
          query: e.query,
          params: e.params,
          duration: `${e.duration}ms`,
        });
      });

      prismaInstance.$on('error', (e: Prisma.LogEvent) => {
        logger.error('Prisma Error', { message: e.message });
      });

      prismaInstance.$on('warn', (e: Prisma.LogEvent) => {
        logger.warn('Prisma Warning', { message: e.message });
      });
    }
  }

  return prismaInstance;
}

/**
 * Disconnects the Prisma client.
 * Should be called during graceful shutdown.
 */
export async function disconnectPrisma(): Promise<void> {
  if (prismaInstance !== null) {
    const logger = getLogger();
    logger.info('Disconnecting Prisma client...');
    await prismaInstance.$disconnect();
    prismaInstance = null;
    logger.info('Prisma client disconnected');
  }
}

/**
 * Resets the Prisma client singleton (for testing purposes).
 * Does NOT disconnect - just clears the reference.
 */
export function resetPrismaClient(): void {
  prismaInstance = null;
}

/**
 * Type export for Prisma client
 */
export type { PrismaClient };
