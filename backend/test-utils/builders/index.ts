/**
 * Test Data Builders
 *
 * Builder pattern implementations for creating test data.
 * Builders provide a fluent API for creating realistic test fixtures.
 *
 * Usage:
 * const user = new UserBuilder().withEmail('test@example.com').build();
 */

/**
 * Base builder interface
 */
interface Builder<T> {
  build(): T;
}

/**
 * Health status builder for testing health service responses
 */
export class HealthStatusBuilder implements Builder<{
  success: boolean;
  message: string;
  timestamp: string;
  version: string;
  environment: string;
}> {
  private success = true;
  private message = 'Hola, soy Jaarvi';
  private timestamp = new Date().toISOString();
  private version = '1.0.0';
  private environment = 'test';

  public withSuccess(success: boolean): this {
    this.success = success;
    return this;
  }

  public withMessage(message: string): this {
    this.message = message;
    return this;
  }

  public withTimestamp(timestamp: string): this {
    this.timestamp = timestamp;
    return this;
  }

  public withVersion(version: string): this {
    this.version = version;
    return this;
  }

  public withEnvironment(environment: string): this {
    this.environment = environment;
    return this;
  }

  public build(): {
    success: boolean;
    message: string;
    timestamp: string;
    version: string;
    environment: string;
  } {
    return {
      success: this.success,
      message: this.message,
      timestamp: this.timestamp,
      version: this.version,
      environment: this.environment,
    };
  }
}

/**
 * Error response builder for testing error responses
 */
export class ErrorResponseBuilder implements Builder<{
  success: false;
  error: {
    message: string;
    code: string;
    details?: Record<string, string[]>;
  };
}> {
  private message = 'An error occurred';
  private code = 'ERROR';
  private details: Record<string, string[]> | undefined = undefined;

  public withMessage(message: string): this {
    this.message = message;
    return this;
  }

  public withCode(code: string): this {
    this.code = code;
    return this;
  }

  public withDetails(details: Record<string, string[]>): this {
    this.details = details;
    return this;
  }

  public build(): {
    success: false;
    error: {
      message: string;
      code: string;
      details?: Record<string, string[]>;
    };
  } {
    const response: {
      success: false;
      error: {
        message: string;
        code: string;
        details?: Record<string, string[]>;
      };
    } = {
      success: false,
      error: {
        message: this.message,
        code: this.code,
      },
    };

    if (this.details) {
      response.error.details = this.details;
    }

    return response;
  }
}

// Future builders will be added here as models are implemented:
// export { UserBuilder } from './UserBuilder';
// export { TripBuilder } from './TripBuilder';
// export { DestinationBuilder } from './DestinationBuilder';
