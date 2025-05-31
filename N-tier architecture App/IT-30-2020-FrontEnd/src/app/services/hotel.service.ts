import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HOTEL_URL } from '../constants';
import { Hotel } from '../models/hotel';

@Injectable({
  providedIn: 'root'
})
export class HotelService {

  constructor(private httpClient:HttpClient) { }


  public getAllHotel():Observable<any>{
    return this.httpClient.get(`${HOTEL_URL}`);
  }

  public addHotel(hotel:Hotel):Observable<any>{

    return this.httpClient.post(`${HOTEL_URL}`,hotel);
  }

  public updateHotel(hotel:Hotel):Observable<any>{

    return this.httpClient.put(`${HOTEL_URL}/${hotel.id}`,hotel);
  }

  public deleteHotel(id:number):Observable<any>{

    return this.httpClient.delete(`${HOTEL_URL}/${id}`);
  }
}

