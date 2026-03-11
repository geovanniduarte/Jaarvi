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

/**
 * Validates the request body for creating a trip.
 * Throws ValidationError if invalid.
 */
export function validateCreateTrip(data: unknown): void {
  const body = data as Record<string, unknown>;
  const result = createValidationResult();

  if (!body.startDate) {
    addError(result, 'startDate', 'startDate is required');
  } else if (!isValidISODate(body.startDate)) {
    addError(result, 'startDate', 'startDate must be a valid date (YYYY-MM-DD)');
  } else {
    const start = new Date(body.startDate as string);
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    if (start < today) {
      addError(result, 'startDate', 'startDate must be today or in the future');
    }
  }

  if (!body.endDate) {
    addError(result, 'endDate', 'endDate is required');
  } else if (!isValidISODate(body.endDate)) {
    addError(result, 'endDate', 'endDate must be a valid date (YYYY-MM-DD)');
  } else if (body.startDate && isValidISODate(body.startDate)) {
    const start = new Date(body.startDate as string);
    const end = new Date(body.endDate as string);
    if (end <= start) {
      addError(result, 'endDate', 'endDate must be strictly after startDate');
    }
  }

  if (body.name !== undefined && body.name !== null) {
    if (typeof body.name !== 'string') {
      addError(result, 'name', 'name must be a string');
    } else if (!hasMaxLength(body.name, 100)) {
      addError(result, 'name', 'name must not exceed 100 characters');
    }
  }

  throwIfInvalid(result, 'Trip validation failed');
}

/**
 * Validates the request body for adding a destination to a trip.
 * Throws ValidationError if invalid.
 */
export function validateAddDestination(data: unknown): void {
  const body = data as Record<string, unknown>;
  const result = createValidationResult();

  if (!body.cityId || !isValidUUID(body.cityId)) {
    addError(result, 'cityId', 'cityId is required and must be a valid UUID');
  }

  if (body.dayOrder === undefined || body.dayOrder === null) {
    addError(result, 'dayOrder', 'dayOrder is required');
  } else if (!isPositiveInteger(body.dayOrder)) {
    addError(result, 'dayOrder', 'dayOrder must be a positive integer');
  }

  if (body.daysCount === undefined || body.daysCount === null) {
    addError(result, 'daysCount', 'daysCount is required');
  } else if (!isPositiveInteger(body.daysCount)) {
    addError(result, 'daysCount', 'daysCount must be a positive integer');
  }

  throwIfInvalid(result, 'Destination validation failed');
}

const VALID_TRAVEL_STYLES = ['adventurous', 'cultural', 'relaxed', 'mixed'];
const VALID_BUDGETS = ['budget', 'moderate', 'premium'];
const VALID_PACES = ['slow', 'medium', 'fast'];
const VALID_INTERESTS = ['museums', 'food', 'nature', 'nightlife', 'shopping', 'outdoor'];

/**
 * Validates the request body for saving a planning context.
 * All fields are optional; only validates format when present.
 * Throws ValidationError if invalid.
 */
export function validatePlanningContext(data: unknown): void {
  const body = data as Record<string, unknown>;
  const result = createValidationResult();

  if (body.travelStyle !== undefined && body.travelStyle !== null) {
    if (!isOneOf(body.travelStyle, VALID_TRAVEL_STYLES)) {
      addError(result, 'travelStyle', `travelStyle must be one of: ${VALID_TRAVEL_STYLES.join(', ')}`);
    }
  }

  if (body.budget !== undefined && body.budget !== null) {
    if (!isOneOf(body.budget, VALID_BUDGETS)) {
      addError(result, 'budget', `budget must be one of: ${VALID_BUDGETS.join(', ')}`);
    }
  }

  if (body.pace !== undefined && body.pace !== null) {
    if (!isOneOf(body.pace, VALID_PACES)) {
      addError(result, 'pace', `pace must be one of: ${VALID_PACES.join(', ')}`);
    }
  }

  if (body.interests !== undefined && body.interests !== null) {
    if (!Array.isArray(body.interests)) {
      addError(result, 'interests', 'interests must be an array');
    } else {
      const invalid = (body.interests as unknown[]).filter(
        (i) => !VALID_INTERESTS.includes(i as string)
      );
      if (invalid.length > 0) {
        addError(result, 'interests', `interests contains invalid values: ${invalid.join(', ')}`);
      }
    }
  }

  if (body.specialRequirements !== undefined && body.specialRequirements !== null) {
    if (typeof body.specialRequirements !== 'string') {
      addError(result, 'specialRequirements', 'specialRequirements must be a string');
    } else if (!hasMaxLength(body.specialRequirements, 500)) {
      addError(result, 'specialRequirements', 'specialRequirements must not exceed 500 characters');
    }
  }

  throwIfInvalid(result, 'Planning context validation failed');
}
