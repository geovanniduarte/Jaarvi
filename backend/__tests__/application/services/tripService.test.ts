import {
  TripService,
  getTripService,
  resetTripService,
} from '../../../src/application/services/tripService';
import { Trip } from '../../../src/domain/models/Trip';
import { TripDestination } from '../../../src/domain/models/TripDestination';
import { TripPlanningContext } from '../../../src/domain/models/TripPlanningContext';
import { City } from '../../../src/domain/models/City';
import { ValidationError, NotFoundError, ForbiddenError, ConflictError } from '../../../src/domain/errors';

jest.mock('../../../src/domain/models/Trip');
jest.mock('../../../src/domain/models/TripDestination');
jest.mock('../../../src/domain/models/TripPlanningContext');
jest.mock('../../../src/domain/models/City');

const MockTrip = Trip as jest.MockedClass<typeof Trip>;
const MockTripDestination = TripDestination as jest.MockedClass<typeof TripDestination>;
const MockTripPlanningContext = TripPlanningContext as jest.MockedClass<typeof TripPlanningContext>;

const TRIP_ID = 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const USER_ID = 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const CITY_ID = 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const DEST_ID = 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const COUNTRY_ID = 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';

function makeTripData(overrides = {}) {
  return {
    id: TRIP_ID,
    ownerId: USER_ID,
    name: 'My Trip',
    startDate: new Date('2026-06-01'),
    endDate: new Date('2026-06-10'),
    status: 'draft',
    createdAt: new Date(),
    updatedAt: new Date(),
    destinations: [],
    ...overrides,
  };
}

function makeDestinationData(overrides = {}) {
  return {
    id: DEST_ID,
    tripId: TRIP_ID,
    cityId: CITY_ID,
    dayOrder: 1,
    daysCount: 3,
    notes: null,
    city: { id: CITY_ID, name: 'Paris', country: { name: 'France' } },
    ...overrides,
  };
}

function makeCityData(overrides = {}) {
  return {
    id: CITY_ID,
    name: 'Paris',
    countryId: COUNTRY_ID,
    timezone: 'Europe/Paris',
    createdAt: new Date(),
    coverage: { level: 'high', notes: 'Great coverage' },
    ...overrides,
  };
}

const today = new Date();
today.setHours(0, 0, 0, 0);
const tomorrow = new Date(today);
tomorrow.setDate(tomorrow.getDate() + 1);
const dayAfterTomorrow = new Date(today);
dayAfterTomorrow.setDate(dayAfterTomorrow.getDate() + 2);
const yesterday = new Date(today);
yesterday.setDate(yesterday.getDate() - 1);

function formatDate(d: Date): string {
  return d.toISOString().split('T')[0] as string;
}

describe('TripService', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    resetTripService();
  });

  describe('createTrip', () => {
    it('should create and return a trip with draft status when valid data is provided', async () => {
      const tripInstance = makeTripData();
      const saveMock = jest.fn().mockResolvedValue(tripInstance);
      MockTrip.mockImplementation(() => ({ ...tripInstance, save: saveMock } as unknown as Trip));

      const service = new TripService();
      const result = await service.createTrip('user-uuid-1', {
        startDate: formatDate(tomorrow),
        endDate: formatDate(dayAfterTomorrow),
        name: 'My Trip',
      });

      expect(saveMock).toHaveBeenCalledTimes(1);
      expect(result).toEqual(tripInstance);
    });

    it('should throw ValidationError when startDate is missing', async () => {
      const service = new TripService();
      await expect(
        service.createTrip('user-uuid-1', { startDate: '', endDate: formatDate(dayAfterTomorrow) })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when endDate is missing', async () => {
      const service = new TripService();
      await expect(
        service.createTrip('user-uuid-1', { startDate: formatDate(tomorrow), endDate: '' })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when endDate is not after startDate', async () => {
      const service = new TripService();
      await expect(
        service.createTrip('user-uuid-1', {
          startDate: formatDate(tomorrow),
          endDate: formatDate(tomorrow),
        })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when startDate is in the past', async () => {
      const service = new TripService();
      await expect(
        service.createTrip('user-uuid-1', {
          startDate: formatDate(yesterday),
          endDate: formatDate(tomorrow),
        })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when name exceeds 100 characters', async () => {
      const service = new TripService();
      await expect(
        service.createTrip('user-uuid-1', {
          startDate: formatDate(tomorrow),
          endDate: formatDate(dayAfterTomorrow),
          name: 'a'.repeat(101),
        })
      ).rejects.toThrow(ValidationError);
    });

    it('should create trip with null name when name is not provided', async () => {
      const tripInstance = makeTripData({ name: null });
      const saveMock = jest.fn().mockResolvedValue(tripInstance);
      MockTrip.mockImplementation(() => ({ ...tripInstance, save: saveMock } as unknown as Trip));

      const service = new TripService();
      await service.createTrip('user-uuid-1', {
        startDate: formatDate(tomorrow),
        endDate: formatDate(dayAfterTomorrow),
      });

      expect(MockTrip).toHaveBeenCalledWith(
        expect.objectContaining({ name: null })
      );
    });
  });

  describe('getUserTrips', () => {
    it('should return trips for the given ownerId', async () => {
      const trips = [makeTripData(), makeTripData({ id: '00eebc99-9c0b-4ef8-bb6d-6bb9bd380a22' })];
      jest.spyOn(Trip, 'findByOwnerId').mockResolvedValue(trips as unknown as Trip[]);

      const service = new TripService();
      const result = await service.getUserTrips(USER_ID);

      expect(Trip.findByOwnerId).toHaveBeenCalledWith(USER_ID, undefined);
      expect(result).toEqual(trips);
    });

    it('should filter trips by status when status is provided', async () => {
      const trips = [makeTripData()];
      jest.spyOn(Trip, 'findByOwnerId').mockResolvedValue(trips as unknown as Trip[]);

      const service = new TripService();
      await service.getUserTrips(USER_ID, 'draft');

      expect(Trip.findByOwnerId).toHaveBeenCalledWith(USER_ID, 'draft');
    });

    it('should return empty array when user has no trips', async () => {
      jest.spyOn(Trip, 'findByOwnerId').mockResolvedValue([]);

      const service = new TripService();
      const result = await service.getUserTrips(USER_ID);

      expect(result).toEqual([]);
    });
  });

  describe('getTripById', () => {
    it('should return trip with destinations when owner requests own trip', async () => {
      const tripData = makeTripData();
      jest.spyOn(Trip, 'findById').mockResolvedValue(tripData as unknown as Trip);

      const service = new TripService();
      const result = await service.getTripById(TRIP_ID, USER_ID);

      expect(Trip.findById).toHaveBeenCalledWith(TRIP_ID);
      expect(result).toEqual(tripData);
    });

    it('should throw NotFoundError when trip does not exist', async () => {
      jest.spyOn(Trip, 'findById').mockResolvedValue(null);

      const service = new TripService();
      await expect(service.getTripById(TRIP_ID, USER_ID)).rejects.toThrow(NotFoundError);
    });

    it('should throw ForbiddenError when requesting user is not the trip owner', async () => {
      const tripData = makeTripData({ ownerId: 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11' });
      jest.spyOn(Trip, 'findById').mockResolvedValue(tripData as unknown as Trip);

      const service = new TripService();
      await expect(service.getTripById(TRIP_ID, USER_ID)).rejects.toThrow(ForbiddenError);
    });

    it('should throw ValidationError when id is not a valid UUID', async () => {
      const service = new TripService();
      await expect(service.getTripById('not-a-uuid', USER_ID)).rejects.toThrow(ValidationError);
    });
  });

  describe('addDestination', () => {
    const validDestinationData = {
      cityId: CITY_ID,
      dayOrder: 1,
      daysCount: 3,
    };

    it('should add destination and return it with city data', async () => {
      const tripData = makeTripData();
      const destData = makeDestinationData();
      const saveMock = jest.fn().mockResolvedValue(destData);

      jest.spyOn(Trip, 'findById').mockResolvedValue(tripData as unknown as Trip);
      jest.spyOn(City, 'findById').mockResolvedValue(makeCityData() as unknown as City);
      jest.spyOn(TripDestination, 'findByTripIdAndOrder').mockResolvedValue(destData as unknown as TripDestination);
      MockTripDestination.mockImplementation(() => ({ ...destData, save: saveMock } as unknown as TripDestination));

      const service = new TripService();
      const result = await service.addDestination(TRIP_ID, USER_ID, validDestinationData);

      expect(result).toEqual(destData);
    });

    it('should throw ValidationError when cityId is missing', async () => {
      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, { cityId: '', dayOrder: 1, daysCount: 3 })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when cityId is not a valid UUID', async () => {
      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, { cityId: 'not-uuid', dayOrder: 1, daysCount: 3 })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when dayOrder is not a positive integer', async () => {
      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, { cityId: validDestinationData.cityId, dayOrder: 0, daysCount: 3 })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when daysCount is not a positive integer', async () => {
      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, { cityId: validDestinationData.cityId, dayOrder: 1, daysCount: -1 })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw NotFoundError when trip does not exist', async () => {
      jest.spyOn(Trip, 'findById').mockResolvedValue(null);

      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, validDestinationData)
      ).rejects.toThrow(NotFoundError);
    });

    it('should throw ForbiddenError when user does not own the trip', async () => {
      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData({ ownerId: 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11' }) as unknown as Trip);

      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, validDestinationData)
      ).rejects.toThrow(ForbiddenError);
    });

    it('should throw NotFoundError when cityId does not exist', async () => {
      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(City, 'findById').mockResolvedValue(null);

      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, validDestinationData)
      ).rejects.toThrow(NotFoundError);
    });

    it('should throw ConflictError when dayOrder is already used', async () => {
      const saveMock = jest.fn().mockRejectedValue(new ConflictError('Destination with this day order already exists'));

      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(City, 'findById').mockResolvedValue(makeCityData() as unknown as City);
      MockTripDestination.mockImplementation(() => ({ ...makeDestinationData(), save: saveMock } as unknown as TripDestination));

      const service = new TripService();
      await expect(
        service.addDestination(TRIP_ID, USER_ID, validDestinationData)
      ).rejects.toThrow(ConflictError);
    });
  });

  describe('savePlanningContext', () => {
    const validPreferences = {
      travelStyle: 'cultural',
      budget: 'moderate',
      pace: 'medium',
      interests: ['museums', 'food'],
      specialRequirements: null,
    };

    it('should save context and return it when valid preferences provided', async () => {
      const contextData = { id: 'ctx-1', tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [] };
      const saveMock = jest.fn().mockResolvedValue(contextData);

      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(TripDestination, 'findByTripId').mockResolvedValue([]);
      jest.spyOn(TripPlanningContext, 'getNextVersionForTrip').mockResolvedValue(1);
      MockTripPlanningContext.mockImplementation(() => ({ ...contextData, save: saveMock } as unknown as TripPlanningContext));

      const service = new TripService();
      const result = await service.savePlanningContext(TRIP_ID, USER_ID, validPreferences);

      expect(saveMock).toHaveBeenCalledTimes(1);
      expect(result).toEqual(contextData);
    });

    it('should capture city coverage in coverageSnapshot when destinations exist', async () => {
      const contextData = { id: 'ctx-1', tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [{ cityId: CITY_ID, cityName: 'Paris', level: 'high', notes: null }] };
      const saveMock = jest.fn().mockResolvedValue(contextData);
      const destWithCity = { cityId: CITY_ID, dayOrder: 1, daysCount: 3 } as unknown as TripDestination;

      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(TripDestination, 'findByTripId').mockResolvedValue([destWithCity]);
      jest.spyOn(City, 'findById').mockResolvedValue(makeCityData() as unknown as City);
      jest.spyOn(TripPlanningContext, 'getNextVersionForTrip').mockResolvedValue(1);
      MockTripPlanningContext.mockImplementation(() => ({ ...contextData, save: saveMock } as unknown as TripPlanningContext));

      const service = new TripService();
      await service.savePlanningContext(TRIP_ID, USER_ID, validPreferences);

      expect(City.findById).toHaveBeenCalledWith(CITY_ID);
    });

    it('should handle destination with no city coverage in coverageSnapshot', async () => {
      const contextData = { id: 'ctx-1', tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [] };
      const saveMock = jest.fn().mockResolvedValue(contextData);
      const destNoCoverage = { cityId: CITY_ID, dayOrder: 1, daysCount: 3 } as unknown as TripDestination;

      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(TripDestination, 'findByTripId').mockResolvedValue([destNoCoverage]);
      jest.spyOn(City, 'findById').mockResolvedValue(null);
      jest.spyOn(TripPlanningContext, 'getNextVersionForTrip').mockResolvedValue(1);
      MockTripPlanningContext.mockImplementation(() => ({ ...contextData, save: saveMock } as unknown as TripPlanningContext));

      const service = new TripService();
      await service.savePlanningContext(TRIP_ID, USER_ID, validPreferences);

      expect(saveMock).toHaveBeenCalledTimes(1);
    });

    it('should save context with null fields when no preferences provided', async () => {
      const contextData = { id: 'ctx-1', tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [] };
      const saveMock = jest.fn().mockResolvedValue(contextData);

      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(TripDestination, 'findByTripId').mockResolvedValue([]);
      jest.spyOn(TripPlanningContext, 'getNextVersionForTrip').mockResolvedValue(1);
      MockTripPlanningContext.mockImplementation(() => ({ ...contextData, save: saveMock } as unknown as TripPlanningContext));

      const service = new TripService();
      await service.savePlanningContext(TRIP_ID, USER_ID, {});

      expect(saveMock).toHaveBeenCalledTimes(1);
    });

    it('should throw ValidationError when travelStyle is invalid', async () => {
      const service = new TripService();
      await expect(
        service.savePlanningContext(TRIP_ID, USER_ID, { travelStyle: 'invalid' })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when interests contains unknown values', async () => {
      const service = new TripService();
      await expect(
        service.savePlanningContext(TRIP_ID, USER_ID, { interests: ['skydiving'] })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw ValidationError when specialRequirements exceeds 500 characters', async () => {
      const service = new TripService();
      await expect(
        service.savePlanningContext(TRIP_ID, USER_ID, {
          specialRequirements: 'a'.repeat(501),
        })
      ).rejects.toThrow(ValidationError);
    });

    it('should throw NotFoundError when trip does not exist', async () => {
      jest.spyOn(Trip, 'findById').mockResolvedValue(null);

      const service = new TripService();
      await expect(
        service.savePlanningContext(TRIP_ID, USER_ID, validPreferences)
      ).rejects.toThrow(NotFoundError);
    });

    it('should throw ForbiddenError when user does not own the trip', async () => {
      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData({ ownerId: 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11' }) as unknown as Trip);

      const service = new TripService();
      await expect(
        service.savePlanningContext(TRIP_ID, USER_ID, validPreferences)
      ).rejects.toThrow(ForbiddenError);
    });

    it('should increment version on each call', async () => {
      const contextData = { id: 'ctx-2', tripId: TRIP_ID, version: 2, inputsSnapshot: {}, coverageSnapshot: [] };
      const saveMock = jest.fn().mockResolvedValue(contextData);

      jest.spyOn(Trip, 'findById').mockResolvedValue(makeTripData() as unknown as Trip);
      jest.spyOn(TripDestination, 'findByTripId').mockResolvedValue([]);
      jest.spyOn(TripPlanningContext, 'getNextVersionForTrip').mockResolvedValue(2);
      MockTripPlanningContext.mockImplementation(() => ({ ...contextData, save: saveMock } as unknown as TripPlanningContext));

      const service = new TripService();
      await service.savePlanningContext(TRIP_ID, USER_ID, validPreferences);

      expect(MockTripPlanningContext).toHaveBeenCalledWith(
        expect.objectContaining({ version: 2 })
      );
    });
  });

  describe('getTripService', () => {
    it('should return singleton instance', () => {
      const s1 = getTripService();
      const s2 = getTripService();
      expect(s1).toBe(s2);
    });

    it('should return new instance after reset', () => {
      const s1 = getTripService();
      resetTripService();
      const s2 = getTripService();
      expect(s1).not.toBe(s2);
    });
  });
});
