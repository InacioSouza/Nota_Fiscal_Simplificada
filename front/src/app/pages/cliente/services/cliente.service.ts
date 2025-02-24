import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ICliente } from '../dominio/ICliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  constructor(private http: HttpClient) { }

  cadastra(cliente: ICliente): Observable<any> {

    return this.http.post<ICliente>('http://localhost:8080/snf/cliente', cliente);
  }

  lista(): Observable<ICliente[]> {
    return this.http.get<ICliente[]>('http://localhost:8080/snf/cliente');
  }

  modifica(id: number, clienteModificado: ICliente): Observable<any> {
    return this.http.put(`http://localhost:8080/snf/cliente/${id}`, clienteModificado);
  }

  deleta(id: number): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/snf/cliente/${id}`);
  }
}
