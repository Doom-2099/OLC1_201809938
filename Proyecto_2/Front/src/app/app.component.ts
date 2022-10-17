import { AfterViewInit, Component, OnInit } from '@angular/core';
import * as M from 'materialize-css';
import { ApiService } from './api.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

	constructor(private api: ApiService)  { }

	ngOnInit(): void {
		M.AutoInit();
		this.getFiles();
	}

	ngAfterViewInit(): void {

	}

	getFiles() {
		this.api.getFiles()
			.subscribe(resp => {
				console.log(resp);
				M.toast({ html: 'Se obtuvieron los datos',  classes: 'green white-text '});
			});
	}
}


// Agregar Materialize aqui y hacer las peticiones a la API.servic