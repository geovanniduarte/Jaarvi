import { Logger, getLogger, resetLogger, createLogger } from '../../src/infrastructure/logger';

describe('Logger', () => {
  beforeEach(() => {
    resetLogger();
  });

  it('should construct Logger in test mode', () => {
    const logger = new Logger('test', 'info');
    expect(logger).toBeInstanceOf(Logger);
  });

  it('should construct Logger in development mode', () => {
    const logger = new Logger('development', 'debug');
    expect(logger).toBeInstanceOf(Logger);
  });

  it('should construct Logger in production mode', () => {
    const logger = new Logger('production', 'warn');
    expect(logger).toBeInstanceOf(Logger);
  });

  it('error() should not throw', () => {
    const logger = new Logger('test');
    expect(() => logger.error('test error')).not.toThrow();
  });

  it('warn() should not throw', () => {
    const logger = new Logger('test');
    expect(() => logger.warn('test warn')).not.toThrow();
  });

  it('info() should not throw', () => {
    const logger = new Logger('test');
    expect(() => logger.info('test info')).not.toThrow();
  });

  it('http() should not throw', () => {
    const logger = new Logger('test');
    expect(() => logger.http('test http')).not.toThrow();
  });

  it('debug() should not throw', () => {
    const logger = new Logger('test');
    expect(() => logger.debug('test debug')).not.toThrow();
  });

  it('should accept metadata in log calls', () => {
    const logger = new Logger('test');
    expect(() => logger.error('error with meta', { key: 'value' })).not.toThrow();
    expect(() => logger.info('info with meta', { count: 42 })).not.toThrow();
  });

  describe('getLogger', () => {
    it('should return singleton instance', () => {
      const l1 = getLogger();
      const l2 = getLogger();
      expect(l1).toBe(l2);
    });

    it('should return new instance after reset', () => {
      const l1 = getLogger();
      resetLogger();
      const l2 = getLogger();
      expect(l1).not.toBe(l2);
    });

    it('should use LOG_LEVEL env variable', () => {
      process.env.LOG_LEVEL = 'debug';
      const logger = getLogger();
      expect(logger).toBeInstanceOf(Logger);
      delete process.env.LOG_LEVEL;
    });
  });

  describe('createLogger', () => {
    it('should create a logger for test environment', () => {
      const logger = createLogger('test', 'info');
      expect(logger).toBeDefined();
    });

    it('should create a logger for development environment', () => {
      const logger = createLogger('development', 'debug');
      expect(logger).toBeDefined();
    });

    it('should create a logger for production environment', () => {
      const logger = createLogger('production', 'error');
      expect(logger).toBeDefined();
    });
  });
});
