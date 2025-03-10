import { Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { ICliente } from '../../dominio/ICliente';
import { ClienteService } from '../../services/cliente.service';
import { DxFormComponent } from 'devextreme-angular';
import notify from 'devextreme/ui/notify';

@Component({
  selector: 'app-cadastra-cliente',
  templateUrl: './cadastra-cliente.component.html',
  styleUrls: ['./cadastra-cliente.component.scss']
})
export class CadastraClienteComponent {

  cliente!: ICliente;

  @ViewChild(DxFormComponent, { static: false }) form!: DxFormComponent;

  buttonOptions = {
    text: "Cadastrar",
    type: "success",
    onClick: () => this.cadastraCliente()
  };

  @Output() idClienteCadastrado = new EventEmitter();

  constructor(private clienteService: ClienteService) {

  }

  cadastraCliente(): void {

    const reultadoValidacao = this.form.instance.validate();

    if (reultadoValidacao.isValid) {

      this.clienteService.cadastra(this.cliente).subscribe({
        next: (cliente) => {
          notify('Cliente cadastrado!', 'success', 3000);

          this.form.instance.updateData('nome', '');
          this.form.instance.getEditor('nome')?.reset();

          this.idClienteCadastrado.emit(cliente.id);
        },
        error: (err) => {
          notify('Falha ao cadastrar cliente!', 'error', 3000)
        }
      })
    }
  }

}
