import { ICliente } from "src/app/pages/cliente/dominio/ICliente";
import { IItem } from "./IItem";

export interface INota{
    id: number,
    numero: string,
    dataEmissao: Date,
    cliente: ICliente,
    itens: IItem[],
    valorTotal: number
}