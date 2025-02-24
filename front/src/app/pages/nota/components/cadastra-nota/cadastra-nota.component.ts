import { Component, OnInit, ViewChild, EventEmitter, Output } from '@angular/core';
import { ItemForm } from '../../dominio/ItemForm';
import { NotaService } from '../../services/nota.service';
import { NotaForm } from '../../dominio/NotaForm';
import { DxSelectBoxComponent } from 'devextreme-angular';
import { IProduto } from 'src/app/pages/produto/dominio/IProduto';
import { ProdutoService } from 'src/app/pages/produto/services/produto.service';
import { ICliente } from 'src/app/pages/cliente/dominio/ICliente';
import { ClienteService } from 'src/app/pages/cliente/services/cliente.service';
import notify from 'devextreme/ui/notify';
import { IItem } from '../../dominio/IItem';

@Component({
  selector: 'app-cadastra-nota',
  templateUrl: './cadastra-nota.component.html',
  styleUrls: ['./cadastra-nota.component.scss']
})
export class CadastraNotaComponent implements OnInit {

  dataHoje: Date = new Date();

  clientes!: ICliente[];
  clienteSelecionado!: ICliente;
  @ViewChild(DxSelectBoxComponent, { static: false }) selectCliente!: DxSelectBoxComponent;

  produtos!: IProduto[];
  itens: IItem[] = [];

  semItens = false;

  produtoSelecionado!: IProduto;

  precoUnit: number = 0;
  qtdProduto: number = 0;
  valorTotalItem: number = 0;

  valorTotalNota: number = 0;

  notaEmitida!: NotaForm;

  @Output() idNotaCadastrada = new EventEmitter();

  constructor(private clienteService: ClienteService, private produtoService: ProdutoService, private notaService: NotaService) { }

  ngOnInit(): void {

    this.clienteService.lista().subscribe(
      result => this.clientes = result
    );

    this.produtoService.lista().subscribe(result => {
      this.produtos = result;
    })

  }

  mostraPrecoUnit(event: any) {
    this.precoUnit = event.value.preco;
    this.calculaValorTotItem(); //caso o usuário selecione a qtd de produtos antes
  }

  calculaValorTotItem() {

    if (this.produtoSelecionado) {
      this.valorTotalItem = this.precoUnit * this.qtdProduto;
    }
  }

  adicionaItem(): void {

    const item: IItem = {
      id: 0,
      produto: this.produtoSelecionado,
      quantidade: this.qtdProduto,
      valorTotal: this.valorTotalItem
    }

    if (this.itens.find(itemArray => itemArray.produto.id === item.produto.id))
      return;

    if (this.produtoSelecionado && this.qtdProduto > 0) {

      this.itens.push(item);

      this.calculaValorTotNota(item);
    }

    this.atualizaFlagSemItens();
  }

  calculaValorTotNota(item: IItem): void {
    this.valorTotalNota += item.valorTotal;
  }

  subtraiItemNota(event: any): void {
    this.valorTotalNota -= event.itemData.valorTotal;
    this.atualizaFlagSemItens();
  }

  atualizaFlagSemItens(): void {
    this.semItens = this.itens.length === 0;
  }


  emiteNota(): void {

    if (this.clienteSelecionado === undefined) {
      notify('Selecione o cliente!', 'warning', 4000);
      this.selectCliente.isValid = false;
      return;
    }

    if (this.itens.length === 0) {
      this.atualizaFlagSemItens();
      notify('Deve haver ao menos 1 item na nota!', 'warning', 4000);
      return;
    }

    this.notaEmitida = {
      idCliente: this.clienteSelecionado.id,
      itens: this.itens.map(item => {
        return new ItemForm(item.produto.id, item.quantidade)
      }),
      valorTotal: this.valorTotalNota
    }

    this.notaService.cadastraNota(this.notaEmitida).subscribe({
      next: (nota) => {
        notify('Nota cadastrada!', 'success', 4000);
        this.idNotaCadastrada.emit(nota.id);
      },
      error: (err) => {
        notify('Falha ao cadastrar nota!', 'error', 4000);
      }
    })

  }

}
