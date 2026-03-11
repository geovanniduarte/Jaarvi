import { Country } from '../models/Country';
import { City } from '../models/City';

export interface IDestinationRepository {
  findAllCountries(): Promise<Country[]>;
  findAllCities(countryId?: string): Promise<City[]>;
  findCityById(id: string): Promise<City | null>;
}
