import { AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import * as M from 'materialize-css';
import { ApiService } from './api.service';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

    @ViewChild('filesDisp') filesModal1!: ElementRef;
    @ViewChild('savefiles') filesModal2!: ElementRef;
	@ViewChild('tabsCode') tabsCode!: ElementRef;
	@ViewChild('newFileName') newFilename!: ElementRef;
	@ViewChild('contentTabsCode') contentTabsCode!: ElementRef;
	@ViewChild('fileUploaded') fileUploaded!: ElementRef;

	constructor(private api: ApiService, private render: Renderer2)  { }

	ngOnInit(): void {
		this.getFiles();
		M.AutoInit();
	}

	ngAfterViewInit(): void {

	}

	getFiles() {
		this.api.getFiles()
			.subscribe(resp => {
                resp.files.forEach(file => {
					this.generateOption(file);
				});

				M.AutoInit();
				M.toast({ html: 'Se obtuvieron los datos',  classes: 'green white-text '});
			});
	}

	newFile() {
		this.api.newFile(this.newFilename.nativeElement.value)
			.subscribe(resp => {
				if(resp.flag) {
					M.toast({ html: 'Se Creo El Archivo Correctamente', classes: 'purple white-text' });

					this.generateCodeSpace(resp.name, '');
					this.generateOption(resp.name);

					M.AutoInit();
				} else {
					M.toast({ html: 'No Se Creo El Archivo Correctamente ', classes: 'red white-text' });
				}
			});
	}

	uploadFile() {
		const file = this.fileUploaded.nativeElement.files[0];
		const formData = new FormData();
		formData.append('file', file, file.name);

		this.api.uploadFile(formData, file.name)
			.subscribe(resp => {
				if(resp.flag) {
					M.toast({ html: resp.message, classes: 'green white-text' });
					this.generateCodeSpace(resp.name, resp.code);
					this.generateOption(resp.name);
					M.AutoInit();
				} else {
					M.toast({ html: resp.message, classes: 'red white-text' });
				}
			});
	}

	saveFile() {
		const file = this.filesModal2.nativeElement.value;
		if(file != '') {
			let nodo = this.contentTabsCode.nativeElement.children[1];

			while(nodo != null) {
				if(nodo.id == file) {
					const text = nodo.children[0];
					this.api.saveFile(file, text.value)
						.subscribe(resp => {
							if(resp.flag) {
								M.toast({ html: resp.message, classes: 'green white-text' });
							} else {
								M.toast({ html: resp.message, classes: 'orange white-text' });
							}
						});
				}

				nodo = this.render.nextSibling(nodo);
			}
		}
	}

	nuevoTab() {
		const file = this.filesModal1.nativeElement.value;

		if(file != '') {
			this.api.nuevoTab(file)
				.subscribe(resp => {
					if(resp.flag) {
						M.toast({ html: resp.message, classes: 'green white-text' });
						this.generateCodeSpace(file, resp.code);
						M.AutoInit();
					} else {
						M.toast({ html: resp.message, classes: 'red white-text' });
					}
				});
		}
	}

	closeTab() {
		let nodo = this.tabsCode.nativeElement.children[0];
		let filename = '';
		let parts;

		while(nodo != null) {
			let child = nodo.children[0];
			filename = child.href;
			filename = filename.replace('#', '');
			parts = filename.split('/').pop();

			if(child.className.includes('active')) {
				this.render.removeChild(this.tabsCode.nativeElement, nodo);
				break;
			}
			
			nodo = this.render.nextSibling(nodo);
		}

		let nodo2 = this.contentTabsCode.nativeElement.children[1];

		while(nodo2 != null) {
			if(nodo2.id == parts) {
				this.render.removeChild(this.contentTabsCode.nativeElement, nodo2);
				break;
			}

			nodo2 = this.render.nextSibling(nodo2);
		}

		M.AutoInit();
	}

	runCode() {
		let nodo = this.tabsCode.nativeElement.children[0];
		let filename = '';
		let parts;

		while(nodo != null) {
			let child = nodo.children[0];
			filename = child.href;
			filename = filename.replace('#', '');
			parts = filename.split('/').pop();

			if(child.className.includes('active')) {
				break;
			}
			
			nodo = this.render.nextSibling(nodo);
		}

		let nodo2 = this.contentTabsCode.nativeElement.children[1];

		while(nodo2 != null) {
			if(nodo2.id == parts) {
				const text = nodo2.children[0];
				this.api.runCode(text.value, parts)
					.subscribe(resp => {
						// Agregar respuestas de la compilacion en Jison
						
					});
			}

			nodo2 = this.render.nextSibling(nodo2);
		}

		M.AutoInit();
	}


	generateCodeSpace(filename: string, content: string) {
		var li = this.render.createElement('li');
		this.render.addClass(li, 'tab');
		this.render.addClass(li, 'col');
		this.render.addClass(li, 's3');

		var a = this.render.createElement('a');
		this.render.addClass(a, 'white-text');
		this.render.setAttribute(a, 'href', '#' + filename);
		a.innerHTML = filename;
		this.render.appendChild(li, a);
		this.render.appendChild(this.tabsCode.nativeElement, li);

		var div = this.render.createElement('div');
		this.render.addClass(div, 'col');
		this.render.addClass(div, 's12');
		this.render.setAttribute(div, 'id', filename);

		const textArea = this.render.createElement('textarea');
		this.render.setStyle(textArea, 'height', '370px');
		this.render.addClass(textArea, 'white-text');
		this.render.setAttribute(textArea, 'placeholder', 'Write Your Code Here');
		textArea.innerHTML = content;
		this.render.appendChild(div, textArea);
		this.render.appendChild(this.contentTabsCode.nativeElement, div);
	}

	generateOption(filename: string) {
		let op = this.render.createElement('option');
		this.render.setAttribute(op, 'value', filename);
		op.innerHTML = filename;
		this.render.appendChild(this.filesModal1.nativeElement, op);

		op = this.render.createElement('option');
		this.render.setAttribute(op, 'value', filename);
		op.innerHTML = filename;
		this.render.appendChild(this.filesModal2.nativeElement, op);
	}
}




// Agregar Materialize aqui y hacer las peticiones a la API.service