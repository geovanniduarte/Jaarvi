import {
  TripController,
  getTripController,
  resetTripController,
} from '../../../src/presentation/controllers/tripController';
import { getTripService, resetTripService } from '../../../src/application/services/tripService';
import { createMockRequest, createMockResponse, createMockNext } from '../../../test-utils/mocks';
import { NotFoundError, ForbiddenError, ConflictError } from '../../../src/domain/errors';

jest.mock('../../../src/application/services/tripService');

const mockGetTripService = getTripService as jest.MockedFunction<typeof getTripService>;

function makeAuthRequest(overrides = {}) {
  return createMockRequest({
    user: { id: 'user-uuid-1', email: 'user@test.com' },
    ...overrides,
  }) as ReturnType<typeof createMockRequest> & { user: { id: string; email: string } };
}

describe('TripController', () => {
  let mockService: {
    createTrip: jest.Mock;
    getUserTrips: jest.Mock;
    getTripById: jest.Mock;
    addDestination: jest.Mock;
    savePlanningContext: jest.Mock;
  };

  beforeEach(() => {
    jest.clearAllMocks();
    resetTripController();
    resetTripService();

    mockService = {
      createTrip: jest.fn(),
      getUserTrips: jest.fn(),
      getTripById: jest.fn(),
      addDestination: jest.fn(),
      savePlanningContext: jest.fn(),
    };

    mockGetTripService.mockReturnValue(mockService as ReturnType<typeof getTripService>);
  });

  describe('createTrip', () => {
    it('should return 201 with trip data on success', async () => {
      const tripData = { id: 'trip-1', name: 'Test', status: 'draft' };
      mockService.createTrip.mockResolvedValue(tripData);

      const req = makeAuthRequest({ body: { startDate: '2026-06-01', endDate: '2026-06-10' } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.createTrip(req as never, res as never, next);

      expect(res.status).toHaveBeenCalledWith(201);
      expect(res.json).toHaveBeenCalledWith({ success: true, data: tripData });
      expect(next).not.toHaveBeenCalled();
    });

    it('should call next with error when service throws', async () => {
      const error = new Error('Service error');
      mockService.createTrip.mockRejectedValue(error);

      const req = makeAuthRequest({ body: {} });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.createTrip(req as never, res as never, next);

      expect(next).toHaveBeenCalledWith(error);
      expect(res.json).not.toHaveBeenCalled();
    });
  });

  describe('getUserTrips', () => {
    it('should return 200 with trips array on success', async () => {
      const trips = [{ id: 'trip-1' }, { id: 'trip-2' }];
      mockService.getUserTrips.mockResolvedValue(trips);

      const req = makeAuthRequest({ query: {} });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.getUserTrips(req as never, res as never, next);

      expect(res.status).toHaveBeenCalledWith(200);
      expect(res.json).toHaveBeenCalledWith({ success: true, data: trips });
    });

    it('should pass status query param to service', async () => {
      mockService.getUserTrips.mockResolvedValue([]);

      const req = makeAuthRequest({ query: { status: 'draft' } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.getUserTrips(req as never, res as never, next);

      expect(mockService.getUserTrips).toHaveBeenCalledWith('user-uuid-1', 'draft');
    });
  });

  describe('getTripById', () => {
    it('should return 200 with trip on success', async () => {
      const trip = { id: 'trip-1', destinations: [] };
      mockService.getTripById.mockResolvedValue(trip);

      const req = makeAuthRequest({ params: { id: 'trip-1' } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.getTripById(req as never, res as never, next);

      expect(res.status).toHaveBeenCalledWith(200);
      expect(res.json).toHaveBeenCalledWith({ success: true, data: trip });
    });

    it('should call next with NotFoundError when trip not found', async () => {
      const error = new NotFoundError('Trip not found');
      mockService.getTripById.mockRejectedValue(error);

      const req = makeAuthRequest({ params: { id: 'trip-1' } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.getTripById(req as never, res as never, next);

      expect(next).toHaveBeenCalledWith(error);
    });

    it('should call next with ForbiddenError when access denied', async () => {
      const error = new ForbiddenError('Access denied to this trip');
      mockService.getTripById.mockRejectedValue(error);

      const req = makeAuthRequest({ params: { id: 'trip-1' } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.getTripById(req as never, res as never, next);

      expect(next).toHaveBeenCalledWith(error);
    });
  });

  describe('addDestination', () => {
    it('should return 201 with destination on success', async () => {
      const destination = { id: 'dest-1', cityId: 'city-1', dayOrder: 1 };
      mockService.addDestination.mockResolvedValue(destination);

      const req = makeAuthRequest({ params: { id: 'trip-1' }, body: { cityId: 'city-1', dayOrder: 1, daysCount: 3 } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.addDestination(req as never, res as never, next);

      expect(res.status).toHaveBeenCalledWith(201);
      expect(res.json).toHaveBeenCalledWith({ success: true, data: destination });
    });

    it('should call next with ConflictError when dayOrder conflict', async () => {
      const error = new ConflictError('Destination with this day order already exists');
      mockService.addDestination.mockRejectedValue(error);

      const req = makeAuthRequest({ params: { id: 'trip-1' }, body: {} });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.addDestination(req as never, res as never, next);

      expect(next).toHaveBeenCalledWith(error);
    });
  });

  describe('savePlanningContext', () => {
    it('should return 201 with planning context on success', async () => {
      const context = { id: 'ctx-1', version: 1 };
      mockService.savePlanningContext.mockResolvedValue(context);

      const req = makeAuthRequest({ params: { id: 'trip-1' }, body: { travelStyle: 'cultural' } });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.savePlanningContext(req as never, res as never, next);

      expect(res.status).toHaveBeenCalledWith(201);
      expect(res.json).toHaveBeenCalledWith({ success: true, data: context });
    });

    it('should call next with error when service throws', async () => {
      const error = new NotFoundError('Trip not found');
      mockService.savePlanningContext.mockRejectedValue(error);

      const req = makeAuthRequest({ params: { id: 'trip-1' }, body: {} });
      const res = createMockResponse();
      const next = createMockNext();

      const controller = new TripController();
      await controller.savePlanningContext(req as never, res as never, next);

      expect(next).toHaveBeenCalledWith(error);
    });
  });

  describe('getTripController', () => {
    it('should return singleton instance', () => {
      const c1 = getTripController();
      const c2 = getTripController();
      expect(c1).toBe(c2);
    });

    it('should return new instance after reset', () => {
      const c1 = getTripController();
      resetTripController();
      const c2 = getTripController();
      expect(c1).not.toBe(c2);
    });
  });
});
