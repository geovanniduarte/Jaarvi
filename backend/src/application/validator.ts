import { ValidationError } from '../domain/errors';

/**
 * Validation result type
 */
interface ValidationResult {
  isValid: boolean;
  errors: Record<string, string[]>;
}

/**
 * Creates a new validation result
 */
function createValidationResult(): ValidationResult {
  return {
    isValid: true,
    errors: {},
  };
}

/**
 * Adds an error to the validation result
 */
function addError(result: ValidationResult, field: string, message: string): void {
  result.isValid = false;
  if (!result.errors[field]) {
    result.errors[field] = [];
  }
  result.errors[field].push(message);
}

/**
 * Validates that a value is a non-empty string
 */
export function isNonEmptyString(value: unknown): value is string {
  return typeof value === 'string' && value.trim().length > 0;
}

/**
 * Validates that a value is a valid email format
 */
export function isValidEmail(value: unknown): boolean {
  if (typeof value !== 'string') return false;
  // Simple email regex - more complex validation should be done at email verification
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(value);
}

/**
 * Validates that a value is a positive integer
 */
export function isPositiveInteger(value: unknown): boolean {
  if (typeof value === 'number') {
    return Number.isInteger(value) && value > 0;
  }
  if (typeof value === 'string') {
    const parsed = parseInt(value, 10);
    return !isNaN(parsed) && parsed > 0 && parsed.toString() === value;
  }
  return false;
}

/**
 * Validates that a value is a valid UUID v4
 */
export function isValidUUID(value: unknown): boolean {
  if (typeof value !== 'string') return false;
  const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i;
  return uuidRegex.test(value);
}

/**
 * Validates that a value is a valid ISO 8601 date string
 */
export function isValidISODate(value: unknown): boolean {
  if (typeof value !== 'string') return false;
  const date = new Date(value);
  return !isNaN(date.getTime()) && value === date.toISOString().split('T')[0];
}

/**
 * Validates that a string has minimum length
 */
export function hasMinLength(value: unknown, minLength: number): boolean {
  return typeof value === 'string' && value.length >= minLength;
}

/**
 * Validates that a string has maximum length
 */
export function hasMaxLength(value: unknown, maxLength: number): boolean {
  return typeof value === 'string' && value.length <= maxLength;
}

/**
 * Validates that a value is in an allowed list
 */
export function isOneOf<T>(value: unknown, allowedValues: T[]): boolean {
  return allowedValues.includes(value as T);
}

/**
 * Validates required fields in an object
 */
export function validateRequiredFields(
  data: Record<string, unknown>,
  requiredFields: string[]
): ValidationResult {
  const result = createValidationResult();

  for (const field of requiredFields) {
    const value = data[field];
    if (value === undefined || value === null || (typeof value === 'string' && value.trim() === '')) {
      addError(result, field, `${field} is required`);
    }
  }

  return result;
}

/**
 * Throws ValidationError if validation result is invalid
 */
export function throwIfInvalid(result: ValidationResult, message = 'Validation failed'): void {
  if (!result.isValid) {
    throw new ValidationError(message, result.errors);
  }
}

/**
 * Export validation utilities
 */
export { ValidationResult, createValidationResult, addError };
