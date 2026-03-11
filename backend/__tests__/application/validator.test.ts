import {
  isNonEmptyString,
  isValidEmail,
  isPositiveInteger,
  isValidUUID,
  isValidISODate,
  hasMinLength,
  hasMaxLength,
  isOneOf,
  validateRequiredFields,
  throwIfInvalid,
  createValidationResult,
  addError,
  validateCreateTrip,
  validateAddDestination,
  validatePlanningContext,
} from '../../src/application/validator';
import { ValidationError } from '../../src/domain/errors';

describe('Validator utilities', () => {
  describe('isNonEmptyString', () => {
    it('should return true for a non-empty string', () => {
      expect(isNonEmptyString('hello')).toBe(true);
    });

    it('should return false for empty string', () => {
      expect(isNonEmptyString('')).toBe(false);
    });

    it('should return false for whitespace-only string', () => {
      expect(isNonEmptyString('   ')).toBe(false);
    });

    it('should return false for non-string values', () => {
      expect(isNonEmptyString(42)).toBe(false);
      expect(isNonEmptyString(null)).toBe(false);
      expect(isNonEmptyString(undefined)).toBe(false);
    });
  });

  describe('isValidEmail', () => {
    it('should return true for valid email', () => {
      expect(isValidEmail('user@example.com')).toBe(true);
    });

    it('should return false for invalid email', () => {
      expect(isValidEmail('not-an-email')).toBe(false);
      expect(isValidEmail('missing@')).toBe(false);
    });

    it('should return false for non-string values', () => {
      expect(isValidEmail(123)).toBe(false);
      expect(isValidEmail(null)).toBe(false);
    });
  });

  describe('isPositiveInteger', () => {
    it('should return true for a positive integer number', () => {
      expect(isPositiveInteger(5)).toBe(true);
      expect(isPositiveInteger(1)).toBe(true);
    });

    it('should return true for a positive integer string', () => {
      expect(isPositiveInteger('3')).toBe(true);
    });

    it('should return false for zero', () => {
      expect(isPositiveInteger(0)).toBe(false);
    });

    it('should return false for negative numbers', () => {
      expect(isPositiveInteger(-1)).toBe(false);
    });

    it('should return false for floats', () => {
      expect(isPositiveInteger(1.5)).toBe(false);
    });

    it('should return false for non-numeric strings', () => {
      expect(isPositiveInteger('abc')).toBe(false);
    });

    it('should return false for non-integer numeric string (e.g., "1.5")', () => {
      expect(isPositiveInteger('1.5')).toBe(false);
    });

    it('should return false for other types', () => {
      expect(isPositiveInteger(null)).toBe(false);
      expect(isPositiveInteger({})).toBe(false);
    });
  });

  describe('isValidUUID', () => {
    it('should return true for valid UUID v4', () => {
      expect(isValidUUID('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11')).toBe(true);
    });

    it('should return false for invalid UUID', () => {
      expect(isValidUUID('not-a-uuid')).toBe(false);
      expect(isValidUUID('')).toBe(false);
    });

    it('should return false for non-string values', () => {
      expect(isValidUUID(null)).toBe(false);
      expect(isValidUUID(123)).toBe(false);
    });
  });

  describe('isValidISODate', () => {
    it('should return true for valid ISO date string', () => {
      expect(isValidISODate('2026-06-01')).toBe(true);
    });

    it('should return false for invalid date string', () => {
      expect(isValidISODate('not-a-date')).toBe(false);
      expect(isValidISODate('2026-13-01')).toBe(false);
    });

    it('should return false for datetime strings', () => {
      expect(isValidISODate('2026-06-01T10:00:00Z')).toBe(false);
    });

    it('should return false for non-string values', () => {
      expect(isValidISODate(null)).toBe(false);
      expect(isValidISODate(20260601)).toBe(false);
    });
  });

  describe('hasMinLength', () => {
    it('should return true when string meets minimum length', () => {
      expect(hasMinLength('hello', 3)).toBe(true);
      expect(hasMinLength('hi', 2)).toBe(true);
    });

    it('should return false when string is shorter than minimum', () => {
      expect(hasMinLength('hi', 5)).toBe(false);
    });

    it('should return false for non-string values', () => {
      expect(hasMinLength(null, 1)).toBe(false);
    });
  });

  describe('hasMaxLength', () => {
    it('should return true when string is within max length', () => {
      expect(hasMaxLength('hello', 10)).toBe(true);
    });

    it('should return false when string exceeds max length', () => {
      expect(hasMaxLength('hello world', 5)).toBe(false);
    });

    it('should return false for non-string values', () => {
      expect(hasMaxLength(null, 10)).toBe(false);
    });
  });

  describe('isOneOf', () => {
    it('should return true when value is in allowed list', () => {
      expect(isOneOf('a', ['a', 'b', 'c'])).toBe(true);
    });

    it('should return false when value is not in allowed list', () => {
      expect(isOneOf('d', ['a', 'b', 'c'])).toBe(false);
    });
  });

  describe('validateRequiredFields', () => {
    it('should return valid when all fields are present', () => {
      const result = validateRequiredFields({ name: 'Test', email: 'a@b.com' }, ['name', 'email']);
      expect(result.isValid).toBe(true);
    });

    it('should return invalid when a required field is missing', () => {
      const result = validateRequiredFields({ name: '' }, ['name', 'email']);
      expect(result.isValid).toBe(false);
      expect(result.errors['name']).toBeDefined();
      expect(result.errors['email']).toBeDefined();
    });

    it('should report error for null field', () => {
      const result = validateRequiredFields({ name: null }, ['name']);
      expect(result.isValid).toBe(false);
    });
  });

  describe('createValidationResult and addError', () => {
    it('should create valid result', () => {
      const result = createValidationResult();
      expect(result.isValid).toBe(true);
      expect(result.errors).toEqual({});
    });

    it('should add error and mark invalid', () => {
      const result = createValidationResult();
      addError(result, 'field', 'is required');
      addError(result, 'field', 'second error');
      expect(result.isValid).toBe(false);
      expect(result.errors['field']).toHaveLength(2);
    });
  });

  describe('throwIfInvalid', () => {
    it('should not throw when result is valid', () => {
      const result = createValidationResult();
      expect(() => throwIfInvalid(result)).not.toThrow();
    });

    it('should throw ValidationError when result is invalid', () => {
      const result = createValidationResult();
      addError(result, 'field', 'error');
      expect(() => throwIfInvalid(result)).toThrow(ValidationError);
    });

    it('should use custom message', () => {
      const result = createValidationResult();
      addError(result, 'field', 'error');
      expect(() => throwIfInvalid(result, 'Custom error')).toThrow('Custom error');
    });
  });
});

describe('validateCreateTrip', () => {
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  const tomorrow = new Date(today);
  tomorrow.setDate(tomorrow.getDate() + 1);
  const dayAfterTomorrow = new Date(today);
  dayAfterTomorrow.setDate(dayAfterTomorrow.getDate() + 2);

  function fmt(d: Date): string { return d.toISOString().split('T')[0] as string; }

  it('should pass with valid data', () => {
    expect(() => validateCreateTrip({ startDate: fmt(tomorrow), endDate: fmt(dayAfterTomorrow) })).not.toThrow();
  });

  it('should throw when startDate is invalid format', () => {
    expect(() => validateCreateTrip({ startDate: 'invalid', endDate: fmt(dayAfterTomorrow) })).toThrow(ValidationError);
  });

  it('should throw when endDate is invalid format', () => {
    expect(() => validateCreateTrip({ startDate: fmt(tomorrow), endDate: 'bad' })).toThrow(ValidationError);
  });

  it('should throw when name is not a string', () => {
    expect(() => validateCreateTrip({ startDate: fmt(tomorrow), endDate: fmt(dayAfterTomorrow), name: 123 })).toThrow(ValidationError);
  });
});

describe('validateAddDestination', () => {
  const CITY_UUID = 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';

  it('should pass with valid data', () => {
    expect(() => validateAddDestination({ cityId: CITY_UUID, dayOrder: 1, daysCount: 2 })).not.toThrow();
  });

  it('should throw when cityId is missing', () => {
    expect(() => validateAddDestination({ dayOrder: 1, daysCount: 2 })).toThrow(ValidationError);
  });

  it('should throw when dayOrder is missing', () => {
    expect(() => validateAddDestination({ cityId: CITY_UUID, daysCount: 2 })).toThrow(ValidationError);
  });

  it('should throw when daysCount is missing', () => {
    expect(() => validateAddDestination({ cityId: CITY_UUID, dayOrder: 1 })).toThrow(ValidationError);
  });
});

describe('validatePlanningContext', () => {
  it('should pass with empty body', () => {
    expect(() => validatePlanningContext({})).not.toThrow();
  });

  it('should throw when budget is invalid', () => {
    expect(() => validatePlanningContext({ budget: 'extreme' })).toThrow(ValidationError);
  });

  it('should throw when pace is invalid', () => {
    expect(() => validatePlanningContext({ pace: 'turbo' })).toThrow(ValidationError);
  });

  it('should throw when interests is not an array', () => {
    expect(() => validatePlanningContext({ interests: 'museums' })).toThrow(ValidationError);
  });

  it('should throw when specialRequirements is not a string', () => {
    expect(() => validatePlanningContext({ specialRequirements: 42 })).toThrow(ValidationError);
  });

  it('should pass with all valid preferences', () => {
    expect(() => validatePlanningContext({
      travelStyle: 'cultural',
      budget: 'moderate',
      pace: 'medium',
      interests: ['museums', 'food'],
      specialRequirements: 'none',
    })).not.toThrow();
  });
});
