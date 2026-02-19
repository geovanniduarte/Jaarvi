import {
  validateEnvironment,
  isNonEmpty,
  isValidPort,
  isValidNodeEnv,
  constructDatabaseUrl,
} from '../../src/infrastructure/env';

describe('Environment Validation', () => {
  // Store original env to restore after tests
  const originalEnv = process.env;

  beforeEach(() => {
    // Reset process.env before each test
    jest.resetModules();
    process.env = {};
  });

  afterAll(() => {
    // Restore original environment
    process.env = originalEnv;
  });

  describe('isNonEmpty', () => {
    it('should return true for non-empty string', () => {
      expect(isNonEmpty('hello')).toBe(true);
    });

    it('should return false for empty string', () => {
      expect(isNonEmpty('')).toBe(false);
    });

    it('should return false for whitespace-only string', () => {
      expect(isNonEmpty('   ')).toBe(false);
    });

    it('should return false for undefined', () => {
      expect(isNonEmpty(undefined)).toBe(false);
    });
  });

  describe('isValidPort', () => {
    it('should return true for valid port string', () => {
      expect(isValidPort('3000')).toBe(true);
      expect(isValidPort('80')).toBe(true);
      expect(isValidPort('65535')).toBe(true);
    });

    it('should return false for invalid port string', () => {
      expect(isValidPort('0')).toBe(false);
      expect(isValidPort('-1')).toBe(false);
      expect(isValidPort('65536')).toBe(false);
      expect(isValidPort('abc')).toBe(false);
    });

    it('should return false for undefined', () => {
      expect(isValidPort(undefined)).toBe(false);
    });
  });

  describe('isValidNodeEnv', () => {
    it('should return true for valid NODE_ENV values', () => {
      expect(isValidNodeEnv('development')).toBe(true);
      expect(isValidNodeEnv('staging')).toBe(true);
      expect(isValidNodeEnv('production')).toBe(true);
      expect(isValidNodeEnv('test')).toBe(true);
    });

    it('should return false for invalid NODE_ENV values', () => {
      expect(isValidNodeEnv('invalid')).toBe(false);
      expect(isValidNodeEnv('')).toBe(false);
      expect(isValidNodeEnv(undefined)).toBe(false);
    });
  });

  describe('constructDatabaseUrl', () => {
    it('should construct correct DATABASE_URL', () => {
      const url = constructDatabaseUrl('user', 'pass', 'localhost', 5432, 'mydb');
      expect(url).toBe('postgresql://user:pass@localhost:5432/mydb');
    });

    it('should handle special characters in password', () => {
      const url = constructDatabaseUrl('user', 'p@ss:word', 'db.host.com', 5433, 'testdb');
      expect(url).toBe('postgresql://user:p@ss:word@db.host.com:5433/testdb');
    });
  });

  describe('validateEnvironment', () => {
    const validEnv = {
      DB_HOST: 'localhost',
      DB_USER: 'testuser',
      DB_PASSWORD: 'testpass',
      JWT_SECRET: 'this-is-a-very-long-secret-key-for-testing-purposes',
    };

    it('should pass with all required variables', () => {
      process.env = { ...process.env, ...validEnv };

      const result = validateEnvironment();

      expect(result.DB_HOST).toBe('localhost');
      expect(result.DB_USER).toBe('testuser');
      expect(result.DB_PASSWORD).toBe('testpass');
    });

    it('should throw error when DB_HOST is missing', () => {
      process.env = {
        ...process.env,
        DB_USER: 'testuser',
        DB_PASSWORD: 'testpass',
        JWT_SECRET: 'this-is-a-very-long-secret-key-for-testing-purposes',
      };

      expect(() => validateEnvironment()).toThrow('DB_HOST is required');
    });

    it('should throw error when DB_USER is missing', () => {
      process.env = {
        ...process.env,
        DB_HOST: 'localhost',
        DB_PASSWORD: 'testpass',
        JWT_SECRET: 'this-is-a-very-long-secret-key-for-testing-purposes',
      };

      expect(() => validateEnvironment()).toThrow('DB_USER is required');
    });

    it('should throw error when DB_PASSWORD is missing', () => {
      process.env = {
        ...process.env,
        DB_HOST: 'localhost',
        DB_USER: 'testuser',
        JWT_SECRET: 'this-is-a-very-long-secret-key-for-testing-purposes',
      };

      expect(() => validateEnvironment()).toThrow('DB_PASSWORD is required');
    });

    it('should throw error when JWT_SECRET is missing', () => {
      process.env = {
        ...process.env,
        DB_HOST: 'localhost',
        DB_USER: 'testuser',
        DB_PASSWORD: 'testpass',
      };

      expect(() => validateEnvironment()).toThrow('JWT_SECRET is required');
    });

    it('should throw error when JWT_SECRET is too short', () => {
      process.env = {
        ...process.env,
        DB_HOST: 'localhost',
        DB_USER: 'testuser',
        DB_PASSWORD: 'testpass',
        JWT_SECRET: 'short',
      };

      expect(() => validateEnvironment()).toThrow('JWT_SECRET must be at least 32 characters');
    });

    it('should validate NODE_ENV values', () => {
      process.env = {
        ...process.env,
        ...validEnv,
        NODE_ENV: 'invalid',
      };

      expect(() => validateEnvironment()).toThrow(
        'NODE_ENV must be one of: development, staging, production, test'
      );
    });

    it('should use default PORT if not provided', () => {
      process.env = { ...process.env, ...validEnv };
      delete process.env.PORT;

      const result = validateEnvironment();

      expect(result.PORT).toBe(3000);
    });

    it('should use default SERVER_HOST (0.0.0.0) if not provided', () => {
      process.env = { ...process.env, ...validEnv };
      delete process.env.SERVER_HOST;

      const result = validateEnvironment();

      expect(result.SERVER_HOST).toBe('0.0.0.0');
    });

    it('should use default DB_PORT (5432) if not provided', () => {
      process.env = { ...process.env, ...validEnv };
      delete process.env.DB_PORT;

      const result = validateEnvironment();

      expect(result.DB_PORT).toBe(5432);
    });

    it('should use default DB_NAME (jaarvi_dev) if not provided', () => {
      process.env = { ...process.env, ...validEnv };
      delete process.env.DB_NAME;

      const result = validateEnvironment();

      expect(result.DB_NAME).toBe('jaarvi_dev');
    });

    it('should construct DATABASE_URL from DB_* variables', () => {
      process.env = {
        ...process.env,
        ...validEnv,
        DB_PORT: '5433',
        DB_NAME: 'customdb',
      };

      const result = validateEnvironment();

      expect(result.DATABASE_URL).toBe('postgresql://testuser:testpass@localhost:5433/customdb');
    });

    it('should parse ALLOWED_ORIGINS as array', () => {
      process.env = {
        ...process.env,
        ...validEnv,
        ALLOWED_ORIGINS: 'http://localhost:3000,http://localhost:19006',
      };

      const result = validateEnvironment();

      expect(result.ALLOWED_ORIGINS).toEqual(['http://localhost:3000', 'http://localhost:19006']);
    });

    it('should handle empty ALLOWED_ORIGINS', () => {
      process.env = { ...process.env, ...validEnv, ALLOWED_ORIGINS: '' };

      const result = validateEnvironment();

      expect(result.ALLOWED_ORIGINS).toEqual([]);
    });
  });
});
