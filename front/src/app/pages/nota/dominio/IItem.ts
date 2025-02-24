import { IProduto } from "src/app/pages/produto/dominio/IProduto";

export interface IItem {
    id: number,
    produto: IProduto,
    quantidade: number,
    valorTotal: number
}