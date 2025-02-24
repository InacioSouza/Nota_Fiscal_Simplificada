import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IItem } from '../dominio/IItem';
import { Observable } from 'rxjs';
import { NotaForm } from '../dominio/NotaForm';
import { INota } from '../dominio/INota';

@Injectable({
  providedIn: 'root'
})
export class NotaService {

  private urlItem: string = 'http://localhost:8080/snf/item'
  private urlNota: string = 'http://localhost:8080/snf/nota'
  constructor(private http: HttpClient) { }

  cadastraItem(item: IItem): Observable<IItem> {
    return this.http.post<IItem>(this.urlItem, item);
  }

  cadastraNota(nota: NotaForm): Observable<any> {
    return this.http.post<any>(this.urlNota, nota);
  }

  lista(): Observable<INota[]> {
    return this.http.get<INota[]>(this.urlNota);
  }
}
