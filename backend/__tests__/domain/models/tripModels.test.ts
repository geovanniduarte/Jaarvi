import { Trip } from '../../../src/domain/models/Trip';
import { TripDestination } from '../../../src/domain/models/TripDestination';
import { TripPlanningContext } from '../../../src/domain/models/TripPlanningContext';
import { Country } from '../../../src/domain/models/Country';
import { City } from '../../../src/domain/models/City';
import { getPrismaClient } from '../../../src/infrastructure/prismaClient';
import { Prisma } from '@prisma/client';
import { ConflictError } from '../../../src/domain/errors';

jest.mock('../../../src/infrastructure/prismaClient');

const TRIP_ID = 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const USER_ID = 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const CITY_ID = 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const DEST_ID = 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';
const COUNTRY_ID = 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11';

function makeMockPrisma() {
  return {
    trip: {
      create: jest.fn(),
      update: jest.fn(),
      findUnique: jest.fn(),
      findMany: jest.fn(),
    },
    tripDestination: {
      create: jest.fn(),
      findMany: jest.fn(),
      findFirst: jest.fn(),
    },
    tripPlanningContext: {
      create: jest.fn(),
      findMany: jest.fn(),
      aggregate: jest.fn(),
    },
    country: {
      findMany: jest.fn(),
    },
    city: {
      findMany: jest.fn(),
      findUnique: jest.fn(),
    },
  };
}

describe('Trip model', () => {
  let mockPrisma: ReturnType<typeof makeMockPrisma>;

  beforeEach(() => {
    mockPrisma = makeMockPrisma();
    (getPrismaClient as jest.Mock).mockReturnValue(mockPrisma);
  });

  it('should construct with provided data', () => {
    const trip = new Trip({
      id: TRIP_ID,
      ownerId: USER_ID,
      name: 'Test',
      startDate: new Date(),
      endDate: new Date(),
      status: 'draft',
    });
    expect(trip.id).toBe(TRIP_ID);
    expect(trip.ownerId).toBe(USER_ID);
    expect(trip.status).toBe('draft');
  });

  it('save() should create a new trip when no id is set', async () => {
    const created = { id: TRIP_ID, ownerId: USER_ID, name: 'T', startDate: new Date(), endDate: new Date(), status: 'draft' };
    mockPrisma.trip.create.mockResolvedValue(created);

    const trip = new Trip({ ownerId: USER_ID, name: 'T', startDate: new Date(), endDate: new Date(), status: 'draft' });
    const result = await trip.save();

    expect(mockPrisma.trip.create).toHaveBeenCalledTimes(1);
    expect(result).toBeInstanceOf(Trip);
    expect(result.id).toBe(TRIP_ID);
  });

  it('save() should update an existing trip when id is set', async () => {
    const updated = { id: TRIP_ID, ownerId: USER_ID, name: 'Updated', startDate: new Date(), endDate: new Date(), status: 'draft' };
    mockPrisma.trip.update.mockResolvedValue(updated);

    const trip = new Trip({ id: TRIP_ID, ownerId: USER_ID, name: 'Updated', startDate: new Date(), endDate: new Date(), status: 'draft' });
    const result = await trip.save();

    expect(mockPrisma.trip.update).toHaveBeenCalledWith(expect.objectContaining({ where: { id: TRIP_ID } }));
    expect(result).toBeInstanceOf(Trip);
  });

  it('findById() should return Trip when found', async () => {
    const data = { id: TRIP_ID, ownerId: USER_ID, name: null, startDate: new Date(), endDate: new Date(), status: 'draft', destinations: [] };
    mockPrisma.trip.findUnique.mockResolvedValue(data);

    const result = await Trip.findById(TRIP_ID);

    expect(result).toBeInstanceOf(Trip);
    expect(result?.id).toBe(TRIP_ID);
  });

  it('findById() should return null when not found', async () => {
    mockPrisma.trip.findUnique.mockResolvedValue(null);

    const result = await Trip.findById(TRIP_ID);
    expect(result).toBeNull();
  });

  it('findByOwnerId() should return array of trips', async () => {
    const data = [
      { id: TRIP_ID, ownerId: USER_ID, name: null, startDate: new Date(), endDate: new Date(), status: 'draft' },
    ];
    mockPrisma.trip.findMany.mockResolvedValue(data);

    const result = await Trip.findByOwnerId(USER_ID);

    expect(result).toHaveLength(1);
    expect(result[0]).toBeInstanceOf(Trip);
  });

  it('findByOwnerId() should pass status filter when provided', async () => {
    mockPrisma.trip.findMany.mockResolvedValue([]);

    await Trip.findByOwnerId(USER_ID, 'draft');

    expect(mockPrisma.trip.findMany).toHaveBeenCalledWith(
      expect.objectContaining({ where: { ownerId: USER_ID, status: 'draft' } })
    );
  });
});

describe('TripDestination model', () => {
  let mockPrisma: ReturnType<typeof makeMockPrisma>;

  beforeEach(() => {
    mockPrisma = makeMockPrisma();
    (getPrismaClient as jest.Mock).mockReturnValue(mockPrisma);
  });

  it('should construct with provided data', () => {
    const dest = new TripDestination({
      id: DEST_ID,
      tripId: TRIP_ID,
      cityId: CITY_ID,
      dayOrder: 1,
      daysCount: 3,
      notes: null,
    });
    expect(dest.tripId).toBe(TRIP_ID);
    expect(dest.dayOrder).toBe(1);
  });

  it('save() should create destination', async () => {
    const created = { id: DEST_ID, tripId: TRIP_ID, cityId: CITY_ID, dayOrder: 1, daysCount: 3, notes: null };
    mockPrisma.tripDestination.create.mockResolvedValue(created);

    const dest = new TripDestination({ tripId: TRIP_ID, cityId: CITY_ID, dayOrder: 1, daysCount: 3, notes: null });
    const result = await dest.save();

    expect(mockPrisma.tripDestination.create).toHaveBeenCalledTimes(1);
    expect(result).toBeInstanceOf(TripDestination);
  });

  it('save() should throw ConflictError on Prisma P2002', async () => {
    const p2002Error = new Prisma.PrismaClientKnownRequestError('Unique constraint failed', {
      code: 'P2002',
      clientVersion: '5.0.0',
    });
    mockPrisma.tripDestination.create.mockRejectedValue(p2002Error);

    const dest = new TripDestination({ tripId: TRIP_ID, cityId: CITY_ID, dayOrder: 1, daysCount: 3, notes: null });
    await expect(dest.save()).rejects.toThrow(ConflictError);
  });

  it('save() should rethrow non-P2002 Prisma errors', async () => {
    const dbError = new Error('DB connection lost');
    mockPrisma.tripDestination.create.mockRejectedValue(dbError);

    const dest = new TripDestination({ tripId: TRIP_ID, cityId: CITY_ID, dayOrder: 1, daysCount: 3, notes: null });
    await expect(dest.save()).rejects.toThrow('DB connection lost');
  });

  it('findByTripId() should return destinations array', async () => {
    const data = [{ id: DEST_ID, tripId: TRIP_ID, cityId: CITY_ID, dayOrder: 1, daysCount: 3, notes: null, city: { country: {} } }];
    mockPrisma.tripDestination.findMany.mockResolvedValue(data);

    const result = await TripDestination.findByTripId(TRIP_ID);
    expect(result).toHaveLength(1);
    expect(result[0]).toBeInstanceOf(TripDestination);
  });

  it('findByTripIdAndOrder() should return destination when found', async () => {
    const data = { id: DEST_ID, tripId: TRIP_ID, cityId: CITY_ID, dayOrder: 1, daysCount: 3, notes: null, city: { country: {} } };
    mockPrisma.tripDestination.findFirst.mockResolvedValue(data);

    const result = await TripDestination.findByTripIdAndOrder(TRIP_ID, 1);
    expect(result).toBeInstanceOf(TripDestination);
  });

  it('findByTripIdAndOrder() should return null when not found', async () => {
    mockPrisma.tripDestination.findFirst.mockResolvedValue(null);

    const result = await TripDestination.findByTripIdAndOrder(TRIP_ID, 1);
    expect(result).toBeNull();
  });
});

describe('TripPlanningContext model', () => {
  let mockPrisma: ReturnType<typeof makeMockPrisma>;

  beforeEach(() => {
    mockPrisma = makeMockPrisma();
    (getPrismaClient as jest.Mock).mockReturnValue(mockPrisma);
  });

  it('should construct with provided data', () => {
    const ctx = new TripPlanningContext({
      id: 'ctx-1',
      tripId: TRIP_ID,
      version: 1,
      inputsSnapshot: { preferences: {} },
      coverageSnapshot: [],
    });
    expect(ctx.tripId).toBe(TRIP_ID);
    expect(ctx.version).toBe(1);
  });

  it('save() should create planning context', async () => {
    const created = { id: 'ctx-1', tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [] };
    mockPrisma.tripPlanningContext.create.mockResolvedValue(created);

    const ctx = new TripPlanningContext({ tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [] });
    const result = await ctx.save();

    expect(mockPrisma.tripPlanningContext.create).toHaveBeenCalledTimes(1);
    expect(result).toBeInstanceOf(TripPlanningContext);
  });

  it('findByTripId() should return contexts ordered by version desc', async () => {
    const data = [
      { id: 'ctx-2', tripId: TRIP_ID, version: 2, inputsSnapshot: {}, coverageSnapshot: [] },
      { id: 'ctx-1', tripId: TRIP_ID, version: 1, inputsSnapshot: {}, coverageSnapshot: [] },
    ];
    mockPrisma.tripPlanningContext.findMany.mockResolvedValue(data);

    const result = await TripPlanningContext.findByTripId(TRIP_ID);
    expect(result).toHaveLength(2);
    expect(result[0]).toBeInstanceOf(TripPlanningContext);
  });

  it('getNextVersionForTrip() should return 1 when no prior contexts exist', async () => {
    mockPrisma.tripPlanningContext.aggregate.mockResolvedValue({ _max: { version: null } });

    const result = await TripPlanningContext.getNextVersionForTrip(TRIP_ID);
    expect(result).toBe(1);
  });

  it('getNextVersionForTrip() should return max + 1 when contexts exist', async () => {
    mockPrisma.tripPlanningContext.aggregate.mockResolvedValue({ _max: { version: 3 } });

    const result = await TripPlanningContext.getNextVersionForTrip(TRIP_ID);
    expect(result).toBe(4);
  });
});

describe('Country model', () => {
  let mockPrisma: ReturnType<typeof makeMockPrisma>;

  beforeEach(() => {
    mockPrisma = makeMockPrisma();
    (getPrismaClient as jest.Mock).mockReturnValue(mockPrisma);
  });

  it('should construct with provided data', () => {
    const country = new Country({ id: COUNTRY_ID, isoCode: 'FR', name: 'France', createdAt: new Date() });
    expect(country.name).toBe('France');
    expect(country.isoCode).toBe('FR');
  });

  it('findAll() should return all countries ordered by name', async () => {
    const data = [
      { id: COUNTRY_ID, isoCode: 'FR', name: 'France', createdAt: new Date() },
      { id: 'c2', isoCode: 'ES', name: 'Spain', createdAt: new Date() },
    ];
    mockPrisma.country.findMany.mockResolvedValue(data);

    const result = await Country.findAll();
    expect(result).toHaveLength(2);
    expect(result[0]).toBeInstanceOf(Country);
    expect(mockPrisma.country.findMany).toHaveBeenCalledWith({ orderBy: { name: 'asc' } });
  });
});

describe('City model', () => {
  let mockPrisma: ReturnType<typeof makeMockPrisma>;

  beforeEach(() => {
    mockPrisma = makeMockPrisma();
    (getPrismaClient as jest.Mock).mockReturnValue(mockPrisma);
  });

  it('should construct with provided data', () => {
    const city = new City({
      id: CITY_ID,
      countryId: COUNTRY_ID,
      name: 'Paris',
      timezone: 'Europe/Paris',
      createdAt: new Date(),
      country: { id: COUNTRY_ID, isoCode: 'FR', name: 'France', createdAt: new Date() },
      coverage: { level: 'high', notes: null },
    });
    expect(city.name).toBe('Paris');
    expect(city.country).toBeDefined();
    expect(city.coverage?.level).toBe('high');
  });

  it('should set coverage to null when not provided', () => {
    const city = new City({
      id: CITY_ID,
      countryId: COUNTRY_ID,
      name: 'Paris',
      timezone: 'Europe/Paris',
      createdAt: new Date(),
    });
    expect(city.coverage).toBeNull();
  });

  it('findAll() should return all cities', async () => {
    const data = [
      { id: CITY_ID, countryId: COUNTRY_ID, name: 'Paris', timezone: 'Europe/Paris', createdAt: new Date(), country: {}, coverage: null },
    ];
    mockPrisma.city.findMany.mockResolvedValue(data);

    const result = await City.findAll();
    expect(result).toHaveLength(1);
    expect(result[0]).toBeInstanceOf(City);
    expect(mockPrisma.city.findMany).toHaveBeenCalledWith(
      expect.objectContaining({ where: {} })
    );
  });

  it('findAll() should filter by countryId when provided', async () => {
    mockPrisma.city.findMany.mockResolvedValue([]);

    await City.findAll(COUNTRY_ID);

    expect(mockPrisma.city.findMany).toHaveBeenCalledWith(
      expect.objectContaining({ where: { countryId: COUNTRY_ID } })
    );
  });

  it('findById() should return City when found', async () => {
    const data = { id: CITY_ID, countryId: COUNTRY_ID, name: 'Paris', timezone: 'Europe/Paris', createdAt: new Date(), country: {}, coverage: null };
    mockPrisma.city.findUnique.mockResolvedValue(data);

    const result = await City.findById(CITY_ID);
    expect(result).toBeInstanceOf(City);
    expect(result?.id).toBe(CITY_ID);
  });

  it('findById() should return null when not found', async () => {
    mockPrisma.city.findUnique.mockResolvedValue(null);

    const result = await City.findById(CITY_ID);
    expect(result).toBeNull();
  });
});
