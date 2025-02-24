import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IProduto } from '../dominio/IProduto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProdutoService {

  private url: string = 'http://localhost:8080/snf/produto'

  constructor(private http: HttpClient) { }

  cadastra(produto: IProduto): Observable<any> {
    return this.http.post<IProduto>(`${this.url}`, produto);
  }

  lista(): Observable<IProduto[]> {
    return this.http.get<IProduto[]>(this.url);
  }

  deleta(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/${id}`);
  }

  modifica(id: number, produtoModificado: IProduto): Observable<any> {
    return this.http.put<any>(`${this.url}/${id}`, produtoModificado);
  }
}
